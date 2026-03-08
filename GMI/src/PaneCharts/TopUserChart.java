package PaneCharts;

import model.Reservations;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import net.miginfocom.swing.MigLayout;

/**
 * Graphique "Top 10 utilisateurs les plus actifs".
 * Bar chart horizontal trié par nombre de réservations décroissant.
 * Le 1er utilisateur est affiché en rouge.
 * Paddings gauche et droit calculés dynamiquement.
 */
public class TopUserChart extends JPanel {
    private static final long serialVersionUID = 1L;

    public TopUserChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][grow]"));
        JLabel title = new JLabel("Most Active Users", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");
        add(new HBarChart(), "cell 0 1, grow");
    }

    // -------------------------------------------------------------------------
    // Composant graphique interne
    // -------------------------------------------------------------------------

    private static class HBarChart extends JPanel {

        HBarChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // --- Comptage des réservations par utilisateur ---
            HashMap<String, Integer> counts = new HashMap<String, Integer>();
            for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
                Reservations r = Reservations.liste_reservations.get(i);
                String nom = r.getUser().getNom();
                if (counts.containsKey(nom)) {
                    counts.put(nom, counts.get(nom) + 1);
                } else {
                    counts.put(nom, 1);
                }
            }

            if (counts.isEmpty()) { drawEmpty(g2); return; }

            // Transfert dans une ArrayList pour trier
            ArrayList<String> noms = new ArrayList<String>(counts.keySet());

            // Tri décroissant par nombre de réservations
            for (int i = 0; i < noms.size() - 1; i++) {
                for (int j = 0; j < noms.size() - 1 - i; j++) {
                    if (counts.get(noms.get(j)) < counts.get(noms.get(j + 1))) {
                        String tmp = noms.get(j);
                        noms.set(j, noms.get(j + 1));
                        noms.set(j + 1, tmp);
                    }
                }
            }

            // Garder le top 10
            while (noms.size() > 10) {
                noms.remove(noms.size() - 1);
            }

            int maxVal = counts.get(noms.get(0));
            int n      = noms.size();
            int W = getWidth(), H = getHeight();

            // Padding gauche dynamique selon la largeur des noms
            Font labelFont = new Font("Tahoma", Font.BOLD, 11);
            g2.setFont(labelFont);
            FontMetrics fm = g2.getFontMetrics();
            int maxNameW = 0;
            for (int i = 0; i < noms.size(); i++) {
                int w = fm.stringWidth(noms.get(i));
                if (w > maxNameW) maxNameW = w;
            }
            int padL = maxNameW + 14;

            // Padding droit dynamique selon la largeur des valeurs
            Font valFont = new Font("Tahoma", Font.BOLD, 11);
            g2.setFont(valFont);
            FontMetrics fmV = g2.getFontMetrics();
            int padR = fmV.stringWidth(String.valueOf(maxVal)) + 12;

            int padT = 20, padB = 20;
            int chartW = W - padL - padR;
            int chartH = H - padT - padB;
            int rowH   = chartH / n;
            int barH   = Math.min(rowH - 8, 32);

            // --- Axes ---
            g2.setColor(new Color(180, 180, 180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            // --- Barres + labels ---
            for (int i = 0; i < n; i++) {
                String nom = noms.get(i);
                int val  = counts.get(nom);
                int barW = (int) ((double) val / maxVal * chartW);
                int y    = padT + i * rowH + (rowH - barH) / 2;

                // Top 1 en rouge, les autres en bleu
                Color c = (i == 0) ? new Color(234, 67, 53) : new Color(66, 133, 244);
                g2.setColor(c);
                g2.fillRoundRect(padL, y, barW, barH, 6, 6);
                g2.setColor(c.darker());
                g2.drawRoundRect(padL, y, barW, barH, 6, 6);

                // Nom à gauche (complet grâce au padding dynamique)
                g2.setFont(labelFont);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(nom, padL - fm.stringWidth(nom) - 8, y + barH / 2 + 4);

                // Valeur à droite
                g2.setFont(valFont);
                g2.drawString(String.valueOf(val), padL + barW + 6, y + barH / 2 + 4);
            }
        }

        private void drawEmpty(Graphics2D g2) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
            g2.drawString("No data available", getWidth() / 2 - 70, getHeight() / 2);
        }
    }
}
