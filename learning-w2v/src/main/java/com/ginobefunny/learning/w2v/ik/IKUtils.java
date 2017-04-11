package com.ginobefunny.learning.w2v.ik;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ginozhang on 2017/2/27.
 */
public class IKUtils {

    static {
        Dictionary.initial();
    }

    public static List<String> tokens(String input) {
        List<String> results = new ArrayList<>();
        IKAnalyzer analyzer = new IKAnalyzer(true);
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("myfield", new StringReader(input));
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            ts.reset();
            while (ts.incrementToken()) {
                results.add(term.toString());
            }
            ts.end();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放TokenStream的所有资源
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }

    public static void main(String[] args) {
        System.out.println(tokens("南京市长江大桥将进入为期28个月的维修期。"));
    }

}
