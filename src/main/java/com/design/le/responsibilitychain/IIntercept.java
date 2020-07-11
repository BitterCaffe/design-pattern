package com.design.le.responsibilitychain;

import com.design.le.pojo.dto.ConfigDTO;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public interface IIntercept {

    /**
     * pre handler  true 业务中断  false 业务继续
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
