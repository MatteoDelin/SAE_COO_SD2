package PaneRessources;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;

public class ModifieRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField txtRessourceName;
	private JTextField txtDescription;

	/**
	 * Create the panel.
	 */
	public ModifieRessources() {
		setLayout(new MigLayout("", "[grow]", "[51px,grow][92px][][][79px,grow][60px]"));
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 0,grow");
		
		title = new JTextField();
		panel.add(title);
		title.setText("Modification Ressource");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		
		String placeholder = "Enter user name";
		
		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 0 2,grow");
		
		JButton btnCheck = new JButton("Check");
		btnCheck.setFont(new Font("Dialog", Font.PLAIN, 28));
		panel_4.add(btnCheck);
		
		JPanel panel_5 = new JPanel();
		add(panel_5, "cell 0 3,grow");
		
		txtDescription = new JTextField();
		txtDescription.setToolTipText("");
		txtDescription.setText("Description");
		txtDescription.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescription.setForeground(Color.GRAY);
		txtDescription.setFont(new Font("Dialog", Font.PLAIN, 28));
		txtDescription.setColumns(10);
		panel_5.add(txtDescription);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "cell 0 5,alignx right,growy");
		
		JButton CancelButton = new JButton("Cancel");
		panel_3.add(CancelButton);
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "flowx,cell 0 4,grow");
		
		JButton btnModify = new JButton("Modify");
		panel_2.add(btnModify);
		btnModify.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "flowx,cell 0 1,grow");
		
		JComboBox ListUser = new JComboBox();
		ListUser.setEditable(true);
		panel_1.add(ListUser);
		ListUser.setToolTipText("");
		ListUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		txtRessourceName = new JTextField();
		panel_1.add(txtRessourceName);
		txtRessourceName.setToolTipText("");
		txtRessourceName.setText("Ressource name");
		txtRessourceName.setHorizontalAlignment(SwingConstants.CENTER);
		txtRessourceName.setForeground(Color.GRAY);
		txtRessourceName.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtRessourceName.setColumns(10);

	}
}
