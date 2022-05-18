package no.vegardaaberge.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    val username: String,
    var password: String,
    var email: String = "",
    var profileName: String = "",
    var phoneNumber: String = "",
    @BsonId
    val id: String = ObjectId().toString(),
)