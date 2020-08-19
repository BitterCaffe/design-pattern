package com.caffe.design.pattern.responsibilitychain;

import com.caffe.design.pattern.pojo.dto.ConfigDTO;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public class DefaultChainFactory {

    private static DefaultChain defaultChain;

    static {
        // chain
        defaultChain = new DefaultChain();
        // intercept
        ParamIntercept.build();
        ParamNullIntercept.build();
        // sort
        defaultChain.sort();

    }

    /**
     * @param configDTO
     * @return
     */
    public static boolean interceptChain(ConfigDTO configDTO) {
        boolean res = defaultChain.preHandler(configDTO);
        return res;
    }

    /**
     * add intercept
     *
     * @param intercept
     */
    public static void add(IIntercept intercept) {
        defaultChain.add(intercept);
    }
}
