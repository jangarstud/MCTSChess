package com.mctschess.gui;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class ImagesFactory {
	private static Map<String, Image> cache = new HashMap<>();

	private static final String BLACK_ROOK_IMAGE = "/img/black_rook.png";
	private static final String BLACK_KNIGHT_IMAGE = "/img/black_knight.png";
	private static final String BLACK_BISHOP_IMAGE = "/img/black_bishop.png";
	private static final String BLACK_QUEEN_IMAGE = "/img/black_queen.png";
	private static final String BLACK_KING_IMAGE = "/img/black_king.png";
	private static final String BLACK_PAWN_IMAGE = "/img/black_pawn.png";

	private static final String WHITE_ROOK_IMAGE = "/img/white_rook.png";
	private static final String WHITE_KNIGHT_IMAGE = "/img/white_knight.png";
	private static final String WHITE_BISHOP_IMAGE = "/img/white_bishop.png";
	private static final String WHITE_QUEEN_IMAGE = "/img/white_queen.png";
	private static final String WHITE_KING_IMAGE = "/img/white_king.png";
	private static final String WHITE_PAWN_IMAGE = "/img/white_pawn.png";


	public static ImageIcon getScaledImage(JComponent component, String identifier) {
		String imagePath = "/com/mctschess/gui/img/" + identifier + ".png";
		Image imgOriginal;

		if (cache.containsKey(imagePath))
			imgOriginal = cache.get(imagePath);
		else {
			imgOriginal = new ImageIcon(ImagesFactory.class.getResource(imagePath)).getImage();
			cache.put(imagePath, imgOriginal);
		}

		Image scaledImage = imgOriginal.getScaledInstance(component.getWidth(), component.getHeight(),
				Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

}
