package com.ginobefunny.learning.w2v.ansj;

import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ginozhang on 2017/2/9.
 */
public class WordAnalyzer {

    public static final String TAG_START_CONTENT = "<content>";
    public static final String TAG_END_CONTENT = "</content>";

    private static final String INPUT_FILE = "/home/test/w2v/corpus.txt";

    private static final String OUTPUT_FILE = "/home/test/w2v/corpus_out.txt";

    public static void main(String[] args) {
        String temp = null ;

        BufferedReader reader = null;
        PrintWriter pw = null;
        try {
            //reader = IOUtil.getReader(INPUT_FILE, "UTF-8") ;
            System.out.println(ToAnalysis.parse("南京市长江大桥进入为期28个月的维修期。"));
//            pw = new PrintWriter(OUTPUT_FILE);
//            System.out.println("开始处理");
//            long start = System.currentTimeMillis()  ;
//            int allCount =0 ;
//            int termcnt = 0;
//            Set<String> set = new HashSet<String>();
//            while((temp=reader.readLine())!=null){
//                temp = temp.trim();
//                if (temp.startsWith(TAG_START_CONTENT)) {
//                    System.out.println("处理文本:"+temp);
//                    int end = temp.indexOf(TAG_END_CONTENT);
//                    String content = temp.substring(TAG_START_CONTENT.length(), end);
//                    if (content.length() > 0) {
//                        allCount += content.length() ;
//                        Result result = ToAnalysis.parse(content);
//                        for (Term term: result) {
//                            String item = term.getName().trim();
//                            if (item.length() > 0) {
//                                termcnt++;
//                                pw.print(item.trim() + " ");
//                                set.add(item);
//                            }
//                        }
//                        pw.println();
//                    }
//                }
//            }
//            long end = System.currentTimeMillis() ;
//            System.out.println("共" + termcnt + "个term，" + set.size() + "个不同的词，共 "
//                    +allCount+" 个字符，每秒处理了:"+(allCount*1000.0/(end-start)));
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
