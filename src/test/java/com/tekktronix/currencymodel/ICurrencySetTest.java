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
