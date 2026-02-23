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
import net.miginfocom.swing.MigLayout;


public class CreationRessources extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField Title;
	private JTextField TextName;
	private JTextField txtDescription;
	private JComboBox CBDomaine;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;

	/**
	 * Create the panel.
	 */
	public CreationRessources() {
		setBackground(new Color(240, 240, 240));
		setLayout(new MigLayout("", "[grow]", "[44px,grow][93px][92px,grow][60px]"));
		
		panel = new JPanel();
		add(panel, "flowx,cell 0 0,grow");
		
		Title = new JTextField();
		panel.add(Title);
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("Tahoma", Font.BOLD, 31));
		Title.setText("Ressource Creation");
		Title.setColumns(10);
		String placeholder = "Enter user name";
		
		panel_2 = new JPanel();
		add(panel_2, "cell 0 2,grow");
		
		JButton CreateUserButton = new JButton("Create ressource");
		panel_2.add(CreateUserButton);
		CreateUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		panel_3 = new JPanel();
		add(panel_3, "cell 0 3,alignx right,growy");
		
		JButton CancelButton = new JButton("Cancel");
		panel_3.add(CancelButton);
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		panel_1 = new JPanel();
		add(panel_1, "flowx,cell 0 1,grow");
		
		CBDomaine = new JComboBox();
		CBDomaine.setEditable(true);
		panel_1.add(CBDomaine);
		
		TextName = new JTextField();
		panel_1.add(TextName);
		TextName.setText("Ressource name");
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
						TextName.setColumns(10);
						
						txtDescription = new JTextField();
						panel_1.add(txtDescription);
						txtDescription.setToolTipText("");
						txtDescription.setText("Description");
						txtDescription.setHorizontalAlignment(SwingConstants.CENTER);
						txtDescription.setForeground(Color.GRAY);
						txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 28));
						txtDescription.setColumns(10);

	}
}
