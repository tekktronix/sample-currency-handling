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
