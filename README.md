# HealthConnect

Projeto Spring Boot para cadastro e gerenciamento de pacientes.

## Abrir no IntelliJ

1. Abra a pasta `projetoHealth-main` como projeto Maven.
2. Aguarde o IntelliJ importar o `pom.xml`.
3. Se o IDE pedir a JDK, selecione Java 21.

## Perfis de execucao

O projeto inclui configuracoes compartilhadas em `.run/`:

- `HealthConnect App`: sobe a aplicacao com `spring-boot:run`
- `HealthConnect Tests`: executa `test`

No IntelliJ, elas devem aparecer automaticamente na lista de Run Configurations.

## Maven Wrapper

O projeto inclui Maven Wrapper oficial, entao nao depende de `mvn` instalado globalmente.

Voce pode rodar pelo terminal:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Enderecos uteis

- Aplicacao: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`

## Observacoes

- Os dados ficam persistidos localmente na pasta `data/`.
- Os testes usam banco H2 em memoria separado do banco da aplicacao.
