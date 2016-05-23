
public class Gerbil {
	private String id;
	private String name;
	private Integer[] amountFood;
	private Boolean bite;
	private Boolean flight;
	private Integer percentage;
	private String averageFood;
	private String willBite;
	private String willFlight;
	
	public Gerbil() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getBite() {
		return bite;
	}
	public void setBite(Boolean bite) {
		this.bite = bite;
	}
	public Boolean getFlight() {
		return flight;
	}
	public void setFlight(Boolean flight) {
		this.flight = flight;
	}
	public Integer getAmountFood(int i) {
		return amountFood[i];
	}
	public void setAmountFood(Integer[] amountFood) {
		this.amountFood = amountFood;
	}
	public String getAverageFood() {
		return averageFood;
	}
	public void setAverageFood(String averageFood) {
		this.averageFood = averageFood;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public String averageFood() 
	{
		System.out.println(getId() + " " + getName() + " " + getPercentage() + "%");
		
		return averageFood;
	}
	public String getWillBite() {
		return willBite;
	}
	public void setWillBite(String willBite) {
		this.willBite = willBite;
	}
	public String getWillFlight() {
		return willFlight;
	}
	public void setWillFlight(String willFlight) {
		this.willFlight = willFlight;
	}
	
}
