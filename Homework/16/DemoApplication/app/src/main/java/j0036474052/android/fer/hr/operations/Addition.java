package j0036474052.android.fer.hr.operations;

import android.content.Context;
import android.content.res.Resources;

import j0036474052.android.fer.hr.R;

/**
 * This class defines a basic addition operation in our application.
 */
public class Addition implements IMathOperation {
    /**
     * The context of the ppliaction in which this operation is used.
     */
    private Context ctxt;

    /**
     * Constructs a new operation with the defined context.
     * @param ctxt the context of the application in which this operation is used
     */
    public Addition(Context ctxt) {
        this.ctxt=ctxt;
    }

    @Override
    public double execute(double x, double y) {
        return x+y;
    }

    @Override
    public String getName() {
        return this.ctxt.getResources().getString(R.string.add);
    }
}
