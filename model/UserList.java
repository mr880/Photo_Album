/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mkasa
 */
public class UserList implements Comparable<UserList>, Serializable {
    
   
    public static final String ADMIN_USER = "admin";

    private static final long serialVersionUID = 1L;

   
    public void file(boolean save) {
        if (save) {
            listOfAlbumsToKeep = new ArrayList<>(listAlbums);
            listAlbums = null;
            //
            for (Album a : listOfAlbumsToKeep) {
                    a.file(true);
            }
        }
        else {
            listAlbums = FXCollections.observableList(listOfAlbumsToKeep);
            listOfAlbumsToKeep = null;
            //
            for (Album a : listAlbums) {
                    a.file(false);
            }
        }

    }

    
    private String username;

    private ObservableList<Album> listAlbums;

    
    private ArrayList<Album> listOfAlbumsToKeep;

    
    private Album currentAlbum;

  
    
    public UserList(String _user) {
        
        username = _user;
        listAlbums = FXCollections.observableArrayList();
        listOfAlbumsToKeep = null;
	currentAlbum = null;
        
	}


   
    public Album addAlbum(String username) {
        Album album = new Album(username);
        boolean isFound = IntStream.range(0, listAlbums.size())
        .anyMatch(i -> listAlbums.get(i).getAlbumName().equalsIgnoreCase(album.getAlbumName()));
        if (!isFound) {
            listAlbums.add(album);
            return album;
        }
        else 
        {
            return null;
        }
    }


    public void addOrOverwriteAlbum(Album album) {
        IntStream.range(0, listAlbums.size())
        .filter(i -> listAlbums.get(i).getAlbumName().equalsIgnoreCase(album.getAlbumName()))
        .findFirst()
        .ifPresent(i -> listAlbums.remove(i));
        listAlbums.add(album);
    }

   
    public void updateAlbumName(int index, String albumName) {
        if (index >= 0 && index < listAlbums.size()) {
            boolean isFound = IntStream.range(0, listAlbums.size())
            .filter(i -> i != index)
            .anyMatch(i -> listAlbums.get(i).getAlbumName().equalsIgnoreCase(albumName));
            if (!isFound) {
                listAlbums.get(index).setAlbumName(albumName);
            }
        }
}


    
    public Album getAlbum() {
		return currentAlbum;
	}

    
    public void setAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }


    
    public ObservableList<Album> getAlbums() {
        return listAlbums;
    }


    
    public String getUsername() {
	return username;
    }


    public Album deleteAlbum(String albumName) {
        return Operations.delete(listAlbums, albumName, (t,k)->t.getAlbumName().equalsIgnoreCase(k));
    }


    
    public Album getAlbum(String albumName) {
        return Operations.get(listAlbums, albumName, (t,k)->t.getAlbumName().equalsIgnoreCase(k));
    }


    
    @Override
    public String toString() {
        return username;
    }

    @Override
    public int compareTo(UserList arg0) {
        return username.compareToIgnoreCase(arg0.username);
    }

   
    
}
