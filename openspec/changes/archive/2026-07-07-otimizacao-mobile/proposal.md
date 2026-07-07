## Why

O site é usado principalmente no celular, mas o layout atual foi pensado para desktop e adaptado com Bootstrap. A navegação, os botões, as tabelas e os espaçamentos não são ideais para telas pequenas, prejudicando a usabilidade no dia a dia.

## What Changes

- Navbar colapsa em breakpoint menor (`md` em vez de `lg`) para não ficar apertado em tablets
- Navegação inferior (bottom nav) fixa com os 3 links principais, mais ergonômica para uso com uma mão
- Tabelas de partidas e medalhas convertidas para cards/listas verticais em mobile
- Botões de ação com touch targets de no mínimo 44px
- Substituir botões "Novo" no topo por FAB (Floating Action Button) no canto inferior direito
- Cards de estatísticas na página geral adaptados para ocupar melhor o espaço em mobile
- Ajustes gerais de padding, font-size e espaçamento para mobile-first

## Capabilities

### New Capabilities
- `layout-responsivo`: Ajustes no layout base (navbar, bottom nav, container spacing) para mobile-first
- `navegacao-mobile`: Bottom navigation fixa com transições suaves
- `listas-mobile`: Conversão de tabelas para cards touch-friendly em partidas e medalhas
- `formularios-mobile`: Ajustes em formulários para foco e toque em mobile
- `componentes-mobile`: FAB, touch targets, botões maiores

### Modified Capabilities

<!-- Nenhuma spec existente -- todas são novas -->

## Impact

- `layout.html`: navbar breakpoint, bottom nav, estilos globais
- `geral.html`: stats cards, botões, espaçamentos
- `campeonatos/lista.html`: FAB, cards
- `campeonatos/detalhe.html`: tabelas → listas touch
- `treinos/lista.html`: FAB, cards
- `partidas/form.html`, `treinos/form.html`, `campeonatos/form.html`, `medalhas/form.html`: touch targets
- Nenhuma dependência nova necessária (já usa Bootstrap 5.3 + Bootstrap Icons)
