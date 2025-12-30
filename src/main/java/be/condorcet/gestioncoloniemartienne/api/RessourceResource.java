package be.condorcet.gestioncoloniemartienne.api;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Ressource;
import be.condorcet.gestioncoloniemartienne.service.RessourceService;
import jakarta.enterprise.context.RequestScoped; // Phase 4 : RequestScoped
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/ressources")                // URL : /api/ressources
@RequestScoped                      // Une nouvelle instance pour chaque requête (Consigne Prof)
@Produces(MediaType.APPLICATION_JSON) // Parle en JSON
@Consumes(MediaType.APPLICATION_JSON) // Écoute en JSON
public class RessourceResource {

    @Inject
    private RessourceService ressourceService; // On injecte le Service (Phase 5)

    // 1. GET : Lister tout
    @GET
    public List<Ressource> listerTout() {
        return ressourceService.listerTout();
    }

    // 2. GET : Un seul élément
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id) {
        Ressource r = ressourceService.trouverParId(id);
        if (r == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.ok(r).build();
    }

    // 3. POST : Créer
    @POST
    public Response create(Ressource r) {
        try {
            r.setId(null); // On force la création
            ressourceService.ajouterRessource(r);

            // Renvoie 201 Created + l'URL de la nouvelle ressource
            return Response.created(URI.create("/ressources/" + r.getId()))
                    .entity(r)
                    .build();

        } catch (BusinessException e) {
            // Consigne Phase 5 : Si erreur métier (ex: qté négative), on renvoie 400 Bad Request
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // 4. PUT : Modifier
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Ressource r) {
        Ressource existant = ressourceService.trouverParId(id);
        if (existant == null) return Response.status(Response.Status.NOT_FOUND).build();

        // Mise à jour des données
        existant.setNom(r.getNom());
        existant.setQuantite(r.getQuantite());
        existant.setUnite(r.getUnite());
        existant.setSeuilAlerte(r.getSeuilAlerte());

        try {
            ressourceService.modifier(existant);
            return Response.ok(existant).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // 5. DELETE : Supprimer
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Ressource r = ressourceService.trouverParId(id);
        if (r == null) return Response.status(Response.Status.NOT_FOUND).build();

        ressourceService.supprimerRessource(id);
        return Response.noContent().build(); // 204 No Content
    }
}