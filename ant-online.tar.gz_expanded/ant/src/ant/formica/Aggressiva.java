package ant.formica;

import java.awt.Image;
import java.util.Set;

import ant.Ambiente;
import ant.Direzione;

public class Aggressiva extends Formica {
	
	static private int progId = 0;
	
	public Aggressiva(Ambiente ambiente) {
		super(ambiente, Aggressiva.progId);
		Aggressiva.progId++;
	}
	
	@Override
	public Image getImmagine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean decideDiCambiareDirezione() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Direzione cambioDirezione(Set<Direzione> possibili) {
		// TODO Auto-generated method stub
		return null;
	}

}
