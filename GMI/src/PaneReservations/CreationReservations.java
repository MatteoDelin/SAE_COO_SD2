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
	private JTextField txtHoureStart;
	private JDateChooser dateChooserStart;
	private JTextField textHourEnd;

	public CreationReservations() {
		setBackground(new Color(240, 240, 240));
				
				JPanel panel = new JPanel();
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
						Title = new JTextField();
						panel.add(Title);
						Title.setHorizontalAlignment(SwingConstants.CENTER);
						Title.setFont(new Font("Tahoma", Font.BOLD, 31));
						Title.setText("Reservations Creation");
						setLayout(new MigLayout("", "[1012px,grow]", "[100,grow][50][50][][200,grow][]"));
						add(panel, "cell 0 0,growx,aligny top");
						
						JPanel panel_1 = new JPanel();
								panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						
								JComboBox CBUser = new JComboBox();
								CBUser.setEditable(true);
								panel_1.add(CBUser);
								
										JComboBox CBRessources = new JComboBox();
										CBRessources.setEditable(true);
										panel_1.add(CBRessources);
										
										JComboBox CBType = new JComboBox();
										CBType.setEditable(true);
										panel_1.add(CBType);
										add(panel_1, "cell 0 1,growx,aligny center");
										
										JPanel panel_2 = new JPanel();
										panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
												
												JLabel LabelStartDate = new JLabel("Starting date");
												LabelStartDate.setFont(new Font("Dialog", Font.BOLD, 18));
												panel_2.add(LabelStartDate);
										
												// 📅 JCalendar (JDateChooser)
												dateChooserStart = new JDateChooser();
												BorderLayout bl_dateChooserStart = (BorderLayout) dateChooserStart.getLayout();
												panel_2.add(dateChooserStart);
												dateChooserStart.getCalendarButton().addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent e) {
													}
												});
												dateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
												dateChooserStart.setDateFormatString("dd/MM/yyyy");
												
														txtHoureStart = new JTextField();
														panel_2.add(txtHoureStart);
														txtHoureStart.setText("Hour (00:00)");
														txtHoureStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
																add(panel_2, "cell 0 2,grow");
																
																JPanel panel_2_1 = new JPanel();
																add(panel_2_1, "cell 0 3,growx,aligny center");
																panel_2_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
																
																JLabel LabelEndDate = new JLabel("Ending date");
																LabelEndDate.setFont(new Font("Dialog", Font.BOLD, 18));
																panel_2_1.add(LabelEndDate);
																
																JDateChooser dateChooserEnd = new JDateChooser();
																dateChooserEnd.setFont(new Font("Dialog", Font.PLAIN, 22));
																dateChooserEnd.setDateFormatString("dd/MM/yyyy");
																panel_2_1.add(dateChooserEnd);
																
																textHourEnd = new JTextField();
																textHourEnd.setText("Hour (00:00)");
																textHourEnd.setFont(new Font("Dialog", Font.PLAIN, 22));
																panel_2_1.add(textHourEnd);
																
																JPanel panel_3 = new JPanel();
																FlowLayout fl_panel_3 = new FlowLayout(FlowLayout.CENTER, 5, 5);
																panel_3.setLayout(fl_panel_3);
																
																		JButton CreateUserButton = new JButton("Create Reservation");
																		panel_3.add(CreateUserButton);
																		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
																		add(panel_3, "flowx,cell 0 4,alignx center,growy");
																								
																								JPanel panel_4 = new JPanel();
																								panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
																								add(panel_4, "cell 0 5,alignx right,growy");
																								
																										JButton CancelButton = new JButton("Cancel");
																										panel_4.add(CancelButton);
																										CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
	}
}
