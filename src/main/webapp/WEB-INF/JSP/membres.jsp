<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Import de la JSTL pour faire des boucles (c:forEach) -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion de la Colonie - Membres</title>
    <style>
        /* Un peu de CSS rapide pour que ce soit lisible */
        body { font-family: sans-serif; padding: 20px; background-color: #f0f2f5; }
        table { border-collapse: collapse; width: 100%; background: white; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #2c3e50; color: white; }
        .container { display: flex; gap: 20px; }
        .box { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1, h2 { color: #2c3e50; }
        input, select { display: block; margin-bottom: 10px; padding: 5px; width: 100%; }
        button { background-color: #27ae60; color: white; border: none; padding: 10px 20px; cursor: pointer; }
        button:hover { background-color: #219150; }
    </style>
</head>
<body>

<h1>👨‍🚀 Gestion de l'Équipage Martien</h1>

<div class="container">


    <div class="box" style="flex: 1;">
        <h2>Nouveau Membre</h2>
        <form action="membres" method="post">
            <label>Nom :</label>
            <input type="text" name="nom" required>

            <label>Prénom :</label>
            <input type="text" name="prenom" required>

            <label>Login :</label>
            <input type="text" name="login" required>

            <label>Mot de passe :</label>
            <input type="password" name="password" required>

            <input type="hidden" name="role" value="MEMBRE">

            <label>Spécialité :</label>
            <select name="specialite" required>
                <option value=""> Choisir une spécialité </option>
                <option value="CO-PILOTE">Co-Pilote</option>
                <option value="INGENIEUR">Ingénieur</option>
                <option value="MEDECIN">Médecin</option>
                <option value="MECANICIEN">Mécanicien</option>
                <option value="BOTANISTE">Botaniste</option>
                <option value="SECURITE">Sécurité</option>
            </select>


            <button type="submit">Recruter</button>
        </form>
    </div>


    <div class="box" style="flex: 2;">
        <h2>Effectif actuel</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Login</th>
                <th>Rôle</th>
                <th>Spécialité</th>
            </tr>
            </thead>
            <tbody>
            <!-- Boucle sur la liste 'membres' envoyée par la Servlet -->
            <c:forEach var="m" items="${membres}">
                <tr>
                    <td>${m.id}</td>
                    <td>${m.nom}</td>
                    <td>${m.prenom}</td>
                    <td>${m.login}</td>
                    <td>${m.role}</td>
                    <td>${m.specialite}</td>
                    <td>
                        <form action="membres" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="${m.id}"/>
                            <button type="submit"
                                    onclick="return confirm('Supprimer ce membre ?')">
                                ❌ Supprimer
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

</div>

</body>
</html>