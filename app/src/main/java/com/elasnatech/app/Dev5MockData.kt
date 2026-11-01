package com.elasnatech.app

/**
 * ----------------------------------------------------------------------
 * TEMPORÁRIO DEV 5 - DADOS FICTÍCIOS (MOCK DATA)
 * ----------------------------------------------------------------------
 * Estes modelos e dados são provisórios para o desenvolvimento e
 * validação visual da interface do Dev 5 (Emblemas, Missões e Streak).
 *
 * Eles NÃO alteram nem duplicam Models.kt ou Room/DAOs.
 * Quando o BadgesViewModel e o ProfileViewModel estiverem totalmente
 * conectados na branch principal, a interface substituirá essas fontes.
 * ----------------------------------------------------------------------
 */

enum class Dev5MissionType(val label: String) {
    DAILY("Diária"),
    WEEKLY("Semanal")
}

data class Dev5BadgeMock(
    val id: String,
    val title: String,
    val description: String,
    val requirement: String,
    val icon: String,
    val isUnlocked: Boolean,
    val earnedDate: String? = null
)

data class Dev5MissionMock(
    val id: Int,
    val title: String,
    val description: String,
    val currentProgress: Int,
    val totalRequired: Int,
    val xpReward: Int,
    val isCompleted: Boolean,
    val type: Dev5MissionType
)

data class Dev5DayStreakMock(
    val dayName: String,
    val isCompleted: Boolean,
    val isToday: Boolean = false
)

data class Dev5StreakMock(
    val currentStreak: Int,
    val bestStreak: Int,
    val weeklyMissionsCompleted: Int,
    val weeklyGoal: Int,
    val daysOfWeek: List<Dev5DayStreakMock>
)

data class Dev5QuizTopicMock(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: String,
    val badgeIdReward: String,
    val questions: List<QuizQuestion>
)

object Dev5MockData {

    // --- 1. MOCK DE EMBLEMAS (Bloqueados e Desbloqueados) ---
    // Respeita os IDs já definidos nas regras de negócio da equipe:
    // "primeiro_quiz", "quiz_100_por_cento", "5_dias_seguidos", "nivel_5"
    val badges = listOf(
        Dev5BadgeMock(
            id = "primeiro_quiz",
            title = "Primeiro Passo",
            description = "Completou o seu primeiro quiz na plataforma.",
            requirement = "Finalize qualquer um dos quizzes disponíveis.",
            icon = "🌱",
            isUnlocked = true,
            earnedDate = "28/10/2026"
        ),
        Dev5BadgeMock(
            id = "quiz_100_por_cento",
            title = "Gabarito Perfeito",
            description = "Acertou 100% de todas as perguntas de um quiz.",
            requirement = "Obtenha nota máxima (8 de 8 acertos) em um quiz.",
            icon = "🎯",
            isUnlocked = true,
            earnedDate = "30/10/2026"
        ),
        Dev5BadgeMock(
            id = "5_dias_seguidos",
            title = "Fogo Constante",
            description = "Manteve o hábito de aprendizado por 5 dias consecutivos.",
            requirement = "Conquiste um streak ininterrupto de 5 dias.",
            icon = "🔥",
            isUnlocked = false,
            earnedDate = null
        ),
        Dev5BadgeMock(
            id = "nivel_5",
            title = "Mestre da Tecnologia",
            description = "Alcançou o nível 5 de experiência acumulada.",
            requirement = "Acumule pelo menos 400 XP completando desafios.",
            icon = "👑",
            isUnlocked = false,
            earnedDate = null
        ),
        Dev5BadgeMock(
            id = "pioneiras_tech",
            title = "Guardiã das Pioneiras",
            description = "Dominou a história de Ada Lovelace, Grace Hopper e pioneiras.",
            requirement = "Conclua o Quiz 1 com mais de 70% de acertos.",
            icon = "📜",
            isUnlocked = true,
            earnedDate = "31/10/2026"
        ),
        Dev5BadgeMock(
            id = "mulheres_espaco",
            title = "Conquistadora Espacial",
            description = "Aprendeu sobre as matemáticas e engenheiras da NASA.",
            requirement = "Conclua o Quiz 2 com mais de 70% de acertos.",
            icon = "🚀",
            isUnlocked = false,
            earnedDate = null
        ),
        Dev5BadgeMock(
            id = "redes_algoritmos",
            title = "Arquiteta de Redes",
            description = "Conheceu as inventoras de protocolos, circuitos e algoritmos.",
            requirement = "Conclua o Quiz 3 com mais de 70% de acertos.",
            icon = "🌐",
            isUnlocked = false,
            earnedDate = null
        ),
        Dev5BadgeMock(
            id = "tech_brasil",
            title = "Orgulho Brasileiro",
            description = "Explorou o impacto das cientistas e desenvolvedoras no Brasil.",
            requirement = "Conclua o Quiz 4 com mais de 70% de acertos.",
            icon = "🇧🇷",
            isUnlocked = false,
            earnedDate = null
        )
    )

    // --- 2. MOCK DE MISSÕES (Diárias e Semanais) ---
    val missions = listOf(
        Dev5MissionMock(
            id = 1,
            title = "Desafio Diário",
            description = "Responda a pelo menos 1 quiz completo hoje.",
            currentProgress = 1,
            totalRequired = 1,
            xpReward = 50,
            isCompleted = true,
            type = Dev5MissionType.DAILY
        ),
        Dev5MissionMock(
            id = 2,
            title = "Mira Certeira",
            description = "Acerte pelo menos 6 perguntas em uma única tentativa.",
            currentProgress = 4,
            totalRequired = 6,
            xpReward = 40,
            isCompleted = false,
            type = Dev5MissionType.DAILY
        ),
        Dev5MissionMock(
            id = 3,
            title = "Maratona do Conhecimento",
            description = "Complete 3 quizzes diferentes durante a semana.",
            currentProgress = 2,
            totalRequired = 3,
            xpReward = 120,
            isCompleted = false,
            type = Dev5MissionType.WEEKLY
        ),
        Dev5MissionMock(
            id = 4,
            title = "Semana em Chamas",
            description = "Mantenha seu streak ativo por 4 dias esta semana.",
            currentProgress = 4,
            totalRequired = 4,
            xpReward = 100,
            isCompleted = true,
            type = Dev5MissionType.WEEKLY
        ),
        Dev5MissionMock(
            id = 5,
            title = "Colecionadora",
            description = "Desbloqueie pelo menos 1 novo emblema nesta semana.",
            currentProgress = 1,
            totalRequired = 1,
            xpReward = 150,
            isCompleted = true,
            type = Dev5MissionType.WEEKLY
        )
    )

    // --- 3. MOCK DE STREAK E PROGRESSO ---
    val streak = Dev5StreakMock(
        currentStreak = 4,
        bestStreak = 7,
        weeklyMissionsCompleted = 3,
        weeklyGoal = 5,
        daysOfWeek = listOf(
            Dev5DayStreakMock("Seg", isCompleted = true),
            Dev5DayStreakMock("Ter", isCompleted = true),
            Dev5DayStreakMock("Qua", isCompleted = true),
            Dev5DayStreakMock("Qui", isCompleted = true, isToday = true),
            Dev5DayStreakMock("Sex", isCompleted = false),
            Dev5DayStreakMock("Sáb", isCompleted = false),
            Dev5DayStreakMock("Dom", isCompleted = false)
        )
    )

    // --- 4. 4 QUIZZES COMPLETOS COM 8 PERGUNTAS CADA (Total = 32 perguntas) ---
    val quizzes = listOf(
        // Quiz 1: Pioneiras da Computação
        Dev5QuizTopicMock(
            id = 1,
            title = "Pioneiras da Computação",
            subtitle = "As mulheres que criaram a base da ciência da computação",
            description = "Teste seus conhecimentos sobre Ada Lovelace, Grace Hopper, Margaret Hamilton e outras precursoras fundamentais.",
            icon = "💻",
            badgeIdReward = "pioneiras_tech",
            questions = listOf(
                QuizQuestion(
                    "Quem é historicamente reconhecida como a primeira pessoa programadora do mundo?",
                    listOf("Grace Hopper", "Ada Lovelace", "Katherine Johnson", "Marie Curie"),
                    1,
                    "Em 1843, Ada Lovelace escreveu o primeiro algoritmo destinado a ser processado pelo Engenho Analítico de Charles Babbage."
                ),
                QuizQuestion(
                    "Qual importante cientista desenvolveu o primeiro compilador e popularizou a palavra 'bug'?",
                    listOf("Margaret Hamilton", "Grace Hopper", "Radia Perlman", "Carol Shaw"),
                    1,
                    "Grace Hopper desenvolveu o compilador A-0 em 1952 e ajudou a conceber linguagens de programação de alto nível."
                ),
                QuizQuestion(
                    "Quem liderou a equipe de desenvolvimento do software de bordo da missão Apollo 11 à Lua?",
                    listOf("Margaret Hamilton", "Dorothy Vaughan", "Ada Lovelace", "Adele Goldberg"),
                    0,
                    "Margaret Hamilton foi diretora da divisão de engenharia de software do MIT que desenvolveu o software de bordo da Apollo."
                ),
                QuizQuestion(
                    "Quem foi a primeira mulher nos Estados Unidos a obter um doutorado em Ciência da Computação?",
                    listOf("Irmã Mary Kenneth Keller", "Frances Allen", "Jean Sammet", "Karen Spärck Jones"),
                    0,
                    "A Irmã Mary Kenneth Keller concluiu seu doutorado em 1965 e participou da criação da linguagem BASIC."
                ),
                QuizQuestion(
                    "A inventora Hedy Lamarr patenteou na 2ª Guerra Mundial uma tecnologia precursora de:",
                    listOf("Cabos de fibra óptica", "Bancos de dados relacionais", "Wi-Fi, Bluetooth e CDMA", "Disquetes magnéticos"),
                    2,
                    "Hedy Lamarr patenteou um sistema secreto de salto de frequência que é a base das tecnologias wireless modernas."
                ),
                QuizQuestion(
                    "Qual cientista da computação é conhecida como 'Mãe da Internet' por inventar o protocolo STP?",
                    listOf("Radia Perlman", "Sophie Wilson", "Lynn Conway", "Susan Kare"),
                    0,
                    "Radia Perlman criou o protocolo Spanning Tree (STP), essencial para a operação e prevenção de loops em redes Ethernet."
                ),
                QuizQuestion(
                    "Quem foi Jean Sammet, figura seminal das linguagens de programação?",
                    listOf(
                        "Criadora do processador ARM",
                        "Desenvolvedora da linguagem FORMAC e primeira mulher presidente da ACM",
                        "Astronauta e piloto de testes",
                        "Fundadora da Wikipedia"
                    ),
                    1,
                    "Jean Sammet criou a linguagem FORMAC para cálculo simbólico e liderou a Association for Computing Machinery (ACM)."
                ),
                QuizQuestion(
                    "Quem é considerada uma das primeiras designers e desenvolvedoras de jogos de videogame da história?",
                    listOf("Carol Shaw", "Roberta Williams", "Dona Bailey", "Kim Swift"),
                    0,
                    "Carol Shaw começou a programar jogos no fim dos anos 70 na Atari e criou o clássico River Raid para o Atari 2600."
                )
            )
        ),

        // Quiz 2: Mulheres na Ciência Espacial e Hardware
        Dev5QuizTopicMock(
            id = 2,
            title = "Ciência Espacial & Hardware",
            subtitle = "Mentes brilhantes por trás de órbitas, circuitos e arquiteturas",
            description = "Explore os feitos das cientistas que levaram a humanidade ao espaço e revolucionaram a microeletrônica moderna.",
            icon = "🚀",
            badgeIdReward = "mulheres_espaco",
            questions = listOf(
                QuizQuestion(
                    "Qual matemática da NASA calculou à mão as trajetórias críticas dos voos de John Glenn e Apollo 11?",
                    listOf("Katherine Johnson", "Dorothy Vaughan", "Mary Jackson", "Mae Jemison"),
                    0,
                    "Katherine Johnson fez os cálculos essenciais de mecânica orbital e janelas de lançamento que garantiram o sucesso das missões."
                ),
                QuizQuestion(
                    "Dorothy Vaughan destacou-se na NASA como pioneira na introdução de qual tecnologia de computação?",
                    listOf("Linguagem FORTRAN nos computadores eletrônicos IBM", "Circuitos CMOS", "Redes sem fio", "Computação Quântica"),
                    0,
                    "Dorothy Vaughan aprendeu e ensinou FORTRAN para sua equipe quando os computadores eletrônicos chegaram à NASA."
                ),
                QuizQuestion(
                    "Quem foi Mary Jackson na história aeroespacial norte-americana?",
                    listOf(
                        "A primeira engenheira aeronáutica negra da NASA",
                        "A primeira mulher a pilotar o ônibus espacial",
                        "A inventora do telescópio Hubble",
                        "A física teórica pioneira em buracos negros"
                    ),
                    0,
                    "Mary Jackson superou a segregação para se tornar a primeira engenheira aeroespacial negra da NASA em 1958."
                ),
                QuizQuestion(
                    "Gladys West realizou a modelagem matemática geodésica da Terra que permitiu o surgimento de qual tecnologia?",
                    listOf("GPS (Sistema de Posicionamento Global)", "Ressonância magnética", "Bateria de íon de lítio", "Câmeras digitais"),
                    0,
                    "Os modelos de satélite e da forma da Terra desenvolvidos por Gladys West viabilizaram a precisão do GPS."
                ),
                QuizQuestion(
                    "Qual inventora patenteou o primeiro sistema de comutação telefônica computadorizada na Bell Labs?",
                    listOf("Erna Schneider Hoover", "Evelyn Berezin", "Lynn Conway", "Chieko Asakawa"),
                    0,
                    "Erna Schneider Hoover inventou a comutação de telefonia com controle por computador, uma das primeiras patentes de software da história."
                ),
                QuizQuestion(
                    "Quem foi Evelyn Berezin, pioneira dos computadores de escritório?",
                    listOf(
                        "Criadora do primeiro processador de texto computadorizado (Redactron)",
                        "Inventora do mouse de esfera",
                        "Fundadora da Intel",
                        "Desenvolvedora do teclado QWERTY"
                    ),
                    0,
                    "Evelyn Berezin fundou a Redactron e lançou em 1971 o primeiro processador de texto eletrônico para escritórios."
                ),
                QuizQuestion(
                    "Quem co-projetou o processador BBC Micro e a arquitetura pioneira ARM, usada na maioria dos celulares?",
                    listOf("Sophie Wilson", "Grace Hopper", "Frances Allen", "Megan Smith"),
                    0,
                    "Sophie Wilson é a lendária cientista que desenhou o conjunto de instruções do processador ARM na Acorn Computers."
                ),
                QuizQuestion(
                    "A cientista Lynn Conway revolucionou a indústria de chips através de:",
                    listOf(
                        "Metodologia Mead-Conway para projeto de circuitos integrados VLSI",
                        "Descoberta do transistor de grafeno",
                        "Criação das primeiras memórias flash",
                        "Invenção do leitor de código de barras"
                    ),
                    0,
                    "Lynn Conway estruturou o padrão global de design de microchips em escala muito grande (VLSI), democratizando a criação de chips."
                )
            )
        ),

        // Quiz 3: Linguagens, Algoritmos e Internet
        Dev5QuizTopicMock(
            id = 3,
            title = "Linguagens, Algoritmos & Web",
            subtitle = "As pioneiras da arquitetura de software e inteligência computacional",
            description = "Aprofunde-se nos fundamentos de algoritmos, compiladores, interfaces gráficas e teoria da informação.",
            icon = "🌐",
            badgeIdReward = "redes_algoritmos",
            questions = listOf(
                QuizQuestion(
                    "Qual foi o principal objetivo do protocolo Spanning Tree (STP) criado por Radia Perlman?",
                    listOf(
                        "Evitar loops de repetição de pacotes em redes com pontes Ethernet",
                        "Comprimir arquivos multimídia",
                        "Criptografar senhas de banco de dados",
                        "Aumentar a velocidade do processador"
                    ),
                    0,
                    "O algoritmo de árvore geradora de Radia Perlman impede que mensagens fiquem presas em loops infinitos de rede."
                ),
                QuizQuestion(
                    "Adele Goldberg liderou a equipe da Xerox PARC responsável por popularizar:",
                    listOf(
                        "A linguagem Smalltalk-80 e a interface gráfica de usuário (GUI) com janelas e ícones",
                        "A primeira impressora matricial",
                        "O correio eletrônico comercial",
                        "O protocolo TCP/IP"
                    ),
                    0,
                    "Adele Goldberg e sua equipe criaram conceitos de janelas e programação orientada a objetos que inspiraram os computadores modernos."
                ),
                QuizQuestion(
                    "Quem foi Karen Spärck Jones e qual a sua imensa relevância para a tecnologia?",
                    listOf(
                        "Criou o conceito de frequência inversa de documentos (TF-IDF), essencial para motores de busca como o Google",
                        "Inventou o primeiro cabo submarino de fibra óptica",
                        "Desenvolveu o teclado Dvorak",
                        "Criou o primeiro antivírus do mundo"
                    ),
                    0,
                    "Karen Spärck Jones foi pioneira em processamento de linguagem natural e recuperação de informação computacional."
                ),
                QuizQuestion(
                    "Frances Allen foi a primeira mulher a receber o Prêmio Turing (2006). Qual foi sua principal contribuição?",
                    listOf(
                        "Otimização de compiladores e computação paralela",
                        "Desenvolvimento de inteligência artificial generativa",
                        "Criação do cabo HDMI",
                        "Design de jogos de tabuleiro"
                    ),
                    0,
                    "Frances Allen realizou pesquisas fundamentais na IBM sobre análise de fluxo de controle e otimização de compiladores."
                ),
                QuizQuestion(
                    "O que a linguagem COBOL, co-criada por Grace Hopper, trouxe de tão revolucionário?",
                    listOf(
                        "Uso de termos em inglês legíveis para humanos, aproximando a computação do mundo dos negócios",
                        "Capacidade de gerar gráficos em 3D",
                        "Execução exclusiva em computadores quânticos",
                        "Eliminação de qualquer uso de variáveis"
                    ),
                    0,
                    "O COBOL permitiu que programas fossem escritos em uma sintaxe próxima ao inglês comercial, em vez de código de máquina."
                ),
                QuizQuestion(
                    "Shafi Goldwasser é duas vezes vencedora do Prêmio Gödel e vencedora do Prêmio Turing por pesquisas em:",
                    listOf(
                        "Criptografia e Provas de Conhecimento Zero (Zero-Knowledge Proofs)",
                        "Sistemas operacionais móveis",
                        "Design de fontes tipográficas",
                        "Armazenamento óptico em discos laser"
                    ),
                    0,
                    "Shafi Goldwasser lançou os fundamentos matemáticos da criptografia moderna e de protocolos de segurança interativos."
                ),
                QuizQuestion(
                    "O que o sistema de prioridade de tarefas da equipe de Margaret Hamilton fez no pouso lunar da Apollo 11?",
                    listOf(
                        "Descartou tarefas secundárias de um radar acidentalmente acionado para priorizar os motores de pouso",
                        "Desligou completamente o computador de bordo",
                        "Substituiu os comandos dos astronautas por inteligência artificial autônoma",
                        "Acionou os paraquedas antes da hora"
                    ),
                    0,
                    "O software detectou a sobrecarga de interrupções e garantiu que apenas os cálculos vitais de descida fossem executados."
                ),
                QuizQuestion(
                    "Irmã Mary Kenneth Keller acreditava que a programação deveria ser ensinada para:",
                    listOf(
                        "Todas as pessoas e áreas do conhecimento, tornando a tecnologia acessível",
                        "Apenas matemáticos militares de alta patente",
                        "Exclusivamente engenheiros com pós-graduação",
                        "Profissionais da indústria bélica"
                    ),
                    0,
                    "Ela foi uma defensora incansável da democratização do acesso aos computadores e da educação aberta em programação."
                )
            )
        ),

        // Quiz 4: Mulheres na Tecnologia no Brasil e Inclusão
        Dev5QuizTopicMock(
            id = 4,
            title = "Mulheres Tech no Brasil",
            subtitle = "História, representatividade e o futuro da tecnologia no país",
            description = "Descubra as conquistas das brasileiras nas exatas e a importância de iniciativas inclusivas no ecossistema tech.",
            icon = "🇧🇷",
            badgeIdReward = "tech_brasil",
            questions = listOf(
                QuizQuestion(
                    "Quem foi Enedina Alves Marques, figura histórica fundamental na engenharia brasileira?",
                    listOf(
                        "A primeira mulher negra a se formar em engenharia no Brasil (1945, pela UFPR)",
                        "A primeira programadora de computadores Mainframe no Rio de Janeiro",
                        "A fundadora da primeira escola de robótica em São Paulo",
                        "A arquiteta que desenhou o plano piloto de Brasília"
                    ),
                    0,
                    "Enedina Alves Marques formou-se em Engenharia Civil no Paraná e liderou obras históricas de usinas hidrelétricas."
                ),
                QuizQuestion(
                    "Qual é o objetivo principal do programa 'Meninas Digitais', idealizado pela Sociedade Brasileira de Computação (SBC)?",
                    listOf(
                        "Divulgar e incentivar a área de computação entre alunas do ensino básico e superior",
                        "Vender cursos preparatórios pagos para concursos",
                        "Restringir a entrada de estudantes no mercado corporativo",
                        "Organizar feiras exclusivas para compra de eletrônicos"
                    ),
                    0,
                    "O Meninas Digitais realiza oficinas, palestras e mentorias em todo o Brasil para aproximar garotas da computação."
                ),
                QuizQuestion(
                    "A cientista brasileira Jaqueline Goes de Jesus ganhou destaque internacional por liderar:",
                    listOf(
                        "O sequenciamento do genoma do vírus SARS-CoV-2 em apenas 48 horas no Brasil",
                        "A criação da primeira criptomoeda nacional",
                        "O desenvolvimento de um foguete para a Base de Alcântara",
                        "A descoberta de uma nova galáxia pelo Observatório Nacional"
                    ),
                    0,
                    "Jaqueline Goes e sua equipe realizaram o sequenciamento genético recorde, referência mundial em vigilância genômica."
                ),
                QuizQuestion(
                    "O que comunidades como PyLadies Brasil, Women Who Code e Elas na Tech proporcionam?",
                    listOf(
                        "Espaços seguros de aprendizado, mentoria, acolhimento e networking gratuito",
                        "Contratações obrigatórias sem processo seletivo",
                        "Venda exclusiva de licenças de software corporativo",
                        "Plataformas pagas de streaming de jogos"
                    ),
                    0,
                    "Essas comunidades fomentam a capacitação técnica mútua, apoio de carreira e empoderamento feminino no setor de TI."
                ),
                QuizQuestion(
                    "Por que a diversidade de gênero e raça é vital no desenvolvimento de Inteligência Artificial?",
                    listOf(
                        "Para mitigar vieses algorítmicos e evitar discriminações automatizadas em softwares",
                        "Para tornar o processamento computacional mais rápido em megabytes",
                        "Porque linguagens de IA só funcionam com equipes balanceadas",
                        "Para reduzir o consumo de energia dos servidores em nuvem"
                    ),
                    0,
                    "Equipes diversas constroem sistemas mais justos, reduzindo preconceitos ocultos nos dados de treinamento e modelos."
                ),
                QuizQuestion(
                    "Quem é Joana D'Arc Félix de Sousa, cientista e professora premiada brasileira?",
                    listOf(
                        "Química e pesquisadora pioneira em sustentabilidade e reaproveitamento de resíduos industriais",
                        "Desenvolvedora do primeiro navegador de internet brasileiro",
                        "A primeira piloto de testes da Embraer",
                        "A criadora do sistema PIX no Banco Central"
                    ),
                    0,
                    "Dra. Joana D'Arc possui dezenas de patentes e prêmios dedicados à pesquisa científica e formação de jovens na química."
                ),
                QuizQuestion(
                    "Qual a importância de projetos de iniciação científica para meninas na educação básica?",
                    listOf(
                        "Desmistificar que exatas não são para mulheres e despertar vocações científicas desde cedo",
                        "Obrigar todos os estudantes a cursar engenharia",
                        "Substituir as aulas de literatura por programação",
                        "Garantir patrocínio financeiro imediato para as escolas"
                    ),
                    0,
                    "Incentivos precoces rompem estereótipos culturais e mostram que mulheres pertencem e lideram nas ciências e exatas."
                ),
                QuizQuestion(
                    "Qual o papel essencial das alianças e mentorias para mulheres em início de carreira na área de tecnologia?",
                    listOf(
                        "Ajudar a superar a síndrome da impostora, planejar carreira e conquistar os primeiros desafios técnicos",
                        "Realizar as tarefas de trabalho no lugar da iniciante",
                        "Substituir as entrevistas formais de emprego",
                        "Dispensar a necessidade de estudar lógica e código"
                    ),
                    0,
                    "A mentoria acelera o crescimento profissional, compartilha experiências reais e fortalece a permanência de mulheres na área."
                )
            )
        )
    )
}
