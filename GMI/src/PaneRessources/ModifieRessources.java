package PaneRessources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class ModifieRessources extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> listRessource;
    private JButton checkButton;
    private JTextField txtNom;
    private JTextField txtDescription;
    private JComboBox<String> CBDomaine;
    private JButton modifyButton;

    public ModifieRessources() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][92px][][grow][grow]"));

        JPanel panel = new JPanel();
        add(panel, "cell 0 0,grow");
        JTextField title = new JTextField("Ressource Edit");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setEditable(false);
        panel.add(title);

        JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        add(panel_1, "flowx,cell 0 1,grow");
        listRessource = new JComboBox<>();
        listRessource.setEditable(false);
        listRessource.setFont(new Font("Tahoma", Font.PLAIN, 22));
        panel_1.add(listRessource);
        checkButton = new JButton("Check");
        checkButton.setFont(new Font("Dialog", Font.PLAIN, 22));
        panel_1.add(checkButton);

        JLabel sep = new JLabel("── Editable fields ──────────────────────────");
        sep.setFont(new Font("Tahoma", Font.ITALIC, 11));
        sep.setForeground(new Color(150, 150, 150));
        add(sep, "cell 0 2, growx, gaptop 4");

        JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        add(panel_5, "cell 0 3,grow");
        panel_5.add(lbl("Name :"));
        txtNom = new JTextField("Name", 15);
        txtNom.setFont(new Font("Dialog", Font.PLAIN, 20));
        txtNom.setEnabled(false);
        placeholder(txtNom, "Name");
        panel_5.add(txtNom);
        panel_5.add(lbl("Description:"));
        txtDescription = new JTextField("Description", 15);
        txtDescription.setFont(new Font("Dialog", Font.PLAIN, 20));
        txtDescription.setEnabled(false);
        placeholder(txtDescription, "Description");
        panel_5.add(txtDescription);
        panel_5.add(lbl("Domain:"));
        CBDomaine = new JComboBox<>();
        CBDomaine.setEditable(true);
        CBDomaine.setFont(new Font("Dialog", Font.PLAIN, 18));
        CBDomaine.setEnabled(false);
        panel_5.add(CBDomaine);

        JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(panel_2, "cell 0 4,grow");
        modifyButton = new JButton("Modify");
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        modifyButton.setEnabled(false);
        panel_2.add(modifyButton);
    }

    public void unlockEdit(String nom, String desc, String domaine) {
        txtNom.setText(nom);         txtNom.setForeground(Color.BLACK);         txtNom.setEnabled(true);
        txtDescription.setText(desc); txtDescription.setForeground(Color.BLACK); txtDescription.setEnabled(true);
        CBDomaine.setSelectedItem(domaine); CBDomaine.setEnabled(true);
        modifyButton.setEnabled(true);
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

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Tahoma", Font.BOLD, 13)); return l; }

    public JComboBox<String> getListRessource() { return listRessource; }
    public JTextField getTxtNom()               { return txtNom; }
    public JTextField getTxtDescription()       { return txtDescription; }
    public JComboBox<String> getCBDomaine()     { return CBDomaine; }
    public JButton getCheckButton()             { return checkButton; }
    public JButton getModifyButton()            { return modifyButton; }
}
