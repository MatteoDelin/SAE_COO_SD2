import java.util.ArrayList;
import java.util.Date;

public class Ressources {
	private String nom, description, domaine;
	private Date last_maj;
	static ArrayList<Ressources> liste_ressource = new ArrayList<Ressources>();
	
	public Ressources(String nom, String description, String domaine, Date last_maj) {
	    this.nom = nom;
	    this.description = description;
	    this.domaine = domaine;
	    this.last_maj = last_maj;
	    liste_ressource.add(this);
	}
	
	public String getNom()                   { return nom; }
	public void setNom(String nom)           { this.nom = nom; }
	public String getDescription()           { return description; }
	public void setDescription(String d)     { this.description = d; }
	public String getDomaine()               { return domaine; }
	public void setDomaine(String domaine)   { this.domaine = domaine; }
	public Date getLast_maj()                { return last_maj; }
	public void setLast_maj(Date last_maj)   { this.last_maj = last_maj; }

	static Ressources print_user(String nom) {
		for (Ressources e : liste_ressource)
			if (e.getNom().equals(nom)) return e;
		return null;
	}

	static void delete_user(String nom) {
		liste_ressource.removeIf(e -> e.getNom().equals(nom));
	}
}
