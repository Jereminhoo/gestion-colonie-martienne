<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Colonie Martienne - Accueil</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #eef2f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .main-container {
            text-align: center;
            width: 100%;
            max-width: 1000px;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 10px;
            font-size: 2.5em;
        }

        p.subtitle {
            color: #7f8c8d;
            font-size: 1.2em;
            margin-bottom: 50px;
        }

        .cards-container {
            display: flex;
            justify-content: center;
            gap: 30px;
            flex-wrap: wrap;
        }

        .card {
            background: white;
            width: 250px;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-align: center;
            border-top: 5px solid #ccc;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }

        .card-membres { border-color: #e67e22; }
        .card-missions { border-color: #3498db; }
        .card-taches { border-color: #00796b; }
        .card-resssources{border-color: #e62222;}

        .icon {
            font-size: 40px;
            margin-bottom: 20px;
            display: block;
        }

        h2 { margin: 10px 0; color: #333; }
        p { color: #666; font-size: 0.9em; margin-bottom: 25px; }

        .btn {
            display: inline-block;
            text-decoration: none;
            padding: 10px 25px;
            border-radius: 25px;
            color: white;
            font-weight: bold;
            transition: opacity 0.2s;
        }
        .btn:hover { opacity: 0.9; }

        .btn-membres { background-color: #e67e22; }
        .btn-missions { background-color: #3498db; }
        .btn-taches { background-color: #00796b; }
        .btn-ressources { background-color: #790000; }

    </style>
</head>
<body>

<div class="main-container">

    <h1>Base Martienne </h1>
    <p class="subtitle">Système de gestion intégré de la colonie</p>

    <div class="cards-container">

        <div class="card card-membres">
            <span class="icon">👩‍🚀</span>
            <h2>Équipage</h2>
            <p>Gérez les astronautes, leurs rôles et leurs spécialités.</p>
            <a href="membres" class="btn btn-membres">Gérer l'équipe</a>
        </div>

        <div class="card card-resssources">
            <span class="icon">💧</span>
            <h2>Tâches</h2>
            <p>Creez des ressources et gerer son seuil d'alerte</p>
            <a href="ressources" class="btn btn-ressources">Voir les ressources</a>
        </div>

        <div class="card card-missions">
            <span class="icon">🪐</span>
            <h2>Missions</h2>
            <p>Planifiez les expéditions et définissez les dates clés.</p>
            <a href="missions" class="btn btn-missions">Voir le planning</a>
        </div>

        <div class="card card-taches">
            <span class="icon">⚡</span>
            <h2>Tâches</h2>
            <p>Assignez le travail et suivez l'avancement en temps réel.</p>
            <a href="taches" class="btn btn-taches">Tableau de bord</a>
        </div>



    </div>
</div>

</body>
</html>