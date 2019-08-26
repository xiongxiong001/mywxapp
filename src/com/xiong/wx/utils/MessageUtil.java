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
 * ��Ϣ���ߴ�����
 * @author daxiong
 * @date 2015-6-26
 *
 */
public class MessageUtil {
	
	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * ������Ϣ���ͣ�ͼ��
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�ͼƬ
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * ������Ϣ���ͣ�����λ��
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * ������Ϣ���ͣ���Ƶ
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * �¼����ͣ�subscribe(����)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * �¼����ͣ�unsubscribe(ȡ������)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * �¼����ͣ�scan(��ע�û�ɨ���������ά�룩
	 */
	public static final String EVENT_TYPE_SCAN = "scan";
	
	/**
	 * �¼����ͣ�LOCATION(�ϱ�����λ��)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	/**
	 * �¼����ͣ�CLICK(�Զ���˵�����¼�)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * ����΢�ŷ���������
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
	@SuppressWarnings("unchecked")
	public static Map<String,String> parseXml(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		
		//��request��ȡ��������
		InputStream inputStream = request.getInputStream();
		
		//����������
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputStream);
		//�õ�xml��Ԫ��
		Element rootElement = document.getRootElement();
		//�õ������ӽڵ�
		List<Element> elements = rootElement.elements();
		
		//�������нڵ�
		for(Element e : elements){
			map.put(e.getName(), e.getText());
		}
		//�ͷ���Դ
		inputStream.close();
		inputStream = null;
		return map;
	}
	
	/**
	 * ��չxstreamʹ��֧��CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out) {
				//�����е�xml�ڵ��ת�������� CDATA ���
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
	 * �ı���Ϣ����ת����xml 
	 * @param textMessage �ı���Ϣ���� 
	 * @return xml
	 */
	public static String textMessageToXml(TextMessageResp textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/**
	 * ͼƬ��Ϣ����ת����xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessageResp imageMessage){
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * ��Ƶ��Ϣ����ת��xml
	 * @param voiceMessage
	 * @return
	 */
	public static String voiceMessageToXml(VoiceMessageResp voiceMessage){
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}
	
	/**
	 * ��Ƶ��Ϣ����ת��xml
	 * @param videoMessage
	 * @return
	 */
	public static String videoMessageToXml(VideoMessageResp videoMessage){
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}
	/**
	 * ������Ϣ����ת����xml
	 * @param musicMessage
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessageResp musicMessage){
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * ͼ����Ϣ����ת����xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessageResp newsMessage){
		xstream.alias("xml", newsMessage.getClass());
		return xstream.toXML(newsMessage);
	}
	
}
