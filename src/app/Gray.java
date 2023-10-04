package app;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Gray extends AbstractBasic {
	private static final long serialVersionUID = 1L;

	Gray() {
	};

	Gray(File file) {
		super(file);
	}
	
	@Override
	void exe() {
		{
			 
			imgAft = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for(int a = 0; a < height; a++) {
				for(int b = 0; b< width; b++) {
					int gray = Util.covertToGray(data[a][b][0], data[a][b][1], data[a][b][2]);
					int rgb = Util.makeColor(gray, gray, gray);
					imgAft.setRGB(b, a, rgb);
				}
			}
			
			Util.saveImg = imgAft;
			Graphics g = imagePanelAft.getGraphics();
			imagePanelAft.paintComponent(g);
			g.drawImage(imgAft, 0, 0, null);
		}
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdGray);
		btnCmd.setText(Util.cmdGray);
	}
}