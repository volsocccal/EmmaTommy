package emmaTommy.TommyDataHandler.Actors;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import emmaTommy.TommyDataHandler.ActorsMessages.PostData;
import emmaTommy.TommyDataHandler.ActorsMessages.PostDataResponse;
import emmaTommy.TommyDataHandler.ActorsMessages.StartPosting;


public class TommyPostHandler extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected String tommyURL;
	protected String associazione;
	protected String servizioRestName;
	protected String username;
	protected String psswd;
	protected int startingServizioCode;
	protected Boolean postServiceActive;
	protected Boolean POSTflag;
	
	
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
		this.postServiceActive = (Integer.parseInt(props.getProperty("postServiceActive")) == 1) ? (true) : (false);
		
		// Set Conversion cycle to false
		this.POSTflag = false;
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartPosting.class, this::onStart)
				.match(PostData.class, this::postElaborator)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void onStart(StartPosting startPost) {
		
		// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Posting Event");
		
		this.POSTflag = this.postServiceActive;
		
		
	}
	
	protected void postElaborator(PostData postData) {
		String method_name = "::consume(): ";
		logger.trace(method_name + "Received a post msg");
		
		PostDataResponse respData = null;
		
		try {
			
			// Analyze Servizi Arraylist
			if (postData.getCodiciServizi() == null) {
				throw new NullPointerException("Got a null codiciServizi arraylist");
			}
			if (postData.getCodiciServizi().isEmpty()) {
				throw new IllegalArgumentException("Got an empty codiciServizi arraylist");
			}
			for (Integer codiceServizio: postData.getCodiciServizi()) {
				if (codiceServizio <= this.startingServizioCode) {
					throw new IllegalArgumentException("Got " + codiceServizio + " as one of the codiciServizio, but the configuration minumum was " + this.startingServizioCode);
				}
			}
			
			// Analyze input Json
			String json = postData.getJsonServizi();
			if (json == null) {
				throw new NullPointerException("Got a null json arraylist");
			}
			if (json.isEmpty()) {
				throw new NullPointerException("Got an empty json arraylist");
			}
			
			// Build Url
			String restUrl = tommyURL 
							+ "/" + associazione 
							+ "/" + servizioRestName + "/" + "run.php?" 
							+ "&user=" + username 
							+ "&pwd=" + psswd
							+ "&json=" + URLEncoder.encode(json, "UTF-8");			
            URI uri = new URI(restUrl);  
            
            // Post
            try {    			
            	if (this.POSTflag) {
	            	String response = this.post(uri, json);
	     			logger.info(method_name + "Rest Service Answer: " + response);  
	     			if (response.contains("ERR")) {
	     				respData = new PostDataResponse(postData, false, response);
	     			} else {
	     				respData = new PostDataResponse(postData, true, response);
	     			}
            	} else {
            		if (this.postServiceActive) {
            			respData = new PostDataResponse(postData, false, "Posting is disabled by configuration");
            		} else {
            			respData = new PostDataResponse(postData, false, "Posting is disabled (maybe I never received a startPosting msg)");
            		}
            	}
            } catch (MalformedURLException e) {
            	String errorMsg = "Url Malformed Error: " + e.getMessage();
            	logger.error(method_name + errorMsg);
            	respData = new PostDataResponse(postData, false, errorMsg);
    		} catch (IOException e) {
    			String errorMsg = "Failed to post the following servizi for automezzo " + postData.getCodiceMezzo()+ "\n" + e.getMessage();
    			logger.error(errorMsg);
    			respData = new PostDataResponse(postData, false, errorMsg);
    		}
           
        } catch (URISyntaxException e) {
        	String errorMsg = "URI Syntax Error: " + e.getMessage();
        	logger.error(method_name + errorMsg);
        	respData = new PostDataResponse(postData, false, errorMsg);
        } catch (UnsupportedEncodingException e) {
        	String errorMsg = "URL Encoder UnsupportedEncoding Error: " + e.getMessage();
        	logger.error(method_name + errorMsg);
        	respData = new PostDataResponse(postData, false, errorMsg);
        } catch (IllegalArgumentException e) {
        	String errorMsg = "Illegal Argument Error: " + e.getMessage();
        	logger.error(method_name + errorMsg);
        	respData = new PostDataResponse(postData, false, errorMsg);
        } catch (NullPointerException e) {
        	String errorMsg = "Null Pointer Error: " + e.getMessage();
        	logger.error(method_name + errorMsg);
        	respData = new PostDataResponse(postData, false, errorMsg);
		} catch (Exception e) {
			String errorMsg = "Unknown Error: " + e.getMessage();
			logger.error(method_name + errorMsg);
			respData = new PostDataResponse(postData, false, errorMsg);
		}
		
		// Send response
		if (respData != null) {
			this.getSender().tell(respData, this.getSelf());
		}
		
	}	
	
	protected String post(URI restUri, String jsonServizi) throws MalformedURLException, IOException {
		try {
            URL restUrl = restUri.toURL();
            return this.post(restUrl, jsonServizi);
        }
        catch (MalformedURLException e) {
            throw e;
        }
		
	}
	
	protected String post(URL restUrl, String jsonServizi) throws IOException {
		
		String method_name = "::post(): ";
		logger.trace(method_name + "Posting to " + this.tommyURL);
		
		// Create Connection
		URLConnection connection = restUrl.openConnection();
		connection.setDoOutput(true); // Triggers POST action
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		// connection.setConnectTimeout(5000);
		// connection.setReadTimeout(5000);
		try (OutputStream output = connection.getOutputStream()) {
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(jsonServizi);
			out.close();
		}
		
		// Connect
		logger.info(method_name + "Sending POST Request");
		connection.connect();
		
		// Analize Response		
		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		int responseCode = httpConnection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			logger.info(method_name + "Http Request Status: " + responseCode);
		} else {
			logger.error(method_name + "Http Request Status: " + responseCode);
		}
		/**
		logger.trace(method_name + "Loggin Headers");
		
		for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
		    logger.info(method_name + header.getKey() + "=" + header.getValue());
		}
		*/
		
		// Build Response String
		InputStream responseInputStream = connection.getInputStream();
		InputStreamReader responseInputStreamReader = new InputStreamReader(responseInputStream);
		BufferedReader responseInputStreamBufferReader = new BufferedReader(responseInputStreamReader);
	    StringBuffer responseStringBuffer = new StringBuffer();
	    String str;
        while((str = responseInputStreamBufferReader.readLine())!= null){
        	responseStringBuffer.append(str);
        }
		return responseStringBuffer.toString();
		
		
		
	}
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		
	}
	
	
	public static void main(String[] args) {
		
		// Logger
		String method_name = "::main(): ";
		org.apache.logging.log4j.Logger logger = LogManager.getLogger("TommyPostHandler");
		
		// Create Actor System
		logger.info(method_name + "Creating ActorSystem ...");
		ActorSystem system = ActorSystem.create("test-system");
		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Create TommyPostHandler Actor
		logger.info(method_name + "Creating TommyPostHandler Actor ...");
		ActorRef tommyPoster = system.actorOf(Props.create(TommyPostHandler.class, "../conf/tommy_refs.conf"), "TommyPostHandler");
		logger.info(method_name + " TommyPostHandler Actor is Active");
		
		// Send Start to TommyPostHandler
		logger.info(method_name + "Sending TommyPostHandler Actor the Start Posting Msg ...");
		tommyPoster.tell(new StartPosting(), ActorRef.noSender());
		logger.info(method_name + "Sent :)");
				
		try {
			
			//Path path = Paths.get("../docs/RestTommy/test.json");
			// int servizioCode = 213000000;
			// int servizioCode = 213003231;
			int servizioCode = 213003233;
			String codiceMezzo = "VOLCAL+105";
			Path path = Paths.get("../data_json_test/" + servizioCode + ".json");
			String json = Files.readString(path, StandardCharsets.US_ASCII);
			tommyPoster.tell(new PostData(codiceMezzo, servizioCode, json), ActorRef.noSender());
			
			
		} catch (Exception e) {
			logger.error(method_name + "Error while Posting: " + e.getMessage());
		}
 
			
	}
}
