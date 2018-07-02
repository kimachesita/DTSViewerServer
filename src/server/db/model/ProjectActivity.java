package server.db.model;

public class ProjectActivity {
	private int projActid;
	private String name;
	private Activity activity;
	private String phaseId;
	private String phaseName;
	
	public ProjectActivity(int projActid, String name, Activity activity, String phaseId, String phaseName) {
		this.projActid = projActid;
		this.name = name;
		this.activity = activity;
		this.phaseName = phaseName;
		this.phaseId = phaseId;
	}

	public int getProjActid() {
		return projActid;
	}

	public void setProjActid(int projactid) {
		this.projActid = projactid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhasename(String phaseName) {
		this.phaseName = phaseName;
	}		
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}	
	public String getPhaseId() {
		return phaseId;
	}
	
	
}
