package conure.mapart;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
public class NBTFiles {
	private static final byte END=0,BYTE=1,SHORT=2,INT=3,BYTE_ARRAY=7,STRING=8,LIST=9,COMPOUND=10;
	static void exportMap(String path,String[][] materials,int[] offset,int dataVersion) throws IOException {
		DataOutputStream writer=new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new File(path))));
		openTag(writer,COMPOUND,"");
		openTag(writer,COMPOUND,"data");
		openTagByteArray(writer,"colors",16384);
		for(int y=offset[1];y<128+offset[1];y++)
			for(int x=offset[0];x<128+offset[0];x++)
				writer.write(getColorID(materials,x,y));
		writeTagByte(writer,"scale",0);
		writeTagByte(writer,"trackingPosition",0);
		writeTagInt(writer,"xCenter",0);
		writeTagInt(writer,"zCenter",0);
		writeTagByte(writer,"unlimitedTracking",0);
		writeTagByte(writer,"locked",1);
		openTagList(writer,"banners",COMPOUND,0);
		openTagList(writer,"frames",COMPOUND,0);
		if(dataVersion<DataVersion.V1_13.id) {
			writeTagShort(writer,"width",128);
			writeTagShort(writer,"height",128);
		}
		if(dataVersion>=DataVersion.V1_16.id)
			writeTagString(writer,"dimension","");
		else
			writeTagByte(writer,"dimension",20);
		writer.writeByte(END);
		writeTagInt(writer,"DataVersion",dataVersion);
		writer.writeByte(END);
		writer.close();
	}
	private static int getColorID(String[][] materials,int x,int y) {
		try {
			return Constants.colorIDs.get(materials[x][y]);
		} catch(IndexOutOfBoundsException e) {
			return 0;
		}
	}
	private static void writeTagByte(DataOutputStream writer,String name,int n) throws IOException {
		openTag(writer,BYTE,name);
		writer.writeByte(n);
	}
	private static void openTagByteArray(DataOutputStream writer,String name,int size) throws IOException {
		openTag(writer,BYTE_ARRAY,name);
		writer.writeInt(size);
	}
	private static void writeTagShort(DataOutputStream writer,String name,int n) throws IOException {
		openTag(writer,SHORT,name);
		writer.writeShort(n);
	}
	private static void writeTagInt(DataOutputStream writer,String name,int n) throws IOException {
		openTag(writer,INT,name);
		writer.writeInt(n);
	}
	static void writeTagString(DataOutputStream writer,String name,String data) throws IOException {
		openTag(writer,STRING,name);
		int len=data.length();
		writer.write(new byte[] {(byte)(len>>8),(byte)len});
		writer.writeBytes(data);
	}
	private static void openTagList(DataOutputStream writer,String name,byte tagId,int size) throws IOException {
		openTag(writer,LIST,name);
		writer.write(tagId);
		writer.writeInt(size);
	}
	private static void openTag(DataOutputStream writer,byte id,String name) throws IOException {
		int len=name.length();
		writer.write(new byte[] {id,(byte)(len>>8),(byte)len});
		writer.writeBytes(name);
	}
}