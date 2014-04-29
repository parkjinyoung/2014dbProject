package fragment;


import com.example.test.LoginActivity;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.SnuRestaurantDetails;
import com.example.test.R.layout;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.test.Config_SnuResOnOff;
import com.example.test.R;

@SuppressLint("ValidFragment")
public class ConfigFragment extends Fragment {

	int loginFlag=0;
	Context mContext;
	ArrayList<String> configarr;

	public ConfigFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_config, null);
		loginFlag = 1;

		createArr(loginFlag);

		ListView list = (ListView) view.findViewById(R.id.configlist);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mContext, R.layout.config_list_item_1, configarr);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            	switch(position+loginFlag){
            	//ȸ������/�α���
            	case 0:
            		break;
            	//�α���/�α׾ƿ�
            	case 1:
            		if(loginFlag==1)
            		{
            		Intent intent = new Intent(v.getContext(), LoginActivity.class);
            		startActivity(intent);
            		}
            		else
            		{
            			
            		}
            		break;
            		
            	//�Ĵ� onoff
            	case 2:
            		Intent i = new Intent(v.getContext(), Config_SnuResOnOff.class);
                	startActivity(i);
            		break;
            	//�� ���� ����
            	case 3:
            		break;
            	//��������
            	case 4:
            		break;
            	//������ ����
            	case 5:
            		break;
            	default:
            		break;
            	}
            	//String configmenu = configarr.get(position);
            	
            	//Intent i = new Intent(v.getContext(), .class);
            	//i.putExtra("configmenu", configmenu);
            	//startActivity(i);
            }
        });
		return view;
	}

	private void createArr(int flag) {
		configarr = new ArrayList<String>();
		if(flag==1)	configarr.add("ȸ������/�α���");
		else {
			configarr.add("�α׾ƿ�");
			configarr.add("�����ϱ�");
		}
		configarr.add("�Ĵ� on/off");
		configarr.add("�� ���� ����");
		configarr.add("��������");
		configarr.add("������ ����");
	}
}
