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

public class Dilate extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();;
	int[][] data1;
	
	Dilate() {
	};

	Dilate(File file) {
		super(file);
	}
	
	boolean checkColor(int[][][] data, int i, int j) {
		if(data[i][j][0] == 255 && data[i][j][1] == 255 && data[i][j][2] == 255) {
			return true;
		}else {
			return false;
		}
	}
	boolean checkColor2(int[][][] data, int i, int j) {
		if(data[i][j][0] == 0 && data[i][j][1] == 0 && data[i][j][2] == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	void dilate() {
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				data1[i][j] = 0;
			}
		}
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				if(i+1<height && j+1<width && i-1>0 && j-1>0) {
					//上面
					if(checkColor(data,i,j) && checkColor2(data,i+1,j) && checkColor2(data,i,j-1) && data1[i+1][j] == 0 && data1[i][j-1] == 1) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//左邊
					else if(checkColor(data,i,j) && checkColor2(data,i,j+1) && data1[i][j+1] == 0 ) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//下面
					else if(checkColor(data,i,j) && checkColor(data,i+1,j) && checkColor(data,i,j+1) && checkColor2(data,i-1,j) && checkColor2(data,i,j-1) && data1[i-1][j] == 0 && data1[i][j-1] == 1) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//右邊
					else if(checkColor(data,i,j) && checkColor2(data,i,j-1) && data1[i][j-1] == 0 ) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//左上角
					else if(checkColor(data,i,j) && checkColor2(data,i+1,j+1) && data1[i+1][j+1] == 0) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//右上角
					else if(checkColor(data,i,j) && checkColor2(data,i,j-1) && checkColor2(data,i+1,j-1) && data1[i+1][j-1] == 0 && data1[i][j-1] == 1) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//左下角
					else if(checkColor(data,i,j) && checkColor2(data,i-1,j) && checkColor2(data,i-1,j+1) && data1[i-1][j+1] == 0 && data1[i-1][j] == 1) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
					//右下角
					else if(checkColor(data,i,j) && checkColor2(data,i-1,j) && checkColor2(data,i,j-1) && checkColor2(data,i-1,j-1) && data1[i-1][j-1] == 0 && data1[i][j-1] == 1 && data1[i-1][j] == 1) {
						data[i][j][0] = 0;
						data[i][j][1] = 0;
						data[i][j][2] = 0;
						data1[i][j] = 1;
					}
				}
			}
		}
		imgAft = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = Util.makeColor(data[y][x][0], data[y][x][1], data[y][x][2]);
				imgAft.setRGB(x, y, rgb);
			}
		}
		Util.drawImg(imagePanelAft, imgAft);
	}
	
	@Override
	void exe() {
		{
			dilate();
		}
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdDilate);
		btnCmd.setText(Util.cmdDilate);
	}
}