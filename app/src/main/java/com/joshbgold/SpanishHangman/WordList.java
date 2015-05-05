package com.joshbgold.SpanishHangman;

import java.util.Random;

public class WordList  {

    private int wordIndex = 0;
    private int commonWordIndex = 0;

    String SpanishVerbs[] = {"ser", "estar", "tener", "hacer", "poder", "decir", "ir", "ver", "dar", "saber", "querer", "llegar",
            "pasar", "deber", "poner", "parecer", "quedar", "creer", "hablar", "llevar", "dejar", "seguir", "encontrar", "llamar",
            "venir", "pensar", "salir", "volver", "tomar", "conocer", "vivir", "sentir", "tratar", "mirar", "contar", "empezar",
            "esperar", "buscar", "existir", "entrar", "trabajar", "escribir", "perder", "producir", "ocurrir", "entender",
            "pedir", "recibir", "recordar", "terminar"};

    String EnglishDefs[] = {"to be","to be","to have","to do", "to be able", "to say", "to go", "to see", "to give",
            "to know (information)", "to want", "to arrive", "to spend, to happen", "to owe, must, should", "to put", "to seem",
            "to stay", "to believe", "to speak", "to carry", "to leave", "to continue, to follow", "to find", "to call", "to come",
            "to think", "to go out", "to return", "to take , to drink", "to know (people, places", "to live", "to feel", "to treat",
            "to watch, to look", "to count, to tell", "to begin", "to wait for, to hope", "to search", "to exist",
            "to enter", "to work", "to write", "to lose", "to produce", "to occur", "to understand", "to request", "to receive",
            "to remember", "to end" } ;

    String CommonWords[] = {"semana", "año", "hoy", "mañana", "ayer", "calendario", "vez", "tiempo", "dia", "cosa", "hombre",
            "parte", "vida", "momento", "forma", "casa", "mundo", "mujer", "caso", "país", "lugar", "hora", "persona", "trabajo",
            "punto", "mano", "manera", "fin", "tipo", "gente", "buenas días", "nos vemos", "buen provecho", "cuidate", "con permiso",
            "No se preocupe", "esta tranquilo", "otra vez", "como se dice", "tiene sentido", "me parece bien", "esta bien",
            "buenas tardes", "buenas noches", "creo que"};

    String CommonDefs[] =  {"week", "year", "today", "tomorrow", "yesterday", "calendar", "time", "time, weather", "day",
            "thing", "man", "part", "life", "moment", "form, shape", "house", "world", "woman, wife", "case, occasion",
            "country", "place", "hour", "person", "work, job", "point", "hand", "way", "end", "type, kind", "people", "good morning",
            "See you later", "enjoy your meal", "be careful, take care", "excuse me", "No worries", "No worries", "again",
            "how does one say", "it makes sense", "seems good to me", "it's good", "good evening", "good night",
            "I believe that"};

    public int getWordIndex() {
        wordIndex = new Random().nextInt(SpanishVerbs.length);
        return wordIndex;
    }

    public int getCommonWordIndex(){
        commonWordIndex = new Random().nextInt(CommonWords.length);
        return commonWordIndex;
    }

    //return a random word from the list
    public String getVerb(int number) {
        return SpanishVerbs[number];
    }

    public String getEnglishDef(int number) {
        return EnglishDefs[number];
    }

    public String getCommonWords(int number) {
        return CommonWords[number];
    }

    public String getCommonDefs(int number) {
        return CommonDefs[number];
    }
}
