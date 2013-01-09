package cn.cs2c.com;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 * 分析用户元素的xml
 * 
 * @author lanjun
 *
 */
public class AnalysisXMLString {

	public List xmlElements(String xmlDoc) {
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sab = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sab.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            // 获得属性值
            System.out.println("tasktypename:"+root.getAttributeValue("tasktypename"));
            System.out.println("perfrenceNum:"+root.getAttributeValue("perfrenceNum"));
            // 设置属性值
            root.setAttribute("perfrenceNum", "3");
            System.out.println("perfrenceNum:"+root.getAttributeValue("perfrenceNum"));
            
            /**
             * 得到根元素所有子元素的集合
             */
            List nodes = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
//            Namespace ns = root.getNamespace();
            Element element = null;
            for(int i = 0; i < nodes.size(); i++){
            	element = (Element) nodes.get(i);//循环依次得到子元素
                //无命名空间定义时
            	// 获得子元素的值
            	element.getChild("users_id").getText();
            	System.out.println(element.getChild("users_id").getText());
            	element.getChild("users_id").setText("123");
            	// 有命名空间定义时
//                System.out.println(et.getChild("users_id",ns).getText());
//                System.out.println(et.getChild("users_address",ns).getText());
            }
            /**
             * 取<row>下的子元素的名称和值
             */
            element = (Element) nodes.get(0);
            List childrenNodes = element.getChildren();
            for(int j = 0; j < childrenNodes.size(); j++){
                Element xet = (Element) childrenNodes.get(j);
                System.out.println(xet.getName());
                
//                if (xet.getName().equals("users_id")) {
//                	xet.setText("123");
//                }
                
                System.out.println(xet.getText());
            }
            XMLOutputter printDoc = new XMLOutputter("", false);
            OutputStream os = new ByteArrayOutputStream();
            printDoc.output(doc, os);
            System.out.println(os.toString());
            
            
        } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
    	AnalysisXMLString axs = new AnalysisXMLString();
    	// delete namespaces code
    	//"<?xml version=\"1.0\" encoding=\"gb2312\"?>" + 
    	//"<Result xmlns=\"http://www.fiorano.com/fesb/activity/DBQueryOnInput2/Out\">"+
        String xml = "<Result tasktypename=\"kind1\" perfrenceNum=\"2\">" + 
    	"<row resultcount=\"1\">"+
              "<users_id>1001     </users_id>"+
              "<users_name>wangwei   </users_name>"+
              "<users_group>80        </users_group>"+
              "<users_address>1001号   </users_address>"+
           "</row>"+
           "<row resultcount=\"1\">"+
              "<users_id>1002     </users_id>"+
              "<users_name>wangwei   </users_name>"+
              "<users_group>80        </users_group>"+
              "<users_address>1002号   </users_address>"+
           "</row>"+
        "</Result>";
        axs.xmlElements(xml);
    }

}
