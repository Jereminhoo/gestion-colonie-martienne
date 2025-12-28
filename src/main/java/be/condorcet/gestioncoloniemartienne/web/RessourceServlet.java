package be.condorcet.gestioncoloniemartienne.web;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.model.Ressource;
import be.condorcet.gestioncoloniemartienne.service.RessourceService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        // securité pour backend (postma)
        // On vérifie que c'est bien un CHEF. Si un membre essaie de poster, on l'éjecte.
        HttpSession session = request.getSession();
        MembreEquipage user = (MembreEquipage) session.getAttribute("user");

        if (user == null || !"CHEF".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit : Vous n'êtes pas Chef !");
            return;
        }

        try {
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                // suppression
                String idStr = request.getParameter("id");
                service.supprimerRessource(Long.parseLong(idStr));

            } else if ("update".equals(action)) {
                // maj stock
                Long id = Long.parseLong(request.getParameter("id"));


                String montantStr = request.getParameter("montant");
                if (montantStr == null || montantStr.isEmpty()) {
                    // Si le champ est vide ou n'existe pas, on ne fait rien et on recharge
                    response.sendRedirect("ressources");
                    return;
                }

                double montant = Double.parseDouble(montantStr);
                String mode = request.getParameter("mode");

                double changement = 0;
                // Si on clique sur "-", on inverse le montant
                if ("sub".equals(mode)) {
                    changement = -montant;
                } else {
                    changement = montant;
                }

                Ressource r = service.trouverParId(id);

                if (r != null) {
                    double nouvelleQte = r.getQuantite() + changement;

                    // On empêche le négatif
                    if (nouvelleQte < 0) {
                        nouvelleQte = 0.0;
                    }

                    r.setQuantite(nouvelleQte);
                    service.modifier(r);
                }
            }else {
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
        }catch (Exception e) {
            // Sécurité globale pour éviter une page blanche en cas de bug imprévu
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur inattendue : " + e.getMessage());
            request.setAttribute("ressources", service.listerTout());
            request.getRequestDispatcher("/WEB-INF/JSP/ressources.jsp").forward(request, response);
        }
    }
}