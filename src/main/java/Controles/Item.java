package Controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Produit;

import java.io.File;


public class Item {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(produit);
    }

    private Produit produit;
    private MyListener myListener;

    public void setData(Produit produit, MyListener myListener) {
        this.produit = produit;
        this.myListener = myListener;
        nameLabel.setText(produit.getNom());
        priceLable.setText(produit.getPrix() + Home.CURRENCY);

        // Assuming produit.getImage() returns the path string
        String imagePath = produit.getImage();
        try {
            // Assuming imagePath is a valid path to the image resource
            Image image = new Image(new File(imagePath).toURI().toString());
            img.setImage(image);
        } catch (Exception e) {
            // Handle the exception (e.g., log it or display an error message)
            e.printStackTrace();
        }
    }


}

