package conure.mapart;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class MaterialWindow extends JFrame {
	private static final long serialVersionUID=512002003L;
	public MaterialWindow(MapColor[][] data) {
		this(getMaterialCounts(data));
	}
	public MaterialWindow(HashMap<String,Integer> counts) {
		super("Materials");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		int len=counts.keySet().size();
		setSize(300,16*(5+len));
		JPanel panel=new JPanel(new GridLayout(len+1,2));
		panel.setSize(400,16*(2+len));
		panel.add(new JLabel("Material Type"));
		panel.add(new JLabel("Amount"));
		for(String mat:counts.keySet()) {
			panel.add(new JLabel(mat));
			panel.add(new JLabel(""+counts.get(mat)));
		}
		JPanel nullPanel=new JPanel(null);
		nullPanel.add(panel);
		add(nullPanel);
	}
	private static HashMap<String,Integer> getMaterialCounts(MapColor[][] data) {
		HashMap<String,Integer> counts=new HashMap<String,Integer>();
		Integer n;
		String mat;
		for(MapColor[] col:data)
			for(MapColor pix:col) {
				mat=pix.name;
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