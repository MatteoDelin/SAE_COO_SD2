package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class PrintReservations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtAllReservation;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PrintReservations() {
		setLayout(null);
		
		txtAllReservation = new JTextField();
		txtAllReservation.setText("All Reservation");
		txtAllReservation.setHorizontalAlignment(SwingConstants.CENTER);
		txtAllReservation.setFont(new Font("Tahoma", Font.BOLD, 32));
		txtAllReservation.setColumns(10);
		txtAllReservation.setBounds(10, 11, 1048, 51);
		add(txtAllReservation);
		
		table = new JTable();
		table.setBounds(10, 73, 1048, 482);
		add(table);

	}
}
