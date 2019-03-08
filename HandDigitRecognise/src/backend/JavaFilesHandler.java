package backend;

import java.io.*;

public abstract class JavaFilesHandler {
	
	File fp;
	String namesource;
	String dest_name;
	int size;
	int directory;
	String type;
	
	public JavaFilesHandler() {}
	public JavaFilesHandler(String namesource) {
		this.namesource = namesource;
		this.fp = new File(this.namesource);
	}
	
	public JavaFilesHandler(String source_file, String dest_file) {
		this.namesource = source_file;
		this.dest_name = dest_file;
		this.fp = new File(this.namesource);
	}
	
	public abstract void readFile() throws IOException;
	public abstract void writeFile(String file_name) throws IOException;
	
	/*
	 * Introduce getters and setters methods for the class variables
	 */
	
	public File getFp() {
		return fp;
	}
	public void setFp(File fp) {
		this.fp = fp;
	}
	public String getnamesource() {
		return namesource;
	}
	public void setnamesource(String namesource) {
		this.namesource = namesource;
	}
	public String getdest_name() {
		return dest_name;
	}
	public void setdest_name(String dest_name) {
		this.dest_name = dest_name;
	}
	public int getsize() {
		return size;
	}
	public void setsize(int size) {
		this.size = size;
	}
	public int getdirectory() {
		return directory;
	}
	public void setdirectory(int directory) {
		this.directory = directory;
	}
	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type = type;
	}
}
