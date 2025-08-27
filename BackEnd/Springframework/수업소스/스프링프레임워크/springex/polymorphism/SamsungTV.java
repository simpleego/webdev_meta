package com.simple.springex.polymorphism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tv")
public class SamsungTV  implements TV{

    @Autowired
    Speaker speaker;
    int price;

    public SamsungTV() {
        System.out.println("SamsungTV~~~~");
    }

    public SamsungTV(Speaker speaker) {
        System.out.println("===> SamsungTV(2) 객체 생성");
        this.speaker = speaker;
    }

    public void setSpeaker(Speaker speaker) {
        System.out.println("===> setSpeaker() 호출");
        this.speaker = speaker;
    }

    public void setPrice(int price) {
        System.out.println("===> setPrice() 호출");
        this.price = price;
    }

    public void initMethod() {
        System.out.println("객체 초기화 작업 처리..");
    }

    public void powerOn() {
        System.out.println("SamsungTV---전원 켠다.");
    }
    public void powerOff() {
        System.out.println("SamsungTV---전원 끈다.");
    }
    public void volumeUp() {
        speaker.volumeUp();
        //System.out.println("SamsungTV---소리 올린다.");
    }
    public void volumeDown() {
        speaker.volumeDown();
        //System.out.println("SamsungTV---소리 내린다.");
    }

    // 삼성TV의 고유 기능은 추가 가능
}
