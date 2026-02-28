package conure.mapart;
public class MapColor {
	public static final MapColor NONE=new MapColor("NONE",0,new int[]{0,0,0},0);
	public final String name;
	public final int[] rgb;
	public final int colorID,intARGB;
	private MapColor(String name,int alpha,int[] rgb,int colorID) {
		this.name=name;
		this.rgb=rgb;
		this.colorID=colorID;
		intARGB=alpha+(rgb[0]<<16)+(rgb[1]<<8)+rgb[2];
	}
	public MapColor(String name,int[] rgb,int colorID) {
		this(name,0xff000000,rgb,colorID);
	}
	@Override
	public String toString() {
		return name;
	}
}