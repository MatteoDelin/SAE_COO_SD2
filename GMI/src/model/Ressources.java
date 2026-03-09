package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Représente une ressource matérielle disponible à la réservation dans GMI.
 *
 * Une ressource peut être par exemple un ordinateur, une salle, un vidéoprojecteur, etc.
 * Elle est caractérisée par :
 *   - un nom unique (identifiant dans toute l'application),
 *   - une description libre,
 *   - un domaine (catégorie à laquelle elle appartient, ex : "PC Courte Durée"),
 *   - une date de dernière mise à jour (mise à jour automatiquement lors de toute modification).
 *
 * Comme pour {@link Utilisateur}, la liste statique {@code liste_ressource} joue
 * le rôle de base de données en mémoire. Tout objet Ressources créé s'y ajoute
 * automatiquement via le constructeur.
 */
public class Ressources {

    // =========================================================================
    // Attributs
    // =========================================================================

    /**
     * Nom unique de la ressource.
     * Utilisé comme identifiant : on retrouve une ressource par son nom
     * via la méthode {@link #print_user(String)}.
     */
    private String nom;

    /**
     * Description libre de la ressource.
     * Peut contenir des informations complémentaires comme la marque,
     * le modèle, l'emplacement physique, etc.
     * Ce champ peut être vide.
     */
    private String description;

    /**
     * Domaine d'appartenance de la ressource.
     * Permet de regrouper les ressources par catégorie.
     * Exemple : "PC Courte Durée", "Salle de réunion", "Audiovisuel".
     * Utilisé dans les graphiques pour afficher des statistiques par domaine.
     */
    private String domaine;

    /**
     * Date de la dernière mise à jour de la ressource.
     * Initialisée à la date de création, puis mise à jour à chaque modification
     * via {@link #setLast_maj(Date)}.
     */
    private Date last_maj;

    /**
     * Liste statique partagée de toutes les ressources de l'application.
     *
     * Fonctionne comme une base de données en mémoire.
     * Accessible depuis n'importe quelle classe via {@code Ressources.liste_ressource}.
     * Remplie au fil des créations manuelles et des imports CSV.
     */
    public static ArrayList<Ressources> liste_ressource = new ArrayList<Ressources>();

    // =========================================================================
    // Constructeur
    // =========================================================================

    /**
     * Crée une nouvelle ressource et l'ajoute automatiquement à la liste globale.
     *
     * @param nom         Nom unique de la ressource.
     * @param description Description libre (peut être une chaîne vide).
     * @param domaine     Domaine/catégorie de la ressource.
     * @param last_maj    Date de création ou de dernière mise à jour.
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

    /** @return Le nom de la ressource. */
    public String getNom() { return nom; }

    /**
     * Modifie le nom de la ressource.
     * Attention : si une réservation référence cette ressource, elle pointera
     * vers le même objet et verra automatiquement le nouveau nom.
     * @param nom Le nouveau nom.
     */
    public void setNom(String nom) { this.nom = nom; }

    /** @return La description de la ressource. */
    public String getDescription() { return description; }

    /** @param d La nouvelle description. */
    public void setDescription(String d) { this.description = d; }

    /** @return Le domaine de la ressource. */
    public String getDomaine() { return domaine; }

    /** @param domaine Le nouveau domaine. */
    public void setDomaine(String domaine) { this.domaine = domaine; }

    /** @return La date de dernière mise à jour. */
    public Date getLast_maj() { return last_maj; }

    /**
     * Met à jour la date de dernière modification.
     * Appelée automatiquement dans MainWindow après chaque modification de ressource
     * avec {@code new Date()} pour enregistrer la date et l'heure courante.
     * @param last_maj La nouvelle date de mise à jour.
     */
    public void setLast_maj(Date last_maj) { this.last_maj = last_maj; }

    // =========================================================================
    // Méthodes statiques utilitaires
    // =========================================================================

    /**
     * Recherche et retourne une ressource dans la liste globale par son nom.
     *
     * On parcourt la liste élément par élément. Dès qu'un nom correspond,
     * on retourne l'objet Ressources correspondant. Si aucun ne correspond,
     * on retourne null.
     *
     * Utilisée dans MainWindow pour vérifier l'existence d'une ressource
     * avant création, modification ou suppression.
     *
     * @param nom Le nom de la ressource à rechercher (sensible à la casse).
     * @return L'objet Ressources trouvé, ou null si aucune ressource ne porte ce nom.
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
     *
     * On utilise un index entier pour parcourir la liste car on la modifie
     * pendant la boucle (suppression d'un élément).
     * Le {@code i--} après chaque suppression compense le décalage des index
     * pour ne pas sauter l'élément qui vient de prendre la place de celui supprimé.
     *
     * Note : cette méthode ne supprime pas les réservations associées à cette
     * ressource. C'est une limite volontaire de l'application.
     *
     * @param nom Le nom de la ressource à supprimer.
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
