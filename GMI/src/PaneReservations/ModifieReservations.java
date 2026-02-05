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

public class ModifieReservations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public ModifieReservations() {
		setLayout(null);
		
		title = new JTextField();
		title.setText("Modification Reservation");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		title.setBounds(10, 11, 988, 51);
		add(title);
		
		String placeholder = "Enter user name";
		
		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModify.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnModify.setBounds(487, 283, 234, 79);
		add(btnModify);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(764, 462, 234, 60);
		add(CancelButton);
		
		JComboBox CBUser = new JComboBox();
		CBUser.setBounds(29, 92, 295, 38);
		add(CBUser);
		
		JComboBox CBRessources = new JComboBox();
		CBRessources.setBounds(359, 93, 295, 37);
		add(CBRessources);
		
		JComboBox CBType = new JComboBox();
		CBType.setBounds(684, 92, 295, 37);
		add(CBType);
		
		textField = new JTextField();
		textField.setText("Duration of reservations");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField.setBounds(684, 167, 295, 38);
		add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("Hour");
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField_1.setBounds(359, 167, 295, 38);
		add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 22));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setBounds(29, 167, 295, 38);
		add(dateChooser);
		
		JComboBox CBChooser = new JComboBox();
		CBChooser.setBounds(25, 295, 409, 69);
		add(CBChooser);

	}
}
