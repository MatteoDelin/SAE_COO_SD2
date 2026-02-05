package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Color;

public class DeleteRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public DeleteRessources() {
		setLayout(null);
		
		title = new JTextField();		
		title.setText("Delete Ressource");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		title.setBounds(10, 11, 1099, 51);
		add(title);
		
		JButton btnDelate = new JButton("Delete");
		btnDelate.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnDelate.setBounds(660, 190, 178, 79);
		add(btnDelate);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(875, 498, 234, 60);
		add(CancelButton);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setText("Enter ressource name");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.GRAY);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 28));
		textField.setColumns(10);
		textField.setBounds(300, 183, 326, 92);
		add(textField);

	}

}
