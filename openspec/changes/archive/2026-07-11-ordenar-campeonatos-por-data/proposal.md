## Why

A lista de campeonatos hoje é exibida em ordem padrão (por ID), sem considerar a data de início. Isso dificulta encontrar o campeonato mais recente. Além disso, o "campeonato atual" na página Geral pode retornar um campeonato futuro que ainda não começou, quando deveria priorizar campeonatos em andamento ou o próximo futuro mais próximo.

## What Changes

- A listagem de campeonatos (`/campeonatos`) será ordenada por `dataInicio` descendente (mais recente primeiro)
- A lógica de "buscar campeonato atual" será refeita com 3 prioridades:
  1. Campeonato em andamento (`dataInicio ≤ hoje ≤ dataFim`)
  2. Próximo campeonato futuro (`dataInicio > hoje`, o mais próximo)
  3. Campeonato mais recente do passado (`dataInicio ≤ hoje`, o mais recente)
- Serão adicionadas queries no `CampeonatoRepository` para suportar essas consultas

## Capabilities

### New Capabilities

- `ordenar-campeonatos`: Ordenação da lista de campeonatos por data de início descendente
- `buscar-campeonato-atual`: Lógica de prioridade para selecionar o campeonato atual (andamento → próximo futuro → mais recente passado)

### Modified Capabilities

Nenhuma capability existente tem requisitos alterados.

## Impact

- `CampeonatoRepository.java`: Novas queries JPQL
- `CampeonatoService.java`: Métodos `listar()` e `buscarAtual()` alterados
- Nenhum endpoint novo, apenas comportamento alterado
- Sem breaking changes na API
