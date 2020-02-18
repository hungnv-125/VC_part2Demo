package exercise1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger("hung");

	public static void main(String[] args) throws IOException, InterruptedException {

		URL oracle = new URL("http://news.admicro.vn:10002/api/realtime?domain=kenh14.vn");
		int i = 0;
		int lastAddLog = 1;
		int count = 0;
		Person person;
		Gson gson = new Gson();

		while (i < 30) {
			try {
				i++;
				// oracle = new
				// URL("http://news.admicro.vn:10002/api/realtime?domain=kenh14.vn");
				URLConnection yc = oracle.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

				person = gson.fromJson(in.readLine(), Person.class);
				System.out.println(person.user);

				if ((float)person.user > (float)lastAddLog * 1.005) {
					lastAddLog = person.user;
					logger.info(String.valueOf(person.user));
					count = 0;
				} else {
					if (count == 5) {
						logger.debug(String.valueOf(person.user));
						lastAddLog = person.user;
						count = 0;
					} else
						count++;
				}

				in.close();
				Thread.sleep(500);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

