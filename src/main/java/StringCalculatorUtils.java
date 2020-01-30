import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringCalculatorUtils {

    public static Integer add(String input) throws Exception {

        List<String> delimiters = getDelimiters(input);

        String InputNumbersWithComma = convertDelimitersToComma(delimiters, input);

        List<Integer> numbers = convertStringToListOfInteger(InputNumbersWithComma);

        checkForNegativeNumbers(numbers);

        removeNumbersHigherThan1000(numbers);

        Integer sum = numbers.stream().reduce(0, Integer::sum);

        return sum;
    }

    private static void checkForNegativeNumbers(List<Integer> numbers) throws Exception {

        StringBuilder negativeNumbers = new StringBuilder();

        for (Integer number : numbers) {
            if (number < 0) {
                negativeNumbers.append(" ");
                negativeNumbers.append(number);
                negativeNumbers.append(",");
            }
        }
        if (negativeNumbers.length() > 0) {
            negativeNumbers.deleteCharAt(negativeNumbers.length() - 1);
            throw new Exception("negatives not allowed:" + negativeNumbers.toString());
        }

    }

    private static void removeNumbersHigherThan1000(List<Integer> numbers) {
        Iterator<Integer> numbersIterator = numbers.iterator();

        while (numbersIterator.hasNext()) {
            Integer number = numbersIterator.next();
            if (number > 1000) {
                numbersIterator.remove();
            }
        }

    }

    public static List<String> getDelimiters(String input) {
        ArrayList<String> delimiters = new ArrayList<>();

        delimiters.add("\n");

        if (input.contains("//")) {
            for (int i = 2; i < input.length(); i++) {

                if (input.charAt(i) == '[') {
                    StringBuilder delimeter = new StringBuilder();
                    i++;
                    while (input.charAt(i) != ']') {
                        delimeter.append(input.charAt(i));
                        i++;
                    }
                    delimiters.add(delimeter.toString());
                } else {
                    delimiters.add(String.valueOf(input.charAt(i)));
                    break;
                }
            }

        }
        return delimiters;
    }

    public static String convertDelimitersToComma(List<String> delimiters, String input) {

        String inputWithStandardDelimeter = input;

        for (String delimeter : delimiters) {
            inputWithStandardDelimeter = inputWithStandardDelimeter.replace(delimeter, ",");
        }

        inputWithStandardDelimeter = inputWithStandardDelimeter.replaceAll("[\\/\\[\\]]", "");

        return inputWithStandardDelimeter;
    }


    public static List<Integer> convertStringToListOfInteger(String stringNumbers) {
        return new ArrayList<Integer>(
                Arrays.asList(
                        Arrays.stream(stringNumbers.split(","))
                                .filter(s -> s.length() > 0)
                                .map(Integer::parseInt)
                                .toArray(Integer[]::new)));
    }
}
