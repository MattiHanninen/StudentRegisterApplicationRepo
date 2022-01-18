package studentregisterapplication;

/**
 * Student.java
 * Class file
 * @author Matti Hanninen
 * @version 1.00 16.03.2021 
 */

public class Student {
    
    // Attribuutit
    /** Opiskelijan ID */
    private int opiskelija_id;
    /** Opiskelijan etunimi */
    private String etunimi;
    /** Opiskelijan sukunimi */
    private String sukunimi;
    /** Opiskelijan lahiosoite */
    private String lahiosoite;
    /** Opiskelijan postitoimipaikka */
    private String postitoimipaikka;
    /** Opiskelijan postinumero */
    private String postinumero;
    /** Opiskelijan sahkopostiosoite */
    private String email;
    /** Opiskelijan puhelinnumero */
    private String puhelinnumero;
     
    /**
     * Oletuskonstruktori
     * Opiskelijan arvot asetetaan oletusarvoihin
     */
    public Student() {
    }

    /**
     * Konstruktori parametreilla
     * Opiskelijan arvot asetetaan parametreilla
     * @param id Opiskelijan id
     * @param enimi Opiskelijan etunimi
     * @param snimi Opiskelijan sukunimi
     * @param lahios Opiskelijan lahiosoite
     * @param postitp Opiskelijan postitoimipaikka
     * @param postinum Opiskelijan postinumero
     * @param mail Opiskelijan sahkopostiosoite
     * @param puhnum Opiskelijan puhelinnumero
     */
    public Student(int id, String enimi, String snimi, String lahios, String postitp, String postinum, String mail, String puhnum) {
        this.opiskelija_id = id;
        this.etunimi = enimi;
        this.sukunimi = snimi;
        this.lahiosoite = lahios;
        this.postitoimipaikka = postitp;
        this.postinumero = postinum;
        this.email = mail;
        this.puhelinnumero = puhnum;
    }
    
    /**
     * Konstruktori vajailla parametreilla metodia varten
     * @param id Opiskelijan ID
     * @param snimi Opiskelijan sukunimi
     */
    public Student(int id, String snimi) {
        this.opiskelija_id = id;
        this.sukunimi = snimi;
    }
    
    // Setterit

    /**
     * Metodi joka asettaa opiskelijan id:n
     * @param id Opiskelijan id
     */
    public void setOpiskelijaID (int id) {
		this.opiskelija_id = id;
    }
    /**
     * Metodi joka asettaa opiskelijan etunimen
     * @param enimi Opiskelijan etunimi
     */
	public void setEtunimi (String enimi) {
		this.etunimi = enimi;
    }
    /**
     * Metodi joka asettaa opiskelijan sukunimen
     * @param snimi Opiskelijan sukunimi
     */
	public void setSukunimi (String snimi) {
		this.sukunimi = snimi;
    }
    /**
     * Metodi joka asettaa opiskelijan lahiosoitteen
     * @param lahios Opiskelijan lahiosoite
     */
	public void setLahiosoite (String lahios) {
		this.lahiosoite = lahios;
    }
    /**
     * Metodi joka asettaa opiskelijan postitoimipaikan
     * @param postitp Opiskelijan postitoimipaikka
     */
	public void setPostitoimipaikka (String postitp) {
		this.postitoimipaikka = postitp;
    }
    /**
     * Metodi joka asettaa opiskelijan postinumeron
     * @param postinum Opiskelijan postinumero
     */
	public void setPostinro (String postinum) {
		this.postinumero = postinum;
    }
    /**
     * Metodi joka asettaa opiskelijan sahkopostiosoitteen
     * @param mail Opiskelijan sahkopostiosoite
     */
	public void setEmail (String mail) {
		this.email = mail;
    }
    /**
     * Metodi joka asettaa opiskelijan puhelinnumeron
     * @param puhnum Opiskelijan puhelinnumero
     */
	public void setPuhelinnro (String puhnum) {
        this.puhelinnumero = puhnum;
    }
        
        
    // Getterit

    /**
     * Metodi joka palauttaa opiskelijan id:n
     * @return Opiskelijan id
     */
    public int getOpiskelijaID() {
		return opiskelija_id;
    }
    /**
     * Metodi joka palauttaa opiskelijan etunimen
     * @return Opiskelijan etunimi
     */
	public String getEtunimi() {
		return etunimi;
    }
    /**
     * Metodi joka palauttaa opiskelijan sukunimen
     * @return Opiskelijan sukunimi
     */
	public String getSukunimi() {
		return sukunimi;
    }
    /**
     * Metodi joka palauttaa opiskelijan lahiosoitteen
     * @return Opiskelijan lahiosoite
     */
	public String getLahiosoite() {
		return lahiosoite;
    }
    /**
     * Metodi joka palauttaa opiskelijan postitoimipaikan
     * @return Opiskelijan postitoimipaikka
     */
	public String getPostitoimipaikka() {
		return postitoimipaikka;
    }
    /**
     * Metodi joka palauttaa opiskelijan postinumeron
     * @return Opiskelijan postinumero
     */
	public String getPostinro() {
		return postinumero;
    }
    /**
     * Metodi joka palauttaa opiskelijan sahkopostiosoitteen
     * @return Opiskelijan sahkopostiosoite
     */
	public String getEmail() {
		return email;
    }
    /**
     * Metodi joka palauttaa opiskelijan puhelinnumeron
     * @return Opiskelijan puhelinnumero
     */
	public String getPuhelinnro() {
        return puhelinnumero;
    }  
    
}
