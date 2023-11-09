# Overview
This project is divided into several independent microservices, each responsible for a specific functionality, allowing them to be scaled and managed independently.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white) ![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)


## Requirements
- [Java 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- [My SQL](https://www.mysql.com/downloads/)
- [Postman](https://www.postman.com/downloads)
- [RabbitMQ](https://www.rabbitmq.com/download.html)

## Microservices

# `Server`

Each microservice, upon startup, registers itself with the Eureka Server, providing its name, IP address, port, and other relevant information.

![Eureka](https://github.com/Viniciu-s/microservices/assets/84327394/a887792a-0bc2-425d-80ba-eae78f6803b8)


# `Product-service`

Before we can register, update, and delete products in our database, it is necessary to authenticate the user to validate access.


```
URL for registering the user.
http://localhost:8080/auth/register
```
![POST user](https://github.com/Viniciu-s/microservices/assets/84327394/c3da089c-c65e-48ef-8f1f-71a539bd4b12)

```
Immediately afterward, it's necessary to log in with the created user.
http://localhost:8080/auth/login
After logging in, a token will be generated.
```
![POST user login](https://github.com/Viniciu-s/microservices/assets/84327394/019e3da0-d73b-408f-8f91-771137390f45)

```
Now it's necessary to validate the token generated with the user.
```
![POST validação](https://github.com/Viniciu-s/microservices/assets/84327394/944de9ab-183c-44fc-b80e-a4f456e401df)

```
Now we can register our product.
http://localhost:8080/product
We can follow the same logic for updating, listing, and deleting products.
Note that the user was created with the "ADMIN" role. If the user is created with the "USER" role, they will only have access to the GET method.
```
![POST produto criado](https://github.com/Viniciu-s/microservices/assets/84327394/813e0c50-4584-49d9-bcd7-fd878cc829b7)

```
To filter products by category, select the category of the products registered in your system. It's also possible to filter products by name.
The URL image above shows how to perform this action. If you want to filter by name, just follow the same logic, replacing /category/{category} with /name/{name}.
```
![CATEGORY](https://github.com/Viniciu-s/microservices/assets/84327394/03df8da9-d5f5-442a-923a-abca940ce5e2)


# `Stock`
The stock API is used, for example, by other employees to query the products that exist in the system, consuming the product-service API where product registration was performed by an authenticated user.

```
URL to register the user.
http://localhost:8080/auth/register
```
![GET STOCK](https://github.com/Viniciu-s/microservices/assets/84327394/726e31b9-3d5c-41ad-90ca-07520542c4e8)



# `Exchanges RabbitMQ`

![Exchange RabitMQ](https://github.com/Viniciu-s/microservices/assets/84327394/45d67cf2-0933-4174-897a-4f6cac8aceae)

The fanoutExchange and deadLetterExchange methods create and return instances of fanout-type exchanges. The notification exchange forwards messages to consumer queues, and the dead-letter exchange is used for handling undelivered messages.


# `Queues RabbitMQ`

![Queue RabbitMQ](https://github.com/Viniciu-s/microservices/assets/84327394/c110f0f6-935e-45e3-91e0-ae7093065693)

The queueNotification and queueDlqNotification methods create and return instances of queues, one for notifications and another for the dead-letter queue of notifications, respectively. The notification queue is configured as non-durable and associated with a dead-letter exchange. The bindProduct and bindDlxProduct methods create and return instances of bindings, which define the relationship between queues and exchanges. The bindProduct binding associates the notification queue with the notification exchange, and the bindDlxProduct binding associates the dead-letter queue with the dead-letter exchange.

# `Notification-service`

The functionality of this microservice is to notify the responsible party that someone has created a product in the Product-service.

![notificação](https://github.com/Viniciu-s/microservices/assets/84327394/6ffe82f5-bf4c-4d42-897c-79605bffdb3d)



# `Gateway`

It simplifies client-microservice communication and allows microservices to remain isolated from each other, facilitating the maintenance, scalability, and evolution of the system as a whole.

This tutorial is available in [Portuguese](README-portuguese.md)
