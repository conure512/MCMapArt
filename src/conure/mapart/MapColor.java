package conure.mapart;
public class MapColor {
	public final String name;
	public final int dataVersion;
	public final int[] base=new int[3],light=new int[3],dark=new int[3],shade4=new int[3];
	public MapColor(String name,int dataVersion,int r,int g,int b) {
		this.name=name;
		this.dataVersion=dataVersion;
		light[0]=r;
		light[1]=g;
		light[2]=b;
		for(int i=0;i<3;i++) {
			base[i]=(int)(light[i]*.86);
			dark[i]=(int)(light[i]*.71);
			shade4[i]=(int)(light[i]*.53);
		}
	}
}