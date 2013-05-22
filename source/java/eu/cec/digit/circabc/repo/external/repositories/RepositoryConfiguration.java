package eu.cec.digit.circabc.repo.external.repositories;

import java.util.Date;

/**
 * Data about a repository.
 * 
 * @author schwerr
 */
public class RepositoryConfiguration {
	
	private String name = null;
	private Date registrationDate = new Date();
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof RepositoryConfiguration) {
        	return this.name.equals(((RepositoryConfiguration) obj).getName());
        }
		return false;
		
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * Gets the value of the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the value of the name
	 * 
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the value of the registrationDate
	 * 
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	/**
	 * Sets the value of the registrationDate
	 * 
	 * @param registrationDate the registrationDate to set.
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
}
