package com.iti.EShopper.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SiteParser {
    public static SiteParser instanceData;
    static Client client;
    static WebResource webResource;
    ObjectMapper mapper = new ObjectMapper();
    public SiteParser(){
        client =Client.create();
        instanceData = this;
    }


    public List<Product> getProducts() throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/getProducts");
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        List<Product> products = null;
        if (response.getStatus() == 200) {
            String res = response.getEntity(String.class);
            System.out.println(res);
            products = Arrays.asList(mapper.readValue(res, Product[].class));
        } else {
            System.out.println(response.getStatus());
        }
        return products;
    }

    public List<Cart> getCart() throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/getCart");
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        List<Cart> products = null;
        if (response.getStatus() == 200) {
            String res = response.getEntity(String.class);
            System.out.println(res);
            products = Arrays.asList(mapper.readValue(res, Cart[].class));
        } else {
            System.out.println(response.getStatus());
        }
        return products;
    }

    public List<Product> getProduct(int id) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/getProduct/"+id);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        List<Product> products = null;
        if (response.getStatus() == 200) {
            String res = response.getEntity(String.class);
            System.out.println(res);
            products = Arrays.asList(mapper.readValue(res, Product[].class));
        } else {
            System.out.println(response.getStatus());
        }
        return products;
    }

    public List<Product> getProduct(String title) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/getProductWithTitle/"+title);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        List<Product> products = null;
        if (response.getStatus() == 200) {
            String res = response.getEntity(String.class);
            System.out.println(res);
            products = Arrays.asList(mapper.readValue(res, Product[].class));
        } else {
            System.out.println(response.getStatus());
        }
        return products;
    }

    public void addToCart(Cart cart) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/addToCart");
        String jsonString = mapper.writeValueAsString(cart);
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);

        if (response.getStatus() == 200) {
            System.out.println(res);
            System.out.println(response.getStatus());
        } else {
            System.out.println(res);
            System.out.println(response.getStatus());
        }
    }

    public void deleteFromCart(int productid, int userid) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/deleteFromCart");
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("productid", productid);
        hashMap.put("userid", userid);
        String jsonString = mapper.writeValueAsString(hashMap);
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);
        if (response.getStatus() == 200) {
            System.out.println(res);
            System.out.println(response.getStatus());
        } else {
            System.out.println(response.getStatus());
        }
    }

    public int AddProduct(String title, int Price, int Quantity, String photoUrl, String details, String category) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/addProduct");
        Product product=new Product(0,title,Price,Quantity,photoUrl,details,category);

        String jsonString = mapper.writeValueAsString(product);
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);
        if (response.getStatus() == 200) {
            System.out.println(res);
            System.out.println(response.getStatus());
            return 1;
        } else {
            System.out.println(response.getStatus());
            return  -1;
        }
    }

    public String checkSignUp(boolean admin, String name, Date birthday, String password, int phonenumber, String job, String email, int creditlimit, String address, String interests) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/checkSignUp");
        User product=new User(0,admin,name,String.valueOf(birthday),password,phonenumber,job,email,creditlimit,address,interests);

        String jsonString = mapper.writeValueAsString(product);
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);
        if (response.getStatus() == 200) {
            System.out.println(res);
            System.out.println(response.getStatus());
            return "SignedUp Successfully";
        } else {
            System.out.println(response.getStatus());
            return "username or email already exists";
        }
    }

    public User checkSignIn(String email, String password) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/checkSignIn");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password);

        String jsonString = mapper.writeValueAsString(hashMap);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);
        if (response.getStatus() == 200) {
            System.out.println(res);
            System.out.println(response.getStatus());
            Gson gson = new Gson();
            return gson.fromJson(res, User.class);
        } else {
            System.out.println(response.getStatus());
            return null;
        }
    }

    public int editProduct(int price, int quantity, int id) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/editProduct");

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("price", price);
        hashMap.put("quantity", quantity);
        hashMap.put("id", id);

        String jsonString = mapper.writeValueAsString(hashMap);
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);
        if (response.getStatus() == 200) {
            System.out.println(res);
            System.out.println(response.getStatus());
            return 1;
        } else {
            System.out.println(response.getStatus());
            return -1;
        }
    }

    public String checkout(User user) throws IOException {
        webResource = client.resource("http://localhost:8080/E_commerce_API/api/eShopper/checkout");

        HashMap<String,User> HashMap = new HashMap<>();
        HashMap.put("user", user);

        String jsonString = mapper.writeValueAsString(HashMap);
        System.out.println(jsonString);

        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonString);
        String res = response.getEntity(String.class);
        if (response.getStatus() == 200) {
            System.out.println(res);
        }
        System.out.println(response.getStatus());
        return res;

    }

}
