## Context

Aplicação Spring Boot single-user para registrar desempenho no tênis de mesa. A especificação técnica detalhada em `docs/especificacao-tecnica-tenis-de-mesa.md` define stack, modelagem, rotas, regras de negócio e deploy. O projeto é novo (zero código existente).

## Goals / Non-Goals

**Goals:**
- Implementar CRUD completo de Campeonato, Partida (com sets dinâmicos), Medalha e Treino
- Calcular resultado da partida automaticamente a partir dos sets (nunca persistido)
- Exibir estatísticas agregadas por campeonato e gerais (aba Geral), incluindo sequência atual
- Exibir resumo de treinos por dia/semana/mês com breakdown por tipo
- Seed de dados de exemplo na primeira execução
- Dockerfile multi-stage para deploy no Render + MySQL no Aiven
- Endpoint `/actuator/health` para keep-alive

**Non-Goals:**
- Autenticação/login (single-user)
- Múltiplos atletas/usuários
- Testes automatizados (postergado)
- Internacionalização (português only)
- API REST (apenas formulários Thymeleaf)

## Decisions

1. **Resultado calculado, não persistido** — ao invés de armazenar uma coluna `resultado` na tabela `partida`, o cálculo é feito em memória no `PartidaService` e exposto como campo transitório. Isso elimina dessincronização se os sets forem editados.
2. **Sets como `@ElementCollection` (Embeddable)** — Sets são parte intrínseca da partida, sem identidade própria. `@ElementCollection` com `@OrderColumn` preserva a ordem e evita uma tabela separada com PK própria.
3. **Spring Boot 3.5.x (não 4.x)** — Escolha deliberada: mais material de referência, exemplos e familiaridade em geração de código por IA. Migração futura possível.
4. **POST para todas as ações de escrita** — Sem PUT/DELETE (HiddenHttpMethodFilter). Mantém compatibilidade com formulários HTML puros.
5. **Seed via `CommandLineRunner`** — Executa apenas se `count() == 0`, garantindo idempotência.
6. **CSS via CDN (Bootstrap + Bootswatch + Bootstrap Icons)** — Sem build tool frontend (Webpack/Vite), reduzindo complexidade.

## Risks / Trade-offs

- **Render free tier dorme após inatividade**: Mitigado por endpoint `/actuator/health` e serviço externo de ping (cron-job.org).
- **Aiven free tier exige SSL**: `sslMode=REQUIRED` na JDBC URL.
- **Single-user impede escalabilidade futura**: Aceito como trade-off para simplicidade inicial.
- **Sem testes automatizados**: Risco de regressão em mudanças futuras; posto como melhoria posterior.
