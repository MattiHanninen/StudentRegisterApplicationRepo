package studentregisterapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * EditStudentInfoViewController.java
 * FXML Controller class
 * @author Matti Hanninen
 * @version 1.00 16.03.2021 
 */

public class EditStudentInfoViewController implements Initializable {

    @FXML
    private TextField txtMuokkaaOpiskelijaID;
    @FXML
    private TextField txtMuokkaaEtunimi;
    @FXML
    private TextField txtMuokkaaSukunimi;
    @FXML
    private TextField txtMuokkaaLahiosoite;
    @FXML
    private TextField txtMuokkaaPostitoimipaikka;
    @FXML
    private TextField txtMuokkaaPostinumero;
    @FXML
    private TextField txtMuokkaaSahkoposti;
    @FXML
    private TextField txtMuokkaaPuhelinnumero;
    @FXML
    private TextField txtSuoritusOpiskelijaID;
    @FXML
    private TextField txtSuoritusKurssiID;
    @FXML
    private TextField txtSuoritusArvosana;
    @FXML
    private TableView<Course> tblViewCourse;
    @FXML
    private TableColumn<Course, Integer> courseIDColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, Integer> courseScopeColumn;
    @FXML
    private TableColumn<Course, String> courseDescriptionColumn;
    @FXML
    private TableView<Completion> tblViewCompletion;
    @FXML
    private TableColumn<Completion, Integer> completionGradeColumn;
    @FXML
    private TableColumn<Completion, Date> completionDateColumn;   
    @FXML
    private DatePicker txtSuoritusDate;
    @FXML
    private Label txtSuoritusLisatty;
    @FXML
    private Label txtSuoritusPoistettu;
    @FXML
    private Label txtSuoritusPaivitetty;
    @FXML
    private Label txtOpiskelijatiedotPaivitetty;
    private final ArrayList<Student> students = new ArrayList<>();
    private Integer currentIndex;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*
        * Ohjelman alustukset
        */
        currentIndex = 0;
        addStudents();
        dataBind(students.get(0));
        fillTableViews();
        txtOpiskelijatiedotPaivitetty.setVisible(false);
        txtSuoritusPaivitetty.setVisible(false);
        txtSuoritusLisatty.setVisible(false);
        txtSuoritusPoistettu.setVisible(false);
        
        
        // Alustetaan columnit
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinID"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinNimi"));
        courseScopeColumn.setCellValueFactory(new PropertyValueFactory<>("Opintopisteet"));
        courseDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Kuvaus"));
        
        completionGradeColumn.setCellValueFactory(new PropertyValueFactory<>("arvosana"));
        completionDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));   
    }    

    @FXML
    private void btnPaivitaOpiskelijaTiedot(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
                                                 
            StudentRegisterApplication.updateStudentInfo(conn, txtMuokkaaEtunimi.getText(), txtMuokkaaSukunimi.getText(), 
            txtMuokkaaLahiosoite.getText(), txtMuokkaaPostitoimipaikka.getText(), txtMuokkaaPostinumero.getText(), txtMuokkaaSahkoposti.getText(), txtMuokkaaPuhelinnumero.getText(), Integer.parseInt(txtMuokkaaOpiskelijaID.getText())); 
                                   
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
            
            txtOpiskelijatiedotPaivitetty.setVisible(true);
            txtSuoritusPaivitetty.setVisible(false);
            txtSuoritusLisatty.setVisible(false);
            txtSuoritusPoistettu.setVisible(false);                                    
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);       
        } 
            
  
    }

    @FXML
    private void btnLisaaUusiSuoritus(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
            
            StudentRegisterApplication.addNewCompletion(conn, Integer.parseInt(txtSuoritusOpiskelijaID.getText()), Integer.parseInt(txtSuoritusKurssiID.getText()),
                    Integer.parseInt(txtSuoritusArvosana.getText()), txtSuoritusDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            
      
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
            
            txtSuoritusLisatty.setVisible(true);
            tblViewCourse.getItems().clear();
            tblViewCompletion.getItems().clear();
            fillTableViews();
            
            txtOpiskelijatiedotPaivitetty.setVisible(false);
            txtSuoritusPaivitetty.setVisible(false);
            txtSuoritusPoistettu.setVisible(false);
            
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);       
        }    
    }

    @FXML
    private void btnPoistaSuoritus(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
            
            // Poistetaan opiskelijan suoritus
            String sqlQueryDeleteCompletion = "DELETE FROM Suoritus WHERE kurssi_id = ? AND opiskelija_id = ?";
            PreparedStatement preparedStmtCompletion = conn.prepareStatement(sqlQueryDeleteCompletion);
            preparedStmtCompletion.setInt(1, Integer.parseInt(txtSuoritusKurssiID.getText()));
            preparedStmtCompletion.setInt(2, Integer.parseInt(txtSuoritusOpiskelijaID.getText()));
            preparedStmtCompletion.execute();
            

            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
            
            txtSuoritusPoistettu.setVisible(true);
            
            System.out.println("\t>> Opiskelijan suoritustiedot poistettu"); 
            
            txtOpiskelijatiedotPaivitetty.setVisible(false);
            txtSuoritusPaivitetty.setVisible(false);
            txtSuoritusLisatty.setVisible(false);     
            tblViewCompletion.getItems().clear();
            tblViewCourse.getItems().clear();
            fillTableViews();
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
    
    private void fillTableViews() {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
        
            // Haetaan tiedot tietokannasta                               
            ResultSet courseAndCompletionInfo = StudentRegisterApplication.selectCourseAndCompletionInfo(conn);
            
            while (courseAndCompletionInfo.next()) {
                if (courseAndCompletionInfo.getInt("opiskelija_id") == Integer.parseInt(txtMuokkaaOpiskelijaID.getText())) {
                    Course course = new Course(courseAndCompletionInfo.getInt("kurssi_id"), courseAndCompletionInfo.getString("kurssinNimi"),
                            courseAndCompletionInfo.getInt("opintopisteet"), courseAndCompletionInfo.getString("kuvaus"));
                    tblViewCourse.getItems().add(course);
                    Completion completion = new Completion(courseAndCompletionInfo.getInt("opiskelija_id"), courseAndCompletionInfo.getInt("kurssi_id"),
                            courseAndCompletionInfo.getInt("arvosana"), courseAndCompletionInfo.getDate("suorituspaiva"));                   
                    tblViewCompletion.getItems().add(completion);
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
    }

    @FXML
    private void toolFirstClicked(ActionEvent event) {
        
        txtOpiskelijatiedotPaivitetty.setVisible(false);
        txtSuoritusPaivitetty.setVisible(false);
        txtSuoritusLisatty.setVisible(false);
        txtSuoritusPoistettu.setVisible(false);
        
        currentIndex = 0;
       
        Student student = students.get(currentIndex);
        dataBind(student);   
        tblViewCourse.getItems().clear();
        tblViewCompletion.getItems().clear();
        fillTableViews();              
    }

    @FXML
    private void toolPrevClicked(ActionEvent event) {
        
        txtOpiskelijatiedotPaivitetty.setVisible(false);
        txtSuoritusPaivitetty.setVisible(false);
        txtSuoritusLisatty.setVisible(false);
        txtSuoritusPoistettu.setVisible(false);
        
        currentIndex--;
        
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        
        Student student = students.get(currentIndex);
        dataBind(student);
        tblViewCourse.getItems().clear();
        tblViewCompletion.getItems().clear();
        fillTableViews();
    }

    @FXML
    private void toolNextClicked(ActionEvent event) {
        
        txtOpiskelijatiedotPaivitetty.setVisible(false);
        txtSuoritusPaivitetty.setVisible(false);
        txtSuoritusLisatty.setVisible(false);
        txtSuoritusPoistettu.setVisible(false);
        
        currentIndex++;
        
        if (currentIndex > students.size()-1) {
            currentIndex = students.size()-1;
        }
        
        Student student = students.get(currentIndex);
        dataBind(student);  
        tblViewCourse.getItems().clear();
        tblViewCompletion.getItems().clear();
        fillTableViews();
    }
        

    @FXML
    private void toolLastClicked(ActionEvent event) {
        
        txtOpiskelijatiedotPaivitetty.setVisible(false);
        txtSuoritusPaivitetty.setVisible(false);
        txtSuoritusLisatty.setVisible(false);
        txtSuoritusPoistettu.setVisible(false);
        
        currentIndex = students.size()-1;
        
        Student student = students.get(currentIndex);
        dataBind(student);
        tblViewCourse.getItems().clear();
        tblViewCompletion.getItems().clear();
        fillTableViews();
    }

    
    private void addStudents() {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
        
            // Haetaan tiedot tietokannasta
            ResultSet studentInfoResult = StudentRegisterApplication.selectStudentInfo(conn);
            

            while (studentInfoResult.next()) {
                Student student = new Student();
                student.setOpiskelijaID(studentInfoResult.getInt("opiskelija_id"));
                student.setEtunimi(studentInfoResult.getString("etunimi"));
                student.setSukunimi(studentInfoResult.getString("sukunimi"));
                student.setLahiosoite(studentInfoResult.getString("lahiosoite"));
                student.setPostitoimipaikka(studentInfoResult.getString("postitoimipaikka"));
                student.setPostinro(studentInfoResult.getString("postinumero"));
                student.setEmail(studentInfoResult.getString("email"));
                student.setPuhelinnro(studentInfoResult.getString("puhelinnumero"));
                
                // Lisataan opiskelija ArrayListille
                students.add(student);
            }
             
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);                       
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);       
        }               
    }
    
    // Sidoksen poisto
    private Student lastBindedStudent;  // apumuuttuja
    
    private void dataBind(Student student) {
          
        lastBindedStudent = student;
        
        txtMuokkaaOpiskelijaID.setText(Integer.toString(student.getOpiskelijaID()));
        txtMuokkaaEtunimi.setText(student.getEtunimi());
        txtMuokkaaSukunimi.setText(student.getSukunimi());
        txtMuokkaaLahiosoite.setText(student.getLahiosoite());
        txtMuokkaaPostitoimipaikka.setText(student.getPostitoimipaikka());
        txtMuokkaaPostinumero.setText(student.getPostinro());
        txtMuokkaaSahkoposti.setText(student.getEmail());
        txtMuokkaaPuhelinnumero.setText(student.getPuhelinnro());   
    }

    @FXML
    private void btnPaivitaSuoritus(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
                                    
            StudentRegisterApplication.updateCompletionInfo(conn, Integer.parseInt(txtSuoritusArvosana.getText()), txtSuoritusDate.getValue().toString(),
                    Integer.parseInt(txtSuoritusOpiskelijaID.getText()), Integer.parseInt(txtSuoritusKurssiID.getText()));
                                 
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
            
            txtSuoritusPaivitetty.setVisible(true);
            txtOpiskelijatiedotPaivitetty.setVisible(false);           
            txtSuoritusLisatty.setVisible(false);
            txtSuoritusPoistettu.setVisible(false);
            tblViewCourse.getItems().clear();
            tblViewCompletion.getItems().clear();
            fillTableViews();
             
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
    
}
