package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class PrintRessources extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtAllUser;
    private JTable table;

    public PrintRessources() {
        setLayout(new MigLayout("", "[grow]", "[51px][grow]"));

        JPanel panel = new JPanel();
        add(panel, "flowx,cell 0 0,grow");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        txtAllUser = new JTextField();
        panel.add(txtAllUser);
        txtAllUser.setText("All Ressource");
        txtAllUser.setHorizontalAlignment(SwingConstants.CENTER);
        txtAllUser.setFont(new Font("Tahoma", Font.BOLD, 32));
        txtAllUser.setColumns(10);

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        table = new JTable();
        panel_1.add(new JScrollPane(table));
    }

    public JTable getTable() { return table; }
}
