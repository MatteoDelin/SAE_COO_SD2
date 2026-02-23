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
import net.miginfocom.swing.MigLayout;

public class DeleteRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField txtRessourceName;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JComboBox cBDomaine;

	/**
	 * Create the panel.
	 */
	public DeleteRessources() {
		setLayout(new MigLayout("", "[grow]", "[51px,grow][92px][grow][60px]"));
		
		panel = new JPanel();
		add(panel, "flowx,cell 0 0,grow");
		
		title = new JTextField();		
		panel.add(title);
		title.setText("Delete Ressource");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		
		panel_3 = new JPanel();
		add(panel_3, "cell 0 2,grow");
		
		JButton btnDelate = new JButton("Delete");
		panel_3.add(btnDelate);
		btnDelate.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		panel_2 = new JPanel();
		add(panel_2, "cell 0 3,alignx right,growy");
		
		JButton CancelButton = new JButton("Cancel");
		panel_2.add(CancelButton);
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		panel_1 = new JPanel();
		add(panel_1, "flowx,cell 0 1,grow");
		
		cBDomaine = new JComboBox();
		cBDomaine.setEditable(true);
		panel_1.add(cBDomaine);
		
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
