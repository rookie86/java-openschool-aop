Задание 1

1. Создать простой RESTful сервис для управления задачами:

    Task(id, title, description,userId)

   1. POST /tasks — создание новой задачи.

   2. GET /tasks/{id} — получение задачи по ID.

   3. PUT /tasks/{id} — обновление задачи.

   4. DELETE /tasks/{id} — удаление задачи.

   5. GET /tasks — получение списка всех задач.

2. Релизуйте класс аспект, со следующими advice:

   1. Before

   2. AfterThrowing

   3. AfterReturning

   4. Around

В приложении должна быть реализована логика на каждый advice - свой метод, можно сделать больше, использовать несколько advice на отдельные методы, но меньше нельзя.

Задание 2

1. Использовать docker-compose для установки Kafka в Docker (пример демонстрируется на уроке)

   1.1 Установить необходимые инструменты для работы с Kafka. Big Data Tools, Offset Explorer и др. на свое усмотрение и возможности, результатом должна быть возможность "заглянуть" в кафку

2. Создать тестовый topic, установленными средствами

3. Отправить в топик тестовые сообщения

4. Убедиться в их наличии

5. Сконфигурировать Kafka, Producer, Consumer в вашем сервисе рабты с Task.

6. Продюсер пишет в топик id и новый статус task у которых он изменился, при соответствующем входящем запросе (обновления task).

7. Консьюмер слушает этот топик, читает оттуда сообщения, и отправляет в NotificationService (условно сервис заглушка, для имитации отправки уведомления, можно просто логировать)

8. Дополнительно: использовать spring-boot-starter-mail, и в NotificationService реализовать отправку email *

Задание 3

Цель: Создать Spring Boot стартер, который будет добавлять функциональность логирования HTTP-запросов и ответов в приложении.

• Логгирование реализуйте через Aspect (перенесите то что есть в стартер, старый аспект выпилить)

• Добавьте возможность настройки логгирования через параметры в application.properties или application.yml.

• Например, разрешите пользователям включать или отключать логирование, а также выбирать уровень детализации логов.

Задание реализуется в отдельном репозитории https://github.com/rookie86/java-openschool-starter