package a3;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Student {
  protected int student_id;
  protected String first_name;
  protected String last_name;
  protected String email;
  protected Date enrollment_date;

  public Student(int student_id, String first_name, String last_name, String email, Date enrollment_date) {
    this.student_id = student_id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.enrollment_date = enrollment_date;
  }

  int getStudentId() {
    return this.student_id;
  }

  @Override
  public String toString() {
    return String.format(
      "id: %d\n name: %s %s\n email: %s\n Enrollment Date: %s",
       this.student_id, this.first_name, this.last_name,
        this.email, this.enrollment_date.toString());
  }
}
