package model;

import java.util.ArrayList;

/**
 * Représente un utilisateur du système GMI (Gestion de Matériel Informatique).
 *
 * Chaque utilisateur possède un nom unique qui sert d'identifiant dans toute
 * l'application. La liste statique {@code liste_utilisateur} joue le rôle de
 * base de données en mémoire : toutes les classes de l'application accèdent
 * aux utilisateurs via cette liste sans passer par un fichier ou une BDD.
 *
 * Quand un objet Utilisateur est créé avec {@code new Utilisateur(nom)},
 * il s'ajoute automatiquement à la liste. On n'a donc jamais besoin d'appeler
 * {@code liste_utilisateur.add(...)} manuellement.
 */
public class Utilisateur {

    // =========================================================================
    // Attributs
    // =========================================================================

    /**
     * Nom de l'utilisateur.
     * Sert d'identifiant unique : deux utilisateurs ne peuvent pas avoir
     * le même nom (cette contrainte est vérifiée dans MainWindow avant création).
     */
    private String nom;

    /**
     * Liste statique partagée de tous les utilisateurs de l'application.
     *
     * "Statique" signifie qu'elle appartient à la classe, pas à un objet.
     * Il n'existe donc qu'une seule liste pour toute l'application,
     * accessible depuis n'importe quelle classe via {@code Utilisateur.liste_utilisateur}.
     *
     * Elle est initialisée une seule fois au démarrage (vide),
     * puis remplie au fil des créations et des imports CSV.
     */
    public static ArrayList<Utilisateur> liste_utilisateur = new ArrayList<Utilisateur>();

    // =========================================================================
    // Constructeur
    // =========================================================================

    /**
     * Crée un nouvel utilisateur et l'ajoute automatiquement à la liste globale.
     *
     * Le fait d'appeler {@code liste_utilisateur.add(this)} dans le constructeur
     * garantit que tout objet Utilisateur créé dans l'application est
     * immédiatement référencé dans la liste centrale.
     *
     * @param nom Nom de l'utilisateur (doit être unique dans l'application).
     */
    public Utilisateur(String nom) {
        this.nom = nom;                    // On stocke le nom dans l'objet
        liste_utilisateur.add(this);       // On s'ajoute à la liste globale
    }

    // =========================================================================
    // Getters et Setters
    // =========================================================================

    /**
     * Retourne le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de l'utilisateur.
     * Utilisé par le panneau "Modify User" de l'interface.
     * @param nom Le nouveau nom à attribuer.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    // =========================================================================
    // Méthodes statiques utilitaires
    // =========================================================================

    /**
     * Recherche et retourne un utilisateur dans la liste globale par son nom.
     *
     * On parcourt la liste un élément à la fois.
     * Dès qu'on trouve un utilisateur dont le nom correspond exactement,
     * on le retourne. Si on arrive à la fin sans trouver, on retourne null.
     *
     * Cette méthode est utilisée dans MainWindow pour vérifier si un
     * utilisateur existe avant de le créer, le modifier ou le supprimer.
     *
     * @param nom Le nom de l'utilisateur à rechercher (sensible à la casse).
     * @return L'objet Utilisateur correspondant, ou null s'il n'existe pas.
     */
    public static Utilisateur print_user(String nom) {

        // On parcourt toute la liste un élément à la fois
        for (int i = 0; i < liste_utilisateur.size(); i++) {

            // On compare le nom de l'utilisateur en position i avec le nom cherché
            if (liste_utilisateur.get(i).getNom().equals(nom)) {

                // Trouvé : on retourne l'objet directement
                return liste_utilisateur.get(i);
            }
        }

        // On a parcouru toute la liste sans trouver : on retourne null
        return null;
    }

    /**
     * Supprime de la liste globale l'utilisateur dont le nom correspond.
     *
     * On utilise un index {@code i} plutôt qu'un for-each car on modifie
     * la liste pendant la boucle. Quand on supprime l'élément à la position i,
     * tous les éléments suivants reculent d'une case. On fait donc {@code i--}
     * pour ne pas "sauter" l'élément qui vient de prendre la place de celui
     * qu'on vient de supprimer.
     *
     * Dans la pratique, les noms étant uniques, la boucle ne supprimera
     * qu'un seul élément, mais le mécanisme i-- reste une bonne habitude.
     *
     * @param nom Le nom de l'utilisateur à supprimer.
     */
    public static void delete_user(String nom) {

        // On parcourt la liste avec un index entier (pas de for-each)
        // car on va modifier la liste pendant la boucle
        for (int i = 0; i < liste_utilisateur.size(); i++) {

            // Si le nom correspond, on supprime cet élément
            if (liste_utilisateur.get(i).getNom().equals(nom)) {

                liste_utilisateur.remove(i); // Suppression à la position i

                // Après remove(i), l'élément suivant est maintenant à la position i.
                // On décrémente i pour le traiter à la prochaine itération
                // et éviter de le "sauter" par erreur.
                i--;
            }
        }
    }
}
