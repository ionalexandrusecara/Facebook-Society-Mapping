package saintsEvents;

import java.io.*;
import java.util.*;

import org.json.JSONWriter;

import com.restfb.types.Event;

public class FacebookTest {
	static class EventByDate implements Comparator<Event> {
		
		@Override
		public int compare(Event ev1, Event ev2) {
			return ev1.getStartTime().compareTo(ev2.getStartTime());
		}
	}
	
	static class Society {
				
		
		public Society(String name, String id) {
			this.name = name;
			this.id = id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public String getId() {
			return this.id;
		}
		
		private String name;
		private String id;
	}
	
	static Society[] societies = new Society[] {new Society("Blood Donation Society", "164099410348048"),
										 new Society("St Andrews Gaming Society", "186040334775100"),
										 new Society("Greek and Cypriot Society", "2209948925"),
										 new Society("VegSoc StAndrews","415990775104886"),
										 new Society("University of St Andrews Union Debating Society","2201808040"),
										 new Society("St Andrews Computer Science Society", "164507920275790"),
										 new Society("St Andrews Surgical Society","216572323734"),
										 new Society("Test Group","3273327452702356"),
										 new Society("St Andrews A Cappella", "276002722445005"),
										 new Society("Photography Society of St. Andrews", "2201814682")};
	
	public static void main(String[] args) throws IOException, InterruptedException {
		FacebookConnector fbConnector = new FacebookConnector();
		
		while(true) {
			List<Event> events = new ArrayList<Event>();
			
			for(Society soc : societies) {
				events.addAll(fbConnector.getEventsFromId(soc.getId()));
			}
					
			
			Collections.sort(events,new EventByDate());
			
			Date oneMonthAgo = new Date();
			oneMonthAgo.setMonth(oneMonthAgo.getMonth() -1);
			
			FileWriter writer = new FileWriter("./public_data/events.json");
			JSONWriter eventsJSON = new JSONWriter(writer);
			eventsJSON.array();
			for(Event event:events) {
				if(event.getStartTime().compareTo(oneMonthAgo) >= 0) {
					eventsJSON.object();
					eventsJSON.key("id").value(event.getId());
					eventsJSON.key("name").value(event.getName());
					double lon = 0.0, lat = 0.0;
					try {
						lon = event.getPlace().getLocation().getLongitude();
						lat = event.getPlace().getLocation().getLatitude();
					}
					catch (Exception e) {
						
					}
					
					if(lon != 0.0 && lat != 0.0) {
						eventsJSON.key("longitude").value(event.getPlace().getLocation().getLongitude());
						eventsJSON.key("latitude").value(event.getPlace().getLocation().getLatitude());

					}
					
					eventsJSON.key("date").value("" + event.getStartTime());
					eventsJSON.key("description").value(event.getDescription());
					eventsJSON.endObject();
				}
			}
			eventsJSON.endArray();
			
			writer.close();
			Thread.sleep(10000);
		}
		
	}
}
