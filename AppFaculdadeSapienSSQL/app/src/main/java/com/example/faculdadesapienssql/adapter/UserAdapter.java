package com.example.faculdadesapienssql.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.faculdadesapienssql.R;
import com.example.faculdadesapienssql.activity.AddUserActivity;
import com.example.faculdadesapienssql.database.User;
import com.example.faculdadesapienssql.database.UserDAO;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {
    //Attributes UserAdapter
    private List<User> Users;
    private Context mContext;
    private UserDAO userDAO;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        //Attributes Custom ViewHolder
        public TextView id, nome, ra, curso;
        public ImageButton idBtnEdit, idBtnDelete;

        //ClassConstructor
        public CustomViewHolder(View view){
            super(view);
            id = view.findViewById(R.id.txtId);
            nome = view.findViewById(R.id.txtName);
            ra = view.findViewById(R.id.txtRa);
            curso = view.findViewById(R.id.txtCurso);
            idBtnEdit = view.findViewById(R.id.idBtnEdit);
            idBtnDelete = view.findViewById(R.id.idBtnDelete);

        }
    }//end-CustomViewHolder

    //Constructor
    public UserAdapter(List<User> Users, UserDAO userDAO, Context mContext) {
        this.Users = Users;
        this.userDAO = userDAO;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.user_list, viewGroup, false);
        return new CustomViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {
        //Reading an Object from List
        User user = Users.get(i);
        customViewHolder.id.setText(String.valueOf(user.getId()));
        customViewHolder.nome.setText(user.getName());
        customViewHolder.ra.setText(user.getRa());
        customViewHolder.curso.setText(user.getCurso());
        customViewHolder.idBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recorve Objet id from List
                final int id = Users.get(i).getId();
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Atenção")
                        .setMessage("Tem certeza que deseja excluir o usuário " + Users.get(i).getName() + "?")
                        .setNegativeButton("NÃO", null)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Remover from List
                                Users.remove(i);
                                //Updeting DAta set for Recycler View
                                notifyDataSetChanged();
                                //Delete from DB
                                userDAO.Delete(id);
                            }
                        }).create();
                dialog.show();
            }//end_Click
        });//end_Click_Listener
        customViewHolder.idBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recovery Object from list
                User editUser = Users.get(i);
                //Caling update Intent
                Intent intent = new Intent(AddUserActivity.ACTION_ADD_USERS);
                intent = intent.putExtra(AddUserActivity.EXTRA_EDIT_USER, editUser);
                //Stating Intent
                mContext.startActivity(intent);
            }//end _Click
        });//end_Click_Listener
    }
    @Override
    public int getItemCount() {
        return Users.size();
    }

}
