# cache-thymeleaf Specification

## Purpose
TBD - created by archiving change otimizacao-performance. Update Purpose after archive.
## Requirements
### Requirement: Cache de templates habilitado
O Thymeleaf MUST ter o cache de templates habilitado em produção.

#### Scenario: Cache ativo
- **WHEN** uma página é renderizada pela segunda vez
- **THEN** o template NÃO MUST ser recompilado — MUST usar a versão em cache

