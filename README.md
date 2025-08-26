# AutoService Lite CRM

Мини-CRM для автосервиса. REST API для управления клиентами и заказами.
Построено на **Java 17** и **Spring Boot 3**.

Проект демонстрирует чистую архитектуру, использование DTO, миграции с Flyway, глобальную обработку ошибок, работу с PostgreSQL и контейнеризацию в Docker.

---

## Оглавление

* [Функциональность](#функциональность)
* [Архитектура](#архитектура)
* [Тестирование](#тестирование)
* [Технологический стек](#технологический-стек)
* [Модель данных](#модель-данных)
* [Миграции БД](#миграции-бд)
* [Запуск](#запуск)

    * [Вариант A: Всё в Docker](#вариант-a-всё-в-docker)
    * [Вариант B: БД в Docker, приложение локально](#вариант-b-бд-в-docker-приложение-локально)
* [REST API](#rest-api)

    * [Клиенты](#клиенты)
    * [Заказы](#заказы)
    * [Примеры cURL](#примеры-curl)
    * [Скриншоты рабочего процесса](#скриншоты-рабочего-процесса)
* [Дальнейшие развитие](#дальнейшие-развитие)

---

## Функциональность

* CRUD для клиентов.
* CRUD для заказов (привязаны к клиентам).
* Демонстрационные данные загружаются автоматически через Flyway.
* Swagger UI для тестирования запросов.
* PostgreSQL со строгим контролем схемы.
* Глобальная обработка ошибок и единый формат ответа.
* Docker Compose для локального запуска (приложение + база).
* Базовые модульные и интеграционные тесты (репозиторий, REST-контроллер).

---

## Архитектура

```
src/main/java/io/mitrofanovbp/autoservice/
 ├─ config/       # Конфигураторы
 ├─ controller/   # REST-контроллеры
 ├─ dto/          # DTO для запросов/ответов
 ├─ exception/    # Кастомные исключения + обработчик ошибок
 ├─ model/        # JPA-сущности (Customer, Order)
 ├─ repository/   # Spring Data JPA репозитории
 └─ service/      # Бизнес-логика
 
src/test/java/io/mitrofanovbp/autoservice/
 ├─ CustomerRepositoryTest.java   # Модульный тест репозитория
 └─ CustomerControllerTest.java   # Интеграционный тест REST API
```

---

## Тестирование

В проекте реализованы базовые тесты, демонстрирующие подход к покрытию кода:

* **Модульный тест репозитория** (`CustomerRepositoryTest`)  
  Проверяет сохранение и поиск клиента через JPA.

* **Интеграционный тест контроллера** (`CustomerControllerTest`)  
  Использует `MockMvc`, проверяет создание клиента и получение списка клиентов через REST API.

Запуск тестов:
```bash
mvn test
```

---

## Технологический стек

* **Java 17**
* **Spring Boot 3** (Web, Data JPA, Validation)
* **Hibernate** (через Spring Data JPA)
* **PostgreSQL 16**
* **Flyway** (миграции БД)
* **Jackson** (работа с JSON)
* **Swagger (springdoc-openapi)** (документация REST API)
* **JUnit 5**, **Spring Test (MockMvc)** (тестирование)
* **Docker**, **Docker Compose**
* **Maven**


---

## Модель данных

* `customers` — клиенты (`full_name`, `phone`, `email`)
* `orders` — заказы (`description`, `status`, `customer_id`)

**Связь:** один клиент → много заказов.

---

## Миграции БД

* `V1__init.sql` — создание таблиц, связей и ограничений.
* `V2__seed_demo_data.sql` — демо-данные (несколько клиентов и заказов).

---

## Запуск

### Вариант A: Всё в Docker

```bash
docker compose up -d --build
docker compose logs -f app
```

* Swagger UI: `http://localhost:8080/swagger-ui.html`
* БД: `localhost:5433` (если порт указан как 5433:5432)

### Вариант B: БД в Docker, приложение локально

1. Запустить только БД:

   ```bash
   docker compose up -d db
   ```
2. В IDE/консоли настроить переменные окружения:

   ```bash
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/autoservice_lite
   SPRING_DATASOURCE_USERNAME=postgres
   SPRING_DATASOURCE_PASSWORD=postgres
   ```
3. Запустить приложение:

   ```bash
   mvn spring-boot:run
   ```

---

## REST API

Базовый путь: `/api`

### Клиенты

* `GET /api/customers` — список клиентов
* `POST /api/customers` — создать клиента
* `GET /api/customers/{id}` — получить клиента
* `PUT /api/customers/{id}` — обновить клиента
* `DELETE /api/customers/{id}` — удалить клиента

### Заказы

* `GET /api/orders` — список заказов
* `POST /api/orders` — создать заказ (нужен `customerId`)
* `GET /api/orders/{id}` — получить заказ
* `PUT /api/orders/{id}` — обновить заказ
* `DELETE /api/orders/{id}` — удалить заказ

---

### Примеры cURL

```bash
# Создать клиента
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Иван Иванов","phone":"+79998887766","email":"ivan@test.com"}'

# Получить всех клиентов
curl http://localhost:8080/api/customers

# Создать заказ для клиента (id=1)
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"description":"Замена масла","status":"NEW","customerId":1}'
```
---
## Скриншоты рабочего процесса

### Создать клиента
![Create Customer](workflow_screenshots/create_customer.png)
![](workflow_screenshots/create_customer_result.png)

### Список клиентов
![List Customers](workflow_screenshots/get_customers.png)

### Создать заказ
![Create Order](workflow_screenshots/create_order.png)
![](workflow_screenshots/create_order_result.png)

### Список заказов
![List Orders](workflow_screenshots/get_orders.png)

### Удалить заказ
![Delete Order](workflow_screenshots/delete_order.png)
![](workflow_screenshots/get_orders_after_delete.png)
---

## Дальнейшие развитие

* **Аутентификация и роли** (`ADMIN`, `MANAGER`).
* **Расширение модели заказов** (сроки выполнения, история изменения статусов).
* **Фронтенд-интерфейс** (React/Angular) для менеджеров.
* **Уведомления клиентам** (e-mail/Telegram).
* **CI/CD** (GitHub Actions).
* **Продвинутое логирование** (уровни `INFO/DEBUG/ERROR`, вывод в файл, трассировка запросов).

