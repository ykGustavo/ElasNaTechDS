# Elas na Tech: base em Jetpack Compose

## Como usar
1. No Android Studio: **New Project → Empty Activity** (Compose), com nome de pacote `com.elasnatech.app`.
2. Apague o `src/main/java/com/elasnatech/app/MainActivity.kt` e a pasta `ui/theme` que o template criou.
3. Copie todos os `.kt` desta pasta para `src/main/java/com/elasnatech/app`.
4. Se o seu pacote for outro, troque a linha `package` no topo de cada arquivo.

## Dependências extras (módulo `build.gradle.kts`)
O template já traz Compose e Material 3. Confira se tem:
```kotlin
implementation("androidx.navigation:navigation-compose:2.8.5") // ou a versão mais recente
implementation("androidx.activity:activity-compose:1.9.3")     // precisa de 1.8+ por causa do enableEdgeToEdge()
```
Os ícones usados (Home, List, Favorite, Star, PlayArrow, FavoriteBorder) fazem parte do pacote básico, sem precisar do `material-icons-extended`.

## Estrutura
| Arquivo | O que tem |
|---|---|
| `src/main/java/com/elasnatech/app/MainActivity.kt` | Ponto de entrada |
| `src/main/java/com/elasnatech/app/NavGraph.kt` | Barra de navegação inferior e rotas das 4 abas |
| `src/main/java/com/elasnatech/app/Theme.kt` | Cores, modo claro/escuro, fundo em degradê, `AppCard` e `SectionTitle` |
| `src/main/java/com/elasnatech/app/Models.kt` | `data class`es e dados de exemplo (`MockData`) |
| `src/main/java/com/elasnatech/app/HomeScreen.kt` | Boas-vindas, carrossel, linha do tempo, vídeos com favoritar, CTA |
| `src/main/java/com/elasnatech/app/NewsScreen.kt` | Filtros (`FilterChip`) e lista de notícias |
| `src/main/java/com/elasnatech/app/SupportScreen.kt` | Card Ligue 180, comunidades, mentorias e bolsas |
| `src/main/java/com/elasnatech/app/QuizScreen.kt` | Streak, progresso semanal, quiz, feedback, nota e selo |

## Para a equipe expandir (procurem por `TODO`)
- Trocar iniciais por fotos (`Avatar` em `src/main/java/com/elasnatech/app/HomeScreen.kt`).
- Colocar links reais dos vídeos e comunidades em `src/main/java/com/elasnatech/app/Models.kt`.
- Confirmar as frases das cientistas (as marcadas com "confirmar fonte").
- Substituir as notícias de exemplo por dados reais (API ou JSON).
- Mover estados (favoritos, quiz) para `ViewModel` e salvar com DataStore ou Room.
- Mover textos para `strings.xml`.
