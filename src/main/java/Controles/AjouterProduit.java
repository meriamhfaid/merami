

package Controles;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Produit;
import services.ProduitService;



public class AjouterProduit {

    @FXML
    private TextField nomtextfield;

    @FXML
    private TextField prixtextfield;

    @FXML
    private Spinner<Integer> quantitytextfield;

    @FXML
    private TextField descriptiontextfield;

    @FXML
    private ImageView imagetextfield;
    ProduitService produitService = new ProduitService();

    @FXML
    void Ajouter(ActionEvent event) {
        String imagePath = "assets/fles/biosel.png";
        Produit produit = new Produit(quantitytextfield.getValue(), Integer.parseInt(prixtextfield.getText()),
                nomtextfield.getText(), descriptiontextfield.getText(),imagePath);

        try {
            produitService.ajouter(produit);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le produit a été ajouté");

            alert.setOnCloseRequest(e -> afficherProduitInfo());
            alert.show();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    private void afficherProduitInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitInfo.fxml"));
            Parent root = loader.load();
            ProduitInfo produitInfo = loader.getController();
            List<Produit> produits = produitService.recuperer();
            produitInfo.initializeProduits(produits);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitytextfield.setValueFactory(valueFactory);


        URL imageUrl = null;
        try {
            imageUrl = new File("assets/fles/biosel.png").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("failed to load the image");
        }
        Image image = new Image(imageUrl.toString());
        imagetextfield.setImage(image);




    }

    }
