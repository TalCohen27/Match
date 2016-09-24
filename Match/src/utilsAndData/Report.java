package utilsAndData;
import utilsAndData.JDBC;

@SuppressWarnings("unused")
public class Report extends Message {
	
	private long reported;
	
	public Report(long reporterId , long reportedId, String content)
	{
		super(reporterId, Consts.ADMIN_ID, content);
		reported = reportedId;	
	}
	
	public Report(){}
	
	public void setReported(long reportedId)
	{
		reported = reportedId;
	}
	
	public long getReported()
	{
		return reported;
	}

}
