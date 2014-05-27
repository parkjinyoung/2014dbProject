package object;

public class UserInfo {
	String id;
	String nickname;
	String password;
	String key;
	int uno;
	public UserInfo()
	{
		id="";
		nickname="";
		password="";
		key="";
		uno=0;
	}
	public UserInfo(String id, String nickname, String password, String key){
		this.nickname = nickname;
		this.password = password;
		this.id = id;
		this.key= key;
		this.uno = 0;
	}
	public UserInfo(String id, String password){
		this.nickname="";
		this.password = password;
		this.id = id;
		this.key="";
		this.uno = 0;
	}
	public int getUno()
	{
		return uno;
	}
	public void setUno(int uno)
	{
		this.uno = uno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setKey(String key)
	{
		this.key= key;
	}
	public String getKey()
	{
		return key;
	}
}
