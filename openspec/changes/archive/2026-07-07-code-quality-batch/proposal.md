## Why

O código tem diversas oportunidades de melhoria que individualmente são pequenas, mas no conjunto comprometem manutenibilidade: lógica de domínio vazando para controllers, exceções genéricas, strings de enum espalhadas por templates, código duplicado e zero testes. Agrupar tudo num batch evita o atrito de criar N mudanças pequenas.

## What Changes

- **Backend**: Extrair cálculo de estatísticas de `CampeonatoController.detalhe()` para `CampeonatoService`; adicionar exceções customizadas (`NotFoundException`); mover validação de datas repetida para método no service; mover conversão horas/minutos para o model `Treino`
- **Frontend**: Substituir todas as ocorrências de `enum.name()` nos templates por métodos helpers booleanos nos models (`isVitoria()`, `isOuro()`, etc.); extrair estilo de medalha para fragmento Thymeleaf único; trocar URLs hardcoded por `th:href`; migrar inline JS de sets para `app.js`
- **Config**: Mudar `ddl-auto=update` para `validate`; adicionar `@Index` nas FKs de `Partida` e `Medalha`
- **Testes**: Adicionar testes unitários para `PartidaService.validarSets()`, `EstatisticaService.sequenciaAtual()`, `TreinoService.buildResumo()`

## Capabilities

### New Capabilities
- `internal-quality`: Refatorações de qualidade interna — exceções customizadas, lógica de domínio em services, helpers de template, migração de configuração e testes unitários

### Modified Capabilities
(nenhuma — todas as mudanças são internas, sem alteração de comportamento visível)

## Impact

- **src/main/java/.../controller/CampeonatoController.java** — remover cálculo inline de vitórias/derrotas
- **src/main/java/.../controller/TreinoController.java** — remover conversão horas/minutos
- **src/main/java/.../controller/PartidaController.java** — trocar RuntimeException por NotFoundException
- **src/main/java/.../service/CampeonatoService.java** — adicionar `calcularEstatisticas()`, `validarDatas()`
- **src/main/java/.../service/PartidaService.java** — trocar IllegalArgumentException por custom
- **src/main/java/.../exception/NotFoundException.java** — novo arquivo
- **src/main/java/.../exception/package-info.java** — se necessário
- **src/main/java/.../model/Partida.java** — adicionar `isVitoria()`, `isDerrota()`
- **src/main/java/.../model/Medalha.java** — adicionar `getTipoCssClass()`, `getTipoStyle()`, `getTipoLabel()`
- **src/main/java/.../model/enums/TipoMedalha.java** — adicionar campos de cor/icone
- **src/main/resources/templates/** — ajustar referências de enum nos templates
- **src/main/resources/templates/medalhas/fragment.html** — novo fragmento para estilo de medalha
- **src/main/resources/static/js/app.js** — migrar inline JS de sets para cá
- **src/main/resources/application.properties** — ddl-auto=validate
- **src/test/java/.../service/** — novos testes unitários
