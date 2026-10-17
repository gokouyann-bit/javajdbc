package tech.synelia;

import javax.naming.ConfigurationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JeuCapitale {

    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO(conn);
            ConfigDAO configDAO = new ConfigDAO(conn);
            ScoreDAO scoreDAO = new ScoreDAO(conn);

            List<Pays> tousLesPays = new PaysDAO(conn).findAll();
            if (tousLesPays.isEmpty()) {
                IO.println("Aucun pays avec capitale dans la base.");
                return;
            }

            IO.println("===============================TOLEEEEEEEEEEEGAAAAAAAAAAAAAAAAAA! ==============================");
            Utilisateur joueur = accueil(utilisateurDAO);
            if (joueur == null) {
                IO.println("Djo tu connais non apres !");
                return;
            }
            IO.println("\nC'est commeeeeeeeeeeeeeeeeeeeeeeeent " + joueur.getPrenom() + " " + joueur.getNom() + " !");

            boolean rejouer = true;
            while (rejouer) {

                int nbQuestions = configDAO.getNombreQuestions();
                if (nbQuestions < 1) {
                    System.out.println("La table configs est vide : ajoutez une ligne avec nombre_question.");
                    return;
                }
                if (nbQuestions > tousLesPays.size()) {
                    IO.println("Configuration : " + nbQuestions + " questions, mais seulement "
                            + tousLesPays.size() + " pays. Le jeu utilisera " + tousLesPays.size() + " questions.");
                    nbQuestions = tousLesPays.size();
                }

                int score = jouerPartie(tousLesPays, nbQuestions);

                IO.println("\n===== FIN DE LA PARTIE =====");
                IO.println("Votre score : " + score + "/" + nbQuestions);
                IO.println(commentaire(score, nbQuestions));

                try {
                    scoreDAO.enregistrer(joueur.getId(), score, nbQuestions - score);
                    IO.println("Score enregistré.");
                } catch (SQLException e) {
                    IO.println("Score non enregistré : " + e.getMessage());
                }

                rejouer = demanderRejouer();
            }
              IO.println("Yf non tu connais !");

        } catch (SQLException e) {
            IO.println("Erreur base de données : " + e.getMessage());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }



    private static Utilisateur accueil(UtilisateurDAO dao) throws SQLException {
        while (true) {
            IO.println("\n1.Se connecter");
            IO.println("2.S'inscrire");
            IO.println("0.Quitter");
            switch (lireNonVide("Votre choix : ")) {
                case "1" -> {
                    Utilisateur u = connecter(dao);
                    if (u != null) return u;
                }
                case "2" -> {
                    Utilisateur u = inscrire(dao);
                    if (u != null) return u;
                }
                case "0" -> { return null; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static Utilisateur connecter(UtilisateurDAO dao) throws SQLException {
        Utilisateur u = dao.findByEmail(lireEmail());
        if (u == null) {
            IO.println("Toi tu es nouveau et puis tu veux mentir. Choisi  « S'inscrire » la-bas !");
        }
        return u;
    }

    private static Utilisateur inscrire(UtilisateurDAO dao) throws SQLException {
        String email = lireEmail();
        if (dao.findByEmail(email) != null) {
            IO.println("Tu es deja inscrit mon frere. Choisi « Se connecter » la-bas !");
            return null;
        }
        String nom = lireNonVide("Nom : ");
        String prenom = lireNonVide("Prénom : ");
        Utilisateur u = dao.inscrire(nom, prenom, email);
        IO.println("Inscription réussie.");
        return u;
    }

    private static String lireEmail() {
        while (true) {
            String email = lireNonVide("E-mail : ").toLowerCase();
            if (email.contains("@") && email.indexOf('@') < email.lastIndexOf('.')) {
                return email;
            }
            IO.println("E-mail invalide (exemple : nom@mail.com).");
        }
    }



    private static int jouerPartie(List<Pays> tousLesPays, int nbQuestions) {
        List<Pays> disponibles = new ArrayList<>(tousLesPays);
        int score = 0;

        for (int i = 1; i <= nbQuestions; i++) {
            Pays pays = disponibles.remove(random.nextInt(disponibles.size()));

            IO.println("\nQuestion " + i + "/" + nbQuestions);
            String reponse = lireNonVide("Quelle est la capitale de : " + pays.getNom() + " ? ");
            String reponseNorm = normaliser(reponse);

            boolean correct = pays.getCapitales().stream()
                    .anyMatch(c -> normaliser(c).equals(reponseNorm));

            if (correct) {
                IO.println("ROBOOOOOOOOOOOOOOOOOT HEIN !");
                score++;
            } else {
                IO.println("Belelouuuu, c'etait : " + String.join(" ou ", pays.getCapitales()));
            }
        }
        return score;
    }



    private static String lireNonVide(String message) {
        String texte;
        do {
            IO.print(message);
            texte = sc.nextLine().trim();
            if (texte.isEmpty()) {
                IO.println("Djo pardon faut ecrire nom de capital la oubien tu connais pas?");
            }
        } while (texte.isEmpty());
        return texte;
    }

    private static boolean demanderRejouer() {
        String choix;
        do {
            IO.print("\nVoulez-vous rejouer ? (oui/non) : ");
            choix = sc.nextLine().trim().toLowerCase();
            if (!choix.equals("oui") && !choix.equals("non")) {
                IO.println("Répondez par oui ou non.");
            }
        } while (!choix.equals("oui") && !choix.equals("non"));
        return choix.equals("oui");
    }

    private static String normaliser(String texte) {
        String sansAccent = Normalizer.normalize(texte, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sansAccent.trim().toLowerCase().replaceAll("[\\s-]+", " ");
    }

    private static String commentaire(int score, int total) {
        double ratio = (double) score / total;
        if (score == total) return "YA GOAT LA !";
        if (ratio >= 0.7) return "Ijuuuuu le chouchou hein !";
        if (ratio >= 0.5) return "Legerement fort sur le coté  !";
        return "Toi la oh tu fais quoi ici au juste  ? ";
    }
}