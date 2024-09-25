package main;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class TestBase {
    public LocalTime start;
    public LocalTime end;

    public static BufferedReader br;
    public static BufferedWriter bw;
    public static BufferedWriter bl;

    @BeforeAll
    public static void init() throws IOException {
        System.out.println("Initialising resources ...");
        br = new BufferedReader(new FileReader("src/resources/data.txt"));
        bw = new BufferedWriter(new FileWriter("src/resources/result.txt"));
        bl = new BufferedWriter(new FileWriter("src/log.txt"));
    }

    @AfterAll
    public static void destroy() throws IOException {
        System.out.println("Closing resources ...");
        br.close();
        bw.close();
        bl.close();
    }

    @BeforeEach
    public void before() {
        setStartTime();
    }
    @AfterEach
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
        //System.out.println("=========================");
        System.out.println(msg + "\n" + ((float) Duration.between(start, end).toMillis() / 1000) + "s");
        System.out.println("=========================");
    }

    void print(Object s) {
        try{
            System.out.print(s.toString());
            bl.write(s.toString());
        }catch (Exception e) {

        }

    }

    void println(Object s) {
        try {
            System.out.println(s.toString());
            bl.write(s.toString());
            bl.newLine();
        } catch (Exception e) {

        }

    }

    void println() {
        try {
            System.out.println();
            bl.newLine();
        } catch (Exception e) {

        }
    }

}
