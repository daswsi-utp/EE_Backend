package com.report_service.entities.data;

import java.util.ArrayList;

public class Product {

    public int id;
    public String image;
    public String name;
    public String description;
    public String longDescription;
    public String category;
    public double price;
    public String discount;
    public double rating;
    public int reviewCount;
    public int stock;
    public boolean isNew;
    public String materialInfo;
    public String dimensions;
    public String weight;
    public String capacity;
    public String care;
    public String warranty;
    public String origin;
    public ArrayList<String> tags;
    public ArrayList<String> highlights;
    public ArrayList<Review> reviews;
    public ArrayList<Integer> relatedProducts;
}
