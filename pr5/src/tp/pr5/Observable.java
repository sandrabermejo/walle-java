package tp.pr5;

import java.util.ArrayList;

public class Observable<T> {
	
	public Observable(){
	}
	
	/**
	 * Aniade un observador a la lista de observadores
	 * 
	 * @param observer
	 */
	public void addObserver(T observer){
		if(posObserver(observer) == this.observers.size()) { // no está
			this.observers.add(this.observers.size(), observer);
		}
	}
	
	/**
	 * Quita un observador de la lista de observadores
	 * 
	 * @param observer
	 */
	public void removeObserver(T observer){
		int pos = posObserver(observer);
		if (pos < this.observers.size()){ // está
			for (int i = pos + 1; i < this.observers.size(); i++)
				this.observers.add(i-1, this.observers.get(i));
		}
	}
	
	/**
	 * 
	 * @param observer
	 * @return la posicion en la que esta el observador (si no esta, devuelva la primera posicion fuera del array)
	 */
	public int posObserver(T observer){
		int i = 0;
		while (i < this.observers.size()){
			if (this.observers.get(i).equals(observer))
				return i;
			i++;
		}
		return i;
	}
	
	protected ArrayList<T> observers;
}
