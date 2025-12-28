<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <title>Planning Missions</title>
  <style>
    body { font-family: 'Segoe UI', sans-serif; background-color: #f4f6f9; margin: 0; }
    .container { width: 90%; margin: 20px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; }

    .chef-box { background: #eaf2f8; border: 1px solid #3498db; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
    .chef-box h3 { margin-top: 0; color: #2980b9; font-size: 1.1em; }

    input, textarea, select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; margin-right: 5px; margin-bottom: 5px; }
    textarea { width: 100%; height: 60px; resize: vertical; margin-top: 5px; }

    button { padding: 8px 15px; cursor: pointer; border: none; border-radius: 4px; color: white; font-weight: bold; transition: opacity 0.2s; }
    button:hover { opacity: 0.9; }
    .btn-add { background-color: #3498db; }

    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; vertical-align: top; }
    th { background-color: #ecf0f1; color: #333; }

    .badge { padding: 5px 10px; border-radius: 12px; font-size: 0.8em; font-weight: bold; color: white; display: inline-block; }
    .bg-prevue { background-color: #f39c12; }
    .bg-encours { background-color: #27ae60; }
    .bg-terminee { background-color: #7f8c8d; }

    .btn-del { background-color: #c0392b; font-size: 0.8em; padding: 5px 10px; }

    .btn-status { font-size: 0.75em; padding: 3px 6px; background: #95a5a6; margin-top: 5px; }

    .error-msg { color: #c0392b; background: #fadbd8; padding: 10px; border-radius: 4px; margin-bottom: 10px; border: 1px solid #f5b7b1; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/JSP/menu.jsp" />

<div class="container">
  <h1>🪐 Planning des Missions</h1>

  <c:if test="${not empty erreur}">
    <div class="error-msg">Erreur : ${erreur}</div>
  </c:if>

  <c:if test="${sessionScope.user.role == 'CHEF'}">
    <div class="chef-box">
      <h3>Nouvelle Mission</h3>
      <form action="missions" method="post">
        <input type="text" name="titre" placeholder="Titre de la mission" style="width: 300px;" required>

        <label>Début :</label>
        <input type="date" name="dateDebut" required>

        <label>Fin (Optionnel) :</label>
        <input type="date" name="dateFin">

        <textarea name="description" placeholder="Description de l'objectif..."></textarea>

        <br>
        <button type="submit" class="btn-add">Planifier la mission</button>
      </form>
    </div>
  </c:if>

  <table>
    <thead>
    <tr>
      <th style="width: 15%;">Dates</th>
      <th style="width: 20%;">Mission</th>
      <th>Description</th>
      <th style="width: 10%;">Statut</th>
      <c:if test="${sessionScope.user.role == 'CHEF'}">
        <th style="width: 10%;">Actions</th>
      </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${missions}" var="m">
      <tr>
        <td>
          <strong>${m.dateDebut}</strong>
          <c:if test="${not empty m.dateFin}">
            <br>au ${m.dateFin}
          </c:if>
        </td>

        <td><strong>${m.titre}</strong></td>

        <td>${m.description}</td>

        <td>
          <c:choose>
            <c:when test="${m.statut == 'EN_COURS'}"> <span class="badge bg-encours">EN COURS</span> </c:when>
            <c:when test="${m.statut == 'TERMINEE'}"> <span class="badge bg-terminee">TERMINEE</span> </c:when>
            <c:otherwise> <span class="badge bg-prevue">PREVUE</span> </c:otherwise>
          </c:choose>
        </td>

        <c:if test="${sessionScope.user.role == 'CHEF'}">
          <td>
            <div style="display:flex; flex-direction:column; gap:2px; margin-bottom:10px;">
              <form action="missions" method="post">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="id" value="${m.id}">
                <input type="hidden" name="statut" value="EN_COURS">
                <button class="btn-status" style="background:#27ae60;">▶ Lancer</button>
              </form>

              <form action="missions" method="post">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="id" value="${m.id}">
                <input type="hidden" name="statut" value="TERMINEE">
                <button class="btn-status" style="background:#34495e;">🏁 Terminer</button>
              </form>
            </div>

            <form action="missions" method="post" onsubmit="return confirm('Annuler cette mission ?');">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="id" value="${m.id}">
              <button class="btn-del">Supprimer</button>
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