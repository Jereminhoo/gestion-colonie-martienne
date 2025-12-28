package be.condorcet.gestioncoloniemartienne.service;

import be.condorcet.gestioncoloniemartienne.dao.RessourceDAO;
import be.condorcet.gestioncoloniemartienne.dao.Impl.RessourceDAOImpl;
import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Ressource;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RessourceService {

    @PersistenceContext(unitName = "colonie-pu")
    private EntityManager em;

    private RessourceDAO dao;

    @PostConstruct
    public void init() {
        // On lie le DAO à l'EntityManager manuellement comme dans MembreService
        this.dao = new RessourceDAOImpl(em);
    }


    @Transactional
    public void ajouterRessource(Ressource r) {

        // pas de quantité négative à la création
        if (r.getQuantite() < 0) {
            throw new BusinessException("La quantité ne peut pas être négative !");
        }

        //Le nom doit être unique (ex: pas 2x "Oxygène")
        // On fait une petite requête JPQL rapide pour vérifier
        Long count = em.createQuery("SELECT count(r) FROM Ressource r WHERE r.nom = :nom", Long.class)
                .setParameter("nom", r.getNom())
                .getSingleResult();

        if (count > 0) {
            throw new BusinessException("La ressource '" + r.getNom() + "' existe déjà !");
        }

        // Si tout est bon, on sauvegarde
        dao.create(r);
    }


    @Transactional
    public void supprimerRessource(Long id) {
        Ressource r = dao.findById(id);
        if (r != null) {
            dao.delete(r);
        }
    }


    public List<Ressource> listerTout() {
        return dao.findAll();
    }


    @Transactional
    public void modifier(Ressource r) {
        em.merge(r);
    }


    public Ressource trouverParId(Long id) {
        return dao.findById(id);
    }
}