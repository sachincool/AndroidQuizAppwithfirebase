package luthra.harshit.androidquizappwithfirebase;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import luthra.harshit.androidquizappwithfirebase.modal.User;

public class MainActivity extends AppCompatActivity {
        MaterialEditText edtNewusr,edtpassword,edtnewemail; //for sign up
    MaterialEditText username,password; //for singin
    Button sign_up,sign_in;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase setup
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        username=(MaterialEditText)findViewById(R.id.login_username);
        password=(MaterialEditText)findViewById(R.id.login_password);
        sign_in=(Button)findViewById(R.id.login_btn);
        sign_up=(Button)findViewById(R.id.signup_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupDialog();
            }


        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn_function(edtNewusr.getText().toString(),edtpassword.getText().toString());
            }


        });
    }
private void showSignupDialog(){
    AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity.this);
    alertdialog.setTitle("Sign up");
    alertdialog.setMessage("Please fill the information");
    LayoutInflater inflater=this.getLayoutInflater();
    View signup_layout=inflater.inflate(R.layout.sign_up,null);



    edtNewusr=(MaterialEditText)findViewById(R.id.sign_up_username);
    edtpassword=(MaterialEditText)signup_layout.findViewById(R.id.sign_up_password);
    edtnewemail=(MaterialEditText)signup_layout.findViewById(R.id.sign_up_email);
    alertdialog.setView(signup_layout);
    alertdialog.setIcon(R.drawable.ic_group_add_black_24dp);
    alertdialog.setNegativeButton("No",new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    });
alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        final User user =new User(edtNewusr.getText().toString()
                ,edtpassword.getText().toString(),
                edtnewemail.getText().toString());
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             if(   dataSnapshot.child(user.getUserName()).exists())
                Toast.makeText(MainActivity.this,"User already Exists!",Toast.LENGTH_SHORT).show();
             else
                users.child(user.getUserName()).setValue(user);
                Toast.makeText(MainActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dialogInterface.dismiss();
    }
});
    alertdialog.show();
}
    private void signIn_function(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists())
                {
                    if(!user.isEmpty()){
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd))
                            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(MainActivity.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this,"Please Enter Username",Toast.LENGTH_SHORT).show();


                }
                else
                    Toast.makeText(MainActivity.this,"User Doesn't exists !",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
