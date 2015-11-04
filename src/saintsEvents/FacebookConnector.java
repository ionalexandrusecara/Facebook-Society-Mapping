package saintsEvents;

import java.util.ArrayList;
import java.util.List;

import com.restfb.*;
import com.restfb.types.*;

public class FacebookConnector {
	FacebookClient fbClient;
	
	private final String API_TOKEN = "CAACEdEose0cBAOrXFNZAcfZBbWnHsIeNo8hRBWSOGjTXLicDUS0TZAn4y89UxUtWJkwNrhTChjhRtusXYL5SXFsJCpNCblV6KlhhXWAU4W5IwHDSINpGZCkpKmtAzs0TefVZBXO6cJevKmzqIiYByxFkEit4JPvj6qYoHQEEFwUZC2sLEMMVmIMoxZC9wmVYZCEZD";
	
	public FacebookConnector() {
		fbClient = new DefaultFacebookClient(API_TOKEN,Version.VERSION_2_5);
	}
	
	public List<Event> getEventsFromId(String groupId) {
		List<Event> events = new ArrayList<Event>();
		
		for(List<Event> eventPage : fbClient.fetchConnection(groupId+ "/events", Event.class)) {
			for(Event event: eventPage) {
				events.add(event);
			}
		}
		return events;
	}
	
	
}
