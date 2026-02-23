package PaneUser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class DeleteUser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;

	/**
	 * Create the panel.
	 */
	public DeleteUser() {
		setLayout(new MigLayout("", "[grow]", "[54px,grow][][grow][60px]"));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 0,grow");
		
		title = new JTextField();		
		panel_1.add(title);
		title.setText("Delete User");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "flowx,cell 0 2,grow");
		
		JButton btnDelate = new JButton("Delete");
		panel_2.add(btnDelate);
		btnDelate.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "flowx,cell 0 3,alignx right,growy");
		
		JButton CancelButton = new JButton("Cancel");
		panel_3.add(CancelButton);
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		add(panel, "flowx,cell 0 1,growx");
		
		JComboBox ListUser = new JComboBox();
		ListUser.setEditable(true);
		panel.add(ListUser);
		ListUser.setToolTipText("");
		ListUser.setFont(new Font("Tahoma", Font.PLAIN, 29));

	}
}
