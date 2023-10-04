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

public class Segment extends AbstractBasic {
	private static final long serialVersionUID = 1L;
	static JPanel cotrolPanelTranslate = new JPanel();;
	JButton btnNext = new JButton("Next Object");;
	JButton btnPrev = new JButton("Prev Object");;
	int number = 0;
	int[][][] newData;
	int[][] seg;
	int order = 1;
	
	Segment() {
	};

	Segment(File file) {
		super(file);
	}
	
	btnNext.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent arg0) {
		    showImg(number);
		    number+=2;
		    // put your code here

		   }
		  });

	  btnPrev.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent arg0) {
	    showImg(number);
	    number-=2;
	    // put your code here
	
	   }
	  });
	
	  void DFS2(int i, int j) {
		  seg[i][j] = order;
		  // 中右
		  
		  // 左下角
		  if (i + 1 < height && j - 1 >= 0) {
		   if (data[i + 1][j - 1][0] == 0 && seg[i + 1][j - 1] == 0) {
		    DFS2(i + 1, j - 1);
		   }
		  }
		  // 中下
		  
		  // 右下角
		  if (i + 1 < height && j + 1 < width) {
		   if (data[i + 1][j + 1][0]  == 0 && seg[i + 1][j + 1] == 0) {
		    DFS2(i + 1, j + 1);
		   }
		  }
		  if (i - 1 >= 0 && j - 1 >= 0) {
		   if (data[i - 1][j - 1][0]  == 0 && seg[i - 1][j - 1] == 0) {
		    DFS2(i - 1, j - 1);
		   }
		  }
		  // 中上
		  
		  // 右上
		  if (i - 1 >= 0 && j + 1 < width) {
		   if (data[i - 1][j + 1][0] == 0 && seg[i - 1][j + 1] == 0) {
		    DFS2( i - 1, j + 1);
		   }
		  }
		  // 中左
		  

		 }

		 void seg(BufferedImage img) {
		  seg = new int[height][width];
		  for (int i = 0; i < height; i++) {
		   for (int j = 0; j < width; j++) {
		    seg[i][j] = 0;
		   }
		  }
		  for (int i = 0; i < height; i++) {
		   for (int j = 0; j < width; j++) {
		    if (data[i][j][0] == 0 && seg[i][j] == 0) {
		     order++;
		     DFS2(i, j);
		    }
		   }
		  }
		 }
		 
	  void showImg(int order) {
		  newData = new int[height][width][3];
		  for (int A = 0; A < 3; A++) {
		   for (int B = 0; B < height; B++) {
		    for (int C = 0; C < width; C++) {
		     newData[B][C][A] = 255;
		    }
		   }
		  }
		  for (int i = 0; i < height; i++) {
		   for (int j = 0; j < width; j++) {
		    if (seg[i][j] == number || seg[i][j] == number+1) {
		     newData[i][j][0] = 0;
		     newData[i][j][1] = 0;
		     newData[i][j][2] = 0;
		    }
		   }
		  }
		  imgAft = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		  for (int y = 0; y < height; y++) {
		   for (int x = 0; x < width; x++) {
		    int rgb = Util.makeColor(newData[y][x][0], newData[y][x][1], newData[y][x][2]);
		    imgAft.setRGB(x, y, rgb);
		   }
		  }
		  Util.drawImg(imagePanelAft, imgAft);
		 }
	  
	@Override
	void exe() {
		{
			seg(imgBef);
		}
		
	}

	@Override
	void decorate() {
		setTitle(Util.cmdSegment);
		btnCmd.setText(Util.cmdSegment);
		Segment.cotrolPanelTranslate = new JPanel();
		Segment.cotrolPanelTranslate.setBounds(0, 0, 500, 150);
		Segment.cotrolPanelTranslate.add(btnNext);
		Segment.cotrolPanelTranslate.add(btnPrev);
	}
}