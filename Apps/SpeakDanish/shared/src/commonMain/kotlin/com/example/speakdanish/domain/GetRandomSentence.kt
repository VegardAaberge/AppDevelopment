package com.example.speakdanish.domain

import com.example.speakdanish.data.local.Sentences

class GetRandomSentence {

    fun execute(previous: String?): String {
        val newSentence = Sentences.data.random()
        return if (newSentence != previous) {
            newSentence
        } else {
            execute(previous)
        }
    }
}