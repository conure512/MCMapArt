package conure.mapart;
/**Each DataVersion object actually represents a range of Minecraft DataVersions;
 * all versions represented by one instance of this class will have the same map
 * colors available, and maps written will follow the same NBT format.*/
public enum DataVersion {
	V1_17("1.17+",2724),
	V1_16("1.16 - 1.16.5",2566),
	V1_12("1.12 - 1.15.2",1139),
	V1_8("1.8 - 1.11.2",100),
	V1_7("1.7 - 1.7.10",1),
	V1_0("1.0 - 1.6.4",0);
	public final String range;
	/**The LOWEST Minecraft DataVersion number covered by this object*/
	public final int id;
	private DataVersion(String range,int id) {
		this.range=range;
		this.id=id;
	}
	@Override
	public String toString() {
		return range;
	}
	public static DataVersion fromID(int id) {
		for(DataVersion dv:values())
			if(id>=dv.id)
				return dv;
		return null;
	}
}