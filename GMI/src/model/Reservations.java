package model;

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
    public static ArrayList<Reservations> liste_reservations = new ArrayList<Reservations>();

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

    public Utilisateur getUser() { return user; }
    public void setUser(Utilisateur user) { this.user = user; }
    public Ressources getRessource() { return ressource; }
    public void setRessource(Ressources r) { this.ressource = r; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public String getType_emprunt() { return type_emprunt; }
    public void setType_emprunt(String t) { this.type_emprunt = t; }

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
     * Convertit une date + heure en minutes depuis l'epoch pour permettre
     * la comparaison de créneaux horaires.
     */
    private static long toMinutes(Date date, LocalTime heure) {
        if (date == null || heure == null) return -1;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long jourEnMinutes = c.getTimeInMillis() / 60000L;
        return jourEnMinutes + heure.getHour() * 60L + heure.getMinute();
    }

    /**
     * Vérifie si un créneau entre en conflit avec les réservations existantes sur la même ressource.
     */
    public static Reservations findConflict(Ressources ressource, Date date,
                                             LocalTime heure, int duree,
                                             Reservations exclure) {
        if (ressource == null || date == null || heure == null || duree <= 0) return null;

        long debutNouveau = toMinutes(date, heure);
        long finNouveau   = debutNouveau + duree;

        for (int i = 0; i < liste_reservations.size(); i++) {
            Reservations r = liste_reservations.get(i);
            if (r == exclure) continue;
            if (!r.getRessource().getNom().equals(ressource.getNom())) continue;
            if (r.getDate() == null || r.getHeure() == null) continue;

            long debutExistant = toMinutes(r.getDate(), r.getHeure());
            long finExistante  = debutExistant + r.getDuree();

            // Chevauchement détecté
            if (debutNouveau < finExistante && debutExistant < finNouveau) {
                return r;
            }
        }
        return null;
    }

    public static Reservations print_reservation(ArrayList<Reservations> liste,
                                                  String nom_user,
                                                  String nom_ressource,
                                                  Date date) {
        for (int i = 0; i < liste.size(); i++) {
            Reservations e = liste.get(i);
            boolean memeUser = e.getUser().getNom().equals(nom_user);
            boolean memeRess = e.getRessource().getNom().equals(nom_ressource);
            boolean memeDate = memJour(e.getDate(), date);
            if (memeUser && memeRess && memeDate) return e;
        }
        return null;
    }
}
