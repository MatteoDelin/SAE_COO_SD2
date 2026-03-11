package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Représente une réservation de ressource dans le système GMI.
 */
public class Reservations {

    // =========================================================================
    // Attributs
    // =========================================================================


    private Utilisateur user;
    private Ressources ressource;
    private Date date;
    private LocalTime heure;
    private int duree;
    private String type_emprunt;
    public static ArrayList<Reservations> liste_reservations = new ArrayList<Reservations>();

    // =========================================================================
    // Constructeur
    // =========================================================================

    /**
     * Crée une nouvelle réservation et l'ajoute automatiquement à la liste globale.
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

    public Utilisateur getUser() { return user; }

    public void setUser(Utilisateur user) { this.user = user; }

    public Ressources getRessource() { return ressource; }

    public void setRessource(Ressources r) { this.ressource = r; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public LocalTime getHeure() { return heure; }

    /**
     * Modifie l'heure de début de la réservation.
     */
    public void setHeure(LocalTime heure) { this.heure = heure; }

    public int getDuree() { return duree; }

    public void setDuree(int duree) { this.duree = duree; }

    public String getType_emprunt() { return type_emprunt; }

    public void setType_emprunt(String t) { this.type_emprunt = t; }

    // =========================================================================
    // Méthodes statiques utilitaires
    // =========================================================================

    /**
     * Compare deux dates en ignorant complètement l'heure.
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
