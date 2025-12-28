package be.condorcet.gestioncoloniemartienne.dao.Impl;

import be.condorcet.gestioncoloniemartienne.dao.MissionDAO;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MissionDAOImpl extends GenericDAOImpl<Mission, Long> implements MissionDAO {

    public MissionDAOImpl(EntityManager em) {
        super(em, Mission.class);
    }

    @Override
    public List<Mission> findAll() {
        // On SURCHARGE la méthode pour ajouter le ORDER BY
        return em.createQuery("SELECT m FROM Mission m ORDER BY m.dateDebut ASC", Mission.class)
                .getResultList();
    }
}