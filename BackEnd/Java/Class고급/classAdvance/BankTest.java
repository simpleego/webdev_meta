package classAdvance;

import classex.TV;

class Bank{
    double getInterestRate(){
        return 0.0;
    }
}

class BadBank extends Bank{
    double getInterestRate(){
        return 10.0;
    }
}

class NormalBank extends Bank{
    double getInterestRate(){
        return 5.0;
    }
}

class GoodBank extends Bank{
    double getInterestRate(){
        return 1.0;
    }
}


public class BankTest {
    public static void main(String[] args) {
        BadBank badBank = new BadBank();
        NormalBank normalBank = new NormalBank();
        Bank goodBank = new GoodBank();

        System.out.println(badBank.getInterestRate());
        System.out.println(goodBank.getInterestRate());
        System.out.println(normalBank.getInterestRate());
    }
}
