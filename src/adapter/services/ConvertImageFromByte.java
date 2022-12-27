package adapter.services;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ImageIcon;

public class ConvertImageFromByte {
			
	public static Boolean imageLargerSizeLimit(String url) throws Exception {	
		boolean limitImage = false;
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			        return null;
			      }

			      public void checkClientTrusted(X509Certificate[] certs, String authType) {
			      }

			      public void checkServerTrusted(X509Certificate[] certs, String authType) {
			      }
			} };
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());			
			URL newurl = new URL(url);
			Image image = ImageIO.read(newurl.openStream());			
			byte[] imageByte = imageToByte(image);	
			limitImage = (imageByte.length >= 2000000) ? true : false;	
		} catch (Exception e) {
			return false;
		}	
		return limitImage;
	}
	
	public static Boolean imageLargerSizeLimit(byte[] imageByte) throws Exception {
		boolean limitImage = false;
		Image image = convertByteToImage(imageByte);
		byte[] imageByteJpg = imageToByte(image);

		try {
			limitImage = (imageByteJpg.length >= 2000000) ? true : false;
		} catch (Exception e) {
			throw new Exception("Erro ao processar imagem.");
		}
		return limitImage;
	}
	
	public static byte[] imageToByte(Image image) {	
		Image imageJpeg = convertImageToJPG(image);
		Graphics bg = imageJpeg.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();
		
		ByteArrayOutputStream buff = new ByteArrayOutputStream();		
	    try {  
	    	ImageIO.write(convertImageToJPG(image), "JPG", buff);  
	    } catch (IOException e) {  
	    	e.printStackTrace();  
	    }  
	    return buff.toByteArray();
	}

	public static Boolean checkUrlHttps(String url) {		
		boolean urlHttps = (url.substring(0, 5).equals("https")) ? true : false;
		return urlHttps;
	}

	public static BufferedImage convertImageToJPG(Image image) {
		BufferedImage originalImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);	
		
		BufferedImage newBufferedImage = new BufferedImage(
				originalImage.getWidth(null),
				originalImage.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		 newBufferedImage.createGraphics()
         .drawImage(image,
                 0,
                 0,
                 Color.WHITE,
                 null);
	 
		 return newBufferedImage;
	}
	
	public static Image convertByteToImage(byte[] imageByte) {
		return new ImageIcon(imageByte).getImage();
	}
}

