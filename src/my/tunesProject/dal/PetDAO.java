package my.tunesProject.dal;

import my.tunesProject.exceptions.PetShopException;
import my.tunesProject.be.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO implements IPetDAO{

    private final ConnectionManager cm = new ConnectionManager();

    @Override
    public Pet getPet(int id) {
        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Pets WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int pid          = rs.getInt("id");
                String name     = rs.getString("name");

                Pet p = new Pet(pid, name);
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePet(int id) {
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM pets WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePet(Pet p) {
        try(Connection con = cm.getConnection())
        {
            String sql = "UPDATE pets SET name=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getId());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPet(Pet p) {
        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO pets(name) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, p.getName());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();

        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Pets";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id          = rs.getInt("id");
                String name     = rs.getString("name");

                Pet p = new Pet(id, name);
                pets.add(p);
            }
            return pets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pet> getPetsFromOwner(int ownerId) throws PetShopException {
        List<Pet> pets = new ArrayList<>();

        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Pets WHERE ownerId=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id          = rs.getInt("id");
                String name     = rs.getString("name");

                Pet p = new Pet(id, name);
                pets.add(p);
            }
            return pets;
        } catch (SQLException e) {
            throw new PetShopException("Problems with the database or database connection",e);
        }
    }
}
