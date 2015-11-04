
var eventCounter = 0;
var map;
var initMap = function() {
	map = new google.maps.Map(document.getElementById('map'), {
      zoom: 14,
      center: {lat: 56.3404, lng: -2.79}
    });


};

var createEventEntry = function(event) {
	$("#eventContainer").append("<div class='event panel panel-default'></div>");
	$(".event").last().append("<div class='panel-heading'><h3>" + event.name + "</h3></div>");
	$(".event").last().append("<div class='panel-body'>" + event.date + "</div>");
	if(event.description != null) {
		$(".event").last().append("<div class='panel-body'>" + event.description + "</div>");
	}
	if(event.latitude != undefined) {
		var pos = {lat:event.latitude,lng:event.longitude};
		var infoWindow = new google.maps.InfoWindow({map: map});
		infoWindow.setPosition(pos);
		infoWindow.setContent(event.name);

	}
	$(".event").last().append("</br><a class='btn btn-default' href='http://www.facebook.com/" + event.id + "'>Go To Event </a>")
	
};

var checkForUpdates = function() {
	$("#eventContainer").empty();
	$.get("events.json",function(events,status) {
		if(events.length === 0) {
			$("#eventContainer").append("<h3> No events found </h3>");
		}
		for(var i = 0; i < events.length; ++i) {
			createEventEntry(events[i]);
		}
	});
};

$(document).ready(function() {
	checkForUpdates();	

});