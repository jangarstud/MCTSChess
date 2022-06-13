package com.mctschess.model.location;

import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.Piece;

public class LocationAndPiece {
	
	private Location location;
	private Piece piece;
	
	public LocationAndPiece(File file, int rank, Piece piece) {
		this(new Location(file, rank), piece);
	}

	public LocationAndPiece(Location location, Piece piece) {
		super();
		this.location = location;
		this.piece = piece;
	}

	public Location getLocation() {
		return location;
	}
	
	public Piece getPiece() {
		return piece;
	}

}
