package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import valueObject.VLogin;
import valueObject.VUser;

public class LoginDAO extends BaseDAO {

	// 사용자 인증 메서드
	public boolean validateLogin(String userId, String password) {
		String query = "SELECT COUNT(*) FROM users WHERE userId = ? AND password = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next() && resultSet.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 사용자 세부 정보 가져오기
	public VUser getUserDetails(String userId) {
		String query = "SELECT userId, name, address, email, campus_id, college_id, department_id, max_credits, current_credits "
				+ "FROM user_details WHERE userId = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new VUser(resultSet.getString("userId"), resultSet.getString("name"),
							resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("campus_id"),
							resultSet.getInt("college_id"), resultSet.getInt("department_id"),
							resultSet.getInt("max_credits"), resultSet.getInt("current_credits"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ID 중복 확인
	public boolean isUserIdDuplicate(String userId) {
		String query = "SELECT COUNT(*) FROM users WHERE userId = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next() && resultSet.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 회원가입 SQL 수행 메서드
	public boolean registerUser(VUser user, VLogin login) {
		String userQuery = "INSERT INTO users (userId, password) VALUES (?, ?)";
		String detailsQuery = "INSERT INTO user_details (userId, name, address, email, campus_id, college_id, department_id, max_credits, current_credits) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";

		try (Connection connection = getConnection()) {
			try (PreparedStatement userStmt = connection.prepareStatement(userQuery);
					PreparedStatement detailsStmt = connection.prepareStatement(detailsQuery)) {

				connection.setAutoCommit(false);

				// 사용자 등록
				userStmt.setString(1, login.getUserId());
				userStmt.setString(2, login.getPassword());
				userStmt.executeUpdate();

				// 사용자 세부 정보 등록
				detailsStmt.setString(1, user.getUserId());
				detailsStmt.setString(2, user.getName());
				detailsStmt.setString(3, user.getAddress());
				detailsStmt.setString(4, user.getEmail());
				detailsStmt.setInt(5, user.getCampusId());
				detailsStmt.setInt(6, user.getCollegeId());
				detailsStmt.setInt(7, user.getDepartmentId());
				detailsStmt.setInt(8, user.getMaxCredits());
				detailsStmt.executeUpdate();

				connection.commit();
				return true;
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 사용자 ID 찾기 메서드
	public String findUserId(String name, String email) {
		String query = "SELECT userId FROM user_details WHERE name = ? AND email = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("userId");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 비밀번호 찾기 메서드 추가
	public String findPassword(String userId, String email) {
		String query = "SELECT u.password FROM users u JOIN user_details d ON u.userId = d.userId "
				+ "WHERE u.userId = ? AND d.email = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, email);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("password"); // 비밀번호 반환
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // 일치하는 사용자가 없는 경우
	}
}