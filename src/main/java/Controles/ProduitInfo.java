package Controles;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Produit;
import services.ProduitService;

public class ProduitInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Produit> tableViewProduits;

    @FXML
    private TableColumn<Produit, String> columnNom;

    @FXML
    private TableColumn<Produit, String> columnDescription;

    @FXML
    private TableColumn<Produit, Integer> columnPrix;

    @FXML
    private TableColumn<Produit, Integer> columnQuantite;

    @FXML
    private TableColumn<Produit, String> columnImage;

    @FXML
    void initialize() {
        List<Produit> produits = getProduits();
        initializeProduits(produits);
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        columnQuantite.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        columnImage.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<Produit, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        try {
                            URL imageUrl = new File(item).toURI().toURL();
                            Image image = new Image(imageUrl.toString());
                            imageView.setImage(image);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            setGraphic(imageView);
                        } catch (MalformedURLException e) {
                            System.err.println("Error loading image: " + e.getMessage());
                            setGraphic(null);
                        }
                    }
                }
            };
        });
    }

    public void initializeProduits(List<Produit> produits) {
        if (produits != null) {
            ObservableList<Produit> produitList = FXCollections.observableArrayList(produits);
            tableViewProduits.setItems(produitList);
        }
    }

    public List<Produit> getProduits() {
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
}


