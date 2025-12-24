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

import java.io.IOException;
import java.util.List;

@WebServlet("/taches")
public class TacheServlet extends HttpServlet {


    @Inject private TacheService tacheService;
    @Inject private MissionService missionService;
    @Inject private MembreService membreService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        chargerDonnees(request);
        request.getRequestDispatcher("/WEB-INF/JSP/taches.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                String idStr = request.getParameter("id");
                tacheService.supprimerTache(Long.parseLong(idStr));
            } else {
                // add task

                String titre = request.getParameter("titre");
                String description = request.getParameter("description");
                String statut = request.getParameter("statut");

                //recup ds menus déroulants
                String missionIdStr = request.getParameter("missionId");
                String membreIdStr = request.getParameter("membreId");

                // cast ID -> Objet
                Mission mission = null;
                if(missionIdStr != null && !missionIdStr.isEmpty()){
                    mission = missionService.trouverParId(Long.parseLong(missionIdStr));
                }

                MembreEquipage membre = null;
                if(membreIdStr != null && !membreIdStr.isEmpty()){
                    membre = membreService.trouverParId(Long.parseLong(membreIdStr));
                }

                //add &save

                Tache t = new Tache(titre, description, statut, mission, membre);
                tacheService.ajouterTache(t);
            }

            response.sendRedirect("taches");

        } catch (BusinessException e) {
            //erreur métier, ex: pas de membre choisi
            request.setAttribute("erreur", e.getMessage());
            chargerDonnees(request); //on recharge les listes pour réafficher le formulaire
            request.getRequestDispatcher("/WEB-INF/JSP/taches.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); //voir l'erreur dans la console serveur
            request.setAttribute("erreur", "Une erreur technique est survenue.");
            chargerDonnees(request);
            request.getRequestDispatcher("/WEB-INF/JSP/taches.jsp").forward(request, response);
        }
    }

    //éviter copier ce code 3 fois
    private void chargerDonnees(HttpServletRequest request) {
        request.setAttribute("taches", tacheService.listerTout());
        request.setAttribute("missions", missionService.listerTout());
        request.setAttribute("membres", membreService.listerTous());
    }
}