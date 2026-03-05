package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

/**
 * Panneau de modification d'une réservation.
 *
 * Recherche (identifiant) : Utilisateur + Ressource + Date de début
 * Champs modifiables      : Heure de début, Durée, Type d'emprunt
 */
public class ModifieReservations extends JPanel {

    private static final long serialVersionUID = 1L;

    // --- Bloc recherche ---
    private JComboBox<String> CBUser;
    private JComboBox<String> CBRessources;
    private JDateChooser      dateChooser;       // date de début (clé)
    private JButton           checkButton;

    // --- Bloc modification (déverrouillé après Check) ---
    private JTextField        txtHour;           // heure de début modifiable
    private JTextField        txtDuree;          // durée en minutes modifiable
    private JComboBox<String> CBType;            // type d'emprunt modifiable

    // --- Boutons bas ---
    private JButton modifyButton;
    private JButton cancelButton;

    public ModifieReservations() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][][][][][grow][60px]"));

        // ── Titre ──────────────────────────────────────────────────────────
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JTextField title = new JTextField("Modification Reservation");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setEditable(false);
        panelTitre.add(title);
        add(panelTitre, "cell 0 0, alignx center, growy");

        // ── Ligne 1 : critères de recherche ───────────────────────────────
        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));

        JLabel lUser = new JLabel("Utilisateur :");
        lUser.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelRecherche.add(lUser);
        CBUser = new JComboBox<>(); CBUser.setEditable(true);
        panelRecherche.add(CBUser);

        JLabel lRess = new JLabel("Ressource :");
        lRess.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelRecherche.add(lRess);
        CBRessources = new JComboBox<>(); CBRessources.setEditable(true);
        panelRecherche.add(CBRessources);

        JLabel lDate = new JLabel("Date de début :");
        lDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelRecherche.add(lDate);
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panelRecherche.add(dateChooser);

        add(panelRecherche, "cell 0 1, growx");

        // ── Ligne 2 : bouton Check ─────────────────────────────────────────
        JPanel panelCheck = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        checkButton = new JButton("🔍  Rechercher");
        checkButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelCheck.add(checkButton);
        add(panelCheck, "cell 0 2, growx");

        // ── Séparateur visuel ──────────────────────────────────────────────
        JLabel sep = new JLabel("── Champs modifiables ──────────────────────────────────────");
        sep.setFont(new Font("Tahoma", Font.ITALIC, 11));
        sep.setForeground(new java.awt.Color(150, 150, 150));
        add(sep, "cell 0 3, growx, gaptop 6");

        // ── Ligne 3 : champs modifiables ──────────────────────────────────
        JPanel panelEdit = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));

        JLabel lHeure = new JLabel("Heure de début :");
        lHeure.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelEdit.add(lHeure);
        txtHour = new JTextField("HH:mm", 7);
        txtHour.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtHour.setEnabled(false);
        panelEdit.add(txtHour);

        JLabel lDuree = new JLabel("Durée (min) :");
        lDuree.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelEdit.add(lDuree);
        txtDuree = new JTextField("60", 5);
        txtDuree.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtDuree.setEnabled(false);
        panelEdit.add(txtDuree);

        JLabel lType = new JLabel("Type :");
        lType.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelEdit.add(lType);
        CBType = new JComboBox<>(new String[]{"Emprunt", "Cours", "Maintenance"});
        CBType.setEditable(true);
        CBType.setEnabled(false);
        panelEdit.add(CBType);

        add(panelEdit, "cell 0 4, growx");

        // ── Bouton Modify ──────────────────────────────────────────────────
        JPanel panelModify = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        modifyButton = new JButton("✏️  Modifier");
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        modifyButton.setEnabled(false);
        panelModify.add(modifyButton);
        add(panelModify, "cell 0 5, grow");

        // ── Bouton Cancel ──────────────────────────────────────────────────
        JPanel panelCancel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelCancel.add(cancelButton);
        add(panelCancel, "cell 0 6, alignx right, growy");
    }

    /** Active les champs éditables et les pré-remplit avec les valeurs trouvées. */
    public void unlockEdit(String heure, int duree, String type) {
        txtHour.setText(heure);
        txtDuree.setText(String.valueOf(duree));
        CBType.setSelectedItem(type);
        txtHour.setEnabled(true);
        txtDuree.setEnabled(true);
        CBType.setEnabled(true);
        modifyButton.setEnabled(true);
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public JComboBox<String> getCBUser()       { return CBUser; }
    public JComboBox<String> getCBRessources() { return CBRessources; }
    public JDateChooser      getDateChooser()  { return dateChooser; }
    public JTextField        getTxtHour()      { return txtHour; }
    public JTextField        getTxtDuree()     { return txtDuree; }
    public JComboBox<String> getCBType()       { return CBType; }
    public JButton           getCheckButton()  { return checkButton; }
    public JButton           getModifyButton() { return modifyButton; }
    public JButton           getCancelButton() { return cancelButton; }
}
