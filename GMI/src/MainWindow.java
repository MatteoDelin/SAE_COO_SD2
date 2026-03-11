import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
import PaneCharts.*;
import model.Utilisateur;
import model.Ressources;
import model.Reservations;

/**
 * Fenêtre principale de l'application GMI – Ressource Management.
 *
 * Rôle central de cette classe :
 *   1. Créer la fenêtre et la barre de menus (Home, User, Ressources, Reservation, Charts).
 *   2. Réagir aux clics de menu via actionPerformed() pour afficher le bon panneau.
 *   3. "Câbler" les boutons de chaque panneau CRUD aux opérations du modèle
 *      (Utilisateur, Ressources, Reservations).
 *
 * Architecture générale de l'application :
 *   - model/            → Utilisateur, Ressources, Reservations (données en mémoire)
 *   - PaneUser/         → panneaux CRUD pour les utilisateurs
 *   - PaneRessources/   → panneaux CRUD pour les ressources
 *   - PaneReservations/ → panneaux CRUD pour les réservations
 *   - PaneCharts/       → graphiques d'analyse statistique
 *   - HomePanel         → import/export CSV + panneau d'accueil
 *
 * Principe du "câblage" :
 * Les panneaux CRUD (CreationUser, ModifieUser, etc.) sont de simples formulaires
 * graphiques : ils affichent des champs mais ne savent pas ce qu'il faut faire
 * quand on clique sur "Create" ou "Delete". C'est MainWindow qui connecte
 * ces boutons à la logique métier (ajouter dans la liste, valider les données, etc.)
 * grâce aux méthodes wire*() et refresh*().
 *
 * Cette classe implémente ActionListener pour gérer les clics sur les items de menu.
 */
public class MainWindow implements ActionListener {

    // =========================================================================
    // Attributs — fenêtre et items de menu
    // =========================================================================

    /** La fenêtre principale de l'application (JFrame = fenêtre avec barre de titre). */
    private JFrame frame;

    // --- Items du menu "Home" ---
    /** Item "Back to Home" du menu Home. */
    private JMenuItem homeItem;

    // --- Items du menu "User" ---
    private JMenuItem userCreate; // Affiche CreationUser
    private JMenuItem userModify; // Affiche ModifieUser
    private JMenuItem userDelete; // Affiche DeleteUser
    private JMenuItem userPrint;  // Affiche PrintUser (tableau récapitulatif)

    // --- Items du menu "Ressources" ---
    private JMenuItem ressourceCreate;
    private JMenuItem ressourceModify;
    private JMenuItem ressourceDelete;
    private JMenuItem ressourcePrint;

    // --- Items du menu "Reservation" ---
    private JMenuItem reservationCreate;
    private JMenuItem reservationModify;
    private JMenuItem reservationDelete;
    private JMenuItem reservationPrint;

    // =========================================================================
    // Références aux panneaux courants
    // =========================================================================

    /*
     * Ces références permettent à MainWindow de câbler les boutons des panneaux
     * après leur création. On conserve la référence pour pouvoir rafraîchir les
     * listes déroulantes après chaque opération (par exemple, après avoir créé
     * un utilisateur, le combo "Modify" doit afficher le nouveau nom).
     */

    // Panneaux Utilisateurs
    private CreationUser    panelCreationUser;
    private ModifieUser     panelModifieUser;
    private DeleteUser      panelDeleteUser;
    private PrintUser       panelPrintUser;

    // Panneaux Ressources
    private CreationRessources  panelCreationRessources;
    private ModifieRessources   panelModifieRessources;
    private DeleteRessources    panelDeleteRessources;
    private PrintRessources     panelPrintRessources;

    // Panneaux Réservations
    private CreationReservations    panelCreationReservations;
    private ModifieReservations     panelModifieReservations;
    private DeleteReservations      panelDeleteReservations;
    private PrintReservations       panelPrintReservations;

    // =========================================================================
    // Point d'entrée de l'application
    // =========================================================================

    /**
     * Méthode main : point de départ de l'application.
     * Crée la fenêtre et la rend visible.
     *
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        MainWindow window = new MainWindow(); // Création de la fenêtre
        window.frame.setVisible(true);        // Affichage à l'écran
    }

    // =========================================================================
    // Constructeur — construction de la fenêtre et de la barre de menus
    // =========================================================================

    /**
     * Construit la fenêtre principale, la barre de menus et affiche le panneau d'accueil.
     */
    public MainWindow() {

        // --- Configuration de la fenêtre ---
        frame = new JFrame("GMI – Ressources Management");
        frame.setBounds(100, 100, 900, 600); // Position x=100, y=100, largeur=900, hauteur=600
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application à la croix

        // --- Création de la barre de menus ---
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar); // Attachement de la barre à la fenêtre

        // =================================================================
        // Menu "🏠 Home"
        // =================================================================
        JMenu homeMenu = new JMenu("🏠 Home");
        menuBar.add(homeMenu);

        // Item "Back to Home" : retourne au panneau d'accueil
        homeItem = new JMenuItem("Back to Home");
        homeItem.addActionListener(this); // "this" = MainWindow réagira au clic via actionPerformed
        homeMenu.add(homeItem);
        homeMenu.add(new JSeparator()); // Ligne de séparation visuelle dans le menu

        // Item "Export CSV" : raccourci pour exporter depuis le menu
        // Crée un HomePanel invisible, clique programmatiquement sur son bouton Export
        JMenuItem homeExport = new JMenuItem("Export CSV…");
        homeExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePanel hp = new HomePanel(); // Création du panneau
                showPanel(hp);                  // Affichage du panneau
                hp.getExportButton().doClick(); // Déclenchement automatique du bouton Export
            }
        });
        homeMenu.add(homeExport);

        // =================================================================
        // Menu "User"
        // =================================================================
        JMenu userMenu = new JMenu("User");
        menuBar.add(userMenu);

        // Création des items et ajout dans le menu
        // addActionListener(this) = MainWindow gère le clic dans actionPerformed()
        userCreate = new JMenuItem("Create"); userCreate.addActionListener(this); userMenu.add(userCreate);
        userModify = new JMenuItem("Modify"); userModify.addActionListener(this); userMenu.add(userModify);
        userDelete = new JMenuItem("Delete"); userDelete.addActionListener(this); userMenu.add(userDelete);
        userPrint  = new JMenuItem("Print");  userPrint.addActionListener(this);  userMenu.add(userPrint);

        // =================================================================
        // Menu "Ressources"
        // =================================================================
        JMenu ressourcesMenu = new JMenu("Ressources");
        menuBar.add(ressourcesMenu);
        ressourceCreate = new JMenuItem("Create"); ressourceCreate.addActionListener(this); ressourcesMenu.add(ressourceCreate);
        ressourceModify = new JMenuItem("Modify"); ressourceModify.addActionListener(this); ressourcesMenu.add(ressourceModify);
        ressourceDelete = new JMenuItem("Delete"); ressourceDelete.addActionListener(this); ressourcesMenu.add(ressourceDelete);
        ressourcePrint  = new JMenuItem("Print");  ressourcePrint.addActionListener(this);  ressourcesMenu.add(ressourcePrint);

        // =================================================================
        // Menu "Reservation"
        // =================================================================
        JMenu reservationMenu = new JMenu("Reservation");
        menuBar.add(reservationMenu);
        reservationCreate = new JMenuItem("Create"); reservationCreate.addActionListener(this); reservationMenu.add(reservationCreate);
        reservationModify = new JMenuItem("Modify"); reservationModify.addActionListener(this); reservationMenu.add(reservationModify);
        reservationDelete = new JMenuItem("Delete"); reservationDelete.addActionListener(this); reservationMenu.add(reservationDelete);
        reservationPrint  = new JMenuItem("Print");  reservationPrint.addActionListener(this);  reservationMenu.add(reservationPrint);

        // =================================================================
        // Menu "📊 Charts"
        // =================================================================
        JMenu chartsMenu = new JMenu("Charts");
        menuBar.add(chartsMenu);

        // Un JMenuItem par graphique disponible
        JMenuItem cTop5    = new JMenuItem("Top 5 Borrowed Ressources");
        JMenuItem cTopUser = new JMenuItem("Most Active Users");
        JMenuItem cPropDom = new JMenuItem("Avg Duration per Domain");
        JMenuItem cPeriode = new JMenuItem("Rate over a Period");
        JMenuItem cDomaine = new JMenuItem("Rate for a Domain");
        JMenuItem cEvol    = new JMenuItem("Rate Evolution over Time");

        // Chaque item crée un nouveau panneau graphique et l'affiche dans la fenêtre
        // On utilise new ActionListener() car chaque item a une action différente
        cTop5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showPanel(new TopRessourcesChart()); }
        });
        cTopUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showPanel(new TopUserChart()); }
        });
        cPropDom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showPanel(new DureeMoyenneDomaineChart()); }
        });
        cPeriode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showPanel(new TauxEmpruntPeriodeChart()); }
        });
        cDomaine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showPanel(new TauxEmpruntDomaineChart()); }
        });
        cEvol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showPanel(new EvolutionTauxChart()); }
        });

        // Ajout des items dans le menu Charts (séparateur visuel entre analyse globale et par filtre)
        chartsMenu.add(cTop5); chartsMenu.add(cTopUser); chartsMenu.add(cPropDom);
        chartsMenu.addSeparator(); // Ligne de séparation dans le menu
        chartsMenu.add(cPeriode); chartsMenu.add(cDomaine); chartsMenu.add(cEvol);

        // --- Affichage initial : panneau d'accueil ---
        frame.setContentPane(new HomePanel());
    }

    // =========================================================================
    // Dispatcher des clics de menu (implémentation de ActionListener)
    // =========================================================================

    /**
     * Méthode appelée automatiquement par Java quand l'utilisateur clique
     * sur un item de menu qui a addActionListener(this).
     *
     * On compare la source de l'événement (e.getSource()) avec chaque item
     * de menu connu. Quand on trouve la correspondance, on crée et affiche
     * le panneau approprié, en le câblant si nécessaire.
     *
     * @param e L'événement de clic, qui contient la référence à l'item cliqué.
     */
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource(); // L'item de menu qui a été cliqué

        // --- Accueil ---
        if (src == homeItem) {
            showPanel(new HomePanel()); // Retour au panneau d'accueil
            return;
        }

        // =====================================================================
        // Gestion des utilisateurs
        // =====================================================================

        if (src == userCreate) {
            // Création d'un nouvel utilisateur
            panelCreationUser = new CreationUser();
            wireCreationUser(panelCreationUser); // Câblage du bouton "Create"
            showPanel(panelCreationUser);

        } else if (src == userModify) {
            // Modification d'un utilisateur existant
            panelModifieUser = new ModifieUser();
            refreshUserCombo(panelModifieUser);  // Rempli le combo avec les utilisateurs actuels
            wireModifieUser(panelModifieUser);   // Câblage des boutons "Modify"
            showPanel(panelModifieUser);

        } else if (src == userDelete) {
            // Suppression d'un utilisateur
            panelDeleteUser = new DeleteUser();
            refreshUserCombo(panelDeleteUser);   // Rempli le combo avec les utilisateurs actuels
            wireDeleteUser(panelDeleteUser);     // Câblage du bouton "Delete"
            showPanel(panelDeleteUser);

        } else if (src == userPrint) {
            // Affichage du tableau de tous les utilisateurs
            panelPrintUser = new PrintUser();
            wirePrintUser(panelPrintUser);       // Rempli le tableau
            showPanel(panelPrintUser);
        }

        // =====================================================================
        // Gestion des ressources
        // =====================================================================

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

        // =====================================================================
        // Gestion des réservations
        // =====================================================================

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

    // =========================================================================
    // Utilitaire : affichage d'un panneau dans la fenêtre
    // =========================================================================

    /**
     * Remplace le contenu actuel de la fenêtre par le panneau donné.
     *
     * setContentPane() change le panneau principal de la fenêtre.
     * revalidate() recalcule la disposition (layout) du nouveau panneau.
     * repaint() redessine la fenêtre pour que le changement soit visible.
     *
     * @param panel Le nouveau panneau à afficher.
     */
    private void showPanel(javax.swing.JPanel panel) {
        frame.setContentPane(panel); // Remplacement du contenu
        frame.revalidate();          // Recalcul du layout
        frame.repaint();             // Redessin de la fenêtre
    }

    // =========================================================================
    // Câblage — Utilisateurs
    // =========================================================================

    /**
     * Câble le bouton "Create" du panneau CreationUser à la logique de création.
     *
     * Quand l'utilisateur clique sur "Create" :
     *   1. On lit le texte du champ de saisie.
     *   2. On vérifie que le nom n'est pas vide et n'est pas déjà pris.
     *   3. On crée le nouvel Utilisateur (qui s'ajoute automatiquement à la liste).
     *   4. On affiche un message de confirmation et on réinitialise le champ.
     *
     * @param p Le panneau CreationUser contenant les composants à câbler.
     */
    private void wireCreationUser(final CreationUser p) {
        p.getCreateButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nom = p.getTextUser().getText().trim(); // Lecture + suppression des espaces

                // Validation : champ vide ou contenant encore le texte d'aide
                if (nom.isEmpty() || nom.equals("Enter user name")) {
                    showError("Please enter a user name."); return;
                }
                // Validation : un utilisateur avec ce nom existe déjà
                if (Utilisateur.print_user(nom) != null) {
                    showError("User " + nom + " already exists."); return;
                }

                // Création de l'utilisateur (s'ajoute automatiquement à liste_utilisateur)
                new Utilisateur(nom);
                showInfo("User " + nom + " created successfully.");

                // Réinitialisation du champ de saisie (texte d'aide en gris)
                p.getTextUser().setText("Enter user name");
                p.getTextUser().setForeground(java.awt.Color.GRAY);
            }
        });
    }

    /**
     * Remplit le combo de ModifieUser avec tous les utilisateurs actuellement en mémoire.
     * Appelé avant d'afficher le panneau et après chaque modification pour maintenir
     * la liste à jour.
     * @param p Le panneau ModifieUser dont le combo doit être mis à jour.
     */
    private void refreshUserCombo(ModifieUser p) {
        p.getListUser().removeAllItems(); // Vide le combo avant de le remplir
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++) {
            p.getListUser().addItem(Utilisateur.liste_utilisateur.get(i).getNom());
        }
    }

    /**
     * Remplit le combo de DeleteUser avec tous les utilisateurs actuellement en mémoire.
     * @param p Le panneau DeleteUser dont le combo doit être mis à jour.
     */
    private void refreshUserCombo(DeleteUser p) {
        p.getListUser().removeAllItems();
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++) {
            p.getListUser().addItem(Utilisateur.liste_utilisateur.get(i).getNom());
        }
    }

    /**
     * Câble le bouton "Modify" du panneau ModifieUser à la logique de renommage.
     *
     * L'utilisateur sélectionne un nom dans le combo et saisit le nouveau nom.
     * On retrouve l'objet Utilisateur via print_user() et on appelle setNom().
     * Toutes les réservations qui référencent cet objet voient le changement
     * automatiquement car elles stockent une référence, pas une copie du nom.
     *
     * @param p Le panneau ModifieUser.
     */
    private void wireModifieUser(final ModifieUser p) {
        p.getModifyButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String ancien  = (String) p.getListUser().getSelectedItem(); // Nom actuel sélectionné
                String nouveau = p.getTxtNewName().getText().trim();          // Nouveau nom saisi

                // Validation des champs
                if (ancien == null || nouveau.isEmpty() || nouveau.equals("New name")) {
                    showError("Select a user and enter a new name."); return;
                }

                // Recherche de l'objet utilisateur en mémoire
                Utilisateur u = Utilisateur.print_user(ancien);
                if (u == null) { showError("User not found."); return; }

                // Renommage : setNom() modifie le nom de l'objet
                // Les réservations voient le changement automatiquement (même référence)
                u.setNom(nouveau);
                showInfo("User renamed to \"" + nouveau + "\".");

                // Rafraîchissement du combo pour afficher le nouveau nom
                refreshUserCombo(p);
            }
        });
    }

    /**
     * Câble le bouton "Delete" du panneau DeleteUser à la logique de suppression.
     * Une boîte de confirmation est affichée avant la suppression effective.
     * @param p Le panneau DeleteUser.
     */
    private void wireDeleteUser(final DeleteUser p) {
        p.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nom = (String) p.getListUser().getSelectedItem();
                if (nom == null) { showError("Select a user."); return; }

                // Demande de confirmation avant suppression
                int choix = JOptionPane.showConfirmDialog(
                    frame, "Delete user \"" + nom + "\" ?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (choix == JOptionPane.YES_OPTION) {
                    Utilisateur.delete_user(nom); // Suppression de la liste globale
                    showInfo("User \"" + nom + "\" deleted.");
                    refreshUserCombo(p); // Mise à jour du combo
                }
            }
        });
    }

    /**
     * Remplit le tableau de PrintUser avec tous les utilisateurs en mémoire.
     * Crée un DefaultTableModel avec une colonne "Name" et une ligne par utilisateur.
     * @param p Le panneau PrintUser.
     */
    private void wirePrintUser(PrintUser p) {
        String[] colonnes = {"Name"};
        String[][] data = new String[Utilisateur.liste_utilisateur.size()][1];
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++) {
            data[i][0] = Utilisateur.liste_utilisateur.get(i).getNom();
        }
        p.getTable().setModel(new javax.swing.table.DefaultTableModel(data, colonnes));
    }

    // =========================================================================
    // Câblage — Ressources
    // =========================================================================

    /**
     * Câble le bouton "Create" du panneau CreationRessources.
     * Pré-remplit aussi le combo "Domaine" avec les domaines déjà connus.
     * @param p Le panneau CreationRessources.
     */
    private void wireCreationRessources(final CreationRessources p) {

        // Pré-remplissage du combo domaine avec les domaines existants
        for (String d : domainesConnus()) p.getCBDomaine().addItem(d);

        p.getCreateButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nom  = p.getTextName().getText().trim();
                String desc = p.getTxtDescription().getText().trim();
                // getEditor().getItem() permet de lire la valeur saisie dans un combo éditable
                String dom  = p.getCBDomaine().getEditor().getItem().toString().trim();

                // Validation du nom
                if (nom.isEmpty() || nom.equals("Ressource name")) {
                    showError("Please enter a ressource name."); return;
                }
                if (Ressources.print_user(nom) != null) {
                    showError("Ressource " + nom + " already exists."); return;
                }

                // Création de la ressource (new Date() = date et heure actuelles)
                new Ressources(nom, desc.equals("Description") ? "" : desc, dom, new Date());
                showInfo("Ressource \"" + nom + "\" created successfully.");

                // Réinitialisation des champs de saisie
                p.getTextName().setText("Ressource name");
                p.getTextName().setForeground(java.awt.Color.GRAY);
                p.getTxtDescription().setText("Description");
                p.getTxtDescription().setForeground(java.awt.Color.GRAY);
            }
        });
    }

    /**
     * Remplit les combos de ModifieRessources avec les ressources et domaines actuels.
     * @param p Le panneau ModifieRessources.
     */
    private void refreshRessourceCombo(ModifieRessources p) {
        p.getListRessource().removeAllItems();
        for (int i = 0; i < Ressources.liste_ressource.size(); i++) {
            p.getListRessource().addItem(Ressources.liste_ressource.get(i).getNom());
        }
        // Mise à jour du combo domaine
        p.getCBDomaine().removeAllItems();
        for (String d : domainesConnus()) p.getCBDomaine().addItem(d);
    }

    /**
     * Remplit le combo de DeleteRessources avec les noms de ressources actuels.
     * @param p Le panneau DeleteRessources.
     */
    private void refreshRessourceCombo(DeleteRessources p) {
        p.getCBDomaine().removeAllItems(); // Ce combo contient les ressources dans ce panneau
        for (int i = 0; i < Ressources.liste_ressource.size(); i++) {
            p.getCBDomaine().addItem(Ressources.liste_ressource.get(i).getNom());
        }
    }

    /**
     * Câble les boutons "Check" et "Modify" du panneau ModifieRessources.
     *
     * "Check" charge les données actuelles de la ressource dans les champs éditables.
     * "Modify" valide et enregistre les modifications.
     *
     * @param p Le panneau ModifieRessources.
     */
    private void wireModifieRessources(final ModifieRessources p) {

        // Bouton "Check" : charge les données de la ressource sélectionnée
        p.getCheckButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = (String) p.getListRessource().getSelectedItem();
                if (nom == null) { showError("Select a ressource."); return; }
                Ressources r = Ressources.print_user(nom);
                if (r == null) { showError("Ressource not found."); return; }
                // unlockEdit() déverrouille les champs et les pré-remplit avec les valeurs actuelles
                p.unlockEdit(r.getNom(), r.getDescription(), r.getDomaine());
            }
        });

        // Bouton "Modify" : enregistre les nouvelles valeurs
        p.getModifyButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ancien = (String) p.getListRessource().getSelectedItem();
                if (ancien == null) { showError("Select a ressource."); return; }
                Ressources r = Ressources.print_user(ancien);
                if (r == null) { showError("Ressource not found."); return; }

                String nouveauNom   = p.getTxtNom().getText().trim();
                String nouvelleDesc = p.getTxtDescription().getText().trim();
                String nouveauDom   = p.getCBDomaine().getEditor().getItem().toString().trim();

                if (nouveauNom.isEmpty()) { showError("Name cannot be empty."); return; }

                // Vérification de doublon uniquement si le nom a changé
                if (!nouveauNom.equals(ancien) && Ressources.print_user(nouveauNom) != null) {
                    showError("A ressource named " + nouveauNom + " already exists."); return;
                }

                // Application des modifications sur l'objet en mémoire
                r.setNom(nouveauNom);
                r.setDescription(nouvelleDesc);
                r.setDomaine(nouveauDom);
                r.setLast_maj(new Date()); // Mise à jour de la date de modification

                showInfo("Ressource modified successfully.");
                refreshRessourceCombo(p); // Mise à jour du combo avec le nouveau nom
            }
        });
    }

    /**
     * Câble le bouton "Delete" du panneau DeleteRessources.
     * @param p Le panneau DeleteRessources.
     */
    private void wireDeleteRessources(final DeleteRessources p) {
        p.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = (String) p.getCBDomaine().getSelectedItem();
                if (nom == null) { showError("Select a ressource."); return; }
                int choix = JOptionPane.showConfirmDialog(
                    frame, "Delete ressource \"" + nom + "\" ?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
                    Ressources.delete_user(nom);
                    showInfo("Ressource \"" + nom + "\" deleted.");
                    refreshRessourceCombo(p);
                }
            }
        });
    }

    /**
     * Remplit le tableau de PrintRessources avec toutes les ressources en mémoire.
     * @param p Le panneau PrintRessources.
     */
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

    // =========================================================================
    // Câblage — Réservations
    // =========================================================================

    /**
     * Câble le bouton "Create" du panneau CreationReservations.
     * Pré-remplit les combos User, Ressource et Type avec les valeurs connues.
     * @param p Le panneau CreationReservations.
     */
    private void wireCreationReservations(final CreationReservations p) {

        // Pré-remplissage des listes déroulantes
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++)
            p.getCBUser().addItem(Utilisateur.liste_utilisateur.get(i).getNom());
        for (int i = 0; i < Ressources.liste_ressource.size(); i++)
            p.getCBRessources().addItem(Ressources.liste_ressource.get(i).getNom());
        for (String t : typesEmpruntConnus()) p.getCBType().addItem(t);

        p.getCreateButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Lecture des valeurs saisies ou sélectionnées
                String nomUser  = p.getCBUser().getEditor().getItem().toString().trim();
                String nomRess  = p.getCBRessources().getEditor().getItem().toString().trim();
                String typeStr  = p.getCBType().getEditor().getItem().toString().trim();
                Date   dateD    = p.getDateChooserStart().getDate();
                String heureStr = p.getTxtHoureStart().getText().trim();
                String dureeStr = p.getTxtDuree().getText().trim();

                // Validation des champs obligatoires
                if (nomUser.isEmpty() || nomRess.isEmpty() || dateD == null) {
                    showError("Please fill in the user, ressource and date."); return;
                }

                // Si l'utilisateur n'existe pas encore, on le crée automatiquement
                Utilisateur user = Utilisateur.print_user(nomUser);
                if (user == null) user = new Utilisateur(nomUser);

                // Si la ressource n'existe pas encore, on la crée automatiquement
                Ressources ress = Ressources.print_user(nomRess);
                if (ress == null) ress = new Ressources(nomRess, "", "", new Date());

                // Parsing de l'heure (format HH:mm attendu)
                LocalTime heure = parseHeure(heureStr);
                if (heure == null) { showError("Invalid time format (HH:mm)."); return; }

                // Parsing de la durée (doit être un entier)
                int duree;
                try {
                    duree = Integer.parseInt(dureeStr);
                } catch (NumberFormatException ex) {
                    showError("Duration must be an integer (minutes)."); return;
                }

                // Création de la réservation (s'ajoute automatiquement à liste_reservations)
                new Reservations(user, ress, dateD, heure, duree,
                    typeStr.isEmpty() ? "Emprunt" : typeStr); // Type par défaut = "Emprunt"

                showInfo("Reservation created successfully.");
            }
        });
    }

    /**
     * Remplit les combos de ModifieReservations avec les données actuelles.
     * @param p Le panneau ModifieReservations.
     */
    private void refreshReservationCombos(ModifieReservations p) {
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++)
            p.getCBUser().addItem(Utilisateur.liste_utilisateur.get(i).getNom());
        for (int i = 0; i < Ressources.liste_ressource.size(); i++)
            p.getCBRessources().addItem(Ressources.liste_ressource.get(i).getNom());
        for (String t : typesEmpruntConnus()) p.getCBType().addItem(t);
    }

    /**
     * Remplit les combos de DeleteReservations avec les données actuelles.
     * @param p Le panneau DeleteReservations.
     */
    private void refreshReservationCombos(DeleteReservations p) {
        for (int i = 0; i < Utilisateur.liste_utilisateur.size(); i++)
            p.getCBUser().addItem(Utilisateur.liste_utilisateur.get(i).getNom());
        for (int i = 0; i < Ressources.liste_ressource.size(); i++)
            p.getCBRessources().addItem(Ressources.liste_ressource.get(i).getNom());
    }

    /**
     * Câble les boutons "Search" (Check) et "Modify" du panneau ModifieReservations.
     *
     * L'identification d'une réservation se fait par (user + ressource + jour).
     * "Search" retrouve la réservation et pré-remplit les champs modifiables.
     * "Modify" valide et enregistre les changements.
     *
     * @param p Le panneau ModifieReservations.
     */
    private void wireModifieReservations(final ModifieReservations p) {

        // Bouton "Search/Check" : identifie la réservation et charge ses données
        p.getCheckButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nomUser = (String) p.getCBUser().getSelectedItem();
                String nomRess = (String) p.getCBRessources().getSelectedItem();
                Date   date    = p.getDateChooser().getDate();

                if (nomUser == null || nomRess == null || date == null) {
                    showError("Select a user, a ressource and a date."); return;
                }

                // Recherche de la réservation par les trois critères d'identification
                Reservations res = Reservations.print_reservation(
                    Reservations.liste_reservations, nomUser, nomRess, date);

                if (res == null) {
                    showError("No reservation found for these criteria."); return;
                }

                // Formatage de l'heure pour l'afficher dans le champ texte (HH:mm)
                String heureActuelle = res.getHeure() != null
                    ? res.getHeure().format(DateTimeFormatter.ofPattern("HH:mm"))
                    : "00:00";

                // Déverrouillage et pré-remplissage des champs modifiables
                p.unlockEdit(heureActuelle, res.getDuree(), res.getType_emprunt());
            }
        });

        // Bouton "Modify" : enregistre les nouvelles valeurs sur la réservation
        p.getModifyButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nomUser = (String) p.getCBUser().getSelectedItem();
                String nomRess = (String) p.getCBRessources().getSelectedItem();
                Date   date    = p.getDateChooser().getDate();

                if (nomUser == null || nomRess == null || date == null) {
                    showError("Search criteria are incomplete."); return;
                }

                // Recherche de la réservation à modifier
                Reservations res = Reservations.print_reservation(
                    Reservations.liste_reservations, nomUser, nomRess, date);

                if (res == null) { showError("Reservation not found."); return; }

                // Parsing de la nouvelle heure
                LocalTime nouvelleHeure = parseHeure(p.getTxtHour().getText().trim());
                if (nouvelleHeure == null) {
                    showError("Invalid time format. Use HH:mm."); return;
                }

                // Parsing de la nouvelle durée
                int nouvelleDuree;
                try {
                    nouvelleDuree = Integer.parseInt(p.getTxtDuree().getText().trim());
                } catch (NumberFormatException ex) {
                    showError("Duration must be an integer (minutes)."); return;
                }

                String nouveauType = p.getCBType().getEditor().getItem().toString().trim();

                // Application des modifications sur l'objet en mémoire
                res.setHeure(nouvelleHeure);
                res.setDuree(nouvelleDuree);
                res.setType_emprunt(nouveauType.isEmpty() ? "Emprunt" : nouveauType);

                showInfo("Reservation modified successfully.");
            }
        });
    }

    /**
     * Câble le bouton "Delete" du panneau DeleteReservations.
     * @param p Le panneau DeleteReservations.
     */
    private void wireDeleteReservations(final DeleteReservations p) {
        p.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nomUser = (String) p.getCBUser().getSelectedItem();
                String nomRess = (String) p.getCBRessources().getSelectedItem();
                Date   date    = p.getDateChooser().getDate();

                if (nomUser == null || nomRess == null || date == null) {
                    showError("Select a user, a ressource and a date."); return;
                }

                Reservations res = Reservations.print_reservation(
                    Reservations.liste_reservations, nomUser, nomRess, date);

                if (res == null) { showError("Reservation not found."); return; }

                int choix = JOptionPane.showConfirmDialog(frame,
                    "Delete reservation for \"" + nomUser + "\" / \"" + nomRess + "\" ?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

                if (choix == JOptionPane.YES_OPTION) {
                    // Suppression directe de la liste (remove() avec objet, pas index)
                    Reservations.liste_reservations.remove(res);
                    showInfo("Reservation deleted.");
                }
            }
        });
    }

    /**
     * Remplit le tableau de PrintReservations avec toutes les réservations en mémoire.
     * @param p Le panneau PrintReservations.
     */
    private void wirePrintReservations(PrintReservations p) {
        String[] colonnes = {"User", "Ressource", "Date", "Time", "Duration", "Type"};
        String[][] data = new String[Reservations.liste_reservations.size()][6];
        for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
            Reservations r = Reservations.liste_reservations.get(i);
            data[i][0] = r.getUser().getNom();
            data[i][1] = r.getRessource().getNom();
            data[i][2] = r.getDate()  != null ? r.getDate().toString()  : "";
            data[i][3] = r.getHeure() != null ? r.getHeure().toString() : "";
            data[i][4] = String.valueOf(r.getDuree());
            data[i][5] = r.getType_emprunt();
        }
        p.getTable().setModel(new javax.swing.table.DefaultTableModel(data, colonnes));
    }

    // =========================================================================
    // Utilitaires
    // =========================================================================

    /**
     * Tente de parser une heure au format "HH:mm" et la retourne en LocalTime.
     *
     * On entoure le parse dans un try/catch car LocalTime.parse() lève une
     * DateTimeParseException si le format est invalide (ex : "25:00" ou "abc").
     * Dans ce cas, on retourne null et l'appelant affiche un message d'erreur.
     *
     * @param s La chaîne à parser (ex : "09:30").
     * @return Un LocalTime, ou null si la chaîne n'est pas au format HH:mm.
     */
    private LocalTime parseHeure(String s) {
        try {
            return LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            return null; // Format invalide → on signale l'échec avec null
        }
    }

    /**
     * Retourne la liste des domaines distincts présents dans Ressources.liste_ressource,
     * triée alphabétiquement.
     *
     * Utilisée pour pré-remplir les combos "Domaine" dans les panneaux CRUD.
     * On évite les doublons en vérifiant manuellement si le domaine est déjà dans la liste.
     *
     * @return Un tableau String[] des domaines connus, triés alphabétiquement.
     */
    private String[] domainesConnus() {
        ArrayList<String> list = new ArrayList<String>();

        // Parcours de toutes les ressources pour collecter les domaines uniques
        for (int i = 0; i < Ressources.liste_ressource.size(); i++) {
            String d = Ressources.liste_ressource.get(i).getDomaine();
            if (d == null || d.trim().isEmpty()) continue; // Ignore les domaines vides

            d = d.trim();
            boolean found = false;
            // Vérification que ce domaine n'est pas déjà dans la liste
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).equals(d)) { found = true; break; }
            }
            if (!found) list.add(d); // Ajout uniquement si nouveau
        }

        // Tri alphabétique par comparaison des chaînes (ordre lexicographique)
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    // Échange des deux éléments
                    String tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }

        // Conversion ArrayList → tableau String[] (requis par addItem())
        return list.toArray(new String[0]);
    }

    /**
     * Retourne les types d'emprunt distincts présents dans les réservations,
     * triés alphabétiquement. Les trois valeurs par défaut sont toujours incluses.
     *
     * @return Un tableau String[] des types connus, triés alphabétiquement.
     */
    private String[] typesEmpruntConnus() {
        ArrayList<String> list = new ArrayList<String>();

        // Les trois valeurs par défaut sont toujours présentes dans la liste
        list.add("Cours"); list.add("Emprunt"); list.add("Maintenance");

        // Ajout des types rencontrés dans les réservations (pour les types personnalisés)
        for (int i = 0; i < Reservations.liste_reservations.size(); i++) {
            String t = Reservations.liste_reservations.get(i).getType_emprunt();
            if (t == null || t.trim().isEmpty()) continue;
            t = t.trim();
            boolean found = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).equals(t)) { found = true; break; }
            }
            if (!found) list.add(t); // Ajout uniquement si nouveau
        }

        // Tri alphabétique
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    String tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * Affiche une boîte de dialogue d'erreur modale.
     * @param msg Le message d'erreur à afficher.
     */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Affiche une boîte de dialogue de succès modale.
     * @param msg Le message de confirmation à afficher.
     */
    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
