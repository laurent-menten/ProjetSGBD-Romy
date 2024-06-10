package daos.daosMariaDb;

import beans.Actor;
import daos.BaseActorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ActorDAO  extends BaseActorDAO {
    public final String tableName = "actors";
    public final String keyColumnName = "id";
    public final String firstnameColumnName = "firstname";
    public final String lastnameColumnName = "lastname";
    public final String birthdateColumnName = "birthdate";

    public ActorDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Actor obj) throws SQLException {
        String rq = String.format("INSERT INTO %s  VALUES(?,?,?,?);", tableName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getId());
        ps.setObject(2, obj.getFirstname());
        ps.setObject(3, obj.getLastname());
        ps.setObject(4, obj.getBirthdate());
        int result = ps.executeUpdate();
    }

    @Override
    public void update(Actor obj) throws SQLException {
        String rq = String.format("UPDATE %s  SET %s = ?, %s = ? WHERE %s = ?", tableName, firstnameColumnName, lastnameColumnName, birthdateColumnName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getId());
        ps.setObject(2, obj.getFirstname());
        ps.setObject(3, obj.getLastname());
        ps.setObject(4, obj.getBirthdate());
        int result = ps.executeUpdate();
    }

    @Override
    public void delete(Actor obj) throws SQLException {
        deleteById(obj.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String rq = String.format("DELETE FROM %s WHERE %s = ?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        int result = ps.executeUpdate();
    }

    @Override
    public Actor findById(Integer id) throws SQLException {
        String rq = String.format("SELECT * FROM %s WHERE %s=?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();  // se placer sur la 1ère ligne si elle existe
        return rsLineToObj(rs);
    }

    @Override
    public Actor[] findAll() throws SQLException {
        String rq = String.format("SELECT * FROM %s;", tableName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ResultSet rs = ps.executeQuery();

        List<Actor> lst = new LinkedList<>();
        while (rs.next()) {
            lst.add(rsLineToObj(rs));
        }

        return lst.toArray(Actor[]::new);
    }

    @Override
    public Actor rsLineToObj(ResultSet rs) throws SQLException {
        if (rs.getRow() == 0) return null;
        return new Actor(
                rs.getInt("id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                LocalDate.parse(rs.getDate("birthdate").toString()));
    }
}