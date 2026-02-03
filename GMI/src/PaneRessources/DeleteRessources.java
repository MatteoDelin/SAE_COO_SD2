package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DeleteRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;

	/**
	 * Create the panel.
	 */
	public DeleteRessources() {
		setLayout(null);
		
		title = new JTextField();		
		title.setText("Delete User");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		title.setBounds(10, 11, 1031, 51);
		add(title);
		
		JButton btnDelate = new JButton("Delete");
		btnDelate.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnDelate.setBounds(716, 190, 178, 79);
		add(btnDelate);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(807, 525, 234, 60);
		add(CancelButton);
		
		JComboBox ListUser = new JComboBox();
		ListUser.setToolTipText("");
		ListUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
		ListUser.setBounds(82, 198, 397, 60);
		add(ListUser);

	}

}
