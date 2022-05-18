package no.vegardaaberge.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

class Reset (
    val userID : String,
    val username: String,
    val code: String,
    val request_time: Long,

    @BsonId
    val id: String = ObjectId().toString()
)