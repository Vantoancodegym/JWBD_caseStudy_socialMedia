package service.relationshipService;

import model.RelationShip;
import storage.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RelationshipService {
    private static final String INSERT_RELATIONSHIP ="insert into relationship values (?,?,?,?);" ;
    private static final String UPDATE_RELATIVE = "update relationship set relative_status_id = ? where id = ?;";

    public int createRelative(RelationShip relationShip) {
        int rowEffect = 0;
        Connection connection = GetConnection.getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RELATIONSHIP);
            preparedStatement.setInt(1, relationShip.getId());
            preparedStatement.setInt(2, relationShip.getUser_id());
            preparedStatement.setInt(3, relationShip.getFriend_id());
            preparedStatement.setInt(4, relationShip.getRelative_status_id());
            rowEffect = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowEffect;
    }
    public int editRelationship(int relativeId, int id) {
        int rowEffect = 0;
        Connection connection = GetConnection.getConnetion();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RELATIVE);
            preparedStatement.setInt(1,relativeId);
            preparedStatement.setInt(2,id);
            rowEffect = preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rowEffect;
    }
}
