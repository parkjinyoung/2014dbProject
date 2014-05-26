package login_module;

import android.app.Application;

public class MyApplication extends Application{
	
	boolean loginStatus;
	boolean authenticated;
	String nickName;
	String id;
	@Override
	public void onCreate()
	{
		loginStatus = false;
		authenticated = false;
		nickName = "USER";
		id = "";
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
}
