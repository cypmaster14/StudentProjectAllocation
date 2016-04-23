package pachet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * A member of SPA Problem
 * He has a list of project that he supervised, a list of his student preferences and a list of student 
 * that were assing ro his projects
 * @author cipri_000
 *
 */
public class Lecturer extends Person {

	private int capacity;
	private int room;
	private List<Student> listOfStudentPreferences;
	private List<Project> listOfProjectSupervised;
	private List<Student> listOfStudentsSupervised;
	
	/**
	 * We read the personal information for a Lecturer
	 * @param i
	 * @param s
	 */
	public Lecturer(int i , Scanner s) {
		// TODO Auto-generated constructor stub
		listOfStudentPreferences=new ArrayList<Student>();
		listOfProjectSupervised=new ArrayList<Project>();
		listOfStudentsSupervised= new ArrayList<Student>();
		setId(i);
		this.capacity=s.nextInt();
		this.setNume(s.next());
		this.setEmail(s.next());
		this.setRoom(s.nextInt());
		s.nextLine();
	}
	
	
	
	/**
	 * We add a student to the teacher's list of preference
	 * @param s
	 */
	public void addToListOfStudentPreference(Student s)
	{
		listOfStudentPreferences.add(s);
	}
	
	/**
	 * We add a project to the list of Projects that are supervised by him
	 * @param p Specifies the project
	 */
	public void addToListOfProjectsSupervised(Project p)
	{
		listOfProjectSupervised.add(p);
	}
	
	/**
	 * After a Student was proviousli allocated to a lecturer , we add the student
	 *  to the list of students that are supervised by this lecturer
	 * @param s
	 */
	public void addToListOfStudentsSupervised(Student s)
	{
		listOfStudentsSupervised.add(s);
	}
	
	/**
	 * Get the number of the students that he prefers
	 * @return
	 */
	public int numberOfPreffredStudents()
	{
		return listOfStudentPreferences.size();
	}

	/**
	 * We get a student by his index
	 * @param index
	 * @return The student that has this index 
	 */
	Student accessStudByIndex(int index) {
        return listOfStudentPreferences.get(index);
    }
	
	/**
	 * Determinate if the lecturer is offering this project or not
	 * @param p The project that is verified
	 * @return Whether or not the project is offerd by this lecturer
	 */
	public boolean offersAnProject(Project p)
	{
		return listOfProjectSupervised.contains(p);
	}
	
	/**
	 * 
	 * @return The size of his list of Prefered Student
	 */
	public int  getNumberOfStudentsPrefered()
	{
		return this.listOfStudentPreferences.size();
	}
	
	/**
	 * 
	 * @return The number of students that he can supervise
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * We set the capacity of the lecturer
	 * @param capacity Maximum number of supervised students
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getRoom() {
		return room;
	}


	public void setRoom(int room) {
		this.room = room;
	}

	
	
	
	@Override
	public String toString() {
		String aux="";
		aux+="ID: "+this.getId()+"\n";
		aux+="Nume: "+this.getNume()+"\n";
		aux+="Email: "+this.getEmail()+"\n";
		aux+="Room: "+this.getRoom()+"\n";
		aux+="Capacity: "+this.getCapacity()+"\n";
		aux+="List of Project Supervised:";
		for(Project p:listOfProjectSupervised)
		{
			aux+="P"+p.returnID()+" ";
		}
		aux+="\nList of Student Preferences: " ;
		for(Student s:listOfStudentPreferences)
		{
			aux+=s.getId()+" ";
		}
		aux=aux+"\n";
		return aux+"\n";
	}

	/**
	 * We check if a lecturer can take any more students
	 */
	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		if(listOfProjectSupervised.size()<capacity)
			return true;
		
		return false;
	}
	
	
	/**
	 * After we found out that a student cannot have one of lecturer's project we remove him
	 * from the list of teacher's supervised students
	 * @param s
	 */
	public void breakProvisionalAssignment(Student s)
	{
		this.listOfStudentsSupervised.remove(s);
	}

	/**
	 * 
	 * @return Wheather or not the number of students supervised is bigger than his capacity
	 */
	public boolean isOverSubscribed()
	{
		if(listOfStudentsSupervised.size()>capacity)
			return true;
		else
			return false;
	}

	public boolean isFull()
	{
		if(listOfStudentsSupervised.size()==capacity)
			return true;
		else
			return false;
	}
	
	
	/**
	 * 
	 * @return The student that is supervised by him  that is the worst possitioned in teacher's preference list
	 */
	public Student returnWorstStuddentAssigned()
	{
		int i=-1;
		for(Student s: listOfStudentsSupervised)
		{
			if(listOfStudentPreferences.indexOf(s)>i)
				i=listOfStudentPreferences.indexOf(s);
		}
		return listOfStudentPreferences.get(i);
	}

	/**
	 * We retrive a student after his index
	 * @param s
	 * @return
	 */
	public int getIndexOfAStudent(Student s)
	{
		return listOfStudentPreferences.indexOf(s);
	}
	
	/**
	 * 
	 * @return The number of projects that are supervised by this lecturer
	 */
	public int getNumberOfProjectSupervised()
	{
		return listOfProjectSupervised.size();
	}

	/**
	 * 
	 * @param index
	 * @return The student that has this index
	 */
	public Student getStudentByIndex(int index)
	{
		return listOfStudentPreferences.get(index);
	}
	
	/**
	 * 
	 * @param index
	 * @return The project that has this index
	 */
	public Project getProjectByIndex(int index)
	{
		return listOfProjectSupervised.get(index);
	}
	
	
	public String getSatisfiability()
	{
		int average=0,nrOfStudens=listOfStudentsSupervised.size();
		if(nrOfStudens!=0)
		{
			for(int i=0;i<listOfStudentPreferences.size();i++)
			{
				if(listOfStudentsSupervised.contains(listOfStudentPreferences.get(i)))
				{
					int position=listOfStudentPreferences.size()-listOfStudentPreferences.indexOf(listOfStudentPreferences.get(i));
					average=average+((position*100)/listOfStudentPreferences.size());
					listOfStudentPreferences.remove(listOfStudentPreferences.get(i));
					i=-1;
				}
			}
			
			return " Grad multumuire: "+average/nrOfStudens+"%";
		}
		else
			return " Grad multumire: 0 %";
	}
	
	
}
