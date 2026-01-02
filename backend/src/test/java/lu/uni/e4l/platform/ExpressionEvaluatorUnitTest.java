package lu.uni.e4l.platform;

import lu.uni.e4l.platform.service.ExpressionEvaluator;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionEvaluatorUnitTest {
    @Test
    public void tokenize_addsSpacesAroundParenthesis() {
        List<String> tokens = ExpressionEvaluator.tokenize("2.5*(x+3)");
        assertEquals(asList("2.5*", "(", "x+3", ")"), tokens);
    }

    @Test
    public void evaluate_expressionWithVariablesAndFunctions() {
        // (2 + x) * floor(y) where x=3 and y=4.9 => (5) * 4 = 20
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "3");
        vars.put("y", "4.9");

        Double result = ExpressionEvaluator.evaluate("( 2 + x ) * floor ( y )", vars);
        assertEquals(20d, result, 0.0000001);
    }

    public void evaluate_throwsOnUnknownVariable() {
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "1");

        // 'unknownVar' is not in operators, not a number, not provided in vars -> should throw
        try {
            ExpressionEvaluator.evaluate("x + unknownVar", vars);
        } catch (RuntimeException e) {
            assertTrue("Should mention invalid token", e.getMessage() != null && e.getMessage().contains("invalid token"));
            return;
        }

        throw new AssertionError("Expected ExpressionEvaluator to throw for unknown variable");
    }

    // @Test
    // public void dummy_failingTestCase() {
    //     assertTrue("Dummy failing test case (intentional)", false);
    // }

}
