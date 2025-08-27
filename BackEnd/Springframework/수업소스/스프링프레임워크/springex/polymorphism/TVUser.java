package com.simple.springex.polymorphism;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TVUser {

    public static void main(String[] args) {
        // 1. Spring 컨테이너를 구동한다. (스프링 컨테이너중 한 개인 GenericXmlApplicationContext 사용 )
        AbstractApplicationContext factory =
                new GenericXmlApplicationContext("applicationContext.xml");
        // 2. Spring 컨테이너로부터 필요한 객체를 요청(Lookup)한다.
        TV tv = (TV) factory.getBean("tv"); // bean 에 저장된 클래스를 object로 객체화한다.
        tv.powerOn();
        tv.volumeUp();
        tv.volumeDown();
        tv.powerOff();
        System.out.println(tv.hashCode());

        TV tv1 = (TV) factory.getBean("tv");
        tv1.powerOn();
        System.out.println(tv1.hashCode());

        SamsungTV samsungTV = new SamsungTV();
        System.out.println(samsungTV.hashCode());

        // 3. Spring 컨테이너를 종료한다.
        factory.close();
    }
}
