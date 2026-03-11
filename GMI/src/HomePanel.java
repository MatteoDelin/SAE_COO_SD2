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
import java.time.format.DateTimeFormatter;
import java.util.*;
import net.miginfocom.swing.MigLayout;

public class HomePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final DefaultTableModel modelUsers = new DefaultTableModel(
            new String[]{"Nom"}, 0);
    private final DefaultTableModel modelRess = new DefaultTableModel(
            new String[]{"Name", "Description", "Domain"}, 0);
    private final DefaultTableModel modelReserv = new DefaultTableModel(
            new String[]{"User", "Ressources", "Date", "Time", "Duration (min)", "Type"}, 0);

    private JLabel  statusLabel;
    private JButton exportButton;
    private JButton browseButton;

    private final CSV csv = new CSV();

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
        tabs.addTab("Users",        buildScrollTable(modelUsers));
        tabs.addTab("Ressources",   buildScrollTable(modelRess));
        tabs.addTab("Reservations", buildScrollTable(modelReserv));
        add(tabs, "cell 0 2, grow");

        statusLabel = new JLabel(
                "No file loaded — drag a CSV or click Browse.",
                SwingConstants.LEFT);
        statusLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        add(statusLabel, "cell 0 3, growx");

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bar.setOpaque(false);

        browseButton = new JButton("Browse…");
        browseButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { openChooser(); }
        });
        bar.add(browseButton);

        exportButton = new JButton("Export CSV");
        exportButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        exportButton.setBackground(new Color(50, 120, 190));
        exportButton.setForeground(Color.WHITE);
        exportButton.setOpaque(true);
        exportButton.setBorderPainted(false);
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { exporterCSV(); }
        });
        bar.add(exportButton);

        add(bar, "cell 0 4, growx");
    }

    // =========================================================================
    // Construction de l'interface
    // =========================================================================

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
            + "&nbsp;<b>Drag and drop a CSV file here</b> &nbsp;(or click to browse)<br>"
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
            @Override public void dragExit(DropTargetEvent d) { resetZone(zone); }
            @Override public void drop(DropTargetDropEvent d) {
                resetZone(zone);
                try {
                    d.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    java.util.List<File> files = (java.util.List<File>) d.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        File f = files.get(0);
                        if (f.getName().toLowerCase().endsWith(".csv")) {
                            chargerCSV(f);
                        } else {
                            setStatus("File rejected: not a .csv file", Color.RED);
                        }
                    }
                    d.dropComplete(true);
                } catch (Exception ex) {
                    d.dropComplete(false);
                    setStatus("Drop error : " + ex.getMessage(), Color.RED);
                }
            }
        }, true);

        return zone;
    }

    private void resetZone(JPanel zone) {
        zone.setBackground(new Color(225, 235, 250));
        zone.setBorder(new DashBorder(new Color(100, 140, 210), 8));
    }

    // =========================================================================
    // Sélection de fichier
    // =========================================================================

    private void openChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Fichiers CSV (*.csv)", "csv"));
        fc.setDialogTitle("Open a CSV file");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            chargerCSV(fc.getSelectedFile());
    }

    // =========================================================================
    // Import CSV
    // =========================================================================

    private void chargerCSV(File fichier) {

        modelUsers.setRowCount(0);
        modelRess.setRowCount(0);
        modelReserv.setRowCount(0);

        try {
            csv.chargement(fichier.getAbsolutePath());
        } catch (IOException ex) {
            setStatus("Read error : " + ex.getMessage(), Color.RED);
            return;
        }

        // Remplissage des tables depuis les listes statiques du modèle
        for (Utilisateur u : Utilisateur.liste_utilisateur)
            modelUsers.addRow(new Object[]{ u.getNom() });

        for (Ressources r : Ressources.liste_ressource)
            modelRess.addRow(new Object[]{ r.getNom(), r.getDescription(), r.getDomaine() });

        for (Reservations r : Reservations.liste_reservations)
            modelReserv.addRow(new Object[]{
                r.getUser().getNom(),
                r.getRessource().getNom(),
                formatDate(r.getDate()),
                r.getHeure() != null
                    ? r.getHeure().format(DateTimeFormatter.ofPattern("HH:mm"))
                    : "",
                r.getDuree(),
                r.getType_emprunt()
            });

        setStatus(
            String.format("Import complete — %d user(s), %d ressource(s), %d reservation(s)",
                Utilisateur.liste_utilisateur.size(),
                Ressources.liste_ressource.size(),
                Reservations.liste_reservations.size()),
            new Color(0, 130, 0));
    }

    // =========================================================================
    // Export CSV
    // =========================================================================

    void exporterCSV() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("export_gmi.csv"));
        fc.setFileFilter(new FileNameExtensionFilter("Fichiers CSV (*.csv)", "csv"));
        fc.setDialogTitle("Save CSV");
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File dest = fc.getSelectedFile();
        if (!dest.getName().toLowerCase().endsWith(".csv"))
            dest = new File(dest.getAbsolutePath() + ".csv");

        try {
            csv.export(dest.getAbsolutePath());
        } catch (IOException ex) {
            setStatus("Export error : " + ex.getMessage(), Color.RED);
            JOptionPane.showMessageDialog(this,
                    "Export error:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setStatus("Export successful → " + dest.getAbsolutePath(), new Color(0, 130, 0));
        JOptionPane.showMessageDialog(this,
                "Export successful!\n" + dest.getAbsolutePath(),
                "Export CSV", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // Utilitaires
    // =========================================================================

    private String formatDate(Date d) {
        if (d == null) return "";
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return String.format("%02d/%02d/%04d",
            c.get(Calendar.DAY_OF_MONTH),
            c.get(Calendar.MONTH) + 1,
            c.get(Calendar.YEAR));
    }

    private void setStatus(String msg, Color c) {
        statusLabel.setText(msg);
        statusLabel.setForeground(c);
    }

    // =========================================================================
    // Getters pour MainWindow
    // =========================================================================

    public JButton getExportButton() { return exportButton; }
    public JButton getBrowseButton() { return browseButton; }

    // =========================================================================
    // Bordure pointillée de la zone de dépôt
    // =========================================================================

    private static class DashBorder extends AbstractBorder {
        private final Color color;
        private final int   radius;

        DashBorder(Color c, int r) { color = c; radius = r; }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    0, new float[]{8, 6}, 0));
            g2.drawRoundRect(x + 1, y + 1, w - 2, h - 2, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) { return new Insets(6, 6, 6, 6); }
    }
}
