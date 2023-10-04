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

public class R90 extends AbstractBasic {
	private static final long serialVersionUID = 1L;

	R90() {
	};

	R90(File file) {
		super(file);
	}
	
	@Override
	void exe() {
		{
			 
			imgAft = new BufferedImage(height, width,BufferedImage.TYPE_INT_ARGB);
			for(int a = 0; a < width; a++) {
				for(int b = 0; b< height; b++) {
					int rgb = Util.makeColor(data[height - 1 - b][a][0], data[height - 1 - b][a][1], data[height - 1 - b][a][2]);
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
		setTitle(Util.cmdR90);
		btnCmd.setText(Util.cmdR90);
	}
}