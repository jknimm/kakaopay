package com.kakao.pay.utility;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenGeneratorTest {

    @Test
    void generateToken() {
        String token = TokenGenerator.generateToken();
        System.out.println(token);
        Assert.assertNotNull(token);


    }
}