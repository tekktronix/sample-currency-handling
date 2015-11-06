package com.tekktronix.currencymodel;

import org.mockito.Mockito;

/**
 * Created by Tekktronix on 11/5/2015.
 *
 * Helper class to create mock objects
 */
class MockHelper {
    static ICurrency getMockCurrency(ICurrencySet currencySet, String isoCode, String name) {
        ICurrency currency = Mockito.mock(ICurrency.class);
        Mockito.when(currency.getIsoCode()).thenReturn(isoCode);
        Mockito.when(currency.getName()).thenReturn(name);
        Mockito.when(currency.toString()).thenReturn(isoCode);
        if (currencySet != null) {
            Mockito.when(currencySet.getCurrency(currency.getIsoCode())).thenReturn(currency);
            Mockito.when(currency.getCurrencySet()).thenReturn(currencySet);
        }
        return currency;
    }

    static ICurrencySet getMockCurrencySet()  {
        return Mockito.mock(ICurrencySet.class);
    }

    static void stubExchangeRate(ICurrency thisCurrency, ICurrency targetCurrency, double value) {
        ICurrencySet currencySet = thisCurrency.getCurrencySet();
        Mockito.when(currencySet.getExchangeRate(thisCurrency, targetCurrency)).thenReturn(value);
        Mockito.when(currencySet.getExchangeRate(targetCurrency, thisCurrency)).thenReturn(1/value);

        double rate1 = currencySet.getExchangeRate(thisCurrency, targetCurrency);
        double rate2 = currencySet.getExchangeRate(targetCurrency, thisCurrency);
        Mockito.when(thisCurrency.getExchangeRate(targetCurrency)).thenReturn(rate1);
        Mockito.when(targetCurrency.getExchangeRate(thisCurrency)).thenReturn(rate2);
    }
}
