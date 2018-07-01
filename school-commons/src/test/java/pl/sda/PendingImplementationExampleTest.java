package pl.sda;

import junitparams.JUnitParamsRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.credentials.pending.PendingImplementation;
import pl.sda.credentials.pending.PendingRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Pending is an alternative to using @Ignore for bypassing tests that are failing
 * due to the functionality not being implemented yet.
 *
 * Pending provides is a JUnit rule and annotation for marking tests as pending.
 * A pending test is expected to fail. Should the test pass the unexpected success
 * will be reported.

 * Add the @PendingImplementation annotation to either a test class or method to
 * mark tests as pending.


 */

@RunWith(JUnitParamsRunner.class)
public class PendingImplementationExampleTest {
    @Rule
    public PendingRule pendingRule = new PendingRule(PendingCategory.class);

    @PendingImplementation
    @Test
    public void
    failingTestShouldPass() {
        fail();
    }

    @PendingImplementation("Work in progress")
    @Test(expected = AssertionError.class)
    public void
    passingTestShouldFail() {
        assertEquals(1, 2);
    }

}
