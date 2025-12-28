<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Accueil - Base Alpha</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .top-bar {
            background-color: #ffffff;
            border-bottom: 1px solid #ddd;
            padding: 10px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .user-info {
            font-size: 0.9em;
            color: #555;
        }

        .user-info b {
            color: #333;
        }

        .btn-logout {
            text-decoration: none;
            color: #c0392b;
            font-size: 0.85em;
            font-weight: bold;
            padding: 5px 10px;
            border: 1px solid #c0392b;
            border-radius: 4px;
            transition: all 0.2s;
        }

        .btn-logout:hover {
            background-color: #c0392b;
            color: white;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 0 20px;
            text-align: center;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 5px;
        }

        p.subtitle {
            color: #7f8c8d;
            margin-bottom: 40px;
        }

        .dashboard-grid {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 25px;
        }

        .card {
            background: white;
            width: 220px;
            padding: 25px;
            border-radius: 6px;
            border: 1px solid #e1e4e8;
            text-align: center;
            border-top-width: 4px;
            border-top-style: solid;
        }

        .card h2 {
            font-size: 1.2em;
            margin: 15px 0 10px 0;
            color: #333;
        }

        .card p {
            font-size: 0.9em;
            color: #666;
            margin-bottom: 20px;
            line-height: 1.4;
        }

        .icon {
            font-size: 2.5em;
            display: block;
        }

        .btn-link {
            display: inline-block;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 4px;
            font-size: 0.9em;
            font-weight: 500;
            color: white;
            transition: opacity 0.2s;
        }

        .btn-link:hover {
            opacity: 0.9;
        }

        .c-membres { border-top-color: #e67e22; }
        .btn-membres { background-color: #e67e22; }

        .c-ressources { border-top-color: #c0392b; }
        .btn-ressources { background-color: #c0392b; }

        .c-missions { border-top-color: #3498db; }
        .btn-missions { background-color: #3498db; }

        .c-taches { border-top-color: #27ae60; }
        .btn-taches { background-color: #27ae60; }

    </style>
</head>
<body>

<div class="top-bar">
    <div class="user-info">
        Bienvenue, <b>${sessionScope.user.prenom} ${sessionScope.user.nom}</b> (${sessionScope.user.role})
    </div>
    <a href="auth?action=logout" class="btn-logout">Se déconnecter</a>
</div>

<div class="container">
    <h1>Base Martienne</h1>
    <p class="subtitle">Tableau de bord de gestion</p>

    <div class="dashboard-grid">

        <div class="card c-membres">
            <span class="icon">👩‍🚀</span>
            <h2>Équipage</h2>
            <p>Gestion des membres et accréditations.</p>
            <a href="membres" class="btn-link btn-membres">Gérer</a>
        </div>

        <div class="card c-ressources">
            <span class="icon">💧</span>
            <h2>Ressources</h2>
            <p>Eau, oxygène et gestion des stocks.</p>
            <a href="ressources" class="btn-link btn-ressources">Consulter</a>
        </div>

        <div class="card c-missions">
            <span class="icon">🪐</span>
            <h2>Missions</h2>
            <p>Planning et objectifs des expéditions.</p>
            <a href="missions" class="btn-link btn-missions">Planifier</a>
        </div>

        <div class="card c-taches">
            <span class="icon">⚡</span>
            <h2>Tâches</h2>
            <p>Maintenance et assignation du travail.</p>
            <a href="taches" class="btn-link btn-taches">Suivre</a>
        </div>

    </div>
</div>

</body>
</html>