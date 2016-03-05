package j0036474052.android.fer.hr.operations;

import android.content.Context;

import j0036474052.android.fer.hr.R;

/**
 * This class defines a basic division operation in our application.
 */
public class Division implements IMathOperation {

    /**
     * The context of the ppliaction in which this operation is used.
     */
    private Context ctxt;

    /**
     * Constructs a new operation with the defined context.
     * @param ctxt the context of the application in which this operation is used
     */
    public Division(Context ctxt) {

        this.ctxt=ctxt;

    }
    @Override
    public double execute(double x, double y) {
        return x/y;
    }

    @Override
    public String getName() {
        return ctxt.getResources().getString(R.string.divide);
    }
}
