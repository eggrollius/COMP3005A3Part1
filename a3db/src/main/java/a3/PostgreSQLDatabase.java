package a3;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PostgreSQLDatabase implements IA3Database {
  private final String url;
  private final String user;
  private final String password;
  private Connection conn;

  public PostgreSQLDatabase(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public boolean connect() {
    try {
      Class.forName("org.postgresql.Driver");
      this.conn = DriverManager.getConnection(this.url, this.user, this.password);
      if (this.conn == null) {
        System.out.println("Failed to establish connection with the db.");
        return false;
      }
      return true;
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean disconnect() {
    try {
      if(this.conn != null) {
        this.conn.close();
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Collection<Student> getAllStudents() {
    try{
      Statement stmt = conn.createStatement();
      String SQL = "SELECT * FROM students";
      ResultSet rs = stmt.executeQuery(SQL);

      Collection<Student> results = new ArrayList<>();
      while(rs.next()) {
        Student currentStudent;
        int studentId = rs.getInt("student_id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String email = rs.getString("email");
        Date enrollment_date = rs.getDate("enrollment_date");
        currentStudent = new Student(studentId, first_name, last_name, email, enrollment_date);
        results.add(currentStudent);
      }
      return results;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean addStudent(String first_name, String last_name, String email, Date enrollment_date) {
    String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
      pstmt.setString(1, first_name);
      pstmt.setString(2, last_name);
      pstmt.setString(3, email);
      pstmt.setDate(4, enrollment_date);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
        if (e.getSQLState().equals("23505")) {
            // SQLState "23505" indicates a unique constraint violation
            System.out.println("email must be unique");
        } else {
            throw new RuntimeException("An unexpected database error occurred.", e);
        }
      e.printStackTrace();
      return false;
    }
  }

  public boolean updateStudentEmail(int student_id, String new_email) {
    String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
      pstmt.setString(1, new_email);
      pstmt.setInt(2, student_id);
      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected == 1;
    } catch (SQLException e) {
        if (e.getSQLState().equals("23505")) {
            // SQLState "23505" indicates a unique constraint violation
            System.out.println("email must be unique");
        } else {
            throw new RuntimeException("An unexpected database error occurred.", e);
        }
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteStudent(int student_id) {
    String deleteSQL = "DELETE FROM students WHERE student_id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
      pstmt.setInt(1, student_id);
      pstmt.executeUpdate();
      return true;
    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}