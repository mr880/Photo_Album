package controller;


import static controller.MainController.getModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import model.UserList;




public class LoginController extends MainController implements Interface {

    /**
     * User name to login
     */
    @FXML TextField userID;


    /**
     * Initializes login stage
     */
    public void initBeforeShow() {
		getModel().setCurrentUser(null);
		userID.clear();
	}


    /**
     * If enter key is pressed, login
     * @param keyEvent Key pressed (enter)
     */
    public void userIDKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
        	doLogin();
        }
	}


    /**
     * Performs login
     */
    public void doLogin() {
    	String user_id = userID.getText().trim();
    	//
    	UserList user = getModel().getUser(user_id);
    	if (user!=null) {
    		getModel().setCurrentUser(user);
        	if (user_id.equalsIgnoreCase("admin")) {
            	adminLogin();
        	}
        	else {
            	otherLogin();
        	}
    	}
    	else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Not registered User");
			alert.setContentText("Username is not found");
			alert.showAndWait();
    		userID.setText("");
    	}
    }


    /**
     * Exits program
     */
    public void doExit() {
		Platform.exit();
	}


   
}