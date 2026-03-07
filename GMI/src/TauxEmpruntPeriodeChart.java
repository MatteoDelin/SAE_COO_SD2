import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

public class TauxEmpruntPeriodeChart extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final String[] TYPES  = {"Emprunt", "Cours", "Maintenance"};
    private static final Color[]  COLORS = {
        new Color(66,133,244), new Color(52,168,83), new Color(234,67,53)
    };

    private final JDateChooser dateFrom = new JDateChooser();
    private final JDateChooser dateTo   = new JDateChooser();
    private final StackedBarChart chart = new StackedBarChart();

    public TauxEmpruntPeriodeChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][40!][grow][30!]"));

        JLabel title = new JLabel("Reservation Rate over a Period", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");

        // ── Controls ────────────────────────────────────────────────────────
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        controls.setOpaque(false);
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setPreferredSize(new Dimension(130, 28));
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setPreferredSize(new Dimension(130, 28));
        JButton btn = new JButton("Apply");
        btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btn.addActionListener(e -> {
            chart.setRange(dateFrom.getDate(), dateTo.getDate());
            chart.repaint();
        });
        controls.add(new JLabel("From:")); controls.add(dateFrom);
        controls.add(new JLabel("To:"));   controls.add(dateTo);
        controls.add(btn);
        add(controls, "cell 0 1, growx");

        add(chart, "cell 0 2, grow");

        // ── Legend ───────────────────────────────────────────────────────────
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

    // ── Inner stacked bar chart ──────────────────────────────────────────────
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

            // Group by year-month AND type
            // monthly : "2021-09" -> { "Emprunt"->5, "Cours"->2, ... }
            Map<String, Map<String, Integer>> monthly = new TreeMap<>();
            for (Reservations r : Reservations.liste_reservations) {
                Date d = r.getDate();
                if (d == null) continue;
                if (from != null && d.before(from)) continue;
                if (to   != null && d.after(to))   continue;
                Calendar c = Calendar.getInstance(); c.setTime(d);
                String key = String.format("%04d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
                String type = r.getType_emprunt();
                if (type == null || type.trim().isEmpty()) type = "Emprunt";
                monthly.computeIfAbsent(key, k -> new LinkedHashMap<>()).merge(type, 1, Integer::sum);
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

            List<Map.Entry<String, Map<String, Integer>>> entries = new ArrayList<>(monthly.entrySet());
            int n = entries.size();

            // Max total per month (for Y axis)
            int maxTotal = 1;
            for (Map.Entry<String, Map<String, Integer>> e : entries) {
                int total = e.getValue().values().stream().mapToInt(Integer::intValue).sum();
                if (total > maxTotal) maxTotal = total;
            }

            int W = getWidth(), H = getHeight();
            int padL = 50, padR = 20, padT = 20, padB = 50;
            int chartW = W - padL - padR, chartH = H - padT - padB;
            int barW = Math.max(4, chartW / n - 4);

            // Axes
            g2.setColor(new Color(180,180,180));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, W - padR, padT + chartH);

            // Y grid
            g2.setFont(new Font("Tahoma", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int y = padT + chartH - i * chartH / 4;
                g2.setColor(new Color(220,220,220)); g2.drawLine(padL, y, W - padR, y);
                g2.setColor(Color.GRAY); g2.drawString(String.valueOf(maxTotal * i / 4), padL - 28, y + 4);
            }

            // Stacked bars
            for (int i = 0; i < n; i++) {
                Map.Entry<String, Map<String, Integer>> e = entries.get(i);
                Map<String, Integer> typeCounts = e.getValue();
                int x = padL + i * (chartW / n) + 2;
                int yBase = padT + chartH;

                for (int t = 0; t < TYPES.length; t++) {
                    int count = typeCounts.getOrDefault(TYPES[t], 0);
                    if (count == 0) continue;
                    int segH = (int) ((double) count / maxTotal * chartH);
                    yBase -= segH;
                    g2.setColor(COLORS[t]);
                    g2.fillRoundRect(x, yBase, barW, segH, 3, 3);
                    g2.setColor(COLORS[t].darker());
                    g2.drawRoundRect(x, yBase, barW, segH, 3, 3);
                }

                // X label (rotated)
                g2.setFont(new Font("Tahoma", Font.PLAIN, 9));
                g2.setColor(Color.DARK_GRAY);
                Graphics2D gr = (Graphics2D) g2.create();
                gr.translate(x + barW / 2, padT + chartH + 6);
                gr.rotate(Math.PI / 4);
                gr.drawString(e.getKey(), 0, 0);
                gr.dispose();
            }
        }
    }
}
