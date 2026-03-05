package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class ModifieRessources extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField    title;
    private JComboBox<String> listRessource;  // sélection de la ressource à modifier
    private JButton       checkButton;
    // champs modifiables (activés après Check)
    private JTextField    txtNom;
    private JTextField    txtDescription;
    private JComboBox<String> CBDomaine;
    private JButton       modifyButton;
    private JButton       cancelButton;

    public ModifieRessources() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][92px][][grow][grow][60px]"));

        // ── Titre ──────────────────────────────────────────────────────────
        JPanel panel = new JPanel();
        add(panel, "cell 0 0,grow");
        title = new JTextField("Modification Ressource");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setEditable(false);
        panel.add(title);

        // ── Sélection + Check ──────────────────────────────────────────────
        JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        add(panel_1, "flowx,cell 0 1,grow");
        listRessource = new JComboBox<>();
        listRessource.setEditable(false);
        listRessource.setFont(new Font("Tahoma", Font.PLAIN, 22));
        panel_1.add(listRessource);
        checkButton = new JButton("Check");
        checkButton.setFont(new Font("Dialog", Font.PLAIN, 22));
        panel_1.add(checkButton);

        // ── Séparateur ────────────────────────────────────────────────────
        JLabel sep = new JLabel("── Champs modifiables ──────────────────────────");
        sep.setFont(new Font("Tahoma", Font.ITALIC, 11));
        sep.setForeground(new Color(150, 150, 150));
        add(sep, "cell 0 2, growx, gaptop 4");

        // ── Champs modifiables ─────────────────────────────────────────────
        JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        add(panel_5, "cell 0 3,grow");

        panel_5.add(lbl("Nom :"));
        txtNom = new JTextField(15);
        txtNom.setFont(new Font("Dialog", Font.PLAIN, 20));
        txtNom.setEnabled(false);
        panel_5.add(txtNom);

        panel_5.add(lbl("Description :"));
        txtDescription = new JTextField(15);
        txtDescription.setFont(new Font("Dialog", Font.PLAIN, 20));
        txtDescription.setEnabled(false);
        panel_5.add(txtDescription);

        panel_5.add(lbl("Domaine :"));
        CBDomaine = new JComboBox<>();
        CBDomaine.setEditable(true);
        CBDomaine.setFont(new Font("Dialog", Font.PLAIN, 18));
        CBDomaine.setEnabled(false);
        panel_5.add(CBDomaine);

        // ── Bouton Modify ──────────────────────────────────────────────────
        JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(panel_2, "cell 0 4,grow");
        modifyButton = new JButton("Modify");
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        modifyButton.setEnabled(false);
        panel_2.add(modifyButton);

        // ── Bouton Cancel ──────────────────────────────────────────────────
        JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        add(panel_3, "cell 0 5,alignx right,growy");
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
        panel_3.add(cancelButton);
    }

    /** Active et pré-remplit les champs après un Check réussi. */
    public void unlockEdit(String nom, String desc, String domaine) {
        txtNom.setText(nom);
        txtDescription.setText(desc);
        CBDomaine.setSelectedItem(domaine);
        txtNom.setEnabled(true);
        txtDescription.setEnabled(true);
        CBDomaine.setEnabled(true);
        modifyButton.setEnabled(true);
    }

    private JLabel lbl(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Tahoma", Font.BOLD, 13));
        return l;
    }

    public JComboBox<String> getListRessource() { return listRessource; }
    public JTextField        getTxtNom()         { return txtNom; }
    public JTextField        getTxtDescription() { return txtDescription; }
    public JComboBox<String> getCBDomaine()      { return CBDomaine; }
    public JButton           getCheckButton()    { return checkButton; }
    public JButton           getModifyButton()   { return modifyButton; }
    public JButton           getCancelButton()   { return cancelButton; }
}
