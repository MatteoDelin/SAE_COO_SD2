package PaneUser;

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


public class CreationUser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField Title;
	private JTextField TextUser;

	/**
	 * Create the panel.
	 */
	public CreationUser() {
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		Title = new JTextField();
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("Tahoma", Font.BOLD, 31));
		Title.setText("User Creation");
		Title.setBounds(10, 11, 1060, 30);
		add(Title);
		Title.setColumns(10);
		
		TextUser = new JTextField();
		String placeholder = "Enter user name";
		TextUser.setText(placeholder);
		TextUser.setForeground(Color.GRAY);

		TextUser.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (TextUser.getText().equals(placeholder)) {
		            TextUser.setText("");
		            TextUser.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (TextUser.getText().isEmpty()) {
		            TextUser.setText(placeholder);
		            TextUser.setForeground(Color.GRAY);
		        }
		    }
		});

		TextUser.setHorizontalAlignment(SwingConstants.CENTER);
		TextUser.setToolTipText("");
		TextUser.setFont(new Font("Tahoma", Font.PLAIN, 28));
		TextUser.setBounds(10, 177, 795, 92);
		add(TextUser);
		TextUser.setColumns(10);
		
		JButton CreateUserButton = new JButton("Create user");
		CreateUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		CreateUserButton.setBounds(846, 177, 178, 92);
		add(CreateUserButton);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		CancelButton.setBounds(836, 649, 234, 60);
		add(CancelButton);

	}
}
