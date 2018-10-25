package code.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	 /**
	 * @param url
	 * @return String
	 * this will take url as input and returns domain as string
	 * 
	 */
	public static String getDomain(String url) {
		 	String domain="";
			Pattern pattern = Pattern.compile("(https?://)([^:^/]*)(:\\d*)?(.*)?");
			Matcher matcher = pattern.matcher(url);
		 try {
		

			matcher.find();
			//return domain name
			domain = matcher.group(2);
		
		 }catch(Exception e) {
			// System.out.println("Invalid URL"+url);
			 
		 }
			return domain;
}
}

