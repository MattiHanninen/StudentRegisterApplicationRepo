package studentregisterapplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * NewCourseViewController.java
 * FXML Controller class
 * @author Matti Hanninen
 * @version 1.00 16.03.2021 
 */

public class NewCourseViewController implements Initializable {

    @FXML
    private TextField txtUusiKurssiID;
    @FXML
    private TextField txtUusiKurssiNimi;
    @FXML
    private TextField txtUusiKurssinOpintopisteet;
    @FXML
    private TextField txtUusiUusiKurssinKuvaus;
    @FXML
    private Label txtUusiKurssiLisatty;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnLisaaKurssi(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
                              
            StudentRegisterApplication.addNewCourse(conn, Integer.parseInt(txtUusiKurssiID.getText()), txtUusiKurssiNimi.getText(), 
                Integer.parseInt(txtUusiKurssinOpintopisteet.getText()), txtUusiUusiKurssinKuvaus.getText());
        
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);      
            
            txtUusiKurssiLisatty.setVisible(true);
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);       
        }    
    }

    @FXML
    private void btnTyhjennaTiedot(ActionEvent event) {
        
        txtUusiKurssiID.setText("");
        txtUusiKurssiNimi.setText("");
        txtUusiKurssinOpintopisteet.setText("");
        txtUusiUusiKurssinKuvaus.setText("");
        txtUusiKurssiLisatty.setVisible(false);
    }
    
}
