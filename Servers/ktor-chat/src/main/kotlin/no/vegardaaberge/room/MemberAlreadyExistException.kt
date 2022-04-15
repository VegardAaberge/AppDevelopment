package no.vegardaaberge.room

class MemberAlreadyExistException : Exception(
    "There is already a member with that username in that room"
)