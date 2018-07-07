package pl.sda.peding;

import org.junit.runners.model.Statement;

public class PendingImplementationStatement extends Statement {

    private final Statement base;

    public PendingImplementationStatement(Statement base) {
        this.base = base;
    }

    @Override
    public void evaluate() throws Throwable {
        try {
            base.evaluate();
        } catch (Throwable e) {
            // Ignore failures, because the implementation is still pending.
            return;
        }
        throw new AssertionError("Unexpected success");
    }

}
