package daos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAO<T, K> {

    protected final Connection conn;
    public DAO(Connection connection) {
        this.conn = connection;
    }

    public abstract void create(T obj) throws SQLException;
    public abstract void update(T obj) throws SQLException;
    public abstract void delete(T obj) throws SQLException;
    public abstract void deleteById(K id) throws SQLException;
    public abstract T findById(K id) throws SQLException;
    public abstract T[] findAll() throws SQLException;

    public abstract T rsLineToObj(ResultSet rs) throws SQLException;
}
