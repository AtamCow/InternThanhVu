package chapter10;

import org.testng.annotations.BeforeClass;

public class TestTC {
    @BeforeClass
    public void check1() {
        System.out.println("check1");
    }

    @BeforeClass
    public void check2() {
        System.out.println("check2");
    }
}
