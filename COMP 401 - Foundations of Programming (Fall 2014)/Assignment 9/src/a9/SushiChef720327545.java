package a9;

import java.util.ArrayList;
import java.util.Random;

import comp401.sushi.*;

public class SushiChef720327545 implements SushiChef {
	private Plate plate_made;
	private double out_cost = 0;
	private double chef_profit = 0;
	
	public SushiChef720327545() {
	}

	@Override
	public String getName() {
		return "Franz Dominno";
	}

	@Override
	public int getPID() {
		return 720327545;
	}

	@Override
	public Plate makePlate() {
		Random r = new Random();
		Roll roll_made = null;
		Sashimi sashimi_made = null;
		Nigiri nigiri_made = null;
		Sushi sushi_made = null;
		double sushi_made_cost = 0;
		Plate check_plate = null;
		double check_plate_cost = 0;
		ArrayList<Ingredient> ingredient_arraylist = new ArrayList<Ingredient>();
		
		while (sushi_made_cost >= check_plate_cost) {
			//Roll
			int choose_roll_type = r.nextInt(2 - 1 + 1) + 1;
			
			double choose_avocado = ((double)r.nextInt(1001)) / 1000; 
			double choose_rice = ((double)r.nextInt(1001)) / 1000;
			double choose_seaweed = ((double)r.nextInt(1001)) / 1000;
			
			double choose_crab = ((double)r.nextInt(1001)) / 1000;
			double choose_eel = ((double)r.nextInt(1001)) / 1000;
			double choose_salmon = ((double)r.nextInt(1001)) / 1000;
			double choose_shrimp = ((double)r.nextInt(1001)) / 1000;
			double choose_tuna = ((double)r.nextInt(1001)) / 1000;
			
			if (choose_roll_type == 1) {
				ingredient_arraylist.add(new Avocado(choose_avocado));
				ingredient_arraylist.add(new Rice(choose_rice));
				ingredient_arraylist.add(new Seaweed(choose_seaweed));
			} else if (choose_roll_type == 2) {
				ingredient_arraylist.add(new Avocado(choose_avocado));
				ingredient_arraylist.add(new Rice(choose_rice));
				ingredient_arraylist.add(new Seaweed(choose_seaweed));
				ingredient_arraylist.add(new Crab(choose_crab));
				ingredient_arraylist.add(new Eel(choose_eel));
				ingredient_arraylist.add(new Salmon(choose_salmon));
				ingredient_arraylist.add(new Shrimp(choose_shrimp));
				ingredient_arraylist.add(new Tuna(choose_tuna));
			}
			
			Ingredient[] ingredient_list = 
					new Ingredient[ingredient_arraylist.size()];
			ingredient_list = ingredient_arraylist.toArray(ingredient_list);
			
			switch (choose_roll_type) {
			case 1: roll_made = new Roll(ingredient_list);
				break;
			case 2: roll_made = new Roll(ingredient_list);
				break;
			}
			
			//Sashimi
			int choose_sashimi = r.nextInt(5 - 1 + 1) + 1;
			
			switch (choose_sashimi) {
			case 1: sashimi_made = new Sashimi(Sashimi.SashimiType.CRAB);
				break;
			case 2: sashimi_made = new Sashimi(Sashimi.SashimiType.EEL);
				break;
			case 3: sashimi_made = new Sashimi(Sashimi.SashimiType.SALMON);
				break;
			case 4: sashimi_made = new Sashimi(Sashimi.SashimiType.SHRIMP);
				break;
			case 5: sashimi_made = new Sashimi(Sashimi.SashimiType.TUNA);
				break;
			}
			
			//Nigiri
			int choose_nigiri = r.nextInt(5 - 1 + 1) + 1;
			
			switch (choose_nigiri) {
			case 1: nigiri_made = new Nigiri(Nigiri.NigiriType.CRAB);
				break;
			case 2: nigiri_made = new Nigiri(Nigiri.NigiriType.EEL);
				break;
			case 3: nigiri_made = new Nigiri(Nigiri.NigiriType.SALMON);
				break;
			case 4: nigiri_made = new Nigiri(Nigiri.NigiriType.SHRIMP);
				break;
			case 5: nigiri_made = new Nigiri(Nigiri.NigiriType.TUNA);
				break;
			}
			
			//Sushi
			int choose_sushi = r.nextInt(3 - 1 + 1) + 1;
			
			switch (choose_sushi) {
			case 1: sushi_made = roll_made;
				sushi_made_cost = sushi_made.getCost();
				break;
			case 2: sushi_made = sashimi_made;
				sushi_made_cost = sushi_made.getCost();
				break;
			case 3: sushi_made = nigiri_made;
				sushi_made_cost = sushi_made.getCost();
				break;
			}
						
			//Plate color
			int choose_color = r.nextInt(4 - 1 + 1) + 1;
			double choose_gold_price = 
					((double)r.nextInt(1500 - 500 + 1) + 500) / 100;
			
			switch (choose_color) {
			case 1: check_plate_cost = 4.0;
				try {
					check_plate = new BluePlate(sushi_made);
				} catch (PlatePriceException e) {
					check_plate_cost = 0;
				}
				break;
			case 2: check_plate_cost = choose_gold_price;
				try {
					check_plate = new GoldPlate(sushi_made, choose_gold_price);
				} catch (PlatePriceException e) {
					check_plate_cost = 0;
				}
				break;
			case 3: check_plate_cost = 2.0;
				try {
					check_plate = new GreenPlate(sushi_made);
				} catch (PlatePriceException e) {
					check_plate_cost = 0;
				}
				break;
			case 4: check_plate_cost = 1.0;
				try {
					check_plate = new RedPlate(sushi_made);
				} catch (PlatePriceException e) {
					check_plate_cost = 0;
				}
				break;
			}
		}
		
		plate_made = check_plate;
		out_cost =+ plate_made.getContents().getCost();
		return plate_made;
	}

	@Override
	public void observePurchase(String customer, Plate plate, int chef_pid) {
		//String customer_id = customer;
		Plate plate_bought = plate;
		int chef_id = chef_pid;
		
		if (chef_id == 720327545) {
			chef_profit =+ plate_bought.getPrice();
			chef_profit =- plate_bought.getContents().getCost();
		}
	}

	@Override
	public void observeSpoilage(Plate plate, int chef_pid) {
		Plate plate_spoiled = plate;
		int chef_id = chef_pid;
		
		if (chef_id == 720327545) {
			chef_profit =- plate_spoiled.getContents().getCost();
		}
	}

	@Override
	public double getOutstandingCost() {
		return out_cost;
	}

	@Override
	public double getProfit() {
		return chef_profit;
	}

}