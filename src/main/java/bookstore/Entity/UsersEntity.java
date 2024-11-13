package bookstore.Entity;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Users")
public class UsersEntity {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name = "avatar", columnDefinition = "nvarchar")
	private String avatar;
	
	@Column(name = "email", columnDefinition = "nvarchar")
	private String email;
	
	@Column(name = "password", columnDefinition = "nvarchar")
	private String password;
	
	@Column(name = "fullname", columnDefinition = "nvarchar")
	private String fullname;
	
	@Column(name = "gender")
	private Integer gender;
	
	@Column(name = "phone", columnDefinition = "nvarchar")
	private String phone;
	
	@Column(name = "reset_token", columnDefinition = "nvarchar")
	private String reset_token;
	
	@Column(name = "verify_token", columnDefinition = "nvarchar")
	private String verify_token;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date created_at;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date updated_at;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReset_token() {
		return reset_token;
	}

	public void setReset_token(String reset_token) {
		this.reset_token = reset_token;
	}

	public String getVerify_token() {
		return verify_token;
	}

	public void setVerify_token(String verify_token) {
		this.verify_token = verify_token;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
}
