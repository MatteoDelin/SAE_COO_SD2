import model.Utilisateur;
import model.Ressources;
import model.Reservations;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import net.miginfocom.swing.MigLayout;

public class HomePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Map<String, Integer> MOIS = new LinkedHashMap<>();
    static {
        MOIS.put("janvier",1); MOIS.put("février",2); MOIS.put("mars",3);
        MOIS.put("avril",4);   MOIS.put("mai",5);     MOIS.put("juin",6);
        MOIS.put("juillet",7); MOIS.put("août",8);    MOIS.put("septembre",9);
        MOIS.put("octobre",10);MOIS.put("novembre",11);MOIS.put("décembre",12);
    }

    private final DefaultTableModel modelUsers = new DefaultTableModel(
            new String[]{"Nom"}, 0);
    private final DefaultTableModel modelRess = new DefaultTableModel(
            new String[]{"Name", "Description", "Domain"}, 0);
    private final DefaultTableModel modelReserv = new DefaultTableModel(
            new String[]{"User", "Ressources", "Date", "Time", "Duration (min)", "Type"}, 0);

    private JLabel statusLabel;
    private JButton exportButton;
    private JButton browseButton;

    public HomePanel() {
        setBackground(new Color(245, 247, 250));
        setLayout(new MigLayout("insets 20", "[grow]", "[][15][grow][8][]"));

        JLabel titre = new JLabel("GMI – Ressources Management", SwingConstants.CENTER);
        titre.setFont(new Font("Tahoma", Font.BOLD, 26));
        titre.setForeground(new Color(40, 70, 130));
        add(titre, "cell 0 0, growx, align center");

        add(buildDropZone(), "cell 0 1, growx, h 100!");

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tabs.addTab("👤 Users", buildScrollTable(modelUsers));
        tabs.addTab("📦 Ressources",   buildScrollTable(modelRess));
        tabs.addTab("📅 Reservations", buildScrollTable(modelReserv));
        add(tabs, "cell 0 2, grow");

        statusLabel = new JLabel(
                "No file loaded — drag a CSV or click Browse.",
                SwingConstants.LEFT);
        statusLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        add(statusLabel, "cell 0 3, growx");

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bar.setOpaque(false);

        browseButton = new JButton("📂  Browse…");
        browseButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { openChooser(); }
        });
        bar.add(browseButton);

        exportButton = new JButton("💾  Export CSV");
        exportButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        exportButton.setBackground(new Color(50, 120, 190));
        exportButton.setForeground(Color.WHITE);
        exportButton.setOpaque(true);
        exportButton.setBorderPainted(false);
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) { exporterCSV(); }
        });
        bar.add(exportButton);

        add(bar, "cell 0 4, growx");
    }

    private JScrollPane buildScrollTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(new Font("Tahoma", Font.PLAIN, 12));
        t.setRowHeight(21);
        t.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        t.setEnabled(false);
        return new JScrollPane(t);
    }

    private JPanel buildDropZone() {
        JPanel zone = new JPanel(new BorderLayout());
        zone.setBackground(new Color(225, 235, 250));
        zone.setBorder(new DashBorder(new Color(100, 140, 210), 8));

        JLabel hint = new JLabel(
            "<html><center>"
            + "🗂️ &nbsp;<b>Drag and drop a CSV file here</b> &nbsp;(or click to browse)<br>"
            + "<span style='font-size:10px;color:#777'>"
            + "Format: Reservation name ; Domain ; Ressources ; Description ; Time - Duration ; Type ; Last update"
            + "</span></center></html>",
            SwingConstants.CENTER);
        hint.setFont(new Font("Tahoma", Font.PLAIN, 13));
        hint.setForeground(new Color(70, 100, 170));
        zone.add(hint, BorderLayout.CENTER);

        zone.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        zone.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { zone.setBackground(new Color(210, 225, 248)); }
            @Override public void mouseExited(MouseEvent e)  { zone.setBackground(new Color(225, 235, 250)); }
            @Override public void mouseClicked(MouseEvent e) { openChooser(); }
        });

        new DropTarget(zone, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override public void dragEnter(DropTargetDragEvent d) {
                zone.setBackground(new Color(185, 215, 248));
                zone.setBorder(new DashBorder(new Color(30, 90, 200), 8));
            }
            @Override public void dragExit(DropTargetEvent d) {
                resetZone(zone);
            }
            @Override public void drop(DropTargetDropEvent d) {
                resetZone(zone);
                try {
                    d.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) d.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        File f = files.get(0);
                        if (f.getName().toLowerCase().endsWith(".csv")) {
                            chargerCSV(f);
                        } else {
                            setStatus("❌ File rejected: not a .csv file", Color.RED);
                        }
                    }
                    d.dropComplete(true);
                } catch (Exception ex) {
                    d.dropComplete(false);
                    setStatus("❌ Drop error : " + ex.getMessage(), Color.RED);
                }
            }
        }, true);

        return zone;
    }

    private void resetZone(JPanel zone) {
        zone.setBackground(new Color(225, 235, 250));
        zone.setBorder(new DashBorder(new Color(100, 140, 210), 8));
    }

    private void openChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Fichiers CSV (*.csv)", "csv"));
        fc.setDialogTitle("Open a CSV file");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            chargerCSV(fc.getSelectedFile());
    }

    private void chargerCSV(File fichier) {

        modelUsers.setRowCount(0);
        modelRess.setRowCount(0);
        modelReserv.setRowCount(0);

        int nbUsers = 0, nbRess = 0, nbReserv = 0, nbErreurs = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fichier), "ISO-8859-1"))) {

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
                if (cols.length < 6) { nbErreurs++; continue; }

                String nomUser    = cols[0].trim();
                String domaine    = cols[1].trim();
                String nomRess    = cols[2].trim();
                String descRess   = cols[3].trim();
                String heureDuree = cols[4].trim();
                String typeEmpr   = cols[5].trim();

                if (nomUser.isEmpty() || nomRess.isEmpty() || heureDuree.isEmpty()) {
                    nbErreurs++; continue;
                }

                String[] partsHD = heureDuree.split(" - ", 2);
                if (partsHD.length < 2) { nbErreurs++; continue; }

                Date   date  = parseDate(partsHD[0].trim());
                LocalTime heure = parseHeure(partsHD[0].trim());
                int    duree = parseDureeMinutes(partsHD[1].trim());

                if (date == null || heure == null) { nbErreurs++; continue; }

                Utilisateur user = Utilisateur.print_user(nomUser);
                if (user == null) {
                    user = new Utilisateur(nomUser);
                    nbUsers++;
                    modelUsers.addRow(new Object[]{nomUser});
                }

                Ressources ress = Ressources.print_user(nomRess);
                if (ress == null) {
                    ress = new Ressources(nomRess, descRess, domaine, new Date());
                    nbRess++;
                    modelRess.addRow(new Object[]{nomRess, descRess, domaine});
                }

                new Reservations(user, ress, date, heure, duree, typeEmpr);
                nbReserv++;
                modelReserv.addRow(new Object[]{
                    nomUser, nomRess,
                    formatDate(date), heure.format(DateTimeFormatter.ofPattern("HH:mm")),
                    duree, typeEmpr
                });
            }

        } catch (IOException ex) {
            setStatus("❌ Read error : " + ex.getMessage(), Color.RED);
            return;
        }

        String msg = String.format(
            "✅ Import complete — %d user(s), %d ressource(s), %d reservation(s)",
            nbUsers, nbRess, nbReserv);
        if (nbErreurs > 0) msg += String.format(" — ⚠️ %d line(s) skipped", nbErreurs);
        setStatus(msg, nbErreurs == 0 ? new Color(0, 130, 0) : new Color(170, 90, 0));
    }

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

    void exporterCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("export_gmi.csv"));
        fc.setFileFilter(new FileNameExtensionFilter("Fichiers CSV (*.csv)", "csv"));
        fc.setDialogTitle("Save CSV");
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File dest = fc.getSelectedFile();
        if (!dest.getName().toLowerCase().endsWith(".csv"))
            dest = new File(dest.getAbsolutePath() + ".csv");

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(dest), "ISO-8859-1"))) {

            pw.println("Réservation au nom de ;Domaines :;Ressources : ;Description :;Heure - Durée :;Type;Dernière mise à jour");

            DateTimeFormatter fmtH = DateTimeFormatter.ofPattern("HH:mm:ss");

            for (Reservations res : Reservations.liste_reservations) {
                String dateHeure  = formatDateLong(res.getDate())
                        + " " + res.getHeure().format(fmtH);
                String dureeTexte = formatDureeTexte(res.getDuree());
                String maj        = formatDateLong(res.getDate())
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
            }

            setStatus("💾 Export successful → " + dest.getAbsolutePath(), new Color(0, 130, 0));
            JOptionPane.showMessageDialog(this,
                    "Export successful!\n" + dest.getAbsolutePath(),
                    "Export CSV", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            setStatus("❌ Erreur export : " + ex.getMessage(), Color.RED);
            JOptionPane.showMessageDialog(this,
                    "Export error:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final String[] JOURS_FR =
        {"dimanche","lundi","mardi","mercredi","jeudi","vendredi","samedi"};
    private static final String[] MOIS_FR =
        {"","janvier","février","mars","avril","mai","juin",
         "juillet","août","septembre","octobre","novembre","décembre"};

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

    private String formatDate(Date d) {
        if (d == null) return "";
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return String.format("%02d/%02d/%04d",
            c.get(Calendar.DAY_OF_MONTH),
            c.get(Calendar.MONTH) + 1,
            c.get(Calendar.YEAR));
    }

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

    private String csv(String val) {
        if (val == null) return "";
        if (val.contains(";") || val.contains("\""))
            return "\"" + val.replace("\"", "\"\"") + "\"";
        return val;
    }

    private void setStatus(String msg, Color c) {
        statusLabel.setText(msg);
        statusLabel.setForeground(c);
    }

    private static class DashBorder extends AbstractBorder {
        private final Color color; private final int radius;
        DashBorder(Color c, int r) { color = c; radius = r; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    0, new float[]{8,6}, 0));
            g2.drawRoundRect(x+1, y+1, w-2, h-2, radius, radius);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) { return new Insets(6,6,6,6); }
    }

    public JButton getExportButton() { return exportButton; }
    public JButton getBrowseButton() { return browseButton; }
}
