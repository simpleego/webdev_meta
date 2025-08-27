package com.simple.springex.polymorphism;

import org.springframework.stereotype.Component;

//@Component("tv")
public class LgTV implements TV{

    public LgTV() {
        System.out.println("LgTV");
    }

    @Override
    public void powerOn() {
        System.out.println("LGTV 전원을 켠다.");
    }

    @Override
    public void powerOff() {
        System.out.println("LGTV 전원을 끈다.");
    }

    @Override
    public void volumeUp() {
        System.out.println("LGTV 소리을 높이다.");
    }

    @Override
    public void volumeDown() {
        System.out.println("LGTV 소리을 낮춘다.");
    }
}
