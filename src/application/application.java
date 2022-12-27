package application;

import adapter.services.ConvertImageFromByte;

public class application {
	
	public static void main(String[] args) throws Exception {
		System.out.println(ConvertImageFromByte.imageLargerSizeLimit("https://www.lojavirtual.com.br/wp-content/uploads/2017/11/banner_black_friday_1.png"));
	}
	
}
