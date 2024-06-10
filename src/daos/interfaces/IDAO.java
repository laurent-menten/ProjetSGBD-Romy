package daos.interfaces;

import java.sql.SQLException;

public interface IDAO<T, K> {
    void create(T obj) throws SQLException;
    void update(T obj) throws SQLException;
    void delete(T obj) throws SQLException;
    void deleteById(K id) throws SQLException;
    T findById(K id) throws SQLException;
    T[] findAll() throws SQLException;
}
