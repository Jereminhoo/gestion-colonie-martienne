<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <title>Gestion des Tâches</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      padding: 20px;
    }

    h1 { color: #00796b; border-bottom: 2px solid #00796b; padding-bottom: 10px; }

    .dashboard {
      display: flex;
      gap: 20px;
      align-items: flex-start;
    }

    .box {
      background: white;
      padding: 20px;
      border: 1px solid #ccc;
    }

    .left-panel { flex: 1; }
    .right-panel { flex: 2; }

    label { display: block; margin-top: 10px; font-weight: bold; }

    input[type=text], select, textarea {
      width: 100%;
      padding: 8px;
      margin-top: 5px;
      border: 1px solid #999;
      box-sizing: border-box;
    }

    /* Bouton Vert Canard */
    button.btn-main {
      width: 100%;
      margin-top: 15px;
      padding: 10px;
      background-color: #00796b;
      color: white;
      border: none;
      cursor: pointer;
      font-size: 15px;
    }
    button.btn-main:hover { background-color: #004d40; }

    /* Tableau */
    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th { text-align: left; padding: 10px; background-color: #eee; border-bottom: 2px solid #ddd; }
    td { padding: 10px; border-bottom: 1px solid #eee; vertical-align: top; }

    .st-afaire { color: #555; font-weight: bold; }
    .st-encours { color: #00796b; font-weight: bold; }
    .st-terminee { color: green; font-weight: bold; }

    .error { color: red; background: #fee; padding: 10px; border: 1px solid red; margin-bottom: 15px; }
  </style>
</head>
<body>

<h1>⚡ Gestion des Tâches</h1>

<c:if test="${not empty erreur}">
  <div class="error">Erreur : ${erreur}</div>
</c:if>

<div class="dashboard">

  <div class="box left-panel">
    <h3 style="margin-top: 0; color: #00796b;">Nouvelle Tâche</h3>

    <form action="taches" method="post">
      <label>Titre :</label>
      <input type="text" name="titre" required placeholder="Ex: Maintenance Rover">

      <label>Mission :</label>
      <select name="missionId" required>
        <option value=""> Sélectionner </option>
        <c:forEach var="m" items="${missions}">
          <option value="${m.id}">${m.titre}</option>
        </c:forEach>
      </select>

      <label>Responsable :</label>
      <select name="membreId" required>
        <option value=""> Sélectionner </option>
        <c:forEach var="mem" items="${membres}">
          <option value="${mem.id}">${mem.nom} ${mem.prenom}</option>
        </c:forEach>
      </select>

      <label>Description :</label>
      <textarea name="description" rows="3"></textarea>

      <label>Statut :</label>
      <div style="margin-top: 5px;">
        <input type="radio" name="statut" value="A_FAIRE" checked> À faire
        <input type="radio" name="statut" value="EN_COURS"> En cours
      </div>

      <button type="submit" class="btn-main">Ajouter</button>
    </form>
  </div>

  <div class="box right-panel">
    <h3 style="margin-top: 0;">Liste des opérations</h3>

    <c:if test="${empty taches}">
      <p style="color: gray;">Aucune tâche pour le moment.</p>
    </c:if>

    <c:if test="${not empty taches}">
      <table>
        <thead>
        <tr>
          <th>Tâche</th>
          <th>Contexte</th>
          <th>Statut</th>
          <th>X</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="t" items="${taches}">
          <tr>
            <td>
              <b>${t.titre}</b><br>
              <small>${t.description}</small>
            </td>
            <td>
              📂 ${t.mission.titre}<br>
              👤 ${t.membreAssigne.nom}
            </td>
            <td>
              <c:choose>
                <c:when test="${t.statut == 'A_FAIRE'}"><span class="st-afaire">À FAIRE</span></c:when>
                <c:when test="${t.statut == 'EN_COURS'}"><span class="st-encours">EN COURS</span></c:when>
                <c:otherwise><span class="st-terminee">TERMINÉE</span></c:otherwise>
              </c:choose>
            </td>
            <td>
              <form action="taches" method="post" style="margin:0;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="${t.id}">
                <button type="submit" style="color: red; background: none; border: none; font-weight: bold; cursor: pointer;">
                  X
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:if>
  </div>

</div>

</body>
</html>