package PaneCharts;

import model.Reservations;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

/**
 * Graphique "Taux de réservation sur une période" (barres empilées par mois).
 * L'utilisateur choisit une plage From/To et clique Apply.
 * Chaque barre est découpée par type (Emprunt / Cours / Maintenance).
 */
public class TauxEmpruntPeriodeChart extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final String[] TYPES  = {"Emprunt", "Cours", "Maintenance"};
    private static final Color[]  COLORS = {
        new Color(66,133,244), new Color(52,168,83), new Color(234,67,53)
    };

    private final JDateChooser    dateFrom = new JDateChooser();
    private final JDateChooser    dateTo   = new JDateChooser();
    private final StackedBarChart chart    = new StackedBarChart();

    public TauxEmpruntPeriodeChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][40!][grow][30!]"));

        JLabel title = new JLabel("Reservation Rate over a Period", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");

        // Barre de contrôle
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        controls.setOpaque(false);
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setPreferredSize(new Dimension(130, 28));
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setPreferredSize(new Dimension(130, 28));
        JButton btn = new JButton("Apply");
        btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                chart.setRange(dateFrom.getDate(), dateTo.getDate());
                chart.repaint();
            }
        });
        controls.add(new JLabel("From:")); controls.add(dateFrom);
        controls.add(new JLabel("To:"));   controls.add(dateTo);
        controls.add(btn);
        add(controls, "cell 0 1, growx");
        add(chart, "cell 0 2, grow");

        // Légende des couleurs
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 2));
        legend.setOpaque(false);
        for (int i = 0; i < TYPES.length; i++) {
            JLabel lbl = new JLabel("■ " + TYPES[i]);
            lbl.setForeground(COLORS[i]);
            lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
            legend.add(lbl);
        }
        add(legend, "cell 0 3, growx");
    }

    // -------------------------------------------------------------------------
    // Composant graphique interne
    // -------------------------------------------------------------------------

    private static class StackedBarChart extends JPanel {

        private static final String[] TYPES  = {"Emprunt", "Cours", "Maintenance"};
        private static final Color[]  COLORS = {
            new Color(66,133,244), new Color(52,168,83), new Color(234,67,53)
        };

        private Date from, to;

        StackedBarChart() { setBackground(Color.WHITE); }

        void setRange(Date f, Date t) { this.from = f; this.to = t; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // --- Agrégation : clé "AAAA-MM" → type → count ---
            // On stocke les clés dans une ArrayList séparée pour pouvoir les trier
            ArrayList<String> keys = new ArrayList<String>();
            HashMap<String, HashMap<String, Integer>> monthly = new HashMap<String, HashMap<String, Integer>>();

            for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
                Reservations r = Reservations.liste_reservations.get(i);
                Date d = r.getDate();
                if (d == null) continue;
                if (from != null && d.before(from)) continue;
                if (to   != null && d.after(to))   continue;

                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                String key = cal.get(Calendar.YEAR) + "-" + String.format("%02d", cal.get(Calendar.MONTH) + 1);

                String type = r.getType_emprunt();
                if (type == null || type.trim().isEmpty()) type = "Emprunt";

                // Créer la map du mois si elle n'existe pas encore
                if (!monthly.containsKey(key)) {
                    monthly.put(key, new HashMap<String, Integer>());
                    keys.add(key); // On note l'ordre d'apparition
                }
                HashMap<String, Integer> typeMap = monthly.get(key);
                if (typeMap.containsKey(type)) {
                    typeMap.put(type, typeMap.get(type) + 1);
                } else {
                    typeMap.put(type, 1);
                }
            }

            if (monthly.isEmpty()) {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
                String msg = (from == null && to == null)
                        ? "Select a period and click Apply"
                        : "No reservations in this period";
                g2.drawString(msg, getWidth() / 2 - 110, getHeight() / 2);
                return;
            }

            // Tri chronologique des clés "AAAA-MM" (tri alphabétique = tri chronologique)
            for (int i = 0; i < keys.size() - 1; i++) {
                for (int j = 0; j < keys.size() - 1 - i; j++) {
                    if (keys.get(j).compareTo(keys.get(j + 1)) > 0) {
                        String tmp = keys.get(j);
                        keys.set(j, keys.get(j + 1));
                        keys.set(j + 1, tmp);
                    }
                }
            }

            // Calcul du total maximal par mois (pour calibrer l'axe Y)
            int maxTotal = 1;
            for (int i = 0; i < keys.size(); i++) {
                HashMap<String, Integer> typeMap = monthly.get(keys.get(i));
                int total = 0;
                for (int val : typeMap.values()) total += val;
                if (total > maxTotal) maxTotal = total;
            }

            int n = keys.size();
            int W = getWidth(), H = getHeight();

            // Padding bas dynamique pour les labels "AAAA-MM" à 45°
            Font labelFont = new Font("Tahoma", Font.PLAIN, 10);
            g2.setFont(labelFont);
            int sampleW = g2.getFontMetrics().stringWidth("2021-09");
            int padB = (int) (sampleW * Math.sin(Math.PI / 4)) + 20;

            int padL = 50, padR = 20, padT = 20;
            int chartW = W - padL - padR;
            int chartH = H - padT - padB;
            int barW   = Math.max(4, chartW / n - 4);

            // --- Axes ---
            g2.setColor(new Color(180, 180, 180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            // --- Grille Y et ticks ---
            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int y = padT + chartH - i * chartH / 4;
                g2.setColor(new Color(220, 220, 220)); g2.drawLine(padL, y, W - padR, y);
                g2.setColor(Color.GRAY);
                String tick = String.valueOf(maxTotal * i / 4);
                g2.drawString(tick, padL - g2.getFontMetrics().stringWidth(tick) - 4, y + 4);
            }

            // --- Barres empilées + labels X ---
            for (int i = 0; i < n; i++) {
                String key = keys.get(i);
                HashMap<String, Integer> typeMap = monthly.get(key);
                int x     = padL + i * (chartW / n) + 2;
                int yBase = padT + chartH; // On empile depuis le bas

                for (int t = 0; t < TYPES.length; t++) {
                    int count = 0;
                    if (typeMap.containsKey(TYPES[t])) {
                        count = typeMap.get(TYPES[t]);
                    }
                    if (count == 0) continue;
                    int segH = (int) ((double) count / maxTotal * chartH);
                    yBase -= segH;
                    g2.setColor(COLORS[t]);
                    g2.fillRoundRect(x, yBase, barW, segH, 3, 3);
                    g2.setColor(COLORS[t].darker());
                    g2.drawRoundRect(x, yBase, barW, segH, 3, 3);
                }

                // Label X en diagonale 45°
                g2.setFont(labelFont);
                g2.setColor(Color.DARK_GRAY);
                Graphics2D gr = (Graphics2D) g2.create();
                gr.translate(x + barW / 2, padT + chartH + 6);
                gr.rotate(Math.PI / 4);
                gr.drawString(key, 0, 0);
                gr.dispose();
            }
        }
    }
}
