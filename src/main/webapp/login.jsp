<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Identification - Base martienne</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #2c3e50;
            display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;
        }
        .login-box {
            background: white; padding: 30px; border-radius: 5px; width: 300px; text-align: center;
        }
        h2 { color: #333; }
        input {
            width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; box-sizing: border-box;
        }
        button {
            width: 100%; padding: 10px; background-color: #e67e22; color: white; border: none; cursor: pointer; margin-top: 10px;
        }
        button:hover { background-color: #d35400; }
        .error { color: red; margin-bottom: 10px; font-size: 0.9em; }
    </style>
</head>
<body>

<div class="login-box">
    <h2>Accès Réservé</h2>

    <c:if test="${not empty erreur}">
        <div class="error">${erreur}</div>
    </c:if>

    <form action="auth" method="post">
        <input type="text" name="login" placeholder="Identifiant" required>
        <input type="password" name="password" placeholder="Mot de passe" required>
        <button type="submit">Connexion</button>
    </form>
</div>

</body>
</html>