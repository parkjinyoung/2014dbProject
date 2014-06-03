package snu_module;

import java.util.ArrayList;

import com.example.test.R;
import com.example.test.R.drawable;
import com.example.test.R.id;

import object.Comment;
import object.SnuMenu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CafeListAdapter extends ArrayAdapter<SnuMenu>{
	// 식당 상세보기 화면의 list를 표시하는데 사용
	// 각각의 메뉴를 담고있는 list
    private ArrayList<SnuMenu> items;
    private int rsrc;
    
	public CafeListAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<SnuMenu> objects) {
		super(context, resource, textViewResourceId, objects);
		this.items = objects;
		this.rsrc = resource;
		// TODO Auto-generated constructor stub
	}
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(rsrc, null);
        } 
        SnuMenu e = items.get(position);
        if (e != null) {
        	
        	ImageView[] image_eval = new ImageView[5];
    		image_eval[0] = (ImageView) v.findViewById(R.id.eval_stars1);
    		image_eval[1] = (ImageView) v.findViewById(R.id.eval_stars2);
    		image_eval[2] = (ImageView) v.findViewById(R.id.eval_stars3);
    		image_eval[3] = (ImageView) v.findViewById(R.id.eval_stars4);
    		image_eval[4] = (ImageView) v.findViewById(R.id.eval_stars5);
        	
            ((TextView)v.findViewById(R.id.row_title)).setText(e.getMenu());
            ((TextView)v.findViewById(R.id.snu_todaymenu_price)).setText(Integer.toString(e.getPrice()));
           
            String tmpeval = e.getRating();
            
            System.out.println("eval : " + tmpeval);
            
    		if(tmpeval!=null){
    			float eval = Float.parseFloat(tmpeval);
    			for(int j=0; j<5; j++){
    				// 별점계산해서 화면에 그려주는부분
    				if(eval >= 1) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.star25));
    				else if (eval >= 0.5) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.halfstar25));
    				else image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
    				eval -= 1;
    			}
    		}
        
    		else{
//    			No Comment Image
    			for(int j=0; j<5; j++){
    				image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
    			}
    		}
    		
        }
        
        return v;
    }
}
	
