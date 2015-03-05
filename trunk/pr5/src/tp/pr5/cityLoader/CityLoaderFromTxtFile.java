package tp.pr5.cityLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.items.*;

public class CityLoaderFromTxtFile {
	
	/**
	 * Constructor
	 */
	public CityLoaderFromTxtFile(){
		CityLoaderFromTxtFile.streets = new ArrayList<Street>();
		CityLoaderFromTxtFile.places = new ArrayList<Place>();
		this.token = null;
	}
	
	/**
	 * Carga la ciudad del fichero dado
	 * @param file
	 * @return La ciudad cargada
	 * @throws IOException
	 */
	public City loadCity(InputStream file) throws IOException {
		Reader reader = new BufferedReader(new InputStreamReader(file));
		this.token = new StreamTokenizer(reader);
		this.token.wordChars('\u0021', '\u007E');
		this.token.quoteChar((int)'"');
		
		this.forceString("BeginCity");
		this.parsePlaces();
		this.parseStreets();
		this.parseItems();
		this.forceString("EndCity");
				
	    // Convertimos el ArrayList<Street> en un array de Street
		Street [] cityMap = CityLoaderFromTxtFile.streets.toArray(new Street[CityLoaderFromTxtFile.streets.size()]);
		return new City(cityMap);
	}
	
	/**
	 * 
	 * @return El lugar donde el robot comienza la simulacion
	 */
	public Place getInitialPlace(){
		return CityLoaderFromTxtFile.places.get(0);
	}
	
	private int forceNumber() throws IOException {
		this.token.nextToken();
	 	if (this.token.sval != null)
			throw new WrongCityFormatException();
		return (int)this.token.nval;
	}	
	
	private void forceNumber(int num) throws IOException {
		if ((this.token.nextToken() != StreamTokenizer.TT_NUMBER) || (this.token.nval != num))
				throw new WrongCityFormatException();
	}
	
	private String forceString() throws IOException {
		this.token.nextToken();
		if (this.token.sval == null)
			throw new WrongCityFormatException();
		return this.token.sval;
	}	
	
	private void forceString(String expected) throws IOException {
		if ((this.token.nextToken() != StreamTokenizer.TT_WORD))
			throw new WrongCityFormatException();
		if (this.token.sval == null)
			throw new WrongCityFormatException();
		else if (!this.token.sval.equals(expected))
			throw new WrongCityFormatException();
	}
		
	private void parsePlaces() throws IOException {
		int i = 0;
		this.forceString("BeginPlaces");
		this.token.nextToken();
		while ((this.token.sval != null) && (!this.token.sval.equals("EndPlaces"))) {
			Place p = this.parsePlace(i);
			CityLoaderFromTxtFile.places.add(p);
			i++;
			this.token.nextToken();
		}
	}
	
	private Place parsePlace(int num) throws IOException {
		if((this.token.sval == null) || (!this.token.sval.equals("place")))
			throw new WrongCityFormatException();
		this.forceNumber(num);
		String name = this.forceString();
		String description = this.forceString();
		description = description.replace("_", " "); // Sustituye los guiones bajos por espacios
		String s = this.forceString();
		boolean isSpaceShip;
		if (s.equals("spaceShip"))
			isSpaceShip = true;
		else if (s.equals("noSpaceShip"))	
			isSpaceShip = false;
		else
			throw new WrongCityFormatException();
		return new Place(name, isSpaceShip, description);
	}
	
	private void parseStreets() throws IOException {
		this.forceString("BeginStreets");
		int i = 0;
		this.token.nextToken();
		while ((this.token.sval != null) && (!this.token.sval.equals("EndStreets"))) {
			Street s = this.parseStreet(i);
			CityLoaderFromTxtFile.streets.add(s);
			i++;
			this.token.nextToken();
		}
	}
	
	private Street parseStreet(int num) throws IOException {
		if ((this.token.sval == null) || !this.token.sval.equals("street"))
			throw new WrongCityFormatException();
		
		this.forceNumber(num);
		this.forceString("place");
		int n1 = this.forceNumber();
		if ((n1 < 0) || (n1 >= CityLoaderFromTxtFile.places.size()))
			throw new WrongCityFormatException();
		
		String s = this.forceString();
		Direction direction;
		if (s.equals("north"))
			direction = Direction.NORTH;
		else if (s.equals("south"))
			direction = Direction.SOUTH;
		else if (s.equals("east"))
			direction = Direction.EAST;
		else if (s.equals("west"))
			direction = Direction.WEST;
		else
			throw new WrongCityFormatException();
		
		this.forceString("place");
		int n2 = this.forceNumber();
		if ((n2 < 0) || (n2 >= CityLoaderFromTxtFile.places.size()))
			throw new WrongCityFormatException();
			
		String openclose = this.forceString();
		if (openclose.equals("open")) {
			return new Street(CityLoaderFromTxtFile.places.get(n1), direction, CityLoaderFromTxtFile.places.get(n2));
		}
		else if (openclose.equals("closed")) {
			String codigo = this.forceString();
			return new Street(CityLoaderFromTxtFile.places.get(n1), direction, CityLoaderFromTxtFile.places.get(n2), false, codigo);
		}
		else
			throw new WrongCityFormatException();
	}

	private void parseItems() throws IOException {
		this.forceString("BeginItems");
		int i = 0;
		this.token.nextToken();
		while ((this.token.sval != null) && (!this.token.sval.equals("EndItems"))) {
			Item it = this.parseItem(i);
			Place place = CityLoaderFromTxtFile.places.get((int)this.token.nval);
			place.addItem(it);
			i++;
			this.token.nextToken();
		}
	}
	
	private Item parseItem(int num) throws IOException {
		Item item = null;
		String typeItem = this.token.sval;
		if (!typeItem.equals("fuel") && !typeItem.equals("garbage") && !typeItem.equals("codecard"))
			throw new WrongCityFormatException();
		else {
			this.forceNumber(num);
			String name = this.forceString();
			String description = this.forceString();
			description = description.replace("_", " "); // Sustituye los guiones bajos por espacios		
			if (typeItem.equals("fuel")){
				int energy = this.forceNumber();
				int times = this.forceNumber();
				item = new Fuel(name, description, energy, times);
			}
			else if (typeItem.equals("garbage")){
				int recycledMaterial = this.forceNumber();
				item = new Garbage(name, description, recycledMaterial);
			}
			else { // typeItem.equals("codecard")
				String code = this.forceString();
				item = new CodeCard(name, description, code);
			}
			this.forceString("place");
			int pos = this.forceNumber();
			if ((pos < 0) || (pos >= CityLoaderFromTxtFile.places.size()))
				throw new WrongCityFormatException();			
		}
		return item;
	}

	private static ArrayList<Place> places;
	private static ArrayList<Street> streets;
	private StreamTokenizer token;
	
}
