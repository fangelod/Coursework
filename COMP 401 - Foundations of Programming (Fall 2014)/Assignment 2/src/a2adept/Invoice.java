package a2adept;

import java.util.ArrayList;

public class Invoice {
	private String customer;
	private double total_cost;
	private ArrayList<InvoiceItem> item_list = new ArrayList<InvoiceItem>();
	private InvoiceItem ref;
	
	public Invoice(String customer) {
		this.customer = customer;
	}
	public String getCustomer() {
		return customer;
	}
	public double getTotalCost() {
		total_cost = 0;
		for (int i = 0; i < item_list.size(); i++) {
			int x = item_list.get(i).getUnitCount();
			double y = item_list.get(i).getPricePerUnit();
			double z = x * y;
			total_cost += z;
		}
		return total_cost;
	}	
	public InvoiceItem findItemByName(String name) {
		for (int i = 0; i < item_list.size(); i++) {
			if (item_list.get(i).getName().equals(name)) {
				return item_list.get(i);
			}	
		}
		return null;
	}
	public InvoiceItem removeFromInvoice(String name) {
		for (int i = 0; i < item_list.size(); i++) {
			if (item_list.get(i).getName().equals(name)) {
				InvoiceItem ref = findItemByName(name);
				total_cost = 0;
				item_list.remove(item_list.get(i));
				return ref;
			}
		}
		return null;
	}
	public void addToInvoice(InvoiceItem new_item) {
		InvoiceItem x = findItemByName(new_item.getName());
		if (x != null) {
			ref = x;
			removeFromInvoice(new_item.getName());
			int newcount = ref.getUnitCount() + new_item.getUnitCount();
			double newprice = ((ref.getPricePerUnit() * ref.getUnitCount()) + (new_item.getPricePerUnit() * new_item.getUnitCount())) / newcount;
			InvoiceItem addobj = new InvoiceItem(new_item.getName(), newprice, newcount);
			item_list.add(addobj);
		}
		else {
			item_list.add(new_item);
		}
	}	
}	