package PaneCharts;

import model.Reservations;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import net.miginfocom.swing.MigLayout;

/**
 * Graphique "Durée moyenne des réservations par domaine".
 * Bar chart horizontal trié par durée moyenne décroissante.
 * Dégradé bleu clair → bleu foncé selon la durée.
 */
public class DureeMoyenneDomaineChart extends JPanel {
    private static final long serialVersionUID = 1L;

    public DureeMoyenneDomaineChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][grow]"));
        JLabel title = new JLabel("Average Reservation Duration per Domain", SwingConstants.CENTER);
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

            // --- Calcul durée totale et nombre de réservations par domaine ---
            HashMap<String, Integer> totalMinutes = new HashMap<String, Integer>();
            HashMap<String, Integer> nbReserv     = new HashMap<String, Integer>();

            for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
                Reservations r = Reservations.liste_reservations.get(i);
                String domain = r.getRessource().getDomaine();
                if (domain == null || domain.trim().isEmpty()) domain = "(none)";

                if (totalMinutes.containsKey(domain)) {
                    totalMinutes.put(domain, totalMinutes.get(domain) + r.getDuree());
                    nbReserv.put(domain, nbReserv.get(domain) + 1);
                } else {
                    totalMinutes.put(domain, r.getDuree());
                    nbReserv.put(domain, 1);
                }
            }

            if (totalMinutes.isEmpty()) {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
                g2.drawString("No data available", getWidth() / 2 - 70, getHeight() / 2);
                return;
            }

            // --- Calcul des moyennes ---
            ArrayList<String> domains = new ArrayList<String>(totalMinutes.keySet());
            HashMap<String, Double> averages = new HashMap<String, Double>();
            for (int i = 0; i < domains.size(); i++) {
                String d = domains.get(i);
                averages.put(d, (double) totalMinutes.get(d) / nbReserv.get(d));
            }

            // Tri décroissant par durée moyenne
            for (int i = 0; i < domains.size() - 1; i++) {
                for (int j = 0; j < domains.size() - 1 - i; j++) {
                    if (averages.get(domains.get(j)) < averages.get(domains.get(j + 1))) {
                        String tmp = domains.get(j);
                        domains.set(j, domains.get(j + 1));
                        domains.set(j + 1, tmp);
                    }
                }
            }

            // Recherche de la durée maximale pour calibrer l'axe
            double maxAvg = 1;
            for (int i = 0; i < domains.size(); i++) {
                if (averages.get(domains.get(i)) > maxAvg) {
                    maxAvg = averages.get(domains.get(i));
                }
            }

            int n = domains.size();
            int W = getWidth(), H = getHeight();

            // Padding gauche dynamique selon la largeur des noms de domaine
            Font domainFont = new Font("Tahoma", Font.BOLD, 11);
            g2.setFont(domainFont);
            FontMetrics fmD = g2.getFontMetrics();
            int maxDomainW = 0;
            for (int i = 0; i < domains.size(); i++) {
                int w = fmD.stringWidth(domains.get(i));
                if (w > maxDomainW) maxDomainW = w;
            }
            int padL = maxDomainW + 14;

            // Padding droit dynamique selon la largeur des étiquettes valeur
            Font valFont = new Font("Tahoma", Font.PLAIN, 11);
            g2.setFont(valFont);
            FontMetrics fmV = g2.getFontMetrics();
            int maxValW = 0;
            for (int i = 0; i < domains.size(); i++) {
                int avgMin = (int) Math.round(averages.get(domains.get(i)));
                int count  = nbReserv.get(domains.get(i));
                String lbl;
                if (avgMin >= 60) {
                    lbl = (avgMin / 60) + "h" + String.format("%02d", avgMin % 60) + "  (" + count + " reserv)";
                } else {
                    lbl = avgMin + " min  (" + count + " reserv)";
                }
                int w = fmV.stringWidth(lbl);
                if (w > maxValW) maxValW = w;
            }
            int padR = maxValW + 14;

            int padT = 20, padB = 40;
            int chartW = W - padL - padR;
            int chartH = H - padT - padB;
            int rowH   = chartH / n;
            int barH   = Math.min(rowH - 10, 32);

            // Couleurs pour le dégradé (bleu clair → bleu foncé)
            Color colorMin = new Color(160, 200, 255);
            Color colorMax = new Color(20, 70, 180);

            // --- Grille verticale et ticks axe X ---
            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            FontMetrics fmTick = g2.getFontMetrics();
            for (int i = 0; i <= 4; i++) {
                int x = padL + i * chartW / 4;
                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(x, padT, x, padT + chartH);
                int labelVal = (int) (maxAvg * i / 4);
                String lbl;
                if (labelVal >= 60) {
                    lbl = (labelVal / 60) + "h" + String.format("%02d", labelVal % 60);
                } else {
                    lbl = labelVal + " min";
                }
                g2.setColor(Color.GRAY);
                g2.drawString(lbl, x - fmTick.stringWidth(lbl) / 2, padT + chartH + 14);
            }

            // --- Axes ---
            g2.setColor(new Color(180, 180, 180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            // --- Barres + labels ---
            for (int i = 0; i < n; i++) {
                String domain = domains.get(i);
                double avg   = averages.get(domain);
                int count    = nbReserv.get(domain);
                double ratio = avg / maxAvg;

                int barW = (int) (ratio * chartW);
                int y    = padT + i * rowH + (rowH - barH) / 2;

                // Couleur interpolée entre colorMin et colorMax
                int r  = (int) (colorMin.getRed()   + ratio * (colorMax.getRed()   - colorMin.getRed()));
                int gr = (int) (colorMin.getGreen() + ratio * (colorMax.getGreen() - colorMin.getGreen()));
                int b  = (int) (colorMin.getBlue()  + ratio * (colorMax.getBlue()  - colorMin.getBlue()));
                Color barColor = new Color(r, gr, b);

                g2.setColor(barColor);
                g2.fillRoundRect(padL, y, barW, barH, 8, 8);
                g2.setColor(barColor.darker());
                g2.drawRoundRect(padL, y, barW, barH, 8, 8);

                // Nom du domaine à gauche
                g2.setFont(domainFont);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(domain, padL - fmD.stringWidth(domain) - 8, y + barH / 2 + 4);

                // Valeur durée + compteur à droite
                int avgMin = (int) Math.round(avg);
                String valLbl;
                if (avgMin >= 60) {
                    valLbl = (avgMin / 60) + "h" + String.format("%02d", avgMin % 60) + "  (" + count + " reserv)";
                } else {
                    valLbl = avgMin + " min  (" + count + " reserv)";
                }
                g2.setFont(valFont);
                g2.setColor(new Color(80, 80, 80));
                g2.drawString(valLbl, padL + barW + 8, y + barH / 2 + 4);
            }
        }
    }
}
