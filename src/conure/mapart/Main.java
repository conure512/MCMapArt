package conure.mapart;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
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
	static MapColor[][] generateMap(BufferedImage img,boolean useHeightShades,boolean useShade4,int maxVersion) {
		int w=img.getWidth(),h=img.getHeight();
		MapColor[][] map=new MapColor[w][h];
		for(int y=0;y<h;y++)
			for(int x=0;x<w;x++)
				map[x][y]=getNearestColor(fromRGB(img.getRGB(x,y)),useHeightShades,useShade4,maxVersion);
		return map;
	}
	static void setPixelRegion(int[] raster,int scale,int w,int x,int y,int val) {
		w*=scale;
		x*=scale;
		y*=scale;
		for(int j=0;j<scale;j++)
			for(int i=0;i<scale;i++)
				raster[(y+j)*w+x+i]=val;
	}
	static HashMap<String,Integer> getMaterialCounts(MapColor[][] data) {
		HashMap<String,Integer> counts=new HashMap<String,Integer>();
		Integer n;
		String mat;
		for(MapColor[] col:data)
			for(MapColor pix:col) {
				mat=pix.name;
				if(mat.endsWith(Constants.LIGHT_SUFFIX))
					mat=mat.substring(0,mat.length()-Constants.LIGHT_SUFFIX.length());
				else if(mat.endsWith(Constants.DARK_SUFFIX))
					mat=mat.substring(0,mat.length()-Constants.DARK_SUFFIX.length());
				else if(mat.endsWith(Constants.SHADE4_SUFFIX))
					mat="SHADE4 (Unobtainable)";
				n=counts.get(mat);
				n=(n==null)?1:n+1;
				counts.put(mat,n);
			}
		return counts;
	}
	private static int[] fromRGB(int rgb) {
		return new int[] {(rgb&0xff0000)>>16,(rgb&0xff00)>>8,rgb&0xff};
	}
	private static MapColor getNearestColor(int[] color,boolean useHeightShades,boolean useShade4,int maxVersion) {
		double min=Double.MAX_VALUE,d;
		MapColor sol=null;
		for(MapColorCategory c:Constants.colors) {
			if(c.dataVersion>maxVersion)
				break;
			d=sqrDist(color,c.base.rgb);
			if(d<min) {
				min=d;
				sol=c.base;
			}
			if(useHeightShades) {
				d=sqrDist(color,c.light.rgb);
				if(d<min) {
					min=d;
					sol=c.light;
				}
				d=sqrDist(color,c.dark.rgb);
				if(d<min) {
					min=d;
					sol=c.dark;
				}
			}
			if(useShade4) {
				d=sqrDist(color,c.shade4.rgb);
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
