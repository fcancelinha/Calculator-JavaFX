package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Calculation;
import model.Operand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Control {

    private Calculation calculator;
    private boolean canOperand = true;
    private boolean canDecimal = true;
    private boolean isOutput = false;
    private ArrayList<String> numbers = new ArrayList<String>();
    private String memory = "0";

    @FXML
    private TextField txtMain;

    @FXML
    private Label lblSub;

    @FXML
    private void initialize(){ calculator = new Calculation();}


    //Methods
    @FXML
    private void darkMode(ActionEvent event) throws IOException {

        Parent changeMode = FXMLLoader.load(getClass().getResource("../view/calculatorUIDark.fxml"));
        scenechanger(event, changeMode);
    }

    @FXML
    private void lightMode(ActionEvent event) throws IOException {

        Parent changeMode = FXMLLoader.load(getClass().getResource("../view/calculatorUI.fxml"));
        scenechanger(event, changeMode);
    }

    private void scenechanger(ActionEvent event,Parent changeMode){

        Scene changeModeScene = new Scene(changeMode);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(changeModeScene);
        window.show();
    }

    @FXML
    private void clickNumber(ActionEvent event){
        Button btn = (Button) event.getSource();

        txtMain.setText(txtMain.getText() + btn.getText());
        canOperand = true;
        isOutput = false;
    }

    @FXML
    private void clickDecimal(ActionEvent event){
        Button btn = (Button) event.getSource();

        if(canDecimal){
            isOutput = false;
            txtMain.setText(txtMain.getText() + btn.getText());
            canDecimal = !canDecimal;
        }
    }

    @FXML
    private void clickOperand(ActionEvent event){
        Button btn = (Button) event.getSource();

        if(canOperand && !(txtMain.getText().startsWith(".") && txtMain.getText().endsWith("."))) {
            txtMain.setText(txtMain.getText() + " " + btn.getText() + " ");
            canOperand = !canOperand;
            canDecimal = true;
            isOutput = false;
        }
    }

    private boolean checkOperandBegin(){

        for(Operand c : Operand.values())
            if(txtMain.getText().startsWith(" " + String.valueOf(c.getOperandSymbol())))
                return true;

        return false;
    }

    @FXML
    private void result(){

        if(checkOperandBegin()){
            txtMain.setText("0 " + txtMain.getText().trim());
            isOutput = false;
        }

        if(txtMain.getText().length() >= 5){

            numbers.addAll(Arrays.asList(txtMain.getText().split(" ")));

            lblSub.setText(txtMain.getText());
            txtMain.setText(calculator.calculate(numbers));

            isOutput = true;
            canDecimal = true;
            numbers.clear();
        }

    }

    @FXML
    private void clickDel(){

        if(txtMain.getText().length() > 0)
            if(!txtMain.getText().endsWith(" "))
                txtMain.setText(txtMain.getText().substring(0, txtMain.getText().length() - 1));
    }

    @FXML
    private void clear(ActionEvent event){
        Button btn = (Button) event.getSource();
        canOperand = true;

        if(btn.getText().equals("C")){
            txtMain.clear();
            lblSub.setText("");
            calculator.clear();
        }else if(btn.getText().equals("CE")){
            txtMain.clear();
        }
    }

    @FXML
    private void memory(ActionEvent event) {
        Button btn = (Button) event.getSource();

        if(btn.getText().equals("MS") && isOutput){
            memory = txtMain.getText();
            lblSub.setText("Saved");
        }

        if(btn.getText().equals("MR") && memory != null)
            txtMain.setText(memory);

        if(canOperand) {
            if (btn.getText().equals("M+"))
                txtMain.setText(txtMain.getText() + " + " + memory);
            else if (btn.getText().equals("M-"))
                txtMain.setText(txtMain.getText() + " - " + memory);
        }

    }



}
