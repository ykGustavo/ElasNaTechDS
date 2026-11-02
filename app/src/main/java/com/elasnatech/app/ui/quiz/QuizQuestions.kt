package com.elasnatech.app.ui.quiz

import com.elasnatech.app.QuizQuestion

/**
 * Banco completo de perguntas do Quiz sobre Mulheres na Tecnologia,
 * Programação, Ciência da Computação e História das Cientistas.
 *
 * Cada pergunta possui rigorosamente 4 alternativas, 1 única resposta correta
 * e uma explicação histórica e técnica contextualizada.
 */
object QuizQuestions {

    val allQuestions: List<QuizQuestion> = listOf(
        // Pergunta 1: Ada Lovelace
        QuizQuestion(
            question = "Quem é historicamente reconhecida como a primeira pessoa programadora do mundo por ter escrito o primeiro algoritmo computacional?",
            options = listOf(
                "Grace Hopper",
                "Ada Lovelace",
                "Katherine Johnson",
                "Marie Curie"
            ),
            correctIndex = 1,
            explanation = "Em 1843, Ada Lovelace traduziu e anotou os estudos sobre a Máquina Analítica de Charles Babbage, criando o primeiro algoritmo concebido para ser processado por uma máquina."
        ),

        // Pergunta 2: Grace Hopper
        QuizQuestion(
            question = "Qual importante cientista da computação e almirante da Marinha dos EUA desenvolveu o compilador A-0 e popularizou o termo 'bug'?",
            options = listOf(
                "Margaret Hamilton",
                "Grace Hopper",
                "Carol Shaw",
                "Jean Sammet"
            ),
            correctIndex = 1,
            explanation = "Grace Hopper concebeu o primeiro compilador em 1952 e documentou o termo 'bug' após encontrar uma mariposa real que causava falha no computador Harvard Mark II."
        ),

        // Pergunta 3: Margaret Hamilton
        QuizQuestion(
            question = "Quem liderou a divisão de engenharia de software do MIT responsável pelo software de bordo da missão Apollo 11 que levou a humanidade à Lua?",
            options = listOf(
                "Margaret Hamilton",
                "Dorothy Vaughan",
                "Adele Goldberg",
                "Mary Jackson"
            ),
            correctIndex = 0,
            explanation = "Margaret Hamilton liderou a equipe que criou o software de voo da Apollo 11. Seu código com priorização assíncrona evitou que a alunissagem fosse abortada em 1969."
        ),

        // Pergunta 4: Irmã Mary Kenneth Keller
        QuizQuestion(
            question = "Quem foi a primeira mulher nos Estados Unidos a obter um doutorado em Ciência da Computação (1965) e participou da criação da linguagem BASIC?",
            options = listOf(
                "Frances Allen",
                "Shafi Goldwasser",
                "Irmã Mary Kenneth Keller",
                "Karen Spärck Jones"
            ),
            correctIndex = 2,
            explanation = "A Irmã Mary Kenneth Keller concluiu seu PhD na Universidade de Wisconsin em 1965 e defendia vigorosamente a democratização do ensino de computação."
        ),

        // Pergunta 5: Hedy Lamarr
        QuizQuestion(
            question = "A atriz e inventora Hedy Lamarr patenteou em 1942 um sistema de salto de frequência que se tornou a base tecnológica para:",
            options = listOf(
                "Cabos de fibra óptica submarinos",
                "Wi-Fi, Bluetooth e telefonia celular (CDMA)",
                "Disquetes magnéticos de computador",
                "Bancos de dados relacionais"
            ),
            correctIndex = 1,
            explanation = "Hedy Lamarr patenteou a técnica de espalhamento espectral por salto de frequência para teleguiar torpedos sem interferência de rádio inimiga, fundamento das redes sem fio modernas."
        ),

        // Pergunta 6: Radia Perlman
        QuizQuestion(
            question = "Radia Perlman é amplamente chamada de 'Mãe da Internet' graças a qual contribuição fundamental para redes de computadores?",
            options = listOf(
                "Criação do protocolo Spanning Tree (STP)",
                "Invenção do protocolo de e-mail SMTP",
                "Desenvolvimento da linguagem de marcação HTML",
                "Padronização dos cabos de rede par trançado"
            ),
            correctIndex = 0,
            explanation = "Radia Perlman inventou o protocolo STP (Spanning Tree Protocol), que impede a formação de loops de broadcast infinitos em redes Ethernet interconectadas."
        ),

        // Pergunta 7: Katherine Johnson
        QuizQuestion(
            question = "Qual matemática lendária da NASA calculou à mão as trajetórias orbitais da missão Mercury de John Glenn e da Apollo 11?",
            options = listOf(
                "Katherine Johnson",
                "Mae Jemison",
                "Gladys West",
                "Sophie Wilson"
            ),
            correctIndex = 0,
            explanation = "Katherine Johnson realizou os cálculos manuais de física orbital de extrema precisão para a NASA. O próprio astronauta John Glenn só aceitou voar após ela checar os números dos computadores."
        ),

        // Pergunta 8: Frances Allen
        QuizQuestion(
            question = "Frances Allen foi a primeira mulher a receber o prestigiado Prêmio Turing (em 2006) devido a seus avanços pioneiros em:",
            options = listOf(
                "Telas de toque capacitivas",
                "Compiladores otimizadores e computação paralela de alta performance",
                "Cabos de dados USB",
                "Algoritmos de inteligência artificial generativa"
            ),
            correctIndex = 1,
            explanation = "Trabalhando por mais de quatro décadas na IBM, Fran Allen desenvolveu as bases teóricas e práticas de análise de fluxo e otimização que tornam o código compilado extremamente eficiente."
        ),

        // Pergunta 9: Sophie Wilson
        QuizQuestion(
            question = "A cientista britânica Sophie Wilson é célebre por ter projetado o conjunto de instruções de qual arquitetura de processadores?",
            options = listOf(
                "Intel x86",
                "MIPS R3000",
                "ARM (Advanced RISC Machines)",
                "Motorola 68000"
            ),
            correctIndex = 2,
            explanation = "Sophie Wilson desenhou a arquitetura de instruções do processador ARM em 1985 na Acorn Computers. Hoje a arquitetura ARM está presente em bilhões de smartphones e dispositivos embarcados."
        ),

        // Pergunta 10: Gladys West
        QuizQuestion(
            question = "As análises matemáticas computacionais da forma irregular da Terra feitas por Gladys West viabilizaram qual tecnologia global?",
            options = listOf(
                "GPS (Sistema de Posicionamento Global)",
                "Baterias recarregáveis de íon de lítio",
                "Transmissão de TV digital via satélite",
                "Reconhecimento biométrico facial"
            ),
            correctIndex = 0,
            explanation = "Gladys West programou cálculos geodésicos refinados e simulações com satélites no Naval Surface Warfare Center, tornando viável a precisão do sistema de posicionamento GPS."
        ),

        // Pergunta 11: Adele Goldberg
        QuizQuestion(
            question = "Adele Goldberg, líder na Xerox PARC, desempenhou papel essencial no desenvolvimento de qual marco da computação pessoal?",
            options = listOf(
                "Linguagem Smalltalk-80 e interfaces gráficas orientadas a janelas e objetos",
                "O primeiro processador de 64 bits",
                "A tecnologia de memória flash USB",
                "A primeira rede social baseada em texto"
            ),
            correctIndex = 0,
            explanation = "Adele Goldberg co-criou a linguagem Smalltalk-80 na Xerox PARC e pioneirou conceitos de interfaces gráficas com janelas, menus e mouse que influenciaram decisivamente o Macintosh e o Windows."
        ),

        // Pergunta 12: Carol Shaw
        QuizQuestion(
            question = "Carol Shaw é reconhecida na história da indústria de jogos eletrônicos por qual conquista pioneira?",
            options = listOf(
                "Primeira mulher designer e programadora de games, criadora do jogo River Raid",
                "Criadora do controle analógico para consoles",
                "Compositora dos efeitos sonoros de Space Invaders",
                "Fundadora do primeiro estúdio de e-Sports"
            ),
            correctIndex = 0,
            explanation = "Carol Shaw programou jogos para o Atari 2600 e para a Activision, sendo a criadora e designer integral do clássico atemporal de tiro aéreo 'River Raid' em 1982."
        ),

        // Pergunta 13: Enedina Alves Marques
        QuizQuestion(
            question = "Quem foi Enedina Alves Marques e qual seu marco histórico na ciência e tecnologia brasileira?",
            options = listOf(
                "Primeira mulher negra a se formar em engenharia no Brasil (1945)",
                "Fundadora da primeira faculdade pública de tecnologia no Brasil",
                "Primeira ministra de Ciência e Tecnologia do Brasil",
                "Inventora da urna eletrônica brasileira"
            ),
            correctIndex = 0,
            explanation = "Enedina Alves Marques formou-se em Engenharia Civil na Universidade do Paraná em 1945 e liderou obras históricas de infraestrutura, incluindo a Usina Hidrelétrica Capivari-Cachoeira."
        ),

        // Pergunta 14: Karen Spärck Jones
        QuizQuestion(
            question = "A cientista da computação Karen Spärck Jones criou a fórmula TF-IDF, que é até hoje um dos pilares de:",
            options = listOf(
                "Mecanismos de busca e indexação de textos na internet",
                "Compilação de código assembly para binário",
                "Resfriamento líquido de servidores de dados",
                "Compressão de arquivos em formato ZIP"
            ),
            correctIndex = 0,
            explanation = "Em 1972, Karen Spärck Jones formulou o conceito de frequência inversa de documentos (IDF), base matemática utilizada pelos principais buscadores da web para ordenar resultados relevantes."
        ),

        // Pergunta 15: Dorothy Vaughan
        QuizQuestion(
            question = "Como a matemática Dorothy Vaughan transformou sua equipe na NASA diante da chegada dos computadores eletrônicos?",
            options = listOf(
                "Aprendeu de forma autodidata a linguagem FORTRAN e capacitou suas colaboradoras",
                "Inventou o primeiro monitor com resolução colorida",
                "Construiu artesanalmente os circuitos do primeiro computador da NASA",
                "Criou a primeira linguagem de banco de dados SQL"
            ),
            correctIndex = 0,
            explanation = "Antecipando que os computadores eletrônicos substituiriam o cálculo manual, Dorothy Vaughan dominou FORTRAN nos mainframes IBM e ensinou suas colegas, assegurando posições de liderança na agência."
        ),

        // Pergunta 16: Jaqueline Goes de Jesus
        QuizQuestion(
            question = "A biomédica brasileira Dra. Jaqueline Goes de Jesus ganhou destaque internacional em 2020 ao liderar:",
            options = listOf(
                "O sequenciamento genômico do novo coronavírus em apenas 48 horas",
                "O desenvolvimento da primeira impressora biológica de órgãos",
                "A descoberta da vacina contra a febre amarela",
                "O primeiro transplante robótico da América Latina"
            ),
            correctIndex = 0,
            explanation = "A Dra. Jaqueline Goes coordenou o sequenciamento genômico do vírus SARS-CoV-2 no Instituto Adolfo Lutz em tempo recorde de 48 horas, fundamental para o rastreio epidemiológico mundial."
        )
    )

    /**
     * Retorna uma lista de perguntas embaralhadas a cada nova partida.
     * Por padrão, seleciona [count] perguntas (padrão 10) aleatórias do banco.
     */
    fun getShuffledQuiz(count: Int = 10): List<QuizQuestion> {
        val selectedCount = count.coerceIn(1, allQuestions.size)
        return allQuestions.shuffled().take(selectedCount)
    }
}
