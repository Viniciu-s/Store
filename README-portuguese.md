# Visão Geral
Este projeto é dividido em vários microserviços independentes, cada um responsável por uma funcionalidade específica, permitindo que eles sejam escalados e gerenciados de forma independente.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white) ![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)


## Requisitos
- [Java 17](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- [My SQL](https://www.mysql.com/downloads/)
- [Postman](https://www.postman.com/downloads)
- [RabbitMQ](https://www.rabbitmq.com/download.html)
- [Docker](https://www.docker.com/products/docker-desktop/)

## Microserviços

# `Server`

Cada microserviço, ao ser iniciado, se registra no Eureka Server, informando seu nome, endereço IP, porta e outras informações relevantes.

![Eureka](https://github.com/Viniciu-s/microservices/assets/84327394/a887792a-0bc2-425d-80ba-eae78f6803b8)


# `Product-service`

Antes de conseguirmos cadastrar, atualizar e excluir produtos no nosso banco de dados, é necessário autenticar o usuário para validar o acesso.


```
URL para registrar o usuário.
http://localhost:8080/auth/register
```
![POST user](https://github.com/Viniciu-s/microservices/assets/84327394/c3da089c-c65e-48ef-8f1f-71a539bd4b12)

```
Logo em seguida é necessário realizar o login com o usuário criado.
http://localhost:8080/auth/login
Depois de realizar o login, será gerado um token.
```
![POST user login](https://github.com/Viniciu-s/microservices/assets/84327394/019e3da0-d73b-408f-8f91-771137390f45)

```
Agora é necessário validar o token gerado com o usuário.
```
![POST validação](https://github.com/Viniciu-s/microservices/assets/84327394/944de9ab-183c-44fc-b80e-a4f456e401df)

```
Agora sim podemos cadastrar o nosso produto.
http://localhost:8080/product
Podemos seguir a mesma lógica para atualizar, listar e deletar produtos.
Note que o usuário foi criado com a Role "ADMIN", caso o usuário seja criado com a Role "USER" ele só terá acesso ao método GET.
```
![POST produto criado](https://github.com/Viniciu-s/microservices/assets/84327394/813e0c50-4584-49d9-bcd7-fd878cc829b7)

```
Para conseguir filtrar os produtos por categoria, selecione a categoria dos produtos cadastrados em seu sistema, é possivel tambem filtrar os produtos por nome.
A URL da imagem a cima mostra como realizar essa ação, se você quiser filtrar por nome é só seguir a mesma lógica trocando /category/{category} por /name/{name}.
```
![CATEGORY.PNG](..%2Fread.me%2FMICROSERVICES%2FCATEGORY.PNG)


# `Stock`
A api de stock serve para por exemplo outros funcionários realizarem a consulta dos produtos que existem no sistema, consumindo a api product-service aonde foi realizado a ação de cadastro dos produtos atraves de um usuário autenticado.

```
URL para registrar o usuário.
http://localhost:8080/auth/register
```
![GET STOCK.PNG](..%2Fread.me%2FMICROSERVICES%2FGET%20STOCK.PNG)


# `Exchanges RabbitMQ`

![Exchange RabitMQ](https://github.com/Viniciu-s/microservices/assets/84327394/45d67cf2-0933-4174-897a-4f6cac8aceae)

Os métodos fanoutExchange e deadLetterExchange criam e retornam instâncias de trocas do tipo fanout. A troca de notificações encaminha mensagens para as filas dos consumidores e a troca morta é usada para tratar mensagens não entregues.


# `Queues RabbitMQ`

![Queue RabbitMQ](https://github.com/Viniciu-s/microservices/assets/84327394/c110f0f6-935e-45e3-91e0-ae7093065693)

Os métodos queueNotification e queueDlqNotification criam e retornam instâncias de filas, uma para as notificações e outra para a fila morta (dead-letter queue) de notificações, respectivamente. A fila de notificações é configurada como não durável e associada a uma troca morta.
Os métodos bindProduct e bindDlxProduct criam e retornam instâncias de bindings, que definem a relação entre as filas e as trocas. O binding bindProduct associa a fila de notificações à troca de notificações e o binding bindDlxProduct associa a fila morta à troca morta.

# `Notification-service`

A funcionalidade desse microserviço é notificar o responsável que alguem criou algum produto no Product-service.

![notificação](https://github.com/Viniciu-s/microservices/assets/84327394/6ffe82f5-bf4c-4d42-897c-79605bffdb3d)



# `Gateway`

Simplifica a comunicação cliente-microserviço e permite que os microserviços permaneçam isolados uns dos outros, facilitando a manutenção, escalabilidade e evolução do sistema como um todo.

Esse tutorial está disponivel em [Inglês](README.md)
