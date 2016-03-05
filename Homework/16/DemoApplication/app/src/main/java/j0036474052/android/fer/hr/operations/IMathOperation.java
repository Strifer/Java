package j0036474052.android.fer.hr.operations;

/**
 * This interface defines a single binary mathematical operation within our application.
 */
public interface IMathOperation {
    /**
     * Executes the defined binary mathematical operation on two parameters.
     * @param x the first number
     * @param y the second number
     * @return the result of the operation
     */
    double execute(double x, double y);

    /**
     * Returns the name of this operation.
     * @return the operation's defined name
     */
    String getName();
}
