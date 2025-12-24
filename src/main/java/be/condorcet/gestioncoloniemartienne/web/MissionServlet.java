package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import be.condorcet.gestioncoloniemartienne.service.MissionService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {

    @Inject
    private MissionService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Mission> missions = service.listerTout();
        request.setAttribute("missions", missions);
        request.getRequestDispatcher("/WEB-INF/JSP/missions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                String idStr = request.getParameter("id");
                service.supprimerMission(Long.parseLong(idStr));
            } else {
                //ajout

                String titre = request.getParameter("titre");
                String description = request.getParameter("description");
                String statut = request.getParameter("statut"); // Sera "PREVUE", "EN_COURS", etc.

                //conversion en object localdate
                // Le HTML envoie "YYYY-MM-DD" ou une chaîne vide ""

                String dateDebutStr = request.getParameter("dateDebut");
                String dateFinStr = request.getParameter("dateFin");

                LocalDate dateDebut = null;
                if (dateDebutStr != null && !dateDebutStr.isEmpty()) {
                    dateDebut = LocalDate.parse(dateDebutStr); //String -> LocalDate
                }

                LocalDate dateFin = null;
                if (dateFinStr != null && !dateFinStr.isEmpty()) {
                    dateFin = LocalDate.parse(dateFinStr);
                }

                // add et save
                Mission m = new Mission(titre, description, dateDebut, dateFin, statut);
                service.ajouterMission(m);
            }

            response.sendRedirect("missions");

        } catch (BusinessException e) {
            // Date fin < Date début
            request.setAttribute("erreur", e.getMessage());
            request.setAttribute("missions", service.listerTout());
            request.getRequestDispatcher("/WEB-INF/JSP/missions.jsp").forward(request, response);

        } catch (DateTimeParseException e) {
            // peu probable que ca soit autre chose grace a input=date mais on est jms sur
            request.setAttribute("erreur", "Format de date invalide.");
            request.setAttribute("missions", service.listerTout());
            request.getRequestDispatcher("/WEB-INF/JSP/missions.jsp").forward(request, response);
        }
    }
}