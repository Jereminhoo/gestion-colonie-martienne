package be.condorcet.gestioncoloniemartienne.dao;

import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;

public interface MembreDAO extends GenericDAO<MembreEquipage, Long> {

    // pour connexion
    MembreEquipage findByLogin(String login);

    //vérifier si un membre avec nom + prénom existe
    boolean existsByNomPrenom(String nom, String prenom);


}