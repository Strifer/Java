package j0036474052.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import j0036474052.android.fer.hr.operations.IMathOperation;

/**
 * <code>CalculusActivity</code> represents the window opened after the user executes the
 * chosen <code>IMathOperation</code>. A basic test is performed if the result is greater than 34.
 */
public class CalculusActivity extends AppCompatActivity {
    /**
     * Text area for the result.
     */
    private TextView label;
    /**
     * Button for returning the result back to the main activity.
     */
    private Button returnButton;
    /**
     * Intent constant for the result.
     */
    public static final String RESULT = "result";
    /**
     * Intent constant for the error.
     */
    public static final String ERROR = "error";
    /**
     * Intent constant for the operation's name.
     */
    public static final String OP_NAME = "name";
    /**
     * The result of the operation is stored here.
     */
    private double result;
    /**
     * Errors are recorded here if they happen.
     */
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        label = (TextView) findViewById(R.id.tvCalcLabel);
        returnButton = (Button) findViewById(R.id.backButton);

        Bundle bundy = getIntent().getExtras();

        double first = bundy.getDouble(FormActivity.EXTRAS_VARA);
        double second = bundy.getDouble(FormActivity.EXTRAS_VARB);
        final IMathOperation op = FormActivity.OPERATION_MAP.get(bundy.getInt(FormActivity.EXTRAS_OPERATION));



        String booly;
        result = op.execute(first, second);
        booly = result > 34 ? "veći je" : "nije veći";
        label.setText(String.format("Rezultat je %.4f i %s od 34", result, booly));
        error = "";
        if (Double.valueOf(result).isInfinite()) {
            error = getResources().getString(R.string.ERROR_DivByZero);
        }

        if (Double.valueOf(result).isNaN()) {
            error = getResources().getString(R.string.ERROR_NaN);
        }


        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalculusActivity.this, FormActivity.class);
                i.putExtra(RESULT, result);
                i.putExtra(OP_NAME, op.getName());
                if (error.equals("")) {
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    setResult(RESULT_CANCELED, i);
                    i.putExtra(ERROR, error);
                    finish();
                }
            }

        });







    }
}
