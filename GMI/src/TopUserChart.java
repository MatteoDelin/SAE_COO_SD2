import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.miginfocom.swing.MigLayout;

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

    private static class HBarChart extends JPanel {
        HBarChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Map<String, Integer> counts = new LinkedHashMap<>();
            for (Reservations r : Reservations.liste_reservations)
                counts.merge(r.getUser().getNom(), 1, Integer::sum);
            if (counts.isEmpty()) { drawEmpty(g2); return; }

            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());
            sorted.sort((a, b) -> b.getValue() - a.getValue());
            if (sorted.size() > 10) sorted = sorted.subList(0, 10);

            int maxVal = sorted.get(0).getValue();
            int n = sorted.size();
            int W = getWidth(), H = getHeight();
            int padL = 120, padR = 60, padT = 20, padB = 20;
            int chartW = W - padL - padR, chartH = H - padT - padB;
            int rowH = chartH / n;
            int barH = Math.min(rowH - 8, 30);

            for (int i = 0; i < n; i++) {
                Map.Entry<String, Integer> e = sorted.get(i);
                int barW = (int) ((double) e.getValue() / maxVal * chartW);
                int y = padT + i * rowH + (rowH - barH) / 2;

                Color c = i == 0 ? new Color(234, 67, 53) : new Color(66, 133, 244);
                g2.setColor(c);
                g2.fillRoundRect(padL, y, barW, barH, 6, 6);
                g2.setColor(c.darker());
                g2.drawRoundRect(padL, y, barW, barH, 6, 6);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Tahoma", i == 0 ? Font.BOLD : Font.PLAIN, 11));
                String label = e.getKey().length() > 16 ? e.getKey().substring(0, 15) + "…" : e.getKey();
                g2.drawString(label, padL - g2.getFontMetrics().stringWidth(label) - 6, y + barH / 2 + 4);

                g2.setFont(new Font("Tahoma", Font.BOLD, 11));
                g2.drawString(String.valueOf(e.getValue()), padL + barW + 5, y + barH / 2 + 4);
            }
        }

        private void drawEmpty(Graphics2D g2) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
            g2.drawString("No data available", getWidth() / 2 - 70, getHeight() / 2);
        }
    }
}
