<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <title>Missions - Colonie Martienne</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #eef1f5;
      padding: 20px;
    }

    .container {
      width: 800px;
      margin: 0 auto;
      background-color: white;
      padding: 20px;
      border: 1px solid #ccc;
    }

    h1, h2 { color: #2c3e50;
      border-bottom: 2px solid #2c3e50;
      padding-bottom: 10px;
    }

    .form-group {
      margin-bottom: 15px;
    }

    label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }

    input[type=text], input[type=date], textarea {
      width: 100%;
      padding: 8px;
      border: 1px solid #999;
      box-sizing: border-box;
    }

    textarea { height: 80px;
      font-family: Arial, sans-serif;
    }

    button {
      background-color: #0056b3;
      color: white;
      padding: 10px 20px;
      border: none;
      cursor: pointer;
      font-size: 16px;
    }
    button:hover { background-color: #004494; }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    th {
      background-color: #0056b3;
      color: white;
      padding: 10px;
      text-align: left;
    }

    td {
      border: 1px solid #ccc;
      padding: 8px;
    }


    .btn-del {
      background-color: #d9534f;
      padding: 5px 10px;
      font-size: 12px;
    }
    .btn-del:hover { background-color: #c9302c; }

    .error {
      color: #a94442;
      background-color: #f2dede;
      border: 1px solid #ebccd1;
      padding: 10px;
      margin-bottom: 15px;
    }
  </style>
</head>
<body>

<div class="container">

  <h1>Planification des Missions</h1>

  <c:if test="${not empty erreur}">
    <div class="error">Attention : ${erreur}</div>
  </c:if>

  <div style="background-color: #fafafa; padding: 15px; border: 1px solid #ddd;">
    <h2>Nouvelle Mission</h2>
    <form action="missions" method="post">

      <div class="form-group">
        <label>Titre de la mission :</label>
        <input type="text" name="titre" required>
      </div>

      <div class="form-group">
        <label>Description :</label>
        <textarea name="description" required></textarea>
      </div>

      <div class="form-group">
        <label>Date de début :</label>
        <input type="date" name="dateDebut" required>
      </div>

      <div class="form-group">
        <label>Date de fin :</label>
        <input type="date" name="dateFin" required>
      </div>

      <div class="form-group">
        <label>Statut :</label>
        <input type="radio" name="statut" value="PREVUE" checked> Prévue &nbsp;
        <input type="radio" name="statut" value="EN_COURS"> En cours &nbsp;
        <input type="radio" name="statut" value="TERMINEE"> Terminée
      </div>

      <button type="submit">Enregistrer</button>
    </form>
  </div>

  <h2>Liste des missions</h2>

  <table>
    <thead>
    <tr>
      <th>Mission</th>
      <th>Période</th>
      <th>Statut</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="m" items="${missions}">
      <tr>
        <td>
          <b>${m.titre}</b><br>
          <span style="font-size: 0.9em; color: #555;">${m.description}</span>
        </td>
        <td>${m.dateDebut} <br> ${m.dateFin}</td>
        <td>${m.statut}</td>
        <td>
          <form action="missions" method="post" style="margin:0;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="${m.id}">
            <button type="submit" class="btn-del" onclick="return confirm('Voulez-vous supprimer ?')">
              Supprimer
            </button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>

</body>
</html>