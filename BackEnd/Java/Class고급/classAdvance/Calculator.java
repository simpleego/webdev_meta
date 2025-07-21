package classAdvance;

public class Calculator {
    private String calculatorName;
    private int operationCount;
    private double lastResult;
    private boolean isOn;

    private static int totalCalculators = 0;
    private static final String MANUFACTURER = "JavaTech";

    public Calculator(String calculatorName) {
        this.calculatorName = calculatorName;
        totalCalculators++;
    }

    // 메서드(함수) 유형 4가지
    // 1. 메서드명(기능, 역할)
    // 2. 기능수행 필요한 값 : 매개변수(어떻게), 멤버변수(무엇을,처리)
    // 3. 반환값: 처리한 결과값의 반환

    // 1. 매개변수 없음, 반환값 없음(void)
    public void turnOn() {
        this.isOn = true;
        System.out.println(calculatorName + " 계산기가 켜졌습니다.");
        System.out.println("제조사:" + MANUFACTURER);
    }

    // 2. 매개변수 있음, 반환값 없음(void)
    public void displayInfo(String operation) {
        if (!isOn) {
            System.out.println(" 계산기가 꺼져있습니다. 먼저 켜주세요.");
            return;
        }

        operationCount++;
        System.out.println("== 계산기 정보 ==");
        System.out.println("수행한 연산" + operation);
        System.out.println("총 연산 횟수:" + operationCount);
        System.out.println("마지막 결과:" + lastResult);
    }

    // 3.매개변수 없음, 반환값 있음
    public String getStatus(){
        return calculatorName + "- 상태 -"+(isOn?"커짐":"꺼짐")+
                ", 연산횟수:"+operationCount +
                ", 마지막 결과:"+lastResult;
    }

    // 4. 매개변수 있음, 반환값 있음
    public double caculate(int a, int b, String operator){
        if (!isOn) {
            System.out.println(" 계산기가 꺼져있습니다. 먼저 켜주세요.");
            return 0.0;
        }

        double result=0.0;
        switch (operator){
            case "+":
                result = a+b;
                break;
            case "-":
                result = a-b;
                break;
            case "*":
                result = a*b;
                break;
            case "/":
                result = (double) a / b;
                break;
            default:
                System.out.println("지원하지 않는 연산입니다.");
                return lastResult;
        }

        lastResult = result;
        operationCount++;
        return result;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator("공학계산기");
        Calculator calculator2 = new Calculator("일반계산기");
        calculator.turnOn();

        System.out.println(calculator.calculatorName);
        calculator.displayInfo("나눗셈 연산");

        String msg = calculator.getStatus();
        System.out.println(calculator.getStatus());
        System.out.println(msg+"문자열을 처리한다.");

        calculator2.turnOn();
        double result = calculator2.caculate(10,20,"+");
        System.out.println(result);


    }
}
