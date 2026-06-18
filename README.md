# 🐱 Умный дом кота (Cat Smart Home)

Распределённая система управления умным домом для домашнего питомца, построенная на микросервисной архитектуре с синхронными (REST, GraphQL, gRPC) и асинхронными (RabbitMQ, WebSocket) протоколами взаимодействия.

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?logo=springboot)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-4.0-FF6600?logo=rabbitmq)
![gRPC](https://img.shields.io/badge/gRPC-1.68-4CAF50?logo=grpc)
![GraphQL](https://img.shields.io/badge/GraphQL-DGS-FF69B4?logo=graphql)
![WebSocket](https://img.shields.io/badge/WebSocket-✓-yellow?logo=websocket)

---

## 📦 Основные возможности

- **Управление котами** (CRUD) через REST и GraphQL.
- **Регистрация событий** от датчиков (миска, лоток, активность, дверь).
- **Автоматические реакции** на события (наполнение миски, очистка лотка, активация игрушек).
- **Аналитика активности** (gRPC-сервис вычисляет уровень активности, здоровье и рекомендации).
- **Обогащение данных** – асинхронное событие `cat.created` → синхронный gRPC-вызов → публикация `cat.enriched`.
- **Аудит** – все события сохраняются в журнал.
- **Push-уведомления** в реальном времени через WebSocket.

---

## 🏗 Архитектура

Система состоит из восьми модулей:

| Модуль | Описание |
|--------|----------|
| `cat-api-contract` | Общие REST DTO, API-интерфейсы и исключения |
| `cat-events-contract` | События для RabbitMQ (создание, обогащение, датчики, действия) |
| `cat-grpc-contract` | `.proto`-контракт gRPC-аналитики |
| `demo-rest` | Основной REST/GraphQL-сервер (публикует события) |
| `audit-service` | Слушает все события и сохраняет их в журнал |
| `cat-grpc-analytics-server` | gRPC-сервер аналитики активности |
| `cat-grpc-enrichment-client` | Подписывается на `cat.created`, вызывает gRPC, публикует `cat.enriched` |
| `notification-service` | WebSocket-сервер, рассылает уведомления о событиях |

---

## 🛠 Стек технологий

- **Java 17**
- **Spring Boot 4.0.3** (Web, WebSocket, Data, Actuator)
- **Spring Cloud Netflix DGS** (GraphQL)
- **gRPC** (бинарный протокол с Protocol Buffers)
- **RabbitMQ** (брокер сообщений, Dead Letter Queue)
- **WebSocket** (push-уведомления)
- **HATEOAS** (гипермедиа в REST)
- **Docker** (RabbitMQ)

---

## 🚀 Быстрый старт

### 1. Клонирование репозитория
```bash
git clone https://github.com/your-username/cat-smart-home.git
cd cat-smart-home
