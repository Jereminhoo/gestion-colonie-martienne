<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Mon Profil - Colonie</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f6f9; margin: 0; }
        .container { width: 50%; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; text-align: center; }

        label { display: block; margin-top: 15px; font-weight: bold; color: #555; }
        input, select { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }

        /* Champ Login grisé */
        input[readonly] { background-color: #e9ecef; color: #6c757d; cursor: not-allowed; }

        button { width: 100%; margin-top: 20px; padding: 12px; background-color: #2980b9; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; transition: background 0.3s; }
        button:hover { background-color: #1abc9c; }

        .msg-success { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #c3e6cb; text-align: center; }
        .msg-error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #f5c6cb; text-align: center; }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/JSP/menu.jsp" />

<div class="container">
    <h1>👤 Mon Profil</h1>

    <c:if test="${not empty succes}">
        <div class="msg-success">✅ ${succes}</div>
    </c:if>
    <c:if test="${not empty erreur}">
        <div class="msg-error">⚠️ ${erreur}</div>
    </c:if>

    <form action="profil" method="post">

        <label>Identifiant (Login) :</label>
        <input type="text" value="${sessionScope.user.login}" readonly title="Impossible de changer le login">

        <label>Grade :</label>
        <input type="text" value="${sessionScope.user.role}" readonly>

        <label>Nom :</label>
        <input type="text" name="nom" value="${sessionScope.user.nom}" required>

        <label>Prénom :</label>
        <input type="text" name="prenom" value="${sessionScope.user.prenom}" required>

        <label>Spécialité :</label>
        <select name="specialite" required>
            <option value="" disabled>Choisir une spécialité</option>

            <option value="PILOTE" ${sessionScope.user.specialite == 'PILOTE' ? 'selected' : ''}>Pilote de Vaisseau</option>

            <option value="CO-PILOTE" ${sessionScope.user.specialite == 'CO-PILOTE' ? 'selected' : ''}>Co-Pilote</option>
            <option value="INGENIEUR" ${sessionScope.user.specialite == 'INGENIEUR' ? 'selected' : ''}>Ingénieur en Chef</option>
            <option value="MECANICIEN" ${sessionScope.user.specialite == 'MECANICIEN' ? 'selected' : ''}>Mécanicien Rover</option>

            <option value="BOTANISTE" ${sessionScope.user.specialite == 'BOTANISTE' ? 'selected' : ''}>Botaniste (Culture de patates)</option>
            <option value="MEDECIN" ${sessionScope.user.specialite == 'MEDECIN' ? 'selected' : ''}>Médecin de bord</option>
            <option value="XENOBIOLOGISTE" ${sessionScope.user.specialite == 'XENOBIOLOGISTE' ? 'selected' : ''}>Xénobiologiste (Spécialiste Aliens)</option>
            <option value="GEOLOGUE" ${sessionScope.user.specialite == 'GEOLOGUE' ? 'selected' : ''}>Géologue</option>

            <option value="SECURITE" ${sessionScope.user.specialite == 'SECURITE' ? 'selected' : ''}>Chef de la Sécurité</option>
            <option value="INFORMATICIEN" ${sessionScope.user.specialite == 'INFORMATICIEN' ? 'selected' : ''}>Hacker / Admin Sys</option>
            <option value="CUISINIER" ${sessionScope.user.specialite == 'CUISINIER' ? 'selected' : ''}>Chef Cuisinier (Ration master)</option>
            <option value="OUVRIER" ${sessionScope.user.specialite == 'OUVRIER' ? 'selected' : ''}>Ouvrier Maintenance</option>
        </select>

        <label>Nouveau mot de passe :</label>
        <input type="password" name="password" placeholder="Laisser vide pour ne pas changer">

        <button type="submit">Enregistrer les modifications</button>
    </form>
</div>

</body>
</html>