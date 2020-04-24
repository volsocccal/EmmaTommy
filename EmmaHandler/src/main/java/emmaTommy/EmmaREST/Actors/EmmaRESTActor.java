package emmaTommy.EmmaREST.Actors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import emmaTommy.EmmaParser.ActorsMessages.MissioniDataXML;
import emmaTommy.EmmaREST.ActorsMessages.StartREST;

public class EmmaRESTActor extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
		
	protected String PROTOCOL;
	protected String MISSIONI_URL;
	protected String soreu_BERGAMO_NAME;
	protected int soreu_BERGAMO_NUM;
	protected String soreu_COMO_NAME;
	protected int soreu_COMO_NUM;
	protected String soreu_MILANO_NAME;
	protected int soreu_MILANO_NUM;
	protected String soreu_PAVIA_NAME;
	protected int soreu_PAVIA_NUM;
	protected String ASSOCIAZIONE_NAME;
	protected String ASSOCIAZIONE_SOREU;
	protected String username;
	protected String psswd;
	protected int missione_START_ID; 
	protected Boolean saveXMLToLog;
	protected Boolean saveXMLToFile;
	protected Boolean sendXMLOverAKKA;
	protected String xml_file_path;
	
	protected String xml;
	
	public static Props props(String text, String confPath, String confPathAss) {
        return Props.create(EmmaRESTActor.class, text, confPath, confPathAss);
    }

	public EmmaRESTActor(String confPath, String confPathAss) throws IOException, InterruptedException {
		
		// Logger Method Name
		String method_name = "::EmmaRESTActor(): ";
		
		// Define and Load Configuration File
		Properties prop = new Properties();
		logger.trace(method_name + "Loading Properties FileName: " + confPath);
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(confPath);
		} catch (FileNotFoundException e) {
			logger.fatal(method_name + e.getMessage());
		}
		try {
		    prop.load(fileStream);
		    logger.trace(method_name + prop.toString());
		} catch (IOException e) {
			logger.fatal(method_name + e.getMessage());
		}
		
		// Load Configuration Data
		this.PROTOCOL = prop.getProperty("PROTOCOL");
		this.MISSIONI_URL = prop.getProperty("MISSIONI_URL");
		this.soreu_BERGAMO_NAME = prop.getProperty("soreu_BERGAMO_NAME");
		this.soreu_BERGAMO_NUM = Integer.parseInt(prop.getProperty("soreu_BERGAMO_NUM"));
		this.soreu_COMO_NAME = prop.getProperty("soreu_COMO_NAME");
		this.soreu_COMO_NUM = Integer.parseInt(prop.getProperty("soreu_COMO_NUM"));
		this.soreu_MILANO_NAME = prop.getProperty("soreu_MILANO_NAME");
		this.soreu_MILANO_NUM = Integer.parseInt(prop.getProperty("soreu_MILANO_NUM"));
		this.soreu_PAVIA_NAME = prop.getProperty("soreu_PAVIA_NAME");
		this.soreu_PAVIA_NUM = Integer.parseInt(prop.getProperty("soreu_PAVIA_NUM"));
		this.ASSOCIAZIONE_NAME = prop.getProperty("ASSOCIAZIONE_NAME");
		this.ASSOCIAZIONE_SOREU = prop.getProperty("ASSOCIAZIONE_SOREU");
		this.username = prop.getProperty("username");
		this.psswd = prop.getProperty("psswd");
		this.missione_START_ID = Integer.parseInt(prop.getProperty("missione_START_ID"));		
		this.saveXMLToLog = (Integer.parseInt(prop.getProperty("saveXMLToLog")) == 1) ? (true) : (false);
		this.saveXMLToFile = (Integer.parseInt(prop.getProperty("saveXMLToFile")) == 1) ? (true) : (false);
		this.sendXMLOverAKKA = (Integer.parseInt(prop.getProperty("sendXMLOverAKKA")) == 1) ? (true) : (false);
		this.xml_file_path = prop.getProperty("xml_file_path");
		
		// Load Associazione's Data (Username, Psswd, Name)
		try {			
			Properties propPsswd = new Properties();			
			logger.info(method_name + "Loading Emma Psswd FileName: " + confPathAss);
			FileInputStream fileStreamPsswd = new FileInputStream(confPathAss);
			propPsswd.load(fileStreamPsswd);
			this.ASSOCIAZIONE_NAME = propPsswd.getProperty("ASSOCIAZIONE_NAME");
			this.ASSOCIAZIONE_SOREU = propPsswd.getProperty("ASSOCIAZIONE_SOREU");
			this.username = propPsswd.getProperty("username");
			this.psswd = propPsswd.getProperty("psswd");
		} catch (FileNotFoundException e) {
			logger.warn(method_name + e.getMessage());
			logger.warn(method_name + "Did you set username, psswd and association name in " + confPathAss + "?");
			logger.warn(method_name + "Will Proceed with default values [This will propably fail]");
		} catch (IOException e) {
			logger.warn(method_name + e.getMessage());
			logger.warn(method_name + "Did you set username, psswd and association name in " + confPathAss + "?");
			logger.warn(method_name + "Will Proceed with default values [This will propably fail]");
		}		
				
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartREST.class, this::onStartREST)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	private void onStartREST(StartREST startREST) {
		
		// Logger Method Name
		String method_name = "::onStartREST(): ";
		logger.info(method_name + "Got new startREST msg ");
		this.xml = "";
		
		// Try to Get the new XML
		try {
			this.getEmmaXML(this.ASSOCIAZIONE_SOREU, this.missione_START_ID);
			if (this.sendXMLOverAKKA) {
				MissioniDataXML xmlMissioni = new MissioniDataXML(this.xml, startREST.producerJSONKAFKA);
				startREST.receiverXMLAKKA.tell(xmlMissioni, this.getSelf());
			}
			if (this.saveXMLToLog) {
				logger.info(this.xml);
			}
			if (this.saveXMLToFile) {
				this.writeXMLToFile(this.xml_file_path);
			}
			this.xml = "";
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	protected String getEmmaXML(String SOREU, int idMissione) throws IOException, InterruptedException {
		return this.getEmmaXML(SOREU, Integer.toString(idMissione));
	}
	protected String getEmmaXML(String SOREU, String idMissione) throws IOException, InterruptedException {
		
		// Logger Method Name
		String method_name = "::getEmmaXML(): ";
		logger.info(method_name + "Downloading from missione " + idMissione);
		logger.info(method_name + "Associazione: " + this.ASSOCIAZIONE_NAME);
		
		// Build HttpClient
		HttpClient httpClient = HttpClient.newBuilder()
										  .authenticator(new Authenticator() {
											  @Override
								              protected PasswordAuthentication getPasswordAuthentication() {
								                  return new PasswordAuthentication(username, psswd.toCharArray());
								              }
								          })
									      .version(HttpClient.Version.HTTP_2)
									      .build();
		
		// Build Url
		String url = this.urlBuilder(SOREU, idMissione);
		
		// Build Http Request
		HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient EmmaTommy Bot") // add request header
                //.setHeader("User-Agent", "Mozilla/5.0 Firefox/26.0")
                .setHeader("Content-type", "application/xml")
                .build();


		// Send Response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());   
        HttpHeaders headers = response.headers();  

        // Print status code
        if (response.statusCode() == 200) {
        	logger.info(method_name + "Download was executed correctly (Status Code 200))");
        	logger.info(method_name + "Downloaded " + response.body().getBytes("UTF-8").length + " bytes");
        	
        } else {
        	logger.fatal(method_name + "Error Status Code: " + response.statusCode() + " :(");
        	logger.fatal(response.body());
        	throw new RuntimeException("Failed http request with error code: " + response.statusCode());        	
        }

        // Update XML Field
        this.xml = response.body();
        
        // Return
		return response.body();
	}
	
	protected String urlBuilder(String SOREU, String idMissione) {
		return PROTOCOL + username + ":" + psswd + MISSIONI_URL + SOREU + "/" + ASSOCIAZIONE_NAME + "/" + idMissione + ".xml";
	}
	
	protected void writeXMLToFile(String filePath) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(xml.getBytes());     
        outputStream.close();
	}
	

	protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
	}

	
	
	
}



