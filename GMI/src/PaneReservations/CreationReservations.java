package PaneReservations;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormSpecs;

public class CreationReservations extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField Title;
	private JTextField TextDurée;
	private JTextField txtHoure;
	private JDateChooser dateChooser;

	public CreationReservations() {
		setBackground(new Color(240, 240, 240));
				setLayout(null);
				
				JPanel panel = new JPanel();
				panel.setBounds(0, 0, 449, 68);
				add(panel);
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
						Title = new JTextField();
						panel.add(Title);
						Title.setHorizontalAlignment(SwingConstants.CENTER);
						Title.setFont(new Font("Tahoma", Font.BOLD, 31));
						Title.setText("Reservations Creation");
				
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(0, 0, 473, 157);
				add(panel_1);
						panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
						JComboBox CBUser = new JComboBox();
						panel_1.add(CBUser);
						
								JComboBox CBRessources = new JComboBox();
								panel_1.add(CBRessources);
								
								JComboBox CBType = new JComboBox();
								panel_1.add(CBType);
				
				JPanel panel_2 = new JPanel();
				panel_2.setBounds(0, 0, 1051, 576);
				add(panel_2);
				panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
						// 📅 JCalendar (JDateChooser)
						dateChooser = new JDateChooser();
						panel_2.add(dateChooser);
						dateChooser.getCalendarButton().addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});
						dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 22));
						dateChooser.setDateFormatString("dd/MM/yyyy");
						
								txtHoure = new JTextField();
								panel_2.add(txtHoure);
								txtHoure.setText("Hour");
								txtHoure.setFont(new Font("Tahoma", Font.PLAIN, 22));
								
										TextDurée = new JTextField();
										panel_2.add(TextDurée);
										TextDurée.setFont(new Font("Tahoma", Font.PLAIN, 22));
										TextDurée.setText("Duration of reservations");
				
				JPanel panel_3 = new JPanel();
				panel_3.setBounds(0, 0, 1051, 576);
				add(panel_3);
						panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
						JButton CreateUserButton = new JButton("Create Reservation");
						panel_3.add(CreateUserButton);
						CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
						
						JPanel panel_4 = new JPanel();
						panel_4.setBounds(0, 0, 1051, 576);
						add(panel_4);
						panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
						JButton CancelButton = new JButton("Cancel");
						panel_4.add(CancelButton);
						CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
	}
}
