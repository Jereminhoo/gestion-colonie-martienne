package be.condorcet.gestioncoloniemartienne.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// "/*" signifie : Ce filtre s'applique à TOUTES les pages du site
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // 1. On regarde où l'utilisateur veut aller (L'URL)
        String chemin = req.getRequestURI();

        // 2. On définit les pages "publiques" (où on a le droit d'aller sans être connecté)
        // - login.jsp : pour voir le formulaire
        // - /auth : pour que la Servlet puisse vérifier le mot de passe
        boolean estPageLogin = chemin.endsWith("login.jsp");
        boolean estServletAuth = chemin.contains("/auth");

        // 3. On vérifie si l'utilisateur est connecté (en Session)
        boolean estConnecte = (session != null && session.getAttribute("user") != null);

        // --- LA LOGIQUE DU GARDIEN ---

        if (estConnecte || estPageLogin || estServletAuth) {
            // C'est bon, tu peux passer !
            chain.doFilter(request, response);
        } else {
            // Pas connecté et tu essaies de tricher ? -> Retour à la case départ !
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}