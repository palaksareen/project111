package uk.co.o2.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import uk.co.o2.utility.exception.GoogleServiceException;

@Service
public class CaptchaService {
	
	@Value("${secret}")
	String secret;

	@Value("${googleurl}")
	String googleurl;
	

	public boolean isVarifiedCaptcha(String userip, String grecaptcha) throws GoogleServiceException{
		try {
			return this.extractResponse(this.callGoogleRecaptchaService(userip, grecaptcha));
		} catch (Exception e) {
			throw new GoogleServiceException("Some internal Service is down");
		} 
	}
	
	
	private String callGoogleRecaptchaService(String userip, String grecaptcha)
			throws MalformedURLException, IOException, ProtocolException {
		String inputLine;
		StringBuffer response=new StringBuffer();
		URL verifyURL = new URL(null ,googleurl, new sun.net.www.protocol.https.Handler());
		String post = "remoteip="+userip+
				"+&response="+grecaptcha+
				"&secret="+secret+"";
		HttpsURLConnection connection = (HttpsURLConnection) verifyURL.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(true);
		connection.setUseCaches(false);
		connection.setReadTimeout(10000000);
		connection.setConnectTimeout(10000000);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=US-ASCII");
		connection.setRequestProperty("Content-Length",
				Integer.toString(post.length()));


		// do the post
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(post);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	private  boolean extractResponse(String response) throws IOException, JsonParseException {
		boolean isVarified=false;
		JsonFactory factory = new JsonFactory();
		JsonParser  parser  = factory.createParser(response.toString());

		while(!parser.isClosed()){
			JsonToken jsonToken = parser.nextToken();
			if(JsonToken.FIELD_NAME.equals(jsonToken)){
				String fieldName = parser.getCurrentName();
				System.out.println(fieldName+"------------"+parser.getValueAsString());

				jsonToken = parser.nextToken();

				if("success".equals(fieldName)){
					System.out.println(">>>>>>>>>>>>>>>>>>>>>"+parser.getValueAsString());
					isVarified = parser.getBooleanValue();
				} 
			}
		}
		return isVarified;
	}
}
