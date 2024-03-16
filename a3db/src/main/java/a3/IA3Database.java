package a3;
import java.sql.Date;
import java.util.Collection;

public interface IA3Database {
  public boolean connect();
  public boolean disconnect();
  public Collection<Student> getAllStudents(); 
  public boolean addStudent(String first_name, String last_name, String email, Date enrollment_date);
  public boolean updateStudentEmail(int student_id, String new_email);
  public boolean deleteStudent(int student_id);
}
