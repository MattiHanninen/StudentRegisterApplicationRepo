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
 * NewStudentViewController.java
 * FXML Controller class
 * @author Matti Hanninen
 * @version 1.00 16.03.2021 
 */

public class NewStudentViewController implements Initializable {

    @FXML
    protected TextField txtUusiOpiskelijaID;
    @FXML
    private TextField txtUusiEtunimi;
    @FXML
    private TextField txtUusiSukunimi;
    @FXML
    private TextField txtUusiLahiosoite;
    @FXML
    private TextField txtUusiPostitoimipaikka;
    @FXML
    private TextField txtUusiPostinumero;
    @FXML
    private TextField txtUusiSahkoposti;
    @FXML
    private TextField txtUusiPuhelinnumero;
    @FXML
    private Label txtUusiOpiskelijaLisatty;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtUusiOpiskelijaLisatty.setVisible(false);    
    }    


    @FXML
    private void btnTyhjennaTiedot(ActionEvent event) {
        
        txtUusiOpiskelijaID.setText("");
        txtUusiEtunimi.setText("");
        txtUusiSukunimi.setText("");
        txtUusiLahiosoite.setText("");
        txtUusiPostitoimipaikka.setText("");
        txtUusiPostinumero.setText("");
        txtUusiSahkoposti.setText("");
        txtUusiPuhelinnumero.setText("");
        txtUusiOpiskelijaLisatty.setVisible(false);
    }

    @FXML
    private void btnLisaaOpiskelija(ActionEvent event) {
        
        try {
            
            // Aukaistaan tietokantayhteys
            Connection conn = StudentRegisterApplication.openConnection("jdbc:mariadb://maria.westeurope.cloudapp.azure.com:"
                + "3306?user=opiskelija&password=opiskelija1");
            
            // Asetetaan oikea tietokanta yhteydessa valituksi
            StudentRegisterApplication.useDatabase(conn, "karelia_mattihan");
        
            StudentRegisterApplication.addNewStudent(conn, Integer.parseInt(txtUusiOpiskelijaID.getText()), txtUusiEtunimi.getText(), txtUusiSukunimi.getText(), 
            txtUusiLahiosoite.getText(), txtUusiPostitoimipaikka.getText(), txtUusiPostinumero.getText(), txtUusiSahkoposti.getText(), txtUusiPuhelinnumero.getText());
        
            // Suljetaan tietokantayhteys
            StudentRegisterApplication.closeConnection(conn);
            
            txtUusiOpiskelijaLisatty.setVisible(true);
            
        }
        // Napataan kiinni mahdolliset SQL poikkeukset
        catch (SQLException ex) {
            System.out.println("Catchiin meni");
            java.util.logging.Logger.getLogger(StudentRegisterApplicationViewController.class.getName()).log(Level.SEVERE, null, ex);       
        }          
    }
    
}
