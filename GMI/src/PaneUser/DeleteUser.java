package PaneUser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;

public class DeleteUser extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField title;
    private JComboBox<String> listUser;
    private JButton deleteButton;
    private JButton cancelButton;

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

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, "flowx,cell 0 1,growx");

        listUser = new JComboBox<>();
        listUser.setEditable(true);
        panel.add(listUser);
        listUser.setFont(new Font("Tahoma", Font.PLAIN, 29));

        JPanel panel_2 = new JPanel();
        add(panel_2, "flowx,cell 0 2,grow");

        deleteButton = new JButton("Delete");
        panel_2.add(deleteButton);
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 28));

        JPanel panel_3 = new JPanel();
        add(panel_3, "flowx,cell 0 3,alignx right,growy");

        cancelButton = new JButton("Cancel");
        panel_3.add(cancelButton);
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
    }

    public JComboBox<String> getListUser()  { return listUser; }
    public JButton getDeleteButton()        { return deleteButton; }
    public JButton getCancelButton()        { return cancelButton; }
}
