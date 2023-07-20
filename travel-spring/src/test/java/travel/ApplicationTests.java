package travel;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class ApplicationTests {

    @Test
    void Tests() {
        String pwd = "123456";
        //String salt1 = DigestUtil.md5Hex(pwd);
        //String salt2 = DigestUtil.md5Hex(pwd, "salt");
        //System.out.println("md5带盐加密：" + salt2);
        String salt1 = new MD5().digestHex16(pwd);
        String salt2 = new MD5("salt".getBytes(StandardCharsets.UTF_8)).digestHex16(pwd);
        String salt3 = new MD5("salt".getBytes(StandardCharsets.UTF_8), 3).digestHex16(pwd);
        System.out.println("md5加密：" + salt1);
        System.out.println("md5带盐加密：：" + salt2);
        System.out.println("md5带盐3次加密：：" + salt3);
    }

}
