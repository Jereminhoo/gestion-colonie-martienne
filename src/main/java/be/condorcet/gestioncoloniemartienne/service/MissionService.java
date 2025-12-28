package be.condorcet.gestioncoloniemartienne.service;

import be.condorcet.gestioncoloniemartienne.dao.MissionDAO;
import be.condorcet.gestioncoloniemartienne.dao.Impl.MissionDAOImpl;
import be.condorcet.gestioncoloniemartienne.exception.BusinessException;
import be.condorcet.gestioncoloniemartienne.model.Mission;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MissionService {

    @PersistenceContext(unitName = "colonie-pu")
    private EntityManager em;

    private MissionDAO dao;

    @PostConstruct
    public void init() {
        this.dao = new MissionDAOImpl(em);
    }

    @Transactional
    public void ajouterMission(Mission m) {

        // un titre
        if (m.getTitre() == null || m.getTitre().trim().isEmpty()) {
            throw new BusinessException("La mission doit avoir un titre !");
        }

        // Règle 2 : Cohérence des dates
        // Si une date de fin est définie, elle ne peut pas être AVANT la date de début
        if (m.getDateDebut() != null && m.getDateFin() != null) {
            if (m.getDateFin().isBefore(m.getDateDebut())) {
                throw new BusinessException("La date de fin ne peut pas être avant le début !");
            }
        }

        // Par défaut, si pas de statut, on met "PREVUE"
        if (m.getStatut() == null || m.getStatut().isEmpty()) {
            m.setStatut("PREVUE");
        }

        dao.create(m);
    }

    @Transactional
    public void supprimerMission(Long id) {
        Mission m = dao.findById(id);
        if (m != null) {
            dao.delete(m);
        }
    }

    public List<Mission> listerTout() {
        return dao.findAll();
    }

    public Mission trouverParId(Long id) {
        return dao.findById(id);
    }

    @Transactional
    public void modifierMission(Mission m) {
        em.merge(m);
    }
}