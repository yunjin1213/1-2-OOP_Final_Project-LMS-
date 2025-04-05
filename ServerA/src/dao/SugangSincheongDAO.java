package dao;

import model.MGangjwa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SugangSincheongDAO extends BaseDAO {

    // 기존 메서드 유지
    public void saveSugangSincheong(String userId, MGangjwa mGangjwa) {
        String query = "INSERT INTO sugangsincheong (userId, gangjwa_id, course_name, instructor, credit, schedule_time) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, mGangjwa.getGangjwaId());
            preparedStatement.setString(3, mGangjwa.getCourseName());
            preparedStatement.setString(4, mGangjwa.getInstructor());
            preparedStatement.setInt(5, mGangjwa.getCredit());
            preparedStatement.setString(6, mGangjwa.getScheduleTime());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 새로운 List<MGangjwa> 처리 메서드 추가
    public void saveSugangSincheong(String userId, List<MGangjwa> mGangjwas) {
        String deleteQuery = "DELETE FROM sugangsincheong WHERE userId = ?";
        String insertQuery = "INSERT INTO sugangsincheong (userId, gangjwa_id, course_name, instructor, credit, schedule_time) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            // 기존 데이터 삭제
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, userId);
                deleteStmt.executeUpdate();
                System.out.println("[DEBUG] 수강신청 기존 데이터 삭제 완료: " + userId);
            }

            // 새 데이터 저장
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                for (MGangjwa mGangjwa : mGangjwas) {
                    insertStmt.setString(1, userId);
                    insertStmt.setInt(2, mGangjwa.getGangjwaId());
                    insertStmt.setString(3, mGangjwa.getCourseName());
                    insertStmt.setString(4, mGangjwa.getInstructor());
                    insertStmt.setInt(5, mGangjwa.getCredit());
                    insertStmt.setString(6, mGangjwa.getScheduleTime());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
                System.out.println("[DEBUG] 수강신청 저장 완료: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MGangjwa> getSugangSincheong(String userId) {
        String query = "SELECT * FROM sugangsincheong WHERE userId = ?";
        List<MGangjwa> mGangjwas = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MGangjwa mGangjwa = new MGangjwa();
                    mGangjwa.setGangjwaId(resultSet.getInt("gangjwa_id"));
                    mGangjwa.setCourseName(resultSet.getString("course_name"));
                    mGangjwa.setInstructor(resultSet.getString("instructor"));
                    mGangjwa.setCredit(resultSet.getInt("credit"));
                    mGangjwa.setScheduleTime(resultSet.getString("schedule_time"));
                    mGangjwas.add(mGangjwa);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mGangjwas;
    }
}