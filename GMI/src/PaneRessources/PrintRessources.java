package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class PrintRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtAllUser;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PrintRessources() {
		setLayout(null);
		
		txtAllUser = new JTextField();
		txtAllUser.setText("All Ressource");
		txtAllUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtAllUser.setFont(new Font("Tahoma", Font.BOLD, 32));
		txtAllUser.setColumns(10);
		txtAllUser.setBounds(10, 11, 1048, 51);
		add(txtAllUser);
		
		table = new JTable();
		table.setBounds(10, 73, 1048, 471);
		add(table);

	}
}
