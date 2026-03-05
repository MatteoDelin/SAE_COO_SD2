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
    private JTextField Title;
    private JTextField txtHoureStart;
    private JDateChooser dateChooserStart;
    private JTextField textHourEnd;
    private JComboBox<String> CBUser;
    private JComboBox<String> CBRessources;
    private JComboBox<String> CBType;
    private JButton createButton;
    private JButton cancelButton;

    public CreationReservations() {
        setBackground(new Color(240, 240, 240));
        setLayout(new MigLayout("", "[grow]", "[100,grow][50][50][][200,grow][]"));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        Title = new JTextField();
        panel.add(Title);
        Title.setHorizontalAlignment(SwingConstants.CENTER);
        Title.setFont(new Font("Tahoma", Font.BOLD, 31));
        Title.setText("Reservations Creation");
        add(panel, "cell 0 0,growx,aligny top");

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        CBUser = new JComboBox<>(); CBUser.setEditable(true); panel_1.add(CBUser);
        CBRessources = new JComboBox<>(); CBRessources.setEditable(true); panel_1.add(CBRessources);
        CBType = new JComboBox<>(); CBType.setEditable(true); panel_1.add(CBType);
        add(panel_1, "cell 0 1,growx,aligny center");

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel LabelStartDate = new JLabel("Starting date");
        LabelStartDate.setFont(new Font("Dialog", Font.BOLD, 18));
        panel_2.add(LabelStartDate);
        dateChooserStart = new JDateChooser();
        dateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
        dateChooserStart.setDateFormatString("dd/MM/yyyy");
        panel_2.add(dateChooserStart);
        txtHoureStart = new JTextField("Hour (00:00)");
        txtHoureStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
        panel_2.add(txtHoureStart);
        add(panel_2, "cell 0 2,grow");

        JPanel panel_2_1 = new JPanel();
        panel_2_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel LabelEndDate = new JLabel("Ending date");
        LabelEndDate.setFont(new Font("Dialog", Font.BOLD, 18));
        panel_2_1.add(LabelEndDate);
        JDateChooser dateChooserEnd = new JDateChooser();
        dateChooserEnd.setFont(new Font("Dialog", Font.PLAIN, 22));
        dateChooserEnd.setDateFormatString("dd/MM/yyyy");
        panel_2_1.add(dateChooserEnd);
        textHourEnd = new JTextField("Hour (00:00)");
        textHourEnd.setFont(new Font("Dialog", Font.PLAIN, 22));
        panel_2_1.add(textHourEnd);
        add(panel_2_1, "cell 0 3,growx,aligny center");

        JPanel panel_3 = new JPanel();
        panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        createButton = new JButton("Create Reservation");
        createButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_3.add(createButton);
        add(panel_3, "flowx,cell 0 4,alignx center,growy");

        JPanel panel_4 = new JPanel();
        panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
        panel_4.add(cancelButton);
        add(panel_4, "cell 0 5,alignx right,growy");
    }

    public JComboBox<String> getCBUser()        { return CBUser; }
    public JComboBox<String> getCBRessources()  { return CBRessources; }
    public JComboBox<String> getCBType()        { return CBType; }
    public JDateChooser getDateChooserStart()   { return dateChooserStart; }
    public JTextField getTxtHoureStart()        { return txtHoureStart; }
    public JButton getCreateButton()            { return createButton; }
    public JButton getCancelButton()            { return cancelButton; }
}
