package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import be.condorcet.gestioncoloniemartienne.model.Ressource;
import be.condorcet.gestioncoloniemartienne.model.Tache;
import be.condorcet.gestioncoloniemartienne.service.MissionService;
import be.condorcet.gestioncoloniemartienne.service.RessourceService;
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
import java.util.stream.Collectors;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Inject private RessourceService ressourceService;
    @Inject private MissionService missionService;
    @Inject private TacheService tacheService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MembreEquipage user = (MembreEquipage) session.getAttribute("user");


        //ressources en danger ?
        List<Ressource> ressources = ressourceService.listerTout();
        long alertes = ressources.stream()
                .filter(r -> r.getQuantite() < r.getSeuilAlerte()) // On compte celles en rouge
                .count();
        request.setAttribute("nbAlertes", alertes);

        //cmb missions en cours ?
        List<Mission> missions = missionService.listerTout();
        long missionsActive = missions.stream()
                .filter(m -> "EN_COURS".equals(m.getStatut()))
                .count();
        request.setAttribute("nbMissions", missionsActive);

        //cmb tâches pour MOI ?
        List<Tache> mesTaches = tacheService.listerPourMembre(user.getId());
        long tachesAFaire = mesTaches.stream()
                .filter(t -> !"TERMINE".equals(t.getStatut()))
                .count();
        request.setAttribute("nbTaches", tachesAFaire);

        //vers TON fichier index.jsp (mais rempli d'infos !)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}