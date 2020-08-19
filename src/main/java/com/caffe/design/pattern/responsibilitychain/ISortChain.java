package com.caffe.design.pattern.responsibilitychain;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public interface ISortChain extends IIntercept {

    /**
     * intercept  sort
     */
    void sort();
}
