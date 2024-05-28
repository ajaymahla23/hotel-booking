package com.hotel.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "recharge_wallet")
public class RechargeWallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recharge_id")
	private Long id;

	private Date date;

	private double amountByUser;

	@Column(name = "before_totalAmt")
	private double beforeAmt;

	@Column(name = "after_totalAmt")
	private double afterAmt;

	@Column(name = "freeze_amt")
	private double freezeAmt;

	@Column(name = "status_Y_N")
	private String status;

	@Column(name = "roleType_U_S")
	private String roleType;

	private String remarks;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public double getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(double freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public double getBeforeAmt() {
		return beforeAmt;
	}

	public double getAfterAmt() {
		return afterAmt;
	}

	public void setBeforeAmt(double beforeAmt) {
		this.beforeAmt = beforeAmt;
	}

	public void setAfterAmt(double afterAmt) {
		this.afterAmt = afterAmt;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmountByUser() {
		return amountByUser;
	}

	public void setAmountByUser(double amountByUser) {
		this.amountByUser = amountByUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

}
