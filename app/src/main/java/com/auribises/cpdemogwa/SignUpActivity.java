package com.auribises.cpdemogwa;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.editTextPassword)
    EditText eTxtPassword;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    @InjectView(R.id.spinnerCity)
    Spinner spCity;

    @InjectView(R.id.buttonSignUp)
    Button btnSignUp;

    ArrayAdapter<String> adapter;

    User user,rcvUser;

    ContentResolver resolver;
    boolean updateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        resolver = getContentResolver();

        // Create an Object of User
        user = new User();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select City--"); //0
        adapter.add("Ludhiana");
        adapter.add("Chandigarh");
        adapter.add("Delhi");
        adapter.add("Bengaluru");
        adapter.add("Pune");        // n-1

        spCity.setAdapter(adapter);

        spCity.setOnItemSelectedListener(this);
        btnSignUp.setOnClickListener(this);
        rbMale.setOnClickListener(this);
        rbFemale.setOnClickListener(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra(Util.KEY_USER);

        if(updateMode){
            rcvUser = (User)rcv.getSerializableExtra(Util.KEY_USER);

            eTxtName.setText(rcvUser.getName());
            eTxtEmail.setText(rcvUser.getEmail());
            eTxtPassword.setText(rcvUser.getPassword());

            if(rcvUser.getGender().equals("Male")){
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
            }else{
                rbMale.setChecked(false);
                rbFemale.setChecked(true);
            }

            for(int i=0;i<adapter.getCount();i++){
                if(rcvUser.getCity().equals(adapter.getItem(i))){
                    spCity.setSelection(i);
                    break;
                }
            }

            btnSignUp.setText("Update User");
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.buttonSignUp:

                user.setName(eTxtName.getText().toString().trim());
                user.setEmail(eTxtEmail.getText().toString().trim());
                user.setPassword(eTxtPassword.getText().toString().trim());

                insertUser();

                break;

            case R.id.radioButtonMale:
                user.setGender("Male");
                break;

            case R.id.radioButtonFemale:
                user.setGender("Female");
                break;
        }

    }

    void insertUser(){

        ContentValues values = new ContentValues();
        values.put(Util.COL_NAME,user.getName());
        values.put(Util.COL_EMAIL,user.getEmail());
        values.put(Util.COL_PASSWORD,user.getPassword());
        values.put(Util.COL_GENDER,user.getGender());
        values.put(Util.COL_CITY,user.getCity());

        if(!updateMode) {
            Uri uri = resolver.insert(Util.USER_URI, values);
            Toast.makeText(this, user.getName() + " registered successfully with id " + uri.getLastPathSegment(), Toast.LENGTH_LONG).show();
            clearFields();
        }else{
            String where = Util.COL_ID+" = "+rcvUser.getId();
            int i = resolver.update(Util.USER_URI,values,where,null);
            if(i>0){
                Toast.makeText(this,rcvUser.getName()+ " updated...",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String city = adapter.getItem(i);
        //user.city = city;
        user.setCity(city);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void clearFields(){
        eTxtName.setText("");
        eTxtEmail.setText("");
        eTxtPassword.setText("");
        spCity.setSelection(0);
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_signup,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.allUsers){
            Intent intent = new Intent(SignUpActivity.this,AllUsersActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
