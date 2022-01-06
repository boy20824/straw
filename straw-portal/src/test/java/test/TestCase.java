package test;

import org.junit.jupiter.api.Test;

public class TestCase {

    @Test
    public void testLombok(){
        Msg msg=new Msg();
        msg.setId(1);
        msg.setName("John");
        msg.setMessage("test");
        System.out.println(msg);

        Msg msg2=new Msg();
        msg2.setName("Tom");
        System.out.println(msg2);
    }

    @Test
    public  void testLoggerDemo(){
        LoggerDemo log= new LoggerDemo();
    }
}
