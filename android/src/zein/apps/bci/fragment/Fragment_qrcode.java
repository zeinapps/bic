package zein.apps.bci.fragment;


import java.net.URLEncoder;
import java.util.ArrayList;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import zein.apps.bci.MainActivity;
import zein.apps.bci.R;
import zein.apps.bci.CameraPreview;
import zein.apps.bci.config;
import zein.apps.bci.adapter.Adapter_listuser;
import zein.apps.bci.fragment.Fragment_menu.loadMoreListView;
import zein.apps.bci.model.list_user;
import zein.apps.bci.service.ServiceHandler;
import zein.apps.bci.service.data_listuser;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_qrcode extends Fragment {

	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

	private static String URL_api = config.URL;
    TextView scanText;
    Button scanButton;

    ImageScanner scanner;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
    
    private final int AUTOLOAD_THRESHOLD = 1;
    private final int MAXIMUM_ITEMS = 50;
    private boolean mIsLoading = true;
    private boolean mMoreDataAvailable = true;
//    ViewGroup container;
    
    
	int curentpage = 1;
	LayoutInflater inflater;
    
    static {
        System.loadLibrary("iconv");
    } 
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.fragement_qrcode, container, false);
    	this.inflater = inflater;
//    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview((MainActivity) getActivity(), mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)v.findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)v.findViewById(R.id.scanText);

        
    	
        return v;
    }
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    
                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
//                        scanText.setText("barcode result " + sym.getData());
                        barcodeScanned = true;
                        
                        new loadMoreListView(0,"nomor_polisi",sym.getData().replaceAll("[^A-Za-z0-9]", "")).execute();
                        
                    }
                }
            }
        };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };
        
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
        ProgressDialog pDialog;
        public class loadMoreListView extends AsyncTask<String, Integer, String> {

    		int page;
    		String search_key;
    		String search_value;
    		
    		public loadMoreListView(int page, String search_key, String search_value) {
    			
    			this.page = page;
    			this.search_key = search_key;
    			this.search_value = search_value;
    	    }
    	    @Override
    	    protected void onPreExecute() {
    	        super.onPreExecute();
//    	        listview.addFooterView(v_footer);
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
    	        
    	        data_listuser data_listuser = new data_listuser(result);
    	        ArrayList<list_user> list_usr = data_listuser.getlist_user();
    	        	adapter_listuser = new Adapter_listuser(getActivity(), 0,
    	        			list_usr);
    	        adapter_listuser.notifyDataSetChanged();
    	        
    	        final String detil_user = "" +
            			"Anggota : "+adapter_listuser.getItem(0).rayon+"\n" +
            			"Nama :"+adapter_listuser.getItem(0).display_name+"\n" +
            			"id Anggota : "+adapter_listuser.getItem(0).id_anggota+"\n"+
            			"Nomor Polisi : "+adapter_listuser.getItem(0).nomor_polisi+"\n" +
    					"Jenis Blazer : "+adapter_listuser.getItem(0).jenis_blazer+"\n"  +
    					"Status : "+adapter_listuser.getItem(0).status_id+"\n"
    					;
            	
            	pesan(detil_user, true);
            	getFragmentManager().popBackStack();
    	        
    	    }
    	    
    	}
        
    	
}
