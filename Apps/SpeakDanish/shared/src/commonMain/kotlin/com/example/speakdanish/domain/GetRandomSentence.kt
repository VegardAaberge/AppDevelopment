package com.example.speakdanish.domain

class GetRandomSentence {

    companion object {
        val sentences = listOf(
            "Goddag/Hej",
            "Undskyld",
            "Beklager",
            "Selv tak",
            "Taler du engelsk?",
            "Jeg taler ikke dansk.",
            "Hvor meget koster det?",
            "Jeg kigger bare.",
            "Blyfri benzin",
            "Diesel",
            "Hvor er Hospitalet",
            "Jeg er syg.",
            "Jeg er allergisk over for...",
            "Det gør ondt her.",
            "Hvad hedder du?",
            "Mit navn er Vegard",
            "Hvor kommer du fra?",
            "Jeg er fra Norge",
            "Indgang",
            "Udgang",
            "Åben",
            "Lukket",
            "Forbudt",
            "Politi",
            "Hospitalet/Sygehus",
            "Posthus",
            "Centrum",
            "Hvad er klokken?",
            "Glædelig Jul"
        )
    }


    fun execute(previous: String?): String {
        val newSentence = sentences.random()
        return if (newSentence != previous) {
            newSentence
        } else {
            execute(previous)
        }
    }
}