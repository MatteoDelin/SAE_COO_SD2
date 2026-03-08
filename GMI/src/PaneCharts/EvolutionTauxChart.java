package PaneCharts;

import model.Reservations;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import net.miginfocom.swing.MigLayout;

/**
 * Graphique "Évolution du taux de réservation dans le temps" (courbe avec aire).
 * Affiche le nombre de réservations par mois avec une courbe et une aire translucide.
 */
public class EvolutionTauxChart extends JPanel {
    private static final long serialVersionUID = 1L;

    public EvolutionTauxChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][grow]"));
        JLabel title = new JLabel("Reservation Rate Evolution over Time", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");
        add(new LineChart(), "cell 0 1, grow");
    }

    // -------------------------------------------------------------------------
    // Composant graphique interne
    // -------------------------------------------------------------------------

    private static class LineChart extends JPanel {

        LineChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // --- Agrégation mensuelle ---
            // On stocke les clés "AAAA-MM" et leurs compteurs dans deux ArrayList parallèles
            ArrayList<String> keys   = new ArrayList<String>();
            ArrayList<Integer> vals  = new ArrayList<Integer>();

            for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
                Reservations r = Reservations.liste_reservations.get(i);
                if (r.getDate() == null) continue;
                Calendar c = Calendar.getInstance();
                c.setTime(r.getDate());
                String key = String.format("%04d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);

                // Chercher si la clé existe déjà
                boolean found = false;
                for (int j = 0; j < keys.size(); j++) {
                    if (keys.get(j).equals(key)) {
                        vals.set(j, vals.get(j) + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    keys.add(key);
                    vals.add(1);
                }
            }

            // Tri chronologique : "AAAA-MM" en ordre alphabétique = ordre chronologique
            for (int i = 0; i < keys.size() - 1; i++) {
                for (int j = 0; j < keys.size() - 1 - i; j++) {
                    if (keys.get(j).compareTo(keys.get(j + 1)) > 0) {
                        String tmpK = keys.get(j);
                        int tmpV    = vals.get(j);
                        keys.set(j, keys.get(j + 1));
                        vals.set(j, vals.get(j + 1));
                        keys.set(j + 1, tmpK);
                        vals.set(j + 1, tmpV);
                    }
                }
            }

            if (keys.size() < 2) {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
                g2.drawString("Not enough data to display a trend", getWidth() / 2 - 130, getHeight() / 2);
                return;
            }

            // Valeur maximale pour calibrer l'axe Y
            int maxVal = 1;
            for (int i = 0; i < vals.size(); i++) {
                if (vals.get(i) > maxVal) maxVal = vals.get(i);
            }

            int n = keys.size();
            int W = getWidth(), H = getHeight();

            // Padding bas dynamique pour les labels "AAAA-MM" à 45°
            Font labelFont = new Font("Tahoma", Font.PLAIN, 10);
            g2.setFont(labelFont);
            int sampleW = g2.getFontMetrics().stringWidth("2021-09");
            int padB = (int) (sampleW * Math.sin(Math.PI / 4)) + 20;

            int padL = 55, padR = 20, padT = 20;
            int chartW = W - padL - padR;
            int chartH = H - padT - padB;

            // --- Axes ---
            g2.setColor(new Color(180, 180, 180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            // --- Grille Y et ticks ---
            for (int i = 0; i <= 4; i++) {
                int y = padT + chartH - i * chartH / 4;
                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(padL, y, W - padR, y);
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
                String tick = String.valueOf(maxVal * i / 4);
                g2.drawString(tick, padL - g2.getFontMetrics().stringWidth(tick) - 4, y + 4);
            }

            // --- Calcul des coordonnées de chaque point ---
            int[] px = new int[n];
            int[] py = new int[n];
            for (int i = 0; i < n; i++) {
                px[i] = padL + (int) ((double) i / (n - 1) * chartW);
                py[i] = padT + chartH - (int) ((double) vals.get(i) / maxVal * chartH);
            }

            // --- Aire translucide sous la courbe ---
            // Polygone fermé : points + fermeture par le bas
            int[] polyX = new int[n + 2];
            int[] polyY = new int[n + 2];
            for (int i = 0; i < n; i++) {
                polyX[i] = px[i];
                polyY[i] = py[i];
            }
            polyX[n]   = px[n - 1]; polyY[n]   = padT + chartH; // Coin bas-droit
            polyX[n+1] = px[0];     polyY[n+1] = padT + chartH; // Coin bas-gauche
            g2.setColor(new Color(66, 133, 244, 40));
            g2.fillPolygon(polyX, polyY, n + 2);

            // --- Tracé de la courbe ---
            g2.setColor(new Color(66, 133, 244));
            g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int i = 0; i < n - 1; i++) {
                g2.drawLine(px[i], py[i], px[i+1], py[i+1]);
            }

            // --- Points, valeurs et labels X ---
            for (int i = 0; i < n; i++) {
                // Point : cercle blanc avec contour bleu
                g2.setColor(Color.WHITE);
                g2.fillOval(px[i] - 5, py[i] - 5, 10, 10);
                g2.setColor(new Color(66, 133, 244));
                g2.drawOval(px[i] - 5, py[i] - 5, 10, 10);

                // Valeur au-dessus du point
                g2.setFont(new Font("Tahoma", Font.BOLD, 9));
                g2.setColor(Color.DARK_GRAY);
                String val = String.valueOf(vals.get(i));
                g2.drawString(val, px[i] - g2.getFontMetrics().stringWidth(val) / 2, py[i] - 8);

                // Label X en diagonale 45°
                g2.setFont(labelFont);
                Graphics2D gr = (Graphics2D) g2.create();
                gr.translate(px[i], padT + chartH + 6);
                gr.rotate(Math.PI / 4);
                gr.setColor(Color.DARK_GRAY);
                gr.drawString(keys.get(i), 0, 0);
                gr.dispose();
            }
        }
    }
}
