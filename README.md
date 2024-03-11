# Overview
This project is divided into several independent microservices, each responsible for a specific functionality, allowing them to be scaled and managed independently.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white) ![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)


## Requirements
- [Java 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- [My SQL](https://www.mysql.com/downloads/)
- [Postman](https://www.postman.com/downloads)
- [RabbitMQ](https://www.rabbitmq.com/download.html)

## Usage
- Start the application with Maven
- The API will be accessible at http://localhost:8080

## Endpoints
`API PRODUCT`
```
POST /brand - Create a new brand
GET /brand - List brands
GET /brand/{id} - List brands by id
GET /brand/{brandName} - List brands by name
PUT /brand/{id} - update brand by id
DELETE /brand/{id} - delete brand by id
```
```json
{
    "brandName": "Playstation"
}
```

```
POST /product - Create a new product
GET /product - List Products
GET /product/{id} - List product by id
PUT /product/{id} - Update product by id
DELETE /product/{id} - Delete product
GET /product/category/{category} - List products by category
GET /product/name/{name} - List products by name
GET /product/color/{color} - List products by color
GET /product/size/{size} - List products by size
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
POST /user/register - register a new user
```
```json
{
    "username": "user",
    "password": "password",
    "role": "ADMIN"
}
```
```
POST /user/login - login with your user for generate a token
```
```json
{
    "username": "user",
    "password": "password"
}
```

`API GATEWAY`
```
GET /fallback - shows a message when the service is down
```
````json
{
    "message": "Serviço indisponível no momento."
}
````

`API STOCK`
```
GET /product - List Products
GET /product/{id} - List product by id
GET /product/category/{category} - List products by category
GET /product/name/{name} - List products by name
GET /product/color/{color} - List products by color
GET /product/size/{size} - List products by size
GET /brand - List brands
GET /brand/{id} - List brands by id
GET /brand/{brandName} - List brands by name
```

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.