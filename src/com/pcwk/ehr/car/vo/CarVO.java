/**
 * Package Name : com.pcwk.ehr.book.vo <br/>
 * Class Name: BookVO.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-18<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.car.vo;

import com.pcwk.ehr.cmn.DTO;

public class CarVO extends DTO {
	private String brand;
	private String model;
	private String size;
	private int price;
	private String color;
	private String carNo;
	private String fuel;
	private int modelYear;
	private int distance;
	private boolean reserve;

	public CarVO(String brand, String model, String size, int price, String color, String carNo, String fuel,
			int modelYear, int distance, boolean reserve) {
		super();
		this.brand = brand;
		this.model = model;
		this.size = size;
		this.price = price;
		this.color = color;
		this.carNo = carNo;
		this.fuel = fuel;
		this.modelYear = modelYear;
		this.distance = distance;
		this.reserve = reserve;
	}

	public CarVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CarVO [brand=" + brand + ", model=" + model + ", size=" + size + ", price=" + price + ", color=" + color
				+ ", carNo=" + carNo + ", fuel=" + fuel + ", modelYear=" + modelYear + ", distance=" + distance
				+ ", reserve=" + reserve + "]";
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public int getModelYear() {
		return modelYear;
	}

	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean getReserve() {
		return reserve;
	}

	public void setReserve(boolean reserve) {
		this.reserve = reserve;
	}

}
