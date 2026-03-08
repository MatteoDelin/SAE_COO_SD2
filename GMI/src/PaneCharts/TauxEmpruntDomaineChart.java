package PaneCharts;

import model.Reservations;
import model.Ressources;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;

/**
 * Graphique "Taux de réservation par domaine" (camembert).
 * L'utilisateur choisit un domaine dans la liste déroulante et clique "Apply".
 * Un pie chart affiche la répartition des types d'emprunt pour ce domaine.
 */
public class TauxEmpruntDomaineChart extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JComboBox<String> cbDomain = new JComboBox<String>();
    private final PieChart          chart    = new PieChart();

    public TauxEmpruntDomaineChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][40!][grow]"));

        JLabel title = new JLabel("Reservation Rate per Domain", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");

        // --- Barre de contrôle ---
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        controls.setOpaque(false);

        // Remplissage du combo avec les domaines distincts (sans doublons)
        ArrayList<String> domains = new ArrayList<String>();
        for (int i = 0; i < Ressources.liste_ressource.size(); i++) {
            String d = Ressources.liste_ressource.get(i).getDomaine();
            if (d == null || d.trim().isEmpty()) continue;
            d = d.trim();
            // Ajouter seulement si pas déjà présent
            boolean found = false;
            for (int j = 0; j < domains.size(); j++) {
                if (domains.get(j).equals(d)) { found = true; break; }
            }
            if (!found) domains.add(d);
        }
        // Tri alphabétique simple
        for (int i = 0; i < domains.size() - 1; i++) {
            for (int j = 0; j < domains.size() - 1 - i; j++) {
                if (domains.get(j).compareTo(domains.get(j + 1)) > 0) {
                    String tmp = domains.get(j);
                    domains.set(j, domains.get(j + 1));
                    domains.set(j + 1, tmp);
                }
            }
        }
        for (int i = 0; i < domains.size(); i++) cbDomain.addItem(domains.get(i));
        cbDomain.setPreferredSize(new Dimension(200, 28));

        JButton btn = new JButton("Apply");
        btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                chart.setDomain((String) cbDomain.getSelectedItem());
                chart.repaint();
            }
        });

        controls.add(new JLabel("Domain:")); controls.add(cbDomain); controls.add(btn);
        add(controls, "cell 0 1, growx");
        add(chart, "cell 0 2, grow");
    }

    // -------------------------------------------------------------------------
    // Composant graphique interne
    // -------------------------------------------------------------------------

    private static class PieChart extends JPanel {

        private static final String[] TYPES  = {"Emprunt", "Cours", "Maintenance"};
        private static final Color[]  COLORS = {
            new Color(66,133,244), new Color(52,168,83), new Color(234,67,53)
        };

        private String domain;

        PieChart() { setBackground(Color.WHITE); }

        void setDomain(String d) { this.domain = d; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (domain == null) { drawMsg(g, "Select a domain and click Apply"); return; }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // --- Comptage par type pour le domaine sélectionné ---
            int[] counts = new int[TYPES.length]; // counts[0]=Emprunt, [1]=Cours, [2]=Maintenance
            int total = 0;
            for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
                Reservations r = Reservations.liste_reservations.get(i);
                if (!domain.equals(r.getRessource().getDomaine())) continue;
                String type = r.getType_emprunt();
                if (type == null) type = "Emprunt";
                for (int t = 0; t < TYPES.length; t++) {
                    if (TYPES[t].equals(type)) { counts[t]++; break; }
                }
                total++;
            }

            if (total == 0) { drawMsg(g, "No reservations for this domain"); return; }

            // --- Dessin du camembert ---
            int W = getWidth(), H = getHeight();
            int size = Math.min(W, H) - 100;
            int cx = W / 2 - size / 4;
            int cy = H / 2;

            int startAngle = 0;
            for (int i = 0; i < TYPES.length; i++) {
                if (counts[i] == 0) continue;
                int arc = (int) Math.round(360.0 * counts[i] / total);
                g2.setColor(COLORS[i]);
                g2.fillArc(cx - size/2, cy - size/2, size, size, startAngle, arc);
                g2.setColor(Color.WHITE);
                g2.drawArc(cx - size/2, cy - size/2, size, size, startAngle, arc);
                startAngle += arc;
            }

            // --- Légende à droite ---
            int lx = cx + size/2 + 20;
            int ly = cy - 60;
            g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
            for (int i = 0; i < TYPES.length; i++) {
                double pct = 100.0 * counts[i] / total;
                g2.setColor(COLORS[i]);
                g2.fillRect(lx, ly + i * 28, 16, 16);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(TYPES[i] + "  " + counts[i] + " (" + String.format("%.1f", pct) + "%)",
                        lx + 22, ly + i * 28 + 13);
            }
        }

        private void drawMsg(Graphics g, String msg) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Tahoma", Font.ITALIC, 14));
            g.drawString(msg, getWidth() / 2 - 120, getHeight() / 2);
        }
    }
}
