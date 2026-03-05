package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;

public class DeleteRessources extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField title;
    private JComboBox<String> CBDomaine;
    private JButton deleteButton;
    private JButton cancelButton;

    public DeleteRessources() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][92px][grow][60px]"));

        JPanel panel = new JPanel();
        add(panel, "flowx,cell 0 0,grow");

        title = new JTextField();
        panel.add(title);
        title.setText("Delete Ressource");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setColumns(10);

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");

        CBDomaine = new JComboBox<>();
        CBDomaine.setEditable(true);
        panel_1.add(CBDomaine);

        JPanel panel_3 = new JPanel();
        add(panel_3, "cell 0 2,grow");

        deleteButton = new JButton("Delete");
        panel_3.add(deleteButton);
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 28));

        JPanel panel_2 = new JPanel();
        add(panel_2, "cell 0 3,alignx right,growy");

        cancelButton = new JButton("Cancel");
        panel_2.add(cancelButton);
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
    }

    public JComboBox<String> getCBDomaine() { return CBDomaine; }
    public JButton getDeleteButton()        { return deleteButton; }
    public JButton getCancelButton()        { return cancelButton; }
}
