package com.chaitanya.quicksoft.glutton;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.chaitanya.quicksoft.glutton.databinding.ActivityHomeScreenBinding;
import com.chaitanya.quicksoft.glutton.viewModels.HomeScreenViewModel;
import com.chaitanya.response.HomeResponse;
import com.chaitanya.response.RestaurantsItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.viewpagerindicator.CirclePageIndicator;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Home_screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Home_CustomAdapter_Item_Click,NetworkResponseInterface {

    private AppBarConfiguration mAppBarConfiguration;
    SearchView Restrnt_search;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.one,R.drawable.two,R.drawable.three};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    VillageListRecyclerCustomAdapter villageListRecyclerCustomAdapter;
    RecyclerView home_recycler_view;
    VillageConfigCustomAdapterModel villageConfigCustomAdapterModel;
    ArrayList<VillageConfigCustomAdapterModel> recycler_model_list;
    ActivityHomeScreenBinding activityHomeScreenBinding;
    HomeScreenViewModel homeScreenViewModel;
    List<RestaurantsItem> restaurantsItems = new ArrayList<>();
    NetworkCheck networkCheck;
    NetworkResponseInterface networkResponseInterface;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenViewModel = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        activityHomeScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_screen);
        activityHomeScreenBinding.setLifecycleOwner(this);
        activityHomeScreenBinding.setHomescreenviewmodel(homeScreenViewModel);

        networkResponseInterface = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Restrnt_search = findViewById(R.id.Restrnt_search);
        home_recycler_view = (RecyclerView) findViewById(R.id.home_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Home_screen.this, 1);
        home_recycler_view.setLayoutManager(gridLayoutManager);
        recycler_model_list = new ArrayList<>();

        init();


        Restrnt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Restrnt_search.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (villageListRecyclerCustomAdapter != null) {

                    villageListRecyclerCustomAdapter.getFilter().filter(s);
                }
                return false;
            }
        });

    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(Home_screen.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Restrnt_search.setQuery("", false);
        Restrnt_search.clearFocus();
        networkCheck = new NetworkCheck(connectivityManager, networkResponseInterface, Home_screen.this);
        networkCheck.CheckNetworkState(connectivityManager, Glutton_Constants.LOADRESTAURANTS);

    }

    private void getRestaurants() {

        homeScreenViewModel.gethomerestrntsdata(1).observe(this, new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {

                recycler_model_list = new ArrayList<>();
                restaurantsItems = homeResponse.getRestaurants();
                if(restaurantsItems!=null) {
                    for (RestaurantsItem restaurantsItem : restaurantsItems) {

                        villageConfigCustomAdapterModel = new VillageConfigCustomAdapterModel(restaurantsItem.getName(),
                                restaurantsItem.getAddress(), restaurantsItem.getImage(), "", restaurantsItem.getStatus(), restaurantsItem.getRestId(),restaurantsItem.getDescription());
                        recycler_model_list.add(villageConfigCustomAdapterModel);
                    }
                    villageListRecyclerCustomAdapter = new VillageListRecyclerCustomAdapter(Home_screen.this, recycler_model_list, Home_screen.this);
                    home_recycler_view.setAdapter(villageListRecyclerCustomAdapter);
                    villageListRecyclerCustomAdapter.notifyDataSetChanged();
                }

            }
        });
        activityHomeScreenBinding.getHomescreenviewmodel().getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                recycler_model_list = new ArrayList<>();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.MyOrders:
                Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.help:
                Toast.makeText(getApplicationContext(), "privacy_policy", Toast.LENGTH_SHORT).show();
                break;


        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void Village_List_Custom_Adapter_Item_click(String restaurant_name, String address, String Image, String offers,int restaurant_id,String restaurant_descrp) {

        Intent selected_restrnt = new Intent(Home_screen.this,Food_Items.class);
        selected_restrnt.putExtra("selected_restrnt",restaurant_name);
        selected_restrnt.putExtra("selected_restrnt_id",restaurant_id);
        selected_restrnt.putExtra("restaurant_address",address);
        selected_restrnt.putExtra("restaurant_image",Image);
        selected_restrnt.putExtra("restaurant_offer",offers);
        selected_restrnt.putExtra("restaurant_descrp",restaurant_descrp);
        startActivity(selected_restrnt);

    }

    @Override
    public void IsConnected(Boolean isconnected, int calling_request_from) {
        if(isconnected){

            switch (calling_request_from){

                case Glutton_Constants.LOADRESTAURANTS :

                    getRestaurants();

                    break;

            }
        }else {
            Toast.makeText(getApplicationContext(),"Please Enable internet",Toast.LENGTH_LONG).show();
        }
    }
}
