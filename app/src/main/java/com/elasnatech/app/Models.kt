package com.elasnatech.app

// ---------- MODELOS ----------

data class Scientist(
    val id: Int,
    val name: String,
    val shortBio: String,
    val achievements: List<String>,
    val quote: String
)

data class TimelineEvent(val year: String, val text: String)

data class VideoItem(
    val id: Int,
    val title: String,
    val channel: String,
    val url: String // TODO: colocar o link real do vídeo
)

data class NewsItem(
    val id: Int,
    val title: String,
    val category: String,
    val date: String,
    val description: String
)

data class SupportItem(
    val name: String,
    val description: String,
    val url: String = "" // TODO: colocar o link oficial (vazio = botão não abre nada)
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

// Selo conquistado conforme a pontuação
fun badgeFor(score: Int, total: Int): String {
    val ratio = score.toFloat() / total
    return when {
        ratio >= 0.8f -> "🏆 Especialista"
        ratio >= 0.4f -> "🌟 Exploradora"
        else -> "🌱 Iniciante"
    }
}

// ---------- DADOS DE EXEMPLO (troquem por dados reais depois) ----------

object MockData {

    val scientists = listOf(
        Scientist(
            1, "Ada Lovelace",
            "Matemática inglesa que escreveu, em 1843, o primeiro algoritmo pensado para uma máquina.",
            listOf("Primeiro algoritmo para máquina", "Previu que computadores iriam além de cálculos"),
            "Esse meu cérebro é algo mais do que meramente mortal. (confirmar fonte)"
        ),
        Scientist(
            2, "Grace Hopper",
            "Cientista da computação e oficial da Marinha dos EUA, criou um dos primeiros compiladores.",
            listOf("Um dos primeiros compiladores (1952)", "Contribuiu para a linguagem COBOL"),
            "A frase mais perigosa é: 'sempre fizemos assim'. (confirmar fonte)"
        ),
        Scientist(
            3, "Hedy Lamarr",
            "Atriz e inventora que patenteou, em 1942, um sistema de salto de frequência.",
            listOf("Patente de salto de frequência (1942)"),
            "TODO: adicionar uma frase verificada dela."
        ),
        Scientist(
            4, "Katherine Johnson",
            "Matemática da NASA cujos cálculos de trajetória foram essenciais para missões espaciais.",
            listOf("Calculou trajetórias de missões da NASA"),
            "Goste do que faz e você dará o seu melhor. (confirmar fonte)"
        ),
        Scientist(
            5, "Margaret Hamilton",
            "Cientista da computação que liderou a equipe do software de bordo da missão Apollo.",
            listOf("Liderou o software de voo da Apollo", "Popularizou o termo 'engenharia de software'"),
            "TODO: adicionar uma frase verificada dela."
        )
    )

    val timeline = listOf(
        TimelineEvent("1843", "Ada Lovelace publica o primeiro algoritmo pensado para uma máquina."),
        TimelineEvent("1942", "Hedy Lamarr patenteia o salto de frequência."),
        TimelineEvent("1952", "Grace Hopper desenvolve um dos primeiros compiladores."),
        TimelineEvent("1969", "O software da Apollo, liderado por Margaret Hamilton, ajuda a levar humanos à Lua."),
        TimelineEvent("Hoje", "Mulheres seguem ampliando seu espaço e sua voz na tecnologia.")
    )

    val videos = listOf(
        VideoItem(1, "Conheça 10 mulheres que Fizeram História na Tecnologia", "IlustraDev", ""),
        VideoItem(2, "Dialogando apresenta: A mulher no mundo da tecnologia", "Vivo", ""),
        VideoItem(3, "Women@Cloud | Mulheres na tecnologia", "Google Cloud LATAM", "")
    )

    val newsCategories = listOf("Vagas", "Eventos", "Cursos Gratuitos")

    val news = listOf(
        NewsItem(1, "Exemplo: vaga júnior para desenvolvedora", "Vagas", "10/10/2026", "Texto de exemplo para a vaga."),
        NewsItem(2, "Exemplo: programa de estágio em tecnologia", "Vagas", "09/10/2026", "Texto de exemplo para o estágio."),
        NewsItem(3, "Exemplo: encontro de mulheres na tech", "Eventos", "15/10/2026", "Texto de exemplo para o evento."),
        NewsItem(4, "Exemplo: hackathon feminino", "Eventos", "22/10/2026", "Texto de exemplo para o hackathon."),
        NewsItem(5, "Exemplo: curso de lógica de programação", "Cursos Gratuitos", "01/11/2026", "Texto de exemplo para o curso."),
        NewsItem(6, "Exemplo: trilha de Python para iniciantes", "Cursos Gratuitos", "05/11/2026", "Texto de exemplo para a trilha.")
    )

    val communities = listOf(
        SupportItem("Women Who Code", "Comunidade de mulheres na tecnologia."),
        SupportItem("PyLadies", "Comunidade de mulheres que usam Python."),
        SupportItem("Meninas Digitais", "Programa que incentiva meninas na computação."),
        SupportItem("Mulheres na Tech", "Comunidade brasileira de apoio e networking.")
    )

    val mentorships = listOf(
        SupportItem("Mentorias", "TODO: adicionar programas de mentoria."),
        SupportItem("Bolsas de estudo", "TODO: adicionar bolsas e cursos gratuitos.")
    )

    val quiz = listOf(
        QuizQuestion(
            "Quem é considerada a primeira programadora da história?",
            listOf("Grace Hopper", "Ada Lovelace", "Hedy Lamarr"), 1,
            "Ada Lovelace escreveu o primeiro algoritmo para uma máquina, em 1843."
        ),
        QuizQuestion(
            "Quem desenvolveu um dos primeiros compiladores?",
            listOf("Grace Hopper", "Katherine Johnson", "Margaret Hamilton"), 0,
            "Grace Hopper criou um dos primeiros compiladores, em 1952."
        ),
        QuizQuestion(
            "Hedy Lamarr patenteou qual tecnologia?",
            listOf("Compilador", "Salto de frequência", "Banco de dados"), 1,
            "A patente de 1942 usava salto de frequência."
        ),
        QuizQuestion(
            "Quem liderou o software de bordo da Apollo?",
            listOf("Margaret Hamilton", "Ada Lovelace", "Grace Hopper"), 0,
            "Margaret Hamilton liderou a equipe do software da Apollo."
        ),
        QuizQuestion(
            "Katherine Johnson trabalhou em qual agência?",
            listOf("NASA", "Google", "IBM"), 0,
            "Ela calculou trajetórias de missões para a NASA."
        )
    )
}
