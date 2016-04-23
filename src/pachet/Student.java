package pachet;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * It's a type of person.
 * A part of SPA Problem.
 * @author cipri_000
 *
 */

public class Student extends Person {

	
	private int an;
	private List<Project> listOfPreferences;
	private List<Project> listOfPreferencesAux;
	private List<Integer> idProiecte;
	private Project projectAlocated;
	private Lecturer lecturerAlocated;
	boolean projectWasAllocated=false;
	private float satisfiability=0;
	
	public float getSatisfiability()
	{
		return this.satisfiability;
	}
	
	public void caulculateSatisfiabily()
	{
		float i=this.listOfPreferencesAux.size();
		for(Project p : listOfPreferencesAux)
		{
			if(projectAlocated.returnID()==p.returnID())
				break;
			i--;
		}
		
		satisfiability=(i/this.listOfPreferencesAux.size())*100;
	}
			
			
	
	/**
	 * Using the constructor we set Studen's personal information 
	 * @param id It is an unique identifier
	 * @param s Allow us to read the personal information from console
	 */
	public Student(int id,Scanner s) {
		// TODO Auto-generated constructor stub
		listOfPreferences=new ArrayList<Project>();
		listOfPreferencesAux=new ArrayList<Project>();
		idProiecte= new ArrayList<Integer>();
		setId(id);
		//Citim detele despre student
		String nume=s.next();
		this.setNume(nume);
		
		String email=s.next();
		this.setEmail(email);
		
		int an=s.nextInt();
		this.setAn(an);
		
		//Citim preferintele subiectului
		s.nextLine();
		String preferinte=s.nextLine();
		String totalPreferinte[]=preferinte.split(" ");
		//Adaugam fiecare preferinta in tabel dupa id
		for(String proiect: totalPreferinte)
		{
			idProiecte.add(Integer.valueOf(proiect));
		}
	}	
	
	/**
	 * Gives as student's current year of study
	 * @return
	 */
	public int getAn() {
		return an;
	}
	
	/**
	 * We set the student's current year of study
	 * @param an
	 */
	public void setAn(int an) {
		this.an = an;
	}

	/**
	 * We put the Project P into student's list of preferences
	 * @param p
	 */
	public void addToListOfPreferences(Project p)
	{
		listOfPreferences.add(p);
	}

	
	/**
	 * After we got a possible project for this student , we provisional allocate Project p to this student
	 * @param p Specifies the Project
	 */
	public void assignAProject(Project p)
	{
		projectAlocated=p;
		projectWasAllocated=true;
		p.assignStudentToAProject(this);
	}
	/**
	 * After we got a possible project for this student , we provisional allocate Lecturer to this student.
	 * And we add this student to the list of students that are supervised by Lecture l
	 * @param l
	 */
	public void assigaALecturer(Lecturer l)
	{
		lecturerAlocated=l;
		l.addToListOfStudentsSupervised(this);
	}

	/**
	 * Verify if student still has options and wasn't allocated to a project
	 */
	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return !projectWasAllocated && hasAnNonEmptyList() ;
	}
	
	/**
	 * Student still has options available
	 * @return
	 */
	public boolean hasAnNonEmptyList()
	{
		if(this.listOfPreferences.size()>0&& lecturerAlocated==null && projectAlocated==null )
			return true;
		return false;
	}

	/**
	 * We get the student's current first option
	 * @return
	 */
	public Project getStudentsFirstOption()
	{
		Project p=this.listOfPreferences.get(0);
		return p;
	}
	
	/**
	 * Get the number of his preferences
	 * @return
	 */
	public int numberOfPreferences()
	{
		return idProiecte.size();
	}
	
	/**
	 * We add Project P to his list of preferences
	 * @param p
	 */
	public void addProjectToListOfPreferences(Project p)
	{
		this.listOfPreferences.add(p);
		this.listOfPreferencesAux.add(p);
	}
	/**
	 * Using an index, we get the project according to the index
	 * @param index
	 * @return
	 */
	public int getProjectIdByIndex(int index) {
	        return idProiecte.get(index);
	    }
	
	
	/**
	 * Check if this student want's to take Project p
	 * @param p
	 * @return
	 */
	public boolean studentWantProject(Project p)
	{
		return listOfPreferences.contains(p);
	}
	
	
	@Override
	public String toString() {
		String afisare="";
		afisare+="Nume:"+this.getNume()+"\n";
		afisare+="Email:"+this.getEmail()+"\n";
		afisare+="ID:"+this.getId()+"\n";
		afisare+="Prefirinta proiecte:";
		for(Project p: listOfPreferences)
		{
			afisare+=p.toString();
		}
		afisare+="\n"+"\n";		
		
		return afisare;
	}
	
	
	/**
	 * Get the project that was alocated to this student
	 * @return
	 */
	public Project getProjectAllocated()
	{
		return this.projectAlocated;
	}

	/**
	 * We found out that is better clasified in Lecturer preferences
	 * and we take this project from this student.
	 * He has to do for the momment another project
	 */
	public void breakProvisionalAssignmet()
	{
		lecturerAlocated.breakProvisionalAssignment(this);
		projectAlocated.breakProvisionalAssignment(this);		
		projectAlocated=null;
		lecturerAlocated=null;
		projectWasAllocated=false;
	}

	/**
	 * 
	 * We found out that he cannot take Project P and 
	 * we remove Project P from his list of preferences
	 */
	public void removeProjectFromListOfPreference(Project p)
	{
		this.listOfPreferences.remove(p);
	}

}
