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

import com.tekktronix.utils.ErrorStrings;

/**
 * Created by Tekktronix on 10/24/2015.
 *
 * Represents an amount. This class is and should remain immutable.
 */
class AmountImpl implements IAmount {
    private volatile int _hashCode;
    private final ICurrency _currency;
    private final double _denomination;

    /** constructor */
    AmountImpl(ICurrency currency, double denomination) {
        if (currency == null) {
            throw new IllegalArgumentException(String.format(ErrorStrings.NullArgument, "currency"));
        }
        _currency = currency;
        _denomination = denomination;
    }

    /**
     * Gets the denomination of this amount.
     * @return The denomination.
     */
    @Override
    public double getDenomination() {
        return _denomination;
    }

    /**
     * Gets the currency of this amount.
     * @return The currency.
     */
    @Override
    public ICurrency getCurrency() {
        return _currency;
    }

    /**
     * Gets the denomination of this amount in the target currency after applying the
     * foreign exchange rate between the currency of this amount and the target currency.
     * @param targetCurrency The target currency.
     * @return The denomination in the target currency.
     */
    @Override
    public double getDenominationInCurrency(ICurrency targetCurrency) {
        if (targetCurrency == null) {
            throw new IllegalArgumentException(String.format(ErrorStrings.NullArgument, "targetCurrency"));
        }
        if (this.getCurrency() == targetCurrency) {
            return  this.getDenomination();
        }
        return this.getCurrency().getExchangeRate(targetCurrency) * this.getDenomination();
    }

    /**
     * Adds two amounts together in the currency of the left hand side (LHS) amount.
     * @param other The amount to be added to the LHS amount.
     * @return A new Amount instance in the LHS amount's currency.
     */
    @Override
    public IAmount add(IAmount other) {
        if (other == null) {
            throw new IllegalArgumentException(String.format(ErrorStrings.NullArgument, "other"));
        }
        return IAmount.makeAmount(getCurrency(), getDenomination() + other.getDenominationInCurrency(getCurrency()));
    }

    /**
     * Subtracts the right hand side amount from the left hand side (LHS) amount in the LHS amount's currency.
     * @param other The amount to be subtracted from the LHS amount.
     * @return A new Amount instance in the LHS Amount's currency.
     */
    @Override
    public IAmount subtract(IAmount other) {
        if (other == null) {
            throw new IllegalArgumentException(String.format(ErrorStrings.NullArgument, "other"));
        }
        return IAmount.makeAmount(getCurrency(), getDenomination() - other.getDenominationInCurrency(getCurrency()));
    }

    /**
     * Performs a scalar multiplication operation on the amount.
     * @param multiplicand The multiplicand.
     * @return The resulting amount.
     */
    @Override
    public IAmount scalarMultiply(double multiplicand) {
        return IAmount.makeAmount(getCurrency(), getDenomination() * multiplicand);
    }

    /**
     * Performs a scalar division on the amount.
     * @param divisor The divisor.
     * @return The resulting amount.
     */
    @Override
    public IAmount scalarDivide(double divisor) {
        if (divisor == 0) {
            String msg = String.format(ErrorStrings.AttemptToDivideByZero, "divisor");
            throw new IllegalArgumentException(msg);
        }
        return IAmount.makeAmount(getCurrency(), getDenomination() / divisor);
    }

    /**
     * Converts the Amount to the specified currency after applying the appropriate foreign exchange rate.
     * @param targetCurrency The target currency.
     * @return The Amount converted to the specified currency.
     */
    @Override
    public IAmount convertToCurrency(ICurrency targetCurrency) {
        if (targetCurrency == null) {
            throw new IllegalArgumentException(String.format(ErrorStrings.NullArgument, "targetCurrency"));
        }
        return IAmount.makeAmount(targetCurrency, getDenominationInCurrency(targetCurrency));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AmountImpl other = (AmountImpl)obj;

        // We do a reference comparison for the currency since we expect currencies
        // to be immutable and shared between amount objects with the same currency.
        // The currencies also have to be from the same currency set.
        return (other.getDenomination() == getDenomination()) && (getCurrency() == other.getCurrency());
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            long bits = Double.doubleToLongBits(getDenomination());
            int result = 11;
            result = 31 * result + (int) (bits ^ (bits >>> 32));
            result = 31 * result + getCurrency().hashCode();
            _hashCode = result;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return String.format("%1$2.1f %2$s", getDenomination(), getCurrency().getIsoCode());
    }
}
