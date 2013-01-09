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
 * 修改用户元素的xml
 * 
 * @author lanjun
 *
 */
public class ModfiyXMLString {

	public static String xmlElements(String xmlDoc) {

		StringReader read = new StringReader(xmlDoc);
        InputSource source = new InputSource(read);
        SAXBuilder sab = new SAXBuilder();
        try {
            Document doc = sab.build(source);
            Element root = doc.getRootElement();
            List nodes = root.getChildren();

            Element element = null;
            for(int i = 0; i < nodes.size(); i++){
            	element = (Element) nodes.get(i);
            	if (element.getName().equals("VM_INFO")) {
            		List arguments = element.getChildren();
            		Element elt = null;
            		for (int j = 0; j < arguments.size(); j++) {
            			elt = (Element) arguments.get(j);
            			if (elt.getName().equals("VMNUMS")) {
            				String vmNums = elt.getText();
            				if (vmNums!= null && !vmNums.equals("0")) {
            					elt.setText("<![CDATA[0]]>");
            				}
            			}
            		}
            		break;
            	}
            }
            
            XMLOutputter printDoc = new XMLOutputter("", false);
            OutputStream os = new ByteArrayOutputStream();
            printDoc.output(doc, os);
//            System.out.println(os.toString().contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
            
            String s = os.toString().replaceAll("\\<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?\\>", "").replaceAll("\r","").replaceAll("\n", "");
            return s;
        } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
    }
	
    public static void main(String[] args){
        String xml = "<TEMPLATE><USERINFO><DEPARTMENT><![CDATA[0]]></DEPARTMENT><EMAIL><![CDATA[0]]></EMAIL><JOB><![CDATA[0]]></JOB><REALNAME><![CDATA[0]]></REALNAME></USERINFO><VM_INFO><CPU><![CDATA[-22]]></CPU><DISK><![CDATA[-105]]></DISK><MEM><![CDATA[-11264]]></MEM><VCPU><![CDATA[-22]]></VCPU><VMNUMS><![CDATA[-11]]></VMNUMS></VM_INFO></TEMPLATE>";
//        		"<TEMPLATE><QUOTA><CPU><![CDATA[0]]></CPU><DISK><![CDATA[0]]></DISK><MEM><![CDATA[0]]></MEM><VCPU><![CDATA[0]]></VCPU><VMNUMS><![CDATA[0]]></VMNUMS></QUOTA><QUOTA_ENABLED><![CDATA[0]]></QUOTA_ENABLED><SNAPSHOT_PER_VM><![CDATA[5]]></SNAPSHOT_PER_VM><USERINFO><DEPARTMENT><![CDATA[null]]></DEPARTMENT><EMAIL><![CDATA[qwe@qwe.qwe]]></EMAIL><JOB><![CDATA[null]]></JOB><REALNAME><![CDATA[qwer1234]]></REALNAME></USERINFO><VM_INFO><CPU><![CDATA[0]]></CPU><DISK><![CDATA[0]]></DISK><MEM><![CDATA[0]]></MEM><VCPU><![CDATA[0]]></VCPU><VMNUMS><![CDATA[0]]></VMNUMS></VM_INFO></TEMPLATE>";
//        		"<TEMPLATE>" +
//        		"<QUOTA>" +
//        		"<CPU><![CDATA[0]]></CPU>" +
//        		"<DISK><![CDATA[0]]></DISK>" +
//        		"<MEM><![CDATA[0]]></MEM>" +
//        		"<VCPU><![CDATA[0]]></VCPU>" +
//        		"<VMNUMS><![CDATA[0]]></VMNUMS>" +
//        		"</QUOTA>" +
//        		"<QUOTA_ENABLED><![CDATA[0]]></QUOTA_ENABLED>" +
//        		"<SNAPSHOT_PER_VM><![CDATA[5]]></SNAPSHOT_PER_VM>" +
//        		"<USERINFO>" +
//        		"<DEPARTMENT><![CDATA[5]]></DEPARTMENT>" +
//        		"<EMAIL><![CDATA[qwe@qwe.qwe]]></EMAIL>" +
//        		"<JOB><![CDATA[5]]></JOB>" +
//        		"<REALNAME><![CDATA[qwer1234]]></REALNAME></USERINFO>" +
//        		"<VM_INFO>" +
//        		"<CPU><![CDATA[0]]></CPU>" +
//        		"<DISK><![CDATA[0]]></DISK>" +
//        		"<MEM><![CDATA[0]]></MEM>" +
//        		"<VCPU><![CDATA[0]]></VCPU>" +
//        		"<VMNUMS><![CDATA[4]]></VMNUMS>" +
//        		"</VM_INFO>" +
//        		"</TEMPLATE>";
        String s = xmlElements(xml);
        System.out.print(s);
        
    }

}
