package hu.alkfejl.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SampleController {

    @FXML
    private Button goomb;

    @FXML
    private TextField echo;

    public SampleController(){

    }

    @FXML
    private void handleEcho(ActionEvent e){
        System.out.println(echo.getText());
    }
}
