import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.miginfocom.swing.MigLayout;

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

    private static class LineChart extends JPanel {
        LineChart() { setBackground(Color.WHITE); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Map<String, Integer> monthly = new TreeMap<>();
            for (Reservations r : Reservations.liste_reservations) {
                if (r.getDate() == null) continue;
                Calendar c = Calendar.getInstance(); c.setTime(r.getDate());
                String key = String.format("%04d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
                monthly.merge(key, 1, Integer::sum);
            }

            if (monthly.size() < 2) {
                g2.setColor(Color.GRAY);
                g2.setFont(new Font("Tahoma", Font.ITALIC, 14));
                g2.drawString("Not enough data to display a trend", getWidth() / 2 - 130, getHeight() / 2);
                return;
            }

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(monthly.entrySet());
            int maxVal = entries.stream().mapToInt(Map.Entry::getValue).max().orElse(1);
            int n = entries.size();
            int W = getWidth(), H = getHeight();
            int padL = 55, padR = 20, padT = 20, padB = 55;
            int chartW = W - padL - padR, chartH = H - padT - padB;

            g2.setColor(new Color(180,180,180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int y = padT + chartH - i * chartH / 4;
                g2.setColor(new Color(220,220,220)); g2.drawLine(padL, y, W - padR, y);
                g2.setColor(Color.GRAY); g2.drawString(String.valueOf(maxVal * i / 4), padL - 30, y + 4);
            }

            int[] px = new int[n], py = new int[n];
            for (int i = 0; i < n; i++) {
                px[i] = padL + (int) ((double) i / (n - 1) * chartW);
                py[i] = padT + chartH - (int) ((double) entries.get(i).getValue() / maxVal * chartH);
            }

            int[] polyX = new int[n + 2], polyY = new int[n + 2];
            System.arraycopy(px, 0, polyX, 0, n);
            System.arraycopy(py, 0, polyY, 0, n);
            polyX[n] = px[n-1]; polyY[n] = padT + chartH;
            polyX[n+1] = px[0]; polyY[n+1] = padT + chartH;
            g2.setColor(new Color(66, 133, 244, 40));
            g2.fillPolygon(polyX, polyY, n + 2);

            g2.setColor(new Color(66, 133, 244));
            g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int i = 0; i < n - 1; i++) g2.drawLine(px[i], py[i], px[i+1], py[i+1]);

            for (int i = 0; i < n; i++) {
                g2.setColor(Color.WHITE); g2.fillOval(px[i]-5, py[i]-5, 10, 10);
                g2.setColor(new Color(66,133,244)); g2.drawOval(px[i]-5, py[i]-5, 10, 10);

                g2.setFont(new Font("Tahoma", Font.BOLD, 9));
                g2.setColor(Color.DARK_GRAY);
                String val = String.valueOf(entries.get(i).getValue());
                g2.drawString(val, px[i] - g2.getFontMetrics().stringWidth(val)/2, py[i] - 8);

                if (n <= 24 || i % 2 == 0) {
                    g2.setFont(new Font("Tahoma", Font.PLAIN, 9));
                    Graphics2D gr = (Graphics2D) g2.create();
                    gr.translate(px[i], padT + chartH + 8);
                    gr.rotate(Math.PI / 4);
                    gr.setColor(Color.DARK_GRAY);
                    gr.drawString(entries.get(i).getKey(), 0, 0);
                    gr.dispose();
                }
            }
        }
    }
}
