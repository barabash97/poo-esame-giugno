package ant.formica;

import static ant.costanti.CostantiGUI.IMMAGINE_FORMICA_GIALLA;

import java.awt.Image;
import java.util.Set;

import ant.Ambiente;
import ant.Cibo;
import ant.Coordinate;
import ant.Direzione;
import ant.Formicaio;

public abstract class Formica {

	final protected int id;

	protected Ambiente ambiente;

	protected Formicaio formicaio;

	protected Coordinate posizione; // posizione corrente

	protected Direzione direzione; // direzione corrente

	protected Cibo carico;

	public Formica(Ambiente ambiente, int id) {
		this.id = id;
		this.ambiente = ambiente;
		this.formicaio = ambiente.getFormicaio();
		/* n.b. si parte dal formicaio */
		this.posizione = this.formicaio.getPosizione();
		this.direzione = Direzione.casuale();
		this.carico = null;
	}

	public int getId() {
		return this.id;
	}

	public Ambiente getAmbiente() {
		return this.ambiente;
	}

	private Formicaio getFormicaio() {
		return this.formicaio;
	}

	public Cibo getCarico() {
		return this.carico;
	}

	public void setCarico(Cibo cibo) {
		this.carico = cibo;
	}

	public Coordinate getPosizione() {
		return this.posizione;
	}

	protected void setPosizione(Coordinate nuova) {
		this.posizione = nuova;
	}

	public Direzione getDirezione() {
		return this.direzione;
	}

	protected void setDirezione(Direzione nuova) {
		this.direzione = nuova;
	}

	public void simula(int passo) {
		if (ciboCaricato()) {
			/* cibo gia' trovato? */
			if (nelFormicaio()) {
				/* rientrato, scarica cibo e ricomincia da capo */
				this.formicaio.immagazzinaCaricoDi(this);
			} else {
				/* cibo gia' caricato: redireziona per rientrare verso il formicaio */
				final Direzione versoFormicaio = new Direzione(this.getPosizione(), this.formicaio.getPosizione());
				final Set<Direzione> possibili = getDirezioniAttualmentePossibili();
				if (possibili.contains(versoFormicaio))
					this.setDirezione(versoFormicaio);
				else
					this.setDirezione(Direzione.scegliAcasoTra(possibili));
				/* segnala fonte di cibo lasciando traccia di ferormone durante il rientro */
				this.ambiente.incrementaFerormone(this.getPosizione());
				eseguiSpostamento();
			}
		} else {
			/* ancora senza cibo: continua a cercare */
			final Cibo cibo = this.ambiente.getCibo(this.getPosizione());
			if (cibo != null) {
				/* trovato! raccogli e porta via... */
				raccogli(cibo);
			} else { /* niente da fare: scegli una direzione */
				/* calcola l'insieme delle possibili direzioni (senza urtare gli ostacoli) */
				final Set<Direzione> possibili = getDirezioniAttualmentePossibili();
				final Direzione nuovaDirezione = decidiDirezione(possibili);
				/* ricontrolla che la scelta sia sensata: colpisco un ostacolo? */
				if (!possibili.contains(nuovaDirezione))
					throw new IllegalStateException("La direzione scelta per " + this + " non era tra quelle possibili:"
							+ " cosi' la formica sbatte contro un ostacolo!");
				this.setDirezione(nuovaDirezione);
				eseguiSpostamento();
			}
		}
	}

	private void eseguiSpostamento() {
		this.setPosizione(this.getPosizione().trasla(this.getDirezione()));
	}

	private void raccogli(final Cibo cibo) {
		this.setCarico(this.ambiente.remove(cibo));
		cibo.setRaccoglitrice(this);
	}

	public Direzione decidiDirezione(Set<Direzione> possibili) {
		Direzione risultato = this.getDirezione();

		/* controlla se non sia il momento di cambiare direzione */
		if (decideDiCambiareDirezione() || deveCambiareDirezione(possibili))
			risultato = cambioDirezione(possibili);

		return risultato;
	}

	public boolean ciboCaricato() {
		return (this.getCarico() != null);
	}

	public Cibo scaricaCibo() {
		final Cibo daScaricare = this.carico;
		this.carico = null;
		return daScaricare;
	}

	private boolean nelFormicaio() {
		return getPosizione().equals(this.getFormicaio().getPosizione());
	}

	private Set<Direzione> getDirezioniAttualmentePossibili() {
		return this.getAmbiente().getPossibiliDirezioni(this.getPosizione());
	}

	private boolean deveCambiareDirezione(final Set<Direzione> direzioniPossibili) {
		return !direzioniPossibili.contains(this.getDirezione());
	}

	private boolean percepitaNuovaTraccia(final Direzione direzioneFerormone) {
		/*
		 * controlla se c'e' una "nuova" traccia: ma tornare da dove gia' si proveniva
		 * non serve
		 */
		return (direzioneFerormone != null && !direzioneFerormone.equals(this.getDirezione().opposta()));
	}

	private Direzione scegliDirezioneDopoPerditaTraccia(Set<Direzione> possibili) {
		/*
		 * non e' stato "annusato" nulla: scegli una direzione a caso se non e'
		 * possibile proseguire nella corrente
		 */
		if (possibili.contains(this.getDirezione()))
			return this.getDirezione();
		else
			return Direzione.scegliAcasoTra(possibili);
		/* SUGGERIMENTO: vedi anche domanda 7 */
	}

	abstract public Image getImmagine();

	abstract public boolean decideDiCambiareDirezione();

	abstract public Direzione cambioDirezione(Set<Direzione> possibili);

	@Override
	public String toString() {
		return getClass().getSimpleName() + getId();
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public void setFormicaio(Formicaio formicaio) {
		this.formicaio = formicaio;
	}

}
