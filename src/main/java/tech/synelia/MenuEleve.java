package tech.synelia;

import java.sql.SQLException;
import java.util.List;

public class MenuEleve {
    private final Eleverq dao;

    public MenuEleve(Eleverq dao) {
        this.dao = dao;
    }

    public void demarrer() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- ÉLÈVES ---");
            System.out.println("1. Afficher  2. Ajouter  3. Modifier  4. Supprimer  0. Retour");
            switch (Saisie.texte("Votre choix : ")) {
                case "1" -> afficher();
                case "2" -> ajouter();
                case "3" -> modifier();
                case "4" -> supprimer();
                case "0" -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void afficher() {
        try {
            List<Eleve> eleves = dao.findAll();
            if (eleves.isEmpty()) {
                System.out.println("Aucun élève.");
                return;
            }
            System.out.printf("%-5s %-15s %-15s %-10s %-8s%n", "ID", "Nom", "Prénom", "Matricule", "Classe");
            for (Eleve e : eleves) {
                System.out.printf("%-5d %-15s %-15s %-10s %-8d%n",
                        e.id(), e.nom(), e.prenom(), e.matricule(), e.classeId());
            }
        } catch (SQLException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

    private void ajouter() {
        String nom = Saisie.texte("Nom : ");
        String prenom = Saisie.texte("Prénom : ");
        String matricule = Saisie.texte("Matricule : ");
        int classeId = Saisie.entier("ID de la classe : ");
        try {
            int n = dao.ajouter(new Eleve(0, nom, prenom, matricule, classeId));
            System.out.println(n + " élève ajouté.");
        } catch (SQLException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

    private void modifier() {
        int id = Saisie.entier("ID de l'élève à modifier : ");
        String nom = Saisie.texte("Nouveau nom : ");
        String prenom = Saisie.texte("Nouveau prénom : ");
        try {
            int n = dao.modifier(id, nom, prenom);
            System.out.println(n > 0 ? "Élève modifié." : "Aucun élève avec cet ID.");
        } catch (SQLException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

    private void supprimer() {
        int id = Saisie.entier("ID de l'élève à supprimer : ");
        if (!Saisie.confirmer("Confirmer la suppression ?")) {
            System.out.println("Suppression annulée.");
            return;
        }
        try {
            int n = dao.supprimer(id);
            System.out.println(n > 0 ? "Élève supprimé." : "Aucun élève avec cet ID.");
        } catch (SQLException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}