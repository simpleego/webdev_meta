package com.simple.springex.polymorphism;

public class BeanFactory {
    public Object getBean(String beanName) {
        if (beanName.equals("samsung")) {
            return new SamsungTV();
        }

        if (beanName.equals("lg")) {
            return new LgTV();
        }
        return null;

    }
}
