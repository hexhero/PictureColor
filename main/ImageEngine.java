package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片引擎
 * @author yangb
 *
 */
public class ImageEngine {

	/**
	 * 计算图片
	 * @param bufImg
	 * @param pixMath
	 */
	public static void computeImg(BufferedImage bufImg,PixelMathService pixMath){
		for (int x = bufImg.getMinTileX();x<bufImg.getWidth();x++)
			for(int y=bufImg.getMinTileY();y<bufImg.getHeight();y++){
				int pixel = bufImg.getRGB(x, y);
				bufImg.setRGB(x, y, pixMath.pixMath(pixel));
			}
	}
	
	/**
	 * 图片转换
	 * @param img
	 * @return
	 */
	public static BufferedImage toBufferedImage(ImageIcon img){
		if(img.getImage() == null){
			return null;
		}
		BufferedImage bufferedImage = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		bufferedImage.getGraphics().drawImage(img.getImage(), 0, 0, img.getImageObserver());
		return bufferedImage;
	}
	
	/**
	 * 加载图片
	 * @param path
	 * @param frameWidth
	 * @param frameHeight
	 * @return
	 */
	public static Image loadImage(String path, int frameWidth, int frameHeight){
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(path));
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			//图片缩放
			int max = Math.max(width, height);
			Image smallImg = null;
			if(max == width && max > frameWidth){
				smallImg = bufferedImage.getScaledInstance((int)(frameWidth*0.8), (int)(height*((frameWidth*0.8))/width), Image.SCALE_DEFAULT);
			}else if(max == height && max > frameHeight){
				smallImg = bufferedImage.getScaledInstance((int)(width*((frameHeight*0.8)/height)), (int)(frameHeight*0.8), Image.SCALE_DEFAULT);
			}else{
				smallImg = bufferedImage;
			}
			return smallImg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
