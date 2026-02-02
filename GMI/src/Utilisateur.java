import java.util.ArrayList;

public class Utilisateur {
	private String nom;
	static ArrayList<Utilisateur> liste_utilisateur = new ArrayList<Utilisateur>();
	
	public Utilisateur(String nom) {
	    this.nom = nom;
	    liste_utilisateur.add(this);
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	static Utilisateur print_user(String nom) {
		for (Utilisateur e:liste_utilisateur){
			if (e.getNom().equals(nom)) {
				return e;
			}
		}
		return null;
	}
	
	static void delete_user(String nom) {
		for (Utilisateur e:liste_utilisateur){
			if (e.getNom().equals(nom)) {
				liste_utilisateur.remove(e);
			}
		}
	}

}
