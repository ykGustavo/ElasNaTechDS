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

    // Notícias reais, conferidas em 01/11/2026. A data é a da publicação (vagas) ou do evento;
    // nos cursos gratuitos, que não têm data de publicação, é a data em que as informações foram conferidas.
    val news = listOf(
        NewsItem(
            1, "iFood abre 50 vagas remotas para mulheres na tecnologia", "Vagas", "16/04/2026",
            "A 3ª edição do programa Elas São Tech ofereceu 50 vagas 100% remotas para mulheres profissionais de tecnologia nos níveis pleno, sênior e especialista, com inscrições de 15/04 a 02/05 e admissões previstas para junho de 2026. Inscrições encerradas. Fonte: iFood."
        ),
        NewsItem(
            2, "B3 abre estágio em tecnologia exclusivo para mulheres", "Vagas", "29/07/2026",
            "A 7ª edição do Manas da Tech reservou ao menos 50% das vagas a estudantes pretas ou pardas. Eram elegíveis estudantes de Tecnologia, Matemática, Estatística, Física ou Engenharia com conclusão prevista a partir de setembro de 2027, em modelo híbrido (São Paulo e Santana de Parnaíba). Inscrições até 07/08/2026. Fonte: divulgação da B3."
        ),
        NewsItem(
            3, "Festival Mulheres de Produto 2026", "Eventos", "07/11/2026",
            "Encontro presencial em São Paulo (FAPCOM) para mulheres que atuam com produto e tecnologia. O site oficial divulga 4 trilhas, mais de 10 palestrantes, mais de 50 mentorias e mais de 400 participantes. Fonte: site do Festival Mulheres de Produto."
        ),
        NewsItem(
            4, "Hackathon ElasSãoTech, da Assespro-PR", "Eventos", "06/11/2026",
            "Hackathon presencial com início às 18h, na véspera do encontro ElaSão Tech – Feito por ELAS para TODOS (07/11, às 9h, no Centro Regional de Eventos de Pato Branco, PR, durante o Inventum 2026). Fonte: Assespro-PR."
        ),
        NewsItem(
            5, "Curso gratuito de Lógica de Programação da Trybe", "Cursos Gratuitos", "01/11/2026",
            "Curso online sem pré-requisitos, com materiais em português e mais de 30 exercícios com resolução. O certificado é gratuito para quem concluir todo o conteúdo e acertar ao menos 80% das questões. Fonte: Trybe (informações conferidas em 01/11/2026)."
        ),
        NewsItem(
            6, "Python Básico, curso gratuito da Fundação Bradesco", "Cursos Gratuitos", "01/11/2026",
            "Curso de nível iniciante na Escola Virtual, com 18 horas e 60 dias para concluir. Aborda lógica de programação, variáveis e tipos de dados, estruturas lógicas e funções, com programas em modo console. Fonte: Escola Virtual da Fundação Bradesco (informações conferidas em 01/11/2026)."
        )
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

    // Quiz com 16 perguntas ricas de 4 alternativas sobre mulheres na tecnologia
    val quiz = com.elasnatech.app.ui.quiz.QuizQuestions.allQuestions
}
