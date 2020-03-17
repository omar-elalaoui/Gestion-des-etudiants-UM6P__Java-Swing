package dao;

import connexion.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import model.Etudiant;
import frames.Main_frame;

public class Etudiant_DAO {
    private final Connexion con= new Connexion();
    private final Connection connection= con.getConnection();
    private PreparedStatement ps=null;
    
    
    public final String LIST_ALL="select * from etudiant";
    public final String ADD_ETUDIANT="insert into etudiant(id, prenom, nom, age, master) values(?,?,?,?,?)";
    public final String UPDATE_ETUDIANT="update etudiant set prenom=?, nom=?, age=?, master=? where id=?";
    
    
    public void lister_all(){
        DefaultTableModel model= (DefaultTableModel)Main_frame.jTable1.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
        
        String[] etudiant_str= new String[5];
        try {
            Statement stm= connection.createStatement();
            ResultSet rs= stm.executeQuery(LIST_ALL);
            while(rs.next()){
                etudiant_str[0]=rs.getString("id");
                etudiant_str[1]=rs.getString("prenom");
                etudiant_str[2]=rs.getString("nom");
                etudiant_str[3]=rs.getString("age");
                etudiant_str[4]=rs.getString("master");
                model.addRow(etudiant_str);
            }
            //add
        } catch (SQLException ex) {
            System.out.println("lister problem ");;
        }
    }
    
    
    public void chercher(String str){
        if(str.equals("")){
            lister_all();
            return;
        }
        DefaultTableModel model= (DefaultTableModel)Main_frame.jTable1.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
        
        String[] etudiant_str= new String[5];
        try {
            Statement stm= connection.createStatement();
            ResultSet rs= stm.executeQuery("select * from etudiant where nom='"+str+"' or prenom='"+str+"' or age='"+str+"' or master='"+str+"'");
            while(rs.next()){
                etudiant_str[0]=rs.getString("id");
                etudiant_str[1]=rs.getString("prenom");
                etudiant_str[2]=rs.getString("nom");
                etudiant_str[3]=rs.getString("age");
                etudiant_str[4]=rs.getString("master");
                model.addRow(etudiant_str);
            }
            //add
        } catch (SQLException ex) {
            System.out.println("chercher___ problem ");;
        }
    }
    
    public int extraireIdMAX(){
        
        int id=0;
        try {
            Statement stm= connection.createStatement();
            ResultSet rs= stm.executeQuery("select MAX(id) as id from etudiant");
            if(rs.next()){
                id= rs.getInt("id");
            }
            id++;
            return id;
        } catch (SQLException ex) {
            System.out.println("lister problem ");
            return 0;
        }
        
    }
    
    public boolean ajouter(Etudiant e){
        try {
            ps= connection.prepareStatement(ADD_ETUDIANT);
            ps.setInt(1, e.getId());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getNom());
            ps.setString(4, e.getAge());
            ps.setString(5, e.getMaster());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("ajouter problem ");;
        }
        return false;
    }
    
    public boolean modifier(Etudiant e){
        try {
            ps= connection.prepareStatement(UPDATE_ETUDIANT);
            ps.setString(1, e.getPrenom());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getAge());
            ps.setString(4, e.getMaster());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("modifier problem ");;
        }
        return false;
    }
    
    public boolean supprimer(String id){
        try {
            ps= connection.prepareStatement("delete from etudiant where id=?");
            ps.setString(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("supp problem ");;
        }
        return false;
    }
}
