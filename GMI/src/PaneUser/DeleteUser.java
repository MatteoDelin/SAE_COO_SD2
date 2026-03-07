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

    public DeleteUser() {
        setLayout(new MigLayout("", "[grow]", "[54px,grow][][grow]"));

        JPanel panel_1 = new JPanel();
        add(panel_1, "cell 0 0,grow");
        title = new JTextField("Delete User");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setColumns(10);
        title.setEditable(false);
        panel_1.add(title);

        JPanel panel = new JPanel(new FlowLayout());
        add(panel, "flowx,cell 0 1,growx");
        listUser = new JComboBox<>();
        listUser.setEditable(true);
        listUser.setFont(new Font("Tahoma", Font.PLAIN, 29));
        panel.add(listUser);

        JPanel panel_2 = new JPanel();
        add(panel_2, "flowx,cell 0 2,grow");
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
        panel_2.add(deleteButton);
    }

    public JComboBox<String> getListUser() { return listUser; }
    public JButton getDeleteButton()       { return deleteButton; }
}
