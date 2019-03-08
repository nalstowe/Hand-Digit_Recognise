package backend;
import java.awt.image.*;
import java.io.*;

public class MNISTDataHolder {
	
	private int Mnistlabel; 
	private BufferedImage Mnistbimg;
	private double KnnVal; 
	
	
	public MNISTDataHolder() {	}
	 
	public int getMnistlabel() {
		return this.Mnistlabel;
	}
	
	public void setMnistlabel(int suppliedLbl) {
		this.Mnistlabel = suppliedLbl;
	}
	
	public BufferedImage getMnistbimg()  {
		return this.Mnistbimg;
	}

	public void setMnistbimg(BufferedImage tempimage) {
		this.Mnistbimg = tempimage;
	}
	
	public double getKnnVal() {
		return KnnVal;
	} 
	
	public void setKnnVal(double tempValue) {
		this.KnnVal = tempValue;
	}
}