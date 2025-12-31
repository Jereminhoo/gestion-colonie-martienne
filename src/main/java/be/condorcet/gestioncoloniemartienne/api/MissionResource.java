package be.condorcet.gestioncoloniemartienne.api;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import be.condorcet.gestioncoloniemartienne.model.Tache;
import be.condorcet.gestioncoloniemartienne.service.MissionService;
import be.condorcet.gestioncoloniemartienne.service.TacheService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/missions")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MissionResource {

    @Inject
    private MissionService missionService;

    @Inject
    private TacheService tacheService; // Pour récupérer les tâches

    //Lister toutes les missions
    @GET
    public List<Mission> listerTout() {
        return missionService.listerTout();
    }

    // Trouver une mission par ID
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id) {
        Mission m = missionService.trouverParId(id);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(m).build();
    }


    // GET /api/missions/{id}/taches
    @GET
    @Path("/{id}/taches")
    public Response getTachesByMission(@PathParam("id") Long id) {
        // Vérifier si la mission existe
        Mission m = missionService.trouverParId(id);
        if (m == null) return Response.status(Response.Status.NOT_FOUND).build();

        // Demander au TacheService de trouver les tâches de cette mission
        List<Tache> taches = tacheService.listerParMission(id);

        return Response.ok(taches).build();
    }

    // Créer une mission
    @POST
    public Response create(Mission m) {
        try {
            m.setId(null);
            missionService.ajouterMission(m);
            return Response.created(URI.create("/missions/" + m.getId())).entity(m).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}").build();
        }
    }

    // Modifier une mission
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Mission mJson) {
        Mission existant = missionService.trouverParId(id);
        if (existant == null) return Response.status(Response.Status.NOT_FOUND).build();

        existant.setTitre(mJson.getTitre());
        existant.setDescription(mJson.getDescription());
        existant.setDateDebut(mJson.getDateDebut());
        existant.setDateFin(mJson.getDateFin());
        existant.setStatut(mJson.getStatut());

        try {
            missionService.modifierMission(existant);
            return Response.ok(existant).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}").build();
        }
    }

    // Supprimer une mission
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Mission m = missionService.trouverParId(id);
        if (m == null) return Response.status(Response.Status.NOT_FOUND).build();

        missionService.supprimerMission(id);
        return Response.noContent().build();
    }
}