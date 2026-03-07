package PaneReservations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

public class CreationReservations extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> CBUser;
    private JComboBox<String> CBRessources;
    private JComboBox<String> CBType;
    private JDateChooser      dateChooserStart;
    private JTextField        txtHoureStart;
    private JTextField        txtDuree;
    private JButton           createButton;

    public CreationReservations() {
        setBackground(new Color(240, 240, 240));
        setLayout(new MigLayout("", "[grow]", "[100,grow][50][50][50][200,grow]"));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JTextField title = new JTextField("Reservation Creation");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 31));
        title.setEditable(false);
        panel.add(title);
        add(panel, "cell 0 0,growx,aligny top");

        JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        CBUser = new JComboBox<>(); CBUser.setEditable(true); panel_1.add(CBUser);
        CBRessources = new JComboBox<>(); CBRessources.setEditable(true); panel_1.add(CBRessources);
        CBType = new JComboBox<>(); CBType.setEditable(true); panel_1.add(CBType);
        add(panel_1, "cell 0 1,growx,aligny center");

        JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel lDate = new JLabel("Start date");
        lDate.setFont(new Font("Dialog", Font.BOLD, 18));
        panel_2.add(lDate);
        dateChooserStart = new JDateChooser();
        dateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
        dateChooserStart.setDateFormatString("dd/MM/yyyy");
        panel_2.add(dateChooserStart);
        txtHoureStart = new JTextField("HH:mm");
        txtHoureStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
        txtHoureStart.setColumns(6);
        placeholder(txtHoureStart, "HH:mm");
        panel_2.add(txtHoureStart);
        add(panel_2, "cell 0 2,grow");

        JPanel panel_3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel lDuree = new JLabel("Duration (minutes):");
        lDuree.setFont(new Font("Dialog", Font.BOLD, 18));
        panel_3.add(lDuree);
        txtDuree = new JTextField("60");
        txtDuree.setFont(new Font("Dialog", Font.PLAIN, 22));
        txtDuree.setColumns(6);
        placeholder(txtDuree, "60");
        panel_3.add(txtDuree);
        add(panel_3, "cell 0 3,growx,aligny center");

        JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        createButton = new JButton("Create Reservation");
        createButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_4.add(createButton);
        add(panel_4, "flowx,cell 0 4,alignx center,growy");
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

    public JComboBox<String> getCBUser()          { return CBUser; }
    public JComboBox<String> getCBRessources()    { return CBRessources; }
    public JComboBox<String> getCBType()          { return CBType; }
    public JDateChooser      getDateChooserStart(){ return dateChooserStart; }
    public JTextField        getTxtHoureStart()   { return txtHoureStart; }
    public JTextField        getTxtDuree()        { return txtDuree; }
    public JButton           getCreateButton()    { return createButton; }
}
