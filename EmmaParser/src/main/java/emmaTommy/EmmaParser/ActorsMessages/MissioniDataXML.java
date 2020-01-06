package emmaTommy.EmmaParser.ActorsMessages;

import akka.actor.ActorRef;

public final class MissioniDataXML extends MissioniData {
	
    public final String xml;
    public ActorRef producerJSONKAFKA;
    
    public MissioniDataXML(String xml, ActorRef producerJSONKAFKA) {
    	this.xml = xml;
    	this.producerJSONKAFKA = producerJSONKAFKA;
    	this.validateData();
    }
    
    public Boolean validateData() {
    	this.validData = true;
    	if (xml == null) {
    		this.validData = false;
    		this.errorMsg = "XML Data was NULL";
    	}
    	if (xml.isEmpty()) {
    		this.validData = false;
    		this.errorMsg = "XML Data was Empty";
    	}
    	if (xml.isBlank()) {
    		this.validData = false;
    		this.errorMsg = "XML Data was Blanck";
    	}
    	if (producerJSONKAFKA == null) {
    		this.validData = false;
    		this.errorMsg = "producerJSONKAFKA was NULL";
    	}
    	return this.validData;
    }
	
}

