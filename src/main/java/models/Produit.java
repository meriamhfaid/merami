package models;


import javafx.scene.image.Image;

import java.sql.Blob;

public class Produit {
    private int id_produit , Quantity, prix;
    private String nom, description ;

    private String image;

    public Produit() {

    }

    public Produit(int id_produit, int quantity, int prix, String nom, String description, String image) {
        this.id_produit = id_produit;
        Quantity = quantity;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public Produit(int quantity, int prix, String nom, String description, String image) {
        Quantity = quantity;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }


    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", Quantity=" + Quantity +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
