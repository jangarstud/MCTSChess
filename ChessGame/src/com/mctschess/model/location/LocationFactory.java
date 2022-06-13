package com.mctschess.model.location;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location.File;

public class LocationFactory {

	private static final File[] files = File.values();

	public static Location build(Location currentLocation, int fileOffset, int rankOffset) {
		int currentFile = currentLocation.getFile().ordinal();
		int currentRank = currentLocation.getRank();
		if (currentFile + fileOffset >= files.length || currentFile + fileOffset < 0 || currentRank + rankOffset < 1
				|| currentRank + rankOffset > Board.BOARD_DIMENSION) {
			return null;
		}
		return new Location(files[currentFile + fileOffset], currentLocation.getRank() + rankOffset);
	}
}
