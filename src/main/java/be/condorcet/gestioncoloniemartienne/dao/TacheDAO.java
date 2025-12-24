package be.condorcet.gestioncoloniemartienne.dao;

import be.condorcet.gestioncoloniemartienne.model.Tache;

import java.util.List;

public interface TacheDAO {

    List<Tache> findByMissionId(Long missionId);
}
