package com.mctschess.dto;

import com.mctschess.model.location.Location.File;

public class LocationDto {

	private File file; // columns
	private int rank; // rows
	
	public LocationDto() {
	}
	
	public LocationDto(File file, int rank) {
		this.file = file;
		this.rank = rank;
	}


	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}