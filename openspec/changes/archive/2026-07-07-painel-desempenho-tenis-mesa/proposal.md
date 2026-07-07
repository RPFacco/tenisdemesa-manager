## Why

Minha irmã precisa de um site pessoal para registrar seu desempenho no tênis de mesa: campeonatos, partidas com placar e vídeo, medalhas, estatísticas agregadas e horas de treino. Hoje não há nenhum sistema que unifique esses dados — as informações ficam dispersas. Um painel web single-user, sem login, resolve isso de forma prática e imediata.

## What Changes

- Aplicação Spring Boot 3.5.x com Thymeleaf + Bootstrap 5 (Bootswatch Flatly) entregando um painel responsivo
- CRUD completo de **Campeonatos** (nome, local, período, categoria) com cascata para partidas e medalhas
- CRUD de **Partidas** com sets dinâmicos (adicionar/remover via JS), validação de pontuação (mín. 11 pontos com vantagem de 2), e resultado calculado automaticamente no backend
- CRUD de **Medalhas** (tipo OURO/PRATA/BRONZE, modalidade) vinculadas a campeonatos
- CRUD de **Treinos** (data, duração, tipo TECNICA/FISICO/JOGO) independentes de campeonato
- **Aba Geral** com estatísticas agregadas (vitórias/derrotas/taxa/total de medalhas/campeonatos) e sequência atual de resultados
- **Aba Treino** com resumo por dia/semana/mês e breakdown por tipo
- Seed de dados de exemplo na primeira execução via `CommandLineRunner`
- Endpoint `/actuator/health` para keep-alive no Render free tier
- Dockerfile multi-stage e configuração via variáveis de ambiente para deploy no Render + Aiven MySQL

## Capabilities

### New Capabilities
- `campeonato-management`: CRUD de campeonatos com listagem, formulário, detalhe (estatísticas + partidas + medalhas) e exclusão em cascata
- `partida-management`: CRUD de partidas com sets dinâmicos, validação de pontuação, cálculo automático de resultado e link do YouTube
- `medalha-management`: CRUD de medalhas por tipo e modalidade vinculadas a campeonato
- `treino-management`: CRUD de treinos com listagem, resumo agregado por período e breakdown por tipo
- `estatisticas-gerais`: Painel geral com vitórias, derrotas, taxa, total de medalhas, total de campeonatos e sequência atual
- `data-seeding`: População automática de dados de exemplo na primeira execução
- `infra-deploy`: Dockerfile, configuração de ambiente (actuator health, variáveis), Docker Compose para desenvolvimento local

### Modified Capabilities
<!-- Nenhuma — este é o primeiro conjunto de especificações do projeto. -->

## Impact

- **Novo projeto Maven** com Spring Boot 3.5.x, MySQL 8.4, Thymeleaf, Bootstrap 5, Lombok
- **Entidades JPA**: Campeonato, Partida, SetPartida (Embeddable), Medalha, Treino + enums
- **Camada de serviço** com cálculo de resultado a partir dos sets e agregações estatísticas
- **Controllers** com formulários Thymeleaf + validação server-side
- **Infraestrutura**: Dockerfile multi-stage (Maven build + JRE Alpine), Docker Compose para MySQL local, variáveis de ambiente para credenciais
- **Deploy em nuvem**: Render (app) + Aiven (MySQL), com health-check via actuator
