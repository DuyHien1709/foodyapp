package com.example.foodapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCancel;
    Button btnConfirm;
    EditText edEmailDK, edPassworDK;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        edEmailDK = (EditText) findViewById(R.id.edEmailDK);
        edPassworDK = (EditText) findViewById(R.id.edPassworDK);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConfirm:
                String EmailDK, PasswordDK;
                EmailDK = edEmailDK.getText().toString();
                PasswordDK = edPassworDK.getText().toString();
                if(TextUtils.isEmpty(EmailDK)){
                    Toast.makeText(this,"Vui lòng nhập Email !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(PasswordDK)){
                    Toast.makeText(this,"Vui lòng nhập mật khẩu !",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(EmailDK,PasswordDK).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Đăng ký không thành công !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.btnCancel:
                finish();
                break;
        }
    }
}