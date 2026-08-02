package conure.mapart;
import java.util.Vector;
public enum DataVersion {
	V26_1(4786),
	V1_17(2724),
	V1_16(2566),
	V1_13(1519),
	V1_12(1139),
	V1_8_1(100),
	V1_7(1),
	V1_0(0);
	public final int id;
	private DataVersion(int id) {
		this.id=id;
	}
	public static DataVersion fromID(int id) {
		for(DataVersion dv:values())
			if(id>=dv.id)
				return dv;
		return null;
	}
	public static enum ColorRange {
		V1_17("1.17+"),
		V1_16("1.16+"),
		V1_12("1.12+"),
		V1_8_1("1.8.1+"),
		V1_7("1.7+"),
		V1_0("1.0+");
		public final DataVersion minVersion;
		public final String description;
		private ColorRange(String description) {
			minVersion=DataVersion.valueOf(name());
			this.description=description;
		}
		public int minID() {
			return minVersion.id;
		}
		@Override
		public String toString() {
			return description;
		}
	}
	public static enum ExportRange {
		V26_1("26.1+"),
		V1_17("1.17 - 1.21.11"),
		V1_16("1.16 - 1.16.5"),
		V1_13("1.13 - 1.15.2"),
		V1_12("1.12 - 1.12.2"),
		V1_8_1("1.8.1 - 1.11.2"),
		V1_7("1.7 - 1.8"),
		V1_0("1.0 - 1.6.4");
		public final DataVersion minVersion;
		public final String description;
		private ExportRange(String description) {
			minVersion=DataVersion.valueOf(name());
			this.description=description;
		}
		public int minID() {
			return minVersion.id;
		}
		@Override
		public String toString() {
			return description;
		}
		public static Vector<ExportRange> above(int minVersion) {
			Vector<ExportRange> list=new Vector<ExportRange>();
			for(ExportRange e:values())
				if(e.minVersion.id>=minVersion)
					list.add(e);
			return list;
		}
	}
}