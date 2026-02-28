package conure.mapart;
public class MapColorCategory {
	public final String name;
	public final int colorIndex,dataVersion;
	public final MapColor base,light,dark,shade4;
	public MapColorCategory(String name,int colorIndex,int dataVersion,int[] lightRGB) {
		this.name=name;
		this.colorIndex=colorIndex;
		this.dataVersion=dataVersion;
		int[] baseRGB=new int[3],darkRGB=new int[3],shade4RGB=new int[3];
		for(int i=0;i<3;i++) {
			baseRGB[i]=(int)(lightRGB[i]*.86);
			darkRGB[i]=(int)(lightRGB[i]*.71);
			shade4RGB[i]=(int)(lightRGB[i]*.53);
		}
		dark=new MapColor(name+Constants.DARK_SUFFIX,darkRGB,4*colorIndex);
		base=new MapColor(name,baseRGB,4*colorIndex+1);
		light=new MapColor(name+Constants.LIGHT_SUFFIX,lightRGB,4*colorIndex+2);
		shade4=new MapColor(name+Constants.SHADE4_SUFFIX,shade4RGB,4*colorIndex+3);
	}
}