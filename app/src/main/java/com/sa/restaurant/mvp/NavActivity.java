package com.sa.restaurant.mvp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.sa.restaurant.MainActivity;
import com.sa.restaurant.R;
import com.sa.restaurant.mvp.home.FavRestaurantfragment;
import com.sa.restaurant.mvp.home.MapFragment;
import com.sa.restaurant.mvp.home.RestaurantFragment;
import com.sa.restaurant.mvp.login.LoginFragment;
import com.sa.restaurant.mvp.weather.WeatherFragment;


public class NavActivity extends AppCompatActivity
{
    private DrawerLayout mDrawer;

    private Toolbar toolbar;
    private  boolean isVisible=false;
    FragmentManager fragmentManager;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nav);
        if(savedInstanceState==null) {
            fragmentManager = getFragmentManager();
            RestaurantFragment fragment = (RestaurantFragment) fragmentManager.findFragmentById(R.id.fragment_restaurant_list_layout);
            if (fragment == null)
            {
                //do your fragment creation
                fragmentManager.beginTransaction().add(R.id.activity_nav_content_container,new RestaurantFragment()).commit();
            }
            else
            {
                fragmentManager.beginTransaction().add(R.id.activity_nav_content_container,fragment).commit();
            }
        }
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.activity_nav_nvView);
        setupDrawerContent(nvDrawer);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);



    }



    private ActionBarDrawerToggle setupDrawerToggle()
    {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setCheckedItem(R.id.nav_drawer_view_item_restaurant);
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });
    }

    private void selectDrawerItem(MenuItem menuItem)
    {
        Fragment loadFragment = null;


        switch(menuItem.getItemId()) {

            case R.id.nav_drawer_view_item_restaurant:

                loadFragment=RestaurantFragment.newInstance();

                break;

            case R.id.nav_drawer_view_item_fav_restaurant:

               loadFragment = FavRestaurantfragment.newInstance();

                break;

            case R.id.nav_drawer_view_item_weather:

                loadFragment = WeatherFragment.newInstance();

                break;

            default:
                loadFragment = RestaurantFragment.newInstance();

        }
        getFragmentManager().beginTransaction().replace(R.id.activity_nav_content_container, loadFragment)
                .commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId())
       {
           case R.id.menu_main_action_logout:
               final SharedPreferences loginPref = getSharedPreferences(MainActivity.prefName, Context.MODE_PRIVATE);
               final SharedPreferences.Editor editor = loginPref.edit();
               if(loginPref.getString(MainActivity.value,"").equals("NO"))
               {

                   LoginManager.getInstance().logOut();

               }
               editor.remove(MainActivity.value);
               editor.apply();
               getFragmentManager().beginTransaction().add(R.id.drawer_layout,new LoginFragment()).commit();
               break;
           case R.id.home:
               mDrawer.openDrawer(GravityCompat.START);  // OPEN DRAWER
               return true;

           case R.id.menu_main_action_map:

           if(!isVisible){

               item.setIcon(R.drawable.ic_view_list);
               getFragmentManager().beginTransaction()
                           .replace(R.id.activity_nav_content_container,new MapFragment())
                           .commit();

               isVisible=true;
           }else{

               item.setIcon(R.drawable.ic_map);
               getFragmentManager().beginTransaction()
                       .replace(R.id.activity_nav_content_container,new RestaurantFragment())

                       .commit();

               isVisible=false;
           }




       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }
}
