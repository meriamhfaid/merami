package Controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Produit;
import services.ProduitService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Interface implements Initializable {
    public Button addToCartButton;
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<Produit> produits = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    public List<Produit> getData() {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.recuperer();
            System.out.println("Nombre de produits récupérés : " + produits.size());
            return produits;
        } catch (SQLException e) {
            // Handle any SQL exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération des produits : " + e.getMessage());
            alert.show();
            return null;
        }
    }

    public Produit getProduitById(int id) {
        ProduitService produitService = new ProduitService();
        try {
            Produit produit = produitService.recupererProduitParId(id);
            if (produit != null) {
                System.out.println("Produit récupéré : " + produit.getNom());
            } else {
                System.out.println("Aucun produit trouvé avec l'ID : " + id);
            }
            return produit;
        } catch (SQLException e) {
            // Handle any SQL exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération du produit : " + e.getMessage());
            alert.show();
            return null;
        }
    }

    private void setChosenFruit(Produit produit) {
        fruitNameLable.setText(produit.getNom());
        fruitPriceLabel.setText(produit.getPrix() + Home.CURRENCY);
        String imagePath = produit.getImage();

        // Assuming imagePath is a valid path to the image resource
        Image image = new Image(new File(imagePath).toURI().toString());
        fruitImg.setImage(image);
    }

    @FXML
    private void handleProduitSelection(ActionEvent event) {
        // Get the product ID from the event source (assuming it's a Button or similar)
        int selectedProductId = getProductIdFromEvent(event);

        Produit selectedProduct = getProduitById(selectedProductId);

        if (selectedProduct != null) {
            setChosenFruit(selectedProduct);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le produit n'a pas été trouvé.");
            alert.showAndWait();
        }
    }

    private int getProductIdFromEvent(ActionEvent event) {
        // Modify this logic based on how the ID is stored in your UI elements

        // Example assuming the ID is stored in a custom attribute of the event source:
        Button button = (Button) event.getSource();
        return Integer.parseInt(button.getUserData().toString());

        // Modify this based on your implementation. You might need to check for different types
        // of UI elements and extract the ID differently.
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produits.addAll(getData());
        if (!produits.isEmpty()) {
            setChosenFruit(produits.get(0));
            myListener = produit -> setChosenFruit(produit);
        }
        int column = 0;
        int row = 1;
        try {
            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                Item item = fxmlLoader.getController();
                item.setData(produit, myListener);

                // Create the "Ajouter au panier" button dynamically
                Button addToCartButton = new Button("Ajouter au panier");
                addToCartButton.setOnAction(this::handleAjouterAuPanier);
                addToCartButton.setUserData(produit.getId_produit()); // Set product ID as userData

                // Add other properties and styling to the button
                addToCartButton.setPrefWidth(270);
                addToCartButton.setPrefHeight(20);
                addToCartButton.getStyleClass().add("add-btn");
                addToCartButton.setFont(Font.font("System Bold", 18));
                addToCartButton.setTextFill(Color.web("#828282"));

                // Add the button to the AnchorPane
                anchorPane.getChildren().add(addToCartButton);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAjouterAuPanier(ActionEvent event) {
        Button button = (Button) event.getSource();
        int selectedProductId = (int) button.getUserData();

        Produit selectedProduct = getProduitById(selectedProductId);

        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            try {
                Parent root = loader.load();
                Panier panierController = loader.getController();
                panierController.initData(selectedProduct);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le produit n'a pas été trouvé.");
            alert.showAndWait();
        }
    }
}
