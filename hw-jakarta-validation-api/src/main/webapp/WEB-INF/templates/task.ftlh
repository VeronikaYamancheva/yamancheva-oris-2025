<#ftl encoding="UTF-8">

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Новая задача</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<form action="/task/create" method="post">
    <h2>Новая задача</h2>

    <label>Название:</label>
    <input type="text" name="title" value="${taskDto.title!}">

    <label>Описание:</label>
    <textarea name="description">${taskDto.description!}</textarea>

    <label>Приоритет:</label>
    <input type="number" name="priority" value="${taskDto.priority!}">

    <label>Дата завершения:</label>
    <input type="date" name="endDate" value="${taskDto.endDate!}">

    <label>Бюджет:</label>
    <input type="number" name="budget" value="${taskDto.budget!}" step="0.01">

    <label>Имя сотрудника:</label>
    <input type="text" name="name" value="${taskDto.name!}">

    <label>Должность:</label>
    <input type="text" name="position" value="${taskDto.position!}">

    <label>Email:</label>
    <input type="email" name="email" value="${taskDto.email!}">

    <label>Телефон:</label>
    <input type="text" name="phone" value="${taskDto.phone!}">

    <button type="submit">Сохранить</button>
</form>

</body>
</html>
