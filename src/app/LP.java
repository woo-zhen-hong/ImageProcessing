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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LP extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();;
	int[][][] newData;
	
	LP() {
	};

	LP(File file) {
		super(file);
	}
	
	int[][][] filter(int filter, int[][][] newData) {
		for(int a=0; a<3; a++) {
			for(int x=0; x<height; x++) {
				for(int y=0; y<width; y++) {
					double sum = 0.0;
					for(int z=x-1; z<x+2; z++) {
						for(int q=y-1; q<y+2; q++) {
							if(filter == 0) {
								if(z >= 0 && z < height && q >= 0 && q < width) {
									sum += data[z][q][a];
								}
							}
							else if(filter == 1) {
								if(z >= 0 && z < height && q >= 0 && q < width) {
									if(z == x && q == y) {
										sum += 17.0 * data[z][q][a];
									}
									else {
										sum -= data[z][q][a];
									}
								}
							}
						}
					}
					newData[x][y][a] = Util.checkPixelBounds((int) (sum / 9.0));
				}
			}
		}
		
		return newData;
		
	}
	
	BufferedImage newImg(int[][][] newData) {
		for(int x=0; x<height; x++) {
			for(int y=0; y<width; y++) {
				imgAft.setRGB(y, x, Util.makeColor(newData[x][y][0], newData[x][y][1], newData[x][y][2]));
			}
		}
		return imgAft;
	}
	
	void showImg2() {
		imgAft = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Util.saveImg = imgAft;
		imgAft = newImg(newData);
		Graphics g = imagePanelAft.getGraphics();  
		g.drawImage(imgAft, 0, 0, null);
	}
	
	@Override
	void exe() {
		{
			 
			newData = new int[height][width][3];
			newData = filter(0,newData);
			showImg2();
			
		}
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdLP);
		btnCmd.setText(Util.cmdLP);
	}
}