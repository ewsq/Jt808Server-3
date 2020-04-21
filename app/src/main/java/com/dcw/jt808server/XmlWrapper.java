package com.dcw.jt808server;

import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lixiaobin
 * @date 2019-10-15 18:38
 * @desc Xml消息工具
 *  用正则匹配的方式解析
 */
public class XmlWrapper {

    private static final String TAG = "XmlWrapper";

    public static final String REG_TAG_START = "<\\w+?>";
    public static final String REG_TAG_END = "</\\w+?>";
    public static final String TAG_ROOT = "root";
    public static final String TAG_END = "</" + TAG_ROOT + ">";
    public static final String TAG_START = "<" + TAG_ROOT + ">";
    public static final String XML_FLAG_LEFT = "(";
    public static final String XML_FLAG_RIGHT = ")";
    public static final String ESCAPE_LEFT_BRACKETS = "^$L$^"; //左括号转义
    public static final String ESCAPE_RIGHT_BRACKETS = "^$R$^"; //右括号转义
    public static final String ESCAPE_AND = "&amp;"; //&
    public static final String ESCAPE_MORE_THEN = "&gt;"; //大于 >
    public static final String ESCAPE_LESS_THEN = "&lt;"; //小于 <
    public static final String ESCAPE_APOSTROPHE = "&apos;"; //单引号 '
    public static final String ESCAPE_DOUBLE_QUOTES = "&quot;"; //双引号 "

    public void setEncodeWithRootTag(boolean encodeWithRootTag) {
        this.encodeWithRootTag = encodeWithRootTag;
    }

    private boolean encodeWithRootTag = true; //是否包含root标签

    public Map<String, String> getParams() {
        return mParams;
    }

    private Map<String, String> mParams;

    public static XmlWrapper createMsg(String msg) {
        return new XmlWrapper(msg);
    }

    public static XmlWrapper createEmptyMsg() {
        return new XmlWrapper(null);
    }

    private XmlWrapper(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Pattern pattern = Pattern.compile(REG_TAG_START);
            Matcher matcher = pattern.matcher(msg);
            while (matcher.find()) {
                String tagStart = matcher.group();
                if (!TextUtils.isEmpty(tagStart)) {
                    int indexStartTag = msg.indexOf(tagStart);
                    tagStart = tagStart.replace("<", "").replace(">", "");
                    int indexEndTag = msg.indexOf("</" + tagStart + ">");
                    if (indexEndTag > 0 && indexEndTag > indexStartTag && !TextUtils.equals(tagStart, TAG_ROOT)) {
                        //存在结束标签
                        String value = msg.substring(indexStartTag + tagStart.length() + 2, indexEndTag);
                        if (!TextUtils.isEmpty(value)) {
                            value = value
                                    .replace(ESCAPE_LEFT_BRACKETS, XML_FLAG_LEFT)
                                    .replace(ESCAPE_RIGHT_BRACKETS, XML_FLAG_RIGHT)
                                    .replace(ESCAPE_LESS_THEN, "<")
                                    .replace(ESCAPE_MORE_THEN, ">")
                                    .replace(ESCAPE_AND, "&")
                                    .replace(ESCAPE_APOSTROPHE, "'")
                                    .replace(ESCAPE_DOUBLE_QUOTES, "\"")
                            ;

                        }
                        addParam(tagStart, value);
                    }
                }
            }
        }
    }




    private void decodeXml(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Pattern pattern = Pattern.compile(REG_TAG_START);
            Matcher matcher = pattern.matcher(msg);
            while (matcher.find()) {
                String group = matcher.group();
            }
            if (matcher.find()) {
            }
            XmlPullParser parser = Xml.newPullParser();
            try {
                parser.setInput(new StringReader(msg));
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        String tagValue = parser.nextText();
                        if (!TextUtils.equals(TAG_ROOT, tagName) && !TextUtils.isEmpty(tagName)) {
                            tagValue = !TextUtils.isEmpty(tagValue) ? tagValue
                                    .replace(ESCAPE_LEFT_BRACKETS, XML_FLAG_LEFT)
                                    .replace(ESCAPE_RIGHT_BRACKETS, XML_FLAG_RIGHT)
                                    .replace(ESCAPE_AND,"&")
                                    .replace(ESCAPE_LESS_THEN,"<")
                                    .replace(ESCAPE_MORE_THEN,">")
                                    .replace(ESCAPE_APOSTROPHE,"'")
                                    .replace(ESCAPE_DOUBLE_QUOTES,"\"")
                                    : tagValue;//转义
                            addParam(tagName, tagValue);
                        }
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public XmlWrapper addParam(String key, String value) {
        if (null == mParams) {
            synchronized (XmlWrapper.class) {
                if (null == mParams) {
                    mParams = new HashMap<>();
                }
            }
        }

        mParams.put(key, value);
        return this;
    }

    public XmlWrapper addAll(Map<String, String> params) {
        if (null == mParams) {
            synchronized (XmlWrapper.class) {
                if (null == mParams) {
                    mParams = new HashMap<>();
                }
            }
        }

        mParams.putAll(params);
        return this;
    }

    public String build() {
        StringBuffer sb = new StringBuffer();
        if (encodeWithRootTag) {
            sb.append(XML_FLAG_LEFT + "root" + XML_FLAG_RIGHT);
        }
        if (null != mParams) {
            mParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            Set<String> keySet = mParams.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                sb.append(XML_FLAG_LEFT + next + XML_FLAG_RIGHT);
                String str = mParams.get(next);
                if (!TextUtils.isEmpty(str)) {
                    str = str.replace(XML_FLAG_LEFT, ESCAPE_LEFT_BRACKETS)
                            .replace(XML_FLAG_RIGHT, ESCAPE_RIGHT_BRACKETS)
                            .replace("<",ESCAPE_LESS_THEN)
                            .replace(">",ESCAPE_MORE_THEN)
                            .replace("&",ESCAPE_AND)
                            .replace("'",ESCAPE_APOSTROPHE)
                            .replace("\"",ESCAPE_DOUBLE_QUOTES);
                }
                sb.append(str);
                sb.append(XML_FLAG_LEFT + "/" + next + XML_FLAG_RIGHT);
            }
        }
        if (encodeWithRootTag) {
            sb.append(XML_FLAG_LEFT + "/root" + XML_FLAG_RIGHT);
        }
        String ret = sb.toString();
//        DCWLog.e(TAG, "build msg : " + ret);
        return ret;
    }


}
