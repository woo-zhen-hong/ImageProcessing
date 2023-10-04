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

public class Scaling extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();;
	static JTextField tfAmpX = new JTextField(5);
	static JTextField tfAmpY = new JTextField(5);
	static JLabel lbAmpX = new JLabel("x軸倍率");
	static JLabel lbAmpY = new JLabel("y軸倍率");
	
	Scaling() {
	};

	Scaling(File file) {
		super(file);
	}
	
	int location(double x, double y) {
		int rightTop = 0;
		int leftBottom = 0;
		int rightBottom = 0;
		int leftTop = imgBef.getRGB((int) x,(int) y);
		if((int)x + 1 >= width && (int) y + 1 >= height) {
			rightBottom = imgBef.getRGB((int) x,(int) y);
			rightTop = imgBef.getRGB((int) x,(int) y);
			leftBottom = imgBef.getRGB((int) x,(int) y);
		}
		else if((int)x + 1 >= width) {
			rightTop = imgBef.getRGB((int) x,(int) y);
			rightBottom = imgBef.getRGB((int) x,(int) y + 1);
			leftBottom = imgBef.getRGB((int) x,(int) y + 1);
		}
		else if((int) y + 1 >= height) {
			leftBottom = imgBef.getRGB((int) x,(int) y);
			rightTop = imgBef.getRGB((int) x + 1,(int) y);
			rightBottom = imgBef.getRGB((int) x + 1,(int) y);
		}
		else {
			leftBottom = imgBef.getRGB((int) x,(int) y + 1);
			rightTop = imgBef.getRGB((int) x + 1,(int) y);
			rightBottom = imgBef.getRGB((int) x + 1,(int) y + 1);
		}
		double alpha = x - (int) x;
		double beta = y - (int) y;
		return Util.bilinear(leftTop, rightTop, leftBottom, rightBottom, alpha, beta);
	}
	
	@Override
	void exe() {
		{
			 
			String X = tfAmpX.getText();
			String Y = tfAmpY.getText();
			double x = Double.parseDouble(X);
			double y = Double.parseDouble(Y);
			int newW = (int)(x * width);
			int newH = (int)(y * height);
			imgAft = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
			double[][] matrix = {{1/x,0.0,0.0},{0.0,1/y,0.0},{0.0,0.0,1.0}};
			for(int j = 0; j < newH; j++) {
				for(int i = 0; i < newW; i++) {
					double[] newPosition = {i,j,1.0};
					double[] position = Util.affine(matrix, newPosition);
					int rgb = location(position[0],position[1]);
					imgAft.setRGB(i, j, rgb);
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
		setTitle(Util.cmdScaling);
		btnCmd.setText(Util.cmdScaling);
		tfAmpX.setText("1.0");
		tfAmpY.setText("1.0");
		Scaling.cotrolPanelTranslate = new JPanel();
		Scaling.cotrolPanelTranslate.setBounds(0, 0, 500, 150);
		Scaling.cotrolPanelTranslate.add(lbAmpX);
		Scaling.cotrolPanelTranslate.add(tfAmpX);
		Scaling.cotrolPanelTranslate.add(lbAmpY);
		Scaling.cotrolPanelTranslate.add(tfAmpY);
		getContentPane().add(Scaling.cotrolPanelTranslate);
	}
}