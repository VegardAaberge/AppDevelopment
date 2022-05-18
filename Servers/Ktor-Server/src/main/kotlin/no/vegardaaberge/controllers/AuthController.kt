package no.vegardaaberge.controllers

import no.vegardaaberge.data.model.Reset
import no.vegardaaberge.data.model.User
import no.vegardaaberge.data.responses.LoginResponse
import no.vegardaaberge.data.responses.SimpleResponse
import no.vegardaaberge.data.sources.AuthDataSource

class AuthController(
    private val authDataSource: AuthDataSource
) {
    suspend fun tryLogin(username: String, password: String) : LoginResponse{

        val currentUser = authDataSource.getUserFromUsernameAndPassword(username, password)

        return currentUser?.let {user ->
            LoginResponse(
                successful = true,
                message = "You are now logged in",
                email = user.email,
                profileName = user.profileName,
                phoneNumber = user.phoneNumber,
                createProfile = password == "123"
            )
        } ?: LoginResponse(false, "The email or password is incorrect")
    }

    suspend fun tryRegister(username: String, password: String, email: String) : SimpleResponse{

        val userExist = authDataSource.checkIfUserExist(username)

        return if(!userExist) {
            val newUser = User(
                username = username,
                password = password,
                email = email,
            )
            val registerSuccessful = authDataSource.registerUser(newUser)
            if(registerSuccessful){
                SimpleResponse(true, "Successfully created an account")
            }else{
                SimpleResponse(false, "An unknown error occurred")
            }
        }else{
            SimpleResponse(false, "A user with that email already exist")
        }
    }
    suspend fun tryRequestEmail(email: String): SimpleResponse {

        val userEntity = authDataSource.getUsernameFromEmail(email)

        userEntity?.let { user ->

            val resetRequest = Reset(
                userID = user.id,
                username =  user.username,
                code = (11111..99999).shuffled().last().toString(),
                request_time = System.currentTimeMillis()
            )
            val isInserted = authDataSource.insertResetRequest(resetRequest)

            // TODO Send email

            return if(isInserted){
                return SimpleResponse(true, resetRequest.code)
            }else{
                return SimpleResponse(false, "An unknown error occurred")
            }
        }

        return SimpleResponse(false, "Email does not exist")
    }

    suspend fun tryVerifyCode(code: String): SimpleResponse {

        val isCodeValid = authDataSource.verifyResetRequestCode(code)

        return if(isCodeValid){
            SimpleResponse(true, "Typed in the correct code")
        }else{
            SimpleResponse(false, "Typed in the wrong code")
        }
    }

    suspend fun tryRegisterProfile(
        username: String,
        profileName: String,
        email: String,
        phoneNumber: String,
        newPassword: String
    ): SimpleResponse {

        val user = authDataSource.getUser(username)
        user?.let { user ->
            user.profileName = profileName
            user.email = email
            user.phoneNumber = phoneNumber
            user.password = newPassword

            val isReplaced = authDataSource.replaceUser(user)
            return if(isReplaced){
                SimpleResponse(true, "Added the user data")
            }else{
                SimpleResponse(false, "Unknown error occurred when replacing the user")
            }
        }

        return SimpleResponse(false, "User does not exist")
    }
}