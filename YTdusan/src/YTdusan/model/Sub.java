package YTdusan.model;

public class Sub {

	private String ime;

	
	public Sub(String ime){
		super();
		this.ime = ime;
		
	}
	
	public Sub(){};
	
	@Override
	public String toString() {
		return "Sub [ime=" + ime + "]";
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

}
