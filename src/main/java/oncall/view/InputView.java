package oncall.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public InputView() {}

    public String inputMonthAndWeekDay() {
        System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
        return Console.readLine();
    }

    // 평일 비상 근무 순번대로 사원 닉네임을 입력하세요> 준팍,도밥,고니,수아,루루,글로,솔로스타,우코,슬링키,참새,도리
    // 휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> 수아,루루,글로,솔로스타,우코,슬링키,참새,도리,준팍,도밥,고니
    public String inputWeekDaysWorkers() {
        System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        return Console.readLine();
    }

    public String inputHolidaysWorkers() {
        System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        return Console.readLine();
    }
}
