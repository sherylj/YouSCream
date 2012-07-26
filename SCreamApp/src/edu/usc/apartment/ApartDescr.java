package edu.usc.apartment;

public class ApartDescr {
	
	private String address;
	private String rent;
	private String size;
	
	public ApartDescr(String address, String size, String rent) {
		this.address = address;
		this.rent = rent;
		this.size = size;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getRent() {
		return Integer.parseInt(rent);
	}
	
	public int getSize() {
		return Integer.parseInt(size);
	}

}
