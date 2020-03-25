package gursimar_hehar_a3;

/**
 *
 * @author Gursimar Singh Hehar
 * Represents a Course and their respective marks ,
 * 
 */
public class Course  {

    public double getGrades() {
        return grades;
    }

    public void setGrades(double grades) {
        this.grades = grades;
    }

    String courseName;
    double grades;

    public Course(String courseName, double grades) {
        this.grades=grades;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

 /**
   * 
   * @return toString representation of courseName and grades
   */
    @Override
    public String toString() {
        return courseName + " : " + grades;
    }

}
