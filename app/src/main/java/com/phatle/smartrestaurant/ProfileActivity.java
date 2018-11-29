package com.phatle.smartrestaurant;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.bumptech.glide.request.RequestOptions;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.Integer.parseInt;

public class ProfileActivity extends AppCompatActivity {
    private String mLanguageCode;
    private DrawerLayout mDrawerLayout;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    CallbackManager callbackManager;
    ImageView profile;
    TextView username,userEmail;
    String IntentUsername;
    String IntentPhotoURL;
    String IntentEmail;
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
    private boolean notificationVisible = false;

    ProgressDialog bar;
    String URLDownload;
    public InterfacePassDataRestaurant mListener;
    public InterfacePassDataRestaurantHome mListenerFragmentHome;
    public InterfacePassDataRestaurantBookmark mListenerFragmentBookmark;
    public InterfacePassDataRestaurantNotification mListenerNotification;
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
    public interface InterfacePassDataRestaurantNotification{
        //gửi Data từ API khi load xong vô Fragment RestaurantNotification
        void onPass(List<NotificationResponse> list);
    }
    public void setListenerFragmentNotification(InterfacePassDataRestaurantNotification listener){
        mListenerNotification = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLanguageCode = loadLocale();
        if(mLanguageCode.equals(""))
        {
            Locale current = getResources().getConfiguration().locale;
            mLanguageCode = current.getLanguage();
        }

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
                        int lastItemPos = bottomNavigation.getItemsCount() - 2;
                        if (notificationVisible && position == lastItemPos)
                            bottomNavigation.setNotification(new AHNotification(), lastItemPos);
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
        IntentEmail = intent.getStringExtra("Email");
        if(user != null)
        {
            if(user.getPhotoUrl()!= null)
            {
                IntentPhotoURL = user.getPhotoUrl().toString();
            }
            IntentUsername = user.getDisplayName();
            username.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
        }
        if(IntentEmail != null)
        {
            userEmail.setText(IntentEmail);
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
//            Picasso.with(ProfileActivity.this)
//                    .load(IntentPhotoURL) //extract as User instance method
//                    .transform(new CropCircleTransformation())
////                    .resize(100, 100)
//                    .into(profile);
            GlideApp.with(ProfileActivity.this)
                    .load(IntentPhotoURL)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profile);
        }


        loadAnswers();
        createFakeNotification();

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
            @SuppressLint("RestrictedApi")
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
                    ErrorDialog dialog = new ErrorDialog(ProfileActivity.this, "Version " + getString(R.string.app_version), "App này viết cho vui :D\n\nApp được viết bởi PhatLORD",R.drawable.ic_leftmeu_info);
                    dialog.setupOkButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                }
                if(drawerItem.getItemName().equals(getResources().getString(R.string.profile)))
                {
                    Intent profileIntent = new Intent(ProfileActivity.this,AccountActivity.class);
                    profileIntent.putExtra("Username",IntentUsername);
                    profileIntent.putExtra("PhotoURL",IntentPhotoURL);
                    profileIntent.putExtra("Email",IntentEmail);
                    profileIntent.putExtra("ListBookmark", (Serializable) mListBookmark);
                    Pair[] pair = new Pair[3];

                    pair[0] = new Pair<View,String>(profile,"img_transition");
                    pair[1] = new Pair<View,String>(username,"username_transition");
                    pair[2] = new Pair<View,String>(userEmail,"email_transition");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ProfileActivity.this,pair);
                    startActivityForResult(profileIntent,123,options.toBundle());

                }
            }
        });
//        View headerMenuLayout = navigationView.getHeaderView(0);
        username = mDrawerLayout.findViewById(R.id.tv_username);
        userEmail = mDrawerLayout.findViewById(R.id.tv_email);
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
        profile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(ProfileActivity.this,AccountActivity.class);
                profileIntent.putExtra("Username",IntentUsername);
                profileIntent.putExtra("PhotoURL",IntentPhotoURL);
                profileIntent.putExtra("Email",IntentEmail);
                profileIntent.putExtra("ListBookmark", (Serializable) mListBookmark);
                Pair[] pair = new Pair[3];

                pair[0] = new Pair<View,String>(profile,"img_transition");
                pair[1] = new Pair<View,String>(username,"username_transition");
                pair[2] = new Pair<View,String>(userEmail,"email_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ProfileActivity.this,pair);
                startActivityForResult(profileIntent,123,options.toBundle());
            }
        });
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

                        IntentEmail = json.getString("email");
                        IntentPhotoURL = "https://graph.facebook.com/v2.2/" + json.getString("id") + "/picture?height=120&type=normal";
                        IntentUsername = json.getString("name");
                        Picasso.with(ProfileActivity.this)
                                .load("https://graph.facebook.com/v2.2/" + json.getString("id") + "/picture?height=120&type=normal") //extract as User instance method
                                .transform(new CropCircleTransformation())
                                .resize(100, 100)
                                .into(profile);
//                        profile.setProfileId(json.getString("id"));
                        username.setText(json.getString("name"));
                        userEmail.setText(IntentEmail);
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
                        if(mListenerNotification != null)
                        {
                            mListenerNotification.onPass(responseItem);
                        }
                    }
                });
        mService.getVersion().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Phat","onErrorVersion");
                    }

                    @Override
                    public void onNext(VersionResponse versionResponse) {
                        Log.d("Phat","onNextVersion");

                        URLDownload = versionResponse.getUrl();
                        String currentVersion = getString(R.string.app_version);
                        String[]CurrentVersion = currentVersion.split("\\.");
                        String[]NewVersion = versionResponse.getNewVersion().split("\\.");
                        int CurrentMajor = parseInt(CurrentVersion[0]);
                        int CurrentMinor = parseInt(CurrentVersion[1]);
                        int CurrentPatch = parseInt(CurrentVersion[2]);
                        int CHMajor = parseInt(NewVersion[0]);
                        int CHMinor = parseInt(NewVersion[1]);
                        int CHPatch = parseInt(NewVersion[2]);
                        if (CHMajor > CurrentMajor)
                        {
                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getString(R.string.update_available))
                                    .setContentText(getString(R.string.update_phase1) +" "+ versionResponse.getNewVersion() +" "+ getString(R.string.update_phase2))
                                    .setConfirmText(getString(R.string.update_btn))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            new DownloadNewVersion().execute();
                                        }
                                    })
                                    .setCancelText(getString(R.string.close_btn))
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }else if (CHMinor > CurrentMinor && CHMajor >= CurrentMajor)
                        {
                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getString(R.string.update_available))
                                    .setContentText(getString(R.string.update_phase1) +" "+ versionResponse.getNewVersion() +" "+ getString(R.string.update_phase2))
                                    .setConfirmText(getString(R.string.update_btn))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            new DownloadNewVersion().execute();
                                        }
                                    })
                                    .setCancelText(getString(R.string.close_btn))
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }
                        else if (CHPatch > CurrentPatch && CHMinor >= CurrentMinor && CHMajor >= CurrentMajor)
                        {
                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getString(R.string.update_available))
                                    .setContentText(getString(R.string.update_phase1) +" "+ versionResponse.getNewVersion() +" "+ getString(R.string.update_phase2))
                                    .setConfirmText(getString(R.string.update_btn))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            new DownloadNewVersion().execute();
                                        }
                                    })
                                    .setCancelText(getString(R.string.close_btn))
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }
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
                    position = 1;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                String resultURL = data.getStringExtra("IntentPhotoURL");
//                Picasso.with(ProfileActivity.this)
//                        .load(resultURL) //extract as User instance method
//                        .transform(new CropCircleTransformation())
////                        .resize(100, 100)
//                        .into(profile);
                GlideApp.with(ProfileActivity.this)
                        .load(resultURL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profile);


            }
        }
    }
    private void createFakeNotification() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText("6")
                        .setBackgroundColor(Color.RED)
                        .setTextColor(Color.WHITE)
                        .build();
                // Adding notification to last item.

                bottomNavigation.setNotification(notification, bottomNavigation.getItemsCount() - 2);

                notificationVisible = true;
            }
        }, 1000);
    }
    class DownloadNewVersion extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(ProfileActivity.this);
            bar.setCancelable(false);

            bar.setMessage("Downloading...");

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if(progress[0]>99){

                msg="Finishing... ";

            }else {

                msg="Downloading... "+progress[0]+"%";
            }
            bar.setMessage(msg);

        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if(result){

                Toast.makeText(getApplicationContext(),"Download Completed",
                        Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(getApplicationContext(),"Error: Try Again",
                        Toast.LENGTH_SHORT).show();


            }

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {

//                String APK_URL = "http://upfile.vn/download/guest/A~jmAqXmNVye/odXtKCZC7VXr/PlWKgP7iZBzi/LlpyLCAyaxaL/c324f44360218d9b0/1543514020/60722f649d5a1c1a4b132161ae5bae86bb97ac86f02d148ac/app-debug.apk";
                URL url = new URL(URLDownload);

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory()+"/Download/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file,"app-debug.apk");

                if(outputFile.exists()){
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = 8330841;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded=0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                OpenNewVersion(PATH);

                flag = true;
            } catch (Exception e) {
                Log.e("Phat", "Update Error: " + e.getMessage());
                Log.e("Phat", "Update Error: " + e.getLocalizedMessage());
                Log.e("Phat", "Update Error: " + e.getCause());


                flag = false;
            }
            return flag;

        }

    }

    void OpenNewVersion(String location) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(location + "app-debug.apk")),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
