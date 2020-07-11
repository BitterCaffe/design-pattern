package com.design.le.responsibilitychain;

import com.design.le.annotation.InterceptAnnotation;
import com.design.le.pojo.dto.ConfigDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Caffe
 * @date 2020/7/5
 * @description: 链和拦截器都在方法中实现
 * <p>
 * 在方法中使用局部内部类会显得方法比较臃肿，但是有2个好处
 * 1、如果逃逸分析发现其他地方没有使用所以这个对象就分配在栈内存
 * 2、如果满足git编译条件则会直接生成机器码，运行会更快
 * 3、方法内的局部内部类应该是方法code属性中的局部变量表中存储
 */
public class ChainInterceptInMethod {

    /**
     * 判断方法参数中必填项是否为空
     * 这里的参数问题，按理说局部内部类使用形参要求参数为final的，因为有可能随着内部类逃离他的有效作用范围，
     * 但是下面没报错没想明白，谁看到了说一声非常感谢！
     *
     * @param config
     * @return
     */
    public boolean judgeNull(ConfigDTO config) {

        /**
         * 局部内部类
         */
        class NotNullChain {
            List<AbstractIntercept> intercepts = new ArrayList<>();

            /**
             * 这里使用了类的代码块来关联拦截器和链的关系，所以一定要注意拦截器集合、代码块的先后顺序即<init>方法中执行顺序
             */ {
                new NotNullIntercept();
                new NotEmptyIntercept();
                sort();
            }

            /**
             * 拦截器链
             * @param configDTO
             * @return
             */
            boolean chain(ConfigDTO configDTO) {

                for (AbstractIntercept intercept : intercepts) {
                    boolean flag = intercept.preHandler(configDTO);
                    if (!flag) {
                        return false;
                    }
                }
                return true;
            }

            /**
             * 拦截器排序
             */
            private void sort() {
                intercepts.sort(new Comparator<AbstractIntercept>() {
                    @Override
                    public int compare(AbstractIntercept o1, AbstractIntercept o2) {
                        InterceptAnnotation interceptAnnotation1 = o1.getClass().getAnnotation(InterceptAnnotation.class);
                        InterceptAnnotation interceptAnnotation2 = o2.getClass().getAnnotation(InterceptAnnotation
                                .class);
                        if (interceptAnnotation1 != null && interceptAnnotation2 != null) {
                            int sort1 = interceptAnnotation1.sort();
                            int sort2 = interceptAnnotation2.sort();
                            int res = sort2 - sort1;
                            return res;
                        }
                        return 0;
                    }
                });
            }


            /**
             * intercept specification
             */
            abstract class AbstractIntercept {
                /**
                 * pre handler
                 * @param configDTO
                 * @returnc
                 */
                abstract boolean preHandler(ConfigDTO configDTO);
            }

            @InterceptAnnotation(sort = 1)
            class NotNullIntercept extends AbstractIntercept {
                NotNullIntercept() {
                    intercepts.add(this);
                }

                @Override
                boolean preHandler(ConfigDTO configDTO) {
                    boolean flag = (configDTO.getSkuId() != null
                            || configDTO.getSupplierId() != null);

                    if (flag) {
                        return false;
                    }
                    return true;
                }
            }

            @InterceptAnnotation(sort = 2)
            class NotEmptyIntercept extends AbstractIntercept {
                NotEmptyIntercept() {
                    intercepts.add(this);
                }

                @Override
                boolean preHandler(ConfigDTO configDTO) {
                    boolean flag = (null != configDTO.getWarehouseName()
                            || null != configDTO.getCityName());

                    if (flag) {
                        return false;
                    }
                    return true;
                }
            }

        }
        // 局部内部类实现拦截器
        NotNullChain notNullChain = new NotNullChain();
        boolean flag = notNullChain.chain(config);
        return flag;
    }

    /**
     * 因为是局部内部类所以可以访问方法内的局部变量所以所有方法都为无参
     *
     * @param configDTO
     * @return
     */
    public boolean judgeNullV1(ConfigDTO configDTO) {

        /**
         * 局部内部类
         */
        class NotNullChain {
            List<AbstractIntercept> intercepts = new ArrayList<>();

            {
                new NotNullIntercept();
                new NotEmptyIntercept();
                sort();
            }

            /**
             * 拦截器链
             * @return
             */
            boolean chain() {

                for (AbstractIntercept intercept : intercepts) {
                    boolean flag = intercept.preHandler();
                    if (!flag) {
                        return false;
                    }
                }
                return true;
            }

            /**
             * 拦截器排序
             */
            private void sort() {
                intercepts.sort(new Comparator<AbstractIntercept>() {
                    @Override
                    public int compare(AbstractIntercept o1, AbstractIntercept o2) {
                        InterceptAnnotation interceptAnnotation1 = o1.getClass().getAnnotation(InterceptAnnotation.class);
                        InterceptAnnotation interceptAnnotation2 = o2.getClass().getAnnotation(InterceptAnnotation
                                .class);
                        if (interceptAnnotation1 != null && interceptAnnotation2 != null) {
                            int sort1 = interceptAnnotation1.sort();
                            int sort2 = interceptAnnotation2.sort();
                            int res = sort2 - sort1;
                            return res;
                        }
                        return 0;
                    }
                });
            }


            /**
             * intercept specification
             */
            abstract class AbstractIntercept {
                /**
                 * pre handler
                 * @returnc
                 */
                abstract boolean preHandler();
            }

            @InterceptAnnotation(sort = 1)
            class NotNullIntercept extends AbstractIntercept {
                NotNullIntercept() {
                    intercepts.add(this);
                }

                @Override
                boolean preHandler() {
                    boolean flag = (configDTO.getSkuId() != null
                            || configDTO.getSupplierId() != null);

                    if (flag) {
                        return false;
                    }
                    return true;
                }
            }

            @InterceptAnnotation(sort = 2)
            class NotEmptyIntercept extends AbstractIntercept {
                NotEmptyIntercept() {
                    intercepts.add(this);
                }

                @Override
                boolean preHandler() {
                    boolean flag = (null != configDTO.getWarehouseName()
                            || null != configDTO.getCityName());

                    if (flag) {
                        return false;
                    }
                    return true;
                }
            }

        }
        // 局部内部类实现拦截器
        NotNullChain notNullChain = new NotNullChain();
        boolean flag = notNullChain.chain();
        return flag;
    }

}
