package com.hotel.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table (name = "hotel_info")
public class HotelInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "hotel_name")
	private String hotelName;

	@Column(name = "rent")
	private double hotelRent;

	@Column(name = "hotel_image")
	private String hotelImage;

	@Column(name = "hotel_details")
	private String hotelDetails;

	@Column(name = "address")
	private String hotelAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public double getHotelRent() {
		return hotelRent;
	}

	public void setHotelRent(double hotelRent) {
		this.hotelRent = hotelRent;
	}

	public String getHotelImage() {
		return hotelImage;
	}

	public void setHotelImage(String hotelImage) {
		this.hotelImage = hotelImage;
	}

	public String getHotelDetails() {
		return hotelDetails;
	}

	public void setHotelDetails(String hotelDetails) {
		this.hotelDetails = hotelDetails;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

}
