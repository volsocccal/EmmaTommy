package emmaREST;

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

public class emmaREST {
	
	String PROTOCOL;
	String MISSIONI_URL;
	String soreu_BERGAMO_NAME="bergamo";
	int soreu_BERGAMO_NUM;
	String soreu_COMO_NAME;
	int soreu_COMO_NUM;
	String soreu_MILANO_NAME;
	int soreu_MILANO_NUM;
	String soreu_PAVIA_NAME;
	int soreu_PAVIA_NUM;
	String ASSOCIAZIONE_NAME;
	String username;
	String psswd;
	int requestTimeSecs;

	public emmaREST() throws IOException, InterruptedException {
		
		
		// Define and Load Configuration File
		Properties prop = new Properties();
		String fileName = "../conf/emma.conf";
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
		}
		try {
		    prop.load(fileStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
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
		this.username = prop.getProperty("username");
		this.psswd = prop.getProperty("psswd");
		this.requestTimeSecs = Integer.parseInt(prop.getProperty("requestTimeSecs"));
		
		// Load Associazione's Data (Username, Psswd, Name)
		try {
			Properties propPsswd = new Properties();
			String fileNamePsswd = "../conf/emma_psswd.conf";
			FileInputStream fileStreamPsswd = new FileInputStream(fileNamePsswd);
			propPsswd.load(fileStreamPsswd);
			this.ASSOCIAZIONE_NAME = propPsswd.getProperty("ASSOCIAZIONE_NAME");
			this.username = propPsswd.getProperty("username");
			this.psswd = propPsswd.getProperty("psswd");
		} catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());   
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		String xml = this.getEmmaXML(this.soreu_COMO_NAME, "190000001");
		String filePath = "../data_xml_download/testJava.xml";
		FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(xml.getBytes());     
        outputStream.close();
		
	}
	
	public String getEmmaXML(String SOREU, String idMissione) throws IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newBuilder()
										  .authenticator(new Authenticator() {
											  @Override
								              protected PasswordAuthentication getPasswordAuthentication() {
								                  return new PasswordAuthentication(username, psswd.toCharArray());
								              }
								          })
									      .version(HttpClient.Version.HTTP_2)
									      .build();
		String url = this.urlBuilder(SOREU, idMissione);
		System.out.println("Associazione: " + this.ASSOCIAZIONE_NAME);
		System.out.println("idMssione: " + idMissione);
		HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient EmmaTommy Bot") // add request header
                //.setHeader("User-Agent", "Mozilla/5.0 Firefox/26.0")
                .setHeader("Content-type", "application/xml")
                .build();


        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // print response headers
        HttpHeaders headers = response.headers();
        
        //headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        if (response.statusCode() == 200) {
        	System.out.println("Download was executed correctly (Status Code 200))");
        	System.out.println("Downloaded " + response.body().getBytes("UTF-8").length + " bytes");
        } else {
        	System.out.println("Error Status Code: " + response.statusCode() + " :(");
        }

        // print response body
        // System.out.println("Response: " + response.body());
		return response.body();
	}
	
	public String urlBuilder(String SOREU, String idMissione) {
		return PROTOCOL + username + ":" + psswd + MISSIONI_URL + SOREU + "/" + ASSOCIAZIONE_NAME + "/" + idMissione + ".xml";
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		emmaREST emma = new emmaREST();
		String emmaUrl = emma.urlBuilder(emma.soreu_COMO_NAME, "190000001");
		//System.out.println("Full Url: " + emmaUrl);
	}
	
}


