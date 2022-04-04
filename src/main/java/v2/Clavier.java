package v2;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Clavier {
    int T_MAX = 10;
    int nbLignesMax = 4;
    int nbBoutonsMax = 3;
    int nbBoutons = 0;
    String nomMenu;
    String[] libelles = new String[T_MAX];
    String[][] libelles3x3 = new String[nbLignesMax][nbBoutonsMax];

    Clavier(String []libelles, String nomMenu) {
        System.arraycopy(libelles, 0, this.libelles, 0, T_MAX);
        this.nomMenu = nomMenu;
    }

    Clavier(String [][]libelles3x3) {
        this.libelles3x3 = libelles3x3.clone();
    }

    boolean ligneEstComplet(){
        return getNbBoutonsClavierPreFait() == 3;
    }

    String getLibelle(int numero) {
        return libelles[numero];
    }

    String getLibelle3x3(int ligne, int position){
        return libelles3x3[ligne][position];
    }

    String getNomMenu(){
        return nomMenu;
    }

    int getNbBoutons(){
        return nbBoutons;
    }

    int getNbBoutonsClavierPreFait(){
        boolean estTroisiemePlace;
        boolean estDeuxiemePlace;
        boolean estPremierePlace;
        estPremierePlace = getNbBoutons() == 1 || getNbBoutons() == 4 || getNbBoutons() == 7 || getNbBoutons() == 10;
        estDeuxiemePlace = getNbBoutons() == 2 || getNbBoutons() == 5 || getNbBoutons() == 8 || getNbBoutons() == 11;
        estTroisiemePlace = getNbBoutons() == 3 || getNbBoutons() == 6 || getNbBoutons() == 9 || getNbBoutons() == 12;
        if (estTroisiemePlace){
            return 3;
        } else {
            if (estDeuxiemePlace){
                return 2;
            } else {
                if (estPremierePlace){
                    return 1;
                }
            }
        }
        return 0;
    }

    int getNbLignesClavierPreFait(){
        if (getNbBoutons() <= 12 && getNbBoutons() >= 10){
            return 4;
        } else {
            if (getNbBoutons() <= 9 && getNbBoutons() >= 7){
                return 3;
            } else {
                if (getNbBoutons() <= 6 && getNbBoutons() >= 4){
                    return 2;
                } else {
                    if (getNbBoutons() <= 3 && getNbBoutons() >= 1){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    int getNbLignesClavierPreFaitComplet(){
        int nbLignesComplette;

        if (ligneEstComplet()){
            return getNbLignesClavierPreFait();

        } else {
            nbLignesComplette = getNbLignesClavierPreFait() - 1;
            return nbLignesComplette;
        }
    }

    void setNbBoutons(){
        nbBoutons = 0;
        for (String elem : libelles){
            //System.out.println(elem);
            if (elem != null){
                nbBoutons += 1;
            }
        }
    }

    void setNbBoutonsClaviersPreFait(){
        nbBoutons = 0;
        for (String[] elem : libelles3x3){
            for (int i = 0; i < 3; i++){
                //System.out.println(elem[i]);
                if (elem[i] != null){
                    nbBoutons += 1;
                    //System.out.println("nb boutons = " + getNbBoutons());
                }
            }
        }
    }

    SendMessage affichageClavier(Update update) {
        setNbBoutons();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(getNomMenu());

        for (int i = 0; i < getNbBoutons(); i++){
            row.add(getLibelle(i));
        }

        row.add("/start");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    SendMessage affichageClavierPreDefini(Update update){
        setNbBoutonsClaviersPreFait();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        //KeyboardRow row = new KeyboardRow();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Menu de: ");
        KeyboardRow [] lignes = new KeyboardRow[4];
        for (int i = 0; i < 4; i++){
            lignes[i] = new KeyboardRow();
        }

        if (ligneEstComplet()){
            for (int i = 0; i < getNbLignesClavierPreFait(); i++){
                for (int j = 0; j < 3; j++){
                    lignes[i].add(getLibelle3x3(i,j));
                }
            }
        } else {
            for (int i = 0; i < getNbLignesClavierPreFaitComplet(); i++){
                for (int j = 0; j < 3; j++){
                    lignes[i].add(getLibelle3x3(i,j));
                }
            }
            for (int e = 0; e < getNbBoutonsClavierPreFait(); e++){
                int derniereLigne = getNbLignesClavierPreFait() -1;
                lignes[derniereLigne].add(getLibelle3x3(derniereLigne,e));
            }
        }

        int i = 0;
        while (i < getNbLignesClavierPreFait()) {
            keyboard.add(lignes[i]);
            i++;
        }
        /*
        //row.add("/start");
        //keyboard.add(row);
         */
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}

