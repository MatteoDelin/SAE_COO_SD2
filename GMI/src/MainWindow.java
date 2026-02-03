import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import PaneReservations.*;
import PaneRessources.*;
import PaneUser.*;

public class MainWindow implements ActionListener {

    private JFrame frame;

    // --- User menu items ---
    private JMenuItem userCreate, userModify, userDelete, userPrint;
    // --- Ressources menu items ---
    private JMenuItem ressourceCreate, ressourceModify, ressourceDelete, ressourcePrint;
    // --- Reservation menu items ---
    private JMenuItem reservationCreate, reservationModify, reservationDelete, reservationPrint;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainWindow window = new MainWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // ===== USER MENU =====
        JMenu userMenu = new JMenu("User");
        menuBar.add(userMenu);

        userCreate = new JMenuItem("Create");
        userCreate.addActionListener(this);
        userMenu.add(userCreate);

        userModify = new JMenuItem("Modify");
        userModify.addActionListener(this);
        userMenu.add(userModify);

        userDelete = new JMenuItem("Delete");
        userDelete.addActionListener(this);
        userMenu.add(userDelete);

        userPrint = new JMenuItem("Print");
        userPrint.addActionListener(this);
        userMenu.add(userPrint);

        // ===== RESSOURCES MENU =====
        JMenu ressourcesMenu = new JMenu("Ressources");
        menuBar.add(ressourcesMenu);

        ressourceCreate = new JMenuItem("Create");
        ressourceCreate.addActionListener(this);
        ressourcesMenu.add(ressourceCreate);

        ressourceModify = new JMenuItem("Modify");
        ressourceModify.addActionListener(this);
        ressourcesMenu.add(ressourceModify);

        ressourceDelete = new JMenuItem("Delete");
        ressourceDelete.addActionListener(this);
        ressourcesMenu.add(ressourceDelete);

        ressourcePrint = new JMenuItem("Print");
        ressourcePrint.addActionListener(this);
        ressourcesMenu.add(ressourcePrint);

        // ===== RESERVATION MENU =====
        JMenu reservationMenu = new JMenu("Reservation");
        menuBar.add(reservationMenu);

        reservationCreate = new JMenuItem("Create");
        reservationCreate.addActionListener(this);
        reservationMenu.add(reservationCreate);

        reservationModify = new JMenuItem("Modify");
        reservationModify.addActionListener(this);
        reservationMenu.add(reservationModify);

        reservationDelete = new JMenuItem("Delete");
        reservationDelete.addActionListener(this);
        reservationMenu.add(reservationDelete);

        reservationPrint = new JMenuItem("Print");
        reservationPrint.addActionListener(this);
        reservationMenu.add(reservationPrint);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // ===== USER =====
        if (src == userCreate) {
            frame.setContentPane(new CreationUser());
        } else if (src == userModify) {
            frame.setContentPane(new ModifieUser());
        } else if (src == userDelete) {
            frame.setContentPane(new DeleteUser());
        } else if (src == userPrint) {
            frame.setContentPane(new PrintUser());
        }

        // ===== RESSOURCES =====
        else if (src == ressourceCreate) {
            frame.setContentPane(new CreationRessources());
        } else if (src == ressourceModify) {
            frame.setContentPane(new ModifieRessources());
        } else if (src == ressourceDelete) {
            frame.setContentPane(new DeleteRessources());
        } else if (src == ressourcePrint) {
            frame.setContentPane(new PrintRessources());
        }

        // ===== RESERVATION =====
        else if (src == reservationCreate) {
            frame.setContentPane(new CreationReservations());
        } else if (src == reservationModify) {
            frame.setContentPane(new ModifieReservations());
        } else if (src == reservationDelete) {
            frame.setContentPane(new DeleteReservations());
        } else if (src == reservationPrint) {
            frame.setContentPane(new PrintReservations());
        }

        frame.revalidate();
        frame.repaint();
    }
}
