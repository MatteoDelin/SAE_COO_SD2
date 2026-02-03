package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

public class CreationReservations extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField Title;
	private JTextField TextDurée;
	private JTextField textField_1;
	private JDateChooser dateChooser;

	public CreationReservations() {
		setBackground(new Color(240, 240, 240));
		setLayout(null);

		Title = new JTextField();
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("Tahoma", Font.BOLD, 31));
		Title.setText("Reservations Creation");
		Title.setBounds(10, 11, 1008, 44);
		add(Title);

		JButton CreateUserButton = new JButton("Create Reservation");
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		CreateUserButton.setBounds(342, 426, 315, 92);
		add(CreateUserButton);

		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(784, 496, 234, 60);
		add(CancelButton);

		JComboBox CBUser = new JComboBox();
		CBUser.setBounds(32, 196, 295, 38);
		add(CBUser);

		JComboBox CBRessources = new JComboBox();
		CBRessources.setBounds(362, 197, 295, 37);
		add(CBRessources);

		TextDurée = new JTextField();
		TextDurée.setFont(new Font("Tahoma", Font.PLAIN, 22));
		TextDurée.setText("Duration of reservations");
		TextDurée.setBounds(695, 196, 267, 38);
		add(TextDurée);

		// 📅 JCalendar (JDateChooser)
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dateChooser.setBounds(32, 271, 267, 38);
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 22));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		add(dateChooser);

		textField_1 = new JTextField();
		textField_1.setText("End time / comment");
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField_1.setBounds(362, 271, 267, 38);
		add(textField_1);
	}
}
