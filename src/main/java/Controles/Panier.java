package Controles;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Produit;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Panier {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label productNameLabel;




    @FXML
    private ImageView productImageView;

    private Produit selectedProduct;

    public void initData(Produit product) {
        this.selectedProduct = product;
        if (product != null) {
            productNameLabel.setText(product.getNom());
            String imagePath = product.getImage();
            Image image = new Image(new File(imagePath).toURI().toString());
            productImageView.setImage(image);
        }
    }

}
