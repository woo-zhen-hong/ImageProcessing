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

public class Translation extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();;
	static JTextField tfDeltaX = new JTextField(5);
	static JTextField tfDeltaY = new JTextField(5);
	static JLabel lbDeltaY = new JLabel("y軸位移");
	static JLabel lbDeltaX = new JLabel("x軸位移");
	
	Translation() {
	};

	Translation(File file) {
		super(file);
	}
	
	@Override
	void exe() {
		{
			 
			imgAft = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			String X = tfDeltaX.getText();
			String Y = tfDeltaY.getText();
			int x = Integer.parseInt(X);
			int y = Integer.parseInt(Y);
			
			Util.saveImg = imgAft;
			Graphics g = imagePanelAft.getGraphics();
			imagePanelAft.paintComponent(g);
			g.drawImage(imgBef, x, y, null);
		}
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdTranslation);
		btnCmd.setText(Util.cmdTranslation);
		tfDeltaX.setText("0");
		tfDeltaY.setText("0");
		Translation.cotrolPanelTranslate = new JPanel();
		Translation.cotrolPanelTranslate.setBounds(0, 0, 500, 150);
		Translation.cotrolPanelTranslate.add(lbDeltaX);
		Translation.cotrolPanelTranslate.add(tfDeltaX);
		Translation.cotrolPanelTranslate.add(lbDeltaY);
		Translation.cotrolPanelTranslate.add(tfDeltaY);
		getContentPane().add(Translation.cotrolPanelTranslate);
	}
}