import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.miginfocom.swing.MigLayout;

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

    private static class HBarChart extends JPanel {
        HBarChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Calcul durée totale et count par domaine
            Map<String, int[]> data = new LinkedHashMap<>(); // [totalMinutes, count]
            for (Reservations r : Reservations.liste_reservations) {
                String domain = r.getRessource().getDomaine();
                if (domain == null || domain.trim().isEmpty()) domain = "(none)";
                int duree = r.getDuree();
                data.computeIfAbsent(domain, k -> new int[]{0, 0});
                data.get(domain)[0] += duree;
                data.get(domain)[1]++;
            }

            if (data.isEmpty()) {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
                g2.drawString("No data available", getWidth() / 2 - 70, getHeight() / 2);
                return;
            }

            // Calcul des moyennes et tri décroissant
            List<String> domains = new ArrayList<>(data.keySet());
            Map<String, Double> averages = new LinkedHashMap<>();
            for (String d : domains)
                averages.put(d, (double) data.get(d)[0] / data.get(d)[1]);
            domains.sort((a, b) -> Double.compare(averages.get(b), averages.get(a)));

            double maxAvg = domains.stream().mapToDouble(averages::get).max().orElse(1);
            int n = domains.size();

            int W = getWidth(), H = getHeight();
            int padL = 130, padR = 100, padT = 20, padB = 40;
            int chartW = W - padL - padR;
            int chartH = H - padT - padB;
            int rowH = chartH / n;
            int barH = Math.min(rowH - 10, 32);

            // Gradient de couleur selon la durée (bleu clair → bleu foncé)
            Color colorMin = new Color(160, 200, 255);
            Color colorMax = new Color(20,  70,  180);

            // Grille verticale légère
            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int x = padL + i * chartW / 4;
                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(x, padT, x, padT + chartH);
                int labelVal = (int) (maxAvg * i / 4);
                String lbl = labelVal >= 60
                        ? String.format("%dh%02d", labelVal / 60, labelVal % 60)
                        : labelVal + " min";
                g2.setColor(Color.GRAY);
                g2.drawString(lbl, x - g2.getFontMetrics().stringWidth(lbl) / 2, padT + chartH + 14);
            }

            // Axe gauche
            g2.setColor(new Color(180, 180, 180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            for (int i = 0; i < n; i++) {
                String domain = domains.get(i);
                double avg = averages.get(domain);
                int count = data.get(domain)[1];
                double ratio = avg / maxAvg;

                int barW = (int) (ratio * chartW);
                int y = padT + i * rowH + (rowH - barH) / 2;

                // Couleur interpolée
                int r = (int) (colorMin.getRed()   + ratio * (colorMax.getRed()   - colorMin.getRed()));
                int gr = (int) (colorMin.getGreen() + ratio * (colorMax.getGreen() - colorMin.getGreen()));
                int b = (int) (colorMin.getBlue()  + ratio * (colorMax.getBlue()  - colorMin.getBlue()));
                Color barColor = new Color(r, gr, b);

                g2.setColor(barColor);
                g2.fillRoundRect(padL, y, barW, barH, 8, 8);
                g2.setColor(barColor.darker());
                g2.drawRoundRect(padL, y, barW, barH, 8, 8);

                // Label domaine (gauche)
                g2.setFont(new Font("Tahoma", Font.BOLD, 11));
                g2.setColor(Color.DARK_GRAY);
                String dlbl = domain.length() > 18 ? domain.substring(0, 17) + "…" : domain;
                g2.drawString(dlbl, padL - g2.getFontMetrics().stringWidth(dlbl) - 8, y + barH / 2 + 4);

                // Valeur durée moyenne (droite de la barre)
                int avgMin = (int) Math.round(avg);
                String valLbl = avgMin >= 60
                        ? String.format("%dh%02d  (%d resa)", avgMin / 60, avgMin % 60, count)
                        : String.format("%d min  (%d resa)", avgMin, count);
                g2.setFont(new Font("Tahoma", Font.PLAIN, 11));
                g2.setColor(new Color(80, 80, 80));
                g2.drawString(valLbl, padL + barW + 8, y + barH / 2 + 4);
            }
        }
    }
}
