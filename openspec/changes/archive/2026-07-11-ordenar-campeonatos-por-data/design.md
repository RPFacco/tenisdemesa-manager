## Context

O sistema de tênis de mesa gerencia campeonatos, partidas e treinos. Atualmente:
- A listagem de campeonatos usa `findAll()` sem ordenação por data
- O "campeonato atual" usa lógica que pode retornar campeonatos futuros incorretamente
- O repository já possui queries auxiliares como `findByDataFimGreaterThanEqualOrderByDataInicioDesc`

## Goals / Non-Goals

**Goals:**
- Ordenar campeonatos por `dataInicio` descendente na listagem
- Implementar lógica de prioridade para buscar campeonato atual: andamento → próximo futuro → mais recente passado
- Manter compatibilidade com endpoints existentes

**Non-Goals:**
- Alterar a estrutura do banco de dados
- Adicionar novos endpoints REST
- Modificar a interface visual (appenas comportamento)

## Decisions

### 1. Queries no Repository vs ordenação em memória

**Decisão:** Implementar queries específicas no `CampeonatoRepository`

**Alternativas consideradas:**
- `findAll()` + `stream().sorted()` em memória: Simples mas ineficiente para muitos campeonatos
- Queries JPQL: Mais eficiente, ordenação feita no banco

**Justificativa:** Seguindo o padrão já estabelecido em `queries-otimizadas`, preferimos queries no banco para.performance e clareza.

### 2. Lógica de prioridade para buscarAtual

**Decisão:** Três queries separadas com fallback

```
1. findFirstByDataInicioLessThanEqualAndDataFimGreaterThanEqualOrderByDataInicioDesc(hoje, hoje)
   → Campeonato em andamento (dataInicio ≤ hoje ≤ dataFim)

2. findFirstByDataInicioGreaterThanEqualOrderByDataInicioAsc(hoje)
   → Próximo futuro (dataInicio > hoje)

3. findFirstByDataInicioLessThanEqualOrderByDataInicioDesc(hoje)
   → Mais recente do passado (dataInicio ≤ hoje)
```

**Alternativa considerada:** Uma única query com CASE/WHEN no JPQL - mais complexa e menos legível.

**Justificativa:** Três queries simples são mais fáceis de manter e testar. A performance é aceitável pois são consultas simples com índices.

### 3. Ordenação da listagem

**Decisão:** Adicionar `findAllByOrderByDataInicioDesc()` no repository

**Justificativa:** Simples e direto. A ordenação é feita no banco.

## Risks / Trade-offs

- **Risk:** Queries extras podem causar problemas se houver muitos campeonatos
  - **Mitigation:** Índice em `dataInicio` e `dataFim` (já existentes ou a serem criados)

- **Risk:** Comportamento pode mudar para usuários que esperavam ordem por ID
  - **Mitigation:** Ordenação por data é mais intuitiva e esperada

- **Trade-off:** Três queries em vez de uma para buscarAtual
  - **Aceitável:** Queries são simples e rápidas, código mais legível
