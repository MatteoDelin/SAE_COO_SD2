package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;

public class ModifieRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ModifieRessources() {
		setLayout(null);
		
		title = new JTextField();
		title.setText("Modification Ressource");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		title.setBounds(10, 11, 870, 51);
		add(title);
		
		String placeholder = "Enter user name";
		
		JButton btnC = new JButton("Modify");
		btnC.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnC.setBounds(485, 322, 276, 79);
		add(btnC);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(654, 487, 234, 60);
		add(CancelButton);
		
		JComboBox ListUser = new JComboBox();
		ListUser.setToolTipText("");
		ListUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
		ListUser.setBounds(98, 330, 334, 60);
		add(ListUser);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setText("Enter ressource name");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.GRAY);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 28));
		textField.setColumns(10);
		textField.setBounds(297, 185, 326, 92);
		add(textField);

	}
}
