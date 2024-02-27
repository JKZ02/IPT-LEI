package edacrawler;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class API {
	
	public static ArrayList<Payload> getImages(String urlO, ArrayList<String> links, int profundidade, boolean flag, ArrayList<Payload> payloads) {
		
		ArrayList<String> linksTemp = new ArrayList<>();
		for (String url : links) {
			if(flag && url.startsWith(urlO) || !flag) {
				try {
					EDACrawler eda = new EDACrawler();
					Payload ini = eda.process(url, profundidade);
					
					payloads.add(ini);
					linksTemp.addAll(ini.links);
					//System.out.println(url + " - " + ini.links);
			        //System.out.println(url + " - " + ini.imgs);
				} catch (IOException e) {
					//System.out.println(url + " - Indisponível");
					continue;
				}
			}
		}
		links.removeAll(links);
		
		if(profundidade != 1) {
			profundidade--;
			getImages(urlO, linksTemp, profundidade, flag, payloads);
		}
		
		return payloads;
	}
	
	public static <K, V> LinkedHashMap<K, V> removeDuplicates(LinkedHashMap<K, V> map, LinkedHashMap<K, V> mapS) {
	    for(HashMap.Entry<K, V> entry : mapS.entrySet()) {
	        if (!map.containsKey(entry.getKey())) {
	            map.put(entry.getKey(), entry.getValue());
	        }
	    }
	    return map;
	}
	
	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
		ArrayList<T> newList = new ArrayList<T>();

		for (T element : list) {
			if (!newList.contains(element)) {
				newList.add(element);
			}
		}
		return newList;
	}
	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
	
	public static ImageIcon resizeProportional(ImageIcon image, int width, int height) {
        double pWidth = width / image.getIconWidth();
        double pHeight = height / image.getIconHeight();
        
        if(pWidth == 0) {
        	pWidth = image.getIconWidth() / width;
        }
        if(pHeight == 0) {
        	pHeight = image.getIconHeight() / height;
        }
        
        if (pWidth < pHeight) {
            height = (int)(image.getIconHeight() * pWidth);
            width = (int)(image.getIconWidth() * pWidth);
        }
        else {
            height = (int)(image.getIconHeight() * pHeight);
            width = (int)(image.getIconWidth() * pHeight);
        }
        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }
	
	public static void showImage(ImageIcon img, String title) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.add(new JLabel(img), "Center");
        JOptionPane.showMessageDialog(null, panel, title, -1);
    }
	
	public static void setImageByUrl(JLabel label, String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(path));
		
			int wi = image.getWidth();
			int he = image.getHeight();
			
			if(wi > label.getWidth()-25) {
				wi = label.getWidth()-25;
			}
			if(he > label.getHeight()-25) {
				he = label.getHeight()-25;
			}
			
			Image imgScale = image.getScaledInstance(wi, he, Image.SCALE_SMOOTH);
			ImageIcon newimg = new ImageIcon(imgScale);
			label.setIcon(newimg);
		
		} catch (IOException | NullPointerException e) {
			System.out.println("Error displaying: " + path);
			
			ImageIcon ico = new ImageIcon(API.class.getResource("/images/error.png"));
			Image img = ico.getImage();
			Image imgScale = img.getScaledInstance(label.getWidth()-25, label.getHeight()-25, Image.SCALE_SMOOTH);
			ImageIcon newimg = new ImageIcon(imgScale);
			label.setIcon(newimg);
		}
    }
	
	public static void setGifByURL(JLabel label, String url) {
		try {
			ImageIcon ico = new ImageIcon(new URL(url));
			
			int wi = ico.getIconWidth();
			int he = ico.getIconHeight();
			
			if(wi > label.getWidth()-25) {
				wi = label.getWidth()-25;
			}
			if(he > label.getHeight()-25) {
				he = label.getHeight()-25;
			}
			
			ico.setImage(ico.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
			label.setIcon(ico);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static String removerSpecialCharacter(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}

