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
@Table(name = "my_order")
public class MyOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "order_date")
	private Date date;

	@Column(name = "order_no")
	private String orderNumber;

	@Column(name = "status_R_Y_N")
	private String ordreStatus;

	@Column(name = "approval_date")
	private Date approvalDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "hotelInfo_id")
	private HotelInfo hotelInfo;

	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getOrdreStatus() {
		return ordreStatus;
	}

	public Date getApprovalDate() {
		return approvalDate;
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

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrdreStatus(String ordreStatus) {
		this.ordreStatus = ordreStatus;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
