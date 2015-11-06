/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Tekktronix
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.tekktronix.currencymodel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tekktronix on 11/5/2015.
 *
 * Tests for default method(s) in the ICurrencySet interface.
 */
public class ICurrencySetTest {
    private static class MockCurrencySet implements ICurrencySet {
        @Override
        public ICurrency getCurrency(String isoCode) {
            return null;
        }

        @Override
        public double getExchangeRate(String sourceCurrencyIsoCode, String targetCurrencyIsoCode) {
            return 4.0;
        }
    }

    @Test
    public void getExchangeRate() {
        MockCurrencySet currencySet = new MockCurrencySet();
        ICurrency usd = MockHelper.getMockCurrency(null, "USD", "US Dollar");
        ICurrency gbp = MockHelper.getMockCurrency(null, "GBP", "Great Britain Pound");
        Assert.assertEquals("Invalid exchange rate", 4.0, currencySet.getExchangeRate(usd, gbp), 0.00000001);
    }
}
