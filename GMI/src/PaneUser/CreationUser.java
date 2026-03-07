package PaneUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class CreationUser extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField TextUser;
    private JButton createButton;

    public CreationUser() {
        setBackground(new Color(240, 240, 240));
        setLayout(new MigLayout("", "[grow]", "[39px,grow][][60px,grow]"));

        JPanel panel = new JPanel();
        add(panel, "cell 0 0,grow");
        JTextField title = new JTextField("User Creation");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 31));
        title.setEditable(false);
        panel.add(title);

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");
        TextUser = new JTextField("Enter user name");
        TextUser.setForeground(Color.GRAY);
        TextUser.setHorizontalAlignment(SwingConstants.CENTER);
        TextUser.setFont(new Font("Tahoma", Font.PLAIN, 28));
        TextUser.setColumns(10);
        placeholder(TextUser, "Enter user name");
        panel_1.add(TextUser);

        JPanel panel_2 = new JPanel();
        add(panel_2, "cell 0 2,grow");
        createButton = new JButton("Create user");
        createButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_2.add(createButton);
    }

    private void placeholder(JTextField f, String ph) {
        f.setForeground(Color.GRAY);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(Color.BLACK); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().trim().isEmpty()) { f.setText(ph); f.setForeground(Color.GRAY); }
            }
        });
    }

    public JTextField getTextUser()  { return TextUser; }
    public JButton getCreateButton() { return createButton; }
}
