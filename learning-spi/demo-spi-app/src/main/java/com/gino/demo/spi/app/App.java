package com.gino.demo.spi.app;

import com.gino.demo.spi.search.SearchService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gino.zhang on 2017/7/13.
 */
public class App {

    public static void main(String[] args) {
        Map<String, Double> tdidfScoreMap = new HashMap<>();
        tdidfScoreMap.put("product1", Double.valueOf(0.3D));
        tdidfScoreMap.put("product2", Double.valueOf(0.5D));
        tdidfScoreMap.put("product3", Double.valueOf(0.8D));

        Map<String, Double> cosScoreMap = new HashMap<>();
        cosScoreMap.put("product1", Double.valueOf(0.2D));
        cosScoreMap.put("product2", Double.valueOf(0.7D));
        cosScoreMap.put("product3", Double.valueOf(0.4D));

        Map<String, Double> resultMap = SearchService.search(tdidfScoreMap, cosScoreMap);
        System.out.println("Search Result: " + resultMap);
    }
}
