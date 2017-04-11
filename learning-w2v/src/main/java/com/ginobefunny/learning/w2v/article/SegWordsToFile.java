package com.ginobefunny.learning.w2v.article;

import com.ginobefunny.learning.w2v.ik.IKUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ginozhang on 2017/2/10.
 */
public final class SegWordsToFile {

    private static final String OUTPUT_FILE = "/home/test/w2v/guang_yyyyMMdd.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader reader = null;
        PrintWriter pw = null;
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
            pw = new PrintWriter(OUTPUT_FILE.replace("yyyyMMdd",formater.format(new Date())));
            System.out.println("开始处理分词...");
            long start = System.currentTimeMillis();
            int allCount = 0;
            int termcnt = 0;
            List<String> guangContents = ActicleFetcher.fetchAllContents();
            List<String> yohoContents = ActicleFetcher.fetchAllContentsFromYOHO();
            System.out.println("逛的文章数："+guangContents.size()+", 社区的文章数："+yohoContents.size());
            guangContents.addAll(yohoContents);
            Set<String> set = new HashSet<String>();
            for (String content : guangContents) {
                //System.out.println("处理文本:" + content);
                allCount += content.length();
                //Result result = ToAnalysis.parse(content);
                List<String> terms = IKUtils.tokens(content);
                for (String term : terms) {
                    String item = term.trim();
                    if (item.length() > 0) {
                        termcnt++;
                        pw.print(item.trim() + " ");
                        set.add(item);
                    }
                }
                pw.println();
            }

            //共70919813个term，66311个不同的词，共 224446797 个字符，每秒处理了:3900437.8736271374
            //共5086707个term，61434个不同的词，共 12652109 个字符，每秒处理了:559110.3893234346
            long end = System.currentTimeMillis();
            System.out.println("共" + termcnt + "个term，" + set.size() + "个不同的词，共 "
                    + allCount + " 个字符，每秒处理了:" + (allCount * 1000.0 / (end - start)));
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != pw) {
                pw.close();
            }
        }
    }
}
