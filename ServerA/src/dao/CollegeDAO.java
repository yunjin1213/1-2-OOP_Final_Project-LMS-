package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MCollege;

public class CollegeDAO extends BaseDAO {

    // 특정 캠퍼스 ID에 속한 대학 리스트 가져오기
    public List<MCollege> getCollegesByCampusId(int campusId) {
        String query = "SELECT * FROM colleges WHERE campus_id = ?";
        List<MCollege> colleges = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, campusId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MCollege college = new MCollege();
                college.setId(resultSet.getInt("college_id"));
                college.setName(resultSet.getString("college_name"));
                college.setCode(resultSet.getString("college_code"));
                college.setCampusId(resultSet.getInt("campus_id"));
                college.setMaxCredits(resultSet.getInt("max_credits")); // max_credits 추가
                colleges.add(college);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }

        return colleges;
    }
}