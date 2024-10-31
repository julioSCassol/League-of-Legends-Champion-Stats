# League of Legends Champions App

## Projeto de Programação para Dispositivos Móveis

Bem-vindo ao **League of Legends Champions App**, uma aplicação mobile desenvolvida em **Kotlin** com **Jetpack Compose** que permite explorar os campeões do jogo **League of Legends**. Este projeto proporciona uma experiência interativa, com páginas de detalhes, equipe aleatória e comparações de campeões.

---

### Funcionalidades Principais

- **Tela inicial** com navegação simplificada.
- **Listagem de campeões**:
  - Filtro por categorias (ex.: Fighter, Tank, Mage, etc.).
  - Busca por nome do campeão.
- **Detalhes do campeão**:
  - Visualização da imagem e classe do campeão.
  - Estatísticas completas (vida, mana, dano, etc.).
- **Equipe aleatória**: geração de times aleatórios e compartilhamento via WhatsApp.
- **Navegação fluida**: botão "voltar" integrado para evitar fechamento acidental do aplicativo.

---

### Interface do Aplicativo

#### 1. Tela Inicial & Listagem dos Campeões
<p align="center">
  <img src="https://github.com/user-attachments/assets/ac378929-2839-4789-a332-7c1d4e5798dd" alt="Tela Inicial" width="200"/>
  <img src="https://github.com/user-attachments/assets/f5c8e691-0700-485f-b179-6aa7e0b0d84c" alt="Listagem dos Campeões" width="200"/>
</p>

#### 2. Página do Campeão, Página das Equipes Aleatórias & Dialog dos Itens de cada Campeão Aleatório
<p align="center">
  <img src="https://github.com/user-attachments/assets/b29d2fa7-e9ae-43a9-8149-c7f3082da1c7" alt="Página do Campeão" width="200"/>
  <img src="https://github.com/user-attachments/assets/1f411709-db1c-4c58-9883-e6a265f7ab54" alt="Equipes Aleatórias" width="200"/>
  <img src="https://github.com/user-attachments/assets/187d886f-4e67-45d1-b11b-a25d2574514c" alt="Itens Aleatórios" width="200"/>
</p>

---

### Tecnologias Utilizadas

- **Kotlin**: Linguagem principal para desenvolvimento Android.
- **Jetpack Compose**: Framework moderno da Google para construção de interfaces declarativas.
- **Coroutines**: Para gerenciamento de chamadas assíncronas.
- **StateFlow**: Para gerenciamento de estados reativos.
- **HTTPURLConnection**: Para requisições HTTP e carregamento de dados dos campeões.

---

### Estrutura do Projeto

#### Arquivos e Pastas Principais

1. **MainActivity**: Controla a tela de splash e navegação principal.
2. **AppNavigation**: Configura e controla a navegação entre as telas (início, listagem, detalhes).
3. **ChampionListScreen**: Lista de campeões com opções de filtro e busca.
4. **ChampionDetailScreen**: Exibe detalhes completos de um campeão.
5. **ChampionViewModel**: Gerencia dados e chamadas API para busca de campeões.
6. **ItemsViewModel**: Gerencia os dados dos itens aleatórios de cada campeão.
7. **ChampionService**: Serviço HTTP para busca de dados dos campeões.
8. **ItemsService**: Serviço HTTP para busca dos dados dos itens dos campeões.
9. **ChampionRepository**: Gerencia a comunicação entre os ViewModels e os serviços.
10. **ChampionDatabaseHelper**: Classe auxiliar para gerenciar o banco de dados dos campeões.
11. **Champion.kt**: Modelo de dados que representa um campeão.
12. **Item.kt**: Modelo de dados que representa um item de campeão.
13. **ChampionSoundPlayer**: Utilitário para tocar sons dos campeões.
14. **formatDescription.kt**: Função utilitária para formatar descrições.
15. **LoadImage.kt**: Utilitário para carregamento de imagens.
16. **SplashScreen**: Tela de apresentação exibida ao abrir o app.
17. **HomeScreen**: Tela inicial do app com acesso às demais funcionalidades.
18. **TeamsRandomizerScreen**: Tela que gera equipes aleatórias de campeões.
19. **Navigation.kt**: Controla a navegação entre as telas.

---

### Executando o Projeto

1. Clone o repositório na sua máquina.
2. Abra o projeto no Android Studio.
3. Execute o app em um emulador ou dispositivo físico.
4. Explore e divirta-se!

---

### Autores

- [Júlio Cassol](https://github.com/julioSCassol)
- [Eduardo Ibarr](https://github.com/eduardo-ibarr)
