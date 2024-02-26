package tests;

import services.ProduitService;
import utils.MyDatabase;
import models.Produit;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        MyDatabase database = MyDatabase.getInstance();

        ProduitService ps = new ProduitService();
        try {


            ps.ajouter(new Produit(3, 17, "granula ", "granula 100%healthy", "src/assets/files/gran.png"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {

            ps.modifier(new Produit(3, 3, "granula ", "granula 100% healthy", "src/assets/files/gran.png"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {

           System.out.println( ps.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {

            ps.supprimer(3);;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
}
