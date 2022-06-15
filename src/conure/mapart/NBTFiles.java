package conure.mapart;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
public class NBTFiles {
	private static final byte END=0,BYTE=1,INT=3,BYTE_ARRAY=7,STRING=8,COMPOUND=10;
	static void exportMap(String[][] materials,int[] offset,String path) throws IOException {
		DataOutputStream writer=new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new File(path))));
		openTag(writer,COMPOUND,"");
		openTag(writer,COMPOUND,"data");
		writeTagByte(writer,"scale",0);
		writeTagString(writer,"dimension","");
		writeTagByte(writer,"trackingPosition",0);
		writeTagByte(writer,"unlimitedTracking",0);
		writeTagByte(writer,"locked",1);
		writeTagInt(writer,"xCenter",0);
		writeTagInt(writer,"zCenter",0);
		openTagByteArray(writer,"colors",16384);
		for(int y=offset[1];y<128+offset[1];y++)
			for(int x=offset[0];x<128+offset[0];x++)
				writer.write(getColorID(materials,x,y));
		writer.writeByte(END);
		writer.writeByte(END);
		writer.close();
	}
	private static int getColorID(String[][] materials,int x,int y) {
		try {
			return Constants.colorID.get(materials[x][y]);
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
	private static void writeTagInt(DataOutputStream writer,String name,int n) throws IOException {
		openTag(writer,INT,name);
		writer.writeInt(n);
	}
	private static void writeTagString(DataOutputStream writer,String name,String data) throws IOException {
		openTag(writer,STRING,name);
		int len=data.length();
		writer.write(new byte[] {(byte)(len>>8),(byte)len});
		writer.writeBytes(data);
	}
	private static void openTag(DataOutputStream writer,byte id,String name) throws IOException {
		int len=name.length();
		writer.write(new byte[] {id,(byte)(len>>8),(byte)len});
		writer.writeBytes(name);
	}
}