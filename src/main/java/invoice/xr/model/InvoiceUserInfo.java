package invoice.xr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model to register Manager and Owner of InvoiceXR.
 * 
 * @author Arshath Mohammed
 *
 */
@Entity
@Table(name = "InvoiceUserInfo")
public class InvoiceUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 25)
	private Integer id;

	@Column(name = "firstName", length = 50)
	private String firstName;
	
	@Column(name = "lastName", length = 50)
	private String lastName;
	
	@Column(name = "email", length = 50)
	private String email;
	
	@Column(name = "username", length = 50)
	private String userName;

	@Column(name = "password", length = 800)
	private String password;

	@Column(name = "role", length = 50)
	private String role;

	@Column(name = "enabled")
	private short enabled;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return String.format("InvoiceUserInfo [id=%s, userName=%s, password=%s, role=%s, enabled=%s]", id, userName, password,
				role, enabled);
	}

}


