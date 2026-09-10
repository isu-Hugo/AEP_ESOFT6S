# Arquitetura

O projeto foi construído com base em uma arquitetura em camadas, separando claramente suas responsabilidades.

As páginas web atuam como clientes da API, consumindo os endpoints HTTP de forma independente do backend Spring:

```text
Navegador / Frontend -> fetch / API Call -> Controller -> DTO -> Service
```

A requisição HTTP é direcionada ao DoacaoController, que recebe e valida o RequestDoacaoDTO. Em seguida, delega a execução do caso de uso ao DoacaoService, que aplica as regras de negócio e utiliza o DoacaoRepository para a persistência dos dados no MongoDB.


## Fluxo de saída

```text
MongoDB
  |
Model
  |
Service
  |
Mapper
  |
Response DTO
  |
Controller
  |
JSON
```

Os documentos armazenados no MongoDB são recuperados pela camada de repositório (DoacaoRepository). O DoacaoService processa as informações e utiliza o DoacaoMapper para converter o modelo interno em um ResponseDoacaoDTO, que trafega até o DoacaoController para ser retornado em formato JSON com o código HTTP adequado.


## Responsabilidades

- **Controller:** protocolo HTTP, validação da entrada e códigos de resposta.
- **DTO:** formato público usado para o trafego de dados.
- **Mapper:** conversão explícita, sem acesso ao banco ou regra de negócio.
- **Service:** casos de uso e regras de negócio.
- **Repository:** persistência e consultas.
- **Model:** estrutura persistida no MongoDB.
- **Configuration:** integração com infraestrutura e recursos exclusivos de ambientes específicos.

Nenhum `Model` anotado com `@Document` deve atravessar diretamente a fronteira HTTP.
