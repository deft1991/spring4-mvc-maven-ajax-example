package com.golitsyn.validators;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainValidatorTest {

    @Test
    public void skipUsTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MainValidator mv = new MainValidator();
        Method method = mv.getClass().getDeclaredMethod("skipUs", String.class);
        method.setAccessible(true);
        assertEquals(true, method.invoke(mv, "valueDate"));
        assertEquals(true, method.invoke(mv, "excerciseStartDate"));
        assertEquals(false, method.invoke(mv, "test"));
    }
}