package be.condorcet.gestioncoloniemartienne.dao;

import be.condorcet.gestioncoloniemartienne.model.Tache;

import java.util.List;

public interface TacheDAO extends GenericDAO<Tache, Long> {

    List<Tache> findByMissionId(Long missionId);
}
