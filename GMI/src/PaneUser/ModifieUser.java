package PaneUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class ModifieUser extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> listUser;
    private JTextField txtNewName;
    private JButton modifyButton;

    public ModifieUser() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][79px][60px,grow]"));

        JPanel panel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 5));
        add(panel, "flowx,cell 0 0,grow");
        JTextField title = new JTextField("User Edit");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setEditable(false);
        panel.add(title);

        JPanel panel_1 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 5));
        add(panel_1, "flowx,cell 0 1,grow");
        listUser = new JComboBox<>();
        listUser.setEditable(true);
        listUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
        panel_1.add(listUser);
        txtNewName = new JTextField("New name");
        txtNewName.setHorizontalAlignment(SwingConstants.CENTER);
        txtNewName.setFont(new Font("Tahoma", Font.PLAIN, 28));
        txtNewName.setColumns(10);
        placeholder(txtNewName, "New name");
        panel_1.add(txtNewName);

        JPanel panel_2 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 5));
        add(panel_2, "cell 0 2,grow");
        modifyButton = new JButton("Modify User");
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_2.add(modifyButton);
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

    public JComboBox<String> getListUser()  { return listUser; }
    public JTextField getTxtNewName()       { return txtNewName; }
    public JButton getModifyButton()        { return modifyButton; }
}
