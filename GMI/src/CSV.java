import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSV {

    public void chargement(String chemin) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                String[] donnees = ligne.split(";");

                for (String champ : donnees) {
                    System.out.print(champ + " | ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier CSV : " + e.getMessage());
        }
    }

    public void export(String chemin) {
        try (FileWriter writer = new FileWriter(chemin)) {

            writer.write("nom;prenom;age\n");
            writer.write("Alice;Dupont;22\n");
            writer.write("Bob;Martin;30\n");

            System.out.println("Export CSV réussi.");

        } catch (IOException e) {
            System.out.println("Erreur lors de l'export du fichier CSV : " + e.getMessage());
        }
    }
}