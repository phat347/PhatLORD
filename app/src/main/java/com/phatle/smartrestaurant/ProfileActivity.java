package com.phatle.smartrestaurant;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    CallbackManager callbackManager;
    ImageView profile;
    TextView username;
    String IntentUsername;
    String IntentPhotoURL;
    NavigationView navigationView;
    private List<UserContact> mList = new ArrayList<>();
    private List<DrawerItem> mListMenu = new ArrayList<>();
    private UserContactAdapter mAdapter;
    private DrawerItemAdapter mAdapterMenu;
    SwipeController swipeController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        mList.add(new UserContact("A"));
        mList.add(new UserContact("An","1111111111111"));
        mList.add(new UserContact("Anh","22222222222"));
//        mList.add(new UserContact("B"));
        mList.add(new UserContact("Bao","33333333333333"));
        mList.add(new UserContact("Binh","44444444444"));
//        mList.add(new UserContact("H"));
        mList.add(new UserContact("Hong","55555555555555"));
//        mList.add(new UserContact("M"));
        mList.add(new UserContact("Minh","888888888888"));
        mList.add(new UserContact("minh ","888888888888"));
        mList.add(new UserContact("mẫn","888888888888"));
        mList.add(new UserContact("mạnh","888888888888"));
        mList.add(new UserContact("Aaa","888888888888"));
        mList.add(new UserContact("hòa","888888888888"));
        mList.add(new UserContact("YYY","888888888888"));
        mList.add(new UserContact("ZZZZ","888888888888"));
        mList.add(new UserContact("BBBBBBB","888888888888"));

        //Convert first letter to uppercase
        for (int i = 0; i < mList.size() ; i++) {
            String cap = mList.get(i).getName().substring(0,1).toUpperCase() + mList.get(i).getName().substring(1);
            mList.get(i).setName(cap);
        }
        //Sort list theo alphabet
        Collections.sort(mList,new UserContactComparator());

        //Add vô alphabet list
        String temp = "tmp";
        for (int i = 0; i < mList.size(); i++) {
            String mTemp = mList.get(i).getName().substring(0,1);
            if(!mTemp.equals(temp))
            {
                mList.add(i,new UserContact(mTemp));
                temp = mTemp;
            }
        }


        mAdapter = new UserContactAdapter(mList);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mList.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mList.get(position).getPhoneNumber()));
                startActivity(intent);
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
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
        if(user != null)
        {
            username.setText(user.getDisplayName());
        }
        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
        }

        Intent intent = ProfileActivity.this.getIntent();
        IntentPhotoURL = intent.getStringExtra("PhotoURL");
        IntentUsername = intent.getStringExtra("Username");
        if (IntentUsername != null)
        {
            username.setText(IntentUsername);
        }
        if (IntentPhotoURL != null)
        {
            Picasso.with(ProfileActivity.this)
                    .load(IntentPhotoURL) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(100, 100)
                    .into(profile);
        }
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
        mListMenu.add(new DrawerItem("Item1",R.drawable.power_signal));
        mListMenu.add(new DrawerItem("Item2",R.drawable.power_signal));
        mListMenu.add(new DrawerItem("Item3",R.drawable.power_signal));
        mListMenu.add(new DrawerItem("Đăng xuất",R.drawable.power_signal));

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
                if(drawerItem.getItemName().equals("Đăng xuất"))
                {
                    mDrawerLayout.closeDrawers();
                    signOut();
                    LoginManager.getInstance().logOut();
                    auth.signOut();
                    Intent backtoLogin = new Intent(ProfileActivity.this,MainActivity.class);
                    startActivity(backtoLogin);
                    finish();
                }
            }
        });
//        View headerMenuLayout = navigationView.getHeaderView(0);
        username = mDrawerLayout.findViewById(R.id.tv_username);
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
    private class UserContactComparator implements Comparator<UserContact>{

        public int compare(UserContact a, UserContact b)
        {
            return a.name.compareTo(b.name);
        }
    }
}
