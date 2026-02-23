package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class DeleteReservations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField textHour;

	/**
	 * Create the panel.
	 */
	public DeleteReservations() {
		setLayout(new MigLayout("", "[grow]", "[51px,grow][38px][38px][49px,grow][60px]"));
		
		JPanel panel = new JPanel();
		add(panel, "flowx,cell 0 0,growx,aligny top");
		
		title = new JTextField();		
		panel.add(title);
		title.setText("Delete Reservation");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 32));
		title.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "flowx,cell 0 3,growx,aligny top");
		
		JButton btnDelate = new JButton("Delete");
		panel_3.add(btnDelate);
		btnDelate.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		add(panel_1, "flowx,cell 0 1,grow");
		
		JComboBox CBUser = new JComboBox();
		CBUser.setEditable(true);
		panel_1.add(CBUser);
		
		JComboBox CBRessources = new JComboBox();
		CBRessources.setEditable(true);
		panel_1.add(CBRessources);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "flowx,cell 0 2,growx");
		
		JDateChooser dateChooser = new JDateChooser();
		panel_2.add(dateChooser);
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 22));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setBounds(44, 265, 295, 38);
		
		JLabel LabelStartDate = new JLabel("Starting date");
		dateChooser.add(LabelStartDate, BorderLayout.WEST);
		LabelStartDate.setFont(new Font("Dialog", Font.BOLD, 18));
		
		textHour = new JTextField();
		panel_2.add(textHour);
		textHour.setText("Hour (00:00)");
		textHour.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		JPanel panel_4 = new JPanel();
		add(panel_4, "flowx,cell 0 4,alignx right,growy");
		
		JButton CancelButton = new JButton("Cancel");
		panel_4.add(CancelButton);
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));

	}
}
