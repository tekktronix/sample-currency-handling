/**
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

/**
 * Created by Tekktronix on 10/24/2015.
 *
 */
public interface ICurrency {
    /**
     * Gets the full name of the currency.
     * @return The full name.
     */
    String getName();

    /**
     * Gets the ISO code of the currency.
     * @return The ISO code.
     */
    String getIsoCode();

    /**
     * Gets the set to which this currency belongs.
     * @return The currency set.
     */
    ICurrencySet getCurrencySet();

    /**
     * Gets the foreign exchange rate between this currency and the target currency
     * @param targetCurrency The target currency.
     * @return The foreign exchange rate between the two currencies.
     * @exception java.lang.IllegalArgumentException If the target currency is invalid
     * or if the foreign exchange rates between this currency and the target currency
     * is not available in the currency set.
     */
    default double getExchangeRate(ICurrency targetCurrency) {
        return getCurrencySet().getExchangeRate(this, targetCurrency);
    }
}
