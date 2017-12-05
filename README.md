# PictureColor
图片调色的java实现

运行环境 jdk8 以上.

示例图:
![示例图](https://github.com/cn-Gongfu/PictureColor/raw/master/demo.png)

### 颜色计算代码
```java
	
	/**
	 * 蓝色变换
	 * @param pix
	 * @param offSet
	 * @return
	 */
	public static int bTransform(int pix, int offSet){
		int f1 = pix & 0x000000ff;
		if((f1+offSet) < 0){
			return pix & 0xffffff00;
		}else if((f1+offSet) > 255){
			return pix & 0xffffff00 | 255;
		}else{
			return pix + offSet;
		}
	}
	
	/**
	 * 红色变换
	 * @param pix
	 * @param offSet
	 * @return
	 */
	public static int rTransform(int pix, int offSet){
		offSet = offSet << 16;
		int f1 = pix & 0x00ff0000;
		if((f1+offSet) < 0){
			return pix & 0xff00ffff;
		}else if((f1+offSet) > 0x00ff0000){
			return pix & 0xff00ffff | 0x00ff0000;
		}else{
			return pix + offSet;
		}
	}
	
	/**
	 * 绿色变换
	 * @param pix
	 * @param offSet
	 * @return
	 */
	public static int gTransform(int pix, int offSet){
		offSet = offSet << 8;
		int f1 = pix & 0x0000ff00;
		if((f1+offSet) < 0){
			return pix & 0xffff00ff;
		}else if((f1+offSet) > 0x0000ff00){
			return pix & 0xffff00ff | 0x0000ff00;
		}else{
			return pix + offSet;
		}
	}
	
	/**
	 * 反向通道
	 * @param pix
	 * @return
	 */
	public static int reverseAlpha(int pix){
		return (~pix)+0xff000000;
	}
	
	/**
	 * 红色通道
	 * @param pix
	 * @return
	 */
	public static int rAlpha(int pix){
		return pix & 0xffff0000;
	}
	
	/**
	 * 绿色通道
	 * @param pix
	 * @return
	 */
	public static int gAlpha(int pix){
		return pix & 0xff00ff00;
	}
	
	/**
	 * 蓝色通道
	 * @param pix
	 * @return
	 */
	public static int bAlpha(int pix){
		return pix & 0xff0000ff;
	}

```

