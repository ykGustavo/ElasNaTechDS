package com.elasnatech.app.data

import com.elasnatech.app.QuizQuestion

/**
 * Representa um Quiz temático completo do aplicativo Elas na Tech.
 *
 * @property id Identificador numérico único do quiz (1 a 4).
 * @property title Título principal do quiz.
 * @property subtitle Subtítulo ou tema resumido.
 * @property description Descrição detalhada sobre o escopo histórico e técnico.
 * @property icon Emoji ou ícone representativo.
 * @property sourceName Nome da instituição confiável que valida os fatos e dados históricos.
 * @property sourceUrl Link oficial verificável da fonte de referência.
 * @property questions Lista de 8 perguntas estruturadas como [QuizQuestion].
 */
data class Quiz(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: String,
    val sourceName: String,
    val sourceUrl: String,
    val questions: List<QuizQuestion>
)

/**
 * Banco oficial de conteúdo com os 4 quizzes e 8 perguntas cada (total de 32 perguntas).
 * Todas as referências e biografias foram extraídas de fontes institucionais verificáveis
 * como Computer History Museum, NASA History, ACM, IEEE e Sociedade Brasileira de Computação (SBC).
 */
object QuizData {

    val allQuizzes: List<Quiz> = listOf(
        // =========================================================================
        // QUIZ 1: Pioneiras da Computação (ID: 1)
        // =========================================================================
        Quiz(
            id = 1,
            title = "Pioneiras da Computação",
            subtitle = "As mentes precursoras que conceberam a ciência da computação",
            description = "Conheça as histórias e realizações de Ada Lovelace, Grace Hopper, Margaret Hamilton, Irmã Mary Kenneth Keller, Hedy Lamarr, Radia Perlman, Jean Sammet e Carol Shaw.",
            icon = "💻",
            sourceName = "Computer History Museum & ACM History",
            sourceUrl = "https://www.computerhistory.org",
            questions = listOf(
                QuizQuestion(
                    question = "Quem é historicamente reconhecida como a primeira pessoa programadora do mundo por ter escrito o primeiro algoritmo computacional?",
                    options = listOf("Grace Hopper", "Ada Lovelace", "Katherine Johnson", "Marie Curie"),
                    correctIndex = 1,
                    explanation = "Em 1843, Ada Lovelace traduziu e anotou o artigo de Luigi Menabrea sobre o Engenho Analítico de Charles Babbage, inserindo a famosa 'Nota G' com o primeiro algoritmo concebido para ser executado por uma máquina."
                ),
                QuizQuestion(
                    question = "Qual importante cientista e oficial naval desenvolveu o compilador A-0 em 1952 e popularizou o termo 'bug'?",
                    options = listOf("Margaret Hamilton", "Grace Hopper", "Carol Shaw", "Jean Sammet"),
                    correctIndex = 1,
                    explanation = "Grace Hopper concebeu o compilador A-0 para traduzir comandos matemáticos em código de máquina e documentou o termo 'bug' ao retirar uma mariposa do computador Mark II em 1947."
                ),
                QuizQuestion(
                    question = "Quem liderou a divisão de software do MIT responsável pelo software de bordo da missão Apollo 11 que levou a humanidade à Lua?",
                    options = listOf("Margaret Hamilton", "Dorothy Vaughan", "Ada Lovelace", "Adele Goldberg"),
                    correctIndex = 0,
                    explanation = "Margaret Hamilton liderou a equipe do MIT Draper Lab que desenvolveu o software de voo da Apollo. Seu sistema de priorização assíncrona evitou o cancelamento do pouso lunar minutos antes de tocar o solo."
                ),
                QuizQuestion(
                    question = "Quem foi a primeira mulher nos Estados Unidos a obter um doutorado em Ciência da Computação (1965) e colaborou no desenvolvimento da linguagem BASIC?",
                    options = listOf("Irmã Mary Kenneth Keller", "Frances Allen", "Karen Spärck Jones", "Shafi Goldwasser"),
                    correctIndex = 0,
                    explanation = "A freira católica Mary Kenneth Keller obteve seu PhD pela Universidade de Wisconsin em 1965. Ela atuou na Universidade de Dartmouth e defendia que a computação deveria ser acessível para todas as pessoas."
                ),
                QuizQuestion(
                    question = "A atriz e inventora Hedy Lamarr patenteou em 1942 um sistema de comunicação secreta que serviu de base tecnológica para:",
                    options = listOf("Fitas magnéticas de áudio", "Wi-Fi, Bluetooth e telefonia celular (CDMA)", "Monitores de tubo de raios catódicos", "Discos rígidos modernos"),
                    correctIndex = 1,
                    explanation = "Hedy Lamarr patenteou o sistema de 'salto de frequência' (spread spectrum) para guiar torpedos sem interferência de rádio inimiga, tecnologia que hoje fundamenta conexões sem fio como Wi-Fi, Bluetooth e CDMA."
                ),
                QuizQuestion(
                    question = "Radia Perlman ficou mundialmente conhecida como a 'Mãe da Internet' devido a qual invenção fundamental para redes de computadores?",
                    options = listOf("Protocolo Spanning Tree (STP)", "Linguagem HTML", "Cabo coaxial", "Roteamento de e-mails SMTP"),
                    correctIndex = 0,
                    explanation = "Radia Perlman concebeu o algoritmo e protocolo Spanning Tree (STP) em 1985 na Digital Equipment Corporation (DEC), permitindo que redes Ethernet se conectassem em pontes sem criar loops infinitos de pacotes."
                ),
                QuizQuestion(
                    question = "Quem foi Jean Sammet, influente cientista e pioneira das linguagens de programação na IBM?",
                    options = listOf(
                        "Criadora da linguagem FORMAC e primeira mulher presidente da ACM",
                        "Desenvolvedora do primeiro antivírus comercial",
                        "Engenheira que projetou a primeira placa de vídeo 3D",
                        "Fundadora da primeira provedora de internet do mundo"
                    ),
                    correctIndex = 0,
                    explanation = "Jean Sammet desenvolveu na IBM o FORMAC (uma das primeiras linguagens para manipulação algébrica simbólica), liderou o comitê do COBOL e foi a primeira presidente mulher da Association for Computing Machinery (ACM) em 1974."
                ),
                QuizQuestion(
                    question = "Carol Shaw é celebrada na história dos jogos eletrônicos por qual realização pioneira nos anos 1970 e 1980?",
                    options = listOf(
                        "Primeira mulher designer e programadora de jogos, criadora de River Raid",
                        "Inventora do cartucho de videogame removível",
                        "Fundadora da Nintendo no Japão",
                        "Compositora da trilha sonora de Pac-Man"
                    ),
                    correctIndex = 0,
                    explanation = "Contratada pela Atari em 1978 e posteriormente pela Activision, Carol Shaw foi a primeira mulher a assinar a programação e o design completo de videogames de sucesso, com destaque para o clássico River Raid (1982)."
                )
            )
        ),

        // =========================================================================
        // QUIZ 2: Ciência Espacial e Hardware (ID: 2)
        // =========================================================================
        Quiz(
            id = 2,
            title = "Ciência Espacial & Hardware",
            subtitle = "As mentes brilhantes por trás de órbitas, circuitos e arquiteturas de chips",
            description = "Explore os feitos das cientistas que levaram a humanidade ao espaço e revolucionaram a microeletrônica moderna: Katherine Johnson, Dorothy Vaughan, Mary Jackson, Gladys West, Sophie Wilson, Lynn Conway, Evelyn Berezin e Erna Hoover.",
            icon = "🚀",
            sourceName = "NASA History Division & National Inventors Hall of Fame",
            sourceUrl = "https://www.nasa.gov/history",
            questions = listOf(
                QuizQuestion(
                    question = "Qual matemática da NASA realizou os cálculos manuais de trajetória orbital para a missão Mercury de John Glenn e para a Apollo 11?",
                    options = listOf("Katherine Johnson", "Mary Jackson", "Mae Jemison", "Dorothy Vaughan"),
                    correctIndex = 0,
                    explanation = "Katherine Johnson foi uma das principais mentes matemáticas do Langley Research Center da NASA. O astronauta John Glenn exigiu que ela checasse à mão os cálculos dos computadores IBM antes de voar para orbitar a Terra."
                ),
                QuizQuestion(
                    question = "Como Dorothy Vaughan transformou sua equipe no Langley Memorial Laboratory durante a transição da NASA para computadores eletrônicos?",
                    options = listOf(
                        "Aprendeu e capacitou sua equipe na linguagem FORTRAN para os mainframes IBM",
                        "Criou o primeiro sistema operacional gráfico",
                        "Construiu artesanalmente o primeiro foguete espacial tripulado",
                        "Recusou o uso de computadores e manteve apenas réguas de cálculo"
                    ),
                    correctIndex = 0,
                    explanation = "Dorothy Vaughan previu que os computadores eletrônicos substituiriam o cálculo manual, tornando-se especialista autodidata em FORTRAN e capacitando suas subordinadas, garantindo que mantivessem posições de destaque na agência."
                ),
                QuizQuestion(
                    question = "Mary Jackson alcançou qual marco histórico no Centro de Pesquisas de Langley da NASA em 1958?",
                    options = listOf(
                        "Primeira engenheira aeronáutica e aeroespacial negra da NASA",
                        "Primeira diretora geral da agência espacial",
                        "Primeira astronauta feminina a pousar na Lua",
                        "Inventora do traje pressurizado de caminhada espacial"
                    ),
                    correctIndex = 0,
                    explanation = "Após obter permissão judicial especial para assistir a aulas noturnas de engenharia em uma escola então segregada, Mary Jackson concluiu a formação e tornou-se a primeira mulher negra engenheira da NASA."
                ),
                QuizQuestion(
                    question = "Os cálculos astronômicos e geodésicos computadorizados realizados por Gladys West no US Naval Weapons Laboratory viabilizaram qual tecnologia global?",
                    options = listOf("GPS (Sistema de Posicionamento Global)", "Baterias de estado sólido", "Televisão via satélite analógica", "Sensores biométricos digitais"),
                    correctIndex = 0,
                    explanation = "Gladys West programou modelos matemáticos altamente complexos da forma geodésica irregular da Terra a partir de dados de satélite nos anos 70 e 80, trabalho de precisão que viabilizou o funcionamento do sistema GPS."
                ),
                QuizQuestion(
                    question = "Sophie Wilson é a lendária cientista da computação britânica que concebeu a arquitetura de instruções de qual família de processadores?",
                    options = listOf("ARM (Advanced RISC Machines)", "Intel x86", "MIPS R2000", "PowerPC"),
                    correctIndex = 0,
                    explanation = "Sophie Wilson co-criou na Acorn Computers o processador BBC Micro e desenhou em 1985 o conjunto de instruções do chip ARM. Hoje, a arquitetura com base em seu projeto está presente em mais de 95% dos smartphones do planeta."
                ),
                QuizQuestion(
                    question = "A pioneira Lynn Conway transformou a indústria global de semicondutores nos anos 1970 ao desenvolver:",
                    options = listOf(
                        "A metodologia Mead-Conway para projeto de circuitos integrados em escala muito grande (VLSI)",
                        "O primeiro leitor de código de barras a laser",
                        "A memória RAM dinâmica (DRAM)",
                        "O primeiro sensor óptico para câmeras de celular"
                    ),
                    correctIndex = 0,
                    explanation = "Lynn Conway, em parceria com Carver Mead, criou regras simplificadas de projeto de chips VLSI e escreveu o livro didático 'Introduction to VLSI Systems', democratizando a criação de microchips para milhares de engenheiros."
                ),
                QuizQuestion(
                    question = "Evelyn Berezin é creditada por revolucionar os escritórios modernos no início da década de 1970 com a criação de:",
                    options = listOf(
                        "O primeiro processador de texto computadorizado comercial (Data Secretary)",
                        "A primeira calculadora de bolso movida a energia solar",
                        "A primeira impressora a laser comercial",
                        "O primeiro mouse ergonômico sem fio"
                    ),
                    correctIndex = 0,
                    explanation = "Evelyn Berezin fundou a empresa Redactron Corporation em 1969 e lançou o 'Data Secretary' em 1971, uma máquina com circuitos integrados desenvolvida exclusivamente para edição, armazenamento e impressão de textos."
                ),
                QuizQuestion(
                    question = "Erna Schneider Hoover obteve em 1971 uma das primeiras patentes de software da história no Bell Labs graças à invenção de:",
                    options = listOf(
                        "Um sistema computadorizado de controle de comutação de tráfego telefônico para evitar quedas por sobrecarga",
                        "O primeiro firewall de roteador comercial",
                        "O identificador de chamadas de rádio (Bina)",
                        "Um algoritmo de compressão de voz MP3"
                    ),
                    correctIndex = 0,
                    explanation = "Trabalhando no Bell Labs, Erna Hoover utilizou princípios de controle eletrônico para que computadores monitorassem o fluxo de chamadas e evitassem travamentos de centrais telefônicas em horários de pico."
                )
            )
        ),

        // =========================================================================
        // QUIZ 3: Linguagens, Algoritmos e Redes (ID: 3)
        // =========================================================================
        Quiz(
            id = 3,
            title = "Linguagens, Algoritmos & Redes",
            subtitle = "Fundamentos da arquitetura de software, tolerância a falhas e internet",
            description = "Aprofunde-se no protocolo STP, na linguagem COBOL, no Smalltalk-80, no algoritmo TF-IDF e nas conquistas de Frances Allen, Shafi Goldwasser e Margaret Hamilton.",
            icon = "🌐",
            sourceName = "ACM A.M. Turing Award & IEEE Computer Society",
            sourceUrl = "https://amturing.acm.org",
            questions = listOf(
                QuizQuestion(
                    question = "Qual é o princípio operacional do protocolo Spanning Tree (STP), criado por Radia Perlman, em redes de computadores?",
                    options = listOf(
                        "Construir uma topologia lógica em árvore sem ciclos para prevenir tempestades de pacotes broadcast",
                        "Criptografar mensagens trocadas por navegadores web com chaves assimétricas",
                        "Distribuir endereços IP dinamicamente para dispositivos clientes conectados",
                        "Traduzir nomes de domínio de internet em endereços numéricos IP"
                    ),
                    correctIndex = 0,
                    explanation = "Em redes locais com múltiplos switches, conexões redundantes podem criar loops infinitos que travam o tráfego. O STP desativa logicamente caminhos redundantes até que um link falhe, garantindo estabilidade."
                ),
                QuizQuestion(
                    question = "Por qual razão histórica a linguagem COBOL, influenciada por Grace Hopper em 1959, representou um salto de produtividade na computação?",
                    options = listOf(
                        "Empregava comandos próximos ao vocabulário do inglês, facilitando a automação de processos comerciais",
                        "Executava de forma nativa em dispositivos móveis sem necessidade de interpretação",
                        "Foi a primeira linguagem desenvolvida exclusivamente para renderização gráfica em jogos",
                        "Eliminou o uso de memória RAM por gravar comandos diretamente em placas perfuradas"
                    ),
                    correctIndex = 0,
                    explanation = "Diferente das linguagens com foco estrito em cálculo científico, o COBOL introduziu comandos legíveis em inglês e registros estruturados, viabilizando o processamento de dados nas empresas e governos."
                ),
                QuizQuestion(
                    question = "Adele Goldberg, líder do laboratório de conceitos de sistemas na Xerox PARC, desempenhou papel chave no avanço de qual paradigma?",
                    options = listOf(
                        "Programação Orientada a Objetos com Smalltalk-80 e o conceito moderno de interfaces gráficas com janelas e ícones",
                        "Programação em microcódigo para sistemas embarcados",
                        "Criação das primeiras linguagens puramente funcionais sem estado",
                        "Desenvolvimento dos protocolos de fibra óptica submarina"
                    ),
                    correctIndex = 0,
                    explanation = "Adele Goldberg co-desenvolveu o Smalltalk-80, pioneiro em orientação a objetos pura, e demonstrou a interface com mouse, menus e janelas da Xerox a Steve Jobs, inspirando a revolução dos computadores pessoais."
                ),
                QuizQuestion(
                    question = "A pesquisadora Karen Spärck Jones formulou a métrica de Frequência Inversa de Documentos (IDF), que até hoje é fundamental para:",
                    options = listOf(
                        "Mecanismos de busca na web e recuperação de informação textual relevante",
                        "Compressão de vídeo digital em tempo real",
                        "Cálculo de rotas de satélites meteorológicos",
                        "Controle térmico de placas-mãe de servidores"
                    ),
                    correctIndex = 0,
                    explanation = "Em 1972, Spärck Jones propôs que palavras que aparecem em poucos documentos possuem maior peso semântico na busca. O método TF-IDF tornou-se a base dos sistemas de busca e indexação moderna da internet."
                ),
                QuizQuestion(
                    question = "Frances Allen foi laureada com o Prêmio Turing de 2006 (a primeira mulher a recebê-lo) devido a seus avanços seminais em:",
                    options = listOf(
                        "Teoria e prática de compiladores otimizadores e computação paralela de alta performance",
                        "Desenvolvimento de telas de cristal líquido (LCD)",
                        "Criação do protocolo de correio eletrônico POP3",
                        "Projeto arquitetural de discos rígidos SCSI"
                    ),
                    correctIndex = 0,
                    explanation = "Trabalhando na IBM por mais de quatro décadas, Fran Allen criou técnicas de grafos de fluxo de controle e otimização que transformaram a capacidade de traduzir linguagens de alto nível em código de máquina ultraveloz."
                ),
                QuizQuestion(
                    question = "As contribuições de Shafi Goldwasser para a ciência da computação revolucionaram qual área da tecnologia digital?",
                    options = listOf(
                        "Criptografia probabilística e Provas de Conhecimento Zero (Zero-Knowledge Proofs)",
                        "Renderização vetorial para impressoras laser",
                        "Projeto físico de memórias USB flash",
                        "Padronização de fontes TrueType em sistemas operacionais"
                    ),
                    correctIndex = 0,
                    explanation = "Goldwasser lançou as bases matemáticas da criptografia moderna com provas interativas, garantindo que alguém comprove uma afirmação sem revelar nenhuma informação secreta, hoje essencial em cibersegurança e blockchain."
                ),
                QuizQuestion(
                    question = "Como o software assíncrono desenvolvido pela equipe de Margaret Hamilton evitou um desastre no pouso lunar da Apollo 11?",
                    options = listOf(
                        "Interrompeu processos secundários causados por um radar mal posicionado e manteve o foco nos motores de alunissagem",
                        "Reiniciou o computador central da nave a cada trinta segundos",
                        "Assumiu o controle manual dos propulsores ignorando os astronautas",
                        "Ejetou os módulos de comunicação para poupar energia das baterias"
                    ),
                    correctIndex = 0,
                    explanation = "O radar de acoplamento gerou interrupções desnecessárias que saturaram o processador da Apollo 11. O sistema de prioridades de Hamilton abortou tarefas secundárias sem travar as tarefas críticas de voo."
                ),
                QuizQuestion(
                    question = "Qual era a tese central defendida pela Dra. Irmã Mary Kenneth Keller ao promover a linguagem BASIC nos anos 1960?",
                    options = listOf(
                        "Os computadores deveriam ser acessíveis a estudantes de todas as áreas, e não um privilégio de cientistas matemáticos",
                        "A programação deveria ser restrita a agências governamentais de segurança",
                        "Linguagens de computação só deveriam ser ensinadas na pós-graduação acadêmica",
                        "Os computadores eram ferramentas temporárias que logo seriam substituídas"
                    ),
                    correctIndex = 0,
                    explanation = "Mary Kenneth Keller foi uma das primeiras a enxergar a informática como elemento emancipador e educacional universal, estabelecendo centros de computação para incentivar estudantes de humanidades e exatas."
                )
            )
        ),

        // =========================================================================
        // QUIZ 4: Mulheres Tech no Brasil e Inclusão (ID: 4)
        // =========================================================================
        Quiz(
            id = 4,
            title = "Mulheres Tech no Brasil",
            subtitle = "Pioneirismo brasileiro, representatividade e o combate a vieses em IA",
            description = "Descubra as trajetórias inspiradoras de Enedina Alves Marques, Jaqueline Goes, Joana D'Arc Félix e conheça a importância de redes como Meninas Digitais da SBC, PyLadies e a diversidade na inteligência artificial.",
            icon = "🇧🇷",
            sourceName = "Sociedade Brasileira de Computação (SBC) & CONFEA",
            sourceUrl = "https://www.sbc.org.br",
            questions = listOf(
                QuizQuestion(
                    question = "Quem foi Enedina Alves Marques e qual a sua relevância para a história da engenharia e da ciência brasileira?",
                    options = listOf(
                        "A primeira mulher negra a se formar em engenharia no Brasil (1945, pela Universidade do Paraná)",
                        "A primeira desenvolvedora a criar um software de contabilidade no Brasil",
                        "A engenheira que fundou a primeira montadora automobilística brasileira",
                        "A arquiteta responsável pelo cálculo estrutural da ponte Rio-Niterói"
                    ),
                    correctIndex = 0,
                    explanation = "Enedina superou imensas barreiras de gênero e raça para se diplomar em Engenharia Civil. Atuou no Departamento de Águas e Energia Elétrica do Paraná, destacando-se na construção da Usina Hidrelétrica Capivari-Cachoeira."
                ),
                QuizQuestion(
                    question = "A biomédica e pesquisadora brasileira Dra. Jaqueline Goes de Jesus obteve reconhecimento global em 2020 ao liderar:",
                    options = listOf(
                        "O sequenciamento genômico completo do coronavírus (SARS-CoV-2) em apenas 48 horas após o primeiro caso no Brasil",
                        "A criação do primeiro microscópio de varredura atômica brasileiro",
                        "O desenvolvimento de um foguete espacial do Instituto Nacional de Pesquisas Espaciais",
                        "O projeto de cabos ópticos submarinos entre Santos e Fortaleza"
                    ),
                    correctIndex = 0,
                    explanation = "A pesquisa coordenada por Jaqueline Goes permitiu que a comunidade científica internacional identificasse a cepa viral e monitorasse mutações com rapidez impressionante, servindo de modelo mundial em bioinformática."
                ),
                QuizQuestion(
                    question = "A química e professora brasileira Dra. Joana D'Arc Félix de Sousa é amplamente premiada por suas pesquisas focadas em:",
                    options = listOf(
                        "Sustentabilidade, reaproveitamento de resíduos de couro e síntese de peles artificiais para transplante",
                        "Criação de novos combustíveis fósseis para aviação comercial",
                        "Desenvolvimento de microchips de silício policristalino",
                        "Produção de plástico tradicional não biodegradável"
                    ),
                    correctIndex = 0,
                    explanation = "Com mais de 70 prêmios e patentes nacionais e internacionais, Joana D'Arc dedica sua carreira à formação científica de jovens da periferia na ETEC de Franca, aplicando química limpa e economia circular."
                ),
                QuizQuestion(
                    question = "Qual é a missão fundamental do Programa 'Meninas Digitais', criado pela Sociedade Brasileira de Computação (SBC) em 2011?",
                    options = listOf(
                        "Promover a participação, atração e permanência de meninas nas áreas de Tecnologia da Informação e Computação",
                        "Oferecer empréstimos financeiros para startups lideradas por homens",
                        "Criar testes seletivos eliminatórios para concursos públicos de informática",
                        "Comercializar assinaturas pagas de plataformas de cursos online"
                    ),
                    correctIndex = 0,
                    explanation = "O Programa Meninas Digitais possui projetos parceiros em instituições de ensino de todo o Brasil, promovendo oficinas de programação, robótica, palestras e mentorias para superar o gap de gênero no setor."
                ),
                QuizQuestion(
                    question = "O grupo internacional PyLadies, com dezenas de capítulos ativos em diversas capitais e cidades brasileiras, atua como:",
                    options = listOf(
                        "Uma comunidade voluntária que acolhe, ensina e encoraja mulheres a participarem ativamente do ecossistema de Python",
                        "Uma agência governamental de fiscalização de patentes digitais",
                        "Uma empresa de recrutamento pago para multinacionais estrangeiras",
                        "Um sindicato trabalhista de desenvolvedores de banco de dados"
                    ),
                    correctIndex = 0,
                    explanation = "O PyLadies organiza workshops gratuitos, meetups, grupos de estudos e bolsas para conferências, criando um ambiente seguro para o aprendizado e crescimento técnico de mulheres em tecnologia."
                ),
                QuizQuestion(
                    question = "Por que a diversidade de equipes de desenvolvimento é considerada uma prioridade técnica no treinamento de modelos de IA?",
                    options = listOf(
                        "Para evitar a propagação de preconceitos históricos e vieses algorítmicos em reconhecimento facial, contratações e crédito",
                        "Para diminuir o uso de memória RAM nos servidores das empresas",
                        "Porque algoritmos de aprendizado de máquina exigem linhas de código em línguas diferentes",
                        "Para acelerar a renderização gráfica de computadores quânticos"
                    ),
                    correctIndex = 0,
                    explanation = "Sistemas de IA treinados sem diversidade frequentemente perpetuam e amplificam discriminações contra mulheres e minorias. Equipes plurais identificam lacunas nos dados e constroem soluções mais éticas e justas."
                ),
                QuizQuestion(
                    question = "Estudos da UNESCO sobre gênero na ciência demonstram que o desestímulo cultural a meninas em áreas exatas começa:",
                    options = listOf(
                        "Ainda na infância e nos primeiros anos escolares, devido a estereótipos sociais sobre aptidões inatas",
                        "Apenas na hora da aposentadoria em empresas privadas",
                        "Exclusivamente após a conclusão de cursos de pós-doutorado",
                        "Durante processos de exame de direção veicular"
                    ),
                    correctIndex = 0,
                    explanation = "A pesquisa 'Cracking the Code' da UNESCO aponta que preconceitos sobre meninas 'não levarem jeito para exatas' afastam talentos femininos precocemente, reforçando a importância de projetos de acolhimento escolar."
                ),
                QuizQuestion(
                    question = "Qual é o principal impacto comprovado de programas de mentoria feminina para alunas e iniciantes na área de tecnologia?",
                    options = listOf(
                        "Reduzir a evasão acadêmica e corporativa, fortalecendo a autoconfiança contra a síndrome da impostora",
                        "Eliminar completamente a necessidade de estudar sintaxe de programação",
                        "Dispensar as participantes da realização de estágios curriculares",
                        "Garantir a aprovação automática em qualquer processo de vestibular"
                    ),
                    correctIndex = 0,
                    explanation = "A mentoria oferece representatividade e suporte psicológico e técnico em momentos cruciais de decisão profissional, comprovadamente aumentando a retenção de mulheres nas carreiras de TI."
                )
            )
        )
    )

    /**
     * Retorna um [Quiz] específico pelo seu [id] (1 a 4).
     */
    fun getQuizById(id: Int): Quiz? = allQuizzes.find { it.id == id }

    /**
     * Retorna a lista de [QuizQuestion] para o [id] informado ou lista vazia se não encontrado.
     */
    fun getQuestionsByQuizId(id: Int): List<QuizQuestion> =
        getQuizById(id)?.questions ?: emptyList()
}
