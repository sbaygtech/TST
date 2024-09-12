package cb.ext.dbs.data;

public class Customer {
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}

	
	public String getVkn() {
		return vkn;
	}
	public void setVkn(String vkn) {
		this.vkn = vkn;
	}

	private String title;
	private String organization;
	private long oid;
	private String vkn;

}
