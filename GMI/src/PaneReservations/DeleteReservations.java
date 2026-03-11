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

public class DeleteReservations extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> CBUser;
    private JComboBox<String> CBRessources;
    private JDateChooser      dateChooser;
    private JButton           deleteButton;

    public DeleteReservations() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][][grow]"));

        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JTextField title = new JTextField("Delete Reservation");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setEditable(false);
        panelTitre.add(title);
        add(panelTitre, "flowx, cell 0 0, growx, aligny top");

        JPanel panelCriteres = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        JLabel lUser = new JLabel("User:"); lUser.setFont(new Font("Tahoma", Font.BOLD, 14)); panelCriteres.add(lUser);
        CBUser = new JComboBox<>(); CBUser.setEditable(true); panelCriteres.add(CBUser);
        JLabel lRess = new JLabel("Ressource:"); lRess.setFont(new Font("Tahoma", Font.BOLD, 14)); panelCriteres.add(lRess);
        CBRessources = new JComboBox<>(); CBRessources.setEditable(true); panelCriteres.add(CBRessources);
        JLabel lDate = new JLabel("Start date:"); lDate.setFont(new Font("Tahoma", Font.BOLD, 14)); panelCriteres.add(lDate);
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panelCriteres.add(dateChooser);
        add(panelCriteres, "flowx, cell 0 1, growx");

        JPanel panelDelete = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        panelDelete.add(deleteButton);
        add(panelDelete, "flowx, cell 0 2, grow");
    }

    public JComboBox<String> getCBUser()       { return CBUser; }
    public JComboBox<String> getCBRessources() { return CBRessources; }
    public JDateChooser      getDateChooser()  { return dateChooser; }
    public JButton           getDeleteButton() { return deleteButton; }
}
