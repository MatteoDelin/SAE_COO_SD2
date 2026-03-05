package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;

public class ModifieRessources extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField title;
    private JTextField txtDescription;
    private JComboBox<String> listRessource;
    private JButton checkButton;
    private JButton modifyButton;
    private JButton cancelButton;

    public ModifieRessources() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][92px][][79px,grow][60px]"));

        JPanel panel = new JPanel();
        add(panel, "cell 0 0,grow");

        title = new JTextField();
        panel.add(title);
        title.setText("Modification Ressource");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");

        listRessource = new JComboBox<>();
        listRessource.setEditable(true);
        panel_1.add(listRessource);
        listRessource.setFont(new Font("Tahoma", Font.PLAIN, 29));

        JPanel panel_4 = new JPanel();
        add(panel_4, "cell 0 2,grow");

        checkButton = new JButton("Check");
        checkButton.setFont(new Font("Dialog", Font.PLAIN, 28));
        panel_4.add(checkButton);

        JPanel panel_5 = new JPanel();
        add(panel_5, "cell 0 3,grow");

        txtDescription = new JTextField();
        txtDescription.setText("Description");
        txtDescription.setHorizontalAlignment(SwingConstants.CENTER);
        txtDescription.setForeground(Color.GRAY);
        txtDescription.setFont(new Font("Dialog", Font.PLAIN, 28));
        txtDescription.setColumns(10);
        panel_5.add(txtDescription);

        JPanel panel_2 = new JPanel();
        add(panel_2, "flowx,cell 0 3,grow");

        modifyButton = new JButton("Modify");
        panel_2.add(modifyButton);
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 28));

        JPanel panel_3 = new JPanel();
        add(panel_3, "cell 0 4,alignx right,growy");

        cancelButton = new JButton("Cancel");
        panel_3.add(cancelButton);
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
    }

    public JComboBox<String> getListRessource() { return listRessource; }
    public JTextField getTxtDescription()        { return txtDescription; }
    public JButton getCheckButton()              { return checkButton; }
    public JButton getModifyButton()             { return modifyButton; }
    public JButton getCancelButton()             { return cancelButton; }
}
