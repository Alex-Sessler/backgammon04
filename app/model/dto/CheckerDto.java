package model.dto;


public class CheckerDto {
	private long id;
	
	private byte internId;
	
	private PlayerDto player;
	

	public CheckerDto() {
	}

	public CheckerDto(long id, byte internId, PlayerDto player) {
		super();
		this.id = id;
		this.internId = internId;
		this.player = player;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte getInternId() {
		return internId;
	}

	public void setInternId(byte internId) {
		this.internId = internId;
	}

	public PlayerDto getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDto player) {
		this.player = player;
	}
}
