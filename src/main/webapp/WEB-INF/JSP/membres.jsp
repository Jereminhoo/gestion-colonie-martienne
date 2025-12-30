<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Équipage - Colonie</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f6f9; margin: 0; }
        .container { width: 90%; margin: 20px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; }

        /* Formulaire Chef (Visible seulement pour le chef) */
        .chef-box { background: #e8daef; border: 1px solid #8e44ad; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
        .chef-box h3 { margin-top: 0; color: #8e44ad; font-size: 1.1em; }

        input, select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; margin-right: 5px; margin-bottom: 5px; }

        button { padding: 8px 15px; cursor: pointer; border: none; border-radius: 4px; color: white; font-weight: bold; }
        .btn-add { background-color: #8e44ad; } /* Violet */
        .btn-del { background-color: #c0392b; font-size: 0.8em; padding: 5px 10px; }

        /* Tableau */
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #ecf0f1; color: #333; }

        /* Badge Role */
        .role-badge { padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 0.8em; }
        .role-chef { background: #f1c40f; color: #7f6000; }
        .role-membre { background: #bdc3c7; color: #2c3e50; }

        .error-msg { color: #c0392b; background: #fadbd8; padding: 10px; border-radius: 4px; margin-bottom: 10px; border: 1px solid #f5b7b1; }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/JSP/menu.jsp" />

<div class="container">
    <h1> Annuaire de l'Équipage</h1>

    <c:if test="${not empty erreur}">
        <div class="error-msg">⚠️ ${erreur}</div>
    </c:if>

    <c:if test="${sessionScope.user.role == 'CHEF'}">
        <div class="chef-box">
            <h3>➕ Recruter un nouveau membre</h3>
            <form action="membres" method="post">
                <input type="text" name="nom" placeholder="Nom" required>
                <input type="text" name="prenom" placeholder="Prénom" required>
                <input type="text" name="login" placeholder="Identifiant" required>
                <input type="password" name="password" placeholder="Mot de passe" required>

                <select name="specialite" required>
                    <option value="PILOTE">Pilote de Vaisseau</option>
                    <option value="CO-PILOTE">Co-Pilote</option>
                    <option value="INGENIEUR">Ingénieur en Chef</option>
                    <option value="MECANICIEN">Mécanicien Rover</option>

                    <option value="BOTANISTE">Botaniste (Culture de patates)</option>
                    <option value="MEDECIN">Médecin de bord</option>
                    <option value="XENOBIOLOGISTE">Xénobiologiste (Spécialiste Aliens)</option>
                    <option value="GEOLOGUE">Géologue</option>

                    <option value="SECURITE">Chef de la Sécurité</option>
                    <option value="INFORMATICIEN">Hacker / Admin Sys</option>
                    <option value="CUISINIER">Chef Cuisinier (Ration master)</option>
                    <option value="OUVRIER">Ouvrier Maintenance</option>
                </select>
                </select>

                <button type="submit" class="btn-add">Recruter</button>
            </form>
        </div>
    </c:if>

    <table>
        <thead>
        <tr>
            <th>Nom & Prénom</th>
            <th>Spécialité</th>
            <th>Rôle</th>
            <th>Identifiant</th>
            <c:if test="${sessionScope.user.role == 'CHEF'}">
                <th>Action</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${membres}" var="m">
            <tr>
                <td><strong>${m.nom} ${m.prenom}</strong></td>

                <td>${m.specialite}</td>

                <td>
                    <c:if test="${m.role == 'CHEF'}">
                        <span class="role-badge role-chef">⭐ CHEF</span>
                    </c:if>
                    <c:if test="${m.role == 'MEMBRE'}">
                        <span class="role-badge role-membre">Membre</span>
                    </c:if>
                </td>

                <td>${m.login}</td>

                <c:if test="${sessionScope.user.role == 'CHEF'}">
                    <td>
                        <c:if test="${m.role != 'CHEF'}">
                            <form action="membres" method="post" onsubmit="return confirm('Renvoyer ce membre sur Terre ?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${m.id}">
                                <button class="btn-del">Ejecter</button>
                            </form>
                        </c:if>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>