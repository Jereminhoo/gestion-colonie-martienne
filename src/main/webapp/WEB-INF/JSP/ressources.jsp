<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ressources - Colonie</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f6f9; margin: 0; }
        .container { width: 90%; margin: 20px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; }

        .chef-box { background: #e8f6f3; border: 1px solid #1abc9c; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
        .chef-box h3 { margin-top: 0; color: #16a085; font-size: 1.1em; }
        input { padding: 8px; border: 1px solid #ccc; border-radius: 4px; margin-right: 5px; }

        button { padding: 8px 15px; cursor: pointer; border: none; border-radius: 4px; color: white; font-weight: bold; transition: opacity 0.2s; }
        button:hover { opacity: 0.9; }

        .btn-add { background-color: #1abc9c; }

        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; vertical-align: middle; }
        th { background-color: #ecf0f1; color: #333; }

        .status-ok { color: #27ae60; font-weight: bold; background: #eafaf1; padding: 4px 8px; border-radius: 4px; }
        .status-danger { color: #c0392b; font-weight: bold; background: #fdedec; padding: 4px 8px; border-radius: 4px; }

        .stock-control { display: flex; align-items: center; gap: 5px; }
        .input-mini { width: 70px; padding: 6px; text-align: center; }

        .btn-mini { padding: 6px 12px; font-size: 0.9em; margin: 0; }
        .btn-minus { background-color: #e74c3c; }
        .btn-plus { background-color: #27ae60; }
        .btn-del { background-color: #95a5a6; font-size: 0.8em; padding: 6px 10px; }
        .btn-del:hover { background-color: #7f8c8d; }

        .error-msg { color: #c0392b; background: #fadbd8; padding: 10px; border-radius: 4px; margin-bottom: 10px; border: 1px solid #f5b7b1; }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/JSP/menu.jsp" />

<div class="container">
    <h1>Gestion des Ressources</h1>

    <c:if test="${not empty erreur}">
        <div class="error-msg">Erreur : ${erreur}</div>
    </c:if>

    <c:if test="${sessionScope.user.role == 'CHEF'}">
        <div class="chef-box">
            <h3>Ajouter une ressource</h3>
            <form action="ressources" method="post">
                <input type="text" name="nom" placeholder="Nom (ex: Eau)" required>
                <input type="number" step="any" name="quantite" placeholder="Quantité" required>
                <input type="text" name="unite" placeholder="Unité (ex: Litres)" size="10" required>
                <input type="number" step="any" name="seuilAlerte" placeholder="Seuil Alerte (ex: 50)" required>
                <button type="submit" class="btn-add">Ajouter</button>
            </form>
        </div>
    </c:if>

    <table>
        <thead>
        <tr>
            <th>Ressource</th>
            <th>Quantité</th>
            <th>Seuil Alerte</th> <th>État</th>
            <c:if test="${sessionScope.user.role == 'CHEF'}">
                <th>Ajustement Stock</th>
                <th>Supprimer</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ressources}" var="r">

            <tr class="${r.quantite < r.seuilAlerte ? 'alert-row' : ''}">

                <td style="font-weight: bold;">${r.nom}</td>

                <td>${r.quantite} ${r.unite}</td>

                <td>${r.seuilAlerte} ${r.unite}</td>

                <td>
                    <c:choose>
                        <c:when test="${r.quantite < r.seuilAlerte}">
                            <span class="status-danger">CRITIQUE</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-ok">OK</span>
                        </c:otherwise>
                    </c:choose>
                </td>

                <c:if test="${sessionScope.user.role == 'CHEF'}">
                    <td>
                        <form action="ressources" method="post" class="stock-control">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${r.id}">

                            <input type="number" step="any" name="montant" class="input-mini" placeholder="Qté" required>

                            <button type="submit" name="mode" value="sub" class="btn-mini btn-minus" title="Retirer">-</button>
                            <button type="submit" name="mode" value="add" class="btn-mini btn-plus" title="Ajouter">+</button>
                        </form>
                    </td>

                    <td>
                        <form action="ressources" method="post" onsubmit="return confirm('Supprimer définitivement ?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${r.id}">
                            <button class="btn-mini btn-del">X</button>
                        </form>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>