package com.example.chatapp.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemContainerUserBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.viewmodel.message.MessageActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.userViewHolder> {

    private ItemContainerUserBinding binding;
    private List<UserModel>userModelList;
    private Context context;

    public UsersAdapter(List<UserModel> userModelList, Context context) {
        this.userModelList = userModelList;
        this.context=context;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ItemContainerUserBinding.inflate(inflater, parent, false);
        return new UsersAdapter.userViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
       UserModel userModel=userModelList.get(position);
       holder.binding.txtName.setText(userModel.getName());
       holder.binding.txtEmail.setText(userModel.getEmail());
       holder.binding.imageProfile.setImageBitmap(getUserImage(userModel.getImage()));
        holder.itemView.setOnClickListener(view -> {
            Intent intent =new Intent(context, MessageActivity.class);
            intent.putExtra(Constants.KEY_USER_ID,userModel.getUserID());
            intent.putExtra(Constants.KEY_NAME,userModel.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    class userViewHolder extends RecyclerView.ViewHolder{
            private ItemContainerUserBinding binding;
        public userViewHolder(ItemContainerUserBinding binding) {
            super(binding.getRoot());
           this.binding=binding;
        }
    }

    private Bitmap getUserImage(String encodedImage){
        byte[] bytes= Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
//    public userViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
//        super(itemContainerUserBinding.getRoot());
//        binding=itemContainerUserBinding;
//
//
//    }
//    void setUserData(UserModel userModel){
//        binding.txtEmail.setText(userModel.getEmail());
//        binding.txtName.setText(userModel.getName());
//        binding.imageProfile.setImageBitmap(getUserImage(userModel.getImage()));
//
//    }
}
