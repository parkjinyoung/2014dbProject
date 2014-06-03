package login_module;

import android.app.Application;

// 앱 전체에서 사용되는 유저 정보 저장소.
public class MyApplication extends Application{
	
	boolean loginStatus;
	boolean authenticated;
	String nickName;
	String id;
	String email;
	String uno;
	public void clear()
	{
		onCreate();
	}
	@Override
	public void onCreate()
	{
		email="";
		loginStatus = false;
		authenticated = false;
		nickName = "USER";
		id = "";
		uno = "";
	}
	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
	public void setLoginStatus(boolean loginStatus)
	{
		this.loginStatus = loginStatus;
	}
	public boolean getLoginStatus()
	{
		return loginStatus;
	}
	public void setAuth(boolean auth)
	{
		this.authenticated = auth;
	}
	public boolean getAuth()
	{
		return authenticated;
	}
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	public String getNickName()
	{
		return nickName;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return id;
	}
	public String getUno()
	{
		return uno;
	}
	public void setUno(String uno)
	{
		this.uno = uno;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail()
	{
		return email;
	}
}
