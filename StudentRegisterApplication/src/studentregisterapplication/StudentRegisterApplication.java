package studentregisterapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * StudentRegisterApplication.java Main program
 * @author Matti Hanninen
 * @version 1.00 16.03.2021
 */
public class StudentRegisterApplication extends Application {

    /*
    * Metodi joka avaa tietokantayhteyden
    */    
    public static Connection openConnection(String connString) throws SQLException {
        Connection con = DriverManager.getConnection(connString);
        System.out.println("\t>> Yhteys ok");
        return con;
    }

    /**
     * Metodi joka sulkee tietokantayhteyden
     * @param c Yhteys
     * @throws java.sql.SQLException SQL poikkeus
     */
    public static void closeConnection(Connection c) throws SQLException {
        if (c != null) {
            c.close();
        }
        System.out.println("\t>> Tietokantayhteys suljettu");
    }

   

    /**
     * Metodi joka poistaa tietokannan, jos se on olemassa, jonka jalkeen luo uuden
     * @param c Yhteys
     * @param db Tietokanta
     * @throws SQLException SQL poikkeus
     */

    public static void createDatabase(Connection c, String db) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery("DROP DATABASE IF EXISTS " + db);
        System.out.println("\t>> Tietokanta " + db + " tuhottu");

        stmt.executeQuery("CREATE DATABASE " + db);
        System.out.println("\t>> Tietokanta " + db + " luotu");

        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Kaytetaan tietokantaa " + db);

    }

    /**
     * Metodi joka luo uuden taulun
     * @param c Yhteys
     * @param sql SQL lause
     * @throws SQLException SQL poikkeus
     */
    public static void createTable(Connection c, String sql) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeQuery(sql);
        System.out.println("\t>> Taulu luotu");
    }

    /**
     * Metodi joka asettaa tietokannan valituksi
     * @param c Yhteys
     * @param db Tietokanta
     * @throws SQLException SQL poikkeus
     */
    public static void useDatabase(Connection c, String db) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeQuery("USE " + db);
        System.out.println("\t>> Käytetään tietokantaa " + db);
    }


    /**
     * Metodi joka luo uuden opiskelijan tietokantaan
     * @param c Yhteys
     * @param opiskelija_id Opiskelijan ID
     * @param etunimi Opiskelijan etunimi
     * @param sukunimi Opiskelijan sukunimi
     * @param lahiosoite Opiskelijan lahiosoite
     * @param postitoimipaikka Opiskelijan postitoimipaikka
     * @param postinumero Opiskelijan postinumero
     * @param email Opiskelijan sahkoposti
     * @param puhelinnumero Opiskelijan puhelinnumero
     * @throws SQLException SQL poikkeus
     */
    public static void addNewStudent(Connection c, int opiskelija_id, String etunimi, String sukunimi, String lahiosoite, String postitoimipaikka,
            String postinumero, String email, String puhelinnumero) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO Opiskelija(opiskelija_id, etunimi, sukunimi, lahiosoite, postitoimipaikka, postinumero, email, puhelinnumero) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
        );

        ps.setInt(1, opiskelija_id);
        ps.setString(2, etunimi);
        ps.setString(3, sukunimi);
        ps.setString(4, lahiosoite);
        ps.setString(5, postitoimipaikka);
        ps.setString(6, postinumero);
        ps.setString(7, email);
        ps.setString(8, puhelinnumero);
        ps.execute();

        System.out.println("\t>> Lisatty\n" + opiskelija_id + "\n" + etunimi + "\n" + sukunimi + "\n" + lahiosoite + "\n" + postitoimipaikka + "\n" + postinumero + "\n" + email + "\n" + puhelinnumero);
    }


    /**
     * Metodi joka lisaa uuden kurssin tietokantaan
     * @param c Yhteys
     * @param kurssi_id Kurssin ID
     * @param kurssinNimi Kurssin nimi
     * @param opintopisteet Kurssin opintopisteet
     * @param kuvaus Kurssin kuvaus
     * @throws SQLException SQL poikkeus
     */
    public static void addNewCourse(Connection c, int kurssi_id, String kurssinNimi, int opintopisteet, String kuvaus) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO Kurssi (kurssi_id, kurssinNimi, opintopisteet, kuvaus) "
                + "VALUES (?, ?, ?, ?) "
        );

        ps.setInt(1, kurssi_id);
        ps.setString(2, kurssinNimi);
        ps.setInt(3, opintopisteet);
        ps.setString(4, kuvaus);
        ps.execute();

        System.out.println("\t>> Lisatty\n" + kurssi_id + "\n" + kurssinNimi + "\n" + opintopisteet + "\n" + kuvaus);
    }

    /**
     * Metodi joka lisaa uuden suorituksen tietokantaan
     * @param c Yhteys
     * @param opiskelija_id Opiskelijan ID
     * @param kurssi_id Kurssin ID
     * @param arvosana Kurssin arvosana
     * @param suorituspaiva Kurssin suorituspaiva
     * @throws SQLException SQL poikkeus
     */
    public static void addNewCompletion(Connection c, int opiskelija_id, int kurssi_id, int arvosana, String suorituspaiva) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO Suoritus (opiskelija_id, kurssi_id, arvosana, suorituspaiva) "
                + "VALUES (?, ?, ?, STR_TO_DATE(?, '%d.%m.%Y'))"
        );

        ps.setInt(1, opiskelija_id);
        ps.setInt(2, kurssi_id);
        ps.setInt(3, arvosana);
        ps.setString(4, suorituspaiva);
        ps.execute();

        System.out.println("\t>> Lisatty\n" + opiskelija_id + "\n" + kurssi_id + "\n" + arvosana + "\n" + suorituspaiva);
    }

 
    /**
     * Metodi joka palauttaa opiskelijan tiedot tietokannasta
     * @param c Yhteys
     * @return ResultSet opiskelijan tiedoista
     * @throws SQLException SQL poikkeus
     */
    public static ResultSet selectStudentInfo(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT opiskelija_id, etunimi, sukunimi, lahiosoite, postitoimipaikka,"
                + "postinumero, email, puhelinnumero FROM Opiskelija ORDER BY opiskelija_id");

        return rs;
    }



    /**
     * Metodi joka palauttaa kurssin tiedot tietokannasta
     * @param c Yhteys
     * @return ResultSet kurssin tiedoista
     * @throws SQLException SQL poikkeus
     */
    public static ResultSet selectCourseInfo(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT kurssi_id, kurssinNimi, opintopisteet, kuvaus FROM Kurssi ORDER BY kurssi_id");

        return rs;
    }

    /**
     * Metodi joka palauttaa suorituksen tiedot tietokannasta
     * @param c Yhteys
     * @return ResultSet suorituksen tiedoista
     * @throws SQLException SQL poikkeus
     */
    public static ResultSet selectCompletionInfo(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT opiskelija_id, kurssi_id, arvosana, suorituspaiva FROM Suoritus ORDER BY kurssi_id");

        return rs;
    }


    /**
     * Metodi joka palauttaa kurssien ja suoritusten tiedot tietokannasta
     * @param c Yhteys
     * @return ResultSet kurssin ja suorituksen tiedoista
     * @throws SQLException SQL poikkeus
     */
    public static ResultSet selectCourseAndCompletionInfo(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT Kurssi.kurssi_id, Kurssi.kurssinNimi, Kurssi.opintopisteet, Kurssi.kuvaus, Suoritus.opiskelija_id, Suoritus.kurssi_id, Suoritus.arvosana, Suoritus.suorituspaiva "
                + "FROM Kurssi "
                + "INNER JOIN Suoritus "
                + "ON Kurssi.kurssi_id = Suoritus.kurssi_id ");

        return rs;
    }
  

    /**
     * Metodi joka paivittaa olemassa olevan opiskelijan tiedot tietokantaan
     * @param c Yhteys
     * @param etunimi Opiskelijan etunimi
     * @param sukunimi Opiskelijan sukunimi
     * @param lahiosoite Opiskelijan lahiosoite
     * @param postitoimipaikka Opiskelijan postitoimipaikka
     * @param postinumero Opiskelijan postinumero
     * @param email Opiskelijan sahkoposti
     * @param puhelinnumero Opiskelijan puhelinnumero
     * @param opiskelija_id Opiskelijan ID
     * @throws SQLException SQL poikkeus
     */
    public static void updateStudentInfo(Connection c, String etunimi, String sukunimi, String lahiosoite, String postitoimipaikka,
            String postinumero, String email, String puhelinnumero, int opiskelija_id) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "UPDATE Opiskelija SET etunimi = ?, sukunimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinumero = ?, email = ?, puhelinnumero = ? WHERE opiskelija_id = ? "
        );

        ps.setString(1, etunimi);
        ps.setString(2, sukunimi);
        ps.setString(3, lahiosoite);
        ps.setString(4, postitoimipaikka);
        ps.setString(5, postinumero);
        ps.setString(6, email);
        ps.setString(7, puhelinnumero);
        ps.setInt(8, opiskelija_id);
        ps.execute();

        System.out.println("\t>> Paivitetty\n" + "\n" + etunimi + "\n" + sukunimi + "\n" + lahiosoite + "\n" + postitoimipaikka + "\n" + postinumero + "\n" + email + "\n" + puhelinnumero);
    }

   
    /**
     * Metodi joka paivittaa olemassa olevan kurssin tiedot tietokantaan
     * @param c Yhteys
     * @param kurssinNimi Kurssin nimi
     * @param opintopisteet Kurssin opintopisteet
     * @param kuvaus Kurssin kuvaus
     * @param kurssi_id Kurssin ID
     * @throws SQLException SQL poikkeus
     */
    public static void updateCourseInfo(Connection c, String kurssinNimi, int opintopisteet, String kuvaus, int kurssi_id) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "UPDATE Kurssi SET kurssinNimi = ?, opintopisteet = ?, kuvaus = ? WHERE kurssi_id = ? "
        );

        ps.setString(1, kurssinNimi);
        ps.setInt(2, opintopisteet);
        ps.setString(3, kuvaus);
        ps.setInt(4, kurssi_id);
        ps.execute();

        System.out.println("\t>> Paivitetty\n" + "\n" + kurssinNimi + "\n" + opintopisteet + "\n" + kuvaus);
    }
    
    /**
     * Metodi joka paivittaa olemassa olevan suorituksen tiedot
     * @param c Yhteys
     * @param arvosana Kurssin arvosana
     * @param suorituspaiva Kurssin suorituspaiva
     * @param opiskelija_id Opiskelijan ID
     * @param kurssi_id Kurssin ID
     * @throws SQLException SQL poikkeus
     */
    public static void updateCompletionInfo(Connection c, int arvosana, String suorituspaiva, int opiskelija_id, int kurssi_id) throws SQLException {

        PreparedStatement ps = c.prepareStatement(
                "UPDATE Suoritus SET arvosana = ?, suorituspaiva = ? WHERE opiskelija_id = ? AND kurssi_id = ? "
        );

        ps.setInt(1, arvosana);
        ps.setString(2, suorituspaiva);
        ps.setInt(3, opiskelija_id);
        ps.setInt(4, kurssi_id);
        ps.execute();

        System.out.println("\t>> Paivitetty\n" + "\n" + opiskelija_id + "\n" + arvosana + "\n" + suorituspaiva);
    }
   

    /**
     * Ohjelman Start metodi
     * @param stage Paavalikko
     * @throws java.lang.Exception Poikkeus
     */

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StudentRegisterApplicationView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Paavalikko");
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException SQL poikkeus
     */
    public static void main(String[] args) throws SQLException {
        //launch(args);

        // Luodaan Connection String olemassa olevaan tietokantaan
        Connection conn = openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");

        // Luodaan tietokanta
        createDatabase(conn, "karelia_mattihan");

        // Otetaan tietokanta kayttoon
        StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

        // Luodaan Opiskelija taulu
        StudentRegisterApplication.createTable(conn,
                "CREATE TABLE Opiskelija ("
                + "opiskelija_id INT NOT NULL,"
                + "etunimi VARCHAR(20),"
                + "sukunimi VARCHAR(30),"
                + "lahiosoite VARCHAR(30),"
                + "postitoimipaikka VARCHAR(30),"
                + "postinumero VARCHAR(30),"
                + "email VARCHAR(40),"
                + "puhelinnumero VARCHAR(20),"
                + "PRIMARY KEY (opiskelija_id))"
        );

        // Luodaan Kurssi taulu
        StudentRegisterApplication.createTable(conn,
                "CREATE TABLE Kurssi ("
                + "kurssi_id INT NOT NULL,"
                + "kurssinNimi VARCHAR(30),"
                + "opintopisteet INT NOT NULL,"
                + "kuvaus VARCHAR(70),"
                + "PRIMARY KEY (kurssi_id))"
        );

        // Luodaan Suoritus taulu
        StudentRegisterApplication.createTable(conn,
                "CREATE TABLE Suoritus ("
                + "opiskelija_id INT NOT NULL,"
                + "kurssi_id INT NOT NULL,"
                + "arvosana INT NOT NULL,"
                + "suorituspaiva DATE,"
                + "FOREIGN KEY (opiskelija_id) REFERENCES Opiskelija(opiskelija_id),"
                + "FOREIGN KEY (kurssi_id) REFERENCES Kurssi(kurssi_id))"
        );

        // Lisataan muutama uusi opiskelija tauluihin
        addNewStudent(conn, 2006269, "Matti", "Hanninen", "Ilmarinkatu 46 C 34", "Tampere", "33500", "matti@theunguided.com", "0503762397");
        addNewStudent(conn, 2001234, "Teuvo", "Testaaja", "Testikatu 12", "Tampere", "33100", "teuvo@gmail.com", "050123456");
        addNewStudent(conn, 2004321, "Mauri", "Pekkarinen", "Pekkarisenpolku 10", "Seinajoki", "60320", "mauri@gmail.com", "040123456");
        addNewStudent(conn, 2009876, "Paavo", "Lipponen", "Lipposkuja 20", "Helsinki", "00100", "lippos@gmail.com", "04054321");

        // Lisataan muutama uusi kurssi tauluhin
        addNewCourse(conn, 362447, "Ohjelmoinnin perusteet", 5, "Opintojaksolla opiskellaan ohjelmoinnin peruskasitteet");
        addNewCourse(conn, 362566, "Olio-ohjelmointi", 5, "Opintojaksolla opiskellaan olio-ohjelmointi");
        addNewCourse(conn, 362123, "Kayttoliittymaohjelmointi", 5, "Opintojaksolla opiskellaan graafisten kayttoliittymien ohjelmia");

        // Lisataan muutama uusi suoritus tauluihin
        addNewCompletion(conn, 2006269, 362447, 5, "20.11.2020");
        addNewCompletion(conn, 2006269, 362566, 3, "04.01.2021");
        addNewCompletion(conn, 2001234, 362447, 2, "25.11.2020");
        addNewCompletion(conn, 2004321, 362123, 5, "16.03.2021");
        addNewCompletion(conn, 2009876, 362123, 5, "16.03.2021");
   
        // Suljetaan yhteys
        closeConnection(conn);

        launch(args);

    }

}
