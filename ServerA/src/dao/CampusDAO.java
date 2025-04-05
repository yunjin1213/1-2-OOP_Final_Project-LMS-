package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MCampus;

public class CampusDAO extends BaseDAO {
    
    // 모든 캠퍼스 가져오기
    public List<MCampus> getAllCampuses() {
        String query = "SELECT * FROM campuses";
        List<MCampus> campuses = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MCampus campus = new MCampus();
                campus.setId(resultSet.getInt("campus_id"));
                campus.setName(resultSet.getString("campus_name"));
                campus.setCode(resultSet.getString("campus_code"));
                campuses.add(campus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }

        return campuses;
    }

    // 특정 캠퍼스 ID로 가져오기
    public MCampus getCampusById(int campusId) {
        String query = "SELECT * FROM campuses WHERE campus_id = ?";
        MCampus campus = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, campusId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                campus = new MCampus();
                campus.setId(resultSet.getInt("campus_id"));
                campus.setName(resultSet.getString("campus_name"));
                campus.setCode(resultSet.getString("campus_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }

        return campus;
    }
}