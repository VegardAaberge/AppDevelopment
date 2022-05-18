package no.vegardaaberge.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Message (
    val message: String,
    val owner: String,
    val receivers: List<String>,
    val created: Long,
    @BsonId
    val id: String = ObjectId().toString()
)