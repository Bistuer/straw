package cn.tedu.straw.sys;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {
    public static void main(String[] args) {
        double a = 0.801;
        double b = 0.796;

        double c = 2*a*b/(a+b);
        System.out.println(c);

    }
}
