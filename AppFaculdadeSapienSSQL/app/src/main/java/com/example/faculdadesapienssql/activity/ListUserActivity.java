package com.example.faculdadesapienssql.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.faculdadesapienssql.R;
import com.example.faculdadesapienssql.adapter.UserAdapter;
import com.example.faculdadesapienssql.database.User;
import com.example.faculdadesapienssql.database.UserDAO;
import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {

    //User Recycleview
    private RecyclerView recyclerView;
    //Adapter to Recycler View
    private UserAdapter UserAdapter;
    //List of User
    private List<User> userList;
    //List filter
    private List<User> userListFilter = new ArrayList<>();
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        recyclerView = findViewById(R.id.recycleView);
        //Instanciating Dao
        userDAO = new UserDAO(this);

        //Setting the List in Adapter
        UserAdapter = new UserAdapter(userListFilter, userDAO, this);
        RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
        //Configuring the RecyclerView
        recyclerView.setLayoutManager(myLayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(UserAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.app_bar_search).getActionView();
        //Criando o Evento do btnPesquisa
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                FindUser(s);
                return true;
            }
        });


        return true;
    }

    private void FindUser(String userName) {
        //Using 2 list
        userListFilter.clear();
        for (User user: userList){
            if (user.getName().toLowerCase().contains(userName.toLowerCase())){
                userListFilter.add(user);
            }
        }
        //To Notify Data Update
        UserAdapter.notifyDataSetChanged();

    }

    public void addUser (MenuItem item){
        //Calling Add User by intent
        Intent intentObjs = new Intent(AddUserActivity.ACTION_ADD_USERS);
        //Stant intent
        startActivity(intentObjs);
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        userList = userDAO.getAll();
        userListFilter.clear();
        userListFilter.addAll(userList);
        //To Notify Data Update
        UserAdapter.notifyDataSetChanged();
    }
}
