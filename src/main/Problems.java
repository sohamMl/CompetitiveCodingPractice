package main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class Problems {

    public LocalTime start;
    public LocalTime end;

    public static BufferedReader br;
    public static BufferedWriter bw;

    @BeforeClass
    public static void init() throws IOException {
        System.out.println("Starting tests");
        br = new BufferedReader(new FileReader("src/resources/data.txt"));
        bw = new BufferedWriter(new FileWriter("src/resources/result.txt"));
    }

    @AfterClass
    public static void destroy() throws IOException {
        System.out.println("ending tests");
        br.close();
        bw.close();
    }

    @Before
    public void before() {
        setStartTime();
    }
    @After
    public void after() {
        setEndTime();
        showTimeTaken("Total time taken : ");
    }

    public void setStartTime() {
        start = LocalTime.now();
    }

    public void setEndTime() {
        end = LocalTime.now();
    }
    public void showTimeTaken(String msg) {
        System.out.println("=========================");
        System.out.println(msg + "\n" + ((float) Duration.between(start, end).toMillis() / 1000) + "s");
        System.out.println("=========================");
    }


    @Test
    public void test() throws InterruptedException {
        System.out.println("hello world");
        Thread.sleep(5000);
    }

    @Test
    public void test2() throws InterruptedException, IOException {
        System.out.println("hello world 2");
        Thread.sleep(7000);
    }
}
