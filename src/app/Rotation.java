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

public class Rotation extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();;
	static JTextField tfRed = new JTextField(5);
	static JTextField tfGreen = new JTextField(5);
	static JTextField tfBlue = new JTextField(5);
	static JTextField tfTheta = new JTextField(5);
	static JLabel lbRed = new JLabel("背景 (R)");
	static JLabel lbGreen = new JLabel("背景 (G)");
	static JLabel lbBlue = new JLabel("背景 (B)");
	static JLabel lbTheta = new JLabel("旋轉角度 (1~89度)");
	
	Rotation() {
	};

	Rotation(File file) {
		super(file);
	}
	
	void fillColor(BufferedImage img) {
		int r = Integer.parseInt(tfRed.getText());
		int g = Integer.parseInt(tfGreen.getText());
		int b = Integer.parseInt(tfBlue.getText());
		int rgb = Util.makeColor(r, g, b);
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				img.setRGB(j, i, rgb);
			}
		}
	}
	
	int exeBilinear(double x, double y) {
		int left = Util.checkImageBounds((int) Math.floor(x), width);
		int right = Util.checkImageBounds((int) Math.ceil(x), width);
		int top = Util.checkImageBounds((int) Math.floor(y), height);
		int bottom = Util.checkImageBounds((int) Math.ceil(y), height);
		double alpha = 0.0;
		double beta = 0.0;
		if (left < right)
			alpha = x - left;
		if (top < bottom)
			beta = y - top;
		int[] rgbColor = new int[3];
		for (int i = 0; i < 3; i++)
			rgbColor[i] = Util.bilinear(data[top][left][i], data[top][right][i], data[bottom][left][i],
					data[bottom][right][i], alpha, beta);
		return Util.makeColor(rgbColor[0], rgbColor[1], rgbColor[2]);
	}
	
	@Override
	void exe() {
		{
			 
			int deg = Integer.parseInt(tfTheta.getText());
			if (deg > 89 || deg < 1)
				return;
			double theta = 1.0 * deg / 180 * Math.PI;
			double heightCos = height * Math.cos(theta);
			double heightSin = height * Math.sin(theta);
			double widthCos = width * Math.cos(theta);
			double widthSin = width * Math.sin(theta);
			int newWidth = (int) (widthCos + heightSin);
			int newHeight = (int) (heightCos + widthSin);
			imgAft = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			double[][] matrix = { { Math.cos(theta), 1.0 * Math.sin(theta), 0.0 },
					{ -1.0 * Math.sin(theta), Math.cos(theta), 0.0 }, { 0.0, 0.0, 1 } };
			fillColor(imgAft);
			for (int y = 0; y < newHeight; y++) {
				for (int x = 0; x < newWidth; x++) {
					double[] Postion1 = { 1.0 * x - newWidth / 2.0, 1.0 * newHeight - y - newHeight / 2.0, 1.0 };
					double[] Position2 = Util.affine(matrix, Postion1);
					double x2 = Position2[0] + width / 2.0;
					double y2 = 1.0 * height - (Position2[1] + height / 2.0);
					if(0 <= x2  && x2 < width && 0 <= y2 && y2 < height) {
						int rgb = exeBilinear(x2,y2);
						imgAft.setRGB(x,y,rgb);
					}
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
		setTitle(Util.cmdRotation);
		btnCmd.setText(Util.cmdRotation);
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
		tfTheta.setText("0");
		Rotation.cotrolPanelTranslate = new JPanel();
		Rotation.cotrolPanelTranslate.setBounds(0, 0, 1200, 150);
		Rotation.cotrolPanelTranslate.add(lbRed);
		Rotation.cotrolPanelTranslate.add(tfRed);
		Rotation.cotrolPanelTranslate.add(lbGreen);
		Rotation.cotrolPanelTranslate.add(tfGreen);
		Rotation.cotrolPanelTranslate.add(lbBlue);
		Rotation.cotrolPanelTranslate.add(tfBlue);
		Rotation.cotrolPanelTranslate.add(lbTheta);
		Rotation.cotrolPanelTranslate.add(tfTheta);
		getContentPane().add(Rotation.cotrolPanelTranslate);
	}
}