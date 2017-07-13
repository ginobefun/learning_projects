package com.gino.demo.spi.api;

/**
 * Created by gino.zhang on 2017/7/13.
 */
public interface ScoreService {

    /**
     * 计算商品搜索的得分
     * @param tdidfScore 文本相关性得分
     * @param cosScore   用户和商品偏好性得分
     * @return 计算最终得分
     */
    double calScore(double tdidfScore, double cosScore);
    
}
