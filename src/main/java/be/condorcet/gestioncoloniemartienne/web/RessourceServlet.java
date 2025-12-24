package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Ressource;
import be.condorcet.gestioncoloniemartienne.service.RessourceService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ressources")
public class RessourceServlet extends HttpServlet {

    @Inject
    private RessourceService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ressource> liste = service.listerTout();

        request.setAttribute("ressources", liste);

        request.getRequestDispatcher("/WEB-INF/JSP/ressources.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                // suppression
                String idStr = request.getParameter("id");
                service.supprimerRessource(Long.parseLong(idStr));

            } else {
                // sinn creer
                // recup champs dans url
                String nom = request.getParameter("nom");
                String unite = request.getParameter("unite");

                // Récupération et conversion des Nombres

                double quantite = Double.parseDouble(request.getParameter("quantite"));
                double seuil = Double.parseDouble(request.getParameter("seuilAlerte"));

                // Création de l'objet
                Ressource r = new Ressource(nom, quantite, unite, seuil);

                // Appel du métier
                service.ajouterRessource(r);
            }

            // Si tout va bien, on recharge la page propre (PRG Pattern)
            response.sendRedirect("ressources");

        } catch (BusinessException e) {
            // Erreur Métier (ex: Quantité négative ou Nom déjà pris)
            request.setAttribute("erreur", e.getMessage());
            request.setAttribute("ressources", service.listerTout()); // Faut recharger la liste !
            request.getRequestDispatcher("/WEB-INF/JSP/ressources.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Erreur si l'user tape du texte dans un champ nombre
            request.setAttribute("erreur", "Veuillez entrer des nombres valides pour la quantité et le seuil.");
            request.setAttribute("ressources", service.listerTout());
            request.getRequestDispatcher("/WEB-INF/JSP/ressources.jsp").forward(request, response);
        }
    }
}