/*
import java.io.*;
public class SauvegarderData {
    double emplacement = afficher(String message, File file, double getEmplacement);

    public void ecrire(String message){
        File file = new File("C://Users//ludov/OneDrive/Code/java/sauvegardeDesDonnes/doc2.txt");
        //écrire dans un fichier
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileWriter writer = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(message);
                bw.newLine();
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public double afficher(String message, File file, double emplacement) {

        double test = Double.parseDouble(message);
        System.out.println(test);
        double[] monTableau;
        monTableau = new double[10];
        monTableau[0] = test;
        System.out.println(monTableau[0]);

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                double indice = 0;
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = reader.readLine();


                while (line != null) {
                    line = reader.readLine();
                    double test2 = Double.parseDouble(line);
                    monTableau[emplacement] = test2;
                    System.out.println(indice + " :c'est l'indice de test2" + " et ça sa valeur " + test2);
                    indice++;

                }
                reader.close();
                for (double tableau : monTableau) {
                    System.out.println(tableau);
                }
                return indice;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("fin");
        return monTableau[0];
    }
}
*/