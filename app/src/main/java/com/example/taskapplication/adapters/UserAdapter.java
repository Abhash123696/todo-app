package com.example.taskapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.activity.UserDetailActivity;
import com.example.taskapplication.room.Users;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private final Context context;
    private final List<Users> usersList;


    //** HERE WE MAKE A CONSTRUCTOR
    public UserAdapter(Context context) {
        this.context = context;
        this.usersList = new ArrayList<>();
    }


    public void addUsers(Users users) {
        usersList.add(users);
        notifyDataSetChanged();
    }


    public void clearData(){
        usersList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Users users = usersList.get(position);
        holder.name.setText(users.getName());
        holder.email.setText(users.getEmail());
        holder.address.setText(users.getAddress());
        holder.gender.setText(users.getGender());
        holder.description.setText(users.getDescription());

        //** HERE WE MAKE A ONCLICK LISTENER ON THE ITEM
        holder.linearLayoutUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //** HERE WE PASS THE DATA FROM THE MAIN ACTIVITY TO THE DETAILS ACTIVITY
                Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                intent.putExtra("id",users.getId());
                intent.putExtra("name", users.getName());
                intent.putExtra("email", users.getEmail());
                intent.putExtra("address", users.getAddress());
                intent.putExtra("gender", users.getGender());
                intent.putExtra("description", users.getDescription());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView email;
        private final TextView address;
        private final TextView gender;
        private final TextView description;
        private final LinearLayout linearLayoutUserDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvUsernameShow);
            email = itemView.findViewById(R.id.tvUserEmailShow);
            address = itemView.findViewById(R.id.tvUserAddressShow);
            gender = itemView.findViewById(R.id.tvUserGenderShow);
            description = itemView.findViewById(R.id.tvUserDescriptionShow);
            linearLayoutUserDetails = itemView.findViewById(R.id.linearLayoutUserDetails);
        }
    }
}
