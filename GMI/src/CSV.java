import model.Utilisateur;
import model.Ressources;
import model.Reservations;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

/**
 * Classe utilitaire pour le chargement et l'export des données au format CSV.
  */
public class CSV {

    // =========================================================================
    // Constantes de parsing
    // =========================================================================

    /** Map mois français → numéro (1-12), utilisée dans parseDate(). */
    private static final Map<String, Integer> MOIS = new LinkedHashMap<>();
    static {
        MOIS.put("janvier",1);  MOIS.put("février",2);   MOIS.put("mars",3);
        MOIS.put("avril",4);    MOIS.put("mai",5);        MOIS.put("juin",6);
        MOIS.put("juillet",7);  MOIS.put("août",8);       MOIS.put("septembre",9);
        MOIS.put("octobre",10); MOIS.put("novembre",11);  MOIS.put("décembre",12);
    }

    /** Noms de jours français pour la reconstruction du format long lors de l'export. */
    private static final String[] JOURS_FR =
        {"dimanche","lundi","mardi","mercredi","jeudi","vendredi","samedi"};

    /** Noms de mois français pour la reconstruction du format long lors de l'export. */
    private static final String[] MOIS_FR =
        {"","janvier","février","mars","avril","mai","juin",
         "juillet","août","septembre","octobre","novembre","décembre"};

    // =========================================================================
    // Classe résultat
    // =========================================================================

    /**
     * Encapsule le résultat d'une opération CSV.
     * Permet à HomePanel d'afficher un compte-rendu sans connaître les détails
     * internes du parsing.
     */
    public static class CsvResult {
        public int     nbUsers;
        public int     nbRessources;
        public int     nbReservations;
        public int     nbErreurs;
        public String  erreurFatale;   // null si pas d'erreur bloquante

        /** @return true si l'opération s'est terminée sans erreur fatale. */
        public boolean isOk() { return erreurFatale == null; }
    }

    // =========================================================================
    // Import
    // =========================================================================

    /**
     * Charge un fichier CSV et peuple les listes statiques du modèle.
     */
    public CsvResult chargement(String chemin) {

        CsvResult result = new CsvResult();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(chemin), "ISO-8859-1"))) {

            String  ligne;
            boolean premiereLigne = true;

            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;

                // Ignorer la ligne d'en-tête
                if (premiereLigne) {
                    premiereLigne = false;
                    if (ligne.startsWith("Réservation") || ligne.startsWith("R")) continue;
                }

                // Découpage en colonnes (on garde les champs vides avec -1)
                String[] cols = ligne.split(";", -1);
                if (cols.length < 6) { result.nbErreurs++; continue; }

                String nomUser    = cols[0].trim();
                String domaine    = cols[1].trim();
                String nomRess    = cols[2].trim();
                String descRess   = cols[3].trim();
                String heureDuree = cols[4].trim();
                String typeEmpr   = cols[5].trim();

                // Champs obligatoires
                if (nomUser.isEmpty() || nomRess.isEmpty() || heureDuree.isEmpty()) {
                    result.nbErreurs++; continue;
                }

                // La colonne heureDuree contient "date heure - durée"
                String[] partsHD = heureDuree.split(" - ", 2);
                if (partsHD.length < 2) { result.nbErreurs++; continue; }

                Date      date  = parseDate(partsHD[0].trim());
                LocalTime heure = parseHeure(partsHD[0].trim());
                int       duree = parseDureeMinutes(partsHD[1].trim());

                if (date == null || heure == null) { result.nbErreurs++; continue; }

                // Création ou récupération de l'utilisateur
                Utilisateur user = Utilisateur.print_user(nomUser);
                if (user == null) {
                    user = new Utilisateur(nomUser);
                    result.nbUsers++;
                }

                // Création ou récupération de la ressource
                Ressources ress = Ressources.print_user(nomRess);
                if (ress == null) {
                    ress = new Ressources(nomRess, descRess, domaine, new Date());
                    result.nbRessources++;
                }

                // Création de la réservation (s'ajoute automatiquement à liste_reservations)
                new Reservations(user, ress, date, heure, duree, typeEmpr);
                result.nbReservations++;
            }

        } catch (IOException ex) {
            result.erreurFatale = ex.getMessage();
        }

        return result;
    }

    // =========================================================================
    // Export
    // =========================================================================

    /**
     * Exporte toutes les réservations en mémoire dans un fichier CSV.
     */
    public CsvResult export(String chemin) {

        CsvResult result = new CsvResult();

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(chemin), "ISO-8859-1"))) {

            // Ligne d'en-tête identique au fichier source
            pw.println("Réservation au nom de ;Domaines :;Ressources : ;Description :;"
                     + "Heure - Durée :;Type;Dernière mise à jour");

            DateTimeFormatter fmtH = DateTimeFormatter.ofPattern("HH:mm:ss");

            for (Reservations res : Reservations.liste_reservations) {

                String dateHeure  = formatDateLong(res.getDate())
                        + " " + res.getHeure().format(fmtH);
                String dureeTexte = formatDureeTexte(res.getDuree());

                // La colonne "Dernière mise à jour" reprend la date+heure de réservation
                String maj = formatDateLong(res.getDate())
                        + res.getHeure().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                pw.printf("%s;%s;%s;%s;%s - %s ;%s ;%s%n",
                    csv(res.getUser().getNom()),
                    csv(res.getRessource().getDomaine()),
                    csv(res.getRessource().getNom()),
                    csv(res.getRessource().getDescription()),
                    dateHeure,
                    dureeTexte,
                    csv(res.getType_emprunt()),
                    maj);

                result.nbReservations++;
            }

        } catch (IOException ex) {
            result.erreurFatale = ex.getMessage();
        }

        return result;
    }

    // =========================================================================
    // Méthodes de parsing privées
    // =========================================================================

    /**
     * Parse la date depuis une chaîne au format long français.
     */
    private Date parseDate(String s) {
        Pattern p = Pattern.compile(
            "\\w+\\s+(\\d{1,2})\\s+(\\w+)\\s+(\\d{4})\\s+(\\d{2}):(\\d{2}):(\\d{2})");
        Matcher m = p.matcher(s);
        if (!m.find()) return null;
        try {
            int jour  = Integer.parseInt(m.group(1));
            int mois  = MOIS.getOrDefault(m.group(2).toLowerCase(), -1);
            int annee = Integer.parseInt(m.group(3));
            if (mois < 0) return null;
            Calendar cal = Calendar.getInstance();
            cal.set(annee, mois - 1, jour, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        } catch (Exception e) { return null; }
    }

    /**
     * Extrait l'heure depuis une chaîne contenant un horodatage.
     */
    private LocalTime parseHeure(String s) {
        Pattern p = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
        Matcher m = p.matcher(s);
        if (!m.find()) return null;
        try {
            return LocalTime.of(
                Integer.parseInt(m.group(1)),
                Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)));
        } catch (Exception e) { return null; }
    }

    /**
     * Convertit une durée textuelle en minutes.
     */
    private int parseDureeMinutes(String s) {
        int total = 0;
        Matcher m;
        m = Pattern.compile("(\\d+)\\s*semaine").matcher(s);
        if (m.find()) total += Integer.parseInt(m.group(1)) * 7 * 24 * 60;
        m = Pattern.compile("(\\d+)\\s*jour").matcher(s);
        if (m.find()) total += Integer.parseInt(m.group(1)) * 24 * 60;
        m = Pattern.compile("(\\d+)\\s*heure").matcher(s);
        if (m.find()) total += Integer.parseInt(m.group(1)) * 60;
        m = Pattern.compile("(\\d+)\\s*minute").matcher(s);
        if (m.find()) total += Integer.parseInt(m.group(1));
        return total;
    }

    // =========================================================================
    // Méthodes de formatage privées
    // =========================================================================

    /**
     * Formate une Date en chaîne longue française pour l'export.
     */
    private String formatDateLong(Date d) {
        if (d == null) return "";
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return String.format("%s %02d %s %d ",
            JOURS_FR[c.get(Calendar.DAY_OF_WEEK) - 1],
            c.get(Calendar.DAY_OF_MONTH),
            MOIS_FR[c.get(Calendar.MONTH) + 1],
            c.get(Calendar.YEAR));
    }

    /**
     * Convertit une durée en minutes en texte français lisible.
     */
    private String formatDureeTexte(int minutes) {
        int semaines = minutes / (7 * 24 * 60); minutes %= (7 * 24 * 60);
        int jours    = minutes / (24 * 60);     minutes %= (24 * 60);
        int heures   = minutes / 60;            minutes %= 60;
        int mins     = minutes;

        List<String> parts = new ArrayList<>();
        if (semaines > 0) parts.add(semaines + " semaine(s)");
        if (jours    > 0) parts.add(jours    + " jour(s)");
        if (heures   > 0) parts.add(heures   + " heure(s)");
        if (mins     > 0) parts.add(mins     + " minute(s)");
        if (parts.isEmpty()) return "0 heure(s)";

        if (parts.size() == 1) return parts.get(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) sb.append(i == parts.size() - 1 ? " et " : ", ");
            sb.append(parts.get(i));
        }
        return sb.toString();
    }

    /**
     * Protège une valeur pour l'insertion dans un champ CSV.
     */
    private String csv(String val) {
        if (val == null) return "";
        if (val.contains(";") || val.contains("\""))
            return "\"" + val.replace("\"", "\"\"") + "\"";
        return val;
    }
}
