import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import org.tinyradius.packet.AccessRequest;
import org.tinyradius.packet.RadiusPacket;
import org.tinyradius.util.RadiusClient;
import org.tinyradius.util.RadiusException;

public class TinyRadiusClient {

	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream("config.properties");
			properties.load(fis);
			fis.close();

			// Read properties
			String host = properties.getProperty("radius.host");
			String sharedSecret = properties.getProperty("radius.sharedSecret");
			String username = properties.getProperty("radius.username");
			String token = properties.getProperty("radius.token");

			// Initialize Radius client
			RadiusClient rc = new RadiusClient(host, sharedSecret);

			AccessRequest ar = new AccessRequest(username, token);
			RadiusPacket response = rc.authenticate(ar);

			// Write results to a file
			writeResults("result.txt", response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RadiusException e) {
			e.printStackTrace();
		}
	}

	private static void writeResults(String filename, RadiusPacket response) throws IOException {
		Writer writer = new FileWriter(filename);
		writer.write("Result: " + response.toString() + "\n");
		writer.close();
	}
}
