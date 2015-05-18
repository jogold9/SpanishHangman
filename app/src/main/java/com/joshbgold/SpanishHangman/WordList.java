package com.joshbgold.SpanishHangman;

import java.util.Random;

public class WordList  {

    private int wordIndex = 0;
    private int commonWordIndex = 0;

    String SpanishVerbs[] = {"ser", "estar", "tener", "hacer", "poder", "decir", "ir", "ver", "dar", "saber", "querer", "llegar",
            "pasar", "deber", "poner", "parecer", "quedar", "creer", "hablar", "llevar", "dejar", "seguir", "encontrar", "llamar",
            "venir", "pensar", "salir", "volver", "tomar", "conocer", "vivir", "sentir", "tratar", "mirar", "contar", "empezar",
            "esperar", "buscar", "existir", "entrar", "trabajar", "escribir", "perder", "producir", "ocurrir", "entender",
            "pedir", "recibir", "recordar", "terminar", "permitir", "aparecer", "conseguir", "comenzar", "servir", "sacar",
            "necesitar", "mantener", "resultar", "leer", "caer","cambiar", "presentar", "crear", "abrir", "considerar", "oir",
            "acabar", "convertir", "ganar", "formar", "traer", "partir", "morir", "aceptar", "realizar", "suponer",
            "comprender", "lograr", "explicar", "preguntar", "tocar", "reconocer", "estudiar", "alcanzar", "nacer",
            "dirigir", "correr", "utilizar", "pagar", "ayudar", "gustar", "jugar", "escuchar", "cumplir", "ofrecer",
            "descubrir", "levantar", "intentar", "usar"};

    String EnglishDefs[] = {"to be","to be","to have","to do", "to be able", "to say", "to go", "to see", "to give",
            "to know (information)", "to want", "to arrive", "to spend, to happen", "to owe, must, should", "to put", "to seem",
            "to stay", "to believe", "to speak", "to carry", "to leave", "to continue, to follow", "to find", "to call", "to come",
            "to think", "to go out", "to return", "to take , to drink", "to know (people, places", "to live", "to feel", "to treat",
            "to watch, to look", "to count, to tell", "to begin", "to wait for, to hope", "to search", "to exist",
            "to enter", "to work", "to write", "to lose", "to produce", "to occur", "to understand", "to request", "to receive",
            "to remember", "to end", "to permit", "to appear", "to get", "to begin", "to serve", "to take", "to need",
            "to maintain", "to result", "to read", "to fall", "to change", "to introduce", "to create", "to open", "to consider",
            "to hear", "to finish, to have just", "to convert", "to win", "to form", "to bring","to divide, to leave", "to die", "to accept",
            "to realize", "to suppose", "to understand", "to achieve", "to explain", "to ask", "to touch, to play an instrument",
            "to recognize", "to study", "to reach", "to be born", "to direct", "to run", "to use", "to pay", "to help",
            "to play", "to listen", "to fulfill", "to offer", "to discover", "to raise, to get up", "to attempt", "to use"} ;

    String CommonWords[] = {"semana", "año", "hoy", "mañana", "ayer", "calendario", "vez", "tiempo", "dia", "cosa", "hombre",
            "parte", "vida", "momento", "forma", "casa", "mundo", "mujer", "caso", "país", "lugar", "hora", "persona", "trabajo",
            "punto", "mano", "manera", "fin", "tipo", "gente", "buenas días", "nos vemos", "buen provecho", "cuidate", "con permiso",
            "no se preocupe", "esta tranquilo", "otra vez", "como se dice", "tiene sentido", "me parece bien", "esta bien",
            "buenas tardes", "buenas noches", "creo que", "comportamiento", "saludos", "tengo ganas de", "arriba", "abajo",
            "adentro", "igualmente", "mucho gusto", "que le vaya bien", "te toca a ti", "buena suerte", "buen viaje",
            "quizás", "buena idea", "felicitaciones", "bienvenidos", "primavera", "verano", "otoño", "invierno", "lunes",
            "martes", "miércoles", "jueves", "viernes", "sábado", "domingo", "la víspera", "qué hora es", "fin de semana",
            "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre",
            "deciembre"};

    String CommonDefs[] =  {"week", "year", "today", "tomorrow", "yesterday", "calendar", "occasion, time", "time, weather", "day",
            "thing", "man", "part", "life", "moment", "form, shape", "house", "world", "woman, wife", "case, occasion",
            "country", "place", "hour", "person", "work, job", "point", "hand", "way", "end", "type, kind", "people", "good morning",
            "See you later", "enjoy your meal", "be careful, take care", "excuse me", "No worries", "No worries", "again",
            "how does one say", "it makes sense", "seems good to me", "it's good", "good evening", "good night",
            "I believe that", "behavior", "greetings", "I feel like...", "up", "below", "inside", "Same to you",
            "Nice to meet you", "have a nice day", "It's your turn", "good luck", "have a nice trip", "perhaps",
            "good idea", "congratulations", "welcome", "spring (season)", "summer", "fall (season)", "winter", "Monday",
            "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "the day before", "What time is it",
            "weekend", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"};

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
