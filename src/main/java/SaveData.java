import java.io.*;

public class SaveData {
    public boolean estAchat = false;
    private boolean estVendue = false;
    private boolean estCredit = false;


    public boolean getEstAchat(){
        return estAchat;
    }
    public boolean getEstVendue(){
        return estVendue;
    }
    public boolean getEstCredit(){
        return estCredit;
    }
    public String setCheminAccesFichier(){
        if (estAchat) {
            return "C://Users/ludov/OneDrive/Code/java/sauvegardeDesDonnes/doc_" + MyAmazingBot.premierUserFirstName + "_achat" + ".txt";
        }
        if (estVendue) {
            return "C://Users/ludov/OneDrive/Code/java/sauvegardeDesDonnes/doc_" + MyAmazingBot.premierUserFirstName + "_vente" + ".txt";
        }
        if (estCredit) {
            return "C://Users/ludov/OneDrive/Code/java/sauvegardeDesDonnes/doc_" + MyAmazingBot.premierUserFirstName + "_credit" + ".txt";
        }
    return "pas de fichier";
    }
    public void setEstAchat(boolean estAchatParam){
        estAchat = estAchatParam;
    }
    public void setEstVendue(boolean estVendueParam){
        estVendue = estVendueParam;
    }
    public void setEstCredit(boolean estCreditParam){
        estCredit = estCreditParam;
    }


    public double save(String message) {
        if (message.equals("achat")){
            System.out.println("On est en achat");
            setEstAchat(true);
            setEstVendue(false);
            setEstCredit(false);
            double varDouble = 0;
            String a = "" + varDouble;
            message = a;
        }
        if (message.equals("vente")){
            System.out.println("On est en vente");
            setEstAchat(false);
            setEstVendue(true);
            setEstCredit(false);
            double varDouble = 0;
            String b = "" + varDouble;
            message = b;

        }
        if (message.equals("credit")){
            System.out.println("On est en credit");
            setEstAchat(false);
            setEstVendue(false);
            setEstCredit(true);
            double varDouble = 0;
            String c = "" + varDouble;
            message = c;
        }

        if (getEstAchat()){
            System.out.println(getEstAchat() + " fuck ");

            File file = new File(setCheminAccesFichier());

            // si on tape avant achat ça définit un booléen sur true et enregistre dnas un autre fichier les données

            double test = Double.parseDouble(message);
            System.out.println(test);
            double [] monTableau;
            monTableau = new double[10];
            monTableau[0] = test;
            System.out.println(monTableau[0]);

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
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    int indice = 0;
                    double somme = 0;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String line = reader.readLine();


                    while (line != null) {
                        double test5 = Double.parseDouble(line);
                        monTableau[indice] = test5;
                        System.out.println(line + " c'est ici ");
                        line = reader.readLine();
                        System.out.println(indice);
                        indice++;

                    }
                    reader.close();
                    for (double tableau : monTableau) {
                        somme += tableau;
                        System.out.println(tableau);
                    }
                    System.out.println(somme);
                    return somme;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("fin");
        } else {
            if (getEstVendue()){
                System.out.println(getEstVendue());
                File file = new File(setCheminAccesFichier());

                double test2 = Double.parseDouble(message);
                System.out.println(test2);
                double [] monTableau2;
                monTableau2 = new double[10];
                monTableau2[0] = test2;
                System.out.println(monTableau2[0]+ " tableau 2 ");

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
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        int indice = 0;
                        double somme = 0;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line = reader.readLine();


                        while (line != null) {
                            double test5 = Double.parseDouble(line);
                            monTableau2[indice] = test5;
                            System.out.println(line + " c'est ici ");
                            line = reader.readLine();
                            System.out.println(indice);
                            indice++;

                        }
                        reader.close();
                        for (double tableau : monTableau2) {
                            somme += tableau;
                            System.out.println(tableau);
                        }
                        System.out.println(somme);
                        return somme;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("fin2");



            } else {
                if (getEstCredit()){
                    System.out.println(getEstCredit());
                    File file = new File(setCheminAccesFichier());

                    double test3 = Double.parseDouble(message);
                    System.out.println(test3);
                    double [] monTableau3;
                    monTableau3 = new double[10];
                    monTableau3[0] = test3;
                    System.out.println(monTableau3[0]+ " tableau 3 ");

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
                    if (!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            int indice = 0;
                            double somme = 0;
                            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                            String line = reader.readLine();


                            while (line != null) {
                                double test5 = Double.parseDouble(line);
                                monTableau3[indice] = test5;
                                System.out.println(line + " c'est ici ");
                                line = reader.readLine();
                                System.out.println(indice);
                                indice++;

                            }
                            reader.close();
                            for (double tableau : monTableau3) {
                                somme += tableau;
                                System.out.println(tableau);
                            }
                            System.out.println(somme);
                            return  somme;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("fin3");

                } else {
                    System.out.println("erreur");
                }
            }
        }
        return 1;
    }
}
