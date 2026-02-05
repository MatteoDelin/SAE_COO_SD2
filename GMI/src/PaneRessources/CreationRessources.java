package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JComboBox;


public class CreationRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField Title;
	private JTextField TextName;
	private JTextField txtDescription;
	private JComboBox CBDomaine;

	/**
	 * Create the panel.
	 */
	public CreationRessources() {
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		Title = new JTextField();
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("Tahoma", Font.BOLD, 31));
		Title.setText("Ressource Creation");
		Title.setBounds(10, 11, 1060, 44);
		add(Title);
		Title.setColumns(10);
		
		TextName = new JTextField();
		String placeholder = "Enter user name";
		TextName.setText("Enter ressource name");
		TextName.setForeground(Color.GRAY);

		TextName.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (TextName.getText().equals(placeholder)) {
		            TextName.setText("");
		            TextName.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (TextName.getText().isEmpty()) {
		            TextName.setText(placeholder);
		            TextName.setForeground(Color.GRAY);
		        }
		    }
		});

		TextName.setHorizontalAlignment(SwingConstants.CENTER);
		TextName.setToolTipText("");
		TextName.setFont(new Font("Tahoma", Font.PLAIN, 28));
		TextName.setBounds(382, 206, 326, 92);
		add(TextName);
		TextName.setColumns(10);
		
		JButton CreateUserButton = new JButton("Create ressource");
		CreateUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		CreateUserButton.setBounds(385, 340, 269, 92);
		add(CreateUserButton);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(833, 491, 234, 60);
		add(CancelButton);
		
		txtDescription = new JTextField();
		txtDescription.setToolTipText("");
		txtDescription.setText("Enter description");
		txtDescription.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescription.setForeground(Color.GRAY);
		txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtDescription.setColumns(10);
		txtDescription.setBounds(741, 206, 326, 92);
		add(txtDescription);
		
		CBDomaine = new JComboBox();
		CBDomaine.setBounds(27, 205, 326, 92);
		add(CBDomaine);

	}
}
