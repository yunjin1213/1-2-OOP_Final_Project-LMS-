package dao;

import model.MGangjwa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GangjwaDAO extends BaseDAO {

    // 특정 학과 ID에 속한 강좌 리스트 가져오기
	public List<MGangjwa> getGangjwasByDepartmentId(int departmentId) {
	    String query = "SELECT * FROM gangjwas WHERE department_id = ?";
	    List<MGangjwa> gangjwas = new ArrayList<>();

	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setInt(1, departmentId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                MGangjwa gangjwa = new MGangjwa();
	                gangjwa.setGangjwaId(resultSet.getInt("gangjwa_id"));
	                gangjwa.setCourseName(resultSet.getString("course_name"));
	                gangjwa.setInstructor(resultSet.getString("instructor"));
	                gangjwa.setCredit(resultSet.getInt("credit"));
	                gangjwa.setScheduleTime(resultSet.getString("schedule_time"));
	                gangjwas.add(gangjwa);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    System.out.println("DAO Fetched Gangjwas: " + gangjwas);
	    return gangjwas;
	}
}