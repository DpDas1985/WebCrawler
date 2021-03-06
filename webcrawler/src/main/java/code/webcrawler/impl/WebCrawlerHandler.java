package code.webcrawler.impl;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.util.Util;
import code.webcrawler.Node;
import code.webcrawler.intf.WebCrawlerHandlerInterface;

/*
 * WebCrawlerHandler class takes URL input as String  and will Crawl each hyperlinks.
 * This class recursively called getAllLinksOfCurrentURL method for each node and will fetch all nodes.
 * links are identified by href nodes in HTML using  Jsoup parser.
 */
public class WebCrawlerHandler implements WebCrawlerHandlerInterface {

	private final String aHrefConstant = "a[href]";
	private final String aHrefAttrConstant = "abs:href";
	private final String aHash = "#";
	private HashSet<String> links;
	private final Logger slf4jLogger = LoggerFactory.getLogger(WebCrawlerHandler.class);

	public WebCrawlerHandler() {
		links = new HashSet<>();
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see code.webcrawler.intf.WebCrawlerHandlerInterface#getAllLinksOfCurrentURL(code.webcrawler.Node, java.lang.String, boolean)
	 * input 1:Node
	 * input 2:URL
	 * input 3: if other domain are applicable then true or else false
	 */
	public Node getAllLinksOfCurrentURL(Node node, String baseDomain,boolean isOtherDomainApplicable) {

		if (!node.getUrl().trim().isEmpty() && !links.contains(node.getUrl())
				&& (Util.getDomain(node.getUrl()).equalsIgnoreCase(baseDomain) || isOtherDomainApplicable)) {

			try {
				links.add(node.getUrl().trim());
				Document document = Jsoup.connect(node.getUrl()).get();
				Elements linksOnPage = document.select(aHrefConstant);

				for (Element page : linksOnPage) {

					String childURL = null;
					if (page.attr(aHrefAttrConstant).contains(aHash) && (page.attr(aHrefAttrConstant)
							.lastIndexOf(aHash) == page.attr(aHrefAttrConstant).length() - 1)) {
						childURL = page.attr(aHrefAttrConstant).substring(0,
								page.attr(aHrefAttrConstant).lastIndexOf(aHash));
					} else {
						childURL = page.attr(aHrefAttrConstant);
					}

					// System.out.println(childURL);
					Node childNode = new Node(childURL, null);

					if (Util.getDomain(node.getUrl()).equalsIgnoreCase(baseDomain) || isOtherDomainApplicable) {
						node.getChildNode().add(getAllLinksOfCurrentURL(childNode, baseDomain,isOtherDomainApplicable));
					} else {
						node.getChildNode().add(childNode);
					}

				}
			} catch (IOException e) {
				slf4jLogger.error("Invalid URL [" + node.getUrl() + "]" + e.getMessage());
			}

		}
		return node;
	}

}
