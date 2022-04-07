package com.example.chatapp.viewmodel.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatapp.databinding.ActivityMessageBinding;
import com.example.chatapp.model.SendMessage;
import com.example.chatapp.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back();

        firebaseAuth=FirebaseAuth.getInstance();

        Intent intent =getIntent();
        binding.textName.setText(intent.getStringExtra(Constants.KEY_NAME));
        String userID=intent.getStringExtra(Constants.KEY_USER_ID);

        String userSend=firebaseAuth.getCurrentUser().getUid();


        binding.layoutSend.setOnClickListener(view -> {
            String msg=binding.inputMessage.getText().toString().trim();
            sendMessage(userSend,userID,msg);
        });

    }

    private void back(){
        binding.imgBack.setOnClickListener(view -> {onBackPressed();});
    }

    private void sendMessage(String sender,String receiver,String message){
        SendMessage sendMessage =new SendMessage(sender,receiver,message);
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection(Constants.KEY_COLLECTION_CHAT).add(sendMessage);
    }
}