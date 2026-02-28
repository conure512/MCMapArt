package conure.mapart;
import java.util.LinkedList;
import java.util.List;
public final class Constants {
	public static final List<MapColorCategory> colors=new LinkedList<MapColorCategory>();
	public static final String LIGHT_SUFFIX="_LIGHT",DARK_SUFFIX="_DARK",SHADE4_SUFFIX="_SHADE4";
	private static int colorCount=0,dataVersion;
	static {
		dataVersion=DataVersion.V1_0.id;
		addColor(127,178,56,"GRASS"); //1
		addColor(247,233,163,"SAND");
		addColor(199,199,199,"WOOL");
		addColor(255,0,0,"FIRE");
		addColor(160,160,255,"ICE"); //5
		addColor(167,167,167,"METAL");
		addColor(0,124,0,"PLANT");
		addColor(255,255,255,"SNOW");
		addColor(164,168,184,"CLAY");
		addColor(151,109,77,"DIRT"); //10
		addColor(112,112,112,"STONE");
		addColor(64,64,255,"WATER");
		addColor(143,119,72,"WOOD");
		dataVersion=DataVersion.V1_7.id;
		addColor(255,252,245,"QUARTZ");
		addColor(216,127,51,"COLOR_ORANGE"); //15
		addColor(178,76,216,"COLOR_MAGENTA");
		addColor(102,153,216,"COLOR_LIGHT_BLUE");
		addColor(229,229,51,"COLOR_YELLOW");
		addColor(127,204,25,"COLOR_LIGHT_GREEN");
		addColor(242,127,165,"COLOR_PINK"); //20
		addColor(76,76,76,"COLOR_GRAY");
		addColor(153,153,153,"COLOR_LIGHT_GRAY");
		addColor(76,127,153,"COLOR_CYAN");
		addColor(127,63,178,"COLOR_PURPLE");
		addColor(51,76,178,"COLOR_BLUE"); //25
		addColor(102,76,51,"COLOR_BROWN");
		addColor(102,127,51,"COLOR_GREEN");
		addColor(153,51,51,"COLOR_RED");
		addColor(25,25,25,"COLOR_BLACK");
		addColor(250,238,77,"GOLD"); //30
		addColor(92,219,213,"DIAMOND");
		addColor(74,128,255,"LAPIS");
		addColor(0,217,58,"EMERALD");
		addColor(129,86,49,"PODZOL");
		dataVersion=DataVersion.V1_8.id;
		addColor(112,2,0,"NETHER"); //35
		dataVersion=DataVersion.V1_12.id;
		addColor(209,177,161,"TERRACOTTA_WHITE");
		addColor(159,82,36,"TERRACOTTA_ORANGE");
		addColor(149,87,108,"TERRACOTTA_MAGENTA");
		addColor(112,108,138,"TERRACOTTA_LIGHT_BLUE");
		addColor(186,133,36,"TERRACOTTA_YELLOW"); //40
		addColor(103,117,53,"TERRACOTTA_LIGHT_GREEN");
		addColor(160,77,78,"TERRACOTTA_PINK");
		addColor(57,41,35,"TERRACOTTA_GRAY");
		addColor(135,107,98,"TERRACOTTA_LIGHT_GRAY");
		addColor(87,92,92,"TERRACOTTA_CYAN"); //45
		addColor(122,73,88,"TERRACOTTA_PURPLE");
		addColor(76,62,92,"TERRACOTTA_BLUE");
		addColor(76,50,35,"TERRACOTTA_BROWN");
		addColor(76,82,42,"TERRACOTTA_GREEN");
		addColor(142,60,46,"TERRACOTTA_RED"); //50
		addColor(37,22,16,"TERRACOTTA_BLACK");
		dataVersion=DataVersion.V1_16.id;
		addColor(189,48,49,"CRIMSON_NYLIUM");
		addColor(148,63,97,"CRIMSON_STEM");
		addColor(92,25,29,"CRIMSON_HYPHAE");
		addColor(22,126,134,"WARPED_NYLIUM"); //55
		addColor(58,142,140,"WARPED_STEM");
		addColor(86,44,62,"WARPED_HYPHAE");
		addColor(20,180,133,"WARPED_WART_BLOCK");
		dataVersion=DataVersion.V1_17.id;
		addColor(100,100,100,"DEEPSLATE");
		addColor(216,175,147,"RAW_IRON"); //60
		addColor(127,167,150,"GLOW_LICHEN");
	}
	private static void addColor(int r,int g,int b,String name) {
		colorCount++;
		colors.add(new MapColorCategory(name,colorCount,dataVersion,new int[] {r,g,b}));
	}
}