package com.xiong.wx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.xiong.wx.msgentity.resp.ImageMessageResp;
import com.xiong.wx.msgentity.resp.MusicMessageResp;
import com.xiong.wx.msgentity.resp.NewsMessageResp;
import com.xiong.wx.msgentity.resp.TextMessageResp;
import com.xiong.wx.msgentity.resp.VideoMessageResp;
import com.xiong.wx.msgentity.resp.VoiceMessageResp;

/**
 * 消息工具处理类
 * @author daxiong
 * @date 2015-6-26
 *
 */
public class MessageUtil {
	
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 事件类型：scan(关注用户扫描带参数二维码）
	 */
	public static final String EVENT_TYPE_SCAN = "scan";
	
	/**
	 * 事件类型：LOCATION(上报地理位置)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 解析微信发来的请求
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
	@SuppressWarnings("unchecked")
	public static Map<String,String> parseXml(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		
		//从request中取得输入流
		InputStream inputStream = request.getInputStream();
		
		//读入输入流
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputStream);
		//得到xml根元素
		Element rootElement = document.getRootElement();
		//得到所有子节点
		List<Element> elements = rootElement.elements();
		
		//遍历所有节点
		for(Element e : elements){
			map.put(e.getName(), e.getText());
		}
		//释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}
	
	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out) {
				//对所有的xml节点的转换都增加 CDATA 标记
				boolean cdata =true;
				
				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz){
					super.startNode(name,clazz);
				}
				
				protected void writeText(QuickWriter writer ,String text){
					if(cdata){
						writer.write("<![CDATA[");
					    writer.write(text);
					    writer.write("]]>");
					}else{
					    writer.write(text);	
					}
				}
			};
		}
		
	});
	
	/**
	 * 文本消息对象转换成xml 
	 * @param textMessage 文本消息对象 
	 * @return xml
	 */
	public static String textMessageToXml(TextMessageResp textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/**
	 * 图片消息对象转换成xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessageResp imageMessage){
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * 音频消息对象转化xml
	 * @param voiceMessage
	 * @return
	 */
	public static String voiceMessageToXml(VoiceMessageResp voiceMessage){
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}
	
	/**
	 * 视频消息对象转化xml
	 * @param videoMessage
	 * @return
	 */
	public static String videoMessageToXml(VideoMessageResp videoMessage){
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}
	/**
	 * 音乐消息对象转换成xml
	 * @param musicMessage
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessageResp musicMessage){
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * 图文消息对象转换成xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessageResp newsMessage){
		xstream.alias("xml", newsMessage.getClass());
		return xstream.toXML(newsMessage);
	}
	
}
