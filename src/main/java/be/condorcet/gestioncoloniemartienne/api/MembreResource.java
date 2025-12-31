package be.condorcet.gestioncoloniemartienne.api;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.service.MembreService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/membres")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MembreResource {

    @Inject
    private MembreService membreService;

    // Lister tous les membres
    @GET
    public List<MembreEquipage> listerTout() {
        List<MembreEquipage> membres = membreService.listerTout();
        //On efface les mots de passe avant d'envoyer le JSON
        for (MembreEquipage m : membres) {
            m.setPassword(null);
        }
        return membres;
    }

    // Trouver un membre par ID
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id) {
        MembreEquipage m = membreService.trouverParId(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Sécurité : On cache le mot de passe
        m.setPassword(null);
        return Response.ok(m).build();
    }

    // Créer un membre (Inscription)
    @POST
    public Response create(MembreEquipage m) {
        try {
            // Le service gère déjà le hashage et les vérifications
            membreService.ajouterMembre(m);

            // On cache le mdp pour la réponse
            m.setPassword(null);

            return Response.created(URI.create("/membres/" + m.getId()))
                    .entity(m)
                    .build();

        } catch (BusinessException e) {
            // Ex: Login déjà pris, mot de passe trop court...
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // 4. Modifier un membre
    // Attention : Ton service utilise "mettreAJourProfil" avec des arguments précis
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MembreEquipage mJson) {
        try {
            // On appelle ta méthode spécifique du Service
            // Note : mJson contient les données envoyées par Postman
            MembreEquipage updated = membreService.mettreAJourProfil(
                    id,
                    mJson.getNom(),
                    mJson.getPrenom(),
                    mJson.getSpecialite(),
                    mJson.getPassword() // Peut être null si pas de changement
            );

            updated.setPassword(null); // Sécurité
            return Response.ok(updated).build();

        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // 5. Supprimer un membre
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            membreService.supprimerMembre(id);
            return Response.noContent().build();
        } catch (BusinessException e) {
            // Ex:on essaie de supprimer le CHEF
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}