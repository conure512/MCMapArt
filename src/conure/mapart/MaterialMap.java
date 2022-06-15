package conure.mapart;
import java.util.HashMap;
public class MaterialMap {
	public int[][] pixels;
	public String[][] materials;
	public MaterialMap(int[][] pix,String[][] mat) {
		pixels=pix;
		materials=mat;
	}
	public HashMap<String,Integer> getMaterialCounts() {
		HashMap<String,Integer> counts=new HashMap<String,Integer>();
		Integer n;
		for(String[] col:materials)
			for(String mat:col) {
				if(mat.endsWith(Constants.LIGHT_SUFFIX))
					mat=mat.substring(0,mat.length()-Constants.LIGHT_SUFFIX.length());
				else if(mat.endsWith(Constants.DARK_SUFFIX))
					mat=mat.substring(0,mat.length()-Constants.DARK_SUFFIX.length());
				else if(mat.endsWith(Constants.SHADE4_SUFFIX))
					mat="SHADE4 (Unobtainable)";
				n=counts.get(mat);
				n=(n==null)?1:n+1;
				counts.put(mat,n);
			}
		return counts;
	}
}