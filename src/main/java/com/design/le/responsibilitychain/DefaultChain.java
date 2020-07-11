package com.design.le.responsibilitychain;

import com.design.le.annotation.InterceptAnnotation;
import com.design.le.pojo.dto.ConfigDTO;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public class DefaultChain implements ISortChain, IChain {
    List<IIntercept> interceptList = Lists.newArrayList();

    @Override
    public void sort() {
        interceptList.sort((o1, o2) -> {
            InterceptAnnotation annotation1 = o1.getClass().getAnnotation(InterceptAnnotation.class);
            InterceptAnnotation annotation2 = o1.getClass().getAnnotation(InterceptAnnotation.class);
            if (null != annotation1 && null != annotation2) {
                int sort1 = annotation1.sort();
                int sort2 = annotation2.sort();
                return sort1 - sort2;
            }
            return 0;
        });
    }

    @Override
    public boolean preHandler(ConfigDTO configDTO) {
        for (IIntercept iIntercept : interceptList) {
            boolean flag = iIntercept.preHandler(configDTO);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handler(ConfigDTO configDTO) {

    }


    @Override
    public void add(IIntercept intercept) {
        interceptList.add(intercept);
    }
}
