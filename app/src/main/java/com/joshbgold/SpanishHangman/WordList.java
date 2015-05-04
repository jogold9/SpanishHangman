package com.joshbgold.SpanishHangman;

import java.util.Random;

/**
 * Created by JoshG on 5/2/2015.
 */
//public class WordList  implements Parcelable{

public class WordList  {

    private int wordIndex = 0;
    private int commonWordIndex = 0;

    String SpanishVerbs[] = {"ser", "estar", "tener", "hacer", "poder", "decir", "ir", "ver", "dar", "saber", "querer", "llegar", "pasar", "deber", "poner", "parecer", "quedar", "creer", "hablar", "llevar", "dejar", "seguir", "encontrar", "llamar", "venir", "pensar", "salir", "volver", "tomar", "conocer", "vivir", "sentir", "tratar", "mirar", "contar"};

    String EnglishDefs[] = {"to be","to be","to have","to do", "to be able", "to say", "to go", "to see", "to give", "to know (information)", "to want", "to arrive", "to spend, to happen", "to owe, must, should", "to put", "to seem", "to stay", "to believe", "to speak", "to carry", "to leave", "to continue, to follow", "to find", "to call", "to come", "to think", "to go out", "to return", "to take , to drink", "to know (people, places", "to live", "to feel", "to treat, to handle", "to watch, to look", "to count, to tell" } ;

    String CommonWords[] = {"semana", "año", "hoy", "mañana", "ayer", "calendario"};
    String CommonDefs[] =  {"week", "year", "today", "tomorrow", "yesterday", "calendar"};

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
        String verb = SpanishVerbs[number];
        return verb;
    }

    public String getEnglishDef(int number) {
        String englishWord = EnglishDefs[number];
        return englishWord;
    }

    public String getCommonWords(int number) {
        String commonWord = CommonWords[number];
        return commonWord;
    }

    public String getCommonDefs(int number) {
        String commonDef = CommonDefs[number];
        return commonDef;
    }
}
