## Why

O site está funcional mas visualmente simplista — cards com cores sólidas do Bootstrap, tabelas genéricas, sem footer, sem destaque de navegação ativa. A aparência atual não reflete o cuidado que a atleta tem com seus registros. Um redesign visual torna o uso mais agradável e dá identidade ao painel.

## What Changes

- Reordenar navbar: Campeonatos → Treino → Geral
- Destacar link ativo na navbar com underline e bg diferente
- Adicionar footer com créditos
- Substituir cards de cores sólidas por cards com gradientes suaves, sombra e elevação no hover
- Substituir tabelas de listagem por cards visuais (campeonatos em cards, treinos em timeline)
- Adicionar animações CSS sutis (transições, fade in)
- Melhorar estados vazios com ícones maiores
- Refinar tipografia (pesos, tamanhos, espaçamentos)
- Adicionar CSS customizado no layout.html

## Capabilities

### New Capabilities

Nenhuma — trata-se exclusivamente de redesign visual, sem novas funcionalidades.

### Modified Capabilities

Nenhuma — não há specs existentes e o comportamento funcional não é alterado.

## Impact

- `src/main/resources/templates/layout.html` — navbar, footer, CSS customizado
- `src/main/resources/templates/geral.html` — cards refeitos
- `src/main/resources/templates/campeonatos/lista.html` — tabela convertida em cards
- `src/main/resources/templates/campeonatos/detalhe.html` — cards de estatísticas refeitos
- `src/main/resources/templates/treinos/lista.html` — cards de resumo e timeline
- `src/main/resources/templates/campeonatos/form.html` — refinamento visual
- `src/main/resources/templates/partidas/form.html` — refinamento visual
- `src/main/resources/templates/medalhas/form.html` — refinamento visual
- `src/main/resources/templates/treinos/form.html` — refinamento visual
- Nenhuma dependência nova — apenas CSS inline no layout.html
