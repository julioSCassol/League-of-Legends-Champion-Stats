﻿# League of Legends Champions App

## Projeto de Programação para Dispositivos Móveis

Este projeto é uma aplicação mobile desenvolvida em **Kotlin** com **Jetpack Compose**, que exibe informações sobre os campeões do jogo **League of Legends**. O objetivo principal do projeto é proporcionar uma experiência rica e fluida ao usuário, permitindo que ele explore campeões, veja detalhes e compare estatísticas.

### Funcionalidades

- Tela inicial com navegação para a lista de campeões.
- Listagem dos campeões com:
    - Filtro por categorias (ex.: Fighter, Tank, Mage, etc.).
    - Busca por nome de campeão.
- Detalhes do campeão com:
    - Imagem do campeão.
    - Descrição e classe do campeão.
    - Exibição de estatísticas (vida, mana, dano, velocidade de ataque, etc.).
- Navegação com botão de voltar, tanto na interface quanto pelo botão físico do dispositivo.
- Interceptação do botão "voltar" para garantir que o aplicativo não seja fechado acidentalmente.

### Tecnologias Utilizadas

- **Kotlin**: Linguagem principal para desenvolvimento Android.
- **Jetpack Compose**: Framework moderno da Google para construção de interfaces declarativas.
- **Coroutines**: Para gerenciamento de chamadas assíncronas.
- **StateFlow**: Para gerenciamento de estados reativos.
- **HTTPURLConnection**: Para fazer requisições HTTP e buscar os dados dos campeões.

### Estrutura do Projeto

1. **MainActivity**: Ponto de entrada do aplicativo. Controla a exibição da SplashScreen e navega entre as telas.
2. **AppNavigation**: Gerencia a navegação entre as telas principais (Home, Listagem, Detalhes).
3. **ChampionListScreen**: Tela que exibe a lista de campeões, com busca e filtros por tags.
4. **ChampionDetailScreen**: Tela de detalhes de cada campeão, mostrando suas estatísticas e uma imagem.
5. **ChampionViewModel**: ViewModel que gerencia a lógica de negócios e as chamadas à API para obter os campeões.
6. **ChampionService**: Serviço responsável por realizar as chamadas HTTP para buscar os campeões da API.
7. **Champion**: Modelo de dados que representa um campeão do jogo.

### Como Executar

1. Clone o repositório para sua máquina.
2. Abra o projeto no Android Studio.
3. Execute o aplicativo em um emulador ou dispositivo físico.
4. Aguarde o carregamento dos campeões e explore a aplicação.
5. Divirta-se!

### Autores

- [Júlio Cassol](https://github.com/julioSCassol)
- [Eduardo Ibarr](https://github.com/eduardo-ibarr)
