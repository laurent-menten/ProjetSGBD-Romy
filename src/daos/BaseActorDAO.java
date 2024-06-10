package daos;

import beans.Actor;

import java.sql.Connection;

public abstract class BaseActorDAO extends DAO<Actor, Integer> {

    public BaseActorDAO(Connection connection) {
        super(connection);
    }
}
