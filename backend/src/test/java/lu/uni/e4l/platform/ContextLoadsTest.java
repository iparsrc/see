package lu.uni.e4l.platform;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContextLoadsTest {

    @Test
    public void contextLoads() { }

    @Test
    public void dummyPrintTest() {
        System.out.println("Hello, this is a dummy passing test!");
    }

    // @Test
    // public void dummyFailingTest() {
    //     System.out.println("Hello, this is a dummy failing test!");
    //     org.junit.Assert.fail("Hello, this is a dummy failing test!");
    // }

}