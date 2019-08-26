package com.xiong.wx.utils;


import java.io.InputStream;
import java.util.Properties;

/**璇诲彇config.properties鏂囦欢
 * 
 * @author chenweixian
 *
 */
public class ConfigPropertiesUtil {
	

	private String propertiesFileName = "/application.properties";
	private static ConfigPropertiesUtil configPropertiesUtil = null;
	private static Properties prop = null;

	private ConfigPropertiesUtil() {
		loadProperties();
	}
	/**获取实例*/
	public static ConfigPropertiesUtil getInstance(){
		if (configPropertiesUtil == null){
			configPropertiesUtil = new ConfigPropertiesUtil();
		}
		return configPropertiesUtil;
	}

	public String getProp(String key) {
		if (prop == null){
			loadProperties();
		}
		return prop.getProperty(key);
	}
	/**加载信息
	 * 
	 */
	private void loadProperties() {
		if (configPropertiesUtil == null) {
			try {
				prop = new Properties();
				InputStream in = this.getClass().getResourceAsStream(propertiesFileName);
				prop.load(in);
			} catch (Exception e) {
				System.err.println("读取"+ propertiesFileName +"异常" + e.toString());
			}
		}
	}
}
