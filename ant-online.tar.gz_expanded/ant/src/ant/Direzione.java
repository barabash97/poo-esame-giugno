package ant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Questa classe modella un direzione (incrementale) sul piano cartesiano
 * come coppia di interi in {-1,0,+1}.
 * <B>(DA COMPLETARE VEDI DOMANDA 1)</B>
 */
public class Direzione {

	static private final Random rnd = new Random();
	
	static public List<Direzione> TUTTE ;
	
	static public List<Direzione> tutteAcaso() {
		final List<Direzione> risultato = new ArrayList<>(TUTTE);
		Collections.shuffle(risultato);
		return risultato;
	}

	static {
		TUTTE = new ArrayList<Direzione>(8); // 3 x 3 - 1
		int[] delta  = { +1, 0, -1 };
		for(int dx : delta)
			for(int dy : delta) {
				if (dx==0 && dy==0) continue;
				TUTTE.add(new Direzione(dx,dy));
			}
	}
	
	static public Direzione casuale() {
		return TUTTE.get(rnd.nextInt(TUTTE.size()));
	}

	static public Direzione scegliAcasoTra(Collection<Direzione> possibili) {
		if (possibili.isEmpty()) throw new IllegalArgumentException();
		
		final int chosenIndex = rnd.nextInt(possibili.size());
		final Iterator<Direzione> it = possibili.iterator();
		for(int i=0; i<chosenIndex-1; i++) it.next();
		return it.next();
	}

	/* direzione di uno spostamento; un
	 * delta puo' essere -1 , 0 , +1 */
	private int dx;
	
	private int dy;
	
	public Direzione(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Crea un oggetto {@link Direzione} per spostarsi da una
	 * posizione ad un'altra
	 * @param src posizione di partenza
	 * @param dest posizione di arrivo
	 */
	public Direzione(Coordinate src, Coordinate dest) {
		this(
			proiettaUnitariamente(dest.getX()-src.getX()),
			proiettaUnitariamente(dest.getY()-src.getY())
		);
	}
	
	static final private int proiettaUnitariamente(int delta) {
		return delta!=0 ? delta / Math.abs(delta) : 0;
	}
	
	public int getDx() {
		return this.dx;
	}

	public int getDy() {
		return this.dy;
	}
	
	/**
	 * @return la direzione opposta alla presente
	 */
	public Direzione opposta() {
		return new Direzione(-this.getDx(), -this.getDy());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+" "+getDx()+","+getDy();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dx;
		result = prime * result + dy;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direzione other = (Direzione) obj;
		if (dx != other.dx)
			return false;
		if (dy != other.dy)
			return false;
		return true;
	}
	
	

}
