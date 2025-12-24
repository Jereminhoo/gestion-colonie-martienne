package be.condorcet.gestioncoloniemartienne.dao.Impl;

import be.condorcet.gestioncoloniemartienne.dao.MissionDAO;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import jakarta.persistence.EntityManager;

public class MissionDAOImpl extends GenericDAOImpl<Mission, Long> implements MissionDAO {

    public MissionDAOImpl(EntityManager em) {
        super(em, Mission.class);
    }
}