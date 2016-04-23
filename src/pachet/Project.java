package pachet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reprezents the core of the SPA Problem
 * @author cipri_000
 *
 */
public class Project {
	
	private int capacity;
	private String descriere;
    private ArrayList<Student> subscribedStudents = new ArrayList<Student>();
    private int idProiect;
    private Lecturer lecturer = null;
    private ArrayList<Student> studentsInTeacherPreferenceOrder = new ArrayList<Student>();

    
    
    
    
    /**
     * We read information about the project
     * @param id Identifies unique an project
     * @param s Tool that allow us to read date from console
     */
    Project(int id,Scanner s)
    {
    	this.idProiect=id;
    	capacity=s.nextInt();
    	descriere=s.next();
    }
    
    
    
    /**
     * 
     * @return The description of the project
     */
    public String getDescriere() {
		return descriere;
	}

    /**
     * 
     * @return The lecturer that supervised this project
     */
	public Lecturer getLecturer() {
		return lecturer;
	}



	/**
	 * 
	 * @return The unique id of the project
	 */
	public int returnID()
    {
    	return idProiect;
    }
	
    /**
     * We set the lecturer who supervised the project
     * @param l
     */
    public void setLecturer(Lecturer l)
    {
    	this.lecturer=l;
    }
    
    /**
     * We add the student that is prefered by the teacher that supervised the project 
     * @param s A student
     */
    public void addPreferedStudent(Student s)
    {
    	this.studentsInTeacherPreferenceOrder.add(s);
    }
    
    /**
     * Add a student to the list of students that are subsribed to this project
     * @param s
     */
    public void addSubscribedStudent(Student s)
    {
    	this.subscribedStudents.add(s);
    }
    
    public String toString() {
    	return "P"+idProiect+" "+descriere+"\n";
    }
    
    
    public boolean equals(Object obj)
    {
    	if(this.returnID()==((Project)obj).returnID())
    		return true;
    	return false;
    }
    
    /**
     * Add the paramter s to the list of students that are allocated to this project
     * @param s
     */
    public void assignStudentToAProject(Student s)
    {
    	subscribedStudents.add(s);
    }
    
    /**
     * 
     * @return Whether or not the number of students that are allocated to this project is bigger that the project capacity 
     */
    public boolean isOverSubscribed()
    {
    	if(this.subscribedStudents.size()>capacity)
    		return true;
    	return false;
    }
    
    /**
     * 
     * @return Weather or not the capacity of the project is reached
     */
    public boolean isFull()
    {
    	if(this.subscribedStudents.size()==capacity)
    		return true;
    	return false;
    }
    
    /**
     * 
     * @return The Student that is the worst positioned in the list of this project of the Lecturer
     */
    public Student getWorstSubscribedStudent()
    {
    	int i=-1;
    	for(Student s:subscribedStudents)
    	{
    		if(studentsInTeacherPreferenceOrder.indexOf(s)>i)
    			i=studentsInTeacherPreferenceOrder.indexOf(s);
    		
    	}
    	return studentsInTeacherPreferenceOrder.get(i);
    }

    /**
	 * We retrive a student after his index
	 * @param s
	 * @return
	 */
    public int  getIndexOfAStudent(Student s)
    {
    	return studentsInTeacherPreferenceOrder.indexOf(s);
    }
    
    /**
     * 
     * @return The number of prefered student by the lecturer who supervised this project
     */
    public int getNumberOfPrefferdStudents()
    {
    	return studentsInTeacherPreferenceOrder.size();
    }

    /**
	 * 
	 * @param index
	 * @return The student that has this index
	 */
    public Student getStudentByIndex(int index)
    {
    	return studentsInTeacherPreferenceOrder.get(index);
    }
    
    /**
	 * After we found out that a student cannot have this project we remove him
	 * from the list of students that are subscribed to this project
	 * @param s
	 */
    public void breakProvisionalAssignment(Student s)
    {
    	subscribedStudents.remove(s);
    }

}
