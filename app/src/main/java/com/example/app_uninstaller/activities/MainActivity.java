package com.example.app_uninstaller.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_uninstaller.R;
import com.example.app_uninstaller.adapters.CustomAdapter;
import com.example.app_uninstaller.models.App;
import com.example.app_uninstaller.preferences.AppSharedPreference;
import com.example.app_uninstaller.utils.AppUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class MainActivity extends AppCompatActivity {
    private List<App> list ;
    private RecyclerView recyclerView ;
    private CustomAdapter customAdapter;
    private AppUtils appUtils ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // add Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(getDrawable(R.drawable.ic_delete)); // set the logo of toolbar
        setSupportActionBar(myToolbar);


        // change the icon of 3dots
        myToolbar.setOverflowIcon(getDrawable(R.drawable.ic_more));

        appUtils = new AppUtils(this) ;

        // get list of user apps
        list = appUtils.getInstalledApps();

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
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
        String[] listItems = new String[] { "By Name (ASC)" ,
                "By Name (DESC)" ,
                "By Size (ASC)" ,
                "By Size (DESC)" ,
                "By Installed Date (ASC)",
                "By Installed Date (DESC)" } ;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Sort") ;
        alert.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (listItems[which])
                {
                    case "By Name (ASC)" :
                        sortListByNameAsc();
                        break;
                    case "By Name (DESC)" :
                        sortListByNameDesc();
                        break;
                    case "By Size (ASC)" :
                        sortListBySizeAsc();
                        break;
                    case "By Size (DESC)" :
                        sortListBySizeDesc();
                        break;
                    case "By Installed Date (ASC)" :
                        sortListByInstalledDateAsc() ;
                        break;
                    case "By Installed Date (DESC)" :
                        sortListByInstalledDateDesc();
                        break;
                }
                customAdapter.notifyDataSetChanged();
            }
        }) ;

        alert.show() ;
    }

    public void sortListByNameAsc()
    {
        Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    public void sortListByNameDesc()
    {
        Collections.sort(list, (o1, o2) -> o2.getName().compareTo(o1.getName()));
    }

    public void sortListBySizeAsc()
    {
        Collections.sort(list, (Comparator<App>) (o1, o2) -> Double.compare(o1.getAppSize(),o2.getAppSize()));
    }

    public void sortListBySizeDesc()
    {
        Collections.sort(list, (Comparator<App>) (o1, o2) -> Double.compare(o2.getAppSize(),o1.getAppSize()));
    }

    public void sortListByInstalledDateAsc()
    {
        Collections.sort(list, new Comparator<App>() {
            @Override
            public int compare(App o1, App o2) {
                Date dateO1 = null ;
                Date dateO2 = null ;
                try {
                     dateO1 = new SimpleDateFormat("EEE, d MMM yyyy").parse(o1.getInstalledDate()) ;
                     dateO2 = new SimpleDateFormat("EEE, d MMM yyyy").parse(o2.getInstalledDate()) ;
                } catch (ParseException e) {
                    System.out.println("eeee : " + e.getMessage());
                    e.printStackTrace();
                }
                return Long.compare(dateO1.getTime(),dateO2.getTime()) ;
            }
        });
    }

    public void sortListByInstalledDateDesc()
    {
        Collections.sort(list, new Comparator<App>() {
            @Override
            public int compare(App o1, App o2) {
                Date dateO1 = null ;
                Date dateO2 = null ;
                try {
                    dateO1 = new SimpleDateFormat("EEE, d MMM yyyy").parse(o1.getInstalledDate()) ;
                    dateO2 = new SimpleDateFormat("EEE, d MMM yyyy").parse(o2.getInstalledDate()) ;
                } catch (ParseException e) {
                    System.out.println("eeee : " + e.getMessage());
                    e.printStackTrace();
                }
                return Long.compare(dateO2.getTime(),dateO1.getTime()) ;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
        {
            AppSharedPreference appSharedPreference = AppSharedPreference.getInstance(this) ;
            String appPackage = appSharedPreference.getPackageName() ;
            int position = appSharedPreference.getItemPostion() ;
            if (appUtils.isAppDeleted(appPackage))
            {
                list.remove(position);
                customAdapter.notifyItemRemoved(position);
                customAdapter.removeItemFromFullList(appPackage);
            }
            appSharedPreference.removeItem();
            appSharedPreference.removePackageName();
        }
    }
}