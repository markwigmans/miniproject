package com.ximedes.bitcoin.miniproject.service;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * unit test class for {@link FeeService}
 */
public class FeeServiceTest {

    @Test
    public void testCalcFee() {
        int fee = 20;
        FeeService service = new FeeService(fee);
        byte[] message = new byte[]{1, 2, 3, 4,};
        assertThat(service.calcFee(message), is(80));
    }

    @Test
    public void testCalcFeeNull() {
        int fee = 30;
        FeeService service = new FeeService(fee);
        assertThat(service.calcFee(null), is(0));
    }
}