package PaneUser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class ModifieUser extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField title;
    private JTextField txtNewName;
    private JComboBox<String> listUser;
    private JButton modifyButton;
    private JButton cancelButton;

    public ModifieUser() {
        setLayout(new MigLayout("", "[grow]", "[51px,grow][79px][60px,grow][]"));

        JPanel panel = new JPanel();
        add(panel, "flowx,cell 0 0,grow");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        title = new JTextField();
        panel.add(title);
        title.setText("Modification User");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setColumns(10);

        JPanel panel_1 = new JPanel();
        add(panel_1, "flowx,cell 0 1,grow");
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        listUser = new JComboBox<>();
        listUser.setEditable(true);
        panel_1.add(listUser);
        listUser.setFont(new Font("Tahoma", Font.PLAIN, 29));

        txtNewName = new JTextField();
        panel_1.add(txtNewName);
        txtNewName.setText("New name");
        txtNewName.setHorizontalAlignment(SwingConstants.CENTER);
        txtNewName.setFont(new Font("Tahoma", Font.PLAIN, 28));
        txtNewName.setColumns(10);

        JPanel panel_2 = new JPanel();
        add(panel_2, "cell 0 2,grow");
        panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        modifyButton = new JButton("Modify User");
        panel_2.add(modifyButton);
        modifyButton.setFont(new Font("Tahoma", Font.PLAIN, 28));

        JPanel panel_3 = new JPanel();
        add(panel_3, "cell 0 3,alignx right,growy");
        panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        cancelButton = new JButton("Cancel");
        panel_3.add(cancelButton);
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
    }

    public JComboBox<String> getListUser()  { return listUser; }
    public JTextField getTxtNewName()       { return txtNewName; }
    public JButton getModifyButton()        { return modifyButton; }
    public JButton getCancelButton()        { return cancelButton; }
}
