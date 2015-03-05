package tp.pr5;

public class City {
	
	/**
	 * Constructor sin parametros
	 */
	public City() {
	}
	
	/**
	 * Constructor usando un array de calles
	 * 
	 * @param cityMap
	 */
	public City(Street[] cityMap) {
		this.streets = cityMap;
	}
	
	/**
	 * Aniade la calle dada al mapa
	 * 
	 * @param street
	 */
	public void addStreet(Street street){
		this.streets[this.streets.length] = street;
	}
	
	/**
	 * Busca la calle que comienza en el lugar y la direccion dadas
	 * 
	 * @param currentPlace
	 * @param currentHeading
	 * @return La calle buscada
	 */
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		int i = 0;
		if (streets == null) 
			return null;
		
		while (i < this.streets.length) { // Recorre el array de calles hasta encontrar una valida
			if ((this.streets[i] != null) && (this.streets[i].comeOutFrom(currentPlace, currentHeading))) 
				return this.streets[i];
			i++;
		}
		return null;
	}
		
	private Street[] streets = null;
}
