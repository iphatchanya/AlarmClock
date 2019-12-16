package code;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Problem {
    private String[] operators = {"+", "-"};
    private String problemString = "";
    private Random random = new Random();
    private List<Integer> listOfNumbers =  new ArrayList<Integer>();
    private List<Integer> listOfOperators = new ArrayList<Integer>();
    private int index = 0;

    public String getProblem() {
        int numbers = 4;
        for (int i=0; i < numbers; i++) {
            listOfNumbers.add(random.nextInt(9)+1);
            if(i < numbers - 1) {
                listOfOperators.add(random.nextInt(2));
            }
        }
        for (Integer number:listOfNumbers) {
            problemString += number + " ";
            if (index < listOfOperators.size()) {
                problemString += operators[listOfOperators.get(index)] + " ";
            }
            index++;
        }
        return problemString;

    }
    public String getAnswer()
    {
        int sum = listOfNumbers.get(0);
        for (index = 0; index < listOfOperators.size(); index++)
        {
            switch (listOfOperators.get(index))
            {
                case 0:
                    sum += listOfNumbers.get(index+1);
                    break;
                case 1:
                    sum -= listOfNumbers.get(index+1);
                    break;
                default:
                    break;
            }
        }
        return Integer.toString(sum);
    }
}
