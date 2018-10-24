package code.webcrawler;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	
	public Node(String url, List<Node> nodes) {
		super();
		this.url = url;
		this.nodes = nodes;
	}
	private String url;
	
	private List<Node> nodes;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Node> getChildNode() {
		
		if(nodes== null) {
			nodes =  new ArrayList<>();
		}
		return nodes;
	}
	public void setChildNode(List<Node> nodes) {
		this.nodes = nodes;
	}
	@Override
	public String toString() {
		return "Node [url=" + url + ", nodes=" + nodes.toString() + "]";
	}
	
	
}
