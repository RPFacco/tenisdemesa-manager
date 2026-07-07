## 1. Project Setup

- [x] 1.1 Create Maven project with pom.xml (Spring Boot 3.5.x, MySQL, Thymeleaf, Lombok, Actuator, thymeleaf-layout-dialect)
- [x] 1.2 Configure application.properties with MySQL connection via env vars, JPA ddl-auto=update, actuator health endpoint, server port
- [x] 1.3 Create package structure under com.ana.tenisdemesa (config, model, model/enums, repository, service, controller)

## 2. Model Layer

- [x] 2.1 Create enums: TipoMedalha, Modalidade, FasePartida, ResultadoPartida, TipoTreino
- [x] 2.2 Create SetPartida embeddable (numeroSet, pontosAtleta, pontosAdversario)
- [x] 2.3 Create Campeonato entity (nome, local, dataInicio, dataFim, categoria, @OneToMany partidas + medalhas with cascade ALL orphanRemoval)
- [x] 2.4 Create Partida entity (campeonato @ManyToOne, adversario, fase, data, @ElementCollection sets, linkYoutube; resultado as transient)
- [x] 2.5 Create Medalha entity (campeonato @ManyToOne, tipo, modalidade)
- [x] 2.6 Create Treino entity (data, duracaoHoras, tipo — standalone, no relationship)

## 3. Repository Layer

- [x] 3.1 Create CampeonatoRepository (JpaRepository)
- [x] 3.2 Create PartidaRepository (JpaRepository)
- [x] 3.3 Create MedalhaRepository (JpaRepository)
- [x] 3.4 Create TreinoRepository (JpaRepository)

## 4. Service Layer

- [x] 4.1 Create PartidaService with set validation (11-point rule, 2-point advantage), result calculation (VITORIA/DERROTA from sets), and match tie detection
- [x] 4.2 Create CampeonatoService (CRUD delegation)
- [x] 4.3 Create MedalhaService (CRUD delegation)
- [x] 4.4 Create TreinoService (CRUD delegation + daily/weekly/monthly aggregation with tipo breakdown)
- [x] 4.5 Create EstatisticaService (aggregated wins/losses/win-rate across all championships, medal totals, championship count, current streak calculation)

## 5. Controller Layer

- [x] 5.1 Create GeralController (GET / redirect to /geral, GET /geral with aggregated stats)
- [x] 5.2 Create CampeonatoController (list, new form, save, detail, edit form, update, delete)
- [x] 5.3 Create PartidaController (new form with dynamic sets JS, save, edit form, update, delete)
- [x] 5.4 Create MedalhaController (new form, save, delete)
- [x] 5.5 Create TreinoController (list with summaries, new form, save, delete)

## 6. View Layer (Thymeleaf Templates)

- [x] 6.1 Create layout.html (Bootstrap 5 via Bootswatch Flatly CDN, Bootstrap Icons CDN, navbar with 3 items: Campeonatos, Geral, Treino, layout dialect for content insertion)
- [x] 6.2 Create geral.html (stats cards: wins, losses, win rate, medals by type, championships count, current streak badge)
- [x] 6.3 Create campeonatos/lista.html (table of championships with medal counts, "Novo Campeonato" button)
- [x] 6.4 Create campeonatos/form.html (create/edit form with validation, date pickers)
- [x] 6.5 Create campeonatos/detalhe.html (championship stats + partidas list + medalhas list with add/delete)
- [x] 6.6 Create partidas/form.html with JavaScript for dynamic set rows (add/remove sets, validation)
- [x] 6.7 Create medalhas/form.html (tipo and modalidade selects)
- [x] 6.8 Create treinos/lista.html (summary cards for today/week/month with tipo breakdown, full list)
- [x] 6.9 Create treinos/form.html (date, duration, tipo select)
- [x] 6.10 Wire flash messages for all save/update/delete operations

## 7. Data Seeder

- [x] 7.1 Create DataSeeder (CommandLineRunner) that checks campeonatoRepository.count() == 0
- [x] 7.2 Seed 2 championships with realistic data (Rio Grande do Sul locations)
- [x] 7.3 Seed 3-4 matches per championship with varied sets (wins, losses, some with YouTube links)
- [x] 7.4 Seed 1-2 medals per championship
- [x] 7.5 Seed 8-10 training records across last 3-4 weeks with all 3 tipos

## 8. Infrastructure & Deploy

- [x] 8.1 Create multi-stage Dockerfile (Maven build + JRE Alpine)
- [x] 8.2 Create docker-compose.yml for local MySQL development

## 9. Final Verification

- [x] 9.1 Compilation verified (`mvn compile -q` succeeds)
- [x] 9.2 Test context loads with H2 in-memory database (`mvn test` succeeds)
- [ ] 9.3 (Requires MySQL) Run `mvn spring-boot:run` and verify app starts, connects to MySQL, and seed populates data
- [ ] 9.4 (Requires MySQL) Verify all CRUD operations: create, list, detail, edit, delete for each entity
- [ ] 9.5 (Requires MySQL) Verify match result is calculated correctly from sets (never persisted)
- [ ] 9.6 (Requires MySQL) Verify geral page shows correct aggregated stats and current streak
- [ ] 9.7 (Requires MySQL) Verify treino page shows correct day/week/month summaries with breakdown
- [ ] 9.8 (Requires running app) Verify GET /actuator/health returns UP
- [ ] 9.9 (Requires Docker) Verify Docker build succeeds
