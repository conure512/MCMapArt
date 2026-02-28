package conure.mapart;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class HeightMapWindow extends JFrame {
	private static final long serialVersionUID=1L;
	private final MapColor[] materialList;
	private final JLabel imgLabel,materialLabel;
	public HeightMapWindow(MapColor[][] map,int x,int[] origin,int scale) {
		super("Height Map for x="+x);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		materialList=map[x-origin[0]];
		int[] steps=getSteps(materialList),elv=new int[steps.length];
		int min=0,max=0;
		for(int i=1;i<steps.length;i++) {
			elv[i]=elv[i-1]+steps[i];
			if(elv[i]<min)
				min=elv[i];
			else if(elv[i]>max)
				max=elv[i];
		}
		int w=elv.length,h=max-min+1;
		BufferedImage heightImage=new BufferedImage(scale*w,scale*h,BufferedImage.TYPE_INT_ARGB);
		int[] raster=((DataBufferInt)heightImage.getRaster().getDataBuffer()).getData();
		for(int z=0;z<w;z++)
			Main.setPixelRegion(raster,scale,w,z,h-elv[z]+min-1,map[x-origin[0]][z].intARGB);
		setSize(scale*w+30,scale*h+70);
		JPanel panel=new JPanel(null);
		imgLabel=new JLabel(new ImageIcon(heightImage));
		imgLabel.setSize(scale*w,scale*h);
		panel.add(imgLabel);
		materialLabel=new JLabel("No pixel selected.");
		materialLabel.setBounds(0,scale*h,400,15);
		panel.add(materialLabel);
		imgLabel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {}
			@Override
			public void mouseMoved(MouseEvent e) {
				int z=e.getX()/scale;
				materialLabel.setText("["+x+","+(origin[1]+z)+"] "+materialList[z].name);
			}
		});
		add(panel);
	}
	private static int[] getSteps(MapColor[] materialList) {
		int[] steps=new int[materialList.length];
		for(int z=0;z<materialList.length;z++) {
			if(materialList[z].name.endsWith(Constants.LIGHT_SUFFIX))
				steps[z]=1;
			else if(materialList[z].name.endsWith(Constants.DARK_SUFFIX))
				steps[z]=-1;
			else
				steps[z]=0;
		}
		return steps;
	}
}