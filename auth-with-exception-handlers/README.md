# Домашняя работа от 04.04.25

## Обработка исключений в Spring Boot

### Сделано:

- Реализован проект AuthExample:
  - Классы конфигурации - DatabaseConfig, WebMvcConfig, WebAppInitializer
  - Контроллеры(реализуют предоставленные интерфейсы) - LogoutController, SignInController, SignUpController
  - Классы сервисы
  - Jpa-репозиторий - UserRepository
  - Классы валидаторы для пароля (одна заглавная, одна строчная, одна цифра, спец символ, минимум 8 символов) и email-а(паттерн)
  - Сущность UserEntity, соответствующая таблица в бд представлена ниже

```sql
CREATE TABLE account
(
    uuid              UUID PRIMARY KEY,
    email             VARCHAR(255) NOT NULL,
    hash_password     VARCHAR(255) NOT NULL,
    token             VARCHAR(255),
    confirmation_code VARCHAR(255),
    is_confirmed      BOOLEAN DEFAULT FALSE,
    CONSTRAINT uk_account_email UNIQUE (email)
);
```

#### CustomExceptionHandler

- корректно обрабатывает исключения и отдает ExceptionDto

#### Дерево наследования кастомных исключений

- Все наследуются от ServiceException
  - есть также семейства NotFoundServiceException(404) и ValidationServiceException(400)
  - в случае внутренней ошибки сервиса - InternalServiceException(500)

### Тесты

- тестами покрыты сервисы и валидаторы

Процент покрытия:
- По классам: 64% (20/31)
- По методам: 60% (23/38)
- По строкам: 62% (93/149)