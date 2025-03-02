package com.sheryians.java.global;

import java.util.ArrayList;
import java.util.List;

import com.sheryians.java.model.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
}
