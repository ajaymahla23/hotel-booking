package com.hotel.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	private String fullName;
	private String email;
	private Long mobileNumber;
	private String password;
	private Long invitationCode;
	private boolean enabled;
	private String role;
	private Date date;
	private Long pass;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private List<AgentInviteCode> agentInviteCodeList = new ArrayList<>();

	public Long getPass() {
		return pass;
	}

	public void setPass(Long pass) {
		this.pass = pass;
	}

	public List<AgentInviteCode> getAgentInviteCodeList() {
		return agentInviteCodeList;
	}

	public void setAgentInviteCodeList(List<AgentInviteCode> agentInviteCodeList) {
		this.agentInviteCodeList = agentInviteCodeList;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(Long invitationCode) {
		this.invitationCode = invitationCode;
	}

}
