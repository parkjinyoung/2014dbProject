package fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.test.Config_SnuResOnOff;
import com.example.test.R;

@SuppressLint("ValidFragment")
public class ConfigFragment extends Fragment {

	Context mContext;
	ArrayList<String> configarr;

	public ConfigFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_config, null);

		createArr();

		ListView list = (ListView) view.findViewById(R.id.configlist);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mContext, R.layout.config_list_item_1, configarr);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            	switch(position){
            	//ȸ������
            	case 0:
            		break;
            	//�α���/�α׾ƿ�
            	case 1:
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

	private void createArr() {
		configarr = new ArrayList<String>();
		configarr.add("ȸ������");
		configarr.add("�α���/�α׾ƿ�");
		configarr.add("�Ĵ� on/off");
		configarr.add("�� ���� ����");
		configarr.add("��������");
		configarr.add("������ ����");
	}
}
