/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 */
package com.ginobefunny.learning.w2v.ik;

import org.elasticsearch.common.io.PathUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 词典管理类,单子模式
 */
public class Dictionary {

    /*
     * 词典单子实例
     */
    private static Dictionary singleton;

    private DictSegment _MainDict;

    //TODO: not used by IK
    //private DictSegment _SurnameDict;

    private DictSegment _QuantifierDict;

    //TODO: not used by IK
    //private DictSegment _SuffixDict;

    //TODO: not used by IK
    //private DictSegment _PrepDict;

    private DictSegment _StopWords;

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    public static final String PATH_DIC_MAIN = "main.dic";
    //public static final String PATH_DIC_SURNAME = "surname.dic";
    public static final String PATH_DIC_QUANTIFIER = "quantifier.dic";
    //public static final String PATH_DIC_SUFFIX = "suffix.dic";
    //public static final String PATH_DIC_PREP = "preposition.dic";
    public static final String PATH_DIC_STOP = "stopword.dic";

    public static final String ROOT_PATH = "/home/test/w2v/config";

    private Dictionary() {

    }

    /**
     * 词典初始化 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
     * 只有当Dictionary类被实际调用时，才会开始载入词典， 这将延长首次分词操作的时间 该方法提供了一个在应用加载阶段就初始化字典的手段
     *
     * @return Dictionary
     */
    public static synchronized Dictionary initial() {
        if (singleton == null) {
            synchronized (Dictionary.class) {
                if (singleton == null) {
                    singleton = new Dictionary();
                    singleton.loadMainDict();
                    //singleton.loadSurnameDict();
                    singleton.loadQuantifierDict();
                    //singleton.loadSuffixDict();
                    //singleton.loadPrepDict();
                    singleton.loadStopWordDict();
                    return singleton;
                }
            }
        }
        return singleton;
    }

    /**
     * 获取词典单子实例
     *
     * @return Dictionary 单例对象
     */
    public static Dictionary getSingleton() {
        if (singleton == null) {
            throw new IllegalStateException("词典尚未初始化，请先调用initial方法");
        }
        return singleton;
    }

    /**
     * 批量加载新词条
     *
     * @param words Collection<String>词条列表
     */
    public void addWords(Collection<String> words) {
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    // 批量加载词条到主内存词典中
                    singleton._MainDict.fillSegment(word.trim().toCharArray());
                }
            }
        }
    }

    /**
     * 批量移除（屏蔽）词条
     */
    public void disableWords(Collection<String> words) {
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    // 批量屏蔽词条
                    singleton._MainDict.disableSegment(word.trim().toCharArray());
                }
            }
        }
    }

    /**
     * 检索匹配主词典
     *
     * @return Hit 匹配结果描述
     */
    public Hit matchInMainDict(char[] charArray) {
        return singleton._MainDict.match(charArray);
    }

    /**
     * 检索匹配主词典
     *
     * @return Hit 匹配结果描述
     */
    public Hit matchInMainDict(char[] charArray, int begin, int length) {
        return singleton._MainDict.match(charArray, begin, length);
    }

    /**
     * 检索匹配量词词典
     *
     * @return Hit 匹配结果描述
     */
    public Hit matchInQuantifierDict(char[] charArray, int begin, int length) {
        return singleton._QuantifierDict.match(charArray, begin, length);
    }

    /**
     * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
     *
     * @return Hit
     */
    public Hit matchWithHit(char[] charArray, int currentIndex, Hit matchedHit) {
        DictSegment ds = matchedHit.getMatchedDictSegment();
        return ds.match(charArray, currentIndex, 1, matchedHit);
    }

    /**
     * 判断是否是停止词
     *
     * @return boolean
     */
    public boolean isStopWord(char[] charArray, int begin, int length) {
        return singleton._StopWords.match(charArray, begin, length).isMatch();
    }

    /**
     * 加载主词典及扩展词典
     */
    private boolean loadMainDict() {
        // 建立一个主词典实例
        _MainDict = new DictSegment((char) 0);

        // 读取主词典文件
        this.loadWordsFromDictFile(_MainDict, Dictionary.PATH_DIC_MAIN);

        return true;
    }


    /**
     * 加载用户扩展的停止词词典
     */
    private boolean loadStopWordDict() {
        // 建立主词典实例
        _StopWords = new DictSegment((char) 0);

        // 读取主词典文件
        this.loadWordsFromDictFile(_StopWords, Dictionary.PATH_DIC_STOP);


        return true;
    }

    /**
     * 加载量词词典
     */
    private void loadQuantifierDict() {
        // 建立一个量词典实例
        _QuantifierDict = new DictSegment((char) 0);

        // 读取量词词典文件
        this.loadWordsFromDictFile(_QuantifierDict, Dictionary.PATH_DIC_QUANTIFIER);

    }

    private void loadWordsFromDictFile(DictSegment dict, String dictFile) {
        Path file = PathUtils.get(ROOT_PATH, dictFile);

        InputStream is = null;
        try {
            is = new FileInputStream(file.toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (is == null) {
            throw new RuntimeException("Dictionary file " + dictFile + " not found!!!");
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            String theWord = null;
            do {
                theWord = br.readLine();
                if (theWord != null && !"".equals(theWord.trim())) {
                    dict.fillSegment(theWord.trim().toCharArray());
                }
            } while (theWord != null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}