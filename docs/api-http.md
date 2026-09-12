# Contratos HTTP

Base path: `/api/v1/doacao`.

A documentação visual e interativa está disponível em `/swagger-ui.html`. O contrato OpenAPI em JSON é exposto em `/v3/api-docs`.

| Método | Caminho | Entrada | Resposta de sucesso | Recurso ausente |
|---|---|---|---|---|
| `GET` | `/api/v1/doacoes` | — | `200` com lista de `ResponseDoacaoDTO` | — |
| `GET` | `/api/v1/doacao/{id}` | — | `200` com `ResponseDoacaoDTO` | `404` |
| `POST` | `/api/v1/doacao` | `ResponseDoacaoDTO` | `201`, cabeçalho `Location` e `ResponseDoacaoDTO` | — |

## Criação

POST recebe:

```json
{
  "item_doacao": "string",
  "obs": "string",
  "estado_uf": "string",
  "cidade": "string",
  "categoria_doacao": "ROUPA",
  "tamanho": "string",
  "estado_conservacao": "NOVO",
  "ponto_coleta": "string"
}
```


### Validação

Nos contratos de criação e atualização:

- `categoria_doacao` e `estado_conservacao` devem estar nos formatos previstos de enum;
- `status` é atribuído automaticamente como `DISPONIVEL`

```text
categoria_doacao:
  ROUPA, CALCADO, COBERTOR, CAMA_E_BANHO, BRINQUEDO, ALIMENTO_NAO_PERECIVEL, OUTRO

status:
  DISPONIVEL, DOADO, RESERVADO

estado_conservacao:
  NOVO, SEMINOVO, USADO
```


Violações retornam `400 Bad Request`.

## Listagem resumida

```json
[
    {
    "id": "first",
    "item_doacao": "Camiseta",
    "obs": "",
    "status": "DISPONIVEL",
    "estado_uf": "PR",
    "cidade": "MARINGA",
    "categoria_doacao": "ROUPA",
    "tamanho": "M",
    "estado_conservacao": "USADO",
    "ponto_coleta": "Prefeitura"
  }
]
```

## Consulta individual

```json
  {
    "id": "first",
    "item_doacao": "Camiseta",
    "obs": "",
    "status": "DISPONIVEL",
    "estado_uf": "PR",
    "cidade": "MARINGA",
    "categoria_doacao": "ROUPA",
    "tamanho": "M",
    "estado_conservacao": "USADO",
    "ponto_coleta": "Prefeitura"
  }
```

Caso o recurso não seja encontrado para o ID informado, a API retorna o status 404 Not Found.


