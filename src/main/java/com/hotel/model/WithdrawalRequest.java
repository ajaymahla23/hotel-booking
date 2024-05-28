package com.hotel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "withdrawal_request")
public class WithdrawalRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "withdrawlReq_Id")
	private Long id;

	private Date date;

	@Column(name = "withdraw_amt")
	private double withdrawAmout;

	@Column(name = "req_Y_N")
	private String status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "bank_id")
	private MyBankCard myBankCard;

	public MyBankCard getMyBankCard() {
		return myBankCard;
	}

	public void setMyBankCard(MyBankCard myBankCard) {
		this.myBankCard = myBankCard;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public double getWithdrawAmout() {
		return withdrawAmout;
	}

	public String getStatus() {
		return status;
	}

	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setWithdrawAmout(double withdrawAmout) {
		this.withdrawAmout = withdrawAmout;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
