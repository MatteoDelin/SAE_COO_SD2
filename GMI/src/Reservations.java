import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reservations {

    private Utilisateur user;
    private Ressources ressource;
    private Date date;
    private LocalTime heure;
    private int duree;
    private String type_emprunt;
    static ArrayList<Reservations> liste_reservations = new ArrayList<Reservations>();

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

    // --- Getters / Setters ---
    public Utilisateur getUser()               { return user; }
    public void setUser(Utilisateur user)       { this.user = user; }
    public Ressources getRessource()            { return ressource; }
    public void setRessource(Ressources r)      { this.ressource = r; }
    public Date getDate()                       { return date; }
    public void setDate(Date date)              { this.date = date; }
    public LocalTime getHeure()                 { return heure; }
    public void setHeure(LocalTime heure)       { this.heure = heure; }
    public int getDuree()                       { return duree; }
    public void setDuree(int duree)             { this.duree = duree; }
    public String getType_emprunt()             { return type_emprunt; }
    public void setType_emprunt(String t)       { this.type_emprunt = t; }

    /**
     * Compare deux dates en ignorant l'heure et les millisecondes.
     * Nécessaire car JDateChooser retourne minuit exact alors que
     * les dates parsées depuis le CSV peuvent avoir des ms résiduelles.
     */
    private static boolean memJour(Date a, Date b) {
        if (a == null || b == null) return false;
        Calendar ca = Calendar.getInstance();
        Calendar cb = Calendar.getInstance();
        ca.setTime(a);
        cb.setTime(b);
        return ca.get(Calendar.YEAR)         == cb.get(Calendar.YEAR)
            && ca.get(Calendar.MONTH)        == cb.get(Calendar.MONTH)
            && ca.get(Calendar.DAY_OF_MONTH) == cb.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Recherche par utilisateur, ressource et date (jour uniquement).
     * L'heure n'est pas un critère d'identification — elle est modifiable.
     */
    static Reservations print_reservation(ArrayList<Reservations> liste,
                                          String nom_user, String nom_ressource,
                                          Date date) {
        for (Reservations e : liste) {
            if (e.getUser().getNom().equals(nom_user)
                    && e.getRessource().getNom().equals(nom_ressource)
                    && memJour(e.getDate(), date)) {
                return e;
            }
        }
        return null;
    }
}
