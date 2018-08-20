package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.Serializable;
import java.util.ArrayList;

public class Admin implements Serializable {

    
   private static final long serialVersionUID = 1L;
    private ObservableList<UserList> listUsers;
    private ArrayList<UserList> listOfUsersToKeep;
    private UserList currentUser;

   
   
    public void file(boolean save) {
        if (save) {
            listOfUsersToKeep = new ArrayList<>(listUsers);
            listUsers = null;
            for (UserList u : listOfUsersToKeep) {
                u.file(true);
            }
        }
        else {
            listUsers = FXCollections.observableList(listOfUsersToKeep);
            listOfUsersToKeep = null;
            for (UserList u : listUsers) {
                u.file(false);
            }
        }
    }


    
    public Admin() {
        listUsers = FXCollections.observableArrayList();
        listOfUsersToKeep = null;
        currentUser = null;
    }

    public ObservableList<UserList> getListUsers() {
        return listUsers;
    }

    public UserList getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserList currentUser) {
        this.currentUser = currentUser;
    }


    
    public void addUser(String userName) {
        UserList user = new UserList(userName);
        //
        Operations.addOrRetrieveOrderedList(listUsers, user);
    }



    public UserList deleteUser(String userName) {
        if (!userName.equalsIgnoreCase(UserList.ADMIN_USER)) {
            return Operations.delete(listUsers, userName, (t,k)->t.getUsername().equalsIgnoreCase(k));
        }
        else {
            return null;
        }
    }


    public void deleteUser(int i) {
        if (i>=0 && i<listUsers.size()) {
            if (!listUsers.get(i).getUsername().equalsIgnoreCase(UserList.ADMIN_USER)) {
                listUsers.remove(i);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Deleting Admin.");
                alert.setContentText("Admin cannot be deleted.");
                alert.showAndWait();
            }
        }
    }

    public UserList getUser(String username) {
        return listUsers.stream().filter(user->user.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }
}