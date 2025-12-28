package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
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

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {

    @Inject
    private MembreService membreService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // On affiche la page
        request.getRequestDispatcher("/WEB-INF/JSP/profil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        MembreEquipage user = (MembreEquipage) session.getAttribute("user");


        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String specialite = request.getParameter("specialite");
            String password = request.getParameter("password"); // Peut être vide

            // Appel au service
            MembreEquipage userMisAJour = membreService.mettreAJourProfil(user.getId(), nom, prenom, specialite, password);

            //Mettre à jour l'utilisateur en Session !!!!!
            session.setAttribute("user", userMisAJour);

            request.setAttribute("succes", "Profil mis à jour avec succès !");
            request.getRequestDispatcher("/WEB-INF/JSP/profil.jsp").forward(request, response);

        } catch (BusinessException e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/JSP/profil.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("erreur", "Erreur technique.");
            request.getRequestDispatcher("/WEB-INF/JSP/profil.jsp").forward(request, response);
        }
    }
}