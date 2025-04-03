# Домашняя работа от 28.03

## Описание

- CRUD на Spring Framework 6.2.1 для сообщений.
- Контроллер, который маппится на `/messages` и обрабатывает GET, POST, DELETE, PATCH запросы
- Добавлен JPA-репозиторий, mapstruct-маппер, MessageService (как уровень абстракции между контроллером и репозиторием)
- Простое дерево наследований для кастомных exception + handler для исключения MessageNotFoundException

## Схема базы данных (миграция)
```sql
CREATE TABLE message
(

    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    author      VARCHAR(255)             NOT NULL,
    content     TEXT                     NOT NULL,
    sent_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    last_update TIMESTAMP WITH TIME ZONE NOT NULL
);

```