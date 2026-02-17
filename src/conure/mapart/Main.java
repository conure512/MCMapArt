package conure.mapart;
import java.awt.image.BufferedImage;
import java.io.File;
public class Main {
	static String directory;
	static {
		try {
			directory=new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent()+File.separatorChar;
		} catch(Exception e) {
			directory="";
		}
		Session.load();
		System.setProperty("sun.java2d.uiScale","1");
	}
	public static void main(String[] args) {
		new WindowConsole().setVisible(true);
	}
	static MaterialMap generateMap(BufferedImage img,boolean useHeightShades,boolean useShade4,int maxVersion) {
		int w=img.getWidth(),h=img.getHeight();
		int[][] pixels=new int[w][h];
		String[][] materialMap=new String[w][h];
		for(int y=0;y<h;y++)
			for(int x=0;x<w;x++) {
				int[] pix=getNearestColor(fromRGB(img.getRGB(x,y)),useHeightShades,useShade4,maxVersion);
				pixels[x][y]=toARGB(pix);
				materialMap[x][y]=getColorID(pix);
			}
		return new MaterialMap(pixels,materialMap);
	}
	static int[] getSteps(String[] materialList) {
		int[] steps=new int[materialList.length];
		for(int z=0;z<materialList.length;z++) {
			if(materialList[z].endsWith(Constants.LIGHT_SUFFIX))
				steps[z]=1;
			else if(materialList[z].endsWith(Constants.DARK_SUFFIX))
				steps[z]=-1;
			else
				steps[z]=0;
		}
		return steps;
	}
	static void setPixelRegion(int[] raster,int scale,int w,int x,int y,int val) {
		w*=scale;
		x*=scale;
		y*=scale;
		for(int j=0;j<scale;j++)
			for(int i=0;i<scale;i++)
				raster[(y+j)*w+x+i]=val;
	}
	private static int[] fromRGB(int rgb) {
		return new int[] {(rgb&0xff0000)>>16,(rgb&0xff00)>>8,rgb&0xff};
	}
	private static int toARGB(int[] rgb) {
		return 0xff000000+(rgb[0]<<16)+(rgb[1]<<8)+rgb[2];
	}
	private static String getColorID(int[] color) {
		for(MapColor c:Constants.colors) {
			if(color.equals(c.base))
				return c.name;
			if(color.equals(c.light))
				return c.name+Constants.LIGHT_SUFFIX;
			if(color.equals(c.dark))
				return c.name+Constants.DARK_SUFFIX;
			if(color.equals(c.shade4))
				return c.name+Constants.SHADE4_SUFFIX;
		}
		return null;
	}
	private static int[] getNearestColor(int[] color,boolean useHeightShades,boolean useShade4,int maxVersion) {
		double min=Double.MAX_VALUE,d;
		int[] sol=null;
		for(MapColor c:Constants.colors) {
			if(c.dataVersion>maxVersion)
				break;
			d=sqrDist(color,c.base);
			if(d<min) {
				min=d;
				sol=c.base;
			}
			if(useHeightShades) {
				d=sqrDist(color,c.light);
				if(d<min) {
					min=d;
					sol=c.light;
				}
				d=sqrDist(color,c.dark);
				if(d<min) {
					min=d;
					sol=c.dark;
				}
			}
			if(useShade4) {
				d=sqrDist(color,c.shade4);
				if(d<min) {
					min=d;
					sol=c.shade4;
				}
			}
		}
		return sol;
	}
	private static double sqrDist(int[] c1,int[] c2) {
		double d=0;
		for(int i=0;i<3;i++)
			d+=Math.pow(c1[i]-c2[i],2);
		return d;
	}
}
