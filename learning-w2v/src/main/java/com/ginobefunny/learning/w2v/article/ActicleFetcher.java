package com.ginobefunny.learning.w2v.article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ginozhang on 2017/2/10.
 */
public final class ActicleFetcher {

    public static List<String> fetchAllContents() throws Exception {
        List<String> contentList = new ArrayList<String>();
        List<String> contentDataList = JDBCUtils.getAllContentData();
        String temp;
        for (String contentData : contentDataList) {
            if ((temp = ContentParser.parse(contentData)) != null) {
                contentList.add(temp);
            }
        }

        return contentList;
    }

    public static void main(String[] args) throws Exception {
        List<String> list = fetchAllContents();

        System.out.println(list.size());
        System.out.println(list.get(0));
    }
}
