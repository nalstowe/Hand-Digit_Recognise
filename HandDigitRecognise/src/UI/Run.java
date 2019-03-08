package UI;
import javax.swing.*;

import backend.ReadMnist;

import java.io.IOException;
import java.util.concurrent.Executors;

//main class to run UI from
public class Run {

    private static JFrame mainFrame = new JFrame();

    public static void main(String[] args) throws Exception {

    	ReadMnist filereader = new ReadMnist();
    	
        UI ui = new UI();
        Executors.newCachedThreadPool().submit(()->{
                try {
                	//call class ReadMnist and UI
                	filereader.Read();
                    ui.initUI();
                } catch (IOException e) {
					
					e.printStackTrace();
				} finally {
                    mainFrame.dispose();
                }
         });
    }
}