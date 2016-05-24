package zein.apps.bci.adapter;

import java.util.ArrayList;

import zein.apps.bci.R;
import zein.apps.bci.model.list_user;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_listuser extends ArrayAdapter<list_user> {
    private Activity activity;
    private ArrayList<list_user> lList;
    private static LayoutInflater inflater = null;

//    public ImageLoader imageLoader; 
    
    public Adapter_listuser (Activity activity, int textViewResourceId,ArrayList<list_user> al_List) {
        super(activity, textViewResourceId, al_List);
        try {
            this.activity = activity;
            this.lList = al_List;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            imageLoader=new ImageLoader(activity.getApplicationContext());
        } catch (Exception e) {

        }
    }
    
//    public void addMoreItems(int count) {
//        notifyDataSetChanged();
//    }

    public int getCount() {
        return lList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView nama;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.item_list, null);
                holder = new ViewHolder();

                holder.nama = (TextView) vi.findViewById(R.id.txt_list_nama);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.nama.setText(lList.get(position).display_name);
        } catch (Exception e) {
        	Log.d("LIST EROR", e.getMessage());
        }
        return vi;
    }
    
}
