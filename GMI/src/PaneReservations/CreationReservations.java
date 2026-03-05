package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class CreationReservations extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField        Title;
    private JComboBox<String> CBUser;
    private JComboBox<String> CBRessources;
    private JComboBox<String> CBType;
    private JDateChooser      dateChooserStart;
    private JTextField        txtHoureStart;
    private JTextField        txtDuree;          // ← durée en minutes (remplace dateChooserEnd)
    private JButton           createButton;
    private JButton           cancelButton;

    public CreationReservations() {
        setBackground(new Color(240, 240, 240));
        setLayout(new MigLayout("", "[grow]", "[100,grow][50][50][50][200,grow][]"));

        // ── Titre ──────────────────────────────────────────────────────────
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        Title = new JTextField("Reservations Creation");
        Title.setHorizontalAlignment(SwingConstants.CENTER);
        Title.setFont(new Font("Tahoma", Font.BOLD, 31));
        Title.setEditable(false);
        panel.add(Title);
        add(panel, "cell 0 0,growx,aligny top");

        // ── Ligne 1 : Utilisateur + Ressource + Type ───────────────────────
        JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        CBUser = new JComboBox<>(); CBUser.setEditable(true); panel_1.add(CBUser);
        CBRessources = new JComboBox<>(); CBRessources.setEditable(true); panel_1.add(CBRessources);
        CBType = new JComboBox<>(); CBType.setEditable(true); panel_1.add(CBType);
        add(panel_1, "cell 0 1,growx,aligny center");

        // ── Ligne 2 : Date + Heure de début ───────────────────────────────
        JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel LabelStartDate = new JLabel("Starting date");
        LabelStartDate.setFont(new Font("Dialog", Font.BOLD, 18));
        panel_2.add(LabelStartDate);
        dateChooserStart = new JDateChooser();
        dateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
        dateChooserStart.setDateFormatString("dd/MM/yyyy");
        panel_2.add(dateChooserStart);
        txtHoureStart = new JTextField("08:00");
        txtHoureStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
        panel_2.add(txtHoureStart);
        add(panel_2, "cell 0 2,grow");

        // ── Ligne 3 : Durée en minutes (remplace la date de fin) ──────────
        JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel LabelDuree = new JLabel("Durée (minutes) :");
        LabelDuree.setFont(new Font("Dialog", Font.BOLD, 18));
        panel_3.add(LabelDuree);
        txtDuree = new JTextField("60");
        txtDuree.setFont(new Font("Dialog", Font.PLAIN, 22));
        txtDuree.setColumns(6);
        panel_3.add(txtDuree);
        add(panel_3, "cell 0 3,growx,aligny center");

        // ── Bouton Create ──────────────────────────────────────────────────
        JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        createButton = new JButton("Create Reservation");
        createButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_4.add(createButton);
        add(panel_4, "flowx,cell 0 4,alignx center,growy");

        // ── Bouton Cancel ──────────────────────────────────────────────────
        JPanel panel_5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
        panel_5.add(cancelButton);
        add(panel_5, "cell 0 5,alignx right,growy");
    }

    public JComboBox<String> getCBUser()         { return CBUser; }
    public JComboBox<String> getCBRessources()   { return CBRessources; }
    public JComboBox<String> getCBType()         { return CBType; }
    public JDateChooser      getDateChooserStart(){ return dateChooserStart; }
    public JTextField        getTxtHoureStart()  { return txtHoureStart; }
    public JTextField        getTxtDuree()       { return txtDuree; }  // ← getter manquant
    public JButton           getCreateButton()   { return createButton; }
    public JButton           getCancelButton()   { return cancelButton; }
}
