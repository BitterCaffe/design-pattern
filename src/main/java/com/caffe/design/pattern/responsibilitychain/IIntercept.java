package com.caffe.design.pattern.responsibilitychain;

import com.caffe.design.pattern.pojo.dto.ConfigDTO;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public interface IIntercept {

    /**
     * pre handler  false 业务中断  true 业务继续
     *
     * @param configDTO
     * @return
     */
    boolean preHandler(ConfigDTO configDTO);

    /**
     * handler
     *
     * @param configDTO
     */
    void handler(ConfigDTO configDTO);
}
