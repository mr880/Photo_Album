/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;



import static controller.MainController.getModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Admin;
import model.Album;
import model.UserList;



/**
 * 
 *
 * @author mkasa
 */
public class UserController extends MainController implements EventHandler<MouseEvent>, Interface, ChangeListener<Album>  {

    
    @FXML TableView<Album> table;
    @FXML TableColumn albumNameColumn;
    @FXML TableColumn colphotoCount;
    @FXML TextField newAlbumName;

   
    public void initBeforeShow() {
    	UserList user = getModel().getCurrentUser();
    	ObservableList<Album> albumList = user.getAlbums();
    	
    	table.setItems(albumList);
        if (user.getAlbums().size() > 0) {
            table.getSelectionModel().select(0);
        }
        table.refresh();
    }

	
    public UserController() {
    }


    
    @FXML
    public void initialize() {
    	table.setEditable(true);
    	//
    	albumNameColumn.setCellFactory(TextFieldTableCell.<Album>forTableColumn());
    	albumNameColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Album, String>>() {
                @Override
                public void handle(CellEditEvent<Album, String> t) {
                    String newAlbumName = t.getNewValue().trim();
                    if (newAlbumName.length()>0) {
                        UserList user = getModel().getCurrentUser();
                        int i = table.getSelectionModel().getSelectedIndex();//
                        user.updateAlbumName(i, newAlbumName);
                    }
                    table.refresh();
                }
            }
        );
    	table.setRowFactory(tableView -> {
            final TableRow<Album> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem removeMenuItem = new MenuItem("Remove");
            removeMenuItem.setOnAction(event -> table.getItems().remove(row.getItem()));
            contextMenu.getItems().add(removeMenuItem);
           // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                    .then((ContextMenu)null)
                    .otherwise(contextMenu)
            );
            return row ;
        });
 
    	table.getSelectionModel().selectedItemProperty().addListener(this);
    	table.setOnMouseClicked(event -> {
            if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 1) 
            {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    otherUser();
                }
            }
            else {

            }
        });
    	
    }

    
    
    @Override
    public void handle(MouseEvent arg0) {
    }


    

    /**
     * Adds a new album
     */
    public void doAddNewAlbum() {
        UserList user = getModel().getCurrentUser();
        //
        String album_name = newAlbumName.getText().trim();
        if (album_name.length()>0) {
                user.addAlbum(album_name);
        table.refresh();
                
            newAlbumName.setText("");
        }
    }

    
    
    @Override
    public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
        Admin save = getModel();
        UserList user = save.getCurrentUser();
        //
        if (newValue!=null) {
            user.setAlbum(newValue);
        }
    }



}