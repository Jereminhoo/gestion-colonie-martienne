package be.condorcet.gestioncoloniemartienne.dao.Impl;

import be.condorcet.gestioncoloniemartienne.dao.MembreDAO;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class MembreDAOImpl extends GenericDAOImpl<MembreEquipage, Long> implements MembreDAO {

    // Constructeur OBLIGATOIRE car le parent (GenericDAOImpl) n'a pas de constructeur vide.
    // On doit lui passer l'EntityManager et la classe qu'on gère.
    public MembreDAOImpl(EntityManager em) {
        super(em, MembreEquipage.class);
    }

    @Override
    public MembreEquipage findByLogin(String login) {
        try {
            // Une requête JPQL simple : on cherche l'objet m où m.login correspond
            TypedQuery<MembreEquipage> query = em.createQuery(
                    "SELECT m FROM MembreEquipage m WHERE m.login = :login",
                    MembreEquipage.class
            );
            query.setParameter("login", login);

            return query.getSingleResult();

        } catch (NoResultException e) {
            // Si on ne trouve personne, on renvoie null
            return null;
        }
    }
}