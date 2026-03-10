package PaneCharts;

import model.Reservations;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import net.miginfocom.swing.MigLayout;

/**
 * Graphique "Top 5 ressources les plus empruntées".
 * Bar chart vertical : chaque barre = une ressource, hauteur = nombre de réservations.
 * Padding bas calculé dynamiquement pour que les labels soient toujours visibles.
 */
public class TopRessourcesChart extends JPanel {
    private static final long serialVersionUID = 1L;

    public TopRessourcesChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][grow]"));
        JLabel title = new JLabel("Top 5 Most Borrowed Ressources", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");
        add(new BarChart(), "cell 0 1, grow");
    }

    // -------------------------------------------------------------------------
    // Composant graphique interne
    // -------------------------------------------------------------------------

    private static class BarChart extends JPanel {

        BarChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // --- Comptage du nombre de réservations par ressource ---
            HashMap<String, Integer> counts = new HashMap<String, Integer>();
            for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
                Reservations r = Reservations.liste_reservations.get(i);
                String nom = r.getRessource().getNom();
                if (counts.containsKey(nom)) {
                    counts.put(nom, counts.get(nom) + 1);
                } else {
                    counts.put(nom, 1);
                }
            }

            if (counts.isEmpty()) { drawEmpty(g2); return; }

            // Transfert dans une ArrayList pour pouvoir trier
            ArrayList<String> noms = new ArrayList<String>(counts.keySet());

            // Tri décroissant par nombre de réservations (tri à bulles simple)
            for (int i = 0; i < noms.size() - 1; i++) {
                for (int j = 0; j < noms.size() - 1 - i; j++) {
                    if (counts.get(noms.get(j)) < counts.get(noms.get(j + 1))) {
                        String tmp = noms.get(j);
                        noms.set(j, noms.get(j + 1));
                        noms.set(j + 1, tmp);
                    }
                }
            }

            // Garder uniquement le top 5
            while (noms.size() > 5) {
                noms.remove(noms.size() - 1);
            }

            int maxVal = counts.get(noms.get(0));
            int n      = noms.size();
            int W = getWidth(), H = getHeight();

            // Padding bas dynamique selon la longueur des labels (inclinés à 45°)
            Font labelFont = new Font("Tahoma", Font.PLAIN, 11);
            g2.setFont(labelFont);
            FontMetrics fm = g2.getFontMetrics();
            int maxLabelW = 0;
            for (int i = 0; i < noms.size(); i++) {
                int w = fm.stringWidth(noms.get(i));
                if (w > maxLabelW) maxLabelW = w;
            }
            int padB = (int) (maxLabelW * Math.sin(Math.PI / 4)) + 20;

            int padL = 50, padR = 20, padT = 30;
            int chartW = W - padL - padR;
            int chartH = H - padT - padB;
            int barW   = chartW / n - 10;

            // Couleurs distinctes pour chaque barre
            Color[] palette = {
                new Color(66,133,244), new Color(52,168,83),
                new Color(251,188,5),  new Color(234,67,53),
                new Color(103,78,167)
            };

            // --- Axes ---
            g2.setColor(new Color(180, 180, 180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            // --- Grille horizontale et ticks axe Y ---
            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int y = padT + chartH - i * chartH / 4;
                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(padL, y, W - padR, y);
                g2.setColor(Color.GRAY);
                String tick = String.valueOf(maxVal * i / 4);
                g2.drawString(tick, padL - g2.getFontMetrics().stringWidth(tick) - 4, y + 4);
            }

            // --- Barres + labels ---
            for (int i = 0; i < n; i++) {
                String nom = noms.get(i);
                int val  = counts.get(nom);
                int barH = (int) ((double) val / maxVal * chartH);
                int x    = padL + i * (chartW / n) + 5;
                int y    = padT + chartH - barH;

                // Barre colorée
                g2.setColor(palette[i % palette.length]);
                g2.fillRoundRect(x, y, barW, barH, 6, 6);
                g2.setColor(palette[i % palette.length].darker());
                g2.drawRoundRect(x, y, barW, barH, 6, 6);

                // Valeur au-dessus de la barre
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Tahoma", Font.BOLD, 11));
                String valStr = String.valueOf(val);
                int tx = x + barW / 2 - g2.getFontMetrics().stringWidth(valStr) / 2;
                g2.drawString(valStr, tx, y - 5);

                // Label X en diagonale 45°
                g2.setFont(labelFont);
                Graphics2D gr = (Graphics2D) g2.create();
                gr.translate(x + barW / 2, padT + chartH + 6);
                gr.rotate(Math.PI / 4);
                gr.setColor(Color.DARK_GRAY);
                gr.drawString(nom, 0, 0);
                gr.dispose();
            }
        }

        private void drawEmpty(Graphics2D g2) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
            g2.drawString("No data available", getWidth() / 2 - 70, getHeight() / 2);
        }
    }
}
