## Context

O app é um painel de tênis de mesa (Spring Boot 3.5.16 + Thymeleaf + MySQL/Aiven), hospedado na Render. Atualmente não possui nenhuma autenticação — qualquer pessoa com a URL acessa e edita todos os dados. O único usuário será a irmã do desenvolvedor.

O app usa HTMX para atualização parcial na página `/geral` (filtro de período). Todos os endpoints são MVC (nenhum REST). Existem 19 endpoints no total, todos protegidos pela nova configuração.

## Goals / Non-Goals

**Goals:**
- Proteger todas as rotas com login obrigatório
- Usuário único em memória, credenciais via variáveis de ambiente
- Form login com "remember me" (30 dias) para boa UX
- Liberar static resources e actuator health
- Tratar HTMX para evitar quebra visual quando sessão expira

**Non-Goals:**
- Sistema de múltiplos usuários ou roles
- Cadastro de usuários
- OAuth/OIDC
- Proteção por IP
- Login social

## Decisions

### 1. Spring Security com InMemoryUserDetailsManager

**Decisão:** Usar `InMemoryUserDetailsManager` com usuário único.

**Alternativa considerada:** JdbcUserDetailsManager (usuários no banco) — desnecessário para 1 usuário, adiciona complexidade.

**Justificativa:** Simples, sem dependência de tabela de usuários no banco. A senha é codificada em runtime com BCrypt a partir da variável de ambiente.

### 2. Form Login em vez de HTTP Basic

**Decisão:** Usar `formLogin()` em vez de `httpBasic()`.

**Alternativa considerada:** HTTP Basic Auth (popup nativo do navegador) — feio, sem "lembrar-me", sem botão de logout.

**Justificativa:** Form login é mais amigável, suporta remember-me, e o Spring fornece a tela de login padrão sem necessidade de template customizado.

### 3. Remember Me com token persistente

**Decisão:** Configurar `rememberMe()` com validade de 30 dias.

**Justificativa:** Evita que a irmã precise fazer login toda vez que acessa o site. O token é armazenado em cookie no navegador.

### 4. Tratamento HTMX via entry point customizado

**Decisão:** Criar `HtmxAwareAuthenticationEntryPoint` que retorna 401 para requests HTMX em vez de redirect para `/login`.

**Alternativa considerada:** Não tratar — HTMX injetaria HTML do login dentro do container, quebrando a visualização.

**Justificativa:** Quando a sessão expira e HTMX faz request, receber 401 é mais limpo que receber HTML do login injetado num container. O usuário pode recarregar a página normalmente.

### 5. Variáveis de ambiente com prefixo APP_

**Decisão:** Usar `APP_LOGIN_USERNAME` e `APP_LOGIN_PASSWORD`.

**Alternativa considerada:** `RENDER_BASIC_AUTH_*` — conflito semântico com variáveis internas do Render.

**Justificativa:** Evita confusão e possível conflito futuro com variáveis que o Render injeta automaticamente.

## Risks / Trade-offs

- **[Risco]** Senha ficaria visível nos logs do Render se logada → **Mitigação:** Spring Security não loga a senha. Variáveis de ambiente são ocultas no dashboard.

- **[Risco]** Usuário único = sem audit trail por pessoa → **Aceitável:** App pessoal, sem necessidade de rastreamento.

- **[Trade-off]** InMemoryUserDetailsManager não persiste entre restarts → **Aceitável:** Usuário é recriado a cada startup a partir da variável de ambiente. Não há estado de sessão a perder.

- **[Risco]** CSRF pode quebrar algo → **Mitigação:** Thymeleaf injeta token automaticamente em `th:action`. Todos os forms já usam `th:action`.

## Migration Plan

1. Adicionar dependência `spring-boot-starter-security` no `pom.xml`
2. Criar `SecurityConfig.java`
3. Adicionar variáveis de ambiente no Render
4. Deploy automático
5. Testar login com as credenciais
6. Verificar que HTMX funciona na página Geral

**Rollback:** Remover `spring-boot-starter-security` do `pom.xml` e deletar `SecurityConfig.java`. O app volta a ficar aberto.
