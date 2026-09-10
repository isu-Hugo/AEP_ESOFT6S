# Decisões arquiteturais

## ADR-001 — Java 21

**Decisão:** utilizar Java 21.

**Motivação:** trabalhar com uma versão LTS moderna e tornar recursos atuais da linguagem, como records, disponíveis aos exemplos.

**Alternativas consideradas:** Java 17 e versões não LTS.

**Consequências:** o ambiente de desenvolvimento e execução precisa disponibilizar JDK 21.

## ADR-002 — Spring Boot

**Decisão:** utilizar Spring Boot 4.1.1 como base da API REST.

**Motivação:** integrar servidor HTTP, validação, persistência e testes com configuração pequena e convenções amplamente adotadas.

**Alternativas consideradas:** configurar Spring Framework manualmente ou usar outro framework web.

**Consequências:** versões das bibliotecas principais serão gerenciadas pelo Spring Boot e o projeto seguirá suas convenções.

## ADR-003 — MongoDB

**Decisão:** persistir doações em MongoDB.

**Motivação:** demonstrar persistência NoSQL orientada a documentos.

**Alternativas consideradas:** banco relacional e armazenamento somente em memória.

**Consequências:** a aplicação usará Spring Data MongoDB.

## ADR-004 — Camadas Controller, Service e Repository

**Decisão:** separar protocolo HTTP, casos de uso e persistência em camadas explícitas.

**Motivação:** tornar responsabilidade, composição e injeção de dependência claras na estrutura do projeto.

**Alternativas consideradas:** Controller acessando Repository diretamente e arquiteturas com excesso de abstrações.

**Consequências:** Controllers permanecem enxutos e Services concentram os casos de uso, mantendo o código organizado sem a necessidade de interfaces artificiais.

## ADR-005 — DTO genérico.

**Decisão:** cria um contrato para criação, e listagem.

**Motivação:** O DTOs se torna idêntico para ambos os casos e fácil ajuste se necessário.

**Alternativas consideradas:** Exposição direta do documento MongoDB.

**Consequências:** reduz a quantidade de arquivos/classes, mas pode expor campos não utilizados em todas as operações.

## ADR-006 — Mapping explícito

**Decisão:** implementar conversões em classes Mapper com Java explícito.

**Motivação:** permitir visualização de dados entre contratos e persistência.

**Alternativas consideradas:** Implementação dentro do bloco do DTO.

**Consequências:** existe algum código de conversão manual, centralizado e fácil de depurar.

## ADR-007 — Testcontainers

**Decisão:** executar testes de integração contra MongoDB real criado por Testcontainers.

**Motivação:** validar comportamento real do driver e do banco sem depender da máquina ou do Compose de desenvolvimento.

**Alternativas consideradas:** MongoDB local, banco compartilhado e implementações fake embarcadas.

**Consequências:** testes de integração são reproduzíveis, mas exigem Docker disponível.

## ADR-008 — Java Records para DTOs

**Decisão:** utilizar records quando forem adequados aos DTOs imutáveis.

**Motivação:** expressar contratos de dados concisos e imutáveis com recurso nativo do Java 21.

**Alternativas consideradas:** classes mutáveis com getters e setters e Lombok.

**Consequências:** DTOs têm pouca cerimônia e não dependem de geração de código externa.

## ADR-009 — Springdoc OpenAPI

**Decisão:** gerar o contrato OpenAPI e a documentação visual com Springdoc OpenAPI 3.1.10, expondo a interface em `/swagger-ui.html`.

**Motivação:** oferecer uma página web interativa que permaneça sincronizada com os Controllers, DTOs e validações da aplicação.

**Alternativas consideradas:** manter somente exemplos manuais no README.

**Consequências:** a aplicação passa a expor a interface visual em `/swagger-ui.html` e o contrato JSON em `/v3/api-docs`, com uma dependência adicional compatível com Spring Boot 4.1.1.

## ADR-010 — Cliente web com framework

**Decisão:** servir uma página inicial em `/` e implementar a criação de uma doação em uma página, usando Angular com `HTTPClient`.

**Motivação:** Contato prévio de todo o time com a tecnologia.

**Alternativas consideradas:** Linguagem nativa, HTML, CSS e JS.

**Consequências:** A manutenção se torna modularizada para a expansão de funcionalidades, depêndencia.

## ADR-011 — MongoDB 7.0 no ambiente de desenvolvimento

**Decisão:** utilizar `mongo:7.0` como versão padrão no Compose de desenvolvimento.

**Motivação:** a imagem `mongo:8.0` recusa iniciar em alguns hosts com kernel Linux 6.19 ou mais novos. A versão 7.0 também é a utilizada pelos testes de integração e torna o exemplo mais reproduzível entre as máquinas da turma.

**Alternativas consideradas:** exigir atualização do kernel, manter 8.0 e documentar um comando alternativo ou usar uma tag sem versão fixa.

**Consequências:** `docker compose up -d` funciona no ambiente validado sem parâmetros adicionais.

## ADR-012 — Volume de dados exclusivo do projeto

**Decisão:** montar o volume Docker nomeado `data` em `/data/db`.

**Motivação:** manter os dados isolados de qualquer MongoDB instalado no host e oferecer o mesmo Compose no Linux e no Docker Desktop do Windows. A imagem oficial do MongoDB recomenda volumes nomeados no Windows, pois bind mounts do host podem ser incompatíveis com seus arquivos mapeados em memória.

**Alternativas consideradas:** bind mount em uma pasta do repositório e caminhos absolutos específicos de cada sistema operacional.

**Consequências:** o Docker gerencia a localização física dos dados; `docker volume inspect data` permite consultá-la. O volume não é removido por `docker compose down`, mas é apagado por `docker compose down --volumes`.
