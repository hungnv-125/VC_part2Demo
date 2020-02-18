package exercise2;

import java.io.IOException;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

public class Main {
	private static final Logger logger = LoggerFactory.getLogger("ex2");
	// Tạo Document từ URL và lấy title
    public static void getTitleOfUrl() throws IOException {
        Document doc = Jsoup.connect("http://dantri.com.vn/").get();
        String title = doc.text() ;
        System.out.println(title);
        logger.debug(title);
    }
    public static void getTitleOfHtmlString() throws IOException {
        String html = "<html><head><title>Java Tutorials</title></head>"
            + "<body>Welcome to GP Coder</body></html>";
        Document doc = Jsoup.parse(html);
        String title = doc.title(); 
        System.out.println(title);
        System.out.println(doc.title());
    }
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		getTitleOfUrl();
	}

}
