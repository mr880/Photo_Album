/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MainController.admin;
import static controller.MainController.getModel;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import model.UserList;

/**
 *
 * @author mkasa
 */
public class AdminController extends MainController implements Interface, ChangeListener<UserList> {
    
   
    @FXML ListView<UserList> listView;
    @FXML TextField usern;
    
    public AdminController() {

    }

   
    @Override
    public void initBeforeShow() {
        usern.clear();
    }


  
    public void initialize() {
    	Admin save = getModel();
        listView.setItems(save.getListUsers());
        listView.getSelectionModel().selectedItemProperty().addListener(this);
        if (save.getListUsers().size() > 0) {
            listView.getSelectionModel().select(0);
        }
    }

    public void doDelete() {
    	Admin save = getModel();
    	int index = listView.getSelectionModel().getSelectedIndex();
    	if (index>=0) {
            save.deleteUser(index);
            listView.refresh();
    	}
    }

    public void doAdd() {
    	String username = usern.getText().trim();
    	Admin save = getModel();
        if (!username.isEmpty()) {
        	UserList user = save.getUser(username);
            if (user!=null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name should be unique case insensitive.");
                alert.setContentText("Another user with this name exists already.");
                alert.showAndWait();
            }
            else {
                save.addUser(username);
                
                usern.clear();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Username");
            alert.setContentText("Username should not be empty");
            alert.showAndWait();
        }
    }


    
    public void gotoAlbumList() {
        admin();
    }

     public void logoutBut(ActionEvent event) throws IOException{
        ((Node) (event.getSource())).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
   


    public void changed(ObservableValue<? extends UserList> arg0, UserList arg1, UserList arg2) {
    }
    
}
