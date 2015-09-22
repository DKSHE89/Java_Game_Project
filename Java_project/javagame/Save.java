package javagame;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;

import javax.imageio.ImageIO;

import java.sql.Connection;  
import java.sql.PreparedStatement;

public class Save{
	
	Play playObj;
	BufferedImage buffIm1;
	BufferedImage buffIm2;
	BufferedImage buffIm3;
	BufferedImage buffIm4;
	BufferedImage buffIm5;
	ByteArrayOutputStream byteArray1;
	ByteArrayOutputStream byteArray2;
	ByteArrayOutputStream byteArray3;
	ByteArrayOutputStream byteArray4;
	ByteArrayOutputStream byteArray5;
	byte[] jpgByteArray1;
	byte[] jpgByteArray2;
	byte[] jpgByteArray3;
	byte[] jpgByteArray4;
	byte[] jpgByteArray5;
	StringBuilder sb;
	Connection conn;
	PreparedStatement ps;
	static float locX;
	static float locY;
	static String strX;
	static String strY;

		public Save(Play obj) throws IOException {
		getPlay(obj);
		getPosition(obj);
		writeToFile();
		readImage();
		saveToDatabase();
		
	}
		
		public  void getPlay(Play obj){
			playObj=obj;
		}

		public  void getPosition(Play obj2){
			getPlay(obj2);
			locX=playObj.getPositionX();
			locY=playObj.getPositionY();                 
		
		}
	   
	   public  void writeToFile( ) throws IOException{
		  
		   FileWriter fw= new FileWriter("save.sav");
		   BufferedWriter bfw=new BufferedWriter(fw);
		   strX=String.valueOf(locX);
		   strY=String.valueOf(locY);
		   bfw.write(strX+':'+strY);
		   bfw.close();
		   System.out.println("Your coordinates are saved to save.sav");
	   }
	   
	   public void readImage()throws IOException{
		   
		   buffIm1=ImageIO.read(new File("res/buckysBack.png"));
		   buffIm2=ImageIO.read(new File("res/buckysFront.png"));
		   buffIm3=ImageIO.read(new File("res/buckysLeft.png"));
		   buffIm4=ImageIO.read(new File("res/buckysRight.png"));
		   buffIm5=ImageIO.read(new File("res/world.png"));
		   
		   byteArray1=new ByteArrayOutputStream();
		   byteArray2=new ByteArrayOutputStream();
		   byteArray3=new ByteArrayOutputStream();
		   byteArray4=new ByteArrayOutputStream();
		   byteArray5=new ByteArrayOutputStream();
		   ImageIO.write(buffIm1, "png", byteArray1);
		   ImageIO.write(buffIm2, "png", byteArray2);
		   ImageIO.write(buffIm3, "png", byteArray3);
		   ImageIO.write(buffIm4, "png", byteArray4);
		   ImageIO.write(buffIm5, "png", byteArray5);
		   jpgByteArray1=byteArray1.toByteArray();
		   jpgByteArray2=byteArray2.toByteArray();
		   jpgByteArray3=byteArray3.toByteArray();
		   jpgByteArray4=byteArray4.toByteArray();
		   jpgByteArray5=byteArray5.toByteArray();
		   
	   }
	   public void saveToDatabase() {  
		   
		   try{
			   		Class.forName("com.mysql.jdbc.Driver");
		            Connection conn= DriverManager.getConnection(  
		                    "jdbc:mysql://localhost:3306/mysql","root","");  
		  
		            ps = conn.prepareStatement("insert into java_game values(?,?,?,?,?,?,?)");  
		            ps.setFloat(1, locX);
		            ps.setFloat(2,locY);
		            ps.setBytes(3, jpgByteArray1);
		            ps.setBytes(4, jpgByteArray2);
		            ps.setBytes(5, jpgByteArray3);
		            ps.setBytes(6, jpgByteArray4);
		            ps.setBytes(7, jpgByteArray5);
		            ps.executeUpdate();
		            ps.close();
		            conn.close(); 
		            
		   }catch (Exception e)
	       {
	            e.printStackTrace();
	        }
		}  
	   
	   
	   
	   
	
}
