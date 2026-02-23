package PaneUser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class ModifieUser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField txtNewName;

	/**
	 * Create the panel.
	 */
	public ModifieUser() {
		setLayout(new MigLayout("", "[grow]", "[51px,grow][79px][60px,grow][]"));
		
		JPanel panel = new JPanel();
		add(panel, "flowx,cell 0 0,grow");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		title = new JTextField();
		panel.add(title);
		title.setText("Modification User");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		
		String placeholder = "Enter user name";
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "cell 0 2,grow");
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnC = new JButton("Modify User");
		panel_2.add(btnC);
		btnC.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "flowx,cell 0 1,grow");
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JComboBox ListUser = new JComboBox();
		ListUser.setEditable(true);
		panel_1.add(ListUser);
		ListUser.setToolTipText("");
		ListUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		txtNewName = new JTextField();
		panel_1.add(txtNewName);
		txtNewName.setText(placeholder);
		txtNewName.setForeground(Color.GRAY);
		
		txtNewName.setToolTipText("");
		txtNewName.setText("New name");
		txtNewName.setHorizontalAlignment(SwingConstants.CENTER);
		txtNewName.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNewName.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "cell 0 3,alignx right,growy");
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton CancelButton = new JButton("Cancel");
		panel_3.add(CancelButton);
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));

	}
}
