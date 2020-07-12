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
        chainTest();
        chainInMethodTest();
    }

    /**
     * 使用外部类实现
     */
    public static void chainTest() {
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setCityName("cityName");
        configDTO.setSupplierId(1);
        boolean result = DefaultChainFactory.interceptChain(configDTO);
        System.out.println("result:" + result);
    }

    /**
     * 内部类测试
     */
    public static void chainInMethodTest() {
        ChainInterceptInMethod chainInterceptInMethod = new ChainInterceptInMethod();
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setCityName("cityName");
        configDTO.setSupplierId(1);
        boolean result12 = chainInterceptInMethod.judgeNullV1(configDTO);
        System.out.println("result12: " + result12);
    }

}
