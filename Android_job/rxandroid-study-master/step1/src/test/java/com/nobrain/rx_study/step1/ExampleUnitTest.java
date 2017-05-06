package com.nobrain.rx_study.step1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        StringBuilder builder = new StringBuilder();
        for(String s : list){

            builder.append(s.toLowerCase());
        }
        System.out.println(builder.toString());
    }


    @Test
    public void test() throws Exception{
        List<String> list = Arrays.asList("A", "B", "C", "D");
        final StringBuilder builder = new StringBuilder();
        Observable.fromIterable(list)
                .map(s -> s.toLowerCase())
                .subscribe(s -> builder.append(s), throwable -> {
                    //nothing
                }, () -> System.out.print(builder.toString()));
    }
}