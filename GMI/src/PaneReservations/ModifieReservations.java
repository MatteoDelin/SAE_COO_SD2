package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class ModifieReservations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField txtHour;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public ModifieReservations() {
		setLayout(new MigLayout("", "[grow]", "[51px,grow][][][][grow][60px]"));
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 0,alignx center,growy");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		title = new JTextField();
		panel.add(title);
		title.setText("Modification Reservation");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		
		String placeholder = "Enter user name";
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 1,grow");
		
		JComboBox CBUser = new JComboBox();
		CBUser.setEditable(true);
		panel_1.add(CBUser);
		
		JComboBox CBRessources = new JComboBox();
		CBRessources.setEditable(true);
		panel_1.add(CBRessources);
		
		JDateChooser dateChooser = new JDateChooser();
		panel_1.add(dateChooser);
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 22));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		
		txtHour = new JTextField();
		panel_1.add(txtHour);
		txtHour.setText("Hour (00:00)");
		txtHour.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "cell 0 2,grow");
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCheck = new JButton("Check");
		btnCheck.setFont(new Font("Dialog", Font.PLAIN, 28));
		panel_2.add(btnCheck);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "cell 0 3,growx,aligny center");
		
		JComboBox CBType = new JComboBox();
		panel_3.add(CBType);
		CBType.setEditable(true);
		
		JLabel LabelEndDate = new JLabel("Ending date");
		LabelEndDate.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_3.add(LabelEndDate);
		
		textField_1 = new JTextField();
		textField_1.setText("Hour (00:00)");
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 22));
		panel_3.add(textField_1);
		
		JPanel panel_5 = new JPanel();
		add(panel_5, "flowx,cell 0 4,grow");
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnModify = new JButton("Modify");
		panel_5.add(btnModify);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModify.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 0 5,alignx right,growy");
		
		JButton CancelButton = new JButton("Cancel");
		panel_4.add(CancelButton);
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));

	}
}
