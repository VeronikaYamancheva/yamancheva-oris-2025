# Домашняя работа от 15.04

## CRUD-репозиторий на Spring Boot 3
### Описание проекта

Проект предоставляет REST API для выполнения CRUD-операций над AppointmentEntity(запись пользователя к врачу). В качестве используемыых сущностей взяты упрощенные модели из моей семестровой работы. 

### Ссылка на видео:

https://youtu.be/PXiapsYp4jc

### Сделано:

#### Контроллер управления записями администратора (/admin/appointments)

- `GET /admin/appointments` - Получение списка всех записей

- `GET /admin/appointments/by_client_email` - Получение записей по email клиента

- `GET /admin/appointments/{appointmentId}` - Получение детальной информации о записи

- `GET /admin/appointments/date_range` - Получение записей за указанный период

- `POST /admin/appointments/new` - Создание новой записи

- `PATCH /admin/appointments/{appointmentId}` - Обновление даты и времени записи

- `DELETE /admin/appointments/{appointmentId}` - Удаление записи

#### Репозитории и операции с данными

##### AppointmentRepository (JPA репозиторий)

Кастомные методы:

- findByClient_User_Email(String email)
  - Реализация: custom name method

- deleteByAppointmentId(UUID appointmentId)
  - Реализация: custom name method

- updateAppointmentDateTime(UUID, LocalDate, LocalTime)
  - Реализация: Нативный SQL-запрос

- findDetailedInfoById(UUID appointmentId)
  - Реализация: JPQL-запрос

##### AppointmentRepositoryCustom

- findAppointmentDetailedInfoByDateRange(LocalDate, LocalDate)
  - Реализация: Criteria API


### Тесты:

Протестированы:
- AdminAppointmentsManagementController
- CustomExceptionHandler
- AppointmentRepositoryCustomImpl
- AdminAppointmentManagementServiceImpl

Процент покрытия:
- По классам: 61% (8/13)
- По методам: 71% (20/28)
- По строкам: 68% (83/122)