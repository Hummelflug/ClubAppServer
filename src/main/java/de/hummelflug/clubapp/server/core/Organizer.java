package de.hummelflug.clubapp.server.core;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import de.hummelflug.clubapp.server.utils.GenderType;
import de.hummelflug.clubapp.server.utils.UserRole;

@Entity
@Table(name = "organizer")
@PrimaryKeyJoinColumn(name = "user_id")
@NamedQueries({
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Organizer.findAll",
            query = "select o from Organizer o"),
    @NamedQuery(name = "de.hummelflug.clubapp.server.core.Organizer.findByName",
            query = "select o from Organizer o "
            + "where o.firstName like :name "
            + "or o.lastName like :name")
})
public class Organizer extends User {
	
	@Column(name = "user_id", nullable = false)
	private Long id;
	
	@Column(name = "organization")
	private String organization;
	
	/**
	 * A no-argument constructor
	 */
	public Organizer() {
		super(UserRole.ORGANIZER);
	}
	
	/**
	 * @param lastName organizer last name
     * @param firstName organizer first name
     * @param birthday organizer birthday
     * @param email organizer email
     * @param password organizer password
     * @param gender organizer gender
	 * @param organization of organizer
	 */
	public Organizer(@Nonnull String lastName, @Nonnull String firstName, @Nonnull Date birthday, @Nonnull String email, 
			@Nonnull String password, GenderType gender, String organization) {
		super(lastName, firstName, birthday, email, password, gender, UserRole.ORGANIZER);
		this.organization = organization;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organizer other = (Organizer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
