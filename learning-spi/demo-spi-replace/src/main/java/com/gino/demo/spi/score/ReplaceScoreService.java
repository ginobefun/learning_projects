package com.gino.demo.spi.score;

import com.gino.demo.spi.api.ScoreService;

/**
 * Created by gino.zhang on 2017/7/13.
 */
public class ReplaceScoreService implements ScoreService {

    public double calScore(double tdidfScore, double cosScore) {
        return cosScore;
    }
}
