package conure.mapart;
public class MapColor {
	public final String name;
	public final int[] rgb;
	public final int colorID,intARGB;
	public MapColor(String name,int[] rgb,int colorID) {
		this.name=name;
		this.rgb=rgb;
		this.colorID=colorID;
		intARGB=0xff000000+(rgb[0]<<16)+(rgb[1]<<8)+rgb[2];
	}
	@Override
	public String toString() {
		return name;
	}
}