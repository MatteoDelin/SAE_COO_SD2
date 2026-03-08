package model;

import java.util.ArrayList;

/**
 * Représente un utilisateur du système GMI.
 * La liste statique liste_utilisateur centralise tous les utilisateurs en mémoire.
 */
public class Utilisateur {

    /** Nom de l'utilisateur (identifiant unique). */
    private String nom;

    /** Liste de tous les utilisateurs de l'application. */
    public static ArrayList<Utilisateur> liste_utilisateur = new ArrayList<Utilisateur>();

    // -------------------------------------------------------------------------
    // Constructeur
    // -------------------------------------------------------------------------

    /**
     * Crée un nouvel utilisateur et l'ajoute à la liste globale.
     * @param nom Nom de l'utilisateur.
     */
    public Utilisateur(String nom) {
        this.nom = nom;
        liste_utilisateur.add(this);
    }

    // -------------------------------------------------------------------------
    // Getters / Setters
    // -------------------------------------------------------------------------

    public String getNom()           { return nom; }
    public void setNom(String nom)   { this.nom = nom; }

    // -------------------------------------------------------------------------
    // Méthodes statiques utilitaires
    // -------------------------------------------------------------------------

    /**
     * Recherche un utilisateur par son nom.
     * @param nom Nom à rechercher.
     * @return L'utilisateur trouvé, ou null s'il n'existe pas.
     */
    public static Utilisateur print_user(String nom) {
        for (int i = 0; i < liste_utilisateur.size(); i++) {
            if (liste_utilisateur.get(i).getNom().equals(nom)) {
                return liste_utilisateur.get(i);
            }
        }
        return null;
    }

    /**
     * Supprime un utilisateur de la liste par son nom.
     * @param nom Nom de l'utilisateur à supprimer.
     */
    public static void delete_user(String nom) {
        for (int i = 0; i < liste_utilisateur.size(); i++) {
            if (liste_utilisateur.get(i).getNom().equals(nom)) {
                liste_utilisateur.remove(i);
                i--; // On recule l'index car la liste a rétréci
            }
        }
    }
}
