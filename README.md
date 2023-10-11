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



## Запуск проекта с помощью Docker-compose

#### 1. Клонируйте репозиторий:

git clone https://github.com/IyaLobach/ApplicationProcessorDocker.git

#### 2. Убедитесь в том, что у вас установлен Docker командой:
docker

#### 3. Запустите docker-compose командой, перейдя в нужную директорию:

docker-compose up

## Просмотр базы данных

В проекте используется база данных postgreSQL, где предварительно будут созданы все необходимые таблицы и записи в них.

#### 1. Таблицы можно посмотреть, передя по адресу:

https://localhost:5050 

#### 2. Введите логин admin@admin.com и пароль root

#### 3. Создайте сервер с любым именем и выполните следующее подключение: 

#### 4. Теперь Вам доступны созданные таблицы и записи.

### Связи между таблицами
#### ER-диаграмма, отражающая связь таблиц имеет вид:



## Тестирование сервиса

#### Для работы с сервисом можно использовать, например, Postman. 

Для обращения необходимо указать адрес:

https://localhost:8082/api

#### Все пользователи, взаимодействующие с сервисом должны авторизоваться по адресу

https://localhost:8082/api/login

#### В базу данных предваритель были внесены данные о следующих пользователях:
1. login: iya@mail.ru password: 1234 ROLE_USER
2. login: petr@mail.ru password: 4567 ROLE_USER
3. login: anita@mail.ru password: 8910 ROLE_USER
4. login: ivan@mail.ru password: 1357 ROLE_OPERATOR
5. login: ann@mail.ru password: 0246 ROLE_OPERATOR
6. login: povh@mail.ru password: 3579 ROLE_ADMIN
7. login: gera@mail.ru password: 2468 ROLE_ADMIN

#### Для разлогинивания необходимо перейти по адресу:
https://localhost:8082/api/logout

## Доступные операции
#### Пользователь (ROLE_USER)
1. Для просмотра заявков, созданных пользователем необходимо отправить

GET https://localhost:8082/api/users/applications

В качестве обязятельного параметра указать page, который указвает номер страницы для пагинации


2. Для создания новой заявки необходимо отправить

POST https://localhost:8082/api/users/applications

В качестве тела запроса отправить JSON вида:

{

	"text": "your_text"
	
}

3. Для отправки заявки необходимо отправить

PATCH https://localhost:8082/api/users/applications/{applicationId}/submit

Тело запроса оставить пустым

4. Для редактирования заявки необходимо отправить

PATCH https://localhost:8082/api/users/applications/{applicationId}/edit

В качестве тела запроса отправить JSON вида:

{

	"text": "your_text"

}

#### Оператор (ROLE_OPERATOR)
1. Для просмотра заявков, созданных пользователем необходимо отправить

GET https://localhost:8082/api/operators/applications

В качестве обязятельного параметра указать page, который указвает номер страницы для пагинации.

Помимо этого можно указать необязательные параметры name и указать имя пользователя и/или sort, которое принимает значение asc или desc для сортироваки заявок по времени по возрастанию и убыванию соответственно

2. Для принятия заявки необходимо отправить

PATCH https://localhost:8082/api/operators/applications/{applicationId}/accept

Тело запроса оставить пустым.

3. Для отклонения заявки необходимо отправить

PATCH https://localhost:8082/api/operators/applications/{applicationId}/reject

Тело запроса оставить пустым.

#### Администратор (ROLE_ADMIN)
1. Для просмотра пользователей необходимо отправить

GET https://localhost:8082/api/admins/users

В качестве необязятельного параметра указать name для поиска заявок у конкретного пользователя

2. Для назначения прав необходимо отправить

PATCH https://localhost:8082/api/admins/users/{userId}/appoint

Тело запроса оставить пустым.