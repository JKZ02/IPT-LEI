package edacrawler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import GUI.Frame;

public class EDACrawler {

	private Connection connection = null;
	
    public EDACrawler() throws IOException {
    }

    public Payload process(String url, int nivel) throws IOException {
    	Payload payload = new Payload();
    	connection = mysqlconnection.bdconnector();
    	
    	if(Frame.chckbxOfflineSearch.isSelected()) {
	    	try {
				Statement stmt = connection.createStatement();
			    ResultSet rs = stmt.executeQuery("SELECT * FROM images WHERE domain = '" + url + "' AND nivel=" + nivel);
			    if(rs.next()) {
			    	ArrayList<String> attrs = new ArrayList<>();
			    	attrs.add(rs.getString(4));
			    	attrs.add(rs.getString(3));
			    	payload.imgs.put(rs.getString(2), attrs);
			    	
				    while (rs.next()) {
				    	attrs = new ArrayList<>();
				    	if (!payload.imgs.containsKey(rs.getString(2))) {
					    	attrs.add(rs.getString(4));
					    	attrs.add(rs.getString(3));
					    	
					    	payload.imgs.put(rs.getString(2), attrs);
				    	}
				    }
				    //System.out.println("Current");
			    }else {
			    	payload = new Payload();
			    	payload.imgs = processImgs(url, nivel).imgs;
			    }
			    
			    stmt = connection.createStatement();
			    rs = stmt.executeQuery("SELECT * FROM linksvisitados WHERE domain = '" + url + "' AND nivel=" + nivel);
			    if(rs.next()) {
			    	payload.links.add(rs.getString(2));
			    	
				    while (rs.next()) {
				    	if (!payload.links.contains(rs.getString(2))) {
				    		payload.links.add(rs.getString(2));
				    	}
				    }
				    //System.out.println("Current");
			    }else {
			    	payload.links = processLinks(url, nivel).links;
			    }
			    
			} catch (SQLException e) {
				e.printStackTrace();
				payload = new Payload();
		    	payload.links = processLinks(url, nivel).links;
		    	payload.imgs = processImgs(url, nivel).imgs;
			}
    	}else {
    		payload = new Payload();
	    	payload.links = processLinks(url, nivel).links;
	    	payload.imgs = processImgs(url, nivel).imgs;
    	}
    	try {connection.close();} catch (SQLException e) {}
    	
		return payload;
    }
    
    public Payload processLinks(String url, int nivel) throws IOException {
    	
    	Payload payload = new Payload();

        /*if (!url.endsWith("/")) {
            url += "/";
        }*/

        Document doc = Jsoup.connect(url).get();

        Elements links = doc.select("a");
        Iterator<Element> aux = links.iterator();

        while (aux.hasNext()) {
            String href = aux.next().attr("abs:href");
            if (href.length() > 1) {
            	if(href.contains("../")) {
            		href = href.replace("../","");
            	}
            	if (!payload.links.contains(href)) {
            		
            		if (Frame.chckbxAutosave.isSelected()) {
						try {
							String sql2 = "REPLACE INTO linksvisitados (domain, link, nivel) VALUES (?,?,?)";
			    			
			    			PreparedStatement statement2 = connection.prepareStatement(sql2);
			    			statement2.setString(1, url);
			    			statement2.setString(2, href);
			    			statement2.setInt(3, nivel);
			    			statement2.executeUpdate();
			            	statement2.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
            		}
	            	
            		payload.links.add(href);
            	}
            }
        }
    	
        return payload;
    }
    
    public Payload processImgs(String url, int nivel) throws IOException {
    	
    	Payload payload = new Payload();

        Document doc = Jsoup.connect(url).get();
        
        Elements imgs = doc.select("img");
        Iterator<Element> aux = imgs.iterator();

        while (aux.hasNext()) {
        	Element elem = aux.next();
            String src = elem.attr("abs:src");
            String title = elem.attr("abs:title");
            String alt = elem.attr("abs:alt");
            if (src.length() > 1) {
            	if(src.contains("../")) {
            		src = src.replace("../","");
            	}
            	if(src.contains("/./")) {
            		src = src.replace("/./","/");
            	}
            	if (!payload.imgs.containsKey(src)) {
            		//System.out.println("src: " + src);
            		ArrayList<String> attrs = new ArrayList<>();
            		if(title.length() > 0) {
            			//System.out.println("title: " + title.substring(title.lastIndexOf("/")+1, title.length()));
            			title = title.substring(title.lastIndexOf("/")+1, title.length());
            			attrs.add(title);
            		}else {
            			//System.out.println("title:");
            			title = "";
            			attrs.add(title);
            		}
            		if(alt.length() > 0) {
            			//System.out.println("alt: " + alt.substring(alt.lastIndexOf("/")+1, alt.length()));
            			alt = alt.substring(alt.lastIndexOf("/")+1, alt.length());
            			attrs.add(alt);
            		}else {
            			//System.out.println("alt:");
            			alt = "";
            			attrs.add("");
            		}
            		
            		if (Frame.chckbxAutosave.isSelected()) {
    					try {
    						String sql = "REPLACE INTO images (domain, path, title, alt, nivel) VALUES (?, ?, ?, ?, ?)";
			    			
			    			PreparedStatement statement = connection.prepareStatement(sql);
			    			
			    			statement.setString(1, url);
			    			statement.setString(2, src);
			    			statement.setString(3, alt);
			    			statement.setString(4, title);
			    			statement.setInt(5, nivel);
			    			statement.executeUpdate();
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
                	}
            		
            		payload.imgs.put(src, attrs);
            	}
            }
        }
    	
        return payload;
    }

}
