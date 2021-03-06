package code.webcrawler;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

import code.util.Util;
import code.webcrawler.impl.WebCrawlerHandler;
import code.webcrawler.intf.WebCrawlerHandlerInterface;

/**
 * @author DU394084
 *
 * WebCrawler class takes URL input as String  and will Crawl each hyperlinks.
 * It will now crawl inside other domain link but it will capture the presence of other domain link also
 * This method invokes getAllLinksOfCurrentURL method of WebCrawlerHandler
 */
public class WebCrawler{
   
	private WebCrawlerHandlerInterface handler;
	
	/**
     * @param baseURL
     * @param isOtherDomainApplicable
     * @return
     * @throws IOException
     */
    public String doWebCrawl(String baseURL,boolean isOtherDomainApplicable) throws IOException{ 																																																																															
    	
    	System.out.println(System.currentTimeMillis()/1000);
    	
    	//Initialize the root node for web-crawling
    	Node rootNode = new Node(baseURL,null);
    	
    	//Inject WebCrawlerHandler to handle the request
    	setHandler(new WebCrawlerHandler());
    	
    	Node node =handler.getAllLinksOfCurrentURL(rootNode,Util.getDomain(baseURL),isOtherDomainApplicable);
    	writeFinalOutputIntoFile(node);
    	System.out.println("current time::"+System.currentTimeMillis()/1000);
    	return "success";
    }
  
	/**
	 * @param node
	 * @throws IOException
	 * this method consumes node as input and converts the object into JSON message
	 * and write the data into file Outout.json.
	 */
	public void writeFinalOutputIntoFile(Node node) throws IOException {
		
		Gson gson = new Gson();
		String jsonInString = gson.toJson(node);
		Path path = Paths.get("./Outout.json");
		 
		//Use try-with-resource to get auto-closeable writer instance
		try (BufferedWriter writer = Files.newBufferedWriter(path))
		{
		    writer.write(jsonInString);
		}
		
	}
	
	

	public WebCrawlerHandlerInterface getHandler() {
		return handler;
	}

	public void setHandler(WebCrawlerHandlerInterface handler) {
		this.handler = handler;
	}
}