## 1. Repository - Queries

- [x] 1.1 Adicionar `findAllByOrderByDataInicioDesc()` no CampeonatoRepository
- [x] 1.2 Adicionar `findFirstByDataInicioLessThanEqualAndDataFimGreaterThanEqualOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim)` no CampeonatoRepository
- [x] 1.3 Adicionar `findFirstByDataInicioGreaterThanEqualOrderByDataInicioAsc(LocalDate data)` no CampeonatoRepository
- [x] 1.4 Adicionar `findFirstByDataInicioLessThanEqualOrderByDataInicioDesc(LocalDate data)` no CampeonatoRepository

## 2. Service - Lógica de Negócio

- [x] 2.1 Alterar `CampeonatoService.listar()` para usar `findAllByOrderByDataInicioDesc()`
- [x] 2.2 Refatorar `CampeonatoService.buscarAtual()` com lógica de 3 prioridades:
  - Buscar em andamento (dataInicio ≤ hoje ≤ dataFim)
  - Se não, buscar próximo futuro (dataInicio > hoje)
  - Se não, buscar mais recente passado (dataInicio ≤ hoje)

## 3. Validação

- [x] 3.1 Testar listagem de campeonatos ordenada por data
- [x] 3.2 Testar busca de campeonato atual com cenário de em andamento
- [x] 3.3 Testar busca de campeonato atual com cenário de próximo futuro
- [x] 3.4 Testar busca de campeonato atual com cenário de passado
- [x] 3.5 Testar busca de campeonato atual sem nenhum cadastrado
