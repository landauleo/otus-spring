### Описание:
Использовать WebFlux

### Цель:
Разрабатывать Responsive и Resilent приложения на реактивном стеке Spring c помощью Spring Web Flux и Reactive Spring Data Repositories

### Требования:
1. За основу для выполнения работы можно взять ДЗ с Ajax + REST (классическое веб-приложение для этого лучше не использовать).
   
2. Но можно выбрать другую доменную модель (не библиотеку).
   
3. Необходимо использовать Reactive Spring Data Repositories.
   
4. В качестве БД лучше использовать MongoDB или Redis. Использовать PostgreSQL и экспериментальную R2DBC не рекомендуется.
   
5. RxJava vs Project Reactor - на Ваш вкус.
   
6. Вместо классического Spring MVC и embedded Web-сервера использовать WebFlux.
   
7. Опционально: реализовать на Functional Endpoints 

<br>

####Рекомендации:
Старайтесь избавиться от лишних архитектурных слоёв. Самый простой вариант - весь flow в контроллере.

####Запуск webpack:
````npx webpack serve --config webpack.config.js````


````npm install html-loader --save-dev````


####Запуск Docker-compose:
- просто не забыть запустить
