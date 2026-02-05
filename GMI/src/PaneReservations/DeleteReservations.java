package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteReservations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField textHour;

	/**
	 * Create the panel.
	 */
	public DeleteReservations() {
		setLayout(null);
		
		title = new JTextField();		
		title.setText("Delete Reservation");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		title.setBounds(10, 11, 1031, 51);
		add(title);
		
		JButton btnDelate = new JButton("Delete");
		btnDelate.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnDelate.setBounds(724, 206, 178, 79);
		add(btnDelate);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(845, 465, 234, 60);
		add(CancelButton);
		
		JComboBox CBUser = new JComboBox();
		CBUser.setBounds(44, 190, 295, 38);
		add(CBUser);
		
		JComboBox CBRessources = new JComboBox();
		CBRessources.setBounds(374, 191, 295, 37);
		add(CBRessources);
		
		textHour = new JTextField();
		textHour.setText("Hour");
		textHour.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textHour.setBounds(374, 265, 295, 38);
		add(textHour);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 22));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setBounds(44, 265, 295, 38);
		add(dateChooser);

	}
}
