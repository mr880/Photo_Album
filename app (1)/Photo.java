/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controller.MainController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author mkasa
 */
public class Photo extends Application {
    
   
    public void start(Stage primaryStage) throws Exception {
            MainController.start(primaryStage);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop(){
    MainController.saveToFile();
    }
    
}
