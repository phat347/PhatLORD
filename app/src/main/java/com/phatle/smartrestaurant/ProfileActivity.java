package com.phatle.smartrestaurant;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {
    private String mLanguageCode;
    private DrawerLayout mDrawerLayout;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    CallbackManager callbackManager;
    ImageView profile;
    TextView username;
    String IntentUsername;
    String IntentPhotoURL;
    NavigationView navigationView;
    private List<DrawerItem> mListMenu = new ArrayList<>();
    List<RestaurantDrawerItem> mList = new ArrayList<>();
    List<RestaurantDrawerItem> mListBookmark = new ArrayList<>();
    List<NotificationResponse> mListNotification = new ArrayList<>();
    private SOService mService;
    private DrawerItemAdapter mAdapterMenu;


    private Toolbar toolbar;
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;
    ImageView btnMenu;
    ImageView btnMenuClose;

    public InterfacePassDataRestaurant mListener;
    public InterfacePassDataRestaurantHome mListenerFragmentHome;
    public InterfacePassDataRestaurantBookmark mListenerFragmentBookmark;
    public interface InterfacePassDataRestaurant{
        //gửi Data từ API khi load xong vô Fragment RestaurantSearch
        void onPass(List<RestaurantDrawerItem> list);
        void onPassUserName(String username);
        void onPassUserPhoto(String photo);
    }
    public void setListener(InterfacePassDataRestaurant listener){
        mListener = listener;
    }

    public interface InterfacePassDataRestaurantHome{
        //gửi Data từ API khi load xong vô Fragment RestaurantHome
        void onPass(List<RestaurantDrawerItem> list);
        void onPassUserName(String username);
        void onPassUserPhoto(String photo);
    }
    public void setListenerFragmentHome(InterfacePassDataRestaurantHome listener){
        mListenerFragmentHome = listener;
    }
    public interface InterfacePassDataRestaurantBookmark{
        //gửi Data từ API khi load xong vô Fragment RestaurantBookmark
        void onPass(List<RestaurantDrawerItem> list);
    }
    public void setListenerFragmentBookmark(InterfacePassDataRestaurantBookmark listener){
        mListenerFragmentBookmark = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLanguageCode = loadLocale();
        LocaleHelper.setLocale(ProfileActivity.this, mLanguageCode);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        checkNetWork();
        mService = ApiUtils.getSOService();
        toolbar = findViewById(R.id.toolbar);
        final TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        btnMenu = toolbar.findViewById(R.id.btn_menu);

        mTitle.setText(getResources().getString(R.string.tab_home));
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        setupViewPager();
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setupBottomNavBehaviors();
        setupBottomNavStyle();



        addBottomNavigationItems();
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Fragment fm = getSupportFragmentManager().findFragmentById(R.id.viewpager);
                if(fm instanceof SearchFragment)
                {
                    ((SearchFragment) fm).setListener(new SearchFragment.RestoreBottomTab() {
                        @Override
                        public void onRestore() {
                            bottomNavigation.restoreBottomNavigation(true);
                        }
                    });
                }
//                fragment.updateColor(ContextCompat.getColor(MainActivity.this, colors[position]));
                switch (position){
                    case 0:
                        mTitle.setText(getResources().getString(R.string.tab_home));
                        break;
                    case 1:
                        mTitle.setText(getResources().getString(R.string.tab_search));
                        break;
                    case 2:
                        mTitle.setText(getResources().getString(R.string.tab_map));
                        break;
                    case 3:
                        mTitle.setText(getResources().getString(R.string.tab_notification));
                        break;
                    case 4:
                        mTitle.setText(getResources().getString(R.string.tab_bookmark));
                        break;
                    default:
                        break;

                }
                if (!wasSelected)
                    viewPager.setCurrentItem(position);

                return true;
            }
        });
        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();
        setUpUImenu();
        FirebaseUser user = auth.getCurrentUser();

        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
        }

        Intent intent = ProfileActivity.this.getIntent();
        IntentPhotoURL = intent.getStringExtra("PhotoURL");
        IntentUsername = intent.getStringExtra("Username");
        if(user != null)
        {
            IntentUsername = user.getDisplayName();
            username.setText(user.getDisplayName());
        }
        if (IntentUsername != null)
        {
            username.setText(IntentUsername);
        }
        if (IntentPhotoURL == null)
        {
            Picasso.with(ProfileActivity.this)
                    .load(R.drawable.user)
                    .into(profile);
        }
        else if (IntentPhotoURL.equals(""))
        {
            Picasso.with(ProfileActivity.this)
                    .load(R.drawable.user)
                    .into(profile);
        }
        else if (IntentPhotoURL != null)
        {
            Picasso.with(ProfileActivity.this)
                    .load(IntentPhotoURL) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(100, 100)
                    .into(profile);
        }


        loadAnswers();

    }
    public void setUpUImenu(){
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        // set item as selected to persist highlight
//                        menuItem.setChecked(true);
//                        // close drawer when item is tapped
//                        mDrawerLayout.closeDrawers();
//                        if (menuItem.getItemId() == R.id.nav_logout) {
//                            signOut();
//                            LoginManager.getInstance().logOut();
//                            auth.signOut();
//                            Intent backtoLogin = new Intent(ProfileActivity.this,MainActivity.class);
//                            startActivity(backtoLogin);
//                            finish();
//
//                        }
//                        // Add code here to update the UI based on the item selected
//                        // For example, swap UI fragments here
//
//                        return true;
//                    }
//                });
        mListMenu.add(new DrawerItem(getResources().getString(R.string.profile),R.drawable.ic_leftmenu_profile));
        mListMenu.add(new DrawerItem(getResources().getString(R.string.contact),R.drawable.ic_leftmenu_phone));
        mListMenu.add(new DrawerItem(getResources().getString(R.string.app_info),R.drawable.ic_leftmeu_info));
        mListMenu.add(new DrawerItem(getResources().getString(R.string.language),R.drawable.ic_leftmenu_translate));

        mAdapterMenu = new DrawerItemAdapter(mListMenu);

        RecyclerView recyclerView = mDrawerLayout.findViewById(R.id.list_menu_nav);
        recyclerView.setAdapter(mAdapterMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        Button btnLogout = mDrawerLayout.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                            signOut();
                            LoginManager.getInstance().logOut();
                            auth.signOut();
                            Intent backtoLogin = new Intent(ProfileActivity.this,MainActivity.class);
                            startActivity(backtoLogin);
                            finish();
            }
        });
        mAdapterMenu.setListener(new DrawerItemAdapter.InterfaceItemClick() {
            @Override
            public void onItemClick(DrawerItem drawerItem) {
                if(drawerItem.getItemName().equals(getResources().getString(R.string.language)))
                {
//                    mDrawerLayout.closeDrawers();
//                    signOut();
//                    LoginManager.getInstance().logOut();
//                    auth.signOut();
//                    Intent backtoLogin = new Intent(ProfileActivity.this,MainActivity.class);
//                    startActivity(backtoLogin);
//                    finish();
                    showChangelaguageDialog();
                }
                if(drawerItem.getItemName().equals(getResources().getString(R.string.contact)))
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + "012345678"));
                    startActivity(intent);
                }
                if(drawerItem.getItemName().equals(getResources().getString(R.string.app_info)))
                {
                    ErrorDialog dialog = new ErrorDialog(ProfileActivity.this, "Version 1.0", "App này viết cho vui :D",R.drawable.ic_leftmeu_info);
                    dialog.setupOkButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }
            }
        });
//        View headerMenuLayout = navigationView.getHeaderView(0);
        username = mDrawerLayout.findViewById(R.id.tv_username);
        btnMenuClose = mDrawerLayout.findViewById(R.id.close_img);
        btnMenuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
            }
        });
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/open_sans_regular.ttf");
        username.setTypeface(face);
        profile = mDrawerLayout.findViewById(R.id.picture);
        Picasso.with(ProfileActivity.this)
                .load(R.drawable.user)
                .into(profile);
    }
    private void signOut() {
        mGoogleSignInClient.signOut();
    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){


                        IntentPhotoURL = "https://graph.facebook.com/v2.2/" + json.getString("id") + "/picture?height=120&type=normal";
                        IntentUsername = json.getString("name");
                        Picasso.with(ProfileActivity.this)
                                .load("https://graph.facebook.com/v2.2/" + json.getString("id") + "/picture?height=120&type=normal") //extract as User instance method
                                .transform(new CropCircleTransformation())
                                .resize(100, 100)
                                .into(profile);
//                        profile.setProfileId(json.getString("id"));
                        username.setText(json.getString("name"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    public void setupBottomNavBehaviors() {
//        bottomNavigation.setBehaviorTranslationEnabled(false);

        /*
        Before enabling this. Change MainActivity theme to MyTheme.TranslucentNavigation in
        AndroidManifest.
        Warning: Toolbar Clipping might occur. Solve this by wrapping it in a LinearLayout with a top
        View of 24dp (status bar size) height.
         */
        bottomNavigation.setTranslucentNavigationEnabled(false);
    }

    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.
        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.tomato));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));


        // Colors for selected (active) and non-selected items.
//        bottomNavigation.setColoredModeColors(getResources().getColor(R.color.tomato),Color.WHITE);


        //  Enables Reveal effect
        bottomNavigation.setColored(false);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }


    /**
     * Adds (items) {@link AHBottomNavigationItem} to {@link AHBottomNavigation}
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.tab_home), R.drawable.home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.tab_search), R.drawable.search);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.tab_map), R.drawable.ic_placeholder);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.tab_notification), R.drawable.notification);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getResources().getString(R.string.tab_bookmark), R.drawable.pin);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

    }
    private void setupViewPager() {
        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new RestaurantHomeFragment());
        pagerAdapter.addFragments(new SearchFragment());
        pagerAdapter.addFragments(new RestaurantMapFragment());
        pagerAdapter.addFragments(new NotificationFragment());
        pagerAdapter.addFragments(new BookMarkFragment());


        viewPager.setAdapter(pagerAdapter);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }
        if(1==1) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn back để thoát", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
            return;
        }

        super.onBackPressed();
    }
    public void checkNetWork()
    {

        if (!isOnline(this))
        {
            showNetworkAlert(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkNetWork();
                }
            });
        }
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public static void showNetworkAlert(Context context, View.OnClickListener tryAgainClickListener) {
        ErrorDialog dialog = new ErrorDialog(context, "Bạn đang ngoại tuyến!", "Vui lòng kiểm tra kết nối Internet của bạn và thử lại",R.drawable.ic_warning);
        dialog.setupOkButton("Thử lại", tryAgainClickListener);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public void loadAnswers() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mService.getAnswers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RestaurantDrawerItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Phat","onComplete");
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phat","onError");
                        pDialog.dismiss();
                        new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Không thể lấy dữ liệu!")
                                .setContentText("Kiểm tra kết nối mạng hoặc vui lòng thử lại sau")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();

                    }

                    @Override
                    public void onNext(List<RestaurantDrawerItem> restaurantResponses) {
                        mList = restaurantResponses;
                        pDialog.dismiss();
                        if(mListener != null)
                        {
                            mListener.onPass(restaurantResponses);
                            mListener.onPassUserName(IntentUsername);
                            mListener.onPassUserPhoto(IntentPhotoURL);
                        }
                        if(mListenerFragmentHome != null)
                        {
                            mListenerFragmentHome.onPass(restaurantResponses);
                            mListenerFragmentHome.onPassUserName(IntentUsername);
                            mListenerFragmentHome.onPassUserPhoto(IntentPhotoURL);
                        }
                    }
                });

        mService.getBookmarkAnswers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RestaurantDrawerItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Phat","onComplete2");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phat","onError");
                    }

                    @Override
                    public void onNext(List<RestaurantDrawerItem> restaurantDrawerItems) {
                        mListBookmark = restaurantDrawerItems;
                        Log.d("Phat","onNext2");
                        if(mListenerFragmentBookmark != null)
                        {
                            mListenerFragmentBookmark.onPass(restaurantDrawerItems);
                        }
                    }
                });
        mService.getNotificationAnswers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NotificationResponse>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Phat","onComplete3");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phat","onError");
                    }

                    @Override
                    public void onNext(List<NotificationResponse> responseItem) {
                        mListNotification = responseItem;
                        Log.d("Phat","onNext3");

                    }
                });
    }
    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }
    public String loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        return language;
    }
    public void showChangelaguageDialog(){
        final String[] listLanguage = {"Tiếng Việt","English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(getResources().getString(R.string.language));
        int position;
        switch (mLanguageCode){
            case "vi":
                position = 0;
                break;
            case "en":
                position = 1;
                break;
                default:
                    position = 0;
                    break;
        }
        mBuilder.setSingleChoiceItems(listLanguage, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    mLanguageCode = "vi";
                    saveLocale(mLanguageCode);
                    LocaleHelper.setLocale(ProfileActivity.this, mLanguageCode);
                    recreate();
                }
                if (i == 1)
                {
                    mLanguageCode = "en";
                    saveLocale(mLanguageCode);
                    LocaleHelper.setLocale(ProfileActivity.this, mLanguageCode);
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}
