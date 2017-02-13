package com.ginobefunny.learning.w2v.article;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ginozhang on 2017/2/10.
 */
public final class ContentParser {

    public static String parse(String contentJSONData) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(contentJSONData);
            JSONObject data;
            String content;
            if ((data = (JSONObject) jsonObject.get("data")) != null && (content = (String) data.get("text")) != null) {
                return content;
            }
        } catch (Exception e) {
            System.err.println("Parse failed. json=" + contentJSONData);
            //e.printStackTrace();
        }

        return null;
    }

}
