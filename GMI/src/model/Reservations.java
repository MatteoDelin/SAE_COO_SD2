package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Représente une réservation de ressource dans le système GMI.
 *
 * Une réservation est le lien entre un {@link Utilisateur} et une {@link Ressources}
 * à un moment précis. Elle contient :
 *   - l'utilisateur qui a réservé,
 *   - la ressource réservée,
 *   - la date de la réservation,
 *   - l'heure de début,
 *   - la durée en minutes,
 *   - le type d'emprunt (Emprunt, Cours, Maintenance, etc.).
 *
 * Identification d'une réservation :
 * Une réservation est identifiée de façon unique par la combinaison
 * (utilisateur + ressource + jour calendaire). L'heure n'est pas un critère
 * d'identification car elle peut être modifiée après création.
 *
 * La liste statique {@code liste_reservations} sert de base de données en mémoire.
 * Tout objet Reservations créé s'y ajoute automatiquement dans le constructeur.
 */
public class Reservations {

    // =========================================================================
    // Attributs
    // =========================================================================

    /**
     * Utilisateur ayant effectué la réservation.
     * On stocke une référence vers l'objet Utilisateur, pas juste son nom.
     * Ainsi, si le nom de l'utilisateur est modifié, la réservation reflète
     * automatiquement le changement (les deux pointent vers le même objet).
     */
    private Utilisateur user;

    /**
     * Ressource réservée.
     * Même principe que pour user : référence directe à l'objet Ressources.
     * Si la ressource est modifiée (nom, domaine...), la réservation le voit.
     */
    private Ressources ressource;

    /**
     * Date de la réservation.
     * Seule la partie "jour" est utilisée pour identifier une réservation.
     * L'heure contenue dans ce champ est ignorée lors des comparaisons
     * (voir la méthode privée {@link #memJour(Date, Date)}).
     */
    private Date date;

    /**
     * Heure de début de la réservation.
     * Stockée séparément de la date sous forme de LocalTime (HH:mm:ss).
     * Peut être modifiée après création via {@link #setHeure(LocalTime)}.
     */
    private LocalTime heure;

    /**
     * Durée de la réservation exprimée en minutes.
     * Exemple : 90 = 1 heure 30 minutes, 1440 = 1 journée entière.
     * Les méthodes de formatage dans HomePanel convertissent cette valeur
     * en texte lisible ("1 heure(s) 30 minute(s)").
     */
    private int duree;

    /**
     * Type de l'emprunt.
     * Indique la nature de la réservation. Valeurs possibles dans le CSV :
     * "Emprunt", "Cours", "Maintenance", ou toute autre valeur saisie par l'utilisateur.
     * Utilisé dans les graphiques pour répartir les réservations par type.
     */
    private String type_emprunt;

    /**
     * Liste statique partagée de toutes les réservations de l'application.
     *
     * Fonctionne comme une base de données en mémoire, accessible depuis
     * n'importe quelle classe via {@code Reservations.liste_reservations}.
     * Remplie au fil des créations manuelles et des imports CSV.
     */
    public static ArrayList<Reservations> liste_reservations = new ArrayList<Reservations>();

    // =========================================================================
    // Constructeur
    // =========================================================================

    /**
     * Crée une nouvelle réservation et l'ajoute automatiquement à la liste globale.
     *
     * On passe directement les objets Utilisateur et Ressources (pas juste leurs noms)
     * pour conserver un lien vivant : si l'utilisateur est renommé, la réservation
     * voit le nouveau nom sans aucune modification.
     *
     * @param user         L'utilisateur qui effectue la réservation.
     * @param ressource    La ressource réservée.
     * @param date         La date de la réservation (seul le jour est utilisé).
     * @param heure        L'heure de début (format HH:mm ou HH:mm:ss).
     * @param duree        La durée en minutes.
     * @param type_emprunt Le type de réservation ("Emprunt", "Cours", "Maintenance"...).
     */
    public Reservations(Utilisateur user, Ressources ressource, Date date,
                        LocalTime heure, int duree, String type_emprunt) {
        this.user         = user;
        this.ressource    = ressource;
        this.date         = date;
        this.heure        = heure;
        this.duree        = duree;
        this.type_emprunt = type_emprunt;
        liste_reservations.add(this); // Ajout automatique à la liste globale
    }

    // =========================================================================
    // Getters et Setters
    // =========================================================================

    /** @return L'utilisateur de la réservation. */
    public Utilisateur getUser() { return user; }

    /** @param user Le nouvel utilisateur. */
    public void setUser(Utilisateur user) { this.user = user; }

    /** @return La ressource réservée. */
    public Ressources getRessource() { return ressource; }

    /** @param r La nouvelle ressource. */
    public void setRessource(Ressources r) { this.ressource = r; }

    /** @return La date de la réservation. */
    public Date getDate() { return date; }

    /** @param date La nouvelle date. */
    public void setDate(Date date) { this.date = date; }

    /** @return L'heure de début de la réservation. */
    public LocalTime getHeure() { return heure; }

    /**
     * Modifie l'heure de début de la réservation.
     * L'heure ne fait pas partie de l'identifiant d'une réservation,
     * elle peut donc être modifiée librement sans créer de doublon.
     * @param heure La nouvelle heure de début.
     */
    public void setHeure(LocalTime heure) { this.heure = heure; }

    /** @return La durée de la réservation en minutes. */
    public int getDuree() { return duree; }

    /** @param duree La nouvelle durée en minutes. */
    public void setDuree(int duree) { this.duree = duree; }

    /** @return Le type d'emprunt ("Emprunt", "Cours", "Maintenance"...). */
    public String getType_emprunt() { return type_emprunt; }

    /** @param t Le nouveau type d'emprunt. */
    public void setType_emprunt(String t) { this.type_emprunt = t; }

    // =========================================================================
    // Méthodes statiques utilitaires
    // =========================================================================

    /**
     * Compare deux dates en ignorant complètement l'heure.
     *
     * Pourquoi cette méthode existe :
     * Quand on compare deux objets Date avec equals(), Java compare l'horodatage
     * complet (jour + heure + minutes + secondes + millisecondes).
     * Or, le JDateChooser retourne minuit exact (00:00:00.000) tandis que les dates
     * importées depuis le CSV contiennent un vrai horaire (ex: 14:32:07.000).
     * Un simple equals() dirait "dates différentes" alors qu'elles représentent
     * le même jour. Cette méthode extrait uniquement le jour/mois/année
     * de chaque date via Calendar pour comparer uniquement la partie calendaire.
     *
     * Exemple :
     *   a = "2021-10-05 00:00:00" (vient du JDateChooser)
     *   b = "2021-10-05 09:15:30" (vient du CSV)
     *   memJour(a, b) → true  (même jour calendaire)
     *   a.equals(b)   → false (heures différentes)
     *
     * @param a Première date à comparer.
     * @param b Deuxième date à comparer.
     * @return true si les deux dates tombent le même jour calendaire, false sinon.
     */
    private static boolean memJour(Date a, Date b) {

        // Sécurité : si l'une des deux dates est null, on ne peut pas comparer
        if (a == null || b == null) return false;

        // Calendar est un outil Java qui permet d'extraire les composantes
        // d'une date : année, mois, jour, heure, etc.
        Calendar ca = Calendar.getInstance();
        Calendar cb = Calendar.getInstance();

        // On "charge" chaque Calendar avec sa date respective
        ca.setTime(a);
        cb.setTime(b);

        // Comparaison uniquement sur l'année, le mois et le jour.
        // Calendar.YEAR   = numéro de l'année (ex : 2021)
        // Calendar.MONTH  = mois de 0 à 11 (janvier = 0, décembre = 11)
        // Calendar.DAY_OF_MONTH = jour du mois de 1 à 31
        return ca.get(Calendar.YEAR)         == cb.get(Calendar.YEAR)
            && ca.get(Calendar.MONTH)        == cb.get(Calendar.MONTH)
            && ca.get(Calendar.DAY_OF_MONTH) == cb.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Recherche une réservation dans une liste par utilisateur, ressource et date.
     *
     * C'est la méthode principale pour retrouver une réservation spécifique.
     * Elle est utilisée dans MainWindow pour les panneaux "Modify" et "Delete"
     * des réservations : l'utilisateur choisit un user, une ressource et une date
     * dans l'interface, et cette méthode retrouve la réservation correspondante.
     *
     * La comparaison de date utilise {@link #memJour} et non equals() pour
     * éviter les faux négatifs dus aux heures résiduelles (voir memJour).
     *
     * @param liste         La liste dans laquelle chercher (généralement liste_reservations).
     * @param nom_user      Le nom de l'utilisateur recherché.
     * @param nom_ressource Le nom de la ressource recherchée.
     * @param date          La date du jour recherché (seule la partie jour est utilisée).
     * @return La première réservation correspondant aux critères, ou null si aucune trouvée.
     */
    public static Reservations print_reservation(ArrayList<Reservations> liste,
                                                  String nom_user,
                                                  String nom_ressource,
                                                  Date date) {

        // On parcourt chaque réservation de la liste une par une
        for (int i = 0; i < liste.size(); i++) {
            Reservations e = liste.get(i);

            // On vérifie les trois critères d'identification :
            // 1. Le nom de l'utilisateur correspond-il ?
            boolean memeUser = e.getUser().getNom().equals(nom_user);

            // 2. Le nom de la ressource correspond-il ?
            boolean memeRess = e.getRessource().getNom().equals(nom_ressource);

            // 3. La date est-elle le même jour calendaire ? (via memJour, pas equals)
            boolean memeDate = memJour(e.getDate(), date);

            // Si les trois conditions sont remplies simultanément, on a trouvé
            if (memeUser && memeRess && memeDate) {
                return e;
            }
        }

        // On a parcouru toute la liste sans trouver de correspondance
        return null;
    }
}
