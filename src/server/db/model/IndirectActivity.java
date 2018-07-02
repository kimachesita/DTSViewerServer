package server.db.model;

public class IndirectActivity extends TimesheetActivity{
	private int id;
	private String phase;
	private Activity activity;
	
	public IndirectActivity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
	
}
