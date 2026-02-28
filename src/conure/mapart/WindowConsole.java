package conure.mapart;
import java.awt.event.ActionEvent;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
public class WindowConsole extends JFrame {
	private static final long serialVersionUID=512L;
	private final JTextField loadPath;
	private final JButton loadButton,viewFullMap,viewHeightMap,viewMaterials,exportButton;
	private final JCheckBox useHeightShades,useShade4,saveSession;
	private final JSpinner originX,originZ,scale,heightColumn,mapID;
	private final JLabel mapIcon,errorMsg,fileCount;
	private final JComboBox<DataVersion> dataVersionSelector;
	private int dataVersionLoaded=0;
	private MapColor[][] data;
	public WindowConsole() {
		super("Minecraft Map Generator");
		setBounds(100,100,550,270);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel=new JPanel(null);
		JLabel label=new JLabel("Image File to Load");
		label.setBounds(2,0,105,15);
		panel.add(label);
		loadPath=new JTextField(Session.imageToLoad);
		loadPath.setBounds(2,16,250,20);
		panel.add(loadPath);
		loadButton=new JButton("Generate Map");
		loadButton.setBounds(2,36,130,20);
		panel.add(loadButton);
		dataVersionSelector=new JComboBox<DataVersion>(DataVersion.values());
		dataVersionSelector.setSelectedIndex(Session.versionIndex);
		dataVersionSelector.setBounds(134,36,100,20);
		panel.add(dataVersionSelector);
		errorMsg=new JLabel();
		errorMsg.setBounds(5,56,130,15);
		errorMsg.setVisible(false);
		panel.add(errorMsg);
		label=new JLabel("Map Loading Options");
		label.setBounds(260,0,125,15);
		panel.add(label);
		useHeightShades=new JCheckBox("Use Height Shading",Session.useHeightShades);
		useHeightShades.setBounds(256,15,140,20);
		panel.add(useHeightShades);
		useShade4=new JCheckBox("Use Shade4",Session.useShade4);
		useShade4.setBounds(256,35,95,20);
		panel.add(useShade4);
		label=new JLabel("Top-Left Corner");
		label.setBounds(420,0,100,15);
		panel.add(label);
		label=new JLabel("x=");
		label.setBounds(420,20,20,15);
		panel.add(label);
		SpinnerNumberModel model=new SpinnerNumberModel();
		model.setValue(Session.origin[0]);
		model.setStepSize(128);
		originX=new JSpinner(model);
		originX.setBounds(435,17,50,20);
		panel.add(originX);
		label=new JLabel("z=");
		label.setBounds(420,40,20,15);
		panel.add(label);
		model=new SpinnerNumberModel();
		model.setValue(Session.origin[1]);
		model.setStepSize(128);
		originZ=new JSpinner(model);
		originZ.setBounds(435,37,50,20);
		panel.add(originZ);
		viewFullMap=new JButton("View Interactive Map");
		viewFullMap.setBounds(2,80,155,20);
		panel.add(viewFullMap);
		viewHeightMap=new JButton("View Height Map");
		viewHeightMap.setBounds(157,80,155,20);
		panel.add(viewHeightMap);
		label=new JLabel("at scale");
		label.setBounds(22,105,50,15);
		panel.add(label);
		scale=new JSpinner(new SpinnerNumberModel(Session.scale,1,10,1));
		scale.setBounds(70,102,32,20);
		panel.add(scale);
		label=new JLabel("for column x=");
		label.setBounds(170,105,100,15);
		panel.add(label);
		heightColumn=new JSpinner(new SpinnerNumberModel(Session.heightColumn,Session.heightColumn,Session.heightColumn,1));
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
		model.setValue(Session.mapID);
		label=new JLabel("Starting map ID:");
		label.setBounds(165,170,90,15);
		panel.add(label);
		mapID=new JSpinner(model);
		mapID.setBounds(258,168,40,20);
		panel.add(mapID);
		fileCount=new JLabel();
		fileCount.setBounds(165,186,120,40);
		panel.add(fileCount);
		saveSession=new JCheckBox("Save Session on Exit",Session.sessionFound);
		saveSession.setBounds(2,180,160,20);
		panel.add(saveSession);
		add(panel);
		setPostLoadEnabled(false);
		loadButton.addActionListener(this::loadButtonClicked);
		originX.addChangeListener(e -> alignColumnToAxis());
		viewFullMap.addActionListener(e -> new RenderWindow(data,new int[] {(int)originX.getValue(),(int)originZ.getValue()},(int)scale.getValue()).setVisible(true));
		viewHeightMap.addActionListener(e -> new HeightMapWindow(data,(int)heightColumn.getValue(),
				new int[] {(int)originX.getValue(),(int)originZ.getValue()},(int)scale.getValue()).setVisible(true));
		viewMaterials.addActionListener(e -> new MaterialWindow(data).setVisible(true));
		exportButton.addActionListener(this::exportButtonClicked);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(saveSession.isSelected())
					Session.save(loadPath.getText(),useHeightShades.isSelected(),useShade4.isSelected(),
							new int[] {(int)originX.getValue(),(int)originZ.getValue()},
							(int)scale.getValue(),(int)heightColumn.getValue(),(int)mapID.getValue(),
							dataVersionSelector.getSelectedIndex());
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
			int min=(int)originX.getValue(),max=min+data.length-1;
			heightColumn.setModel(new SpinnerNumberModel(Session.heightColumn>=min&&Session.heightColumn<=max?Session.heightColumn:min,min,max,1));
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
	private void loadButtonClicked(ActionEvent e) {
		try {
			BufferedImage img=ImageIO.read(new File(loadPath.getText()));
			if(img==null) {
				errorMsg.setText("File must be an image.");
				errorMsg.setVisible(true);
				return;
			}
			int dv=((DataVersion)dataVersionSelector.getSelectedItem()).id;
			data=Main.generateMap(img,useHeightShades.isSelected(),useShade4.isSelected(),dv);
			dataVersionLoaded=dv; //Only update version if generateMap() succeeds
		} catch(IOException ex) {
			errorMsg.setText("Unable to open file.");
			errorMsg.setVisible(true);
			return;
		}
		errorMsg.setVisible(false);
		setPostLoadEnabled(true);
		alignColumnToAxis();
		int wD=data.length,hD=data[0].length,
				w=(int)(128.0*wD/Math.max(wD,hD)),h=(int)(128.0*hD/Math.max(wD,hD));
		BufferedImage icon=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		int[] raster=((DataBufferInt)icon.getRaster().getDataBuffer()).getData();
		for(int y=0;y<h;y++)
			for(int x=0;x<w;x++)
				raster[y*w+x]=data[x*wD/w][y*hD/h].intARGB;
		mapIcon.setIcon(new ImageIcon(icon));
		mapIcon.setSize(w,h);
		int c=(int)Math.ceil(data.length/128d)*(int)Math.ceil(data[0].length/128d);
		fileCount.setText("<html>Exporting will create<br/>"+c+" map file"+(c==1?"":"s")+".</html>");
	}
	private void exportButtonClicked(ActionEvent e) {
		try {
			int id=(int)mapID.getValue();
			for(int oz=0;oz<data[0].length;oz+=128)
				for(int ox=0;ox<data.length;ox+=128) {
					NBTFiles.exportMap(Main.directory+"map_"+id+".dat",data,ox,oz,dataVersionLoaded,(int)originX.getValue(),(int)originZ.getValue());
					id++;
				}
		} catch(IOException ex) {}
	}
}
