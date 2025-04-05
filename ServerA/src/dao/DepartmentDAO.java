package dao;

import model.MDepartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends BaseDAO {

	// 특정 대학 ID에 속한 학과 리스트 가져오기
	public List<MDepartment> getDepartmentsByCollegeId(int collegeId) {
		String query = "SELECT * FROM departments WHERE college_id = ?";
		List<MDepartment> departments = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, collegeId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				MDepartment department = new MDepartment();
				department.setId(resultSet.getInt("department_id"));
				department.setName(resultSet.getString("department_name"));
				department.setCode(resultSet.getString("department_code"));
				department.setCollegeId(resultSet.getInt("college_id"));
				departments.add(department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(connection, preparedStatement, resultSet);
		}

		return departments;
	}
}