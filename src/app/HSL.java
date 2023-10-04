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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HSL extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();
	JPanel cotrolPanelHue = new JPanel();
	JPanel cotrolPanelSat = new JPanel();
	JPanel cotrolPanelLum = new JPanel();
	JSlider sliderHue;
	JSlider sliderSat;
	JSlider sliderLum;
	JLabel lbHue = new JLabel("    Hue");
	JLabel lbSat = new JLabel("    Saturation");
	JLabel lbLum = new JLabel("    Luminance");
	JLabel lbHueBeging = new JLabel("-180");
	JLabel lbHueEnd = new JLabel("180");
	JLabel lbSatBeging = new JLabel("-100(%)");
	JLabel lbSatEnd =  new JLabel("100(%)");
	JLabel lbLumBeging  = new JLabel("-100(%)");
	JLabel lbLumEnd  = new JLabel("100(%)");
	JTextField tfHueValue = new JTextField(3);
	JTextField tfSatValue = new JTextField(3);
	JTextField tfLumValue = new JTextField(3);
	int HueValue;
	int SatValue;
	int LumValue;
	double[] HSL;
	
	HSL() {};

	HSL(File file) {
		super(file);
	}
	
	sliderHue.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			HueValue = sliderHue.getValue();
			tfHueValue.setText(HueValue + "");
			changePixel();
		}
	});

	sliderSat.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			SatValue = sliderSat.getValue();
			tfSatValue.setText(SatValue + "");
			changePixel();
		}
	});

	sliderLum.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			LumValue = sliderLum.getValue();
			tfLumValue.setText(LumValue + "");
			changePixel();
		}
	});
	
	void loadImg() {
		try {
			height = imgBef.getHeight();
			width = imgBef.getWidth();
			data = new int[height][width][3];
			imgAft = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			imgAft = imgBef;
			
			for(int a = 0; a < height; a++) {
				for(int b = 0; b < width; b++) {
					int rgb = imgBef.getRGB(b, a);
					data[a][b][0] = Util.getR(rgb);
					data[a][b][1] = Util.getG(rgb);
					data[a][b][2] = Util.getB(rgb);
				}
			}
		} catch (IOException e) {
			System.out.println("IO exception");
		}
	}
	
	double[] RGBToHSL(double r, double g, double b) {
		HSL = new double[3];
		double h = 0.0,s = 0.0,l = 0.0,max,min,delta;
		r = r / 255.0;
		g = g / 255.0;
		b = b / 255.0;
		max = Math.max(Math.max(r, g), b);
		min = Math.min(Math.min(r, g), b);
		delta = max - min;
		l = (max + min) / 2;
		s = getS(l,delta);
		if(delta == 0) {
			h = 0.0;
		}else if(max == r) {
			h = (((g - b) / delta) % 6 ) * 60;
		}else if(max == g) {
			h = ((b - r) / delta + 2.0 ) * 60;
		}else if(max == b) {
			h = ((r - g) / delta + 4.0 ) * 60;
		}
		HSL[0] = h;
		HSL[1] = s;
		HSL[2] = l;
		return HSL;
	}
	
	double getS(double l, double delta) {
		if(delta == 0.0) {
			return 0.0;
		}
		else {
			return delta / (1 - Math.abs(2 * l - 1));
		}
	}
	
	int[] HSLToRGB(double h, double s, double l) {
		int[] RGB = new int[3];
		if(h < 0.0)
			h = 0.0;
		if(h > 360.0)
			h = 0.0;
		if(s < 0.0)
			s = 0.0;
		if(s > 1.0)
			s = 1.0;
		if(l < 0.0)
			l = 0.0;
		if(l > 1.0)
			l = 1.0;
		double R,G,B,C,X,M;
		C = (1.0 - Math.abs(2.0 * l - 1.0)) * s;
		X = C * (1.0 - Math.abs(((h / 60) % 2 ) - 1));
		M = l - C / 2;
		if(h >= 0 && h < 60) {
			R = C;
			G = X;
			B = 0.0;
		}else if(h >= 60 && h < 120) {
			R = X;
			G = C;
			B = 0.0;
		}else if(h >= 120 && h < 180) {
			R = 0.0;
			G = C;
			B = X;
		}else if(h >= 180 && h < 240) {
			R = 0.0;
			G = X;
			B = C;
		}else if(h >= 240 && h < 300) {
			R = X;
			G = 0.0;
			B = C;
		}else{
			R = C;
			G = 0.0;
			B = X;
		}
		R += M;
		G += M;
		B += M;
		RGB[0] = Util.checkPixelBounds((int) (255 * R));
		RGB[1] = Util.checkPixelBounds((int) (255 * G));
		RGB[2] = Util.checkPixelBounds((int) (255 * B));
		return RGB;
	}
	
	void changePixel() {
		double R,G,B;
		int[] rgb = new int[3];
		for(int a = 0; a < height; a++) {
			for(int b = 0; b < width; b++) {
				R = 1.0 * data[a][b][0];
				G = 1.0 * data[a][b][1];
				B = 1.0 * data[a][b][2];
				double[] hsl = RGBToHSL(R,G,B);
				rgb = HSLToRGB(hsl[0] + 1.0 * HueValue, hsl[1] + SatValue / 100.0, hsl[2] + LumValue / 100.0);
				imgAft.setRGB(b, a, Util.makeColor(rgb[0], rgb[1], rgb[2]));
			}
		}
		Graphics gra = imagePanelAft.getGraphics();
		gra.drawImage(imgAft,0,0,null);
	}
	
	@Override
	void exe() {
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdRotation);
		btnCmd.setText(Util.cmdRotation);
		tfHueValue.setText("0");
		tfSatValue.setText("0");
		tfLumValue.setText("0");
		tfHueValue.setEditable(false);
		tfSatValue.setEditable(false);
		tfLumValue.setEditable(false);
		HSL.cotrolPanelTranslate = new JPanel();
		HSL.cotrolPanelTranslate.setBounds(0, 0, 1200, 150);
		sliderHue = new JSlider(-180, 180, 0);
		cotrolPanelHue.add(lbHueBeging);
		cotrolPanelHue.add(sliderHue);
		cotrolPanelHue.add(lbHueEnd);
		cotrolPanelHue.add(tfHueValue);
		cotrolPanelHue.add(lbHue);
		sliderSat = new JSlider(-100, 100, 0);
		cotrolPanelSat.add(lbSatBeging);
		cotrolPanelSat.add(sliderSat);
		cotrolPanelSat.add(lbSatEnd);
		cotrolPanelSat.add(tfSatValue);
		cotrolPanelSat.add(lbSat);
		sliderLum = new JSlider(-100, 100, 0);
		cotrolPanelLum.add(lbLumBeging);
		cotrolPanelLum.add(sliderLum);
		cotrolPanelLum.add(lbLumEnd);
		cotrolPanelLum.add(tfLumValue);
		cotrolPanelLum.add(lbLum);
		HSL.cotrolPanelTranslate.add(cotrolPanelHue);
		HSL.cotrolPanelTranslate.add(cotrolPanelSat);
		HSL.cotrolPanelTranslate.add(cotrolPanelLum);
		getContentPane().add(HSL.cotrolPanelTranslate);
	}
}