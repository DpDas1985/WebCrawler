package code.webCrawler;

  
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import code.webcrawler.WebCrawler;

public class WebCrawlerTest {  
	/*
	 * 
	 * Input: URL need to pass in doWebCrawl method "https://wiprodigital.com"
	 * 
	 * Output will be shown as {
	"url": "https://wiprodigital.com",
	"nodes": [
		{
			"url": "https://wiprodigital.com"
		},
		{
			"url": "https://wiprodigital.com/who-we-are",
			"nodes": [
				{
					"url": "https://wiprodigital.com"
				}]}]}
		
	 * Please refresh the webcrawler project to see the output Outout.json file in target folder.
	 */
	@Test
	public void testPrintHelloWorld() throws IOException {
		WebCrawler webCrawler = new WebCrawler();

		Assert.assertEquals(webCrawler.doWebCrawl("https://wiprodigital.com"), "success");

	}
	
}
