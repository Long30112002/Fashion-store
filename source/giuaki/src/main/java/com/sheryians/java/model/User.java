package com.sheryians.java.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;



@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@NotEmpty
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email(message = "{errors.invalid_email}")
	private String email;
	
	
	private String password;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
			name="user_role",
			joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}
			, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
	)
	private List<Role> roles;

	
	
	public User() {
	}
	
	

	public User(Integer id, @NotEmpty String firstName, String lastName,
			@NotEmpty @Email(message = "{errors.invalid_email}") String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(User user) {
		super();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	
	
}
