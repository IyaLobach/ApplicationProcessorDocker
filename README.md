# Обработчик пользовательских заявок

## Функции приложения
•	Создать заявку (Заявка помимо прочих системных полей состоит из статуса и текстового обращения пользователя)

•	Отправить заявку оператору на рассмотрение

•	Просмотреть список заявок с возможностью сортировки по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов

•	Посмотреть заявку

•	Принять заявку

•	Отклонить заявку

•	Просмотреть список пользователей

•	Назначить права оператора

### В системе предусмотрены 3 роли:

•	Пользователь

•	Оператор

•	Администратор

У пользователя системы может быть одновременно несколько ролей, например, «Оператор» и «Администратор». 

### У заявки пользователя предусмотрено 4 состояния:
•	черновик

•	отправлено

•	принято

•	отклонено

### Пользователь может 
•	создавать заявки

•	просматривать созданные им заявки с возможностью сортировки по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов

•	редактировать созданные им заявки в статусе «черновик»

•	отправлять заявки на рассмотрение оператору.

### Пользователь НЕ может:
•	редактировать отправленные на рассмотрение заявки

•	видеть заявки других пользователей

•	принимать заявки

•	отклонять заявки

•	назначать права

•	смотреть список пользователей

### Оператор может
•	Просматривать все отправленные на рассмотрение  заявки с возможностью сортировки по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов

•	Просматривать отправленные заявки только конкретного пользователя по его имени/части имени (у пользователя, соотетственно, должно быть поле name) с возможностью сортировки по дате создания в оба направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5 элементов

•	Принимать заявки

•	Отклонять заявки

### Оператор НЕ может
•	создавать заявки

•	просматривать заявки в статусе отличном от «отправлено»

•	редактировать заявки

•	назначать права

### Администратор может
•	смотреть список пользователей

•	искать конкретного пользователя по его имени/части имени

•	назначать пользователям права оператора

### Администратор НЕ может
•	создавать заявки

•	просматривать заявки

•	редактировать заявки 

•	принимать заявки

•	отклонять заявки

Создание пользователей и ролей не предусмотрено в этой системе. Подразумевается, что данные об учетных записях пользователей и роли уже есть в БД.

В случае просмотра заявки оператором текст заявки выводить со знаком <-> после каждого символа. Пример: Пользователь отправил на рассмотрение заявку с текстом «Мне нужна помощь», а оператор на экране видит текст в формате «М-н-е- -н-у-ж-н-а- -п-о-м-о-щ-ь».

## Используемые технологии

1. Архитектра REST

2. Spring Boot

3. Spring Security

4. Hibernate

5. PostgreSQL

6. Liquibase


## Запуск проекта с помощью Docker-compose

#### 1. Клонируйте репозиторий:

`git clone https://github.com/IyaLobach/ApplicationProcessorDocker.git`

#### 2. Убедитесь в том, что у вас установлен Docker командой:

`docker`

#### 3. Запустите docker-compose командой, перейдя в нужную директорию:

`docker-compose up`

## Просмотр базы данных

В проекте используется база данных postgreSQL, где предварительно создаются все необходимые таблицы и записи в них.

#### Таблицы можно посмотреть, перейдя по адресу:

https://localhost:5050 

Введите логин admin@admin.com и пароль root

![pgAdmin login](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/pglogin.png)

Создайте сервер с любым именем
![pgAdmin new Server](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/pgnewServer.png) 

В качестве имени укажите service-db, имя postgres и пароль 12345678

![pgAdmin new Server](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/pgconnection.png) 

Теперь Вам доступны созданные таблицы и записи

![Tables](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/pgtables.png)



### Связи между таблицами

ER-диаграмма, отражающая связь таблиц имеет вид:

![Database ER](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/er.png)

## Тестирование сервиса

Для работы с сервисом можно использовать, например, Postman. 

Обращение к сервису осуществляется по адресу:

https://localhost:8081/api

#### Все пользователи, взаимодействующие с сервисом должны авторизоваться по адресу

https://localhost:8081/api/login

#### В базу данных предварительно были внесены данные о следующих пользователях:
1. login: iya@mail.ru password: 1234 ROLE_USER
2. login: petr@mail.ru password: 4567 ROLE_USER
3. login: anita@mail.ru password: 8910 ROLE_USER
4. login: ivan@mail.ru password: 1357 ROLE_OPERATOR
5. login: ann@mail.ru password: 0246 ROLE_OPERATOR
6. login: povh@mail.ru password: 3579 ROLE_ADMIN
7. login: gera@mail.ru password: 2468 ROLE_ADMIN

#### Для разлогинивания необходимо перейти по адресу:
https://localhost:8082/api/logout

### Доступные операции

#### Пользователь (ROLE_USER)

1. Для просмотра заявков, созданных пользователем необходимо отправить запрос

GET https://localhost:8081/api/users/applications

В качестве обязательного параметра указать page в качестве номера страницы для пагинации

![Applications](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/applications.png)

2. Для создания новой заявки необходимо отправить запрос

POST https://localhost:8081/api/users/applications

В качестве тела запроса отправить JSON вида:

`{

	"text": "your_text"
	
}`

![Create](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/applicationCreate.png)

3. Для отправки заявки необходимо отправить запрос

PATCH https://localhost:8081/api/users/applications/{applicationId}/submit

Тело запроса оставить пустым

![Submit](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/applicationSubmit.png)

4. Для редактирования заявки необходимо отправить запрос

PATCH https://localhost:8081/api/users/applications/{applicationId}/edit

В качестве тела запроса отправить JSON вида:

`{

	"text": "your_text"

}`

![Edit](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/applicationEdit.png)

#### Оператор (ROLE_OPERATOR)

1. Для просмотра заявков, созданных пользователем необходимо отправить запрос

GET https://localhost:8081/api/operators/applications

В качестве обязятельного параметра указать page в качестве номера страницы для пагинации.

Помимо этого можно указать необязательные параметры name в качестве имени пользователя и/или sort, которое принимает значение asc или desc для сортироваки заявок по времени по возрастанию и убыванию соответственно.

![Applications](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/operatorShow.png)

2. Для принятия заявки необходимо отправить запрос

PATCH https://localhost:8081/api/operators/applications/{applicationId}/accept

Тело запроса оставить пустым.

![Accept](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/operatorAccept.png)

3. Для отклонения заявки необходимо отправить запрос

PATCH https://localhost:8081/api/operators/applications/{applicationId}/reject

Тело запроса оставить пустым.

![Reject](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/operatorReject.png)

#### Администратор (ROLE_ADMIN)

1. Для просмотра пользователей необходимо отправить запрос

GET https://localhost:8081/api/admins/users

В качестве необязятельного параметра указать name для поиска заявок у конкретного пользователя

![Show](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/adminShow.png)



2. Для назначения прав необходимо отправить запрос

PATCH https://localhost:8081/api/admins/users/{userId}/appoint

Тело запроса оставить пустым.

![Appoint](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/adminAppoint.png)

![Show](https://github.com/IyaLobach/ApplicationProcessorDocker/blob/master/png/adminShow2.png)