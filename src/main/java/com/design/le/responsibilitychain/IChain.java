package com.design.le.responsibilitychain;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public interface IChain {
    /**
     * add intercept
     *
     * @param intercept
     */
    void add(IIntercept intercept);
}
