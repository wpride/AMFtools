import javax.xml.parsers.*;
import java.io.*;
import org.w3c.dom.*;
import java.util.*;

public class amfParserTester{
  public ArrayList <coordinate> coordinates = new ArrayList<coordinate>();
  public ArrayList <triangle> triangles = new ArrayList<triangle>();
  
  public void parseFile(String filename){
    
    try{
      
      File amfFile = new File(filename);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(amfFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("vertex");
      
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node oNode = nList.item(temp);
        
        if (oNode.getNodeType() == Node.ELEMENT_NODE) {
          
          NodeList oList = oNode.getChildNodes();
          
          int oLength = oList.getLength();
          
          boolean NORM_BOOL = false;
          boolean COORD_BOOL = false;
          boolean COLOR_BOOL = false;
          
          float xVal=0,yVal=0,zVal=0;
          float nxVal=0, nyVal=0, nzVal=0;
          float rVal=0, gVal=0, bVal=0, aVal=0;
          
          for(int i=0;i<oLength;i++){
            Node pNode = oList.item(i);
            String pName = pNode.getNodeName();
            //Element eElement = (Element) pNode;
            if(pName == "normal"){
              Element eElement = (Element) pNode;
              nxVal = Float.parseFloat(getTagValue("nx", eElement));
              nyVal = Float.parseFloat(getTagValue("ny", eElement));
              nzVal = Float.parseFloat(getTagValue("nz", eElement));
              NORM_BOOL = true;
            }
            if(pName == "coordinates"){
              Element eElement = (Element) pNode;
              xVal = Float.parseFloat(getTagValue("x", eElement));
              yVal = Float.parseFloat(getTagValue("y", eElement));
              zVal = Float.parseFloat(getTagValue("z", eElement));
              COORD_BOOL = true;
            }
            if(pName == "color"){
              Element eElement = (Element) pNode;
              rVal = Float.parseFloat(getTagValue("r", eElement));
              gVal = Float.parseFloat(getTagValue("g", eElement));
              bVal = Float.parseFloat(getTagValue("b", eElement));
              aVal = Float.parseFloat(getTagValue("a", eElement));
              COLOR_BOOL = true;
            }
            
          }
          
          if(COORD_BOOL){
            coordinate newCoordinate = new coordinate(xVal, yVal, zVal);
            if(NORM_BOOL){
              newCoordinate.setNormals(nxVal,nyVal,nzVal);
              
            }
            if(COLOR_BOOL){
              newCoordinate.setColors(rVal,gVal,bVal,aVal);
            }
            coordinates.add(newCoordinate);
          }
          
        }
      }
      
      /*
       * 
       Node nNode = nList.item(temp);
       if (nNode.getNodeType() == Node.ELEMENT_NODE) {
       
       Element eElement = (Element) nNode;
       double xVal = Double.parseDouble(getTagValue("nx", eElement));
       double yVal = Double.parseDouble(getTagValue("ny", eElement));
       double zVal = Double.parseDouble(getTagValue("nz", eElement));
       
       coordinate newCoordinate = new coordinate(xVal, yVal, zVal);
       coordinates.add(newCoordinate);
       
       }
       }
       */
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
          
          triangle newTriangle = new triangle(c1, c2, c3, v1, v2, v3);
          triangles.add(newTriangle);
          
        }
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
  
  
  
  public String getTagValue(String sTag, Element eElement) {
    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
    
    Node nValue = (Node) nlList.item(0);
    
    return nValue.getNodeValue();
  }
}