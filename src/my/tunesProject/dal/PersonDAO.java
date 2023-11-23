package my.tunesProject.dal;

import my.tunesProject.be.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements IPersonDAO {
    private final ConnectionManager cm = new ConnectionManager();


    @Override
    public Person getPerson(int id) {
        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Persons WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int pid          = rs.getInt("id");
                String name     = rs.getString("name");
                String email    = rs.getString("email");

                Person p = new Person(pid, name, email);
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerson(int id) {
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM persons WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePerson(Person p) {
        try(Connection con = cm.getConnection())
        {
            String sql = "UPDATE persons SET name=?, email=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getEmail());
            pstmt.setInt(3, p.getId());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPerson(Person p) {
        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO persons(name, email) VALUES (?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getEmail());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Person> getAllPersons() {

        List<Person> persons = new ArrayList<>();

        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Persons";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id          = rs.getInt("id");
                String name     = rs.getString("name");
                String email    = rs.getString("email");

                Person p = new Person(id, name, email);
                persons.add(p);
            }
            return persons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
