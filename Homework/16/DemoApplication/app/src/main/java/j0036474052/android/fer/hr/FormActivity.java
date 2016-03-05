package j0036474052.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import j0036474052.android.fer.hr.operations.Addition;
import j0036474052.android.fer.hr.operations.Division;
import j0036474052.android.fer.hr.operations.IMathOperation;
import j0036474052.android.fer.hr.operations.Multiplication;
import j0036474052.android.fer.hr.operations.Subtraction;

/**
 * <code>FormActivity</code> represents the first opened window in this simple application.
 * It enables the user to choose a binary mathematical operation and to provide two real numbers
 * on which to perform the chosen operation.
 */
public class FormActivity extends AppCompatActivity {

    /**
     * Input area for the first number.
     */
    private EditText firstNumber;
    /**
     * Input area for the second number.
     */
    private EditText secondNumber;
    /**
     * Button for performing the mathematical operation.
     */
    private Button performButton;
    /**
     * Button for generating a report.
     */
    private Button reportButton;
    /**
     * Output area for the result.
     */
    private TextView label;
    /**
     * Output area for potential errors.
     */
    private TextView errorLabel;
    /**
     * ID of the currently selected mathematical operation.
     */
    private Integer currentOperationKey = R.id.rbAdd;

    /**
     * The name of the last performed operation.
     */
    private String returnedOpName = "";
    /**
     * Report email subject field.
     */
    private static final String SUBJECT = "0036474052: dz report";
    /**
     * Report email recipient field.
     */
    private static final String RECIPIENT = "ana.baotic@infinum.hr";

    /**
     * Intent constant for first number.
     */
    public static final String EXTRAS_VARA="first";
    /**
     * Intent constant for second number.
     */
    public static final String EXTRAS_VARB="second";
    /**
     * Intent constant for the operation being performed.
     */
    public static final String EXTRAS_OPERATION="operation";

    /**
     * This map stores all of our defined operations associated by their IDs.
     */
    public static Map<Integer,IMathOperation> OPERATION_MAP;

    /**
     * Fills our operation map with the defined mathematical operations.
     * @return new filled map of operations
     */
    private Map<Integer, IMathOperation> constructOperations() {
        Map<Integer, IMathOperation> map = new HashMap<>();
        map.put(R.id.rbAdd, new Addition(this.getApplicationContext()));
        map.put(R.id.rbSub, new Subtraction(this.getApplicationContext()));
        map.put(R.id.rbDiv, new Division(this.getApplicationContext()));
        map.put(R.id.rbMul, new Multiplication(this.getApplicationContext()));
        return map;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OPERATION_MAP = constructOperations();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        reportButton = (Button) findViewById(R.id.buttonReport);
        reportButton.setVisibility(View.INVISIBLE);
        performButton = (Button) findViewById(R.id.buttonCalculate);
        performButton.setText(getResources().getString(R.string.add));
        firstNumber = (EditText) findViewById(R.id.etFirstNumber);
        secondNumber = (EditText) findViewById(R.id.etSecondNumber);
        label = (TextView) findViewById(R.id.tvSum);
        errorLabel = (TextView) findViewById(R.id.tvError);


        reportButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{RECIPIENT});
                i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                i.putExtra(Intent.EXTRA_TEXT, FormActivity.this.getBody());
                try {
                    startActivity(Intent.createChooser(i, "Pošalji mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(FormActivity.this, "Trenutno nema email klijenata", Toast.LENGTH_SHORT).show();
                }
            }
        });


        performButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstNumber.getText().toString();
                String second = secondNumber.getText().toString();

                Double firstN;
                Double secondN;

                try {
                    firstN = Double.parseDouble(first);
                } catch (NumberFormatException e) {
                    if (first.equals("")) {
                        label.setText(getResources().getString(R.string.noInput));
                    }
                    return;
                }

                try {
                    secondN = Double.parseDouble(second);
                } catch (NumberFormatException e) {
                    if (second.equals("")) {
                        label.setText(getResources().getString(R.string.noInput));
                    }
                    return;
                }

                Intent i = new Intent(FormActivity.this, CalculusActivity.class);
                i.putExtra(EXTRAS_VARA, firstN);
                i.putExtra(EXTRAS_VARB, secondN);
                i.putExtra(EXTRAS_OPERATION, currentOperationKey);
                startActivityForResult(i, 666);
            }


        });









    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 666  && resultCode == RESULT_OK && data!= null) {
            label.setText(data.getExtras().getDouble(CalculusActivity.RESULT) + "");
            returnedOpName = data.getExtras().getString(CalculusActivity.OP_NAME);
            errorLabel.setText("");
            reportButton.setVisibility(View.VISIBLE);
        }
        if (requestCode == 666  && resultCode == RESULT_CANCELED && data!= null) {
            label.setText(data.getExtras().getDouble(CalculusActivity.RESULT) + "");
            returnedOpName = data.getExtras().getString(CalculusActivity.OP_NAME);
            errorLabel.setText(data.getExtras().getString(CalculusActivity.ERROR));
            reportButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Remembers the chosen operation as the last radio button that was clicked.
     * @param view the radio button that was clicked
     */
    public void onRadioButtonClicked(View view) {
        performButton = (Button) findViewById(R.id.buttonCalculate);
        performButton.setText(OPERATION_MAP.get(view.getId()).getName());
        currentOperationKey=view.getId();
    }

    /**
     * Generates the required report email body.
     * @return the generated default report email body
     */
    private String getBody() {
        String text = "Rezultat operacije "+returnedOpName+" je "+label.getText();
        if (!errorLabel.getText().equals("")) {
            text+="\nIzvođenje je bilo neuspješno, uzrok:"+errorLabel.getText();
        }
        return text;
    }

}
