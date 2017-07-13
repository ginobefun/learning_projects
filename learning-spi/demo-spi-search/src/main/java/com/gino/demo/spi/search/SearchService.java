package com.gino.demo.spi.search;

import com.gino.demo.spi.api.ScoreService;

import java.util.*;

public class SearchService {

    public static Map<String, Double> search(Map<String, Double> tdidfScoreMap, Map<String, Double> cosScoreMap) {
        ScoreService scoreService;
        ServiceLoader<ScoreService> loader = ServiceLoader.load(ScoreService.class);
        if (loader.iterator().hasNext()) {
            scoreService = loader.iterator().next();
        } else {
            throw new IllegalStateException("Cannot find score services.");
        }

        System.out.println("Use Score Service: " + scoreService.getClass().getName());
        Map<String, Double> finalScoreMap = new HashMap<>();
        tdidfScoreMap.forEach((pId, tdidfScore) -> {
            finalScoreMap.put(pId, scoreService.calScore(tdidfScore, cosScoreMap.get(pId)));
        });

        Map<String, Double> resultMap = new LinkedHashMap<>();
        finalScoreMap.keySet().stream()
                .sorted(Comparator.comparing(pId -> finalScoreMap.get(pId), Comparator.comparingDouble(s -> s)).reversed())
                .forEachOrdered(pId -> {
                    resultMap.put(pId, finalScoreMap.get(pId));
                });

        return resultMap;
    }
}
