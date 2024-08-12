package main;
import org.junit.Test;
import java.io.IOException;

public class ProblemsTest extends TestBase{

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
