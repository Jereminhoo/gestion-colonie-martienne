package be.condorcet.gestioncoloniemartienne.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBRE_EQUIPAGE")
public class MembreEquipage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    // "CHEF" ou "MEMBRE"
    @Column(nullable = false)
    private String role;

    private String specialite; // ex: mecano, Ingénieur...

    //Un membre peut avoir plusieurs tâches assignées
    @OneToMany(mappedBy = "membreAssigne")
    private List<Tache> tachesAssignees;


    // Constructeur vide OBLIGATOIRE pour JPA
    public MembreEquipage() {}

    // Constructeur complet
    public MembreEquipage(String nom, String prenom, String login, String password, String role, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.role = role;
        this.specialite = specialite;
    }

    //Getters et Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public List<Tache> getTachesAssignees() { return tachesAssignees; }
    public void setTachesAssignees(List<Tache> tachesAssignees) { this.tachesAssignees = tachesAssignees; }
}