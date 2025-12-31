package be.condorcet.gestioncoloniemartienne.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TACHE")
public class Tache implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String statut; // comme mission

    //les clé entrangere

    // Une tâche ne peut pas exister sans mission.
    @ManyToOne
    @JoinColumn(name = "REF_MISSION_ID", nullable = false)
    private Mission mission;

    // Une tâche doit etre attribué a un membre
    @ManyToOne
    @JoinColumn(name = "REF_MEMBRE_ID", nullable = false)
    private MembreEquipage membreAssigne;


    public Tache() {
    }

    public Tache(String titre, String description, String statut, Mission mission, MembreEquipage membreAssigne) {
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.mission = mission;
        this.membreAssigne = membreAssigne;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @JsonbTransient //On ne veut pas voir la Mission quand on affiche une Tâche en JSON
    public Mission getMission() { return mission; }
    public void setMission(Mission mission) { this.mission = mission; }

    @JsonbTransient
    public MembreEquipage getMembreAssigne() { return membreAssigne; }
    public void setMembreAssigne(MembreEquipage membreAssigne) { this.membreAssigne = membreAssigne; }

    @Override
    public String toString() {
        return "Tache: " + titre;
    }
}