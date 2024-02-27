package edacrawler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Payload {
    public ArrayList<String> links;
    public LinkedHashMap<String, ArrayList<String>> imgs;
    public String html = "";

    public Payload() {
        links = new ArrayList<>();
        imgs = new LinkedHashMap<String, ArrayList<String>>();
    }
	
}
