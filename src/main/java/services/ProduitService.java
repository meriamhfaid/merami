package services;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Produit;


public class ProduitService implements IService<Produit>{
    private Connection connection;
    public ProduitService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Produit produit) throws SQLException {
        String req = "INSERT INTO produit(nom, description, Quantity, prix, image) VALUES ('" + produit.getNom() + "','"
                + produit.getDescription() + "','" + produit.getQuantity() + "','" + produit.getPrix() + "','" + produit.getImage() + "')";


        Statement statement = MyDatabase.getInstance().getConnection().createStatement();

        statement.executeUpdate(req);

        statement.close();
    }




    @Override

    public void modifier(Produit produit) throws SQLException {
        String sql = "UPDATE produit SET nom=?, description=?, Quantity=?, prix=?, image=? WHERE id_produit=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, produit.getNom());
        preparedStatement.setString(2, produit.getDescription());
        preparedStatement.setInt(3, produit.getQuantity());
        preparedStatement.setInt(4, produit.getPrix());
        preparedStatement.setString(5, produit.getImage());
        preparedStatement.setInt(6, produit.getId_produit());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }











    @Override
    public void supprimer(int id_produit) throws SQLException {
        String req ="DELETE FROM `produit` WHERE id_produit=?";


        PreparedStatement preparedStatement = connection.prepareStatement(req);

        preparedStatement.setInt(1, id_produit);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    @Override
    public List<Produit> recuperer() throws SQLException {
        String sql= "select * from produit";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List <Produit> list = new ArrayList<>();
        while (rs.next()){
            Produit p = new Produit();
            p. setId_produit(rs.getInt("id_produit"));
            p. setQuantity(rs.getInt("Quantity"));
            p. setPrix(rs.getInt("prix"));
            p. setDescription(rs.getString("description"));
            p. setNom(rs.getString("nom"));
            p.setImage(rs.getString("image"));
            list.add(p);

        }
        System.out.println("Nombre de produits récupérés : " + list.size());
        return list ;
    }

    @Override
    public Produit recupererProduitParId(int id) throws SQLException {
        String sql = "SELECT * FROM produit WHERE id_produit = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        Produit produit = null;
        if (rs.next()) {
            produit = new Produit();
            produit.setId_produit(rs.getInt("id_produit"));
            produit.setQuantity(rs.getInt("Quantity"));
            produit.setPrix(rs.getInt("prix"));
            produit.setDescription(rs.getString("description"));
            produit.setNom(rs.getString("nom"));
            produit.setImage(rs.getString("image"));
        }
        return produit;
    }




}
