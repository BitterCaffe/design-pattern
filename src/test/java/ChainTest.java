import com.design.le.pojo.dto.ConfigDTO;
import com.design.le.responsibilitychain.ChainInterceptInMethod;
import com.design.le.responsibilitychain.DefaultChainFactory;

/**
 * @author Caffe
 * @date 2020/7/11
 * @description: TODO
 */
public class ChainTest {
    public static void main(String[] args) {
        //使用外部类实现拦截器
//        new ChainTest().chainTest();
        //使用内部类但是逃逸分析失败
//        new ChainTest().chainInMethodTest();
        // 使用内部类但是逃逸分析失败
//        new ChainTest().escapeAnalysis();
        // 使用内部类逃逸分析成功
//        new ChainTest().escapeAnalysisV1();
        // 直接在方法中做判断
        new ChainTest().escapeAnalysisV2();


    }

    /**
     * 使用外部类实现
     * server模式下jit默认阀值10000所以我们这里实行100000次来比较
     * 14223、14065
     */
    public void chainTest() {
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setCityName("cityName");
        configDTO.setSupplierId(1);
        boolean result = DefaultChainFactory.interceptChain(configDTO);
        System.out.println("result:" + result);
        long begin = System.currentTimeMillis();
        int count = 1000000000;
        for (int i = 0; i < count; i++) {
            DefaultChainFactory.interceptChain(configDTO);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    /**
     * 内部类测试
     * server模式下jit默认阀值10000所以我们这里实行1000000000次来比较
     * 逃逸分析开启： -XX:+DoEscapeAnalysis
     * 关闭逃逸分析：126527、126833
     * 开启逃逸分析：106382、101544
     * 没有想象的那么大、这种写法逃逸分析好像没起作用因为在堆内存中有频繁的分配内存和回收内存的行为……
     */
    public void chainInMethodTest() {
        ChainInterceptInMethod chainInterceptInMethod = new ChainInterceptInMethod();
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setCityName("cityName");
        configDTO.setSupplierId(1);
        boolean result12 = chainInterceptInMethod.judgeNullV1(configDTO);
        System.out.println("result12: " + result12);
    }


    /**
     * 逃逸分析失败
     * server模式下jit默认阀值10000所以我们这里实行1000000000次来比较
     * 逃逸分析开启： -XX:+DoEscapeAnalysis
     * 104600、131652
     */
    public void escapeAnalysis() {
        int count = 1000000000;
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setSupplierId(1);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                configDTO.setSkuId(null);
            }
            ChainInterceptInMethod.judgeNullV1(configDTO);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    /**
     * 逃逸分析起作用
     * server模式下jit默认阀值10000所以我们这里实行1000000000次来比较
     * 逃逸分析开启： -XX:+DoEscapeAnalysis
     * 1860、1909、1905、1937、1900、1850
     * 逃逸分析前置关闭： -XX:-DoEscapeAnalysis
     * 20992、18642、18736
     */
    public void escapeAnalysisV1() {
        int count = 1000000000;
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setSupplierId(1);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                configDTO.setSkuId(null);
            }
            ChainInterceptInMethod.judgeNullV2(configDTO);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }


    /**
     * 和内部类逃逸分析做比较
     * 2138、2060、2086、2167、2685、2669
     */
    public void escapeAnalysisV2() {
        int count = 1000000000;
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setSupplierId(1);
        Integer sku = configDTO.getSkuId();
        Integer supplierId = configDTO.getSupplierId();
        String cityName = "cityName";
        String wareHouseName = "houseName";

        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                configDTO.setSkuId(null);
            }
            boolean flag = (null != sku && null != supplierId);
            boolean flag1 = (null != cityName && null != wareHouseName);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}
