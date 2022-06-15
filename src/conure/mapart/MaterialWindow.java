package conure.mapart;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class MaterialWindow extends JFrame {
	private static final long serialVersionUID=1L;
	public MaterialWindow(MaterialMap data) {
		super("Materials");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		HashMap<String,Integer> counts=data.getMaterialCounts();
		int len=counts.keySet().size();
		setSize(300,18*(2+len));
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
}