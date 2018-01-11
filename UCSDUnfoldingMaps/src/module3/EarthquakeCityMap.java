package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
* An application with an interactive map displaying earthquake data.
* Author: UC San Diego Intermediate Software Development MOOC team
* @author Your name here
* Date: July 17, 2015
* */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	//private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	List<Marker> markers = new ArrayList<Marker>();
	
	public void setup() 
	{
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			//map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.AerialProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    
       
        
	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    
	    if (earthquakes.size() > 0) {
		    	for (int k = 0; k < earthquakes.size(); k++) {
			    	PointFeature f = earthquakes.get(k);
			    	Object magObj = f.getProperty("magnitude");
			    	float mag = Float.parseFloat(magObj.toString());
			    	// PointFeatures also have a getLocation method
			    	Marker valEq = createMarker(f);
			    	//valEq.setColor(color(255,0,0));
			    	markers.add(valEq);
			    	map.addMarker(valEq);
		    	}
	    }
	    
	    
	    
	    
	    List<PointFeature> features = new ArrayList<PointFeature>();     
	    
	    
	    
	    List<Marker> markers = new ArrayList<Marker>();
	    
	    for (PointFeature eq: earthquakes) {
	    	markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
	    }
	    map.addMarkers(markers);
	   
	    List<SimplePointMarker> marker = new ArrayList<SimplePointMarker>();
	    
	    
	    
	    int blue = color(0, 0, 255);
	    int yellow = color(255, 255, 0);
	    int red = color(255,0, 0); 
	    
	
	    
        for (Marker mk : markers ) {
	        	if ( (float) mk.getProperty("magnitude") < 4.0 ) 
	        	{
	        		mk.setColor(blue);
	        		
	        	}
	        	
	        	
	        	else  if ( (float) mk.getProperty("magnitude") >= 4.0 &&
	        			(float) mk.getProperty("magnitude") <= 4.9 )
	        	{
	        		mk.setColor(yellow);
	        	} 
	        	
	        	else {
	        		mk.setColor(red);
	        	}
        }
       
    	
       
	    
	    //TODO: Add code here as appropriate
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	
	
	
	
	private SimplePointMarker createMarker(PointFeature feature)
	{
		/* SimplePointMarker spm = new SimplePointMarker(feature.getLocation());
		
		spm.setRadius(10);
		
		return spm; */
		
		return new SimplePointMarker(feature.getLocation());
		
		
	}
	
	
	
	public void draw() {
	    background(10);
	    map.draw();
	   
	    for (Marker m : markers) {
	    	Location eq = m.getLocation();
	    
	    }
	    addKey();
	    
	    
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		
		// Remember you can use Processing's graphics methods here
	
	}
}
