import javax.xml.parsers.*;
import java.io.*;
import org.w3c.dom.*;
import java.util.*;

public class amfParser{
  public ArrayList <coordinate> coordinates = new ArrayList<coordinate>();
  public ArrayListco <triangle> triangles = new ArrayList<triangle>();
  
  public NodeList parseFile(String filename){
    
    try{
      
      File amfFile = new File(filename);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(amfFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("coordinates");
      
      for (int temp = 0; temp < nList.getLength(); temp++) {
        
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          
          Element eElement = (Element) nNode;
          double xVal = Double.parseDouble(getTagValue("x", eElement));
          double yVal = Double.parseDouble(getTagValue("y", eElement));
          double zVal = Double.parseDouble(getTagValue("z", eElement));
          
          coordinate newCoordinate = new coordinate(xVal, yVal, zVal);
          coordinates.add(newCoordinate);
          
        }
      }
      
      NodeList mList = doc.getElementsByTagName("triangle");
      
      for (int temp = 0; temp < mList.getLength(); temp++) {
        
        Node mNode = mList.item(temp);
        if (mNode.getNodeType() == Node.ELEMENT_NODE) {
          
          Element eElement = (Element) mNode;
          
          int v1 = Integer.parseInt(getTagValue("v1", eElement));
          int v2 = Integer.parseInt(getTagValue("v2", eElement));
          int v3 = Integer.parseInt(getTagValue("v3", eElement));
          
          coordinate c1 = coordinates.get(v1);
          coordinate c2 = coordinates.get(v2);
          coordinate c3 = coordinates.get(v3);
          
          triangle newTriangle = new triangle(c1, c2, c3);
          triangles.add(newTriangle);
          
        }
      }
      return nList;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }
  
  
  
  public String getTagValue(String sTag, Element eElement) {
    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
    
    Node nValue = (Node) nlList.item(0);
    
    return nValue.getNodeValue();
  }
}