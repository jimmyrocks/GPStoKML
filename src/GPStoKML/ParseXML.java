/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GPStoKML;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jim
 * Code from: http://stuffnwotnot.blogspot.com/2009/05/simple-way-to-parse-xml-in-java-2.html
 */
public class ParseXML
{
	private static Element rootElement(String filename, String rootName)
	{
		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = new FileInputStream(filename);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(fileInputStream);
			Element rootElement = document.getDocumentElement();
			if(!rootElement.getNodeName().equals(rootName))
				throw new RuntimeException("Could not find root node: "+rootName);
			return rootElement;
		}
		catch(Exception exception)
		{
			throw new RuntimeException(exception);
		}
		finally
		{
			if(fileInputStream!=null)
			{
				try
				{
					fileInputStream.close();
				}
				catch(Exception exception)
				{
					throw new RuntimeException(exception);
				}
			}
		}
	}

	public ParseXML(String filename, String rootName)
	{
		this(rootElement(filename,rootName));
	}

	private ParseXML(Element element)
	{
		this.name = element.getNodeName();
                this.content = new StringBuffer(); //Clear out the String Buffer
		this.content.append(element.getTextContent());
		NamedNodeMap namedNodeMap = element.getAttributes();
		int n = namedNodeMap.getLength();
		for(int i=0;i<n;i++)
		{
			Node node = namedNodeMap.item(i);
			String name = node.getNodeName();
			addAttribute(name,node.getNodeValue());
		}
		NodeList nodes = element.getChildNodes();
		n = nodes.getLength();
	    for(int i=0;i<n;i++)
	    {
	    	Node node = nodes.item(i);
	    	int type = node.getNodeType();
	    	if(type==Node.ELEMENT_NODE) addChild(node.getNodeName(),new ParseXML((Element)node));
	    }
	}

	private void addAttribute(String name, String value)
	{
		nameAttributes.put(name,value);
	}

	private void addChild(String name, ParseXML child)
	{
		List<ParseXML> children = nameChildren.get(name);
		if(children==null)
		{
			children = new ArrayList<ParseXML>();
			nameChildren.put(name,children);
		}
		children.add(child);
	}

	public String name()
	{
		return name;
	}

	public StringBuffer content()
	{
		return content;
	}

	public ParseXML child(String name)
	{
		List<ParseXML> children = children(name);
		if(children.size()!=1) throw new RuntimeException("Could not find individual child node: "+name);
		return children.get(0);
	}

	public List<ParseXML> children(String name)
	{
		List<ParseXML> children = nameChildren.get(name);
		return children==null ? new ArrayList<ParseXML>() : children;
	}

	public String string(String name)
	{
		String value = nameAttributes.get(name);
		if(value==null) throw new RuntimeException("Could not find attribute: "+name+", in node: "+this.name);
		return value;
	}

	public int integer(String name)
	{
		return Integer.parseInt(string(name));
	}

	private String name;
	private StringBuffer content;
	private Map<String,String> nameAttributes = new HashMap<String,String>();
	private Map<String,List<ParseXML>> nameChildren = new HashMap<String,List<ParseXML>>();
}

