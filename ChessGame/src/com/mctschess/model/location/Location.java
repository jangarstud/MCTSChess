package com.mctschess.model.location;

import java.util.Objects;

import com.mctschess.model.pieces.AbstractPiece.PieceColor;

public class Location {

	public static enum File {
		A, B, C, D, E, F, G, H;

		public File sum(int offset) {
			int newFile = ordinal() + offset;
			if (newFile < 0 || newFile > 7)
				return null;
			return File.values()[newFile];
		}

	};

	private File file; // columns
	private int rank; // rows

	public Location(File file, int rank) {
		this.file = file;
		this.rank = rank;
	}

	public File getFile() {
		return file;
	}

	public int getRank() {
		return rank;
	}

	public PieceColor getSquareColor() {
		if (rank % 2 == 0) {
			if (file.ordinal() % 2 == 0) {
				return PieceColor.WHITE;
			} else {
				return PieceColor.BLACK;
			}
		} else {
			if (file.ordinal() % 2 == 0) {
				return PieceColor.BLACK;
			} else {
				return PieceColor.WHITE;
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(file, rank);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return file == other.file && rank == other.rank;
	}

	@Override
	public String toString() {
		return "Location{" + "File = " + file + "; Rank = " + rank + "}";
	}
}
