package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.service.MembreService;
import be.condorcet.gestioncoloniemartienne.util.PasswordUtils;
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
        System.out.println("DEBUG ACTION: " + action); // Regarde ta console serveur !

        if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("login.jsp");

        } else if ("install".equals(action)) {
            // --- CRÉATION DU CHEF ---
            try {
                // 1. Hashage du mot de passe "jeremtk"
                String mdpHashe = PasswordUtils.hash("jeremtk");

                // 2. Création (Login "Jeremtk")
                MembreEquipage chef = new MembreEquipage(
                        "Muanza",
                        "Jeremy",
                        "Jeremtk",
                        mdpHashe,
                        "CHEF",
                        "PILOTE"
                );

                membreService.ajouterMembre(chef);

                // Message de confirmation
                response.setContentType("text/html");
                response.getWriter().write("<h1>✅ Chef Jeremtk re-créé !</h1><p>Login: Jeremtk / Pass: jeremtk</p><a href='login.jsp'>Retour Login</a>");

            } catch (Exception e) {
                response.getWriter().write("<h1>❌ Erreur : " + e.getMessage() + "</h1>");
                e.printStackTrace();
            }

        } else {
            // Si pas d'action, on affiche le formulaire
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginForm = request.getParameter("login");
        String passForm = request.getParameter("password");

        List<MembreEquipage> membres = membreService.listerTout();
        MembreEquipage userTrouve = null;

        for (MembreEquipage m : membres) {

            // 🔥 CORRECTION ICI : equalsIgnoreCase au lieu de equals
            // Cela permet de se connecter avec "jeremtk" même si le login est "Jeremtk"
            if (m.getLogin().equalsIgnoreCase(loginForm)) {

                // Vérification du mot de passe hashé
                if (PasswordUtils.verify(passForm, m.getPassword())) {
                    userTrouve = m;
                    break;
                }
            }
        }

        if (userTrouve != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userTrouve);
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("erreur", "Identifiant ou mot de passe incorrect.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}