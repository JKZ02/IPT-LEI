package GUI;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import org.apache.batik.swing.JSVGCanvas;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import edacrawler.API;
import edacrawler.Payload;
import edacrawler.mysqlconnection;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel contentPane;
	private static JTextField textLink;
	private JTextField textTema;
	private static JPanel panel;
	private static JButton btnFiltrar;
	private static JButton btnDownloadAllImg;
	private static JProgressBar progressBar;
	public static JCheckBox chckbxAutosave;
	public static JCheckBox chckbxOfflineSearch;
	private static boolean bool = false;
	private static int width = 0;
	private static int height = 0;
	private static int iVer = 1;
	private Thread th;
	private Thread th2;
	private Thread th4;
	private Thread th5;
	private LinkedHashMap<String, ArrayList<String>> Himgs;
	private static JLabel lName;
	private static JPanel pName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
		} catch (ParseException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo.png")));
		setTitle("CrawlMe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1160, 728);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				bool = false;
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane jScrollPane1 = new JScrollPane();
		
		panel = new JPanel();
		jScrollPane1.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblLink = new JLabel();
		lblLink.setText("Link :");
		
		JLabel lblNvel = new JLabel();
		lblNvel.setText(" N\u00EDvel :");
		
		progressBar = new JProgressBar();
		
		JSlider Slider = new JSlider();
		Slider.setValue(1);
		Slider.setPaintTicks(true);
		Slider.setPaintLabels(true);
		Slider.setMinimum(1);
		Slider.setMaximum(12);
		Slider.setMajorTickSpacing(1);
		
		JCheckBox chckbxMesmoDomnio = new JCheckBox("Mesmo dom\u00EDnio");
		
		chckbxAutosave = new JCheckBox("Guardar dados da pesquisa");
		
		chckbxOfflineSearch = new JCheckBox("Pesquisa p. dados guardados");
		
		JButton btnVoltar = new JButton();
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				
				th4 = new Thread() {
	                @Override
	                public void run(){
	                	
	                	progressBar.setValue(Himgs.size());
	                	panel.removeAll();
				    	
				    	width = 0;
				    	height = 0;
				    	iVer = 1;
				    	for(String img : Himgs.keySet()) {
				    		addImageToPanel(Himgs, img);
				    	}
				    	
				    	btnVoltar.setEnabled(false);
	                }
				};th4.start();
				
			}
		});
		btnVoltar.setText("Voltar");
		btnVoltar.setEnabled(false);
		
		btnFiltrar = new JButton();
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				th2 = new Thread() {
	                @Override
	                public void run(){
	                	
	                	if(!textTema.getText().isEmpty()) {
	                		panel.removeAll();
	                		width = 0;
					    	height = 0;
					    	iVer = 1;
		                	for (String key : Himgs.keySet()) {
		                		//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                		if(API.removerSpecialCharacter(key.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			addImageToPanel(Himgs, key);
		                		}else {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			for (String value : Himgs.get(key)) {
		                				if(API.removerSpecialCharacter(value.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                					addImageToPanel(Himgs, key);
		                					break;
		                				}
		                			}
		                			
		                		}
		                	}
		                	progressBar.setValue(progressBar.getMaximum());
	                	}
	                	btnVoltar.setEnabled(true);
	                }
				};th2.start();
			}
		});
		btnFiltrar.setEnabled(false);
		btnFiltrar.setText("Filtrar");
		
		JButton btnSearch = new JButton();
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				progressBar.setMaximum(100);
				
				th = new Thread() {
	                @Override
	                public void run(){
	                	/*long milis1 = System.currentTimeMillis();
	                	SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss z");
	                	Date date = new Date(milis1);
	                	System.out.println("Start Search: " + formatter.format(date));*/
	                	progressBar.setValue(1);
	                	
						String link = textLink.getText();
				    	ArrayList<String> links = new ArrayList<>();
				    	links.add(link);
				    	ArrayList<Payload> payloads = new ArrayList<>();
				    	
				    	//ArrayList<String> imgsDu = new ArrayList<>();
				    	Himgs = null;
				    	for(Payload payl : API.getImages(link, links, Slider.getValue(), chckbxMesmoDomnio.isSelected(), payloads)) {
				    		progressBar.setValue(3);
				    		
				    		//imgsDu.addAll(payl.imgs);
				    		
				    		if(Himgs != null) {
				    			Himgs = API.removeDuplicates(payl.imgs, Himgs);
				    		}else {
				    			Himgs = payl.imgs;
				    		}
				    		//System.out.println(payl.imgs);
				    	}
				    	//ArrayList<String> imgs = API.removeDuplicates(imgsDu);
				    	//System.out.println(Himgs);
				    	
				    	LinkedHashMap<String, ArrayList<String>> imgs = new LinkedHashMap<String, ArrayList<String>>();
				    	if(textTema.getText().isEmpty()) {
				    		imgs = Himgs;
				    		progressBar.setMaximum(imgs.size());
				    		//System.out.println("Imagens: " + imgs.size());
				    	}else {
				    		for (String key : Himgs.keySet()) {
		                		//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                		if(API.removerSpecialCharacter(key.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			imgs.put(key, Himgs.get(key));
		                		}else {
		                			//System.out.println("key: " + key + " value: " + Himgs.get(key));
		                			
		                			for (String value : Himgs.get(key)) {
		                				if(API.removerSpecialCharacter(value.toLowerCase()).contains(API.removerSpecialCharacter(textTema.getText().toLowerCase()))) {
		                					imgs.put(key, Himgs.get(key));
		                				}
		                			}
		                			
		                		}
		                	}
				    		progressBar.setMaximum(imgs.size());
				    		//System.out.println(imgs.size());
				    	}
				    	/*long milis2 = System.currentTimeMillis();
				    	Date date1 = new Date(milis2);
				    	System.out.println("End Search | Start Display: " + formatter.format(date1) + " - Time: " + (new SimpleDateFormat("mm:ss:SSS")).format(new Date(milis2 - milis1)));
				    	*/
				    	btnFiltrar.setEnabled(false);
				    	btnDownloadAllImg.setEnabled(false);
				    	panel.removeAll();
				    	
				    	width = 0;
				    	height = 0;
				    	iVer = 1;
				    	for(String img : imgs.keySet()) {
				    		addImageToPanel(imgs, img);
				    	}
				    	
				    	/*long milis3 = System.currentTimeMillis();
				    	Date date2 = new Date(System.currentTimeMillis());
				    	System.out.println("End Display: " + formatter.format(date2) + " - Time: " + (new SimpleDateFormat("mm:ss:SSS")).format(new Date(milis3 - milis2)));
				    	*/
					}
		        };th.start();
				
			}
		});
		btnSearch.setText("Procurar");
		
		JButton btnCancel = new JButton();
		btnCancel.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				if(th.isAlive()) {
					th.stop();
					progressBar.setValue(0);
				}
				
				if(th2.isAlive()) {
					th2.stop();
					progressBar.setValue(0);
				}
				
				if(th4.isAlive()) {
					th4.stop();
					progressBar.setValue(0);
				}
				
				if(th5.isAlive()) {
					th5.stop();
					progressBar.setValue(0);
				}
			}
		});
		btnCancel.setText("Cancelar");
		
		textLink = new JTextField();
		
		JLabel lblTema = new JLabel("Tema");
		lblTema.setHorizontalAlignment(SwingConstants.CENTER);
		
		textTema = new JTextField();
		
		JButton btnResetDB = new JButton();
		btnResetDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Thread th3 = new Thread() {
	                @Override
	                public void run(){
						Connection connection = mysqlconnection.bdconnector();
		    			
		    			String sql = "DELETE FROM images";
		    			String sql2 = "DELETE FROM linksvisitados";
		    			
						try {
							PreparedStatement statement2 = connection.prepareStatement(sql2);
							
			    			PreparedStatement statement = connection.prepareStatement(sql);
			    			
			    			statement.executeUpdate();
			    			statement2.executeUpdate();
			    			
			            	statement.close();
			            	statement2.close();
			            	connection.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
	                }
		        };th3.start();
			}
		});
		btnResetDB.setText("Eliminar todos os dados guardados");
		
		btnDownloadAllImg = new JButton();
		btnDownloadAllImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				th5 = new Thread() {
	                @Override
	                public void run(){
	                	
	                	Object[] options1 = { "PNG", "ZIP", "Cancelar" };
	            		
				        int result = JOptionPane.showOptionDialog(null, "Escolha o formato que pretende", "CrawlMe", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, null);
				        if (result == JOptionPane.YES_OPTION){
				        
							try {
			            		JFileChooser chooser = new JFileChooser();
			            		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    				int returnVal = chooser.showOpenDialog(null);
			    				
			    				if(returnVal == JFileChooser.APPROVE_OPTION) {
			    					
			    					for(String img : Himgs.keySet()) {
				    					String nome1 = "";
					    	    		if(img.split("/")[img.split("/").length-1].contains("?")) { nome1 = img.split("/")[img.split("/").length-1].split("\\?")[0]; }else{ nome1 = img.split("/")[img.split("/").length-1]; }
					                	
				                		if(nome1.endsWith(".php")) {
				                			API.saveImage(img, chooser.getSelectedFile().getAbsolutePath() + "\\" + API.generateRandomString() + ".png");
				                		}else {
				                			API.saveImage(img, chooser.getSelectedFile().getAbsolutePath() + "\\" + API.generateRandomString() + "." + nome1.split("\\.")[1]);
				                		}
			    					}
									JOptionPane.showMessageDialog(null,"Imagens salvas com sucesso" ,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
			    				}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								JOptionPane.showMessageDialog(null,"Houve um erro ao salvar a imagem." ,"Erro", JOptionPane.ERROR_MESSAGE);
							}
				        }
				        if (result == JOptionPane.NO_OPTION){
				        	
				        	try {
			            		JFileChooser chooser = new JFileChooser();
			            		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    				int returnVal = chooser.showOpenDialog(null);
			    				
			    				if(returnVal == JFileChooser.APPROVE_OPTION) {
			    					
			    					File f = new File(chooser.getSelectedFile().getAbsolutePath() + "\\Imagens.zip");
				    	    		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
			    					
			    					for(String img : Himgs.keySet()) {
				    					String nome1 = "";
					    	    		if(img.split("/")[img.split("/").length-1].contains("?")) { nome1 = img.split("/")[img.split("/").length-1].split("\\?")[0]; }else{ nome1 = img.split("/")[img.split("/").length-1]; }
					    	    		if(nome1.endsWith(".php")) { nome1 = nome1.split("\\.")[0] + ".png";}
					    	    		
					    	    		ZipEntry e = new ZipEntry(API.generateRandomString() + "." + nome1.split("\\.")[1]);
					    	    		out.putNextEntry(e);
					    	    		URL url = new URL(img);
					    	    		InputStream is = url.openStream();

					    	    		byte[] b = new byte[2048];
					    	    		int length;

					    	    		while ((length = is.read(b)) != -1) {
					    	    			out.write(b, 0, length);
					    	    		}

					    	    		is.close();
					    	    		out.closeEntry();
			    					}
			    					out.close();
			    					
									JOptionPane.showMessageDialog(null,"Imagens salvas com sucesso" ,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
			    				}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								JOptionPane.showMessageDialog(null,"Houve um erro ao salvar as imagens." ,"Erro", JOptionPane.ERROR_MESSAGE);
							}
				        }
	                }
		        };th5.start();
				
			}
		});
		btnDownloadAllImg.setEnabled(false);
		btnDownloadAllImg.setText("Transferir todas as imagens");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(lblLink, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(textLink, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
					.addGap(203)
					.addComponent(lblTema, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addGap(185))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNvel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(Slider, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxOfflineSearch, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(chckbxAutosave, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
									.addGap(16))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(chckbxMesmoDomnio, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
									.addGap(66))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(4)
									.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
									.addGap(14)
									.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
									.addGap(4))))
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE))
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(115)
							.addComponent(textTema, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(125)
							.addComponent(btnFiltrar, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnResetDB, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(btnDownloadAllImg, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1130, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(lblLink))
						.addComponent(textLink, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTema)))
					.addGap(3)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(21)
									.addComponent(lblNvel))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addComponent(Slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(32)
											.addComponent(chckbxOfflineSearch))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(13)
											.addComponent(chckbxAutosave))
										.addComponent(chckbxMesmoDomnio, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
									.addGap(2)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnSearch)
										.addComponent(btnCancel))))
							.addGap(7)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(textTema, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnFiltrar)
								.addComponent(btnVoltar))
							.addGap(15)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnResetDB)
								.addComponent(btnDownloadAllImg))))
					.addGap(6)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static void addImageToPanel(LinkedHashMap<String, ArrayList<String>> imgs, String img) {
		
		JLabel Jimage;
		JLabel lblImgNome;
		JPanel panel1;
		
		//System.out.println(img);
		//if(img.contains(".php") || img.contains(".svg") || img.contains(".gif") || img.contains(".apng") || img.contains(".jpg") || img.contains(".png") || img.contains(".jpeg") || img.contains(".jfif")) {
			if(!img.endsWith(".svg")) {
    			//System.out.println(img);
    			
				String nome1 = "";
	    		if(img.split("/")[img.split("/").length-1].contains("?")) { nome1 = img.split("/")[img.split("/").length-1].split("\\?")[0]; }else{ nome1 = img.split("/")[img.split("/").length-1]; }
	    		final String nome2 = nome1;
	    		
	    		/*if (chckbxAutosave.isSelected()) {	
					try {
							Connection connection = mysqlconnection.bdconnector();
							System.out.println("Conectado.");
			    			
			    			String sql = "REPLACE INTO images (domain, path, title, alt) VALUES (?, ?, ?, ?)";
			    			
			    			PreparedStatement statement = connection.prepareStatement(sql);
			    			
			    			statement.setString(1, textLink.getText());
			    			statement.setString(2, img);
			    			statement.setString(3, imgs.get(img).get(1));
			    			statement.setString(4, imgs.get(img).get(0));
			    			int rows = statement.executeUpdate();
			    			if (rows > 0) {
			    				System.out.println("Dados atualizados.");
			    			}
			            	statement.close();
			            	connection.close();
					} catch (SQLException | IOException e1) {
						System.out.println("Ocorreu um erro.");
						e1.printStackTrace();
					}
				}*/
	    		
				JPopupMenu popup = new JPopupMenu();
	    		JMenuItem down = new JMenuItem("Salvar imagem");
	    		down.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				Thread th1 = new Thread() {
	    	                @Override
	    	                public void run(){
	    	                	try {
	    	                		JFileChooser chooser = new JFileChooser();
	    	                		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    	        				int returnVal = chooser.showOpenDialog(null);
	    	        				
	    	        				if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	                		if(nome2.endsWith(".php")) {
		    	                			API.saveImage(img, chooser.getSelectedFile().getAbsolutePath() + "\\" + nome2.split("\\.")[0] + ".png");
		    	                		}else {
		    	                			API.saveImage(img, chooser.getSelectedFile().getAbsolutePath() + "\\" + nome2);
		    	                		}
										
										JOptionPane.showMessageDialog(null,"Imagem salva com sucesso" ,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
	    	        				}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									JOptionPane.showMessageDialog(null,"Houve um erro ao salvar a imagem." ,"Erro", JOptionPane.ERROR_MESSAGE);
								}
	    	                }
	    	            };th1.start();
	    			}
	    		});
	    		popup.add(down);
	    		
	    		//if(nome1.length() > 20 && nome1.contains(".")) { nome1 = nome1.substring(0, 15)+"..."+nome1.substring(nome1.lastIndexOf(".")-1, nome1.length()); } else
	    		if(nome1.length() > 20) { nome1 = nome1.substring(0, 15)+"..."+nome1.substring(nome1.length()-5, nome1.length()); }
	    		final String nome = nome1;
				
	    		Jimage = new JLabel();
	    		Jimage.setOpaque(true);
	    		Jimage.addMouseListener(new MouseAdapter() {
	    			@Override
	    			public void mouseClicked(MouseEvent paramMouseEvent) {
	    				if(SwingUtilities.isRightMouseButton(paramMouseEvent)) {
	    					if(pName != null) {
    							pName.setBackground(new Color(240, 240, 240));
    						}
	    					if(lName != null) {
	    						lName.setBackground(new Color(240, 240, 240));
	    						lName.setForeground(new Color(0, 0, 0));
	    						lName = Jimage;
	    					}
	    					
	    					Jimage.setBackground(new Color(0, 102, 255));
		    				Jimage.setForeground(new Color(255, 255, 255));
	    					
		    				lName = Jimage;
	    					bool = true;
		    				
	    					popup.show(Jimage, paramMouseEvent.getX(), paramMouseEvent.getY());
	    				}else if(SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
	    				
		    				Jimage.setBackground(new Color(0, 102, 255));
		    				Jimage.setForeground(new Color(255, 255, 255));
		    				
		    				if(!bool){
		    					lName = Jimage;
		    					bool = true;
		    				}else {
		    					if(lName.getIcon() != null && lName.getIcon().equals(Jimage.getIcon())) {
		    						String iLink = "";
			    					for (String url : imgs.keySet()) {
			    						if(url.equals(img)) {
			    							iLink = url;
			    							break;
			    						}
			    					}
			    					
			    					if (Jimage.getIcon() != null) {
			    			            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			    			            int w = (int)(screenSize.width * 0.75);
			    			            int h = (int)(screenSize.height * 0.75);
			    			            
										try {
											ImageIcon imag = API.resizeProportional(new ImageIcon(new URL(iLink)), w, h);
								    		
											API.showImage(imag, nome);
										} catch (MalformedURLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			    			        }
		    					}else if(lName.getText().equals(Jimage.getText()) && lName.getIcon().equals(Jimage.getIcon())) {
			    					String iLink = "";
			    					for (String url : imgs.keySet()) {
			    						if(url.equals(img)) {
			    							iLink = url;
			    							break;
			    						}
			    					}
			    					
			    					if (Jimage.getIcon() != null) {
			    			            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			    			            int w = (int)(screenSize.width * 0.75);
			    			            int h = (int)(screenSize.height * 0.75);
			    			            
										try {
											ImageIcon imag = API.resizeProportional(new ImageIcon(new URL(iLink)), w, h);
								    		
											API.showImage(imag, nome);
										} catch (MalformedURLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			    			        }
		    					}else {
		    						if(pName != null) {
		    							pName.setBackground(new Color(240, 240, 240));
		    						}
		    						lName.setBackground(new Color(240, 240, 240));
		    						lName.setForeground(new Color(0, 0, 0));
		    						lName = Jimage;
		    					}
		    					pName = null;
		    				}
	    				}
	    			}
	    		});
	    		Jimage.setBounds(0+width, 0+height, 220, 220);
	    		Jimage.setPreferredSize(new Dimension(220, 220));
	    		Jimage.setHorizontalTextPosition(SwingConstants.CENTER);
	    		Jimage.setVerticalTextPosition(SwingConstants.BOTTOM);
	    		Jimage.setHorizontalAlignment(SwingConstants.CENTER);
	    		Jimage.setVerticalAlignment(SwingConstants.CENTER);
	    		
	    		Jimage.setText(nome);
	    		
		    	if(img.contains(".gif") || img.contains(".apng")) {
		    		API.setGifByURL(Jimage, img);
			    }else {
			    	API.setImageByUrl(Jimage, img);
			    }
	    		
		    	GridBagConstraints gbc_Jimage = new GridBagConstraints();
				gbc_Jimage.gridx = width;
				gbc_Jimage.gridy = height;
                panel.add(Jimage, gbc_Jimage);
                panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent paramMouseEvent) {
						if(pName != null) {
							pName.setBackground(new Color(240, 240, 240));
						}
						if(lName != null) {
							lName.setForeground(new Color(0, 0, 0));
						}
						
						Jimage.setBackground(new Color(240, 240, 240));
						Jimage.setForeground(new Color(0, 0, 0));
						bool = false;
					}
				});
                width++;
                if(width >= 5) { width = 0; width = 0; height ++;}
                
    		}else {
    			if(img.contains(".svg")) {
    				
    				String nome = "";
		    		if(img.split("/")[img.split("/").length-1].contains("?")) { nome = img.split("/")[img.split("/").length-1].split("\\?")[0]; }else{ nome = img.split("/")[img.split("/").length-1]; }
		    		final String nome2 = nome;
		    		
		    		JPopupMenu popup = new JPopupMenu();
		    		JMenuItem down = new JMenuItem("Salvar imagem");
		    		down.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent arg0) {
		    				Thread th1 = new Thread() {
		    	                @Override
		    	                public void run(){
				    				try {
										API.saveImage(img, nome2);
										
										JOptionPane.showMessageDialog(null,"Imagem salva com sucesso" ,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										JOptionPane.showMessageDialog(null,"Houve um erro ao salvar a imagem." ,"Erro", JOptionPane.ERROR_MESSAGE);
									}
		    	                }
		    	            };th1.start();
		    			}
		    		});
		    		popup.add(down);
		    		
		    		//if(nome.length() > 20) { nome = nome.substring(0, 15)+"..."+nome.substring(nome.lastIndexOf(".")-1, nome.length()); }
		    		if(nome.length() > 20) { nome = nome.substring(0, 15)+"..."+nome.substring(nome.length()-5, nome.length()); }
		    		
	    			lblImgNome = new JLabel(nome);
	    			lblImgNome.setAlignmentY(0.0f);
	    			lblImgNome.setAlignmentX(0.5f);
	    			
	    			JSVGCanvas svg = new JSVGCanvas();
	    			svg.setURI(img);
	    			svg.setBackground(new Color(0, 0, 0, 0));
	    			svg.setBounds(0+width, 0+height, 220, 220);
	    			
	    			panel1 = new JPanel();
	    			panel1.setOpaque(true);
	    			svg.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseClicked(MouseEvent paramMouseEvent) {
	    					
	    					if(SwingUtilities.isRightMouseButton(paramMouseEvent)) {
	    						if(lName != null) {
		    						if(lName.getBackground() != new Color(0, 102, 255)) {
	    								lName.setBackground(new Color(240, 240, 240));
	    							}
		    						lName.setForeground(new Color(0, 0, 0));
	    						}
    							if(pName != null) {
									pName.setBackground(new Color(240, 240, 240));
								}
	    						
	    						panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
	    						
		    					lName = lblImgNome;
	    						pName = panel1;
	    						bool = true;
		    					
		    					popup.show(svg, paramMouseEvent.getX(), paramMouseEvent.getY());
		    				}else if(SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
	    					
		    					panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
		    					
		    					if(!bool){
		    						lName = lblImgNome;
		    						pName = panel1;
		    						bool = true;
		    					}else {
		    						if(lName.getText().equals(lblImgNome.getText())) {
		    	    					JOptionPane.showMessageDialog(null,"Infelizmente o formato '.svg' não é suportado." ,"Aviso", JOptionPane.WARNING_MESSAGE);
		    						}else {
		    							if(lName.getBackground() != new Color(0, 102, 255)) {
		    								lName.setBackground(new Color(240, 240, 240));
		    							}
		    							if(pName != null) {
											pName.setBackground(new Color(240, 240, 240));
										}
		    							
		    							lName.setForeground(new Color(0, 0, 0));
		    							lName = lblImgNome;
		    							pName = panel1;
		    						}
		    					}
		    				}
	    				}
	    			});
	    			panel1.addMouseListener(new MouseAdapter() {
	    				@Override
	    				public void mouseClicked(MouseEvent paramMouseEvent) {
	    					
	    					if(SwingUtilities.isRightMouseButton(paramMouseEvent)) {
	    						if(lName != null) {
		    						if(lName.getBackground() != new Color(0, 102, 255)) {
	    								lName.setBackground(new Color(240, 240, 240));
	    							}
		    						lName.setForeground(new Color(0, 0, 0));
	    						}
    							if(pName != null) {
									pName.setBackground(new Color(240, 240, 240));
								}
	    						
	    						panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
	    						
		    					lName = lblImgNome;
	    						pName = panel1;
	    						bool = true;
		    					
		    					popup.show(svg, paramMouseEvent.getX(), paramMouseEvent.getY());
		    				}else if(SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
	    					
		    					panel1.setBackground(new Color(0, 102, 255));
		    					lblImgNome.setForeground(new Color(255, 255, 255));
		    					
		    					if(!bool){
		    						lName = lblImgNome;
		    						pName = panel1;
		    						bool = true;
		    					}else {
		    						if(lName.getText().equals(lblImgNome.getText())) {
		    	    					JOptionPane.showMessageDialog(null,"Infelizmente o formato '.svg' não é suportado." ,"Aviso", JOptionPane.WARNING_MESSAGE);
		    						}else {
		    							if(lName.getBackground() != new Color(0, 102, 255)) {
		    								lName.setBackground(new Color(240, 240, 240));
		    							}
		    							if(pName != null) {
											pName.setBackground(new Color(240, 240, 240));
										}
		    							
		    							lName.setForeground(new Color(0, 0, 0));
		    							lName = lblImgNome;
		    							pName = panel1;
		    						}
		    					}
		    				}
	    				}
	    			});
	    			panel1.setBounds(0+width, 0+height, 220, 220);
	    			panel1.setPreferredSize(new Dimension(220, 220));
	    			panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
	    			
	    			panel1.add(svg);
	    			
	    			panel1.add(lblImgNome);
	    			
	    			GridBagConstraints gbc_panel1 = new GridBagConstraints();
					gbc_panel1.gridx = width;
					gbc_panel1.gridy = height;
	                panel.add(panel1, gbc_panel1);
	                width++;
	                if(width >= 5) { width = 0; width = 0; height++;}
    			}
    		}
		//}
		
		progressBar.setValue(iVer++);
		panel.revalidate();
		panel.repaint();
		btnFiltrar.setEnabled(true);
		btnDownloadAllImg.setEnabled(true);
    }
}
