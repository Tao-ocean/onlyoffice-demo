package cn.ght.demo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @Author: ght
 * @Date: 2021/1/20
 */
public class TestStr {

    @Test
    void test() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
    }
}
