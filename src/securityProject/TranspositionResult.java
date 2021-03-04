/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityProject;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static securityProject.TranspositionController.result;
import static securityProject.TranspositionController.flag2;

/**
 * FXML Controller class
 *
 * @author Ola Galal
 */
public class TranspositionResult implements Initializable {

    @FXML
    private Label idText;
    @FXML
    private Label tResult;
    @FXML
    private Button bExit;
    @FXML
    private Button bBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(flag2){
            idText.setText("Cipher Text:");
        }else{
            idText.setText("Plain Text:");
        }
        tResult.setText(result);
    }    

    @FXML
    private void toExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void toBack(ActionEvent event) throws IOException {
        flag2 = false;
        result = "";
                
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("Transposition.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void toCopy(ActionEvent event) {
        String copy = tResult.getText();
        StringSelection myString = new StringSelection(copy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(myString, null);
    }
    
}
