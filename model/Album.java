package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;


public class Album implements Comparable<Album>, Serializable {

	
    private static final long serialVersionUID = 1L;
    private String albumNameToKeep;
    private  SimpleStringProperty albumName;
    private  SimpleIntegerProperty PhotoNum;
    private int indexCurrentPhoto;
    
    
    public String getAlbumName() {
        return albumName.get();
    }
    
    
    public void setAlbumName(String albumName) {
        this.albumName.set(albumName);
    }
    
    public int getPhotoCount() {
        return PhotoNum.get();
    }

    public void setPhotoCount(int photoCount) {
        this.PhotoNum.set(photoCount);
    }

  

   
    public void file(boolean save) {
        if (save) {
            albumNameToKeep = getAlbumName();
            albumName = null;
            PhotoNum 	= null;
        }
        else 
        {
            albumName = new SimpleStringProperty(albumNameToKeep);
            PhotoNum = new SimpleIntegerProperty(); //
            albumNameToKeep = null;
        }
     
    }

    
    public Album(String _albumName) {
        albumName = new SimpleStringProperty(_albumName);
        PhotoNum = new SimpleIntegerProperty(0);
        indexCurrentPhoto = -1;
    }


    @Override
    public int compareTo(Album album) {
            return getAlbumName().compareToIgnoreCase(album.getAlbumName());
    }

    @Override
    public String toString() {
            return getAlbumName();
    }


}