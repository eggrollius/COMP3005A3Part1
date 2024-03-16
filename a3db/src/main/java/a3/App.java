package a3;
import java.sql.Date;
import java.util.Scanner;
import java.util.Collection;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/A3P1";
        String user = "postgres";
        String password = "1911peanut";

        IA3Database db = new PostgreSQLDatabase(url, user, password);
        if (!db.connect()) {
            System.out.println("Failed to connect to db");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Print all students");
            System.out.println("2. Add a new student");
            System.out.println("3. Change an email");
            System.out.println("4. Delete a student");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.println("----------------------------------------------------");

            switch (choice) {
                case 1:
                  { 
                    System.out.println("Printing all students: ");
                    Collection<Student> students = db.getAllStudents();
                    for (Student student : students) {
                        System.out.println(student.toString());
                    }
                    System.out.println("----------------------------------------------------");
                    break;
                  }

                case 2:
                  {
                    System.out.println("Enter first name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter last name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter birth date (YYYY-MM-DD):");
                    String birthDateString = scanner.nextLine();
                    Date birthDate = Date.valueOf(birthDateString);
                    db.addStudent(firstName, lastName, email, birthDate);
                    System.out.println("Student added successfully");
                    System.out.println("----------------------------------------------------");
                    break;
                  }

                case 3:
                    {
                      System.out.println("Enter the students id:");
                      int id = Integer.parseInt(scanner.nextLine());
                      System.out.println("Enter new email:");
                      String newEmail = scanner.nextLine();
                      
                      try {
                        if(db.updateStudentEmail(id, newEmail)) {
                          System.out.println("Email updated successfully");
                        }
                      } catch(Exception e) {
                         System.out.println("Duplicate email, no update performed");
                      }
 
                      System.out.println("----------------------------------------------------");
                      break;
                    }

                case 4:
                    {
                      System.out.println("Enter id of student to delete:");   
                      int id = Integer.parseInt(scanner.nextLine());
                      db.deleteStudent(id);
                      System.out.println("All students deleted successfully");
                      System.out.println("----------------------------------------------------");
                      break;
                    }
                case 5:
                    {
                      running = false;
                      System.out.println("Exiting...");
                      System.out.println("----------------------------------------------------");
                      break;
                    }

                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("----------------------------------------------------");
            }
        }

        scanner.close();
    }
}
