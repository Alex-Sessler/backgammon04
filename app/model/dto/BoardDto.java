package model.dto;

import java.util.List;

public class BoardDto {
	
	private long id;
	
	private List<PointDto> pointList;
	
	public BoardDto() {}
	
	public BoardDto(long id, List<PointDto> pointList) {
		this.id = id;
		this.pointList = pointList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<PointDto> getPointList() {
		return pointList;
	}

	public void setPointList(List<PointDto> pointList) {
		this.pointList = pointList;
	}
}
