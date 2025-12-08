package be.condorcet.gestioncoloniemartienne;

import be.condorcet.gestioncoloniemartienne.dao.MembreDAO;
import be.condorcet.gestioncoloniemartienne.dao.Impl.MembreDAOImpl;
import be.condorcet.gestioncoloniemartienne.model.MembreEquipage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        // 1. Démarrage de JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("colonie-pu");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // 2. Préparation du DAO
        MembreDAO membreDAO = new MembreDAOImpl(em);

        try {
            // --- TEST 1 : CRÉATION ---
            tx.begin();

            System.out.println("Création d'un nouvel astronaute...");
            MembreEquipage nouveau = new MembreEquipage(
                    "Muanza",
                    "Jerem",
                    "jeremtk",
                    "jeremtk",
                    "CHEF",
                    "Pilote"
            );

            membreDAO.create(nouveau);

            tx.commit(); // ca part vers sqldeveloper
            System.out.println(">>> Astronaute enregistré avec succès ! ID = " + nouveau.getId());


            // --- TEST 2 : LECTURE ---
            System.out.println("\n--- Lecture depuis la base ---");

            // Test de la méthode générique
            MembreEquipage m = membreDAO.findById(nouveau.getId());
            System.out.println("Trouvé par ID : " + m.getPrenom() + " " + m.getNom());

            // Test de la méthode spécifique
            MembreEquipage m2 = membreDAO.findByLogin("jeremtk");
            System.out.println("Trouvé par Login : " + m2.getPrenom() + " (" + m2.getRole() + ")");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}