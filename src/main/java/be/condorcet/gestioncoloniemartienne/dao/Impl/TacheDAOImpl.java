package be.condorcet.gestioncoloniemartienne.dao.Impl;

import be.condorcet.gestioncoloniemartienne.dao.TacheDAO;
import be.condorcet.gestioncoloniemartienne.model.Tache;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TacheDAOImpl extends GenericDAOImpl<Tache, Long> implements TacheDAO {

    public TacheDAOImpl(EntityManager em) {
        super(em, Tache.class);
    }

    @Override
    public List<Tache> findByMissionId(Long missionId) {
        return em.createQuery("SELECT t FROM Tache t WHERE t.mission.id = :id", Tache.class)
                .setParameter("id", missionId)
                .getResultList();
    }
}