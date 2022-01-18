package studentregisterapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * StudentRegisterApplicationViewController.java FXML Controller class
 *
 * @author Matti Hanninen
 * @version 1.00 16.03.2021
 */
public class StudentRegisterApplicationViewController implements Initializable {

    private Label label;
    @FXML
    private ComboBox<Integer> cboOpiskelija;
    @FXML
    private ComboBox<Integer> cboKurssi;
    @FXML
    private TableView<Student> tblViewStudent;
    @FXML
    private TableView<Course> tblViewCourse;
    @FXML
    private TableColumn<Student, Integer> studentIDColumn;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Course, String> completionNameColumn;
    @FXML
    private TableColumn<Course, Integer> courseIDColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, Integer> courseScopeColumn;
    @FXML
    private TableView<Completion> tblViewCompletion1;
    @FXML
    private TableView<Completion> tblViewCompletion2;
    @FXML
    private TableColumn<Completion, Integer> completionStudentIDColumn;
    @FXML
    private TableColumn<Completion, Date> completionDateColumn2;
    @FXML
    private TableColumn<Completion, Integer> completionGradeColumn2;
    @FXML
    private TableColumn<Completion, Date> completionDateColumn1;
    @FXML
    private TableColumn<Completion, Integer> completionGradeColumn1;
    @FXML
    private Label txtOpiskelijaPoistettu;
    @FXML
    private Label txtKurssiPoistettu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
        * Ohjelman alustukset
        */
        // Asetetaan oletusarvot CheckBoxeihin, ja laitetaan yksi arvo nakymaan kun ohjelma kaynnistetaan.
        cboOpiskelija.getItems().addAll(2006269, 2001234, 2004321, 2009876);
        cboOpiskelija.setValue(2006269);
        cboKurssi.getItems().addAll(362447, 362566, 362123);
        cboKurssi.setValue(362447);

        // Piilotetaan Poistettu-tekstit
        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);

        // Paivitetaan ComboBoxit
        updateComboBoxStudent();
        updateComboBoxCourse();

        // Alustetaan columnit
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("OpiskelijaID"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("sukunimi"));

        completionNameColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinID"));
        completionDateColumn1.setCellValueFactory(new PropertyValueFactory<>("Date"));
        completionGradeColumn1.setCellValueFactory(new PropertyValueFactory<>("arvosana"));

        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinID"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinNimi"));
        courseScopeColumn.setCellValueFactory(new PropertyValueFactory<>("Opintopisteet"));

        completionStudentIDColumn.setCellValueFactory(new PropertyValueFactory<>("OpiskelijaID"));
        completionDateColumn2.setCellValueFactory(new PropertyValueFactory<>("Date"));
        completionGradeColumn2.setCellValueFactory(new PropertyValueFactory<>("arvosana"));
    }

    @FXML
    private void menuCloseClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void menuAboutClicked(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION, "(c) Matti Hanninen", ButtonType.CLOSE);
        about.setTitle("About");
        about.setHeaderText("Student Register Application v1.0");
        about.show();

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);
    }

    @FXML
    private void cboOpiskelijaSelected(ActionEvent event) {

        cboOpiskelija.getValue();
        tblViewStudent.getItems().clear();
        tblViewCompletion1.getItems().clear();
        updateComboBoxStudent();

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);
    }

    @FXML
    private void btnLisaaUusiOpiskelija(ActionEvent event) throws IOException {

        // Aukaistaan opiskelijan lisays ikkuna
        Parent root = FXMLLoader.load(getClass().getResource("NewStudentView.fxml"));

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Lisaa uusi opiskelija");
        stage.setScene(scene);
        stage.show();

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);
    }

    @FXML
    private void btnPoistaOpiskelija(ActionEvent event) throws SQLException {

        try {

            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

            // Poistetaan opiskelijan suoritus
            String sqlQueryDeleteCompletion = "DELETE FROM Suoritus WHERE opiskelija_id = ?";
            PreparedStatement preparedStmtCompletion = conn.prepareStatement(sqlQueryDeleteCompletion);
            preparedStmtCompletion.setInt(1, cboOpiskelija.getValue());
            preparedStmtCompletion.execute();

            // Poistetaan opiskelija
            String sqlQueryDeleteStudent = "DELETE FROM Opiskelija WHERE opiskelija_id = ?";
            PreparedStatement preparedStmtStudent = conn.prepareStatement(sqlQueryDeleteStudent);
            preparedStmtStudent.setInt(1, cboOpiskelija.getValue());
            preparedStmtStudent.execute();

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);

            System.out.println("\t>> Opiskelijan suoritustiedot poistettu");
            System.out.println("\t>> Opiskelijan tiedot poistettu");

            updateComboBoxListStudent();
            txtOpiskelijaPoistettu.setVisible(true);

        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cboKurssiSelected(ActionEvent event) {

        cboKurssi.getValue();
        tblViewCourse.getItems().clear();
        tblViewCompletion2.getItems().clear();
        updateComboBoxCourse();

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);
    }

    @FXML
    private void btnLisaaKurssi(ActionEvent event) throws IOException {

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);

        // Aukaistaan kurssin lisays ikkuna
        Parent root = FXMLLoader.load(getClass().getResource("NewCourseView.fxml"));

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Lisaa uuden kurssin tiedot");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnPoistaKurssi(ActionEvent event) {

        try {

            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

            // Poistetaan opiskelijan suoritus
            String sqlQueryDeleteCompletion = "DELETE FROM Suoritus WHERE kurssi_id = ?";
            PreparedStatement preparedStmtCompletion = conn.prepareStatement(sqlQueryDeleteCompletion);
            preparedStmtCompletion.setInt(1, cboKurssi.getValue());
            preparedStmtCompletion.execute();

            // Poistetaan kurssi
            String sqlQueryDeleteCourse = "DELETE FROM Kurssi WHERE kurssi_id = ?";
            PreparedStatement preparedStmtStudent = conn.prepareStatement(sqlQueryDeleteCourse);
            preparedStmtStudent.setInt(1, cboKurssi.getValue());
            preparedStmtStudent.execute();

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);

            System.out.println("\t>> Opiskelijan suoritustiedot poistettu");
            System.out.println("\t>> Kurssin tiedot poistettu");

            updateComboBoxListCourse();

            txtKurssiPoistettu.setVisible(true);
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnMuokkaaOpiskelijaaJaSuorituksia(ActionEvent event) throws IOException {

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);

        // Aukaistaan opiskelijan muokkaus ikkuna
        Parent root = FXMLLoader.load(getClass().getResource("EditStudentInfoView.fxml"));

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Muokkaa tietoja / lisaa uusia suorituksia");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnMuokkaaKurssia(ActionEvent event) throws IOException {

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);

        // Aukaistaan kurssin muokkaus ikkuna
        Parent root = FXMLLoader.load(getClass().getResource("EditCourseInfoView.fxml"));

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Muokkaa kurssin tietoja");
        stage.setScene(scene);
        stage.show();
    }

    private void updateComboBoxStudent() {

        try {

            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

            // Haetaan tiedot tietokannasta
            ResultSet studentInfoResult = StudentRegisterApplication.selectStudentInfo(conn);
            ResultSet completionInfoResult = StudentRegisterApplication.selectCompletionInfo(conn);

            while (studentInfoResult.next()) {
                if (studentInfoResult.getInt("opiskelija_id") == cboOpiskelija.getValue()) {
                    Student student = new Student(studentInfoResult.getInt("opiskelija_id"), studentInfoResult.getString("sukunimi"));
                    tblViewStudent.getItems().add(student);
                }

            }

            while (completionInfoResult.next()) {
                if (completionInfoResult.getInt("opiskelija_id") == cboOpiskelija.getValue()) {
                    Completion completion = new Completion(completionInfoResult.getInt("opiskelija_id"), completionInfoResult.getInt("kurssi_id"), completionInfoResult.getInt("arvosana"), completionInfoResult.getDate("suorituspaiva"));
                    tblViewCompletion1.getItems().add(completion);
                }
            }

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);

        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Napataan kiinni InvocationTargetException, joka syntyy opiskelijaa poistaessa tai opiskelijalistaa paivittaessa
        catch (Exception ex) {
            System.err.println("An InvocationTargetException was caught!");
        }
    }

    private void updateComboBoxCourse() {

        try {

            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

            // Haetaan tiedot tietokannasta         
            ResultSet courseInfoResult = StudentRegisterApplication.selectCourseInfo(conn);
            ResultSet completionInfoResult = StudentRegisterApplication.selectCompletionInfo(conn);

            while (courseInfoResult.next()) {
                if (courseInfoResult.getInt("kurssi_id") == cboKurssi.getValue()) {
                    Course course = new Course(courseInfoResult.getInt("kurssi_id"), courseInfoResult.getString("kurssinNimi"), courseInfoResult.getInt("opintopisteet"));
                    tblViewCourse.getItems().add(course);

                }
            }

            while (completionInfoResult.next()) {
                if (completionInfoResult.getInt("kurssi_id") == cboKurssi.getValue()) {
                    Completion completion = new Completion(completionInfoResult.getInt("opiskelija_id"), completionInfoResult.getInt("kurssi_id"), completionInfoResult.getInt("arvosana"), completionInfoResult.getDate("suorituspaiva"));
                    tblViewCompletion2.getItems().add(completion);
                }
            }

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Napataan kiinni InvocationTargetException, joka syntyy opiskelijaa poistaessa tai opiskelijalistaa paivittaessa
        catch (Exception ex) {
            System.err.println("An InvocationTargetException was caught!");
        }
    }

    @FXML
    private void btnPaivitaOpiskelijaLista(ActionEvent event) {

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);

        updateComboBoxListStudent();
    }

    @FXML
    private void btnPaivitaKurssiLista(ActionEvent event) {

        txtOpiskelijaPoistettu.setVisible(false);
        txtKurssiPoistettu.setVisible(false);

        updateComboBoxListCourse();
    }

    private void updateComboBoxListStudent() {

        try {

            cboOpiskelija.getItems().clear();

            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

            // Haetaan tiedot tietokannasta
            ResultSet studentInfoResult = StudentRegisterApplication.selectStudentInfo(conn);
            ResultSet completionInfoResult = StudentRegisterApplication.selectCompletionInfo(conn);

            while (studentInfoResult.next()) {
                cboOpiskelija.getItems().addAll(studentInfoResult.getInt(1));
                cboOpiskelija.setValue(studentInfoResult.getInt(1));
            }

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);

            System.out.println("\t>> Opiskelilista paivitetty");
        }
        // Napataan kiinni SQL mahdolliset poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateComboBoxListCourse() {

        try {

            cboKurssi.getItems().clear();

            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                    + "3306?user=opiskelija&password=opiskelija1");

            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");

            // Haetaan tiedot tietokannasta
            ResultSet courseInfoResult = StudentRegisterApplication.selectCourseInfo(conn);
            ResultSet completionInfoResult = StudentRegisterApplication.selectCompletionInfo(conn);

            while (courseInfoResult.next()) {
                cboKurssi.getItems().addAll(courseInfoResult.getInt(1));
                cboKurssi.setValue(courseInfoResult.getInt(1));
            }

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);

            System.out.println("\t>> Kurssilista paivitetty");
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
