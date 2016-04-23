package pachet;

/**
 * This abstract class describes a person that is involved in SPA Problem
 * @author cipri_000
 * @version 2.0
 *
 */

public abstract class Person {
	
	private String nume,email;
	private int id;
	
	/**
	 * This method will allow as to determinate if a person is free , in other words if a person is still 
	 * able to be part of SPA Problem.
	 * @return
	 */
	public abstract boolean isFree();

	/**
	 * Give us the name of the person
	 * @return
	 */
	public String getNume() {
		return nume;
	}
	
	/**
	 * We set the name of the person
	 * @param nume One of a person's identifier
	 */

	public void setNume(String nume) {
		this.nume = nume;
	}

	/**
	 * Give us the email of a persom
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * We set the person's email
	 * @param email 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gives us the person's ID
	 * @return
	 */
	
	public int getId() {
		return id;
	}

	/**
	 * We set the person's ID
	 * @param id Will help us to identify unique a person
	 */
	public void setId(int id) {
		this.id = id;
	} 
	

}
