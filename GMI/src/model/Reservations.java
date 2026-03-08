package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Représente une réservation de ressource dans le système GMI.
 *
 * Une réservation lie un {@link Utilisateur} à une {@link Ressources}
 * pour une date, une heure de début, une durée (en minutes) et un type d'emprunt.
 *
 * <b>Identification :</b> une réservation est identifiée de manière unique
 * par la combinaison (utilisateur + ressource + jour).
 * L'heure n'est pas un critère d'identification — elle peut être modifiée.
 *
 * La liste statique {@code liste_reservations} centralise toutes les réservations
 * et sert de "base de données" en mémoire.
 */
public class Reservations {

    /** Utilisateur ayant effectué la réservation. */
    private Utilisateur user;

    /** Ressource réservée. */
    private Ressources ressource;

    /** Date de la réservation (heure ignorée pour la comparaison). */
    private Date date;

    /** Heure de début de la réservation. */
    private LocalTime heure;

    /** Durée de la réservation en minutes. */
    private int duree;

    /** Type d'emprunt (ex : "Emprunt", "Cours", "Maintenance"). */
    private String type_emprunt;

    /** Liste statique partagée de toutes les réservations de l'application. */
    public static ArrayList<Reservations> liste_reservations = new ArrayList<>();

    // -------------------------------------------------------------------------
    // Constructeur
    // -------------------------------------------------------------------------

    /**
     * Crée une nouvelle réservation et l'ajoute automatiquement à la liste globale.
     *
     * @param user         Utilisateur concerné.
     * @param ressource    Ressource réservée.
     * @param date         Date de la réservation.
     * @param heure        Heure de début.
     * @param duree        Durée en minutes.
     * @param type_emprunt Type de l'emprunt.
     */
    public Reservations(Utilisateur user, Ressources ressource, Date date,
                        LocalTime heure, int duree, String type_emprunt) {
        this.user         = user;
        this.ressource    = ressource;
        this.date         = date;
        this.heure        = heure;
        this.duree        = duree;
        this.type_emprunt = type_emprunt;
        liste_reservations.add(this);
    }

    // -------------------------------------------------------------------------
    // Getters / Setters
    // -------------------------------------------------------------------------

    /** @return L'utilisateur de la réservation. */
    public Utilisateur getUser()               { return user; }

    /** @param user Nouvel utilisateur. */
    public void setUser(Utilisateur user)       { this.user = user; }

    /** @return La ressource réservée. */
    public Ressources getRessource()            { return ressource; }

    /** @param r Nouvelle ressource. */
    public void setRessource(Ressources r)      { this.ressource = r; }

    /** @return La date de la réservation. */
    public Date getDate()                       { return date; }

    /** @param date Nouvelle date. */
    public void setDate(Date date)              { this.date = date; }

    /** @return L'heure de début. */
    public LocalTime getHeure()                 { return heure; }

    /** @param heure Nouvelle heure de début. */
    public void setHeure(LocalTime heure)       { this.heure = heure; }

    /** @return La durée en minutes. */
    public int getDuree()                       { return duree; }

    /** @param duree Nouvelle durée en minutes. */
    public void setDuree(int duree)             { this.duree = duree; }

    /** @return Le type d'emprunt. */
    public String getType_emprunt()             { return type_emprunt; }

    /** @param t Nouveau type d'emprunt. */
    public void setType_emprunt(String t)       { this.type_emprunt = t; }

    // -------------------------------------------------------------------------
    // Méthodes statiques utilitaires
    // -------------------------------------------------------------------------

    /**
     * Compare deux dates en ignorant l'heure et les millisecondes.
     *
     * Nécessaire car {@code JDateChooser} retourne minuit exact tandis que
     * les dates parsées depuis le CSV peuvent avoir des millisecondes résiduelles.
     * Un simple {@code equals()} échouerait dans ce cas.
     *
     * @param a Première date.
     * @param b Deuxième date.
     * @return {@code true} si les deux dates tombent le même jour calendaire.
     */
    private static boolean memJour(Date a, Date b) {
        if (a == null || b == null) return false;
        Calendar ca = Calendar.getInstance();
        Calendar cb = Calendar.getInstance();
        ca.setTime(a);
        cb.setTime(b);
        // Comparaison jour / mois / année uniquement — l'heure est ignorée
        return ca.get(Calendar.YEAR)         == cb.get(Calendar.YEAR)
            && ca.get(Calendar.MONTH)        == cb.get(Calendar.MONTH)
            && ca.get(Calendar.DAY_OF_MONTH) == cb.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Recherche une réservation par utilisateur, ressource et date (jour uniquement).
     *
     * L'heure n'est PAS un critère car elle est modifiable après création.
     * La comparaison de date utilise {@link #memJour} pour ignorer les millisecondes.
     *
     * @param liste         Liste dans laquelle chercher.
     * @param nom_user      Nom de l'utilisateur recherché.
     * @param nom_ressource Nom de la ressource recherchée.
     * @param date          Date du jour recherché.
     * @return La réservation correspondante, ou {@code null} si aucune trouvée.
     */
    public static Reservations print_reservation(ArrayList<Reservations> liste,
                                                  String nom_user,
                                                  String nom_ressource,
                                                  Date date) {
        for (Reservations e : liste) {
            if (e.getUser().getNom().equals(nom_user)
                    && e.getRessource().getNom().equals(nom_ressource)
                    && memJour(e.getDate(), date)) {
                return e;
            }
        }
        return null; // Aucune réservation trouvée
    }
}
