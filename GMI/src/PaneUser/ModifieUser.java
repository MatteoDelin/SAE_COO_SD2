package PaneUser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;

public class ModifieUser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField txtNewName;

	/**
	 * Create the panel.
	 */
	public ModifieUser() {
		setLayout(null);
		
		title = new JTextField();
		title.setText("Modification User");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		title.setBounds(10, 11, 870, 51);
		add(title);
		
		txtNewName = new JTextField();
		
		String placeholder = "Enter user name";
		txtNewName.setText(placeholder);
		txtNewName.setForeground(Color.GRAY);
		
		txtNewName.setToolTipText("");
		txtNewName.setText("New name");
		txtNewName.setHorizontalAlignment(SwingConstants.CENTER);
		txtNewName.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNewName.setColumns(10);
		txtNewName.setBounds(309, 189, 333, 60);
		add(txtNewName);
		
		JButton btnC = new JButton("Modify User");
		btnC.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnC.setBounds(674, 180, 234, 79);
		add(btnC);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(646, 435, 234, 60);
		add(CancelButton);
		
		JComboBox ListUser = new JComboBox();
		ListUser.setToolTipText("");
		ListUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
		ListUser.setBounds(40, 188, 234, 60);
		add(ListUser);

	}
}
