package be.condorcet.gestioncoloniemartienne.service;

import be.condorcet.gestioncoloniemartienne.dao.MembreDAO;
import be.condorcet.gestioncoloniemartienne.dao.Impl.MembreDAOImpl;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;
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
        m.setRole("MEMBRE"); // règle métier

        // Hash du mot de passe
        String hashed = PasswordUtils.hash(m.getPassword());
        m.setPassword(hashed);

        dao.create(m);
    }

    @Transactional
    public void supprimerMembre(Long id) {
        MembreEquipage m = dao.findById(id);
        dao.delete(m);
        System.out.println("Supprimer membre avec succes!");

    }


    public List<MembreEquipage> listerTous() {
        return dao.findAll();
    }

    public MembreEquipage trouverParLogin(String login) {
        return dao.findByLogin(login);
    }
}
