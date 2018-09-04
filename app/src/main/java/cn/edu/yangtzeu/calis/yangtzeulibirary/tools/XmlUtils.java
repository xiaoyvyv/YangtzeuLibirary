package cn.edu.yangtzeu.calis.yangtzeulibirary.tools;

import android.util.Xml;

import com.blankj.utilcode.util.LogUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookIndexBean;

/**
 * Created by 1250052380@qq.com on 2015/5/19.
 */
public class XmlUtils {

    public static List<BookIndexBean> parseBookIndexBean(String xml) {
        List<BookIndexBean> bookIndexBeans = new ArrayList<>();
        BookIndexBean bookIndexBean = new BookIndexBean();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            //使用PULL解析
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(is, "UTF-8");
            //获取解析的标签的类型
            int type = xmlPullParser.getEventType();

            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                        /*
                        <record>
                        <id>2</id>
                        <parentId>0</parentId>
                        <name><![CDATA[A]]></name>
                        <categoryDesc><![CDATA[A 马列主义、毛泽东思想、邓小平理论]]></categoryDesc>
                        <showOrder>0</showOrder>
                        <libcode><![CDATA[999]]></libcode>
                        <childrenCount>8</childrenCount>
                        </record>
                         */

                        // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        bookIndexBeans = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        //获取开始标签的名字
                        String starttgname = xmlPullParser.getName();
                        if ("record".equals(starttgname)) { // 判断开始标签元素是否是daxiongmei
                            bookIndexBean = new BookIndexBean();
                        }
                        if ("id".equals(starttgname)) {
                            //获取id的值
                            String id = xmlPullParser.nextText();
                            LogUtils.i("id", id);
                            bookIndexBean.setId(id);
                        } else if ("parentId".equals(starttgname)) {
                            String parentId = xmlPullParser.nextText();
                            LogUtils.i("parentId", parentId);
                            bookIndexBean.setParentId(parentId);
                        } else if ("name".equals(starttgname)) {
                            String name = xmlPullParser.nextText();
                            LogUtils.i("name", name);
                            bookIndexBean.setName(name);
                        } else if ("categoryDesc".equals(starttgname)) {
                            String categoryDesc = xmlPullParser.nextText();
                            LogUtils.i("categoryDesc", categoryDesc);
                            bookIndexBean.setCategoryDesc(categoryDesc);
                        } else if ("showOrder".equals(starttgname)) {
                            String showOrder = xmlPullParser.nextText();
                            LogUtils.i("showOrder", showOrder);
                            bookIndexBean.setShowOrder(showOrder);
                        } else if ("libcode".equals(starttgname)) {
                            String libcode= xmlPullParser.nextText();
                            LogUtils.i("libcode", libcode);
                            bookIndexBean.setLibcode(libcode);
                        } else if ("childrenCount".equals(starttgname)) {
                            String childrenCount = xmlPullParser.nextText();
                            LogUtils.i("childrenCount", childrenCount);
                            bookIndexBean.setChildrenCount(childrenCount);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        bookIndexBeans.add(bookIndexBean);
                        break;

                }

                //细节：
                type = xmlPullParser.next();

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return bookIndexBeans;
    }
}