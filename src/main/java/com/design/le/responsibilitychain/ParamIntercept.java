package com.design.le.responsibilitychain;

import com.design.le.annotation.InterceptAnnotation;
import com.design.le.pojo.dto.ConfigDTO;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
@InterceptAnnotation(sort = 1)
public final class ParamIntercept implements IIntercept {
    /**
     * construct
     */
    private ParamIntercept() {
        DefaultChainFactory.add(this);
    }

    @Override
    public boolean preHandler(ConfigDTO configDTO) {
        boolean flag = (null != configDTO.getWarehouseName()
                && null != configDTO.getCityName());
        return flag;
    }

    @Override
    public void handler(ConfigDTO configDTO) {
        // do something
    }

    /**
     * build param
     */
    public static void build() {
        new ParamIntercept();
    }
}
