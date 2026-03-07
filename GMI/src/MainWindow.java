import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JOptionPane;

import PaneReservations.*;
import PaneRessources.*;
import PaneUser.*;

public class MainWindow implements ActionListener {

    private JFrame frame;

    // --- Home menu item ---
    private JMenuItem homeItem;
    // --- User menu items ---
    private JMenuItem userCreate, userModify, userDelete, userPrint;
    // --- Ressources menu items ---
    private JMenuItem ressourceCreate, ressourceModify, ressourceDelete, ressourcePrint;
    // --- Reservation menu items ---
    private JMenuItem reservationCreate, reservationModify, reservationDelete, reservationPrint;

    // --- Panels courants ---
    private CreationUser    panelCreationUser;
    private ModifieUser     panelModifieUser;
    private DeleteUser      panelDeleteUser;
    private PrintUser       panelPrintUser;

    private CreationRessources  panelCreationRessources;
    private ModifieRessources   panelModifieRessources;
    private DeleteRessources    panelDeleteRessources;
    private PrintRessources     panelPrintRessources;

    private CreationReservations    panelCreationReservations;
    private ModifieReservations     panelModifieReservations;
    private DeleteReservations      panelDeleteReservations;
    private PrintReservations       panelPrintReservations;

    // -----------------------------------------------------------------------
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

    // -----------------------------------------------------------------------
    public MainWindow() {

        frame = new JFrame("GMI – Resource Management");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // ===== HOME MENU =====
        JMenu homeMenu = new JMenu("🏠 Home");
        menuBar.add(homeMenu);

        homeItem = new JMenuItem("Back to Home");
        homeItem.addActionListener(this);
        homeMenu.add(homeItem);

        homeMenu.add(new JSeparator());

        JMenuItem homeExport = new JMenuItem("Export CSV…");
        homeExport.addActionListener(ev -> {
            // Ouvre l'accueil positionné sur le HomePanel puis lance l'export
            HomePanel hp = new HomePanel();
            showPanel(hp);
            hp.getExportButton().doClick();
        });
        homeMenu.add(homeExport);

        // ===== USER MENU =====
        JMenu userMenu = new JMenu("User");
        menuBar.add(userMenu);

        userCreate = new JMenuItem("Create");   userCreate.addActionListener(this);   userMenu.add(userCreate);
        userModify = new JMenuItem("Modify");   userModify.addActionListener(this);   userMenu.add(userModify);
        userDelete = new JMenuItem("Delete");   userDelete.addActionListener(this);   userMenu.add(userDelete);
        userPrint  = new JMenuItem("Print");    userPrint.addActionListener(this);    userMenu.add(userPrint);

        // ===== RESSOURCES MENU =====
        JMenu ressourcesMenu = new JMenu("Resources");
        menuBar.add(ressourcesMenu);

        ressourceCreate = new JMenuItem("Create"); ressourceCreate.addActionListener(this); ressourcesMenu.add(ressourceCreate);
        ressourceModify = new JMenuItem("Modify"); ressourceModify.addActionListener(this); ressourcesMenu.add(ressourceModify);
        ressourceDelete = new JMenuItem("Delete"); ressourceDelete.addActionListener(this); ressourcesMenu.add(ressourceDelete);
        ressourcePrint  = new JMenuItem("Print");  ressourcePrint.addActionListener(this);  ressourcesMenu.add(ressourcePrint);

        // ===== RESERVATION MENU =====
        JMenu reservationMenu = new JMenu("Reservation");
        menuBar.add(reservationMenu);

        reservationCreate = new JMenuItem("Create"); reservationCreate.addActionListener(this); reservationMenu.add(reservationCreate);
        reservationModify = new JMenuItem("Modify"); reservationModify.addActionListener(this); reservationMenu.add(reservationModify);
        reservationDelete = new JMenuItem("Delete"); reservationDelete.addActionListener(this); reservationMenu.add(reservationDelete);
        reservationPrint  = new JMenuItem("Print");  reservationPrint.addActionListener(this);  reservationMenu.add(reservationPrint);

        // ===== CHARTS MENU =====
        JMenu chartsMenu = new JMenu("📊 Charts");
        menuBar.add(chartsMenu);

        JMenuItem cTop5     = new JMenuItem("Top 5 Borrowed Resources");
        JMenuItem cTopUser  = new JMenuItem("Most Active Users");
        JMenuItem cPropDom  = new JMenuItem("Avg Duration per Domain");
        JMenuItem cPeriode  = new JMenuItem("Rate over a Period");
        JMenuItem cDomaine  = new JMenuItem("Rate for a Domain");
        JMenuItem cEvol     = new JMenuItem("Rate Evolution over Time");

        cTop5   .addActionListener(ev -> showPanel(new TopRessourcesChart()));
        cTopUser.addActionListener(ev -> showPanel(new TopUserChart()));
        cPropDom.addActionListener(ev -> showPanel(new DureeMoyenneDomaineChart()));
        cPeriode.addActionListener(ev -> showPanel(new TauxEmpruntPeriodeChart()));
        cDomaine.addActionListener(ev -> showPanel(new TauxEmpruntDomaineChart()));
        cEvol   .addActionListener(ev -> showPanel(new EvolutionTauxChart()));

        chartsMenu.add(cTop5); chartsMenu.add(cTopUser); chartsMenu.add(cPropDom);
        chartsMenu.addSeparator();
        chartsMenu.add(cPeriode); chartsMenu.add(cDomaine); chartsMenu.add(cEvol);

        // ── Affichage initial : panneau d'accueil ──────────────────────────
        frame.setContentPane(new HomePanel());
    }

    // -----------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // ===== HOME =====
        if (src == homeItem) {
            showPanel(new HomePanel());
            return;
        }

        // ===== USER =====
        if (src == userCreate) {
            panelCreationUser = new CreationUser();
            wireCreationUser(panelCreationUser);
            showPanel(panelCreationUser);

        } else if (src == userModify) {
            panelModifieUser = new ModifieUser();
            refreshUserCombo(panelModifieUser);
            wireModifieUser(panelModifieUser);
            showPanel(panelModifieUser);

        } else if (src == userDelete) {
            panelDeleteUser = new DeleteUser();
            refreshUserCombo(panelDeleteUser);
            wireDeleteUser(panelDeleteUser);
            showPanel(panelDeleteUser);

        } else if (src == userPrint) {
            panelPrintUser = new PrintUser();
            wirePrintUser(panelPrintUser);
            showPanel(panelPrintUser);
        }

        // ===== RESSOURCES =====
        else if (src == ressourceCreate) {
            panelCreationRessources = new CreationRessources();
            wireCreationRessources(panelCreationRessources);
            showPanel(panelCreationRessources);

        } else if (src == ressourceModify) {
            panelModifieRessources = new ModifieRessources();
            refreshRessourceCombo(panelModifieRessources);
            wireModifieRessources(panelModifieRessources);
            showPanel(panelModifieRessources);

        } else if (src == ressourceDelete) {
            panelDeleteRessources = new DeleteRessources();
            refreshRessourceCombo(panelDeleteRessources);
            wireDeleteRessources(panelDeleteRessources);
            showPanel(panelDeleteRessources);

        } else if (src == ressourcePrint) {
            panelPrintRessources = new PrintRessources();
            wirePrintRessources(panelPrintRessources);
            showPanel(panelPrintRessources);
        }

        // ===== RESERVATION =====
        else if (src == reservationCreate) {
            panelCreationReservations = new CreationReservations();
            wireCreationReservations(panelCreationReservations);
            showPanel(panelCreationReservations);

        } else if (src == reservationModify) {
            panelModifieReservations = new ModifieReservations();
            refreshReservationCombos(panelModifieReservations);
            wireModifieReservations(panelModifieReservations);
            showPanel(panelModifieReservations);

        } else if (src == reservationDelete) {
            panelDeleteReservations = new DeleteReservations();
            refreshReservationCombos(panelDeleteReservations);
            wireDeleteReservations(panelDeleteReservations);
            showPanel(panelDeleteReservations);

        } else if (src == reservationPrint) {
            panelPrintReservations = new PrintReservations();
            wirePrintReservations(panelPrintReservations);
            showPanel(panelPrintReservations);
        }
    }

    // -----------------------------------------------------------------------
    // Utilitaire : affiche un panneau dans la fenêtre
    // -----------------------------------------------------------------------
    private void showHome() {
        showPanel(new HomePanel());
    }

    private void showPanel(javax.swing.JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ============================= USER =====================================

    /** Branche le bouton "Create user" sur Utilisateur */
    private void wireCreationUser(CreationUser p) {
        p.getCreateButton().addActionListener(ev -> {
            String nom = p.getTextUser().getText().trim();
            if (nom.isEmpty() || nom.equals("Enter user name")) {
                showError("Please enter a user name.");
                return;
            }
            if (Utilisateur.print_user(nom) != null) {
                showError("User " + nom + " already exists.");
                return;
            }
            new Utilisateur(nom);
            showInfo("User " + nom + " created successfully.");
            p.getTextUser().setText("Enter user name"); p.getTextUser().setForeground(java.awt.Color.GRAY); // reset placeholder
        });

    }

    /** Remplit le JComboBox de ModifieUser avec les utilisateurs existants */
    private void refreshUserCombo(ModifieUser p) {
        p.getListUser().removeAllItems();
        for (Utilisateur u : Utilisateur.liste_utilisateur) {
            p.getListUser().addItem(u.getNom());
        }
    }

    /** Remplit le JComboBox de DeleteUser */
    private void refreshUserCombo(DeleteUser p) {
        p.getListUser().removeAllItems();
        for (Utilisateur u : Utilisateur.liste_utilisateur) {
            p.getListUser().addItem(u.getNom());
        }
    }

    private void wireModifieUser(ModifieUser p) {
        p.getModifyButton().addActionListener(ev -> {
            String ancien = (String) p.getListUser().getSelectedItem();
            String nouveau = p.getTxtNewName().getText().trim();
            if (ancien == null || nouveau.isEmpty() || nouveau.equals("New name")) {
                showError("Select a user and enter a new name.");
                return;
            }
            Utilisateur u = Utilisateur.print_user(ancien);
            if (u == null) { showError("User not found."); return; }
            u.setNom(nouveau);
            showInfo("Utilisateur renamed to \"" + nouveau + "\".");
            refreshUserCombo(p);
        });

    }

    private void wireDeleteUser(DeleteUser p) {
        p.getDeleteButton().addActionListener(ev -> {
            String nom = (String) p.getListUser().getSelectedItem();
            if (nom == null) { showError("Select a user."); return; }
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Delete user \"" + nom + "\" ?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Utilisateur.delete_user(nom);
                showInfo("User \"" + nom + "\" deleted.");
                refreshUserCombo(p);
            }
        });

    }

    private void wirePrintUser(PrintUser p) {
        String[] colonnes = {"Name"};
        String[][] data = new String[Utilisateur.liste_utilisateur.size()][1];
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++) {
            data[i][0] = Utilisateur.liste_utilisateur.get(i).getNom();
        }
        p.getTable().setModel(new javax.swing.table.DefaultTableModel(data, colonnes));
    }

    // =========================== RESSOURCES =================================

    private void wireCreationRessources(CreationRessources p) {
        // Pré-remplir le combo domaine avec les valeurs distinctes déjà connues
        for (String d : domainesConnus()) p.getCBDomaine().addItem(d);

        p.getCreateButton().addActionListener(ev -> {
            String nom  = p.getTextName().getText().trim();
            String desc = p.getTxtDescription().getText().trim();
            String dom  = p.getCBDomaine().getEditor().getItem().toString().trim();

            if (nom.isEmpty() || nom.equals("Ressource name")) {
                showError("Please enter a resource name."); return;
            }
            if (Ressources.print_user(nom) != null) {
                showError("Resource " + nom + " already exists."); return;
            }

            new Ressources(nom,
                           desc.equals("Description") ? "" : desc,
                           dom, new Date());
            showInfo("");
            p.getTextName().setText("Ressource name"); p.getTextName().setForeground(java.awt.Color.GRAY);
            p.getTxtDescription().setText("Description"); p.getTxtDescription().setForeground(java.awt.Color.GRAY);
        });

    }

    private void refreshRessourceCombo(ModifieRessources p) {
        p.getListRessource().removeAllItems();
        for (Ressources r : Ressources.liste_ressource) p.getListRessource().addItem(r.getNom());
        p.getCBDomaine().removeAllItems();
        for (String d : domainesConnus()) p.getCBDomaine().addItem(d);
    }

    private void refreshRessourceCombo(DeleteRessources p) {
        p.getCBDomaine().removeAllItems();
        for (Ressources r : Ressources.liste_ressource) p.getCBDomaine().addItem(r.getNom());
    }

    private void wireModifieRessources(ModifieRessources p) {
        p.getCheckButton().addActionListener(ev -> {
            String nom = (String) p.getListRessource().getSelectedItem();
            if (nom == null) { showError("Select a resource."); return; }
            Ressources r = Ressources.print_user(nom);
            if (r == null) { showError("Resource not found."); return; }
            p.unlockEdit(r.getNom(), r.getDescription(), r.getDomaine());
        });

        p.getModifyButton().addActionListener(ev -> {
            String ancien       = (String) p.getListRessource().getSelectedItem();
            if (ancien == null) { showError("Select a resource."); return; }
            Ressources r = Ressources.print_user(ancien);
            if (r == null) { showError("Resource not found."); return; }

            String nouveauNom   = p.getTxtNom().getText().trim();
            String nouvelleDesc = p.getTxtDescription().getText().trim();
            String nouveauDom   = p.getCBDomaine().getEditor().getItem().toString().trim();

            if (nouveauNom.isEmpty()) { showError("Name cannot be empty."); return; }
            if (!nouveauNom.equals(ancien) && Ressources.print_user(nouveauNom) != null) {
                showError("A resource named " + nouveauNom + " already exists."); return;
            }

            r.setNom(nouveauNom);
            r.setDescription(nouvelleDesc);
            r.setDomaine(nouveauDom);
            r.setLast_maj(new Date());
            showInfo("Resource modified successfully.");
            refreshRessourceCombo(p);
        });

    }

    private void wireDeleteRessources(DeleteRessources p) {
        p.getDeleteButton().addActionListener(ev -> {
            String nom = (String) p.getCBDomaine().getSelectedItem();
            if (nom == null) { showError("Select a resource."); return; }
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Delete resource \"" + nom + "\" ?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Ressources.delete_user(nom);
                showInfo("");
                refreshRessourceCombo(p);
            }
        });

    }

    private void wirePrintRessources(PrintRessources p) {
        String[] colonnes = {"Name", "Description", "Domain", "Last update"};
        String[][] data = new String[Ressources.liste_ressource.size()][4];
        for (int i = 0; i < Ressources.liste_ressource.size(); i++) {
            Ressources r = Ressources.liste_ressource.get(i);
            data[i][0] = r.getNom();
            data[i][1] = r.getDescription();
            data[i][2] = r.getDomaine();
            data[i][3] = r.getLast_maj() != null ? r.getLast_maj().toString() : "";
        }
        p.getTable().setModel(new javax.swing.table.DefaultTableModel(data, colonnes));
    }

    // =========================== RESERVATIONS ===============================

    private void wireCreationReservations(CreationReservations p) {
        for (Utilisateur u : Utilisateur.liste_utilisateur) p.getCBUser().addItem(u.getNom());
        for (Ressources r  : Ressources.liste_ressource)    p.getCBRessources().addItem(r.getNom());
        for (String t      : typesEmpruntConnus())          p.getCBType().addItem(t);

        p.getCreateButton().addActionListener(ev -> {
            String nomUser  = p.getCBUser().getEditor().getItem().toString().trim();
            String nomRess  = p.getCBRessources().getEditor().getItem().toString().trim();
            String typeStr  = p.getCBType().getEditor().getItem().toString().trim();
            Date   dateD    = p.getDateChooserStart().getDate();
            String heureStr = p.getTxtHoureStart().getText().trim();
            String dureeStr = p.getTxtDuree().getText().trim();

            if (nomUser.isEmpty() || nomRess.isEmpty() || dateD == null) {
                showError("Please fill in the user, resource and date."); return;
            }

            Utilisateur user = Utilisateur.print_user(nomUser);
            if (user == null) user = new Utilisateur(nomUser);

            Ressources ress = Ressources.print_user(nomRess);
            if (ress == null) ress = new Ressources(nomRess, "", "", new Date());

            LocalTime heure = parseHeure(heureStr);
            if (heure == null) { showError("Invalid time format (HH:mm)."); return; }

            int duree;
            try { duree = Integer.parseInt(dureeStr); }
            catch (NumberFormatException ex) { showError("Duration must be an integer (minutes)."); return; }

            new Reservations(user, ress, dateD, heure, duree, typeStr.isEmpty() ? "Emprunt" : typeStr);
            showInfo("Reservation created successfully.");
        });

    }

    private void refreshReservationCombos(ModifieReservations p) {
        for (Utilisateur u : Utilisateur.liste_utilisateur) p.getCBUser().addItem(u.getNom());
        for (Ressources r  : Ressources.liste_ressource)    p.getCBRessources().addItem(r.getNom());
        for (String t      : typesEmpruntConnus())          p.getCBType().addItem(t);
    }

    private void refreshReservationCombos(DeleteReservations p) {
        for (Utilisateur u : Utilisateur.liste_utilisateur) p.getCBUser().addItem(u.getNom());
        for (Ressources r  : Ressources.liste_ressource)    p.getCBRessources().addItem(r.getNom());
    }

    private void wireModifieReservations(ModifieReservations p) {

        // Bouton Check : identifie la réservation par utilisateur + ressource + date
        p.getCheckButton().addActionListener(ev -> {
            String nomUser = (String) p.getCBUser().getSelectedItem();
            String nomRess = (String) p.getCBRessources().getSelectedItem();
            Date   date    = p.getDateChooser().getDate();

            if (nomUser == null || nomRess == null || date == null) {
                showError("Select a user, a resource and a date.");
                return;
            }

            Reservations res = Reservations.print_reservation(
                    Reservations.liste_reservations, nomUser, nomRess, date);
            if (res == null) {
                showError("No reservation found for these criteria.");
                return;
            }

            // Pré-remplit et déverrouille les champs modifiables
            String heureActuelle = res.getHeure() != null
                    ? res.getHeure().format(DateTimeFormatter.ofPattern("HH:mm")) : "00:00";
            p.unlockEdit(heureActuelle, res.getDuree(), res.getType_emprunt());
        });

        // Bouton Modify : applique les modifications sur la réservation trouvée
        p.getModifyButton().addActionListener(ev -> {
            String nomUser = (String) p.getCBUser().getSelectedItem();
            String nomRess = (String) p.getCBRessources().getSelectedItem();
            Date   date    = p.getDateChooser().getDate();

            if (nomUser == null || nomRess == null || date == null) {
                showError("Search criteria are incomplete."); return;
            }

            Reservations res = Reservations.print_reservation(
                    Reservations.liste_reservations, nomUser, nomRess, date);
            if (res == null) { showError("Reservation not found."); return; }

            // Heure modifiable
            LocalTime nouvelleHeure = parseHeure(p.getTxtHour().getText().trim());
            if (nouvelleHeure == null) {
                showError("Invalid time format. Use HH:mm."); return;
            }

            // Durée modifiable
            int nouvelleDuree;
            try {
                nouvelleDuree = Integer.parseInt(p.getTxtDuree().getText().trim());
            } catch (NumberFormatException ex) {
                showError("Duration must be an integer (minutes)."); return;
            }

            String nouveauType = p.getCBType().getEditor().getItem().toString().trim();

            res.setHeure(nouvelleHeure);
            res.setDuree(nouvelleDuree);
            res.setType_emprunt(nouveauType.isEmpty() ? "Emprunt" : nouveauType);
            showInfo("Reservation modified successfully.");
        });

    }

    private void wireDeleteReservations(DeleteReservations p) {
        p.getDeleteButton().addActionListener(ev -> {
            String nomUser = (String) p.getCBUser().getSelectedItem();
            String nomRess = (String) p.getCBRessources().getSelectedItem();
            Date   date    = p.getDateChooser().getDate();

            if (nomUser == null || nomRess == null || date == null) {
                showError("Select a user, a resource and a date."); return;
            }

            Reservations res = Reservations.print_reservation(
                    Reservations.liste_reservations, nomUser, nomRess, date);
            if (res == null) { showError("Reservation not found."); return; }

            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Delete reservation for \"" + nomUser
                    + "\" pour \"" + nomRess + "\" ?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Reservations.liste_reservations.remove(res);
                showInfo("Reservation deleted.");
            }
        });

    }

    private void wirePrintReservations(PrintReservations p) {
        String[] colonnes = {"User", "Resource", "Date", "Time", "Duration", "Type"};
        String[][] data = new String[Reservations.liste_reservations.size()][6];
        for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
            Reservations r = Reservations.liste_reservations.get(i);
            data[i][0] = r.getUser().getNom();
            data[i][1] = r.getRessource().getNom();
            data[i][2] = r.getDate() != null ? r.getDate().toString() : "";
            data[i][3] = r.getHeure() != null ? r.getHeure().toString() : "";
            data[i][4] = String.valueOf(r.getDuree());
            data[i][5] = r.getType_emprunt();
        }
        p.getTable().setModel(new javax.swing.table.DefaultTableModel(data, colonnes));
    }

    // -----------------------------------------------------------------------
    // Utilitaires
    // -----------------------------------------------------------------------

    /** Parse une heure au format HH:mm, retourne null si invalide. */
    private LocalTime parseHeure(String s) {
        try {
            return LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /** Retourne les domaines distincts issus de Ressources.liste_ressource, triés. */
    private String[] domainesConnus() {
        java.util.TreeSet<String> set = new java.util.TreeSet<>();
        for (Ressources r : Ressources.liste_ressource)
            if (r.getDomaine() != null && !r.getDomaine().trim().isEmpty())
                set.add(r.getDomaine().trim());
        return set.toArray(new String[0]);
    }

    /** Retourne les types d'emprunt distincts issus de Reservations.liste_reservations, triés. */
    private String[] typesEmpruntConnus() {
        java.util.TreeSet<String> set = new java.util.TreeSet<>();
        // Valeurs par défaut toujours présentes
        set.add("Cours"); set.add("Emprunt"); set.add("Maintenance");
        for (Reservations r : Reservations.liste_reservations)
            if (r.getType_emprunt() != null && !r.getType_emprunt().trim().isEmpty())
                set.add(r.getType_emprunt().trim());
        return set.toArray(new String[0]);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
