package tech.synelia;

import java.util.Scanner;

public class Saisie {

    private static final Scanner sc = new Scanner(System.in);

    static String texte(String message) {
        IO.println(message);
        return sc.nextLine().trim();
    }

    public static int entier(String message) {
        while (true) {
            try {
                return Integer.parseInt(texte(message));
            } catch (NumberFormatException e) {
                IO.println("Entrez un chiffre correct");
            }
        }

    }

    public static boolean confirmer(String message){
        return texte(message + "oui(o)/non(n) :").equalsIgnoreCase("o");
    }



}
