# Visão Geral
Este projeto é dividido em vários microserviços independentes, cada um responsável por uma funcionalidade específica, permitindo que eles sejam escalados e gerenciados de forma independente.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white) ![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)


## Requisitos
- [Java 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- [My SQL](https://www.mysql.com/downloads/)
- [Postman](https://www.postman.com/downloads)
- [RabbitMQ](https://www.rabbitmq.com/download.html)

## Usage
- Comece a aplicação com Maven
- A API está acessível na porta http://localhost:8080

## Endpoints
`API PRODUCT`
```
POST /brand - Crie uma nova marca
GET /brand - Lista marcas
GET /brand/{id} - Lista marcas por id
GET /brand/{brandName} - Lista marcas por nome
PUT /brand/{id} - atualizar marca por id
DELETE /brand/{id} - deleta marca por id
```
```json
{
    "brandName": "Playstation"
}
```

```
POST /product - Cria um novo produto
GET /product - Lista produtos
GET /product/{id} - Lista produto por id
PUT /product/{id} - Atualiza produto por id
DELETE /product/{id} - Deleta produto
GET /product/category/{category} - Lista produtos por categoria
GET /product/name/{name} - Lista produtos por nome
GET /product/color/{color} - Lista produtos por cor
GET /product/size/{size} - Lista produtos por tamanho
```
```json
{
    "name": "Playstation 5",
    "category": "videogames",
    "price": 1500,
    "image": "",
    "color": "branco",
    "size": "null",
    "description": "Playstation 5 com 2 controles",
    "brand": {
        "id": "brand id",
        "brandName": "Playstation"
    }
}
```

`APU USER`
```
POST /user/register - registra um novo usuário
```
```json
{
    "username": "user",
    "password": "password",
    "role": "ADMIN"
}
```
```
POST /user/login - logue com um usuário para gerar um token
```
```json
{
    "username": "user",
    "password": "password"
}
```

`API GATEWAY`
```
GET /fallback - Mostra uma mensagem quando o serviço está indisponível
```
````json
{
    "message": "Serviço indisponível no momento."
}
````

`API STOCK`
```
GET /product - Lista produtos
GET /product/{id} - Lista produto por id
GET /product/category/{category} - Lista produtos por categoria
GET /product/name/{name} - Lista produtos por nome
GET /product/color/{color} - Lista produtos por cor
GET /product/size/{size} - Lista produtos por tamanho
GET /brand - Lista marcas
GET /brand/{id} - Lista marcas por id
GET /brand/{brandName} - Lista marcas por nome
```

## Contributing

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões para melhorias, abra uma issue ou envie um pull request para o repositório.