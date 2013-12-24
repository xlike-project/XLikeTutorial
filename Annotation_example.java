import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Test_Example {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		HttpURLConnection connection;			
		DataOutputStream outStream;			

		String sentence;					
		sentence = "Bruce Springsteen is an American singer-songwriter and multi-instrumentalist.";					

		for(int i=0; i<args.length; i++){
			sentence = sentence.concat(args[i]+" ");
		}					

		URL url = new URL("http://sandbox-xlike.isoco.com/services/analysis_en/analyze");
		
		String body = URLEncoder.encode(sentence, "UTF-8");
		body = body.replace("+", "%20");	
		body = "text=" + body + "&target=entities&conll=false";

		connection = (HttpURLConnection) url.openConnection();						
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Length",
				"" + Integer.toString(body.getBytes().length));
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		System.out.println("Sending POST...");					
		outStream = new DataOutputStream(connection.getOutputStream());					
		outStream.writeBytes(body);					
		outStream.flush();				
		outStream.close();					

		System.out.println("Receiving response...");
		//Receiving response
		if (connection.getResponseCode() != 200) { 
			throw new IOException(connection.getResponseMessage()); 
		}

		InputStream is = connection.getInputStream();						
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));				
		StringBuffer response = new StringBuffer();					
		String str;

		while ((str = rd.readLine()) != null) {
			response.append(str);
		}
		rd.close();

		System.out.println(response);			

		File file = new File("test.xml");
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		writer.write(response.toString());
		writer.flush();
		writer.close();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();				
		Document doc = dBuilder.parse(new File("test.xml"));
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("sentence");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {						
				Element element = (Element) node;	
				NodeList sents = element.getElementsByTagName("text").item(0).getChildNodes();
				System.out.println(sents.item(0).getNodeValue());	

				//Tokens
				Element tokens = (Element) element.getElementsByTagName("tokens").item(0);
				NodeList list = tokens.getElementsByTagName("token");	
				for (int j = 0; j < list.getLength(); j++) {				
					Node token = list.item(j);
					System.out.println(token.getTextContent());
				}
			}
		}
		
		///////////////////////////////////////////
		//Semantic annotations
		///////////////////////////////////////////
		URL curl2 = new URL("http://km.aifb.kit.edu/services/annotation-en/");
		HttpURLConnection urlConnection2;
		DataOutputStream outStream2;
		InputStream iscA;
		
		//Opening connection
		urlConnection2 = (HttpURLConnection) curl2.openConnection();
		urlConnection2.setRequestMethod("POST");
		urlConnection2.setDoInput(true);
		urlConnection2.setDoOutput(true);
		urlConnection2.setUseCaches(false);
		String full = response.toString();
		urlConnection2.setRequestProperty("Content-Length",
		"" + Integer.toString(full.getBytes().length));
		urlConnection2.setRequestProperty("Content-Type", "text/xml");
		
		// Create I/O streams
		outStream2 = new DataOutputStream(urlConnection2.getOutputStream());
		outStream2.write(full.getBytes("UTF-8"));
		
		outStream2.flush();
		outStream2.close();
		
		StringBuffer response2= new StringBuffer();
		String str2;
		
		iscA = urlConnection2.getInputStream();
		BufferedReader rd2 = new BufferedReader(new InputStreamReader(iscA, "UTF-8"));
		response2 = new StringBuffer();
		str="";
		
		// Printing response
		while ((str2 = rd2.readLine()) != null) {
		response2.append(str2);
		}
					
		String result= response2.toString();
		System.out.println(result);
				
	}
}