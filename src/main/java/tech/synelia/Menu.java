package tech.synelia;

import java.sql.Connection;

public class Menu {
    private final MenuEleve menuEleve;

    public Menu(Connection conn) {
        this.menuEleve = new MenuEleve(new Eleverq(conn));
    }

    public void demarrer() {
        boolean continuer = true;
        while (continuer) {
            IO.println("\n===== GESTION DE L'ÉCOLE =====");
            IO.println("1. Élèves");
            IO.println("0. Quitter");
            switch (Saisie.texte("Votre choix : ")) {
                case "1" -> menuEleve.demarrer();
                case "0" -> continuer = false;
                default -> System.out.println("Choix invalide.");
            }
        }
        System.out.println("Au revoir !");
    }
}