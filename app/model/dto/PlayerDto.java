package model.dto;

import backgammon04.util.Color;

public class PlayerDto {
	private long id;

	private UserDto user;

	private Color color;

	public PlayerDto(long id, UserDto user, Color color) {
		this.id = id;
		this.user = user;
		this.color = color;
	}

	public PlayerDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
