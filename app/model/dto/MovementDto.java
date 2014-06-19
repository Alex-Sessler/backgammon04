package model.dto;

import java.util.Date;
import java.util.List;

public class MovementDto {
	private long id;

	private Date created;

	private Integer sourcePoint;

	private Integer targetPoint;

	private List<DiceDto> dices;

	public MovementDto(long id, Date created, Integer sourcePoint,
			Integer targetPoint, List<DiceDto> dices) {
		this.id = id;
		this.created = created;
		this.sourcePoint = sourcePoint;
		this.targetPoint = targetPoint;
		this.dices = dices;
	}

	public MovementDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getTargetPoint() {
		return targetPoint;
	}

	public void setTargetPoint(Integer targetPoint) {
		this.targetPoint = targetPoint;
	}

	public List<DiceDto> getDices() {
		return dices;
	}

	public void setDices(List<DiceDto> dices) {
		this.dices = dices;
	}

	public int getSourcePoint() {
		return sourcePoint;
	}

	public void setSourcePoint(Integer sourcePoint) {
		this.sourcePoint = sourcePoint;
	}
}
