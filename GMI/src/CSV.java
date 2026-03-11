import model.Utilisateur;
import model.Ressources;
import model.Reservations;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

public class CSV {

    public String[][] chargement(String chemin) throws IOException {

        Map<String, Integer> mois = new LinkedHashMap<>();
        mois.put("janvier",1);  mois.put("février",2);   mois.put("mars",3);
        mois.put("avril",4);    mois.put("mai",5);        mois.put("juin",6);
        mois.put("juillet",7);  mois.put("août",8);       mois.put("septembre",9);
        mois.put("octobre",10); mois.put("novembre",11);  mois.put("décembre",12);

        List<String[]> lignes = new ArrayList<>();
        List<String> lignesIgnorees = new ArrayList<>(); // Descriptions des conflits détectés

        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(chemin), "ISO-8859-1"));
        String ligne;
        boolean premiereLigne = true;

        while ((ligne = br.readLine()) != null) {
            ligne = ligne.trim();
            if (ligne.isEmpty()) continue;

            if (premiereLigne) {
                premiereLigne = false;
                if (ligne.startsWith("Réservation") || ligne.startsWith("R")) continue;
            }

            String[] cols = ligne.split(";", -1);
            if (cols.length < 6) continue;

            String nomUser    = cols[0].trim();
            String domaine    = cols[1].trim();
            String nomRess    = cols[2].trim();
            String descRess   = cols[3].trim();
            String heureDuree = cols[4].trim();
            String typeEmpr   = cols[5].trim();

            if (nomUser.isEmpty() || nomRess.isEmpty() || heureDuree.isEmpty()) continue;

            String[] partsHD = heureDuree.split(" - ", 2);
            if (partsHD.length < 2) continue;

            // Parsing date
            Date date = null;
            Matcher mDate = Pattern.compile(
                "\\w+\\s+(\\d{1,2})\\s+(\\w+)\\s+(\\d{4})\\s+(\\d{2}):(\\d{2}):(\\d{2})")
                .matcher(partsHD[0].trim());
            if (mDate.find()) {
                int jour  = Integer.parseInt(mDate.group(1));
                int m     = mois.getOrDefault(mDate.group(2).toLowerCase(), -1);
                int annee = Integer.parseInt(mDate.group(3));
                if (m >= 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(annee, m - 1, jour, 0, 0, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    date = cal.getTime();
                }
            }

            // Parsing heure
            LocalTime heure = null;
            Matcher mHeure = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})")
                .matcher(partsHD[0].trim());
            if (mHeure.find()) {
                heure = LocalTime.of(
                    Integer.parseInt(mHeure.group(1)),
                    Integer.parseInt(mHeure.group(2)),
                    Integer.parseInt(mHeure.group(3)));
            }

            if (date == null || heure == null) continue;

            // Parsing durée
            int duree = 0;
            Matcher md;
            md = Pattern.compile("(\\d+)\\s*semaine").matcher(partsHD[1]);
            if (md.find()) duree += Integer.parseInt(md.group(1)) * 7 * 24 * 60;
            md = Pattern.compile("(\\d+)\\s*jour").matcher(partsHD[1]);
            if (md.find()) duree += Integer.parseInt(md.group(1)) * 24 * 60;
            md = Pattern.compile("(\\d+)\\s*heure").matcher(partsHD[1]);
            if (md.find()) duree += Integer.parseInt(md.group(1)) * 60;
            md = Pattern.compile("(\\d+)\\s*minute").matcher(partsHD[1]);
            if (md.find()) duree += Integer.parseInt(md.group(1));

            // Création des objets métier
            Utilisateur user = Utilisateur.print_user(nomUser);
            if (user == null) user = new Utilisateur(nomUser);

            Ressources ress = Ressources.print_user(nomRess);
            if (ress == null) ress = new Ressources(nomRess, descRess, domaine, new Date());

            // ── Vérification de conflit avant l'import ──────────────────────
            Reservations conflit = Reservations.findConflict(ress, date, heure, duree, null);
            if (conflit != null) {
                lignesIgnorees.add(String.format(
                    "%s / %s — conflicts with %s's reservation at %s",
                    nomUser, nomRess,
                    conflit.getUser().getNom(),
                    conflit.getHeure().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))));
                continue; // On ignore cette ligne
            }
            // ────────────────────────────────────────────────────────────────

            new Reservations(user, ress, date, heure, duree, typeEmpr);

            lignes.add(new String[]{ nomUser, nomRess, domaine, descRess,
                                     heureDuree, typeEmpr });
        }

        br.close();

        // On stocke les descriptions des conflits pour que l'appelant puisse les afficher
        this.dernierLignesIgnorees = lignesIgnorees;

        return lignes.toArray(new String[0][]);
    }

    /** Liste des réservations ignorées (descriptions) lors du dernier chargement. */
    private List<String> dernierLignesIgnorees = new ArrayList<>();

    /** Retourne la liste des conflits détectés lors du dernier import. */
    public List<String> getLignesIgnorees() { return dernierLignesIgnorees; }

    public void export(String chemin) throws IOException {

        String[] joursFr = {"dimanche","lundi","mardi","mercredi","jeudi","vendredi","samedi"};
        String[] moisFr  = {"","janvier","février","mars","avril","mai","juin",
                             "juillet","août","septembre","octobre","novembre","décembre"};

        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(chemin), "ISO-8859-1"));

        pw.println("Réservation au nom de ;Domaines :;Ressources : ;Description :;"
                 + "Heure - Durée :;Type;Dernière mise à jour");

        for (Reservations res : Reservations.liste_reservations) {

            String dateTexte = "";
            if (res.getDate() != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(res.getDate());
                dateTexte = String.format("%s %02d %s %d ",
                    joursFr[c.get(Calendar.DAY_OF_WEEK) - 1],
                    c.get(Calendar.DAY_OF_MONTH),
                    moisFr[c.get(Calendar.MONTH) + 1],
                    c.get(Calendar.YEAR));
            }

            String dateHeure = dateTexte + res.getHeure().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            int minutes  = res.getDuree();
            int semaines = minutes / (7 * 24 * 60); minutes %= (7 * 24 * 60);
            int jours    = minutes / (24 * 60);     minutes %= (24 * 60);
            int heures   = minutes / 60;            minutes %= 60;
            int mins     = minutes;

            List<String> parts = new ArrayList<>();
            if (semaines > 0) parts.add(semaines + " semaine(s)");
            if (jours    > 0) parts.add(jours    + " jour(s)");
            if (heures   > 0) parts.add(heures   + " heure(s)");
            if (mins     > 0) parts.add(mins     + " minute(s)");
            if (parts.isEmpty()) parts.add("0 heure(s)");

            StringBuilder sbDuree = new StringBuilder();
            for (int i = 0; i < parts.size(); i++) {
                if (i > 0) sbDuree.append(i == parts.size() - 1 ? " et " : ", ");
                sbDuree.append(parts.get(i));
            }

            String[] vals = {
                res.getUser().getNom(),
                res.getRessource().getDomaine(),
                res.getRessource().getNom(),
                res.getRessource().getDescription(),
                res.getType_emprunt(),
                dateTexte + res.getHeure().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            };
            for (int i = 0; i < vals.length; i++) {
                if (vals[i] == null) vals[i] = "";
                if (vals[i].contains(";") || vals[i].contains("\""))
                    vals[i] = "\"" + vals[i].replace("\"", "\"\"") + "\"";
            }

            pw.printf("%s;%s;%s;%s;%s - %s ;%s ;%s%n",
                vals[0], vals[1], vals[2], vals[3],
                dateHeure, sbDuree.toString(),
                vals[4], vals[5]);
        }

        pw.close();
    }
}
