package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;

import backend.*;
public class UI {

   //set the JFrame width/height
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 500;

   //Declare all variables
    private DrawArea drawArea;
    
    private JFrame mainFrame;
    
    private JPanel mainPanel;
    private JPanel drawDigit_predictionPanel;  
    private JPanel outputPanel;
    
    private JFileChooser fileChooser;
    
    private final Font sansSerifBold = new Font("SansSerif", Font.BOLD, 10);

    private JButton openBtn;
	private JTextField fileNameTxt;
	private JLabel fileNameLbl;
	private JLabel image;
	private String selectFile = "";
	private JProgressBar proBar;

	//set up exceptions
    public UI() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
    }

    //Initialise the GUI class
    public void initUI() {
        
    	mainFrame = createMainFrame();  
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        addTopPanel();
        drawDigit_predictionPanel = new JPanel(new GridLayout());
        addActionPanel();
        addDrawAreaAndPredictionPanel();
        mainPanel.add(drawDigit_predictionPanel, BorderLayout.CENTER);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

    }

    private void addActionPanel() {
    	//create button to recognise digit
        JButton recognize = new JButton("Compare");
        //add actionlistener to recognize compare button
        recognize.addActionListener(e -> {
//        	 
            });
        
        //create clear button
        JButton clear = new JButton("Clear");
        //actionlistener to repaint drawarea white 
        clear.addActionListener(e -> {
                drawArea.setImage(null);
                drawArea.repaint();
                drawDigit_predictionPanel.updateUI();
                image.setIcon( null );    
		        fileNameTxt.setText("");
		        proBar.setString("0%"); 
            });
        
            
        JPanel layer1Panel = new JPanel();
        layer1Panel.setLayout(new BoxLayout(layer1Panel, BoxLayout.Y_AXIS));
        layer1Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.BLACK));
        
        JPanel actionPanel = new JPanel(new GridLayout(4, 1));
        
        JPanel ImagePanel = new JPanel();
        ImagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Selected Image",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.BLACK));
        image = new JLabel(" ");
        ImagePanel.add(image);
             
        actionPanel.add(recognize);
        actionPanel.add(clear);
        actionPanel.add(ImagePanel);
        
        layer1Panel.add(actionPanel);
        layer1Panel.add(ImagePanel);
        
        mainPanel.add(layer1Panel, BorderLayout.WEST);
    }

    private void addDrawAreaAndPredictionPanel() {
        drawArea = new DrawArea();
        
        drawDigit_predictionPanel.add(drawArea);

    }
    private void addTopPanel() {
  
        JPanel fileSelectPanel = new JPanel();
        
        fileNameLbl = new JLabel ("File name: ");
        fileNameTxt = new JTextField(50);
        fileNameTxt.setEnabled(false);        
        openBtn = new JButton("Open File");
        
        proBar = new JProgressBar(0, 100);        
        proBar.setValue(0);
        proBar.setStringPainted(true);
        proBar.setIndeterminate(true);
      
		
        // Add components to the JPanel fileSelectPanel
        fileSelectPanel.add(fileNameLbl);
        fileSelectPanel.add(fileNameTxt);
        fileSelectPanel.add(openBtn);
        fileSelectPanel.add(proBar);
        
		openBtn.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = showFileChooserDialog();
				
				if(selectedFile != null) {
					fileNameTxt.setText(selectedFile.getAbsolutePath());
					selectFile = selectedFile.getAbsolutePath();
					try {
	    				
	    				if (selectFile != null)  {
	    					
	    					ImageFilesHandler currentfileHandler = new ImageFilesHandler(selectFile); 
	    			        currentfileHandler.readFile(); 
	    			        ImageProcessor ICObject = new ImageProcessor();
	    			        ICObject.setImage(currentfileHandler.getImage());
	    			        ICObject.convertRGBToGrayscale(ICObject.getImage());
	    			        ICObject.resizeGreyScaleImage(28, 28);
	    			        
	    			        Algorithms DILoader = new Algorithms();
	    			        DILoader.setImageController(ICObject);
	    			        DILoader.loadItemArray();		     
	    			        DILoader.computingEcludianDidst(); 
	    			        proBar.setString("100%"); 
	    	                drawArea.setImage(null);
	    	                drawArea.repaint();
	    	                drawDigit_predictionPanel.updateUI();
	    	                	    	                			             			     
	    			        outputPanel.removeAll();             	               
	    	                outputPanel.updateUI();
	    	                
	    	            
	    					
	    				}
	    			} catch (Exception ex) {
	    				ex.printStackTrace();
	    			}

				}
				else
					fileNameTxt.setText("No file selected");	
			}
		});	
        

        mainPanel.add(fileSelectPanel, BorderLayout.SOUTH);
      
    }
    
    //file chooser function
	private File showFileChooserDialog() {
		//set up file chooser
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new 
				File(System.getProperty("user.home")));
	    int status = fileChooser.showOpenDialog(this.mainFrame);
	    File selected_file = null;
	    if (status == JFileChooser.APPROVE_OPTION) {
	        selected_file = fileChooser.getSelectedFile();
	        try {
	        	//get image of selected file and add it to JLabel(image) 
                image.setIcon(new ImageIcon(ImageIO.read(selected_file).getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            } catch (IOException e) {
                e.printStackTrace();
            }     
	    }
	    return selected_file;
	}
  
    

    public static BufferedImage toBufferedImage(Image img) {
       
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

      
        return bimage;
    }

    private static double[] transformImageToOneDimensionalVector(BufferedImage img) {
        double[] imageGray = new double[28 * 28];
        int w = img.getWidth();
        int h = img.getHeight();
        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color color = new Color(img.getRGB(j, i), true);
                int red = (color.getRed());
                int green = (color.getGreen());
                int blue = (color.getBlue());
                double v = 255 - (red + green + blue) / 3d;
                imageGray[index] = v;
                index++;
            }
        }
        return imageGray;
    }

    
    //method to create and display the main Frame
    private JFrame createMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Digit Recognizer");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //give mainframe height and width 
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        
        mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            });

        return mainFrame;
    }


}