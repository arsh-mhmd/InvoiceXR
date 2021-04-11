/**
 * 
 */
package invoice.xr.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author arshm
 *
 */
@Entity
@Table(name="InvoiceUser")
public class InvoiceUser {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="userName")
	
	private String userName;
	
	@Column(name="password")
	
	private String password;
	
	@Column(name="fullName")
	
	private String fullName;
	
	@Column(name="contactNo")
	
	private String contactNo;
	
	@Column(name="userType")
	
	private String userType;
	
	@Column(name="isLoggedIn")
	
	private boolean isLoggedIn;
	
	public InvoiceUser() {
    }
    public InvoiceUser( String userName, 
                 String password) {
        this.userName = userName;
        this.password = password;
        this.isLoggedIn = false;
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceUser)) return false;
        InvoiceUser invoiceUser = (InvoiceUser) o;
        return Objects.equals(userName, invoiceUser.userName) &&
                Objects.equals(password, invoiceUser.password);
    }
	
}
