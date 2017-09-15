package com.azrap.tipster;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private EditText tbTotal, tbNumPeople, tbOther;
    private RadioGroup radioGroup;
    private RadioButton radioOther;
    private TextView txtTipAmount, txtPerson, txtTotal;
    private Button btnReset, btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tbTotal = (EditText)findViewById(R.id.tbTotal);
        tbNumPeople = (EditText)findViewById(R.id.tbNumPeople);
        tbOther = (EditText)findViewById(R.id.tbOther);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioOther = (RadioButton)findViewById(R.id.radioOther);
        btnReset = (Button)findViewById(R.id.btnReset);
        btnCalculate = (Button)findViewById(R.id.btnCalculate);
        txtTipAmount = (TextView)findViewById(R.id.txtTipAmount);
        txtPerson = (TextView)findViewById(R.id.txtPerson);
        txtTotal= (TextView)findViewById(R.id.txtTotal);

        tbOther.setEnabled(false);

        btnReset.setOnClickListener(klik);
        btnCalculate.setOnClickListener(klik);

        tbTotal.setOnKeyListener(key1);
        tbNumPeople.setOnKeyListener(key1);
        tbOther.setOnKeyListener(key1);

        radioOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tbOther.setEnabled(isChecked);
                btnCalculate.setEnabled(hasInput());
            }
        });

    }

    View.OnKeyListener key1 = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            btnCalculate.setEnabled(hasInput());
            return false;
        }
    };

    private boolean hasInput(){

        return tbTotal.getText().length() > 0
                && tbNumPeople.getText().length() > 0
                && (!radioOther.isChecked() || tbOther.getText().length() > 0);
    }

    private View.OnClickListener klik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             switch (v.getId()){

                 case R.id.btnReset: btnReset_OnClick(); break;
                 case R.id.btnCalculate: btnCalculate_OnClick(); break;
             }
        }
    };


    public void btnReset_OnClick() {

        tbTotal.setText("");
        tbNumPeople.setText("");
        tbOther.setText("");
        radioGroup.check(R.id.rb1);
    }

    public void btnCalculate_OnClick() {

        Double totalAmount = Double.parseDouble(tbTotal.getText().toString());
        Double totalPeople = Double.parseDouble(tbNumPeople.getText().toString());

        int id = radioGroup.getCheckedRadioButtonId();
        double tipPercent = 0;
        switch(id){

            case R.id.rb1:
                tipPercent = 15;
                break;
            case R.id.rb2:
                tipPercent = 20;
                break;
            case R.id.radioOther:
                tipPercent = Double.parseDouble(tbOther.getText().toString());
                break;
        }

        boolean error = false;

        if(totalAmount<0){

            error = true;
        }

        if(totalPeople<0){

            error = true;
        }

        if(!error){

            Double tipAmount = totalAmount * tipPercent / 100;
            Double totalPay = totalAmount + tipAmount;
            Double perPerson = totalPay / totalPeople;

            txtTipAmount.setText(tipAmount.toString());
            txtPerson.setText(perPerson.toString());
            txtTotal.setText(totalPay.toString());
        }


    }
}
