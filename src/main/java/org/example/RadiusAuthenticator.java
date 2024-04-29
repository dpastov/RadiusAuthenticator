package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.tinyradius.packet.AccessRequest;
import org.tinyradius.packet.RadiusPacket;
import org.tinyradius.util.RadiusClient;
import org.tinyradius.util.RadiusException;

public class RadiusAuthenticator {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            // Load properties from file
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            properties.load(fileInputStream);
            fileInputStream.close();

            // Read properties
            String host = properties.getProperty("radius.host");
            String sharedSecret = properties.getProperty("radius.sharedSecret");
            String username = properties.getProperty("radius.username");
            String validToken = properties.getProperty("radius.validToken");
            String invalidToken = properties.getProperty("radius.invalidToken");

            // Initialize Radius client
            RadiusClient rc = new RadiusClient(host, sharedSecret);

            // Authenticate with valid token
            RadiusPacket validResponse = authenticate(rc, username, validToken);
            logResult("Valid Token", validResponse);

            // Authenticate with invalid token
            RadiusPacket invalidResponse = authenticate(rc, username, invalidToken);
            logResult("Invalid Token", invalidResponse);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RadiusException e) {
			e.printStackTrace();
		}
    }

    private static RadiusPacket authenticate(RadiusClient rc, String username, String token) throws IOException, RadiusException {
        AccessRequest ar = new AccessRequest(username, token);
        return rc.authenticate(ar);
    }

    private static void logResult(String type, RadiusPacket response) {
        String result = (response != null && response.getPacketType() == RadiusPacket.ACCESS_ACCEPT) ? "Success" : "Failure";
        System.out.println(type + " Authentication Result: " + result);
        // Log the result to a file for debugging
        // You can implement the file logging here
    }
}
