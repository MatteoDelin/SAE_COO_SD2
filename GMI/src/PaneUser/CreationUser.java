package PaneUser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import net.miginfocom.swing.MigLayout;

public class CreationUser extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField Title;
    private JTextField TextUser;
    private JButton createButton;
    private JButton cancelButton;

    public CreationUser() {
        setBackground(new Color(240, 240, 240));
        setLayout(new MigLayout("", "[grow]", "[39px,grow][][60px,grow][]"));

        JPanel panel = new JPanel();
        add(panel, "cell 0 0,grow");

        Title = new JTextField();
        panel.add(Title);
        Title.setHorizontalAlignment(SwingConstants.CENTER);
        Title.setFont(new Font("Tahoma", Font.BOLD, 31));
        Title.setText("User Creation");
        Title.setColumns(10);

        String placeholder = "Enter user name";

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");

        TextUser = new JTextField();
        panel_1.add(TextUser);
        TextUser.setText(placeholder);
        TextUser.setForeground(Color.GRAY);

        TextUser.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                if (TextUser.getText().equals(placeholder)) {
                    TextUser.setText(""); TextUser.setForeground(Color.BLACK);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (TextUser.getText().isEmpty()) {
                    TextUser.setText(placeholder); TextUser.setForeground(Color.GRAY);
                }
            }
        });

        TextUser.setHorizontalAlignment(SwingConstants.CENTER);
        TextUser.setFont(new Font("Tahoma", Font.PLAIN, 28));
        TextUser.setColumns(10);

        JPanel panel_2 = new JPanel();
        add(panel_2, "cell 0 2,grow");

        createButton = new JButton("Create user");
        panel_2.add(createButton);
        createButton.setFont(new Font("Tahoma", Font.PLAIN, 28));

        JPanel panel_3 = new JPanel();
        add(panel_3, "cell 0 3,alignx right,growy");

        cancelButton = new JButton("Cancel");
        panel_3.add(cancelButton);
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
    }

    public JTextField getTextUser()   { return TextUser; }
    public JButton getCreateButton()  { return createButton; }
    public JButton getCancelButton()  { return cancelButton; }
}
