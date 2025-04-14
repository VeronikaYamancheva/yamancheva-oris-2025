<#ftl encoding="UTF-8">

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ошибка</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="error-container">
    <h2>${errorMessage} (Статус: ${errorStatus})</h2>
    <p><strong>URL:</strong> ${errorUrl}</p>
    <p><strong>Время:</strong> ${errorTimestamp}</p>

    <h3>Ошибки в полях:</h3>
    <ul>
        <#list errors as error>
            <li>${error}</li>
        </#list>
    </ul>
    <a class="back-link" href="/task/create">Назад к форме</a>
</div>
</body>
</html>
