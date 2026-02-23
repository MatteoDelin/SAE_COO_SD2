package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class PrintReservations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtAllReservation;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PrintReservations() {
		setLayout(new MigLayout("", "[grow]", "[51px][grow]"));
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 0,grow");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtAllReservation = new JTextField();
		panel.add(txtAllReservation);
		txtAllReservation.setText("All Reservation");
		txtAllReservation.setHorizontalAlignment(SwingConstants.CENTER);
		txtAllReservation.setFont(new Font("Tahoma", Font.BOLD, 32));
		txtAllReservation.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 1");
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		table = new JTable();
		panel_1.add(table);

	}
}
