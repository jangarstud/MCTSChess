package com.mctschess.model.squares;

import com.mctschess.model.location.Location;
import com.mctschess.model.pieces.AbstractPiece;

public class Square {
	public enum SquareColor{LIGHT, DARK};
	private final SquareColor color;
	private final Location location;
	private boolean occupied;
	private AbstractPiece currentPiece;
	
	public Square(SquareColor color, Location location) {
		this.color = color;
		this.location = location;
		occupied = false;
	}
	
	public void initialize() {
		this.occupied = false;
	}
	
	public SquareColor getColor() {
		return this.color;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public boolean isOccupied() {
		return this.occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public AbstractPiece getCurrentPiece() {
		return this.currentPiece;
	}
	
	public void setCurrentPiece(AbstractPiece newPiece) {
		this.currentPiece = newPiece;
		setOccupied(true);
	}
	
	@Override
	public String toString() {
		return "Square{" +
			"Color = " + color +
			"; Location = " + location +
			"; Is occupied = " + occupied +
			"; Current piece = " + currentPiece +
			"}";
	}
	
}
