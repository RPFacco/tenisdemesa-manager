## Context

O app carrega Bootstrap, Bootswatch Flatly e Bootstrap Icons via CDN (jsdelivr). Cada página também faz consultas ineficientes: `TreinoService` carrega todos os registros com `findAll()` e filtra em memória 3 vezes (diário, semanal, mensal); `EstatisticaService` carrega todos os campeonatos com partidas e medalhas para contar vitórias/derrotas. Não há configuração explícita de cache do Thymeleaf.

## Goals / Non-Goals

**Goals:**
- Servir Bootstrap, Bootswatch e Bootstrap Icons localmente (zero requests a CDN)
- Cache de templates Thymeleaf explícito em produção
- Queries de estatísticas e resumo de treinos usando SQL filtrado no banco

**Non-Goals:**
- Cache de segundo nível (Redis, etc.)
- Minificação adicional (já vem minificado do CDN)
- Mudar o banco de dados
- Lazy loading de imagens (não há imagens)

## Decisions

### 1. Download manual dos assets vs webjars
**Decisão:** Baixar os arquivos manualmente para `src/main/resources/static/css/` e `static/js/`.

**Por quê:** Webjars exigem configuração extra, mapeamento de versão e podem conflitar com a versão atual (Bootstrap 5.3.3, Bootswatch Flatly 5.3.3, Bootstrap Icons 1.11.3). Download manual é mais simples e previsível.

**Alternativa considerada:** Webjars Maven. Rejeitada porque `bootswatch` não tem webjar oficial e exigiria configuração de resource handler.

### 2. Queries derivadas no repositório em vez de JPQL nativo
**Decisão:** Usar `findByDataBetween()` e `countByDataBetween()` do Spring Data JPA.

**Por quê:** Zero código SQL escrito — Spring Data gera a query automaticamente. Mais seguro e de fácil manutenção.

**Alternativa considerada:** `@Query` com JPQL. Mais explícito, mas desnecessário para filtros simples por data.

### 3. PartidaRepository para estatísticas em vez de navegar por Campeonato
**Decisão:** `EstatisticaService` passa a usar `PartidaRepository` diretamente com queries por período.

**Por quê:** A implementação atual (`todasPartidas()`) carrega todos os campeonatos, suas partidas e medalhas — um `N+1` disfarçado. Query direta em `Partida` com JOIN na data é muito mais eficiente.

## Risks / Trade-offs

| Risco | Mitigação |
|-------|-----------|
| Assets locais aumentam o tamanho do JAR (~300KB) | Aceitável para o ganho de velocidade |
| Versão dos assets locais fica dessincronizada com CDN | Atualizar manualmente quando mudar de versão |
| Queries novas podem não cobrir edge cases | Manter os métodos antigos como fallback até validar |
