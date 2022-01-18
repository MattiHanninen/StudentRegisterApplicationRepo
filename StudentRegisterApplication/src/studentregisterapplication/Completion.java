package studentregisterapplication;

import java.util.ArrayList;
import java.util.Date;


/**
 * Completion.java
 * Class file
 * @author Matti Hanninen
 * @version 1.00 16.03.2021
 */
public class Completion {
    
    // Attribuutit
    /** Opiskelijan ID */
    private int opiskelija_id;
    /** Kurssin ID */
    private int kurssi_id;
    /** Kurssin arvosana */
    private int arvosana;
    /** Kurssin suorituspaiva */
    private Date suorituspaiva;
    
    
    /**
     * Oletuskonstruktori
     * Suorituksen arvot asetetaan oletusarvoihin
     */
    public Completion() {
        this.suorituspaiva = new Date();
    }

    /**
     * Konstruktori parametreilla
     * Suorituksen arvot asetetaan parametreilla
     * @param opiskID Opiskelijan ID
     * @param kursID Kurssin ID
     * @param arvos Kurssin arvosana
     * @param suorituspaiva Kurssin suorituspaiva
     */
    public Completion(int opiskID, int kursID, int arvos, Date suorituspaiva) {  
        this.opiskelija_id = opiskID;
        this.kurssi_id = kursID;
        this.arvosana = arvos;
        this.suorituspaiva = suorituspaiva;
    }
    

    /**
     * Konstruktori vajailla parametreilla metodia varten
     * @param kurssi_id Kurssin ID
     * @param arvosana Kurssin arvosana
     * @param suorituspaiva Kurssin suorituspaiva
     */
    public Completion(int kurssi_id, int arvosana, Date suorituspaiva) {
        this.kurssi_id = kurssi_id;
        this.arvosana = arvosana;
        this.suorituspaiva = suorituspaiva;        
    }
    
    
    // Setterit

    /**
     * Metodi joka asettaa opiskelijan id:n
     * @param opiskID Opiskelijan ID
     */
    public void setOpiskelijanID(int opiskID) {
        this.opiskelija_id = opiskID;
    }
    /**
     * Metodi joka asettaa kurssin id:n
     * @param kursID Kurssin ID
     */
    public void setKurssinID(int kursID) {
        this.kurssi_id = kursID;
    }
    /**
     * Metodi joka asettaa kurssin arvosanan
     * @param arvos Kurssin arvosana
     */
    public void setArvosana(int arvos) {
        this.arvosana = arvos;
    }
    
    
    // Getterit

    /**
     * Metodi joka palauttaa opiskelijan id:n
     * @return Opiskelijan ID
     */
    public int getOpiskelijaID() {
        return opiskelija_id;
    }
    /**
     * Metodi joka palauttaa kurssin id:n
     * @return Kurssin ID
     */
    public int getKurssinID() {
        return kurssi_id;
    }
    /**
     * Metodi joka palauttaa kurssin arvosanan
     * @return Kurssin arvosana
     */
    public int getArvosana() {
        return arvosana;
    }
    /**
     * Metodi joka palauttaa suorituksen paivamaaran
     * @return Suorituksen paivamaara
     */
    public Date getDate() {
        return suorituspaiva;
    }
    
}
