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
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hodam
 */
public class AffineController implements Initializable {

    @FXML
    private Font x1;
    @FXML
    private RadioButton radioNormal;
    @FXML
    private TextField inputField;
    @FXML
    private TextField multiplicativeKey;
    @FXML
    private RadioButton radioEncryption;
    @FXML
    private RadioButton radioDecryption;
    @FXML
    private CheckBox checkUpper;
    @FXML
    private CheckBox checkSpecial;
    @FXML
    private RadioButton radioCustom;
    @FXML
    private TextField customField;
    @FXML
    private TextField additiveKey;

    final ToggleGroup group1 = new ToggleGroup();
    final ToggleGroup group2 = new ToggleGroup();

    AffineAlgorithm algorithm = new AffineAlgorithm();

    static String affineResult = "";
    @FXML
    private CheckBox checkSpaces;

    boolean labelFlag;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioNormal.setToggleGroup(group1);
        radioCustom.setToggleGroup(group1);
        radioNormal.setSelected(true);

        customField.setDisable(true);

        radioEncryption.setToggleGroup(group2);
        radioDecryption.setToggleGroup(group2);
        radioEncryption.setSelected(true);

        radioCustom.setOnAction((event) -> {
            customField.setDisable(false);
            checkUpper.setDisable(true);
            checkSpaces.setDisable(true);
            checkSpecial.setDisable(true);
        });

        radioNormal.setOnAction((event) -> {
            customField.setDisable(true);
            checkUpper.setDisable(false);
            checkSpaces.setDisable(false);
            checkSpecial.setDisable(false);
        });
    }

    @FXML
    private void toExit(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    private void toBack(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/securityProject/Main.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void toNext(ActionEvent event) throws IOException {
        try {
            algorithm.setInput(inputField.getText());
            algorithm.setK(Integer.parseInt(additiveKey.getText()));
            algorithm.setM(Integer.parseInt(multiplicativeKey.getText()));

            if (group1.getSelectedToggle() == radioNormal) {
                if (checkUpper.isSelected()) {
                    algorithm.insertUpperCase();
                    algorithm.setN(algorithm.getN() + 26);
                }
                if (checkSpecial.isSelected()) {
                    algorithm.insertSpecialCharacters();
                    algorithm.setN(algorithm.getN() + 30);
                }
                if (checkSpaces.isSelected()) {
                    algorithm.insertSpaces();
                    algorithm.setN(algorithm.getN() + 1);
                }
            } else {
                algorithm.customAlphabet(customField.getText());
            }

            if (group2.getSelectedToggle() == radioEncryption) {
                affineResult = algorithm.encryption();
                labelFlag = true;

            } else if (group2.getSelectedToggle() == radioDecryption) {
                affineResult = algorithm.decryption();
                labelFlag = false;
            }

            Parent root = null;
            root = FXMLLoader.load(getClass().getResource("AffineResult.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
            algorithm = new AffineAlgorithm();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Please Fill The Fields Correctly !");
        }
    }
}
