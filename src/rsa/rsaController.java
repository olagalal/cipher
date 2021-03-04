/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import numberTheory.EuclidianForGCD;
import numberTheory.PrimeFactorization;
import numberTheory.PrimeNumbers;

/**
 * FXML Controller class
 *
 * @author Ola Glal
 */
public class rsaController implements Initializable {

    @FXML
    private Font x2;
    @FXML
    private TextField tP1;
    @FXML
    private Button bExit;
    @FXML
    private Button bBack;
    @FXML
    private Font x1;
    @FXML
    private RadioButton rC1;
    @FXML
    private RadioButton rC2;
    @FXML
    private TextField tQ1;
    @FXML
    private TextField tE1;
    @FXML
    private TextField tP2;
    @FXML
    private TextField tQ2;
    @FXML
    private TextField tE2;
    @FXML
    private Label lblR;
    @FXML
    private TextField msg;
    @FXML
    private RadioButton rEn;
    @FXML
    private RadioButton rDe;
    @FXML
    private Button bRun;

    Client c1 = null;
    Client c2 = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final ToggleGroup group1 = new ToggleGroup();
        rC1.setToggleGroup(group1);
        rC1.setSelected(true);
        rC2.setToggleGroup(group1);
        
        final ToggleGroup group2 = new ToggleGroup();
        rEn.setToggleGroup(group2);
        rEn.setSelected(true);
        rDe.setToggleGroup(group2);
        
        lblR.setText("wait the result");
        long p1 = PrimeNumbers.genRandomPrime();
        tP1.setText(Long.toString(p1));
        long p2 = PrimeNumbers.genRandomPrime();
        tP2.setText(Long.toString(p2));
        
        long Q1 = PrimeNumbers.genRandomPrime();
        tQ1.setText(Long.toString(Q1));
        long Q2 = PrimeNumbers.genRandomPrime();
        tQ2.setText(Long.toString(Q2));
        long phiN1 = (p1 - 1) * (Q1 - 1);
        tE1.setText(Long.toString(getRandomeE(phiN1)));
        long phiN2 = (p2 - 1) * (Q2 - 1);
        tE2.setText(Long.toString(getRandomeE(phiN2)));
        
    }    
    public long getRandomeE(long phiN){
        long e = PrimeNumbers.genRandomPrime();
        while(EuclidianForGCD.GCD(e, phiN) != 1 && e > 1)
            e = PrimeNumbers.genRandomPrime();
        return e;
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
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    public ArrayList<Long> AL(String msg){
        ArrayList<Long> ret = new ArrayList<Long>();
        String temp = "" ;
        for(int i = 0; i < msg.length(); ++i){
            if(msg.charAt(i) == ' '){
                ret.add(Long.parseLong(temp));
                temp = "";
                continue;
            }
            temp += msg.charAt(i);
        }
        if(!temp.equals(""))
            ret.add(Long.parseLong(temp));
        return ret;
    }
    boolean validClient(long p, long q, long e){
        long phiN = (p - 1) * (q - 1);
        return PrimeNumbers.isPrime(p) && PrimeNumbers.isPrime(q) && EuclidianForGCD.GCD(e, phiN) == 1 && e != 1;
    }
    
    @FXML
    private void toRun(ActionEvent event) {
        if(!validClient(Long.parseLong(tP1.getText()), Long.parseLong(tQ1.getText()), Long.parseLong(tE1.getText()))){
            JOptionPane.showMessageDialog(null, "Client 1 has invalid data input");
        }else if(!validClient(Long.parseLong(tP2.getText()), Long.parseLong(tQ2.getText()), Long.parseLong(tE2.getText()))){
            JOptionPane.showMessageDialog(null, "Client 2 has invalid data input");
        }else{
            c1 = new Client(Long.parseLong(tP1.getText()), Long.parseLong(tQ1.getText()), Long.parseLong(tE1.getText()));
            c2 = new Client(Long.parseLong(tP2.getText()), Long.parseLong(tQ2.getText()), Long.parseLong(tE2.getText()));
            if(rC1.isSelected()&&rEn.isSelected()){
                ArrayList<Long> enC1 = c1.encryptMsg(msg.getText(), c2.getPublicKey());
                String res = "";
                for(int i = 0; i < enC1.size(); ++i)
                    res += ("" + enC1.get(i) + " ");
                lblR.setText(res);
            } else if(rC1.isSelected()&&rDe.isSelected()){
                ArrayList<Long> enC1 = AL(msg.getText());
                lblR.setText(c1.decryptMsg(enC1));
                System.out.println(c1.decryptMsg(enC1));
            } else if(rC2.isSelected()&&rEn.isSelected()){
                ArrayList<Long> enC2 = c2.encryptMsg(msg.getText(), c1.getPublicKey());
                String res = "";
                for(int i = 0; i < enC2.size(); ++i)
                    res += ("" + enC2.get(i) + " ");
                lblR.setText(res);
            } else if(rC2.isSelected()&&rDe.isSelected()){
                ArrayList<Long> enC2 = AL(msg.getText());
                lblR.setText(c2.decryptMsg(enC2));
            }
        }
    }

    @FXML
    private void toCopy(ActionEvent event) {
        String copy = lblR.getText();
        StringSelection myString = new StringSelection(copy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(myString, null);
    }

    @FXML
    private void toRandom(ActionEvent event) {
        
        long p1 = PrimeNumbers.genRandomPrime();
        tP1.setText(Long.toString(p1));
        long p2 = PrimeNumbers.genRandomPrime();
        tP2.setText(Long.toString(p2));
        
        long Q1 = PrimeNumbers.genRandomPrime();
        tQ1.setText(Long.toString(Q1));
        long Q2 = PrimeNumbers.genRandomPrime();
        tQ2.setText(Long.toString(Q2));
        long phiN1 = (p1 - 1) * (Q1 - 1);
        tE1.setText(Long.toString(getRandomeE(phiN1)));
        long phiN2 = (p2 - 1) * (Q2 - 1);
        tE2.setText(Long.toString(getRandomeE(phiN2)));
    }
    
}
