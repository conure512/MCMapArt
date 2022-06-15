package conure.mapart;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
public class WindowConsole extends JFrame {
	private static final long serialVersionUID=1L;
	private final JTextField loadPath;
	private final JButton loadButton,viewFullMap,viewHeightMap,viewMaterials,exportButton;
	private final JCheckBox useHeightShades,useShade4;
	private final JSpinner oX,oZ,scale,heightColumn,mapID;
	private final JLabel mapIcon,errorMsg,fileCount;
	private MaterialMap data;
	public WindowConsole() {
		super("Minecraft Map Generator");
		setBounds(100,100,550,270);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel=new JPanel(null);
		JLabel label=new JLabel("Image File to Load");
		label.setBounds(2,0,105,15);
		panel.add(label);
		loadPath=new JTextField(Config.imageToLoad);
		loadPath.setBounds(2,16,250,20);
		panel.add(loadPath);
		loadButton=new JButton("Generate Map");
		loadButton.setBounds(2,36,130,20);
		panel.add(loadButton);
		errorMsg=new JLabel();
		errorMsg.setBounds(5,56,130,15);
		errorMsg.setVisible(false);
		panel.add(errorMsg);
		label=new JLabel("Map Loading Options");
		label.setBounds(260,0,125,15);
		panel.add(label);
		useHeightShades=new JCheckBox("Use Height Shading",Config.useHeightShades);
		useHeightShades.setBounds(256,15,140,20);
		panel.add(useHeightShades);
		useShade4=new JCheckBox("Use Shade4",Config.useShade4);
		useShade4.setBounds(256,35,95,20);
		panel.add(useShade4);
		label=new JLabel("Map Coordinates");
		label.setBounds(420,0,100,15);
		panel.add(label);
		label=new JLabel("x=");
		label.setBounds(420,20,20,15);
		panel.add(label);
		SpinnerNumberModel model=new SpinnerNumberModel();
		model.setValue(Config.origin[0]);
		model.setStepSize(128);
		oX=new JSpinner(model);
		oX.setBounds(435,17,50,20);
		panel.add(oX);
		label=new JLabel("z=");
		label.setBounds(420,40,20,15);
		panel.add(label);
		model=new SpinnerNumberModel();
		model.setValue(Config.origin[1]);
		model.setStepSize(128);
		oZ=new JSpinner(model);
		oZ.setBounds(435,37,50,20);
		panel.add(oZ);
		viewFullMap=new JButton("View Interactive Map");
		viewFullMap.setBounds(2,80,155,20);
		panel.add(viewFullMap);
		viewHeightMap=new JButton("View Height Map");
		viewHeightMap.setBounds(157,80,155,20);
		panel.add(viewHeightMap);
		label=new JLabel("at scale");
		label.setBounds(22,105,50,15);
		panel.add(label);
		scale=new JSpinner(new SpinnerNumberModel(Config.scale,1,10,1));
		scale.setBounds(70,102,32,20);
		panel.add(scale);
		label=new JLabel("for column x=");
		label.setBounds(170,105,100,15);
		panel.add(label);
		heightColumn=new JSpinner(new SpinnerNumberModel(Config.heightColumn,Config.heightColumn,Config.heightColumn,1));
		heightColumn.setBounds(248,102,50,20);
		panel.add(heightColumn);
		mapIcon=new JLabel("No image loaded.");
		mapIcon.setBounds(315,80,100,15);
		panel.add(mapIcon);
		viewMaterials=new JButton("View Materials");
		viewMaterials.setBounds(2,145,155,20);
		panel.add(viewMaterials);
		exportButton=new JButton("Export to Files");
		exportButton.setBounds(157,145,155,20);
		panel.add(exportButton);
		model=new SpinnerNumberModel();
		model.setMinimum(0);
		model.setValue(Config.mapID);
		label=new JLabel("Starting map ID:");
		label.setBounds(165,170,90,15);
		panel.add(label);
		mapID=new JSpinner(model);
		mapID.setBounds(258,168,40,20);
		panel.add(mapID);
		fileCount=new JLabel();
		fileCount.setBounds(165,186,120,40);
		panel.add(fileCount);
		add(panel);
		setPostLoadEnabled(false);
		loadButton.addActionListener(e -> {
			try {
				BufferedImage img=ImageIO.read(new File(loadPath.getText()));
				if(img==null) {
					errorMsg.setText("File must be an image.");
					errorMsg.setVisible(true);
					return;
				} else
					data=Main.generateMap(img,useHeightShades.isSelected(),useShade4.isSelected());
			} catch(IOException ex) {
				errorMsg.setText("Unable to open file.");
				errorMsg.setVisible(true);
				return;
			}
			errorMsg.setVisible(false);
			setPostLoadEnabled(true);
			alignColumnToAxis();
			int wD=data.pixels.length,hD=data.pixels[0].length,
					w=(int)(128.0*wD/Math.max(wD,hD)),h=(int)(128.0*hD/Math.max(wD,hD));
			BufferedImage icon=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			int[] raster=((DataBufferInt)icon.getRaster().getDataBuffer()).getData();
			for(int y=0;y<h;y++)
				for(int x=0;x<w;x++)
					raster[y*w+x]=data.pixels[x*wD/w][y*hD/h];
			mapIcon.setIcon(new ImageIcon(icon));
			mapIcon.setSize(w,h);
			int c=(int)Math.ceil(data.materials.length/128d)*(int)Math.ceil(data.materials[0].length/128d);
			fileCount.setText("<html>Exporting will create<br/>"+c+" map file"+(c==1?"":"s")+".</html>");
		});
		oX.addChangeListener(e -> alignColumnToAxis());
		viewFullMap.addActionListener(e -> new RenderWindow(data,new int[] {(int)oX.getValue(),(int)oZ.getValue()},(int)scale.getValue()).setVisible(true));
		viewHeightMap.addActionListener(e -> new HeightMapWindow(data,(int)heightColumn.getValue(),
				new int[] {(int)oX.getValue(),(int)oZ.getValue()},(int)scale.getValue()).setVisible(true));
		viewMaterials.addActionListener(e -> new MaterialWindow(data).setVisible(true));
		exportButton.addActionListener(e -> {
			try {
				int id=(int)mapID.getValue();
				for(int oy=0;oy<data.materials[0].length;oy+=128)
					for(int ox=0;ox<data.materials.length;ox+=128) {
						NBTFiles.exportMap(data.materials,new int[] {ox,oy},Main.directory+"map_"+id+".dat");
						id++;
					}
			} catch(IOException ex) {}
		});
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				Config.save(loadPath.getText(),useHeightShades.isSelected(),useShade4.isSelected(),
						new int[] {(int)oX.getValue(),(int)oZ.getValue()},
						(int)scale.getValue(),(int)heightColumn.getValue(),(int)mapID.getValue());
			}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		});
	}
	private void alignColumnToAxis() {
		if(data!=null) {
			int min=(int)oX.getValue(),max=min+data.pixels.length-1;
			heightColumn.setModel(new SpinnerNumberModel(Config.heightColumn>=min&&Config.heightColumn<=max?Config.heightColumn:min,min,max,1));
		}
	}
	private void setPostLoadEnabled(boolean enable) {
		viewFullMap.setEnabled(enable);
		viewHeightMap.setEnabled(enable);
		viewMaterials.setEnabled(enable);
		scale.setEnabled(enable);
		heightColumn.setEnabled(enable);
		exportButton.setEnabled(enable);
		mapID.setEnabled(enable);
	}
}
