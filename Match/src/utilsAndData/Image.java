package utilsAndData;

public class Image {
	
	private long user_id;
	private String image;
	private String activity_time;
	private boolean admin_Flag;
	
	public long getId(){

		return user_id;
	}
	
	public String getImage() {
		
		return image;
	}
	
	public String getTimeStamp(){
		
		return activity_time;
	}
	
	public boolean getAdminFlag(){
		
		return admin_Flag;
	}
	
	public void setId(long inId){
		
		user_id = inId;
	}
	
public void setImage(String inImage){
	
	image = inImage;
}

public void setAdminFlag(boolean adminFlag){
	
	admin_Flag = adminFlag;
}

public void setTimeStamp(String timeStamp) {
	this.activity_time = timeStamp;
}

}

