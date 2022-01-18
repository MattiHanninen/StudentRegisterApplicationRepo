package studentregisterapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * EditCourseInfoViewController.java
 * FXML Controller class
 * @author Matti Hanninen
 * @version 1.00 16.03.2021 
 */

public class EditCourseInfoViewController implements Initializable {

    @FXML
    private TextField txtEditKurssiID;
    @FXML
    private TextField txtEditKurssiNimi;
    @FXML
    private TextField txtEditKurssinOpintopisteet;
    @FXML
    private TextField txtEditKurssinKuvaus;
    @FXML
    private Label txtKurssiPaivitetty;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Alustetaan columnit
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinID"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("KurssinNimi"));
        courseScopeColumn.setCellValueFactory(new PropertyValueFactory<>("Opintopisteet"));
        courseDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Kuvaus"));
        
        txtKurssiPaivitetty.setVisible(false);
        fillTableView();
    }    

    @FXML
    private void btnPaivitaKurssi(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
                     
            StudentRegisterApplication.updateCourseInfo(conn, txtEditKurssiNimi.getText(), Integer.parseInt(txtEditKurssinOpintopisteet.getText()), 
            txtEditKurssinKuvaus.getText(), Integer.parseInt(txtEditKurssiID.getText()));
                                  
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
            
            txtKurssiPaivitetty.setVisible(true);
            tblViewCourse.getItems().clear();
            fillTableView();
             
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
    
    private void fillTableView() {
           
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
            
            // Haetaan tiedot tietokannasta
            ResultSet courseInfoResult = StudentRegisterApplication.selectCourseInfo(conn);

            while (courseInfoResult.next()) {              
                    Course course = new Course(courseInfoResult.getInt("kurssi_id"), courseInfoResult.getString("kurssinNimi"),
                            courseInfoResult.getInt("opintopisteet"), courseInfoResult.getString("kuvaus"));
                            tblViewCourse.getItems().add(course);               
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
  
}
