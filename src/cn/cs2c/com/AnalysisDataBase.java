package cn.cs2c.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 处理数据备份文件
 * 
 * @author lanjun
 * @since 2013.01.07
 */
public class AnalysisDataBase {

	/**
	 * 将fileName1文件修改成fileName2文件
	 * 
	 * @param fileName1
	 * @param fileName2
	 */
	public static void processDataBaseFile(String fileName1, String fileName2) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			File file = new File(fileName1);
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName2))));
			while((text = reader.readLine())!=null) {
				if (text.contains("VALUES")) {
					Map<Integer, String> map = getUserMap(text);
					modifyUserMap(map);
					setUserMap(text, map);
				}
				writer.write(text + "\n");
			}

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		       if (writer != null) {
		           writer.close();
		       }
		       if (reader != null) {
		    	   reader.close();
		       }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		 }
		
	}
	
	/**
	 * 从text中获得用户元素放入map<id,xml>中
	 * 
	 * @param text
	 * @return
	 */
	public static Map<Integer, String> getUserMap(String text) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		int index = text.indexOf('(');
		text = text.substring(index+1, text.length()-2).replaceAll("\\),\\(", ";");
		String[] user = text.split(";");                                                                       
		for(int i = 0; i < user.length; i++) {
			String[] arguments = user[i].split(",");
			Integer userId = Integer.valueOf(arguments[0]);
			String userXml = arguments[4].replaceAll("\\'", "");
			map.put(userId, userXml);
		}
		
		return map;
	}
	
	/**
	 * 将用户map放入text中
	 * 
	 * @param text
	 * @param map
	 */
	public static void setUserMap(String text, Map<Integer, String> map) {
		StringBuilder newText = new StringBuilder();
		int index = text.indexOf('(');
		newText.append(text.substring(0, index));
		text = text.substring(index+1, text.length()-2).replaceAll("\\),\\(", ";");
		String[] user = text.split(";");                                                                       
		for(int i = 0; i < user.length; i++) {
			String[] arguments = user[i].split(",");
			Integer userId = Integer.valueOf(arguments[0]);
			String userXml = map.get(userId);
			
			newText.append("(");
			for (int j = 0; j < arguments.length-1; j++) {
				newText.append(arguments[j] + ",");
			}
			newText.append("'" + userXml + "')");
			if (i < user.length - 1) {
				newText.append(',');
			} else {
				newText.append(';');
			}
		}
		System.out.print(newText);
	}
	
	/**
	 * 修改map元素中相应的值
	 * 
	 * @param map
	 */
	public static void modifyUserMap(Map<Integer, String> map) {
		Iterator<Map.Entry<Integer, String>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
		    Map.Entry<Integer, String> entry = entries.next();
//		    Integer key = entry.getKey();
		    String value = entry.getValue();
		    String newValue = ModfiyXMLString.xmlElements(value);
		    entry.setValue(newValue);
		}
	}
	
	public static void main(String[] args) {
		
		processDataBaseFile("/home/lanjun/copy/user_pool", "/home/lanjun/copy/user_pool2");
		
	}

}
