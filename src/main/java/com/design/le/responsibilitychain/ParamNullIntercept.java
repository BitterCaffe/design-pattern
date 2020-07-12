package com.design.le.responsibilitychain;

import com.design.le.pojo.dto.ConfigDTO;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public class ParamNullIntercept implements IIntercept {

    /**
     * private construct
     */
    private ParamNullIntercept() {
        DefaultChainFactory.add(this);
    }

    @Override
    public boolean preHandler(ConfigDTO configDTO) {
        boolean flag = (configDTO.getSkuId() != null
                && configDTO.getSupplierId() != null);
        return flag;
    }

    @Override
    public void handler(ConfigDTO configDTO) {

    }

    /**
     * build object
     */
    public static void build() {
        new ParamNullIntercept();
    }
}
