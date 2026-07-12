## Why

O site está completamente aberto — qualquer pessoa com a URL pode acessar e modificar todos os dados (campeonatos, partidas, treinos, medalhas). O único usuário será a irmã do desenvolvedor, então é necessário restringir o acesso com um login simples, sem sistema de cadastro complexo.

## What Changes

- Adicionar `spring-boot-starter-security` como dependência
- Criar classe `SecurityConfig` com usuário único em memória, lendo credenciais de variáveis de ambiente (`APP_LOGIN_USERNAME`, `APP_LOGIN_PASSWORD`)
- Configurar form login (não httpBasic) com "remember me" por 30 dias
- Liberar acesso a static resources (`/css/**`, `/js/**`, `/images/**`), actuator (`/actuator/**`) e favicon
- Tratar requests HTMX para retornar 401 em vez de redirect (evita quebra visual na página Geral)
- Proteger todas as outras rotas com autenticação obrigatória
- **BREAKING**: Acesso ao site agora requer login — sem ele, todas as rotas redirecionam para `/login`

## Capabilities

### New Capabilities

- `login-form`: Formulário de login do Spring Security com sessão persistente (remember me)

### Modified Capabilities

Nenhuma capability existente tem requisitos alterados.

## Impact

- `pom.xml`: Nova dependência `spring-boot-starter-security`
- `SecurityConfig.java`: Nova classe de configuração (não existe nenhuma config de segurança)
- `application.properties`: Nenhuma mudança (credenciais via env vars do Render)
- Templates: Nenhum template alterado (CSRF automático via `th:action`)
- Static resources: Acesso liberado via config de segurança
- Actuator: `/actuator/health` liberado
- HTMX: Tratamento especial para requests autenticados
