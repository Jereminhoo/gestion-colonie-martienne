package be.condorcet.gestioncoloniemartienne.api;

import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import be.condorcet.gestioncoloniemartienne.model.Tache;
import be.condorcet.gestioncoloniemartienne.service.MembreService;
import be.condorcet.gestioncoloniemartienne.service.MissionService;
import be.condorcet.gestioncoloniemartienne.service.TacheService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/taches")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TacheResource {

    @Inject
    private TacheService tacheService;

    @Inject
    private MissionService missionService;

    @Inject
    private MembreService membreService;

    // Lister toutes les tâches
    // On peut filtrer ?missionId=2
    @GET
    public List<Tache> listerTout(@QueryParam("missionId") Long missionId,
                                  @QueryParam("membreId") Long membreId) {
        if (missionId != null) {
            return tacheService.listerParMission(missionId);
        }
        if (membreId != null) {
            return tacheService.listerPourMembre(membreId);
        }
        return tacheService.listerTout();
    }

    //Une seule tâche
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id) {
        Tache t = tacheService.trouverParId(id);
        if (t == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(t).build();
    }

    // 3. Créer une tâche
    @POST
    public Response create(Tache t) {
        try {
            // Le JSON nous envoie juste les ID dans des objets vides.
            // Il faut reconnecter avec les vrais objets de la base.

            // Récupérer la vraie Mission
            if (t.getMission() != null && t.getMission().getId() != null) {
                Mission vraieMission = missionService.trouverParId(t.getMission().getId());
                t.setMission(vraieMission);
            }

            // Récupérer le vrai Membre
            if (t.getMembreAssigne() != null && t.getMembreAssigne().getId() != null) {
                MembreEquipage vraiMembre = membreService.trouverParId(t.getMembreAssigne().getId());
                t.setMembreAssigne(vraiMembre);
            }

            //Sauvegarder via le service (qui fera les verifs : titre, mission obligatoire...)
            tacheService.ajouterTache(t);

            return Response.created(URI.create("/taches/" + t.getId())).entity(t).build();

        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}").build();
        }
    }

    // Modifier une tâche (Changer statut, réassigner...)
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Tache tJson) {
        Tache existant = tacheService.trouverParId(id);
        if (existant == null) return Response.status(Response.Status.NOT_FOUND).build();

        // Mise à jour des champs simples
        existant.setTitre(tJson.getTitre());
        existant.setDescription(tJson.getDescription());
        existant.setStatut(tJson.getStatut());

        // Si on veut changer le membre assigné
        if (tJson.getMembreAssigne() != null && tJson.getMembreAssigne().getId() != null) {
            MembreEquipage m = membreService.trouverParId(tJson.getMembreAssigne().getId());
            if (m != null) existant.setMembreAssigne(m);
        }

        try {
            tacheService.modifierTache(existant);
            return Response.ok(existant).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}").build();
        }
    }

    // Supprimer
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Tache t = tacheService.trouverParId(id);
        if (t == null) return Response.status(Response.Status.NOT_FOUND).build();

        tacheService.supprimerTache(id);
        return Response.noContent().build();
    }
}