package com.hotel.model;

import javax.persistence.*;

@Entity
@Table(name = "bank_card")
public class MyBankCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bank_id")
	private Long id;
	private String bankName;
	private Long creditCardNumber;
	private String loginName;
	private String branch;
	private Long loginMobileNumer;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getLoginMobileNumer() {
		return loginMobileNumer;
	}

	public void setLoginMobileNumer(Long loginMobileNumer) {
		this.loginMobileNumer = loginMobileNumer;
	}

}
