/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.UserList;

/**
 *
 * @author mkasa
 */
public class MainController {
    public static String FILE_DATA = "Test.java";
    protected static Scene loginWindow;
    public static Scene adminWindow;
    public static Scene userWindow;
    public static Scene albumWindow;
    public static Stage loginWin;
    public static Stage albumWin;
    public static Interface login;
    public static Interface admin;
    public static Interface user;
    public static Interface album;
    
    
    
    /**
     * @param primaryStage Primary stage of application
     * @throws Exception In case stage does not start
     */
    public static void start(Stage primaryStage) throws Exception {

        {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/view/Login.fxml"));
            Parent root = loader.load();
            loginWindow = new Scene(root);
            login = loader.getController();
        }
        
        {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/view/AdminDisplay.fxml"));
            Parent root = loader.load();
            adminWindow = new Scene(root);
            admin = loader.getController();
        }
        
        {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/view/AlbumDisplay.fxml"));
            Parent root = loader.load();
            userWindow = new Scene(root);
            user = loader.getController();
        }
        
        {	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("/view/Album_1.fxml"));
            Parent root = loader.load();
            albumWindow = new Scene(root);
            album = loader.getController();
        }

        loginWin = primaryStage;
        albumWin = new Stage();


        
        login();
    }
    
     private static Admin save;
    
    
    
    public static Admin getModel() {
        if (save == null) {
            try {
                FileInputStream fileIn = new FileInputStream(FILE_DATA);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                save = (Admin)in.readObject();
                save.file(false);
                in.close();
                fileIn.close();
            }
            catch(IOException | ClassNotFoundException i) {
                save = null;
            }
            if (save==null) {
                save = new Admin();
                save.addUser("admin");
                save.addUser("stock");
                save.setCurrentUser(save.getUser("stock"));
                UserList user = save.getCurrentUser();
                
                Album album1 = user.addAlbum("");
               
                
               Album album2 = user.addAlbum("");
              
            }
        }
        //
        return save;
    }

   
    public static void saveToFile() {
        if (save!=null) {
            save.file(true);
            try {
                FileOutputStream fileOut = new FileOutputStream(FILE_DATA);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(save);
                out.close();
                fileOut.close();
            }
            catch (IOException i) {
                    i.printStackTrace();
                }
        }
    }
    
    public void user() {
        albumWin.setScene(albumWindow);
        album.initBeforeShow();
        albumWin.setTitle("Album " + save.getCurrentUser().getAlbum().getAlbumName());
        albumWin.show();
    }
    
    
    public static void adminLogin() {
        loginWin.setScene(adminWindow);
        admin.initBeforeShow();
        loginWin.setTitle("Welcome Admin");
        loginWin.show();
    }
    
    
     public static void admin() {
    	login();
    }
    
    
    
    public static void album() {
    	albumWin.hide();
    	login();
    }
    
    
    
    public static void otherUser() {
    	albumWin.hide();
    	login();
    }
    
     private static void login() {
    	loginWin.setScene(loginWindow);
        login.initBeforeShow();
        loginWin.setTitle("Welcome To Photos App!");
        loginWin.show();
		
    }

    
    public static void otherLogin() {
        loginWin.hide();
        mainUser();
    }

    
    public static void otherAdmin() {
        loginWin.hide();
        mainUser();
    }

    
    public static void otherAlbum() {
	mainUser();
    }

    private static void mainUser() {
        albumWin.setScene(userWindow);
        user.initBeforeShow();
        albumWin.setTitle("Welcome " + save.getCurrentUser().getUsername());
        albumWin.show();
    }
    
    
    
}
