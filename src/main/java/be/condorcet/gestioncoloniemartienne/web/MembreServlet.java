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

import java.io.IOException;
import java.util.List;

@WebServlet("/membres")
public class MembreServlet extends HttpServlet {

    @Inject
    private MembreService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // juste pour que les accents etc soient la

        // Récupérer la liste
        List<MembreEquipage> liste = service.listerTous();

        // Donner la liste à la JSP
        request.setAttribute("membres", liste);
        System.out.println("Recuperation des membres");

        // Afficher la page
        request.getRequestDispatcher("/WEB-INF/JSP/membres.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // juste pour que les accents etc soient la

        try{

            String action = request.getParameter("action");

            if ("delete".equals(action)) {

                Long id = Long.parseLong(request.getParameter("id"));
                service.supprimerMembre(id);
                System.out.println("Supprimation d'un membre");
            } else {
                // création
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String login = request.getParameter("login");
                String pass = request.getParameter("password");
                String spec = request.getParameter("specialite");

                MembreEquipage m = new MembreEquipage(nom, prenom, login, pass, "MEMBRE", spec);

                // Sauvegarder
                service.ajouterMembre(m);
            }

            // Recharger la page
            response.sendRedirect("membres");
        }catch(BusinessException e){

            // met le msg d'erreur dans la requete
            request.setAttribute("erreur", e.getMessage());

            // recharge les membres car on passe pas par doget donc membres peut etre null
            request.setAttribute("membres", service.listerTous());

            // Ré-affichage de la page avec message
            request.getRequestDispatcher("/WEB-INF/JSP/membres.jsp")
                    .forward(request, response);

        }

    }
}