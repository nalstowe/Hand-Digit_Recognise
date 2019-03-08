package backend;
import java.awt.image.*;
import java.util.*;
import java.io.*; 
//import view.*;

public class Algorithms {
	
	private ImageProcessor tempImageController = null;
	// import MNIST images
	String train_label_filename = "C:\\Users\\Niall\\Documents\\AP/train-labels.idx1-ubyte";
	String train_image_filename = "C:\\Users\\Niall\\Documents\\AP/train-images.idx3-ubyte";
	
	FileInputStream in_stream_labels = null;
	FileInputStream in_stream_images = null;
	
	DataInputStream dataStreamLables = null; 
	DataInputStream dataStreamImages = null; 
	
	private MNISTDataHolder[] currentDataItems = null;
	
	
	public ImageProcessor getImageController() {
		return this.tempImageController;
	}
	
	public void setImageController (ImageProcessor providedIC) {
		this.tempImageController = providedIC;
	}
	
	public MNISTDataHolder[] getDIArray() {
		return this.currentDataItems;
	}
	
	public void setDIArray(MNISTDataHolder[] providedDIArray) {
		this.currentDataItems = providedDIArray;
	}	
	
	public void loadItemArray() { 
		
		try {
			in_stream_labels = new FileInputStream(new File(train_label_filename));
			in_stream_images = new FileInputStream(new File(train_image_filename));
	
			dataStreamLables = new DataInputStream(in_stream_labels);
			dataStreamImages = new DataInputStream(in_stream_images); 
			
			int labelStartCode = dataStreamLables.readInt(); 
			int labelCount = dataStreamLables.readInt();
			int imageStartCode = dataStreamImages.readInt();
			int imageCount = dataStreamImages.readInt();
			int imageHeight = dataStreamImages.readInt(); 
			int imageWidth = dataStreamImages.readInt();
			
			currentDataItems = new MNISTDataHolder[labelCount]; 
			
			int imageSize = imageHeight * imageWidth; 
			byte[] labelData = new byte[labelCount];
			byte[] imageData = new byte[imageSize * imageCount];
			BufferedImage tempImage;
			
			dataStreamLables.read(labelData);
			dataStreamImages.read(imageData);
			
			for (int currentRecord = 0; currentRecord < labelCount; currentRecord++) {
				int currentLabel = labelData[currentRecord];				
				MNISTDataHolder newlabeledimage = new MNISTDataHolder();
				newlabeledimage.setMnistlabel(currentLabel);			
				tempImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);				
				int[][] imageDataArray = new int[imageWidth][imageHeight];
				for (int row = 0; row < imageHeight; row++) {
					for(int column = 0; column < imageWidth; column++) { 
						imageDataArray[column][row] = imageData[(currentRecord * imageSize)+((row*imageWidth) + column)] | 0xFF000000;
						tempImage.setRGB(column, row, imageDataArray[column][row]);
					}
					
				}
				
				newlabeledimage.setMnistbimg(tempImage); 
				currentDataItems[currentRecord] = newlabeledimage;
			}
			if (in_stream_labels != null) {
				in_stream_labels.close();
			}
			if (in_stream_images != null) {
				in_stream_images.close();
			}			
		} catch (FileNotFoundException fn) {
			fn.printStackTrace();	   
		} catch (IOException e)	{
		   
		   e.printStackTrace();
	   }
	
	}
	
	public void sortArray() {  
	    int n = currentDataItems.length;  
	    MNISTDataHolder tempDI = null;  
	    for(int i = 0; i < n; i++){  
	    	for(int j = 1; j < (n-i); j++){  
	    		if(currentDataItems[j - 1].getKnnVal() > currentDataItems[j].getKnnVal()){                       
                   tempDI = currentDataItems[j - 1];  
                   currentDataItems[j - 1] = currentDataItems[j];  
                   currentDataItems[j] = tempDI;  
	    		}                  
	    	}  
	    }	
	}
	
	public void computingEcludianDidst() throws NullPointerException {
		MNISTDataHolder[] processedMDIArray = new MNISTDataHolder[this.getDIArray().length];
		MNISTDataHolder[]  tempDataArray = this.getDIArray();
		BufferedImage ComparisonImage= tempImageController.getImage();
		if (ComparisonImage != null) {
			int currentImageWidth = ComparisonImage.getWidth();
			int currentImageHeight = ComparisonImage.getHeight();
			double mseValue = 0.0;
			double squareSum = 0.0; 
			for(int i =0; i < tempDataArray.length; i++) {
				MNISTDataHolder currentComparisonMNISTDataItem = tempDataArray[i]; 
				BufferedImage currentComparisonImage = currentComparisonMNISTDataItem.getMnistbimg();
				for (int y = 0; y < currentImageHeight; y++) {
					for(int x = 0; x < currentImageWidth; x++) {
					int imagetoComparePixels = ComparisonImage.getRGB(x, y);
				    int currentComparisonImagePixelValue =  currentComparisonImage.getRGB(x, y); 
				    squareSum += (Math.pow((imagetoComparePixels - currentComparisonImagePixelValue), 2));
				    
					}
				}
				mseValue = squareSum / (currentComparisonImage.getWidth() * currentComparisonImage.getHeight());
				currentComparisonMNISTDataItem.setKnnVal(mseValue);
				processedMDIArray[i] = currentComparisonMNISTDataItem;
				
			}
			this.setDIArray(processedMDIArray);			
		}
	}
	

	

}
