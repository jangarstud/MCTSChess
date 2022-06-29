package com.mctschess.model.location;

import com.mctschess.dto.LocationAndPieceDto;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.Piece;
import com.mctschess.model.pieces.PieceFactory;

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
	
	public static LocationAndPiece fromDto(LocationAndPieceDto dto) {
		return new LocationAndPiece(Location.fromDto(dto.getLocation()), PieceFactory.fromDto(dto.getPiece()));
	}
}
