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
        System.out.println(result);
    }

    /**
     * 内部类测试
     */
    public static void chainInMethodTest() {
        ChainInterceptInMethod chainInterceptInMethod = new ChainInterceptInMethod();
        //无值
        boolean result = chainInterceptInMethod.judgeNull(new ConfigDTO());
        System.out.println("judge result:" + result);

        //有值(相关参数)
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setSkuId(1);
        configDTO.setCityName("cityName");
        configDTO.setSupplierId(1);
        boolean result1 = chainInterceptInMethod.judgeNull(configDTO);
        System.out.println("judge result1:" + result1);

        /************************  无参   **********************/
        boolean result11 = chainInterceptInMethod.judgeNullV1(new ConfigDTO());
        boolean result12 = chainInterceptInMethod.judgeNullV1(configDTO);
        System.out.println("result11: " + result11 + " result12: " + result12);
    }

}
