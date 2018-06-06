package ant;

import ant.formica.Formica;

public class Cibo {

	private Coordinate posizione;
	

	private Formica formica;
	
	public Cibo(Coordinate p) {
		this.posizione = p;
	}

	public Coordinate getPosizione() {
		return this.posizione;
	}
	
	public void setRaccoglitrice(Formica formica) {
		this.formica = formica;
	}


	public Formica getFormiche() {
		return this.formica;
	}
}
