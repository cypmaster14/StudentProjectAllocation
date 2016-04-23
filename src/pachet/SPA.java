package pachet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * The class that read the instance of the problem
 * Solves the SPA Problem
 * @author cipri_000
 *
 */
public class SPA {
	
	List<Student> listOfStudents;
	List<Project> listOfProjects;
	List<Lecturer> listOfLecturers;
	
	/**
	 * Instanciate the atributes of the class and reads the input
	 */
	SPA()
	{
		listOfLecturers= new ArrayList<Lecturer>();
		listOfProjects=  new ArrayList<Project>();
		listOfStudents= new ArrayList<Student>();
		try
		{
			Scanner s = new Scanner(new BufferedReader(new FileReader("in.txt")));
			//Citim numarul de studenti
			int numberOfStudents=s.nextInt();
			for(int i=1;i<=numberOfStudents;i++)
			{
				listOfStudents.add(new Student(i,s));
			}
			
			//Citim proiectele
			int numberOfProjects=s.nextInt();
			for(int i=1;i<=numberOfProjects;i++)
			{
				listOfProjects.add(new Project(i,s));
			}
			
			//Introducem proiectele in lista de proiecte a fiecarui student
			for(Student st:listOfStudents)
			{
				for(int i=0;i<st.numberOfPreferences();i++)
				{
					st.addProjectToListOfPreferences(findProjectByID(st.getProjectIdByIndex(i)));
				}
			}
				
			
			
			////Citim profesorii
			int numberOfLectures=s.nextInt();
			for(int i=0;i<numberOfLectures;i++)
			{
				listOfLecturers.add(new Lecturer(i+1,s));
				//Citim preferintele profesorului pt studenti
				String studentPreferences=s.nextLine();
				String[] listaPreferinte=studentPreferences.split(" ");
				for(String aux:listaPreferinte)
				{
					//adaugam la lector , la lista sa de preferinte studentul specifiat
					listOfLecturers.get(i).addToListOfStudentPreference(findStudentByID(Integer.valueOf(aux)));
				}
				
				//Citesc proiectele pe care lectorul le ofere
				String listOfOfferedProject=s.nextLine();
				String listaProiecte[]=listOfOfferedProject.split(" ");
				for(String aux:listaProiecte)
				{
					Project p=findProjectByID(Integer.valueOf(aux));
					listOfLecturers.get(i).addToListOfProjectsSupervised(p);
					p.setLecturer(listOfLecturers.get(i));
					
					//Formam lista de preferinte a fiecarui projesor pt fiecare proiect supervisat de el
					for(int k=0;k<listOfLecturers.get(i).numberOfPreffredStudents();k++)
					{
						if(listOfLecturers.get(i).accessStudByIndex(k).studentWantProject(p)==true)
						{
							p.addPreferedStudent(listOfLecturers.get(i).accessStudByIndex(k));
						}
					}
				}				
			}
			
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}
	
	public void alocateProjects()
	{
		while(someStudentIsFreeAndHasANonEmptyList())
		{
			Student s= getStudentWhoIsFreeAndHasANonEmptyList();
			Project p=s.getStudentsFirstOption();			
			Lecturer l=getLecturerWhoOffersAnProject(p);
			s.assigaALecturer(l);s.assignAProject(p);
			if(p.isOverSubscribed())
			{
				//cautam studetul care este alocat proiectului P si este cel mai slab cotat in ochii profului
				Student prost=p.getWorstSubscribedStudent();
				prost.breakProvisionalAssignmet();				
			}
			
			else if(l.isOverSubscribed())
			{
				//cautam studentul care este alocat Lecturer l si este cel mai slab cotat in ochii profului
				Student prost=l.returnWorstStuddentAssigned();
				prost.breakProvisionalAssignmet();
			}
			
			if(p.isFull())
			{
				//cautam studetul care este alocat proiectului P si este cel mai slab cotat in ochii profului
				Student prost=p.getWorstSubscribedStudent();
				//Determin pozitia pe care se afla studentul in lista de studenti ordonata dupa preferintele
				//profesorului ce are alocat proiectul p
				for(int i=p.getIndexOfAStudent(s)+1;i<p.getNumberOfPrefferdStudents();i++)
				{
					deletePair(p.getStudentByIndex(i), p);
				}
				
			}			
			else if(l.isFull())
			{
				//cautam studentul care este alocat Lecturer l si este cel mai slab cotat in ochii profului
				Student prost=l.returnWorstStuddentAssigned();
				//Determin pozitia pe care se afla studentul in lista de studenti ordonata dupa preferintele
				//profesorului ce are alocat proiectul p
				for(int i=l.getIndexOfAStudent(prost)+1;i<l.getNumberOfStudentsPrefered();i++)
				{
					//Pentru fiecare dintre proiectele pe care le supervizeaze
					//Daca studenti rau clasari aspira la proiectele sale
					//Le tai aripile, elimin aceasta posibilitatea din lista lor de preferinte
					for(int k=0;k<l.getNumberOfProjectSupervised();k++)
					{
						if(l.getStudentByIndex(i).studentWantProject(l.getProjectByIndex(k)))
						{
							deletePair(l.getStudentByIndex(i), l.getProjectByIndex(k));
						}
					}
				}
			}			
		}
		
		System.out.println(printSolution());
	}
	
	/**
	 * 
	 * @return Whether or not a student still has options
	 */
	public boolean someStudentIsFreeAndHasANonEmptyList()
	{
		for(Student s :listOfStudents)
		{
			if(s.isFree())
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return The student that hasn't a project allocated yet
	 */
	public Student getStudentWhoIsFreeAndHasANonEmptyList()
	{
		for(Student s : listOfStudents)
		{
			if(s.isFree())
				return s;
		}
		return null;
	}
	
	/**
	 * 
	 * @param p
	 * @return The lecturer who offers Project p
	 */
	public Lecturer getLecturerWhoOffersAnProject(Project p)
	{
		for(Lecturer l:listOfLecturers)
		{
			if(l.offersAnProject(p))
				return l;
		}
		return null;
	}
	
	/**
	 * We eliminate the posibiliti that Student s will pe alocated to Project p
	 * @param s
	 * @param p
	 */
	public void deletePair(Student s,Project p)
	{
		s.removeProjectFromListOfPreference(p);
	}

	
	/**
	 * 
	 * @param id
	 * @return The project that has that id
	 */
	public Project findProjectByID(int id)
	{
		for(Project p:listOfProjects)
			if(p.returnID()==id)
				return p;
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return The student that has that id
	 */
	public Student findStudentByID(int id)
	{
		for(Student s :listOfStudents)
		{
			if(s.getId()==id)
				return s;
		}
		return null;
	}


	@Override
	public String toString() {
		
		String afisare="";
		for(Student s:listOfStudents)
		{
			afisare+=s.toString();
		}
		afisare+="\n";
		for(Project p : listOfProjects)
		{
			afisare+=p.toString();
		}
		afisare+="\n";
		for(Lecturer l :listOfLecturers)
		{
			afisare+=l.toString();
		}
		
		
		return afisare;
	}
	
	/**
	 * 
	 * @return The solution of the SPA Problem
	 */
	public String printSolution()
	{
		String afisare="";
		for(Student s:listOfStudents)
		{
			afisare+=s.getNume()+" ";
			if(s.getProjectAllocated()==null) //nu a fost posibila alocare unui proiect/profesor
			{
				afisare+="Project Unknown"+" "+"Teacher Unknows";
			}
			else
			{
				s.caulculateSatisfiabily();
				afisare+=s.getProjectAllocated().getDescriere()+" "+s.getProjectAllocated().getLecturer().getNume()+" Student multumit:"+String.valueOf((int)s.getSatisfiability())+"%";
				
			}
			afisare+="\n";
		}
		afisare+="\n";
		for(Lecturer l : listOfLecturers)
		{
			afisare+=l.getNume()+l.getSatisfiability()+"\n";
		}
		
		return afisare;
	}
	
	
	

}
