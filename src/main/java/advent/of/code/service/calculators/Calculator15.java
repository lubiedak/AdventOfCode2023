package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculator15 implements TaskCalculator {
    @Override
    public String calculate1(List<String> lines) {
        var codes = String.join("", lines).split(",");
        //String[] codes = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(",");
        int sum = 0;
        for (var code : codes) {
            int current = 0;
            for (char c : code.toCharArray()) {
                current = hash(current, c);
            }
            sum += current;
            System.out.println(code + " " + current);
        }

        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    private int hash(int current, char c) {
        return ((current + c) * 17) % 256;
    }

    @Override
    public int getId() {
        return 15;
    }
}
