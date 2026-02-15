package conure.mapart;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
public final class Config {
	private static final File file=new File(Main.directory,"config.dat");
	public static String imageToLoad;
	public static boolean useHeightShades,useShade4;
	public static int[] origin;
	public static int scale,heightColumn,mapID,versionIndex;
	public static void load() {
		try(DataInputStream reader=new DataInputStream(new GZIPInputStream(new FileInputStream(file)))) {
			int next=reader.readShort();
			imageToLoad="";
			for(int i=0;i<next;i++)
				imageToLoad+=(char)reader.readByte();
			next=reader.readByte();
			useHeightShades=(next&1)==1;
			useShade4=(next&2)==2;
			origin=new int[] {reader.readInt(),reader.readInt()};
			scale=reader.readInt();
			heightColumn=reader.readInt();
			if(heightColumn<origin[0]||heightColumn>origin[0]+127)
				heightColumn=origin[0];
			mapID=reader.readInt();
			versionIndex=reader.readInt();
		} catch(IOException e) {
			imageToLoad="";
			useHeightShades=true;
			useShade4=false;
			origin=new int[] {-64,-64};
			scale=1;
			heightColumn=-64;
			mapID=0;
			versionIndex=0;
		}
	}
	public static void save(String img,boolean heightShades,boolean shade4,int[] org,int scl,int col,int id,int vInd) {
		try(DataOutputStream writer=new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)))) {
			writer.writeShort(img.length());
			writer.writeBytes(img);
			int boolBits=0;
			if(heightShades)
				boolBits++;
			if(shade4)
				boolBits+=2;
			writer.writeByte(boolBits);
			writer.writeInt(org[0]);
			writer.writeInt(org[1]);
			writer.writeInt(scl);
			writer.writeInt(col);
			writer.writeInt(id);
			writer.writeInt(vInd);
			writer.close();
		} catch(IOException e) {}
	}
}