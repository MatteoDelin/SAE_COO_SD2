package PaneRessources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class CreationRessources extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField TextName;
    private JTextField txtDescription;
    private JComboBox<String> CBDomaine;
    private JButton createButton;

    public CreationRessources() {
        setBackground(new Color(240, 240, 240));
        setLayout(new MigLayout("", "[grow]", "[44px,grow][93px][92px,grow]"));

        JPanel panel = new JPanel();
        add(panel, "flowx,cell 0 0,grow");
        JTextField title = new JTextField("Ressource Creation");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 31));
        title.setEditable(false);
        panel.add(title);

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");
        CBDomaine = new JComboBox<>();
        CBDomaine.setEditable(true);
        panel_1.add(CBDomaine);

        TextName = new JTextField("Ressource name");
        TextName.setHorizontalAlignment(SwingConstants.CENTER);
        TextName.setFont(new Font("Tahoma", Font.PLAIN, 28));
        TextName.setColumns(10);
        placeholder(TextName, "Ressource name");
        panel_1.add(TextName);

        txtDescription = new JTextField("Description");
        txtDescription.setHorizontalAlignment(SwingConstants.CENTER);
        txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 28));
        txtDescription.setColumns(10);
        placeholder(txtDescription, "Description");
        panel_1.add(txtDescription);

        JPanel panel_2 = new JPanel();
        add(panel_2, "cell 0 2,grow");
        createButton = new JButton("Create ressource");
        createButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_2.add(createButton);
    }

    private void placeholder(JTextField f, String ph) {
        f.setForeground(Color.GRAY);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(Color.BLACK); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().trim().isEmpty()) { f.setText(ph); f.setForeground(Color.GRAY); }
            }
        });
    }

    public JTextField getTextName()         { return TextName; }
    public JTextField getTxtDescription()   { return txtDescription; }
    public JComboBox<String> getCBDomaine() { return CBDomaine; }
    public JButton getCreateButton()        { return createButton; }
}
