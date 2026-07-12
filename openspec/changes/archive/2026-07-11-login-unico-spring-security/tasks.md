## 1. Dependência

- [x] 1.1 Adicionar `spring-boot-starter-security` no `pom.xml`

## 2. Configuração de Segurança

- [x] 2.1 Criar `SecurityConfig.java` em `config/` com:
  - `PasswordEncoder` (BCrypt)
  - `InMemoryUserDetailsManager` lendo de `APP_LOGIN_USERNAME` e `APP_LOGIN_PASSWORD`
  - `SecurityFilterChain` com:
    - `permitAll()` para `/css/**`, `/js/**`, `/images/**`, `/webjars/**`, `/actuator/**`, `/login`, `/logout`
    - `anyRequest().authenticated()`
    - `formLogin().permitAll()`
    - `rememberMe()` com 30 dias de validade
    - `logout().permitAll()`
    - Entry point customizado para HTMX

- [x] 2.2 Criar `HtmxAudaAuthenticationEntryPoint` que:
  - Verifica se request tem header `HX-Request: true`
  - Se sim, retorna 401 Unauthorized
  - Se não, redireciona para `/login`

## 3. Variáveis de Ambiente

- [x] 3.1 Documentar variáveis necessárias para deploy no Render:
  - `APP_LOGIN_USERNAME` (ex: nome da irmã)
  - `APP_LOGIN_PASSWORD` (senha forte)

## 4. Validação

- [x] 4.1 Testar que rotas protegidas redirecionam para `/login` sem credenciais
- [x] 4.2 Testar que login com credenciais válidas acesso ao site
- [x] 4.3 Testar que login com credenciais inválidas retorna erro
- [x] 4.4 Testar que static resources funcionam sem login
- [x] 4.5 Testar que `/actuator/health` funciona sem login
- [x] 4.6 Testar que HTMX request não autenticado retorna 401
- [x] 4.7 Testar que HTMX request autenticado retorna fragmento normalmente
- [x] 4.8 Testar que "remember me" mantém sessão após fechar navegador
- [x] 4.9 Testar que logout encerra sessão e limpa cookie
