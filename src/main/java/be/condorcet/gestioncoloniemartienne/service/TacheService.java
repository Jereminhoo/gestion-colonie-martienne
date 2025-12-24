package be.condorcet.gestioncoloniemartienne.service;

import be.condorcet.gestioncoloniemartienne.dao.TacheDAO;
import be.condorcet.gestioncoloniemartienne.dao.Impl.TacheDAOImpl;
import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Tache;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TacheService {

    @PersistenceContext(unitName = "colonie-pu")
    private EntityManager em;

    private TacheDAO dao;

    @PostConstruct
    public void init() {
        this.dao = new TacheDAOImpl(em);
    }

    @Transactional
    public void ajouterTache(Tache t) {

        if (t.getTitre() == null || t.getTitre().trim().isEmpty()) {
            throw new BusinessException("La tâche doit avoir un titre !");
        }

        if (t.getMission() == null) {
            throw new BusinessException("Erreur : Une tâche doit être rattachée à une mission.");
        }

        if (t.getMembreAssigne() == null) {
            throw new BusinessException("Erreur : Vous devez assigner un membre à cette tâche.");
        }

        if (t.getStatut() == null || t.getStatut().isEmpty()) {
            t.setStatut("A_FAIRE");
        }

        dao.create(t);
    }

    @Transactional
    public void supprimerTache(Long id) {
        Tache t = dao.findById(id);
        if (t != null) {
            dao.delete(t);
        }
    }

    public List<Tache> listerTout() {
        return dao.findAll();
    }

    public List<Tache> listerParMission(Long missionId) {
        return dao.findByMissionId(missionId);
    }
}