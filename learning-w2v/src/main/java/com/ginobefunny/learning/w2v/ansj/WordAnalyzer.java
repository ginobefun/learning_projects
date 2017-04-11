package com.ginobefunny.learning.w2v.ansj;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.util.IOUtil;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ginozhang on 2017/2/9.
 */
public class WordAnalyzer {
    private static final String TAG_START_CONTENT = "<content>";
    private static final String TAG_END_CONTENT = "</content>";
    private static final String INPUT_FILE = "/home/test/w2v/corpus.txt";
    private static final String OUTPUT_FILE = "/home/test/w2v/corpus_out.txt";

    public static void main(String[] args) throws Exception {
        BufferedReader reader = null;
        PrintWriter pw = null;
        try {
            System.out.println("开始处理分词...");
            reader = IOUtil.getReader(INPUT_FILE, "UTF-8");
            pw = new PrintWriter(OUTPUT_FILE);
            long start = System.currentTimeMillis();
            int totalCharactorLength = 0;
            int totalTermCount = 0;
            Set<String> set = new HashSet<String>();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                temp = temp.trim();
                if (temp.startsWith(TAG_START_CONTENT)) {
                    //System.out.println("处理文本:" + temp);
                    int end = temp.indexOf(TAG_END_CONTENT);
                    String content = temp.substring(TAG_START_CONTENT.length(), end);
                    totalCharactorLength += content.length();
                    Result result = ToAnalysis.parse(content);
                    for (Term term : result) {
                        String item = term.getName().trim();
                        totalTermCount++;
                        pw.print(item + " ");
                        set.add(item);
                    }
                    pw.println();
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("共" + totalTermCount + "个Term，" + set.size() + "个不同的词，共 "
                    + totalCharactorLength + "个字符，每秒处理了:" + (totalCharactorLength * 1000.0 / (end - start)));
        } finally {
            // close reader and pw
        }
    }
}
