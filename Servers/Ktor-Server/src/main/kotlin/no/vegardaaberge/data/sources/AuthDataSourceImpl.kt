package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.Reset
import no.vegardaaberge.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

class AuthDataSourceImpl(
    private val db: CoroutineDatabase
) : AuthDataSource {

    private val users = db.getCollection<User>()
    private val resetRequests = db.getCollection<Reset>()

    override suspend fun registerUser(user: User): Boolean {
        return users
            .insertOne(user)
            .wasAcknowledged()
    }

    override suspend fun replaceUser(user: User): Boolean {
        return users
            .replaceOne(user)
            .wasAcknowledged()
    }

    override suspend fun checkIfUserExist(username: String): Boolean {
        return users.findOne(User::username eq username) != null
    }

    override suspend fun getUserFromUsernameAndPassword(username: String, password: String): User? {
        return users.findOne(
            User::username eq username,
            User::password eq password
        )
    }

    override suspend fun getUsernameFromEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    override suspend fun insertResetRequest(resetRequest: Reset): Boolean {
        return resetRequests
            .insertOne(resetRequest)
            .wasAcknowledged()
    }

    override suspend fun verifyResetRequestCode(code: String): Boolean {
        val requestTime = resetRequests
            .findOne(Reset::code eq code)
            ?.request_time ?: -1

        val timeElapsed = System.currentTimeMillis() - requestTime
        return timeElapsed in 1..65000
    }

    override suspend fun getUser(username: String): User? {
        return users.findOne(User::username eq username)
    }
}