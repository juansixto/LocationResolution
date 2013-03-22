package location.resolution.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import location.resolution.aux.Utils;
import location.resolution.models.GeoPoint;
import location.resolution.models.LocationDescriptor;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapRectangleImpl;
import org.openstreetmap.gui.jmapviewer.OsmFileCacheTileLoader;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

public class Demo extends JFrame implements JMapViewerEventListener  {

    private static final long serialVersionUID = 1L;

    private JMapViewer map = null;

    private JLabel zoomLabel=null;
    private JLabel zoomValue=null;

    private JLabel mperpLabelName=null;
    private JLabel mperpLabelValue = null;
    
    private int MAX_BOUNDING_BOX_DIAGONAL_IN_KM = 10;
    private int MAX_DISTANCE_BETWEEEN_POINTS_IN_KM = 100;
    private int STROKE_WIDTH = 3;

    private List<LocationDescriptor> lldgn = null;
    private List<LocationDescriptor> lldgrc = null;
    private List<LocationDescriptor> lldosmn = null;
    private List<LocationDescriptor> lldygp = null;
    
    private List<LocationDescriptor> lld = null;
    
    private JMapViewer paintMarker(List<LocationDescriptor> locationDescriptorList, Color color) {
    	for(LocationDescriptor ld : locationDescriptorList) {
    		if(Utils.hasAnyPointNear(ld, this.lld, MAX_DISTANCE_BETWEEEN_POINTS_IN_KM)) {
	        	this.map.addMapMarker(new MapMarkerDot(color, ld.getLatitude(), ld.getLongitude()));
	        	
	        	try {
		        	List<GeoPoint> boundingBox = ld.getBoundingBox().getGeoPoints();
		        	boundingBox.get(0).getLatitude();
		        	
		        	Coordinate northWest = new Coordinate(boundingBox.get(1).getLatitude(), boundingBox.get(0).getLongitude());
		        	Coordinate southEast = new Coordinate(boundingBox.get(0).getLatitude(), boundingBox.get(1).getLongitude());
		
		        	int distanceInKm = Utils.calculateDistance(northWest.getLat(), northWest.getLon(), southEast.getLat(), southEast.getLon());
		        	
		        	if(distanceInKm < this.MAX_BOUNDING_BOX_DIAGONAL_IN_KM) {
		        		this.map.addMapRectangle(new MapRectangleImpl(northWest, southEast, color, new BasicStroke(this.STROKE_WIDTH)));
		        	}
	        	}
	        	catch (Exception e) {
	        		// TODO
	        	}
    		}
        }
    	return this.map;
    }

    
    
    public Demo() {
    	this(null, null, null, null);
    }
    
    public Demo(List<LocationDescriptor> lld) {
    	this(lld, null, null, null);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Demo(List<LocationDescriptor> lldgn, List<LocationDescriptor> lldgrc, List<LocationDescriptor> lldosmn, List<LocationDescriptor> lldygp) {
    	super("JMapViewer");
    	
    	this.lldgn = lldgn;
    	this.lldgrc = lldgrc;
    	this.lldosmn = lldosmn;
    	this.lldygp = lldygp;
    	
    	lld = new ArrayList<LocationDescriptor>();
    	
    	if(lldgn != null) {
    		lld.addAll(lldgn);
    	}
    	if(lldgrc != null) {
    		lld.addAll(lldgrc);
    	}
    	if(lldosmn != null) {
    		lld.addAll(lldosmn);
    	}
    	if(lldygp != null) {
    		lld.addAll(lldygp);
    	}
    	
        setSize(400, 400);

        map = new JMapViewer();

        // Listen to the map viewer for user operations so components will
        // receive events and update
        map.addJMVListener(this);

        // final JMapViewer map = new JMapViewer(new MemoryTileCache(),4);
        // map.setTileLoader(new OsmFileCacheTileLoader(map));
        // new DefaultMapController(map);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s",map.getMeterPerPixel()));

        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map.getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout());
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        JButton button = new JButton("setDisplayToFitMapMarkers");
        button.addActionListener(new ActionListener() {

        	@Override
        	public void actionPerformed(ActionEvent e) {
                map.setDisplayToFitMapMarkers();
            }
        });
        JComboBox tileSourceSelector = new JComboBox(new TileSource[] { 
        		new OsmTileSource.Mapnik(),
                new OsmTileSource.CycleMap(), 
                new BingAerialTileSource(), 
                new MapQuestOsmTileSource(), 
                new MapQuestOpenAerialTileSource() 
        	}
        );
        tileSourceSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map.setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox tileLoaderSelector;
        
        try {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmFileCacheTileLoader(map), new OsmTileLoader(map) });
        }
        catch (IOException e) {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmTileLoader(map) });
        }
        
        tileLoaderSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map.setTileLoader((TileLoader) e.getItem());
            }
        });
        
        map.setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
        panelTop.add(tileSourceSelector);
        panelTop.add(tileLoaderSelector);
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map.getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
        panelBottom.add(showMapMarker);
        final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(map.isTileGridVisible());
        showTileGrid.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setTileGridVisible(showTileGrid.isSelected());
            }
        });
        panelBottom.add(showTileGrid);
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map.getZoomContolsVisible());
        showZoomControls.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        panelBottom.add(showZoomControls);
        final JCheckBox scrollWrapEnabled = new JCheckBox("Scrollwrap enabled");
        scrollWrapEnabled.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map.setScrollWrapEnabled(scrollWrapEnabled.isSelected());
            }
        });
        panelBottom.add(scrollWrapEnabled);
        panelBottom.add(button);

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);

        add(map, BorderLayout.CENTER);
        
        if(this.lldgn != null) {
        	paintMarker(lldgn, Color.BLUE);
        }
        
        if(this.lldgrc != null) {
        	paintMarker(lldgrc, Color.RED);
        }
        
        if(this.lldosmn != null) {
        	paintMarker(lldosmn, Color.GREEN);
        }
        
        if(this.lldygp != null) {
        	paintMarker(lldygp, Color.YELLOW);
        }

        // map.setDisplayPositionByLatLon(49.807, 8.6, 11);
        // map.setTileGridVisible(true);
                
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map.getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });
        
        map.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean cursorHand = map.getAttribution().handleAttributionCursor(e.getPoint());
                
                if (cursorHand) {
                    map.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else {
                    map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

	@Override
	public void processCommand(JMVCommandEvent arg0) {
		// TODO Auto-generated method stub
	}
}
