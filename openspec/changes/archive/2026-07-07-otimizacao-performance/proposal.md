## Why

Cada página do site demora ~1s para carregar no celular. A causa são 3 CDNs externas carregadas em série e consultas ineficientes que trazem dados do banco inteiro para filtrar em memória.

## What Changes

- Baixar Bootstrap, Bootswatch Flatly e Bootstrap Icons para `src/main/resources/static/` e servir localmente
- Configurar `spring.thymeleaf.cache=true` explicitamente
- Otimizar `TreinoService`: substituir `findAll()` + filtro em memória por queries derivadas no repositório (por data)
- Otimizar `EstatisticaService`: criar queries no repositório para filtrar partidas por período no SQL em vez de carregar tudo
- Remover `todasPartidas()` e `partidasNoPeriodo()` — usar `PartidaRepository` com queries derivadas

## Capabilities

### New Capabilities
- `assets-locais`: Servir CSS/JS de Bootstrap localmente em vez de CDN
- `cache-thymeleaf`: Habilitar cache de templates em produção
- `queries-otimizadas`: Substituir filtros em memória por queries SQL no banco

### Modified Capabilities

<!-- Nenhuma spec existente -- todas são novas -->

## Impact

- `layout.html`: trocar hrefs das CDNs por caminhos locais `/css/...` e `/js/...`
- `application.properties`: adicionar `spring.thymeleaf.cache=true`
- `TreinoRepository.java`: adicionar queries derivadas por período
- `TreinoService.java`: usar as novas queries em vez de `listar().stream().filter()`
- `PartidaRepository.java`: adicionar queries derivadas por período
- `EstatisticaService.java`: usar `PartidaRepository` em vez de `campeonatoRepository.findAll().stream()`
- `pom.xml`: adicionar plugin para copiar webjars ou baixar assets manualmente
- Nenhuma dependência nova — Bootstrap já está no projeto via CDN
