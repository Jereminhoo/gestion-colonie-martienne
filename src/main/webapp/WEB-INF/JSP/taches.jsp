<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <title>Mes Tâches</title>
  <style>
    body { font-family: 'Segoe UI', sans-serif; background-color: #f4f6f9; margin: 0; }
    .container { width: 90%; margin: 20px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; }

    /* Formulaire Chef */
    .chef-box { background: #fff8e1; border: 1px solid #f1c40f; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
    .chef-box h3 { margin-top: 0; color: #d35400; font-size: 1.1em; }

    input, select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; margin-right: 5px; margin-bottom: 5px; }

    button { padding: 6px 12px; cursor: pointer; border: none; border-radius: 4px; color: white; font-weight: bold; transition: opacity 0.2s; }
    button:hover { opacity: 0.9; }

    .btn-add { background-color: #e67e22; } /* Orange Mars */
    .btn-validate { background-color: #27ae60; } /* Vert */
    .btn-del { background-color: #c0392b; font-size: 0.8em; } /* Rouge */

    /* Tableau */
    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; vertical-align: middle; }
    th { background-color: #ecf0f1; color: #333; }

    /* Badges Statut */
    .badge { padding: 5px 10px; border-radius: 12px; font-size: 0.8em; font-weight: bold; color: white; display: inline-block; }
    .bg-todo { background-color: #e74c3c; } /* Rouge : A faire */
    .bg-done { background-color: #95a5a6; text-decoration: line-through; } /* Gris : Fini */

    .error-msg { color: #c0392b; background: #fadbd8; padding: 10px; border-radius: 4px; margin-bottom: 10px; border: 1px solid #f5b7b1; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/JSP/menu.jsp" />

<div class="container">
  <h1>⚡ Gestion des Tâches</h1>

  <c:if test="${not empty erreur}">
    <div class="error-msg">⚠️ ${erreur}</div>
  </c:if>

  <c:if test="${sessionScope.user.role == 'CHEF'}">
    <div class="chef-box">
      <h3>📢 Assigner une nouvelle tâche</h3>
      <form action="taches" method="post">
        <input type="text" name="titre" placeholder="Titre de la tâche" style="width: 250px;" required>
        <input type="text" name="description" placeholder="Description courte" style="width: 300px;">

        <select name="missionId" required>
          <option value="" disabled selected> Choisir une Mission </option>
          <c:forEach items="${missions}" var="m">
            <option value="${m.id}">${m.titre}</option>
          </c:forEach>
        </select>

        <select name="membreId" required>
          <option value="" disabled selected> Assigner à </option>
          <c:forEach items="${membres}" var="u">
            <option value="${u.id}">${u.login} (${u.specialite})</option>
          </c:forEach>
        </select>

        <button type="submit" class="btn-add">Assigner</button>
      </form>
    </div>
  </c:if>

  <table>
    <thead>
    <tr>
      <th>Tâche</th>
      <th>Mission liée</th>
      <th>Assigné à</th>
      <th>Statut</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${taches}" var="t">
      <tr>
        <td>
          <strong>${t.titre}</strong><br>
          <small style="color:#7f8c8d;">${t.description}</small>
        </td>

        <td>${t.mission.titre}</td>

        <td>
          <c:if test="${t.membreAssigne.id == sessionScope.user.id}">
            <span style="font-weight:bold; color:#2980b9;">MOI</span>
          </c:if>
          <c:if test="${t.membreAssigne.id != sessionScope.user.id}">
            ${t.membreAssigne.login}
          </c:if>
        </td>

        <td>
          <c:choose>
            <c:when test="${t.statut == 'TERMINE'}">
              <span class="badge bg-done">TERMINE</span>
            </c:when>
            <c:otherwise>
              <span class="badge bg-todo">A FAIRE</span>
            </c:otherwise>
          </c:choose>
        </td>

        <td>
          <c:if test="${t.statut != 'TERMINE'}">
            <form action="taches" method="post" style="display:inline;">
              <input type="hidden" name="action" value="valider">
              <input type="hidden" name="id" value="${t.id}">
              <button class="btn-validate">✅ Valider</button>
            </form>
          </c:if>

          <c:if test="${sessionScope.user.role == 'CHEF'}">
            <form action="taches" method="post" onsubmit="return confirm('Supprimer cette tâche ?');" style="display:inline;">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="id" value="${t.id}">
              <button class="btn-del">🗑</button>
            </form>
          </c:if>
        </td>

      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>