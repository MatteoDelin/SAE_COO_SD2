package model;

import java.util.ArrayList;

/**
 * Représente un utilisateur du système.
 */
public class Utilisateur {

    // =========================================================================
    // Attributs
    // =========================================================================

    private String nom;
    public static ArrayList<Utilisateur> liste_utilisateur = new ArrayList<Utilisateur>();

    // =========================================================================
    // Constructeur
    // =========================================================================

    public Utilisateur(String nom) {
        this.nom = nom;                    // On stocke le nom dans l'objet
        liste_utilisateur.add(this);       // On l'ajoute à la liste globale
    }

    // =========================================================================
    // Getters et Setters
    // =========================================================================

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // =========================================================================
    // Méthodes
    // =========================================================================

    /**
     * Recherche et retourne un utilisateur dans la liste globale par son nom.
     */
    public static Utilisateur print_user(String nom) {

        // On parcourt toute la liste un élément à la fois
        for (int i = 0; i < liste_utilisateur.size(); i++) {

            // On compare le nom de l'utilisateur en position i avec le nom cherché
            if (liste_utilisateur.get(i).getNom().equals(nom)) {

                // Trouvé : on retourne l'objet directement
                return liste_utilisateur.get(i);
            }
        }

        // On a parcouru toute la liste sans trouver : on retourne null
        return null;
    }

    /**
     * Supprime de la liste globale l'utilisateur dont le nom correspond.
     */
    public static void delete_user(String nom) {

        // On parcourt la liste avec un index entier (pas de for-each)
        // car on va modifier la liste pendant la boucle
        for (int i = 0; i < liste_utilisateur.size(); i++) {

            // Si le nom correspond, on supprime cet élément
            if (liste_utilisateur.get(i).getNom().equals(nom)) {

                liste_utilisateur.remove(i); // Suppression à la position i

                // Après remove(i), l'élément suivant est maintenant à la position i.
                // On décrémente i pour le traiter à la prochaine itération
                // et éviter de le "sauter" par erreur.
                i--;
            }
        }
    }
}
