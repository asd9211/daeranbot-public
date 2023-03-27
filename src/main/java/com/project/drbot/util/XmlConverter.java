package com.project.drbot.util;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlConverter {

    private XmlConverter() {
    }

    public static List<Map<String, String>> xmlToItemList(Document xml) {
        //Root엘리먼트 획득
        Element element = xml.getDocumentElement();

        //Root엘리먼트의 channel 자식태그는 1개이므로 item(0)으로 획득
        Node channelNode = element.getElementsByTagName("channel").item(0);

        //channel태그내에 존재하는 자식태그 획득
        NodeList nodeList = channelNode.getChildNodes();

        List<Map<String, String>> itemList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals("item")) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                NodeList itemNodeList = nodeList.item(i).getChildNodes();
                for (int j = 0; j < itemNodeList.getLength(); j++) {
                    String content = itemNodeList.item(j).getTextContent().replaceAll("\n", "");
                    itemMap.put(itemNodeList.item(j).getNodeName(), content);
                }
                itemList.add(itemMap);
            }
        }
        return itemList;
    }
}
