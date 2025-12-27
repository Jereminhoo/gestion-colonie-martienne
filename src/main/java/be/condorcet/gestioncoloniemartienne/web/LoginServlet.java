package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.service.MembreService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/auth"})
public class LoginServlet extends HttpServlet {

    @Inject
    private MembreService membreService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            //On détruit la session et on renvoie au login
            request.getSession().invalidate();
            response.sendRedirect("login.jsp");
        } else {
            // Affichage de la page login
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginForm = request.getParameter("login");
        String passForm = request.getParameter("password");

        // cherche l'utilisateur en base
        List<MembreEquipage> membres = membreService.listerTous();
        MembreEquipage userTrouve = null;

        for (MembreEquipage m : membres) {
            // Vérification Login ET Mot de passe
            if (m.getLogin().equals(loginForm) && m.getPassword().equals(passForm)) {
                userTrouve = m;
                break;
            }
        }

        if (userTrouve != null) {
            //On le met en sess
            HttpSession session = request.getSession();
            session.setAttribute("user", userTrouve);

            // On l'envoie vers l'accueil
            response.sendRedirect("index.jsp");

        } else {
            // echec
            request.setAttribute("erreur", "Identifiant ou mot de passe incorrect.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}