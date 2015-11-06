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
import org.mockito.Mockito;

/**
 * Created by Tekktronix on 11/5/2015.
 *
 * Tests for default methods in the ICurrency interface
 */
public class ICurrencyTest {
    private static class MockCurrency implements ICurrency {


        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getIsoCode() {
            return null;
        }

        @Override
        public ICurrencySet getCurrencySet() {
            return null;
        }
    }

    @Test
    public void testGetExchangeRate() {
        ICurrencySet currencySet = MockHelper.getMockCurrencySet();
        ICurrency usd = new ICurrency() {
            public ICurrencySet getCurrencySet() { return currencySet; }
            public String getIsoCode() { return "USD"; }
            public String getName() { return "US Dollar"; }
        };

        ICurrency gbp = new ICurrency() {
            public ICurrencySet getCurrencySet() { return currencySet; }
            public String getIsoCode() { return "GBP"; }
            public String getName() { return "Great Britain Pound"; }
        };

        Mockito.when(currencySet.getExchangeRate(usd, gbp)).thenReturn(4.0);
        Assert.assertEquals("Incorrect exchange rate", 4.0, usd.getExchangeRate(gbp), 0.00000001);
    }
}
