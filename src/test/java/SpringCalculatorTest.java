import org.junit.Assert;
import org.junit.Test;

public class SpringCalculatorTest {

    @Test
    public void add_ZeroOneOrTwoNumbers_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(0), StringCalculatorUtils.add(""));
        Assert.assertEquals(Integer.valueOf(1), StringCalculatorUtils.add("1"));
        Assert.assertEquals(Integer.valueOf(3), StringCalculatorUtils.add("1,2"));
    }

    @Test
    public void add_ManyNumbers_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(7), StringCalculatorUtils.add("0,1,2,3,1"));
    }

    @Test
    public void add_ManyNumbersWithNewLinesSign_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(7), StringCalculatorUtils.add("0\n1,2,3\n1"));
    }

    @Test
    public void add_ManyNumbersWithCustomDelimiter_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(3), StringCalculatorUtils.add("//;\n1;2"));
        Assert.assertEquals(Integer.valueOf(3), StringCalculatorUtils.add("//,\n1,2"));
    }

    @Test
    public void add_NegativeNumbers_ExceptionThrownWithMessage() {
        Exception thrown = Assert.assertThrows(Exception.class, () -> {
            StringCalculatorUtils.add("//;\n-1;-2");
        });
        Assert.assertEquals(thrown.getMessage(), ("negatives not allowed: -1, -2"));


        thrown = Assert.assertThrows(Exception.class, () -> {
            StringCalculatorUtils.add("//:\n1:-2:3");
        });
        Assert.assertEquals(thrown.getMessage(), ("negatives not allowed: -2"));

    }

    @Test
    public void add_NumberBiggerThan1000_IgnoreNumberBiggerThan1000() throws Exception {
        Assert.assertEquals(Integer.valueOf(2), StringCalculatorUtils.add("//;\n1001;2"));
        Assert.assertEquals(Integer.valueOf(0), StringCalculatorUtils.add("//;\n1001"));
    }

    @Test
    public void add_ManyNumbersWithCustomLengthDelimiter_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(6), StringCalculatorUtils.add("//[***]\n1***2***3"));
        Assert.assertEquals(Integer.valueOf(6), StringCalculatorUtils.add("//[;;;]\n1;;;2;;;3"));
    }

    @Test
    public void add_ManyNumbersWithMultipleDelimiter_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(6), StringCalculatorUtils.add("//[*][%]\n1*2%3"));
        Assert.assertEquals(Integer.valueOf(6), StringCalculatorUtils.add("//[;][%][*]\n1;2%3"));
    }

    @Test
    public void add_ManyNumbersWithMultipleDelimiterLongerThanOne_ProperSum() throws Exception {
        Assert.assertEquals(Integer.valueOf(6), StringCalculatorUtils.add("//[**][%]\n1**2%3"));
        Assert.assertEquals(Integer.valueOf(6), StringCalculatorUtils.add("//[;;;][%%%%][****]\n1;;;2%%%%3"));
    }

}
