/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityProject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ola Galal
 */
public class TranspositionController implements Initializable {

    @FXML
    private TextField tText;
    @FXML
    private Button bExit;
    @FXML
    private Button bBack;
    @FXML
    private Font x1;
    @FXML
    private TextField tKey;
    @FXML
    private Button bNext;
    @FXML
    private RadioButton rEn;
    @FXML
    private RadioButton rDe;

    final private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    final private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
    final ToggleGroup group = new ToggleGroup();

    int flag = 0;
    int totalChar = 0;
    int index = 0;
    public static String result = "";
    char alphabetic = 'a';
    public static boolean flag2 = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //RadioButtons
        rEn.setToggleGroup(group);
        rEn.setSelected(true);
        rDe.setToggleGroup(group);
    }

    @FXML
    private void toExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void toBack(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
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

        //Algorithm
        String textTemp = tText.getText();
        String text = "";
        for (int i = 0; i < textTemp.length(); i++) {
            if (!(textTemp.charAt(i) == ' ')) {
                text += textTemp.charAt(i);
            }
        }

        String sKey = tKey.getText();
        int fa[] = new int[255];
        String res = "";
        for(int i = 0; i < sKey.length(); ++i){
            if(fa[sKey.charAt(i)] == 0)
                res += sKey.charAt(i);
            ++fa[sKey.charAt(i)];
        }
               
        char[] key = res.toCharArray();
        double keyLength = res.length();

        int colLength = (int) Math.ceil(text.length() / keyLength);
        char[][] matrix = new char[colLength][(int) keyLength];

//      System.out.println(colLength);
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (selected == rEn) {
            //Encryption
            for (int i = 0; i < colLength; i++) {
                for (int j = 0; j < keyLength; j++) {
                    if ((i * keyLength) + j < text.length()) {
                        matrix[i][j] = text.charAt((i * (int) keyLength) + j);
                    } else {
                        matrix[i][j] = alphabetic;
                        alphabetic++;
                    }
                }
            }
        
            Pair[] pKey = new Pair[(int) keyLength];
            for (int i = 0; i < keyLength; i++) {
                pKey[i] = new Pair(i, key[i]*-1);
                //pKey[i] = new Pair(i, key[i]*-1);
            }
            Arrays.sort(pKey);
            
            /*for(int i=0; i<matrix.length; i++){
                for(int j=0; j<matrix[0].length; j++){
                    System.out.print(matrix[i][j]);
                }
                System.out.println("");
            }*/
            
            for (int i = 0; i < keyLength; i++) {
                for (int j = 0; j < colLength; j++) {
                    result += matrix[j][pKey[i].index];
                }
            }
            
            //System.out.println(result);
            flag2 = true;
            
        } else {
            //Decryption
            Pair[] pKey = new Pair[(int) keyLength];
            for (int i = 0; i < keyLength; i++) {
                pKey[i] = new Pair(i, key[i]);
            }
            Arrays.sort(pKey);
            
            for (int i = 0; i < keyLength; i++) {
                for (int j = 0; j < colLength; j++) {
                    result += matrix[j][pKey[i].index];
                }
            }

            //fill matrix
            int k = 0;
            for (int i = 0; i < keyLength; i++) {
                for (int j = 0; j < colLength; j++) {
                    matrix[j][pKey[i].index] = text.charAt(k);
                    k++;
                }
            }

            for (int i = 0; i < colLength; i++) {
                for (int j = 0; j < keyLength; j++) {
                    result+=matrix[i][j];
                }
            }
            
            //print
            /*for (int i = 0; i < colLength; i++) {
                for (int j = 0; j < keyLength; j++) {
                    System.out.print(matrix[i][j]);
                }
                System.out.println("");
            }*/
        }
//        System.out.println(result);

        //Build the scene
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("TranspositionResult.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

}
