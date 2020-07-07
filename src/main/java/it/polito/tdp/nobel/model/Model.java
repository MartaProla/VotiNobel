package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	private List<Esame> esami;
	private EsameDAO dao;
	private double bestMedia;
	private Set<Esame>bestSoluzione;
	
	public Model() {
		dao=new EsameDAO();
		this.esami=dao.getTuttiEsami();
		bestMedia=0.0;
		bestSoluzione=null;
	}
	

	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame>parziale=new HashSet<>();
		//this.cerca(parziale, 0, numeroCrediti);
		this.cerca2(parziale, 0, numeroCrediti);
		
		return bestSoluzione;
	}
	
	private void cerca(Set<Esame> parziale, int L, int m) {
		//casi terminali
		int crediti= sommaCrediti(parziale);
		if(crediti>m)
			return;//fermo subito la ricorsione
		if(crediti==m) {
			double media=calcolaMedia(parziale);
			//media> medie fino ad adesso
			if(media>this.bestMedia) {
				this.bestSoluzione=new HashSet<>(parziale);
				this.bestMedia=media;
			}
		}
		//sicuramente crediti<m
		if(L==esami.size())
			return;
		//sotto-problemi
		//esami[L] è da aggiungere o no? Provo le cose 
		
		//provo ad aggiungerlo
		parziale.add(esami.get(L));
		cerca(parziale,L+1, m);
		parziale.remove(esami.get(L));
		
		//provo a non aggiungerlo
		cerca(parziale,L+1, m);
	}



	public double calcolaMedia(Set<Esame> parziale) {
		int crediti=0;
		int somma=0;
		for(Esame e: parziale) {
			crediti+=e.getCrediti();
			somma+=(e.getVoto()*e.getCrediti());
		}
		return somma/crediti;
	}



	private int sommaCrediti(Set<Esame> parziale) {
		int somma=0;
		for(Esame e: parziale)
			somma+=e.getCrediti();
		return somma;
	}
	
	/**
	 * Cerca2 è troppo lungo e lento!!!!!!!!!!!!!!! Ha una complessità maggiore al metodo cerca
	 */
	private void cerca2(Set<Esame> parziale, int L, int m) {
		//casi terminali
		int crediti= sommaCrediti(parziale);
		if(crediti>m)
			return;//fermo subito la ricorsione
		if(crediti==m) {
			double media=calcolaMedia(parziale);
			//media> medie fino ad adesso
			if(media>this.bestMedia) {
				this.bestSoluzione=new HashSet<>(parziale);
				this.bestMedia=media;
			}
		}
		//sicuramente crediti<m
		if(L==esami.size())
			return;
		//sotto-problemi
		for(Esame e:esami) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale, L+1,m);
				parziale.remove(e);
			}
		}
		
		
	}

	

}
