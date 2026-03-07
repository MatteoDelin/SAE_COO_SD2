import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.miginfocom.swing.MigLayout;

public class TopRessourcesChart extends JPanel {
    private static final long serialVersionUID = 1L;

    public TopRessourcesChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][grow]"));
        JLabel title = new JLabel("Top 5 Most Borrowed Resources", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");
        add(new BarChart(), "cell 0 1, grow");
    }

    private static class BarChart extends JPanel {
        BarChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Map<String, Integer> counts = new LinkedHashMap<>();
            for (Reservations r : Reservations.liste_reservations)
                counts.merge(r.getRessource().getNom(), 1, Integer::sum);
            if (counts.isEmpty()) { drawEmpty(g2); return; }

            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());
            sorted.sort((a, b) -> b.getValue() - a.getValue());
            if (sorted.size() > 5) sorted = sorted.subList(0, 5);

            int maxVal = sorted.get(0).getValue();
            int n = sorted.size();
            int W = getWidth(), H = getHeight();
            int padL = 50, padR = 20, padT = 20, padB = 60;
            int chartW = W - padL - padR, chartH = H - padT - padB;
            int barW = chartW / n - 10;

            Color[] palette = {
                new Color(66,133,244), new Color(52,168,83),
                new Color(251,188,5),  new Color(234,67,53),
                new Color(103,78,167)
            };

            g2.setColor(new Color(180,180,180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int y = padT + chartH - i * chartH / 4;
                g2.setColor(new Color(220,220,220));
                g2.drawLine(padL, y, W - padR, y);
                g2.setColor(Color.GRAY);
                g2.drawString(String.valueOf(maxVal * i / 4), padL - 30, y + 4);
            }

            for (int i = 0; i < n; i++) {
                Map.Entry<String, Integer> e = sorted.get(i);
                int barH = (int) ((double) e.getValue() / maxVal * chartH);
                int x = padL + i * (chartW / n) + 5;
                int y = padT + chartH - barH;

                g2.setColor(palette[i % palette.length]);
                g2.fillRoundRect(x, y, barW, barH, 6, 6);
                g2.setColor(palette[i % palette.length].darker());
                g2.drawRoundRect(x, y, barW, barH, 6, 6);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Tahoma", Font.BOLD, 11));
                String val = String.valueOf(e.getValue());
                int tx = x + barW / 2 - g2.getFontMetrics().stringWidth(val) / 2;
                g2.drawString(val, tx, y - 4);

                g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
                String lbl = e.getKey().length() > 14 ? e.getKey().substring(0, 13) + "…" : e.getKey();
                int lx = x + barW / 2 - g2.getFontMetrics().stringWidth(lbl) / 2;
                g2.drawString(lbl, lx, padT + chartH + 16);
            }
        }

        private void drawEmpty(Graphics2D g2) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
            g2.drawString("No data available", getWidth() / 2 - 70, getHeight() / 2);
        }
    }
}
