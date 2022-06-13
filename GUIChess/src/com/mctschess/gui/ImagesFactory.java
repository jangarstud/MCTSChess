package com.mctschess.gui;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class ImagesFactory {
	private static Map<String, Image> cache = new HashMap<>();

	
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
