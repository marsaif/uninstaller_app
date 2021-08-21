package com.example.app_uninstaller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_uninstaller.R;
import com.example.app_uninstaller.adapters.CustomAdapter;
import com.example.app_uninstaller.models.App;
import com.example.app_uninstaller.utils.AppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<App> list ;
    private RecyclerView recyclerView ;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // add Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(getDrawable(R.drawable.ic_delete));
        setSupportActionBar(myToolbar);


        // change the icon of 3dots
        myToolbar.setOverflowIcon(getDrawable(R.drawable.ic_more));



        list = AppUtils.getInstalledApps(this);
       // System.out.println(list);

        customAdapter = new CustomAdapter(list,this) ;
        recyclerView = findViewById(R.id.recyler_view) ;
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search) ;
        SearchView searchView =(SearchView) menuItem.getActionView() ;
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
        ImageView imageViewClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        imageViewClose.setImageResource(R.drawable.ic_cancel);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_sort:
                showSortPopUp() ;
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSortPopUp()
    {
        String[] list = new String[] { "Name" , "Size" , "Date" } ;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        alert.setTitle("Sort by") ;
        alert.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, list[which], Toast.LENGTH_SHORT).show();
            }
        }) ;
        alert.setPositiveButton("DESCENDING", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }) ;

        alert.setNegativeButton("ASCENDING", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }) ;


        alert.show() ;
    }
}