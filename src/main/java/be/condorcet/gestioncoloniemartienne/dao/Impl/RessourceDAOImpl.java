package be.condorcet.gestioncoloniemartienne.dao.Impl;

import be.condorcet.gestioncoloniemartienne.dao.RessourceDAO;
import be.condorcet.gestioncoloniemartienne.model.Ressource;
import jakarta.persistence.EntityManager;

public class RessourceDAOImpl extends GenericDAOImpl<Ressource, Long> implements RessourceDAO {

    // EntityManager et la classe Ressource au parent
    public RessourceDAOImpl(EntityManager em) {
        super(em, Ressource.class);
    }

}