package com.xiaoyv.javaengine;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String word = "why王怀玉";
        char[] chars = word.toCharArray();
        System.out.println(chars[5]);
    }
}