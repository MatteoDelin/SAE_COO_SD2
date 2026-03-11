package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Représente une ressource matérielle disponible à la réservation dans GMI.
 */
public class Ressources {

    // =========================================================================
    // Attributs
    // =========================================================================

    private String nom;
    private String description;
    private String domaine;
    private Date last_maj;
    public static ArrayList<Ressources> liste_ressource = new ArrayList<Ressources>();

    // =========================================================================
    // Constructeur
    // =========================================================================

    /**
     * Crée une nouvelle ressource et l'ajoute automatiquement à la liste globale.
     */
    public Ressources(String nom, String description, String domaine, Date last_maj) {
        this.nom         = nom;
        this.description = description;
        this.domaine     = domaine;
        this.last_maj    = last_maj;
        liste_ressource.add(this); // Ajout automatique à la liste globale
    }

    // =========================================================================
    // Getters et Setters
    // =========================================================================

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }
    public Date getLast_maj() { return last_maj; }
    public void setLast_maj(Date last_maj) { this.last_maj = last_maj; }

    // =========================================================================
    // Méthodes statiques utilitaires
    // =========================================================================

    /**
     * Recherche et retourne une ressource dans la liste globale par son nom.
     */
    public static Ressources print_user(String nom) {

        // Parcours de toute la liste
        for (int i = 0; i < liste_ressource.size(); i++) {

            // Comparaison exacte du nom (sensible à la casse)
            if (liste_ressource.get(i).getNom().equals(nom)) {
                return liste_ressource.get(i); // Ressource trouvée, on la retourne
            }
        }

        // Aucune ressource ne porte ce nom
        return null;
    }

    /**
     * Supprime de la liste globale la ressource dont le nom correspond.
     */
    public static void delete_user(String nom) {

        // Parcours avec index entier pour pouvoir modifier la liste en cours de boucle
        for (int i = 0; i < liste_ressource.size(); i++) {

            if (liste_ressource.get(i).getNom().equals(nom)) {

                liste_ressource.remove(i); // Suppression de la ressource

                // Après suppression, les éléments suivants reculent d'une position.
                // On décrémente i pour ne pas sauter le prochain élément.
                i--;
            }
        }
    }
}
