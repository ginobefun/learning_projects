package com.ginobefunny.learning.w2v.article;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ginozhang on 2017/2/10.
 */
public final class SegWordsToFile {

    private static final String OUTPUT_FILE = "d://w2v_out.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader reader = null;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(OUTPUT_FILE);
            System.out.println("开始处理分词...");
            long start = System.currentTimeMillis();
            int allCount = 0;
            int termcnt = 0;
            List<String> guangContents = ActicleFetcher.fetchAllContents();
            Set<String> set = new HashSet<String>();
            for (String content : guangContents) {
                //System.out.println("处理文本:" + content);
                allCount += content.length();
                Result result = ToAnalysis.parse(content);
                for (Term term : result) {
                    String item = term.getName().trim();
                    if (item.length() > 0) {
                        termcnt++;
                        pw.print(item.trim() + " ");
                        set.add(item);
                    }
                }
                pw.println();
            }

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
