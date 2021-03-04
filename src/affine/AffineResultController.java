/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package affine;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hodam
 */
public class AffineResultController extends AffineController implements Initializable {

    @FXML
    private Label idText;
    @FXML
    private Label resultText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (labelFlag) {
            idText.setText("Cipher Text:");
        } else {
            idText.setText("Plain Text:");
        }
        resultText.setText(affineResult);
    }

    @FXML
    private void toExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void toBack(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/affine/Affine.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

}
