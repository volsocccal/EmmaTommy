package emmaTommy.TommyHandler.Actors;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;
import akka.actor.typed.PostStop;
import emmaTommy.TommyHandler.ActorsMessages.PostData;
import emmaTommy.TommyHandler.ActorsMessages.startPosting;


public class TommyPostHandler extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected String tommyURL;
	protected String associazione;
	protected String servizioRestName;
	protected String username;
	protected String psswd;
	protected int startingServizioCode;
	protected Boolean POST;
	
	public static Props props(String text, String confPath) {
        return Props.create(TommyPostHandler.class, text, confPath);
    }

	private TommyPostHandler(String confPath) {
		
		// Logger Method Name
		String method_name = "::TommyPostHandler(): ";
		
		// Define and Load Configuration File
		Properties props = new Properties();
		logger.trace(method_name + "Loading Properties FileName: " + confPath);
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(confPath);
		} catch (FileNotFoundException e) {
			logger.fatal(method_name + e.getMessage());
		}
		try {
			props.load(fileStream);
		    logger.trace(method_name + props.toString());
		} catch (IOException e) {
			logger.fatal(method_name + e.getMessage());
		}
		
		// Load Configuration Data
		this.tommyURL = props.getProperty("tommyURL");
		this.associazione = props.getProperty("associazione");
		this.servizioRestName = props.getProperty("servizioRestName");
		this.username = props.getProperty("username");
		this.psswd = props.getProperty("psswd");
		this.startingServizioCode = Integer.parseInt(props.getProperty("startingServizioCode"));
		
		// Set Conversion cycle to false
		this.POST = false;
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(startPosting.class, this::onStart)
				.match(PostData.class, this::postElaborator)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void onStart(startPosting startPost) {
		
		// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Posting Event");
		
		
	}
	
	protected void postElaborator(PostData postData) {
		String method_name = "::consume(): ";
		logger.trace(method_name + "Received a post msg");
		
		String json = "";
		
		// Build url
		String restUrl = tommyURL 
				+ "/" + associazione 
				+ "/" + servizioRestName + "/" + "run.php?" 
				+ "user=" + username 
				+ "&pwd=" + psswd 
				+ "&json" + json;
		
		try {
			
			String response = this.post(restUrl, json);
			
		} catch (IOException e) {
			logger.error("Failed to post the following servizi for automezzo " + postData.getCodiceMezzo());
		}
		
		
		
		
	}	
	
	protected String post(String restUrl, String jsonServizi) throws IOException {
		URL url = new URL(restUrl);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(jsonServizi);
		out.close();

		String response = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		while (in.readLine() != null) {
		}
		System.out.println("\nREST Service Invoked Successfully..");
		in.close();
		return response;
	}
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		
	}
	
	
	public static void main(String[] args) {
		
		String json = "";
		
		try {
			
			//Path path = Paths.get("../docs/RestTommy/test.json");
			Path path = Paths.get("../data_xml_test/213003231.json");
			json = Files.readString(path, StandardCharsets.US_ASCII);
			
		} catch (Exception e) {
			System.out.println("\nError while calling REST Service");
			System.out.println(e);
		}
 
			
	}
}
