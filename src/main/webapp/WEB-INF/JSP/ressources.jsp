<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Inventaire - Colonie Martienne</title>
    <style>
        body { font-family: sans-serif; padding: 20px; background-color: #f0f2f5; }
        table { border-collapse: collapse; width: 100%; background: white; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #e67e22; color: white; } /* Orange Mars */
        .container { display: flex; gap: 20px; }
        .box { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1, h2 { color: #d35400; }
        input, button { display: block; margin-bottom: 10px; padding: 5px; width: 100%; }
        button { background-color: #d35400; color: white; border: none; padding: 10px; cursor: pointer; }
        button:hover { background-color: #a04000; }

        /* Classe pour l'alerte stock bas */
        .alerte-stock { background-color: #fadbd8; color: #922b21; font-weight: bold; }
    </style>
</head>
<body>

<h1>📦 Gestion des Ressources</h1>

<c:if test="${not empty erreur}">
    <div style="background-color: #ffcccc; color: red; padding: 10px; margin-bottom: 15px; border-radius: 5px;">
        ⚠️ ${erreur}
    </div>
</c:if>

<div class="container">

    <div class="box" style="flex: 1;">
        <h2>Nouvelle Ressource</h2>
        <form action="ressources" method="post">
            <label>Nom :</label>
            <input type="text" name="nom" placeholder="ex: Oxygène" required>

            <label>Quantité :</label>
            <input type="number" step="0.1" name="quantite" placeholder="0.0" required>

            <label>Unité :</label>
            <input type="text" name="unite" placeholder="ex: Litres, Kg" required>

            <label>Seuil d'alerte :</label>
            <input type="number" step="0.1" name="seuilAlerte" placeholder="ex: 100" required>

            <button type="submit">Ajouter au stock</button>
        </form>
    </div>

    <div class="box" style="flex: 2;">
        <h2>Inventaire Actuel</h2>
        <table>
            <thead>
            <tr>
                <th>Ressource</th>
                <th>Quantité</th>
                <th>Seuil Alerte</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="r" items="${ressources}">
                <tr class="${r.quantite < r.seuilAlerte ? 'alerte-stock' : ''}">

                    <td>${r.nom}</td>
                    <td>${r.quantite} ${r.unite}</td>
                    <td>${r.seuilAlerte}</td>

                    <td>
                        <form action="ressources" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="${r.id}"/>
                            <button type="submit" style="background-color: #c0392b; width: auto; padding: 5px 10px;"
                                    onclick="return confirm('Jeter cette ressource ?')">
                                X
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