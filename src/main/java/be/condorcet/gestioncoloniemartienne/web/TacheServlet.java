package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import be.condorcet.gestioncoloniemartienne.model.Tache;
import be.condorcet.gestioncoloniemartienne.service.MembreService;
import be.condorcet.gestioncoloniemartienne.service.MissionService;
import be.condorcet.gestioncoloniemartienne.service.TacheService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/taches")
public class TacheServlet extends HttpServlet {

    @Inject private TacheService tacheService;
    @Inject private MissionService missionService;
    @Inject private MembreService membreService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'utilisateur connecté
        HttpSession session = request.getSession();
        MembreEquipage user = (MembreEquipage) session.getAttribute("user");

        // Logique d'affichage selon le Rôle
        if ("CHEF".equals(user.getRole())) {
            //Voit tout + a besoin des listes pour le formulaire d'ajout
            request.setAttribute("taches", tacheService.listerTout());
            request.setAttribute("missions", missionService.listerTout());
            request.setAttribute("membres", membreService.listerTout());
        } else {
            // sinn membre voit que SES tâches
            request.setAttribute("taches", tacheService.listerPourMembre(user.getId()));
        }

        request.getRequestDispatcher("/WEB-INF/JSP/taches.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        MembreEquipage user = (MembreEquipage) session.getAttribute("user");

        String action = request.getParameter("action");

        try {
            // validation accese pour tous
            if ("valider".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                Tache t = tacheService.trouverParId(id);

                if(t != null) {
                    // On vérifie que c'est bien MA tâche ou que je suis CHEF
                    if("CHEF".equals(user.getRole()) || t.getMembreAssigne().getId().equals(user.getId())) {
                        t.setStatut("TERMINE");
                        tacheService.modifierTache(t);
                    }
                }
            }
            //gestion pour chef only
            else if ("CHEF".equals(user.getRole())) {

                if ("delete".equals(action)) {
                    String idStr = request.getParameter("id");
                    tacheService.supprimerTache(Long.parseLong(idStr));
                } else {
                    // add task
                    String titre = request.getParameter("titre");
                    String description = request.getParameter("description");
                    // Statut par défaut géré dans le service si null, sinon A_FAIRE
                    String statut = "A_FAIRE";

                    // Récupération des IDs
                    String missionIdStr = request.getParameter("missionId");
                    String membreIdStr = request.getParameter("membreId");

                    // Récupération des Objets
                    Mission mission = missionService.trouverParId(Long.parseLong(missionIdStr));

                    MembreEquipage membre = membreService.trouverParId(Long.parseLong(membreIdStr));

                    Tache t = new Tache(titre, description, statut, mission, membre);
                    tacheService.ajouterTache(t);
                }
            }

            response.sendRedirect("taches");

        } catch (BusinessException e) {
            request.setAttribute("erreur", e.getMessage());
            // On recharge les données pour réafficher la page correctement
            doGet(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Une erreur technique est survenue.");
            doGet(request, response);
        }
    }
}