package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Représente une ressource matérielle du système GMI
 * (ex : ordinateur, salle, équipement).
 * La liste statique liste_ressource centralise toutes les ressources en mémoire.
 */
public class Ressources {

    /** Nom unique de la ressource. */
    private String nom;

    /** Description libre de la ressource. */
    private String description;

    /** Domaine d'appartenance (ex : "PC Courte Durée"). */
    private String domaine;

    /** Date de dernière mise à jour. */
    private Date last_maj;

    /** Liste de toutes les ressources de l'application. */
    public static ArrayList<Ressources> liste_ressource = new ArrayList<Ressources>();

    // -------------------------------------------------------------------------
    // Constructeur
    // -------------------------------------------------------------------------

    /**
     * Crée une nouvelle ressource et l'ajoute à la liste globale.
     * @param nom         Nom de la ressource.
     * @param description Description de la ressource.
     * @param domaine     Domaine auquel appartient la ressource.
     * @param last_maj    Date de dernière mise à jour.
     */
    public Ressources(String nom, String description, String domaine, Date last_maj) {
        this.nom         = nom;
        this.description = description;
        this.domaine     = domaine;
        this.last_maj    = last_maj;
        liste_ressource.add(this);
    }

    // -------------------------------------------------------------------------
    // Getters / Setters
    // -------------------------------------------------------------------------

    public String getNom()                   { return nom; }
    public void setNom(String nom)           { this.nom = nom; }
    public String getDescription()           { return description; }
    public void setDescription(String d)     { this.description = d; }
    public String getDomaine()               { return domaine; }
    public void setDomaine(String domaine)   { this.domaine = domaine; }
    public Date getLast_maj()                { return last_maj; }
    public void setLast_maj(Date last_maj)   { this.last_maj = last_maj; }

    // -------------------------------------------------------------------------
    // Méthodes statiques utilitaires
    // -------------------------------------------------------------------------

    /**
     * Recherche une ressource par son nom.
     * @param nom Nom à rechercher.
     * @return La ressource trouvée, ou null si elle n'existe pas.
     */
    public static Ressources print_user(String nom) {
        for (int i = 0; i < liste_ressource.size(); i++) {
            if (liste_ressource.get(i).getNom().equals(nom)) {
                return liste_ressource.get(i);
            }
        }
        return null;
    }

    /**
     * Supprime une ressource de la liste par son nom.
     * @param nom Nom de la ressource à supprimer.
     */
    public static void delete_user(String nom) {
        for (int i = 0; i < liste_ressource.size(); i++) {
            if (liste_ressource.get(i).getNom().equals(nom)) {
                liste_ressource.remove(i);
                i--; // On recule l'index car la liste a rétréci
            }
        }
    }
}
