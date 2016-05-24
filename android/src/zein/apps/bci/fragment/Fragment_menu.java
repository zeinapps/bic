package zein.apps.bci.fragment;


import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tagmanager.Container.FunctionCallMacroCallback;
import java.net.URLEncoder;
import zein.apps.bci.MainActivity;
import zein.apps.bci.R;
import zein.apps.bci.config;
import zein.apps.bci.adapter.Adapter_listuser;
import zein.apps.bci.model.list_user;
import zein.apps.bci.service.ServiceHandler;
import zein.apps.bci.service.data_listuser;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_menu extends Fragment {

	private static String URL_api = config.URL;

	ProgressDialog pDialog;
	String nopol = "";
	String nama = "";
	String id_anggota = "";

	
	View footer_progress;
	private final int AUTOLOAD_THRESHOLD = 1;
    private final int MAXIMUM_ITEMS = 50;
    private boolean mIsLoading = true;
    private boolean mMoreDataAvailable = true;
//    ViewGroup container;
//    public 
    
	int curentpage = 1;
	LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	this.inflater = inflater;
    	View v = inflater.inflate(R.layout.fragment_menu_utama, container, false);
    	
    	String value = "<a href=\"http://bic.or.id\">bic.or.id</a>";
        TextView link = (TextView) v.findViewById(R.id.txt_link);


        link.setText(Html.fromHtml(value));
        link.setMovementMethod(LinkMovementMethod.getInstance());
    	
    	Button btn_menu_qrcode = (Button) v.findViewById(R.id.btn_menu_qrcode);
    	btn_menu_qrcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Fragment_qrcode fragmen_qr = new Fragment_qrcode();
        		FragmentManager fragment_home_Manager = getFragmentManager();
        		FragmentTransaction ft = fragment_home_Manager.beginTransaction();
        		ft.replace(R.id.content_frame, fragmen_qr);
        		ft.addToBackStack(null);
        		ft.commit();
           }
        });
    	
    	Button btn_menu_nopol = (Button) v.findViewById(R.id.btn_menu_nopol);
    	btn_menu_nopol.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	LayoutInflater li = LayoutInflater.from((MainActivity)getActivity());
				View promptsView = li.inflate(R.layout.input_dialog, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						(MainActivity)getActivity());

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setTitle("Nomor Polisi")
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	nopol  = userInput.getText().toString();
					    	new loadMoreListView(curentpage,"nomor_polisi",nopol.replaceAll("[^A-Za-z0-9]", ""),false).execute();
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
            	
           }
        });
    	
    	Button btn_menu_id = (Button) v.findViewById(R.id.btn_menu_id);
    	btn_menu_id.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	LayoutInflater li = LayoutInflater.from((MainActivity)getActivity());
				View promptsView = li.inflate(R.layout.input_dialog, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						(MainActivity)getActivity());

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setTitle("Id Anggota")
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	id_anggota  = userInput.getText().toString();
					    	new loadMoreListView(curentpage,"id_anggota",id_anggota.replaceAll("[^A-Za-z0-9]", ""),false).execute();
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
            	
           }
        });
    	
    	Button btn_menu_tampil_qrcoed = (Button) v.findViewById(R.id.btn_menu_tampil_qrcode);
    	btn_menu_tampil_qrcoed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	LayoutInflater li = LayoutInflater.from((MainActivity)getActivity());
				View promptsView = li.inflate(R.layout.input_dialog, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						(MainActivity)getActivity());

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setTitle("Id Anggota")
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	id_anggota  = userInput.getText().toString();
					    	new loadMoreListView(curentpage,"id_anggota",id_anggota.replaceAll("[^A-Za-z0-9]", ""),true).execute();
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
            	
           }
        });
    	
    	Button btn_menu_nama = (Button) v.findViewById(R.id.btn_menu_nama);
    	btn_menu_nama.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	LayoutInflater li = LayoutInflater.from((MainActivity)getActivity());
				View promptsView = li.inflate(R.layout.input_dialog, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						(MainActivity)getActivity());
				alertDialogBuilder.setView(promptsView);
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				alertDialogBuilder
					.setCancelable(false)
					.setTitle("Nama Anggota")
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	nama  = userInput.getText().toString();
					    	if(nama.length()>3){
					    		new loadMoreListView(curentpage,"nama_anggota",nama.replaceAll("[^A-Za-z0-9\\s]", ""),false).execute();
					    	}else{
					    		pesan("Karakter minimal 4 huruf", true);
					    	}
					    	
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
            	
           }
        });
    	
		
        return v;
    }

	private void sharetoMedsos(String title, String link) {
	    // Standard message to send
	    String msg = title + " " + link;

	    Intent share = new Intent(Intent.ACTION_SEND);
	    share.setType("text/plain");

	    List<ResolveInfo> resInfo = ((MainActivity) getActivity()).getPackageManager().queryIntentActivities(share, 0);
	    if (!resInfo.isEmpty()) {
	        List<Intent> targetedShareIntents = new ArrayList<Intent>();
	        Intent targetedShareIntent = null;

	        for (ResolveInfo resolveInfo : resInfo) {
	            String packageName = resolveInfo.activityInfo.packageName;
	            targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
	            targetedShareIntent.setType("text/plain");

	            // Find twitter: com.twitter.android...
	            if ("com.twitter.android".equals(packageName)) {
	                targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
	            } else if ("com.google.android.gm".equals(packageName)) {
	                targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
	                targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, Uri.encode(title + "\r\n" + link));
	            } else if ("com.android.email".equals(packageName)) {
	                targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
	                targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, Uri.encode(title + "\n" + link));
	            } else {
	                // Rest of Apps
	                targetedShareIntent.putExtra( android.content.Intent.EXTRA_TEXT, msg);
	            }

	            targetedShareIntent.setPackage(packageName);
	            targetedShareIntents.add(targetedShareIntent);
	        }

	        Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), getResources().getString(R.string.share));
	        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
	        startActivityForResult(chooserIntent, 0);
	    }
	}
	

	void pesan(String txt, boolean dialog){
		if(dialog){

			
			final Dialog dialog_box = new Dialog((MainActivity)getActivity());
			dialog_box.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_box.setContentView(R.layout.dialog_detil_user);
			TextView text = (TextView) dialog_box.findViewById(R.id.txt_detil_user);
			text.setText(txt);
			

			Button dialogButton = (Button) dialog_box.findViewById(R.id.btn_detil_user_ok);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_box.dismiss();
				}
			});

			dialog_box.show();
			
		}else{
			Toast.makeText((MainActivity)getActivity(), txt,
	    			Toast.LENGTH_LONG).show();
		}
    	
    	
    }
	
	void show_qrcode(String url){
		url = url.replace("localhost", "10.0.2.2");
//			ImageLoader imageLoader; 
			final Dialog dialog_box = new Dialog((MainActivity)getActivity());
			dialog_box.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_box.setContentView(R.layout.dialog_show_qrcode);
//			imageLoader=new ImageLoader(((MainActivity) getActivity()));
			ImageView image_qrcode = (ImageView) dialog_box.findViewById(R.id.image_qrcode);
//			imageLoader.DisplayImage(url, image_qrcode);

    		new LoadImage(image_qrcode).execute(url);
			Button dialogButton = (Button) dialog_box.findViewById(R.id.btn_show_qrcode_ok);
			
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_box.dismiss();
				}
			});

			dialog_box.show();
			
    	
    	
    }


	public class loadMoreListView extends AsyncTask<String, Integer, String> {
		boolean show_qr = false;
		int page;
		String search_key;
		String search_value;
		
		public loadMoreListView(int page, String search_key, String search_value, boolean show_qr) {
			this.show_qr = show_qr;
			this.page = page;
			this.search_key = search_key;
			this.search_value = search_value;
	    }
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
//	        listview.addFooterView(v_footer);
	        pDialog = new ProgressDialog((MainActivity) getActivity());
	        pDialog.setMessage("Loading...");
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }
	    @Override
	    protected String  doInBackground(String... arg0) {
	    	ServiceHandler sh = new ServiceHandler();
	        String StringJson = null;
	        String param = null;
	        try {
	        	param = URLEncoder.encode(this.search_value, "UTF-8");
			} catch (Exception e) {
				// TODO: handle exception
			}
	        
	        StringJson = sh.makeServiceCall(URL_api+"?json=get_user&"+this.search_key+"="+param, ServiceHandler.GET);
	        
			if(!sh.getMessageError().equals("")){
				return null;
			}
	        return StringJson;
	    }
	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);

	        if (pDialog.isShowing())
                pDialog.dismiss();
	        

	    	Adapter_listuser adapter_listuser = null;
	        
//	        int currentPosition = listview.getFirstVisiblePosition();
	        data_listuser data_listuser = new data_listuser(result);
	        ArrayList<list_user> list_usr = data_listuser.getlist_user();
//	        if(adapter_listuser == null){
	        	adapter_listuser = new Adapter_listuser(getActivity(), 0,
	        			list_usr);
//	        }else{
//	        	for (int i = 0; i < list_usr.size(); i++) {
//		        	adapter_listuser.add(list_usr.get(i));
//				}
//	        }
//	        Log.i("adapter", adapter_listuser.getCount()+"");
	        adapter_listuser.notifyDataSetChanged();
	        
	        dialoglist(adapter_listuser,this.show_qr);
	        
	        
	    }
	    
	}
	
//	View convertView;
	private void dialoglist(final Adapter_listuser adapter_listuser, boolean show_qr){
		
		View convertView = (View) this.inflater.inflate(R.layout.dialog_list, null);
		ListView listview ;
//    	View v_footer;
		
    	listview = (ListView) convertView.findViewById(R.id.list_user);
        
        listview.setAdapter(adapter_listuser);
        
		listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                //new display_detilbarang(adapter_listbarang.getItem(position).id_barang).execute();
            	final String detil_user = "" +
            			"Anggota : "+adapter_listuser.getItem(position).rayon+"\n" +
            			"Nama :"+adapter_listuser.getItem(position).display_name+"\n" +
            			"id Anggota : "+adapter_listuser.getItem(position).id_anggota+"\n"+
            			"Nomor Polisi : "+adapter_listuser.getItem(position).nomor_polisi+"\n" +
    					"Jenis Blazer : "+adapter_listuser.getItem(position).jenis_blazer+"\n" +
    					"Status : "+adapter_listuser.getItem(position).status_id+"\n";
            	
            	
            	pesan(detil_user, true);
            	
            }
        });
		
		curentpage++;
		listview.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
				if (!mIsLoading && mMoreDataAvailable) {
		            if (totalItemCount >= MAXIMUM_ITEMS) {
		                mMoreDataAvailable = false;
		            } else if (totalItemCount - AUTOLOAD_THRESHOLD <= firstVisibleItem + visibleItemCount) {
		                mIsLoading = true;
//		                new loadMoreListView(curentpage).execute();
		            }
		        }
				
				
			}
		});
//		listview.removeFooterView(v_footer);
//		listview.setSelection(currentPosition+1);
        mIsLoading = false;
        
        if(adapter_listuser.getCount() == 1){
        	final String detil_user = "" +
        			"Anggota : "+adapter_listuser.getItem(0).rayon+"\n" +
        			"Nama :"+adapter_listuser.getItem(0).display_name+"\n" +
        			"id Anggota : "+adapter_listuser.getItem(0).id_anggota+"\n"+
        			"Nomor Polisi : "+adapter_listuser.getItem(0).nomor_polisi+"\n" +
					"Jenis Blazer : "+adapter_listuser.getItem(0).jenis_blazer+"\n" +
					"Status : "+adapter_listuser.getItem(0).status_id+"\n" 
					;
        	
        	
        	if(show_qr == true){
        		final String url_qrcode = adapter_listuser.getItem(0).qr_code;
        		if(url_qrcode != null){
        			show_qrcode(url_qrcode);
        		}else{
        			pesan("QR CODE tidak ditemukan", false);
        		}
        		
        	}else{
        		pesan(detil_user, true);
        	}
        }else if(adapter_listuser.getCount() == 0){
        	pesan("Data tidak ditemukan", true);
        }else{
        	AlertDialog.Builder alertDialog;
    		alertDialog = new AlertDialog.Builder((MainActivity)getActivity());
        	alertDialog.setView(convertView);
        	alertDialog.setTitle("Hasil Pencarian");
            alertDialog.show();
        }
        
        
	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		ImageView img;
		public LoadImage(ImageView img){
			this.img = img;
		}
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
 
        }
         protected Bitmap doInBackground(String... args) {
        	 Bitmap bitmap = null;
             try {
            	 bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
 
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return bitmap;
         }
 
         protected void onPostExecute(Bitmap image) {
 
             if(image != null){
             this.img.setImageBitmap(image);
             
             }
         }
     }
}
