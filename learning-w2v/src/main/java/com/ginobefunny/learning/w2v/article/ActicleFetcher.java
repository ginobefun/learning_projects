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
        int validCount = 0, invalidCount = 0;
        for (String contentData : contentDataList) {
            if ((temp = ContentParser.parse(contentData)) != null) {
                contentList.add(temp);
                validCount++;
            } else {
                invalidCount++;
            }
        }

        System.out.println("[Fetch Result][valid=" + validCount + "][invalid=" + invalidCount + "]");
        return contentList;
    }

    public static List<String> fetchAllContentsFromYOHO() throws Exception {
        List<String> contentList = new ArrayList<String>();
        List<String> contentDataList = JDBCUtils.getAllBoysContentDetail();
        String temp;
        int validCount = 0, invalidCount = 0;
        for (String contentData : contentDataList) {
            if ((temp = ContentParser.parseForHtml(contentData)) != null) {
                contentList.add(temp);
                validCount++;
            } else {
                invalidCount++;
            }
        }

        contentDataList = JDBCUtils.getAllGirlsContentDetail();
        for (String contentData : contentDataList) {
            if ((temp = ContentParser.parseForHtml(contentData)) != null) {
                contentList.add(temp);
                validCount++;
            } else {
                invalidCount++;
            }
        }

        System.out.println("[Fetch Result][valid=" + validCount + "][invalid=" + invalidCount + "]");
        return contentList;
    }

    public static void main(String[] args) throws Exception {
        List<String> list = fetchAllContents();

        System.out.println(list.size());
        System.out.println(list.get(0));
    }
}
