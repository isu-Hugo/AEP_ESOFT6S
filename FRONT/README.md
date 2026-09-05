<<<<<<< HEAD
# Sistema de Gestão de Doações - PoC (Prova de Conceito)

**Engenharia de Software - 6º Semestre (AEP 2026.2)**

## 👥 Equipe (Série: C)
* **Hugo Ernesto Contreras** (RA: 26015160-2)
* **Victor Emanuel Silva Santana** (RA: 26014309-2)
* **Vinicius Girato Moresqui** (RA: 26014779-2)

---

## 🌍 ODS Atendida: 12 - Consumo e Produção Responsáveis
A plataforma incentiva diretamente a economia circular ao facilitar o reuso de roupas e utilidades, reduzindo o descarte prematuro e a geração de resíduos. A implementação de categorias específicas e de uma "esteira de status" (DISPONÍVEL, RESERVADO, DOADO) demonstra uma gestão eficiente dos recursos doados, garantindo que o consumo e o repasse sejam conscientes, organizados e rastreáveis.

## 🎯 O Problema
* A falta de centralização e organização logística para conectar pessoas com itens sobrando (roupas de inverno, calçados) a quem precisa na mesma região local.
* A ausência de um fluxo estruturado de triagem dificulta o controle real de disponibilidade das doações.

## 💡 A Solução (Primeira Versão Funcional)
Uma aplicação web (PoC) para cadastro, triagem e busca de itens para doação, focada na comunidade local. Os usuários podem cadastrar itens, informando estado, cidade, categoria (ex: Roupa de inverno, Calçado) e atualizar a "esteira de status" de cada item, permitindo que os interessados encontrem doações através de filtros geográficos práticos.

---

## 🛠️ Tecnologias Utilizadas
* **Backend:** Java, Spring Boot
* **Banco de Dados:** MongoDB (NoSQL)
* **Frontend:** HTML, CSS, JS 
* **Testes & Cobertura:** JUnit, Mockito, Jacoco (Geração de relatórios)
* **Versionamento:** Git e GitHub

---

## 🚀 Instruções de Execução

### Pré-requisitos
* Java 17+ instalado
* Maven (ou Wrapper do Maven já incluso no projeto)
* MongoDB instalado rodando localmente na porta padrão (27017) ou cluster na nuvem configurado no `application.properties`.

### Passos para rodar a aplicação
1. Clone este repositório:
   ```bash
   git clone <link-do-repositorio>
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd <nome-da-pasta-do-projeto>
   ```
3. Suba o banco de dados MongoDB (caso esteja utilizando Docker, use o docker-compose, se aplicável).
4. Execute o backend via Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Abra o arquivo `index.html` (ou rode a aplicação frontend) no seu navegador para acessar a interface da PoC.

---

## 🧪 Testes Automatizados e Cobertura

O projeto possui cobertura de testes unitários e de integração superior a 70%, focado nas camadas de `Controller` e `Service`.

### Como rodar os testes
Para rodar todos os testes automatizados, execute o comando:
```bash
./mvnw test
```

### Como gerar a evidência de Cobertura (Relatório >= 70%)
Utilizamos o Jacoco para gerar o relatório de cobertura de código.
1. Execute o comando para rodar os testes e compilar o relatório:
   ```bash
   ./mvnw test jacoco:report
   ```
2. Após a execução, acesse o relatório abrindo o seguinte arquivo no navegador:
   `target/site/jacoco/index.html`

---

## 📦 Padrões do Repositório

### Nomenclatura de Branches
* `feature/` - Para novos recursos (ex: `feature/INFRA-20-setup-do-storage`)
* `bugfix/` - Para corrigir erros
* `hotfix/` - Para correções urgentes
* `design/` - Para atualizações de interface do usuário
* `refatorar/` - Para melhorar a estrutura do código
* `test/` - Para escrever ou aprimorar testes
* `doc/` - Para atualizar documentações

### Padrão de Commits
* `feat:` (ex: `feat(front): Adiciona campo de nome`)
* `fix:` (ex: `fix(back): Ajusta validação de campos`)
* `chore:` (ex: `chore(mongo): Adiciona níveis de restrições`)

---
**Nota para Avaliação - Entrega 1:** O commit final referente à primeira entrega (Dia 11) estará marcado com uma `tag` (ex: `v1.0.0`) na branch principal (`main`).
=======
# FrontApp

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.2.23.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
>>>>>>> f9dfbd4 (organizando pasta frontend)
