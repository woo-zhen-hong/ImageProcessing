package app;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Mosaic extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	Point[] originalPoints;
	static Mosaic frame;
	enum State {BEGIN, DRAG_ORG, ADJUSTABLE, POINT_SELECTED, FINISH_SELECTION};
	enum MouseState {PRESSED, RELEASED};
	State state;
	MouseState mState;
	boolean adjustable;
	int[][][] newData;
	
	Mosaic() {
	};

	Mosaic(File file) {
		super(file);
	}
	
	Point [] makePoints(int left, int top, int right, int bottom) {
		 Point [] points = new Point[4];
		 points[0] = new Point(left, top);
		 points[1] = new Point(right, top);
		 points[2] = new Point(right, bottom);
		 points[3] = new Point(left, bottom);
        return points;
	 }
	
	class MA extends MouseAdapter {
		boolean mousePressed;
		boolean mouseReleased;
		int startX;
		int startY;
		int endX;
		int endY;
		int dragPoint = -1;
		
	    public void mouseDragged(MouseEvent arg0) {
			//begin to draw a rectangle
			if (state == State.BEGIN) {
				startX = arg0.getX();
				startY = arg0.getY();
				state = State.DRAG_ORG;
				return;
			}
			int x = arg0.getX();
			int y = arg0.getY();

			//update original rectangle
			if (state == State.DRAG_ORG) {
				imagePanelBef.paintComponent(imagePanelBef.getGraphics(), startX, startY, x, y, imgAft, frame);
			}
			endX = x;
			endY = y;
		}

	    public void mousePressed(MouseEvent arg0) {
			mState = MouseState.PRESSED;
		}

		public void mouseReleased(MouseEvent arg0) {
			mState = MouseState.RELEASED;
			//finish the original rectangle
			if (state == State.DRAG_ORG) {
				originalPoints = makePoints(startX, startY, endX, endY);
				adjustable = true;
				state = State.ADJUSTABLE;
			}
		}
	}
	
	public void mosaic(int startX, int startY, int endX, int endY) {
		int[] ma = new int[3];
		for(int i=0; i<3; i++) {
			double sum = 0;
			int count = 0;
			for(int a= startY; a< endY; a++) {
				for(int b = startX; b < endX; b++) {
					sum += data[a][b][i];
					count++;
				}
			}
			ma[i] = Util.checkPixelBounds((int) (sum/count));
		}
		for(int x=startY; x<endY; x++) {
			for(int y=startX; y<endX; y++) {
				for(int z=0;z<3;z++) {
					newData[x][y][z] = ma[z];
				}
			}
		}
	}
	
	@Override
	void exe() {
		{
			for(int a = originalPoints[0].y; a <= originalPoints[2].y - 10 ; a = a + 10) {
				for(int b = originalPoints[0].x; b <= originalPoints[2].x - 10; b = b + 10) {
					mosaic(b,a,b+10,a+10);
				}
			}
			for(int a = 0; a < height; a++) {
				for(int b = 0; b< width; b++) {
					int rgb = Util.makeColor(newData[a][b][0], newData[a][b][1], newData[a][b][2]);
					imgAft.setRGB(b, a, rgb);
				}
			}
			Util.drawImg(imagePanelAft, imgAft);
		}
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdMosaic);
		btnCmd.setText(Util.cmdMosaic);
	}
}