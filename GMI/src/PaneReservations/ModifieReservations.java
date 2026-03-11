package PaneReservations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

public class ModifieReservations extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> CBUser;
    private JComboBox<String> CBRessources;
    private JDateChooser      dateChooser;
    private JButton           checkButton;
    private JTextField        txtHour;
    private JTextField        txtDuree;
    private JComboBox<String> CBType;
    private JButton           modifyButton;

    public ModifieReservations() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][][][][][grow]"));

        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JTextField title = new JTextField("Reservation Edit");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setEditable(false);
        panelTitre.add(title);
        add(panelTitre, "cell 0 0, alignx center, growy");

        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        panelRecherche.add(lbl("User:")); CBUser = new JComboBox<>(); CBUser.setEditable(true); panelRecherche.add(CBUser);
        panelRecherche.add(lbl("Ressource:")); CBRessources = new JComboBox<>(); CBRessources.setEditable(true); panelRecherche.add(CBRessources);
        panelRecherche.add(lbl("Start date:"));
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panelRecherche.add(dateChooser);
        add(panelRecherche, "cell 0 1, growx");

        JPanel panelCheck = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        checkButton = new JButton("Search");
        checkButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelCheck.add(checkButton);
        add(panelCheck, "cell 0 2, growx");

        JLabel sep = new JLabel("── Editable fields ──────────────────────────────────────");
        sep.setFont(new Font("Tahoma", Font.ITALIC, 11));
        sep.setForeground(new Color(150, 150, 150));
        add(sep, "cell 0 3, growx, gaptop 6");

        JPanel panelEdit = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        panelEdit.add(lbl("Start time:"));
        txtHour = new JTextField("HH:mm", 7);
        txtHour.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtHour.setEnabled(false);
        placeholder(txtHour, "HH:mm");
        panelEdit.add(txtHour);
        panelEdit.add(lbl("Duration (min):"));
        txtDuree = new JTextField("60", 5);
        txtDuree.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtDuree.setEnabled(false);
        placeholder(txtDuree, "60");
        panelEdit.add(txtDuree);
        panelEdit.add(lbl("Type :"));
        CBType = new JComboBox<>(new String[]{"Emprunt", "Cours", "Maintenance"});
        CBType.setEditable(true);
        CBType.setEnabled(false);
        panelEdit.add(CBType);
        add(panelEdit, "cell 0 4, growx");

        JPanel panelModify = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        modifyButton = new JButton("Modify");
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        modifyButton.setEnabled(false);
        panelModify.add(modifyButton);
        add(panelModify, "cell 0 5, grow");
    }

    public void unlockEdit(String heure, int duree, String type) {
        txtHour.setText(heure);                    txtHour.setForeground(Color.BLACK);  txtHour.setEnabled(true);
        txtDuree.setText(String.valueOf(duree));    txtDuree.setForeground(Color.BLACK); txtDuree.setEnabled(true);
        CBType.setSelectedItem(type); CBType.setEnabled(true);
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

    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Tahoma", Font.BOLD, 14)); return l; }

    public JComboBox<String> getCBUser()       { return CBUser; }
    public JComboBox<String> getCBRessources() { return CBRessources; }
    public JDateChooser      getDateChooser()  { return dateChooser; }
    public JTextField        getTxtHour()      { return txtHour; }
    public JTextField        getTxtDuree()     { return txtDuree; }
    public JComboBox<String> getCBType()       { return CBType; }
    public JButton           getCheckButton()  { return checkButton; }
    public JButton           getModifyButton() { return modifyButton; }
}
