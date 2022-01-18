package studentregisterapplication;

import java.util.ArrayList;

/**
 * Course.java
 * Class file
 * @author Matti Hanninen
 * @version 1.00 16.03.2021 
 */

public class Course {
    
    // Attribuutit
    /** Kurssin ID*/
    private int kurssi_id;
    /** Kurssin nimi */
    private String kurssinNimi;
    /** Kurssin opintopisteet */
    private int opintopisteet;
    /** Kurssin kuvaus */
    private String kuvaus;
    
    /* ArrayList kurssin opiskelijoista */
    ArrayList<Course> kurssit = new ArrayList<>();
    
    /**
     * Oletuskonstruktori
     * Kurssin arvot asetetaan oletusarvoihin
     */
    public Course() {
    }

    /**
     * Konstruktori parametreilla
     * Kurssin arvot asetetaan parametreilla
     * @param id Kurssin ID
     * @param nimi Kurssin nimi
     * @param pisteet Kurssin opintopisteet
     * @param kurssinKuv Kurssin kuvaus
     */
    public Course(int id, String nimi, int pisteet, String kurssinKuv) {
        this.kurssi_id = id;
        this.kurssinNimi = nimi;
        this.opintopisteet = pisteet;
        this.kuvaus = kurssinKuv;
    }
    
    /**
     * Konstruktori vajailla parametreilla metodia varten
     * @param id Kurssin ID
     * @param nimi Kurssin nimi
     * @param pisteet Kurssin opintopisteet
     */
    public Course(int id, String nimi, int pisteet) {
        this.kurssi_id = id;
        this.kurssinNimi = nimi;
        this.opintopisteet = pisteet;
    }
    
    
    // Setterit

    /**
     * Metodi joka asettaa kurssin id:n
     * @param id Kurssin ID
     */
    public void setKurssinID(int id) {
        this.kurssi_id = id;
    }
    /**
     * Metodi joka asettaa kurssin nimen
     * @param nimi Kurssin nimi
     */
    public void setKurssinNimi(String nimi) {
        this.kurssinNimi = nimi;
    }
    /**
     * Metodi joka asettaa kurssin opintopisteet
     * @param pisteet Kurssin opintopisteet
     */
    public void setOpintopisteet(int pisteet) {
        this.opintopisteet = pisteet;
    }
    /**
     * Metodi joka asettaa kurssin kuvaukset
     * @param kurssinKuv Kurssin kuvaus
     */
    public void setKuvaus(String kurssinKuv) {
        this.kuvaus = kurssinKuv;
    }
    
    
    // Getterit

    /**
     * Metodi joka palauttaa kurssin id:n
     * @return Kurssin ID
     */
    public int getKurssinID() {
        return kurssi_id;
    }
    /**
     * Metodi joka palauttaa kurssin nimen
     * @return Kurssin nimi
     */
    public String getKurssinNimi() {
        return kurssinNimi;
    }
    /**
     * Metodi joka palauttaa kurssin opintopisteet
     * @return Kurssin opintopisteet
     */
    public int getOpintopisteet() {
        return opintopisteet;
    }
    /**
     * Metodi joka palauttaa kurssin kuvauksen
     * @return Kurssin kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }
    
}
