import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.miginfocom.swing.MigLayout;

public class TauxEmpruntDomaineChart extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JComboBox<String> cbDomain = new JComboBox<>();
    private final PieChart          chart    = new PieChart();

    public TauxEmpruntDomaineChart() {
        setLayout(new MigLayout("insets 10", "[grow]", "[40!][40!][grow]"));
        JLabel title = new JLabel("Reservation Rate per Domain", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setForeground(new Color(40, 70, 130));
        add(title, "cell 0 0, growx");

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        controls.setOpaque(false);
        Set<String> domains = new TreeSet<>();
        for (Ressources r : Ressources.liste_ressource)
            if (r.getDomaine() != null && !r.getDomaine().trim().isEmpty())
                domains.add(r.getDomaine().trim());
        for (String d : domains) cbDomain.addItem(d);
        cbDomain.setPreferredSize(new Dimension(200, 28));
        JButton btn = new JButton("Apply");
        btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btn.addActionListener(e -> { chart.setDomain((String) cbDomain.getSelectedItem()); chart.repaint(); });
        controls.add(new JLabel("Domain:")); controls.add(cbDomain); controls.add(btn);
        add(controls, "cell 0 1, growx");
        add(chart, "cell 0 2, grow");
    }

    private static class PieChart extends JPanel {
        private String domain;
        private static final String[] TYPES  = {"Emprunt", "Cours", "Maintenance"};
        private static final Color[]  COLORS = {
            new Color(66,133,244), new Color(52,168,83), new Color(234,67,53)
        };

        PieChart() { setBackground(Color.WHITE); }

        void setDomain(String d) { this.domain = d; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (domain == null) { drawMsg(g, "Select a domain and click Apply"); return; }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Map<String, Integer> counts = new LinkedHashMap<>();
            for (String t : TYPES) counts.put(t, 0);
            int total = 0;
            for (Reservations r : Reservations.liste_reservations) {
                if (!domain.equals(r.getRessource().getDomaine())) continue;
                String type = r.getType_emprunt();
                if (type == null) type = "Emprunt";
                counts.merge(type, 1, Integer::sum);
                total++;
            }
            if (total == 0) { drawMsg(g, "No reservations for this domain"); return; }

            int W = getWidth(), H = getHeight();
            int size = Math.min(W, H) - 100;
            int cx = W / 2 - size / 4, cy = H / 2;
            int startAngle = 0;
            for (int i = 0; i < TYPES.length; i++) {
                int cnt = counts.getOrDefault(TYPES[i], 0);
                if (cnt == 0) continue;
                int arc = (int) Math.round(360.0 * cnt / total);
                g2.setColor(COLORS[i]);
                g2.fillArc(cx - size/2, cy - size/2, size, size, startAngle, arc);
                g2.setColor(Color.WHITE);
                g2.drawArc(cx - size/2, cy - size/2, size, size, startAngle, arc);
                startAngle += arc;
            }

            int lx = cx + size/2 + 20, ly = cy - 60;
            g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
            for (int i = 0; i < TYPES.length; i++) {
                int cnt = counts.getOrDefault(TYPES[i], 0);
                double pct = 100.0 * cnt / total;
                g2.setColor(COLORS[i]);
                g2.fillRect(lx, ly + i * 28, 16, 16);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(String.format("%s  %d (%.1f%%)", TYPES[i], cnt, pct), lx + 22, ly + i * 28 + 13);
            }
        }

        private void drawMsg(Graphics g, String msg) {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Tahoma", Font.ITALIC, 14));
            g.drawString(msg, getWidth() / 2 - 120, getHeight() / 2);
        }
    }
}
