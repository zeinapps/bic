package zein.apps.bci;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zein.apps.bci.fragment.Fragment_menu;
import zein.apps.bci.fragment.Fragment_qrcode;
import zein.apps.bci.model.list_user;
import zein.apps.bci.service.ServiceHandler;
import zein.apps.bci.service.data_listuser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnChildClickListener{

	private String mTitle = "";

    ProgressDialog pDialog;
    private static String URL = config.URL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Fragment_menu fragmenhome = new Fragment_menu();
		FragmentManager fragment_home_Manager = getFragmentManager();
		FragmentTransaction ft = fragment_home_Manager.beginTransaction();
		ft.replace(R.id.content_frame, fragmenhome);
		ft.addToBackStack(null);
		ft.commit();
		
		mTitle = "Blazer Indonesia Club";
		getSupportActionBar().setTitle(mTitle);
		
		
//		AdView mAdView = (AdView) findViewById(R.id.adView);
//      AdRequest adRequest = new AdRequest.Builder().build();
//      mAdView.loadAd(adRequest);
	}
	
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

	/** Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		
		return super.onPrepareOptionsMenu(menu);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.searchview, menu);
	    MenuItem item = menu.findItem(R.id.menu_item_search);
	    SearchView sv = (SearchView) item.getActionView(); 
	    if (sv != null){
	        sv.setSubmitButtonEnabled(true);
	        sv.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String query) {
					pesan(query);
					return false;
				}
				
				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub
					return false;
				}
			});
	    }
	    return true;
	}
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(this, "Clicked On Child" + v.getTag(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
	void pesan(String pesan){
		Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
	}
	
	public void setActionBarTitle(String title) {
	    getSupportActionBar().setTitle(title);
	}
	
	private boolean doubleBackToExitPressedOnce;
	 @Override
	 public void onBackPressed() {
		 Fragment currentFragment = getFragmentManager().findFragmentById(R.id.content_frame);
	     if(currentFragment instanceof Fragment_menu) {
//	         super.onBackPressed();
	    	 if (doubleBackToExitPressedOnce) {
	    	        super.onBackPressed();
	    	        return;
	    	    }

	    	    this.doubleBackToExitPressedOnce = true;
	    	    Toast.makeText(this, "Sentuh 2 kali untuk keluar", Toast.LENGTH_SHORT).show();

	    	    new Handler().postDelayed(new Runnable() {

	    	        @Override
	    	        public void run() {
	    	            doubleBackToExitPressedOnce=false;                       
	    	        }
	    	    }, 2000);
	     }
	     else if(currentFragment instanceof Fragment_qrcode  ){
	         getFragmentManager().popBackStack();
	    	 return ;
	     }else{
	    	 super.onBackPressed();
	        return;
	     }
	 }
}
