package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

/**
 * @author yangb
 *
 */
public class Main {

	private static JFrame frame;
	private JLabel lable;
	private ImageIcon imageIcon;
	private JPanel jPanel,bottomPanel;
	private FileDialog fileDialog;
	private Image realImg;
	private int frameWidth = 800;
	private int frameHeight = 500;
	private JSlider rSlider,gSlider,bSlider;
	private JMenu file; 
	
	private int flag = -1;

	private void createAndShowGUI(){
				JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("图像处理    作者:杨斌");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 10, frameWidth, frameHeight);
		
		imageIcon = new ImageIcon();
		lable = new JLabel(imageIcon);

		rSlider=new JSlider(-255,255,0);
		gSlider=new JSlider(-255,255,0);
		bSlider=new JSlider(-255,255,0);

		rSlider.addChangeListener((e)->{
			int value = rSlider.getValue();
			if(flag != 1){
				flag = 1;
			}else{
				imageIcon.setImage(realImg);
			}
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.rTransform(pix, value));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();
		});
		gSlider.addChangeListener((e)->{
			if(flag != 2){
				flag = 2;
			}else{
				imageIcon.setImage(realImg);
			}
			int value = gSlider.getValue();
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.gTransform(pix, value));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();		
		});
		bSlider.addChangeListener((e)->{
			if(flag != 3){
				flag = 3;
			}else{
				imageIcon.setImage(realImg);
			}
			int value = bSlider.getValue();
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.bTransform(pix, value));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();
		});

		bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(new Label("Red:"));
		bottomPanel.add(rSlider);

		bottomPanel.add(new Label("Green:"));
		bottomPanel.add(gSlider);

		bottomPanel.add(new Label("Blue:"));
		bottomPanel.add(bSlider);

		jPanel = new JPanel(new BorderLayout());
		jPanel.setBackground(Color.DARK_GRAY);
		jPanel.add(lable,BorderLayout.CENTER);
		jPanel.add(bottomPanel,BorderLayout.SOUTH);

		file = new JMenu("文件");
		JMenuItem f1=new JMenuItem("打开图片");  
		JMenuItem f2=new JMenuItem("重新再来");
		JMenuItem f3=new JMenuItem("另存为");

		JMenu alpha = new JMenu("通道");
		JMenuItem a1=new JMenuItem("红色通道");  
		JMenuItem a2=new JMenuItem("绿色通道");
		JMenuItem a3=new JMenuItem("蓝色通道");
		JMenuItem a4=new JMenuItem("反向通道");

		f1.addActionListener((e)->{
			fileDialog = new FileDialog(frame,"打开图片",FileDialog.LOAD);
			fileDialog.setVisible(true);
			String dir = fileDialog.getDirectory();
			String file = fileDialog.getFile();
			if(dir != null && file !=null){
				realImg = ImageEngine.loadImage(dir + file, frame.getWidth(), frame.getHeight());
				imageIcon.setImage(realImg);
				jPanel.repaint(); 
			}
		});

		f2.addActionListener((e)->{
			imageIcon.setImage(realImg);
			jPanel.repaint(); 
		});
		
		f3.addActionListener((e)->{
			fileDialog = new FileDialog(frame,"图片另存为",FileDialog.SAVE);
			fileDialog.setVisible(true);
			String dir = fileDialog.getDirectory();
			String file = fileDialog.getFile();
			if(dir != null && file !=null){
				try {
					FileOutputStream output = new FileOutputStream(new File(dir+file));
					ImageIO.write(ImageEngine.toBufferedImage(imageIcon), "png", output);
					output.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		a1.addActionListener((e)->{
			imageIcon.setImage(realImg);
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.rAlpha(pix));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();
		});
		a2.addActionListener((e)->{
			imageIcon.setImage(realImg);
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.gAlpha(pix));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();
		});
		a3.addActionListener((e)->{
			imageIcon.setImage(realImg);
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.bAlpha(pix));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();
		});
		a4.addActionListener((e)->{
			imageIcon.setImage(realImg);
			BufferedImage bufferedImage = ImageEngine.toBufferedImage(imageIcon);
			ImageEngine.computeImg(bufferedImage, (pix)->PixelMathService.reverseAlpha(pix));
			imageIcon.setImage(bufferedImage);
			jPanel.repaint();
		});

		file.add(f1);   
		file.add(f2);
		file.add(f3);
		
		alpha.add(a1);
		alpha.add(a2);
		alpha.add(a3);
		alpha.add(a4);
		JMenuBar menuBar= new  JMenuBar();
		menuBar.add(file);
		menuBar.add(alpha);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(jPanel);
		//		frame.pack(); //适应子组件大小
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		Main main = new Main();
		SwingUtilities.invokeLater(()-> main.createAndShowGUI());
	}
}
