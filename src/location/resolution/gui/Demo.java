package location.resolution.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

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

//    public Demo(List<LocationDescriptor> lld) {
    public Demo(List<LocationDescriptor> lldgn,
	    		List<LocationDescriptor> lldgrc,
	    		List<LocationDescriptor> lldosmn,
	    		List<LocationDescriptor> lldygp	) {
        super("JMapViewer Demo");
        setSize(400, 400);

        map = new JMapViewer();

        // Listen to the map viewer for user operations so components will
        // recieve events and update
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

        mperpLabelName=new JLabel("Meters/Pixels: ");
        mperpLabelValue=new JLabel(String.format("%s",map.getMeterPerPixel()));

        zoomLabel=new JLabel("Zoom: ");
        zoomValue=new JLabel(String.format("%s", map.getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout());
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        JButton button = new JButton("setDisplayToFitMapMarkers");
        button.addActionListener(new ActionListener() {

        	@Override
        	public void actionPerformed(ActionEvent e) {
                map.setDisplayToFitMapMarkers();
            }
        });
        JComboBox tileSourceSelector = new JComboBox(new TileSource[] { new OsmTileSource.Mapnik(),
                new OsmTileSource.CycleMap(), new BingAerialTileSource(), new MapQuestOsmTileSource(), new MapQuestOpenAerialTileSource() });
        tileSourceSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map.setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox tileLoaderSelector;
        try {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmFileCacheTileLoader(map),
                    new OsmTileLoader(map) });
        } catch (IOException e) {
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
        
//        for(LocationDescriptor ld : lld) {
//        	map.addMapMarker(new MapMarkerDot(Color.BLUE, ld.getLatitude(), ld.getLongitude()));
//        	
//        	try {
//	        	List<GeoPoint> boundingBox = ld.getBoundingBox().getGeoPoints();
//	        	boundingBox.get(0).getLatitude();
//	        	
//	        	Coordinate northWest = new Coordinate(boundingBox.get(1).getLatitude(), boundingBox.get(0).getLongitude());
//	        	Coordinate southEast = new Coordinate(boundingBox.get(0).getLatitude(), boundingBox.get(1).getLongitude());
//	
//	        	map.addMapRectangle(new MapRectangleImpl(northWest, southEast, Color.YELLOW, new BasicStroke(5)));
//        	}
//        	catch (Exception e) {
//        		// TODO
//        	}
//        }
        
        /* GeoNames */
        for(LocationDescriptor ld : lldgn) {
        	map.addMapMarker(new MapMarkerDot(Color.BLUE, ld.getLatitude(), ld.getLongitude()));
        	
        	try {
	        	List<GeoPoint> boundingBox = ld.getBoundingBox().getGeoPoints();
	        	boundingBox.get(0).getLatitude();
	        	
	        	Coordinate northWest = new Coordinate(boundingBox.get(1).getLatitude(), boundingBox.get(0).getLongitude());
	        	Coordinate southEast = new Coordinate(boundingBox.get(0).getLatitude(), boundingBox.get(1).getLongitude());
	
	        	map.addMapRectangle(new MapRectangleImpl(northWest, southEast, Color.BLUE, new BasicStroke(5)));
        	}
        	catch (Exception e) {
        		// TODO
        	}
        }
        
        /* GoogleReverseCoder */
        for(LocationDescriptor ld : lldgrc) {
        	map.addMapMarker(new MapMarkerDot(Color.BLACK, ld.getLatitude(), ld.getLongitude()));
        	
        	try {
	        	List<GeoPoint> boundingBox = ld.getBoundingBox().getGeoPoints();
	        	boundingBox.get(0).getLatitude();
	        	
	        	Coordinate northWest = new Coordinate(boundingBox.get(1).getLatitude(), boundingBox.get(0).getLongitude());
	        	Coordinate southEast = new Coordinate(boundingBox.get(0).getLatitude(), boundingBox.get(1).getLongitude());
	
	        	map.addMapRectangle(new MapRectangleImpl(northWest, southEast, Color.BLACK, new BasicStroke(5)));
        	}
        	catch (Exception e) {
        		// TODO
        	}
        }
        
        /* OSMNominatim */
        for(LocationDescriptor ld : lldosmn) {
        	map.addMapMarker(new MapMarkerDot(Color.RED, ld.getLatitude(), ld.getLongitude()));
        	
        	try {
	        	List<GeoPoint> boundingBox = ld.getBoundingBox().getGeoPoints();
	        	boundingBox.get(0).getLatitude();
	        	
	        	Coordinate northWest = new Coordinate(boundingBox.get(1).getLatitude(), boundingBox.get(0).getLongitude());
	        	Coordinate southEast = new Coordinate(boundingBox.get(0).getLatitude(), boundingBox.get(1).getLongitude());
	
	        	map.addMapRectangle(new MapRectangleImpl(northWest, southEast, Color.RED, new BasicStroke(5)));
        	}
        	catch (Exception e) {
        		// TODO
        	}
        }
        
        /* YahooGeoplanet */
        for(LocationDescriptor ld : lldygp) {
        	map.addMapMarker(new MapMarkerDot(Color.GREEN, ld.getLatitude(), ld.getLongitude()));
        	
        	try {
	        	List<GeoPoint> boundingBox = ld.getBoundingBox().getGeoPoints();
	        	boundingBox.get(0).getLatitude();
	        	
	        	Coordinate northWest = new Coordinate(boundingBox.get(1).getLatitude(), boundingBox.get(0).getLongitude());
	        	Coordinate southEast = new Coordinate(boundingBox.get(0).getLatitude(), boundingBox.get(1).getLongitude());
	
	        	map.addMapRectangle(new MapRectangleImpl(northWest, southEast, Color.GREEN, new BasicStroke(5)));
        	}
        	catch (Exception e) {
        		// TODO
        	}
        }

        //
//        map.addMapMarker(new MapMarkerDot(49.814284999, 8.642065999));
//        map.addMapMarker(new MapMarkerDot(49.91, 8.24));
//        map.addMapMarker(new MapMarkerDot(49.71, 8.64));
//        map.addMapMarker(new MapMarkerDot(48.71, -1));
//        map.addMapMarker(new MapMarkerDot(49.8588, 8.643));

        // map.setDisplayPositionByLatLon(49.807, 8.6, 11);
        // map.setTileGridVisible(true);
        
//        map.addMapRectangle(new MapRectangleImpl(topLeft, bottomRight))
        
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
                } else {
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
