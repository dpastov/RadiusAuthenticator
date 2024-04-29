package org.example;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
            try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
                properties.load(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

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

            // Authenticate with invalid token
            RadiusPacket invalidResponse = authenticate(rc, username, invalidToken);

            // Write results to a file
            writeResults("result.txt", validResponse, invalidResponse);

        } catch (IOException | RadiusException e) {
            e.printStackTrace();
        }
    }

    private static RadiusPacket authenticate(RadiusClient rc, String username, String token) throws IOException, RadiusException {
        AccessRequest ar = new AccessRequest(username, token);
        return rc.authenticate(ar);
    }

    private static void writeResults(String filename, RadiusPacket validResponse, RadiusPacket invalidResponse) throws IOException {
        try (Writer writer = new FileWriter(filename)) {
            writer.write("Valid Token Authentication Result: " + (validResponse != null && validResponse.getPacketType() == RadiusPacket.ACCESS_ACCEPT ? "Success" : "Failure") + "\n");
            writer.write("Invalid Token Authentication Result: " + (invalidResponse != null && invalidResponse.getPacketType() == RadiusPacket.ACCESS_ACCEPT ? "Success" : "Failure") + "\n");
        }
    }
}
