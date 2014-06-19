package model.dto;

public class PointDto {


	private byte internId;

	private int numberOfCheckers;
	
	private String color;

	public byte getInternId() {
		return internId;
	}

	public void setInternId(byte internId) {
		this.internId = internId;
	}

	public int getNumberOfCheckers() {
		return numberOfCheckers;
	}

	public void setNumberOfCheckers(int numberOfCheckers) {
		this.numberOfCheckers = numberOfCheckers;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
