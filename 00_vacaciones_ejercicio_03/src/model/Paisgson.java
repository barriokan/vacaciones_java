package model;

public class Paisgson {

	private String region;
	private String name;
	private String capital;
	private int population;
	private int area;
	private double [] latlng;
	public Paisgson(String region, String name, String capital, int population, int area, double[] latlng) {
		super();
		this.region = region;
		this.name = name;
		this.capital = capital;
		this.population = population;
		this.area = area;
		this.latlng = latlng;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public double[] getLatlng() {
		return latlng;
	}
	public void setLatlng(double[] latlng) {
		this.latlng = latlng;
	}
	
	
	
}
