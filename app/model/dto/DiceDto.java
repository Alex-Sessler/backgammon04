package model.dto;


public class DiceDto {
	private byte value;
	
	private boolean played;
	
	public DiceDto(byte value, boolean played) {
		this.value = value;
		this.played = played;
	}

	public DiceDto() {
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}
}
