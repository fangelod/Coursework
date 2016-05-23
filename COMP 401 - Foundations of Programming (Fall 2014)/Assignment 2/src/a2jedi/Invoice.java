package a2jedi;

import java.util.ArrayList;

public class Invoice {
	private String customer;
	private double total_cost;
	private ArrayList<InvoiceItem> item_list = new ArrayList<InvoiceItem>();
	private InvoiceItem ref;
	private int invoice_number = 0;
	private static int addnumber = 0;
	private ArrayList<InvoiceItem> tfilter_list = new ArrayList<InvoiceItem>();
	private ArrayList<InvoiceItem> ffilter_list = new ArrayList<InvoiceItem>();
	private InvoiceItem[] tlist;
	private InvoiceItem[] flist;
	
	public Invoice(String customer) {
		this.customer = customer;
		addnumber++;
		invoice_number = addnumber;
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
	public int getInvoiceNumber() {
		return invoice_number;
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
	public InvoiceItem[] findItemsByNameFilter(String filter, boolean strict_prefix) {
		int trigger = item_list.size();
		if (strict_prefix == true) {
			for (int i = 0; i < item_list.size(); i++) {
				if (item_list.get(i).getName().startsWith(filter)) {
					tfilter_list.add(item_list.get(i));
				}
				if (!item_list.get(i).getName().startsWith(filter)) {
					trigger--;
				}
					
			}
			if (trigger == 0) {
				return null;
			}
			tlist = tfilter_list.toArray(new InvoiceItem[tfilter_list.size()]);
			return tlist;
		}
		if (strict_prefix == false) {
			for (int i = 0; i < item_list.size(); i++) {
				if (item_list.get(i).getName().contains(filter)) {
					ffilter_list.add(item_list.get(i));
				}
				if (!item_list.get(i).getName().contains(filter)) {
					trigger--;
				}
			}
			if (trigger == 0) {
				return null;
			}
			flist = ffilter_list.toArray(new InvoiceItem[ffilter_list.size()]);
			return flist;
		}
		return null;
	}
	public Invoice separateByNameFilter(String filter, boolean strict_prefix) {
		InvoiceItem[] sep = findItemsByNameFilter(filter, strict_prefix);
		if (sep != null) {
			for (int i = 0; i < sep.length; i++) {
				removeFromInvoice(sep[i].getName());
			}
			Invoice separated = new Invoice(customer);
			for (int i = 0; i < sep.length; i++) {
				separated.addToInvoice(sep[i]);
			}
			return separated;
		}	
		return null;
	}
}
