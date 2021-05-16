package com.cts.csp.util;

import com.cts.csp.exception.CSPValidationException;

import java.math.BigDecimal;

public class CSPUtils {

    /**
     * If json has valid mutation symbol return it. Otherwise throw exception
     * @param mutation the mutation value
     * @return mutation symbol
     */
    public static char getValidMutationSymbol(BigDecimal mutation) {
        char symbol = mutation.toString().charAt(0);
         if(symbol == '-' || symbol == '+' || Character.isDigit(symbol))
             return symbol;
         throw new CSPValidationException(String.format("Mutation value format is not valid. Should have either + or -.", mutation));

    }
}
