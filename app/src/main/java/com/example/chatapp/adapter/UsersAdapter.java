package com.example.chatapp.adapter;

import android.content.Context;
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
import com.example.chatapp.model.UserModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.userViewHolder> {

    private final List<UserModel>userModelList;
    private final Context context;

    public UsersAdapter(List<UserModel> userModelList, Context context) {
        this.userModelList = userModelList;
        this.context=context;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new userViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
       UserModel userModel=userModelList.get(position);
       holder.txtName.setText(userModel.getName());
       holder.txtEmail.setText(userModel.getEmail());
       holder.roundedImageView.setImageBitmap(getUserImage(userModel.getImage()));

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    class userViewHolder extends RecyclerView.ViewHolder{
        TextView txtEmail,txtName;
        RoundedImageView roundedImageView;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmail=itemView.findViewById(R.id.txtEmail);
            txtName=itemView.findViewById(R.id.txtName);
            roundedImageView=itemView.findViewById(R.id.imageProfile);
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
