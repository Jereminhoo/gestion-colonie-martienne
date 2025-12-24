package be.condorcet.gestioncoloniemartienne.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RESSOURCE")
public class Ressource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private double quantite;

    @Column(nullable = false)
    private String unite; // ex: "Litres", "Kg", "Unités"

    @Column(name = "SEUIL_ALERTE")
    private double seuilAlerte; // Si la quantité descend sous ce nombre, on alerte

    // pour jpa
    public Ressource() {
    }

    public Ressource(String nom, double quantite, String unite, double seuilAlerte) {
        this.nom = nom;
        this.quantite = quantite;
        this.unite = unite;
        this.seuilAlerte = seuilAlerte;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getQuantite() { return quantite; }
    public void setQuantite(double quantite) { this.quantite = quantite; }

    public String getUnite() { return unite; }
    public void setUnite(String unite) { this.unite = unite; }

    public double getSeuilAlerte() { return seuilAlerte; }
    public void setSeuilAlerte(double seuilAlerte) { this.seuilAlerte = seuilAlerte; }

    @Override
    public String toString() {
        return "Ressource{" + "id=" + id + ", nom='" + nom + '\'' + ", quantite=" + quantite + '}';
    }
}