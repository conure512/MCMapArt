package conure.mapart;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class RenderWindow extends JFrame {
	private static final long serialVersionUID=1L;
	private String[][] materialMap;
	private final JLabel imgLabel,materialLabel;
	public RenderWindow(MaterialMap data,int[] origin,int scale) {
		super("Map View");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		materialMap=data.materials;
		int w=data.pixels.length,h=data.pixels[0].length;
		BufferedImage mapImage=new BufferedImage(scale*w,scale*h,BufferedImage.TYPE_INT_ARGB);
		int[] raster=((DataBufferInt)mapImage.getRaster().getDataBuffer()).getData();
		for(int y=0;y<h;y++)
			for(int x=0;x<w;x++)
				Main.setPixelRegion(raster,scale,w,x,y,data.pixels[x][y]);
		w*=scale;
		h*=scale;
		setSize(w+30,h+70);
		JPanel panel=new JPanel(null);
		imgLabel=new JLabel(new ImageIcon(mapImage));
		imgLabel.setSize(w,h);
		panel.add(imgLabel);
		materialLabel=new JLabel("No pixel selected.");
		materialLabel.setBounds(0,h,400,15);
		imgLabel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {}
			@Override
			public void mouseMoved(MouseEvent e) {
				int x=e.getX()/scale,y=e.getY()/scale;
				materialLabel.setText("["+(origin[0]+x)+","+(origin[1]+y)+"] "+materialMap[x][y]);
			}
		});
		panel.add(materialLabel);
		add(panel);
	}
}