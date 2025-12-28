<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  /* Style de la barre de menu */
  .navbar {
    background-color: #2c3e50;
    overflow: hidden;
    padding: 10px 20px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.2);
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    box-sizing: border-box;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
  }

  .navbar a {
    float: left;
    display: block;
    color: #f2f2f2;
    text-align: center;
    padding: 10px 16px;
    text-decoration: none;
    font-family: 'Segoe UI', sans-serif;
    font-weight: bold;
    border-radius: 4px;
    transition: background 0.3s;
  }

  .navbar a:hover {
    background-color: #ddd;
    color: black;
  }

  .navbar .logout-btn {
    background-color: #c0392b;
  }
  .navbar .logout-btn:hover {
    background-color: #e74c3c;
    color: white;
  }
</style>

<div class="navbar">
  <div class="nav-links">
    <a href="home">🏠 Accueil</a>
    <a href="membres">👩‍🚀 Équipage</a>
    <a href="missions">🪐 Missions</a>
    <a href="taches">⚡ Tâches</a>
  </div>
  <a href="profil" style="color: white; text-decoration: none; margin-right: 10px;">
    👤 ${sessionScope.user.login} (Modifier mon profil)
  </a>

  <a href="auth?action=logout" class="logout-btn">Déconnexion</a>
</div>
<div style="height: 60px;"></div>