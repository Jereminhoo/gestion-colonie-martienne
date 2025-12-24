package be.condorcet.gestioncoloniemartienne.service;

import be.condorcet.gestioncoloniemartienne.dao.MembreDAO;
import be.condorcet.gestioncoloniemartienne.dao.Impl.MembreDAOImpl;
import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import be.condorcet.gestioncoloniemartienne.util.PasswordUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MembreService {

    @PersistenceContext(unitName = "colonie-pu")
    private EntityManager em;

    private MembreDAO dao;

    @PostConstruct
    public void init() {
        this.dao = new MembreDAOImpl(em);
    }

    @Transactional
    public void ajouterMembre(MembreEquipage m) {

        // Login déjà pris
        if (dao.findByLogin(m.getLogin()) != null) {
            throw new BusinessException("Login déjà utilisé");
        }

        //Nom + prénom déjà existants
        if (dao.existsByNomPrenom(m.getNom(), m.getPrenom())) {
            throw new BusinessException("Un membre avec ce nom et prénom existe déjà");
        }

        // Mot de passe trop faible
        if (m.getPassword() == null || m.getPassword().length() < 6) {
            throw new BusinessException("Mot de passe trop faible (min. 6 caractères)");
        }
        m.setRole("MEMBRE"); // règle métier

        // Hash du mot de passe
        String hashed = PasswordUtils.hash(m.getPassword());
        m.setPassword(hashed);

        dao.create(m);
    }

    @Transactional
    public void supprimerMembre(Long id) {
        MembreEquipage m = dao.findById(id);


        //Interdiction de supprimer le CHEF
        if (m != null && "CHEF".equals(m.getRole())) {
            throw new BusinessException("Impossible de supprimer le chef de base");
        }

        if (m != null) {
            dao.delete(m);
        }

    }


    public List<MembreEquipage> listerTous() {
        return dao.findAll();
    }

    public MembreEquipage trouverParLogin(String login) {
        return dao.findByLogin(login);
    }

    public MembreEquipage trouverParId(Long id) {
        return dao.findById(id);
    }
}
