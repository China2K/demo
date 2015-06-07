package com.demo.solr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.solr.client.solrj.SolrServerException;

import com.demo.solr.model.Product;

public class Startup {
	private static final Logger logger = Logger.getLogger(Startup.class);

	public static void main(String[] args) throws IOException, ParseException, SolrServerException {
		System.out.println(new Date());
		testSearch();
		System.out.println(new Date());
	}

	
	
	
	public static void testInsert() throws IOException, SolrServerException{
		List<Product> products =new ArrayList<Product>();
		 Random ran = new Random();
		 File dataFile=new File("E://data.txt");
		 FileWriter fileWriter=new FileWriter(dataFile);  
		 BufferedWriter bw=new BufferedWriter(fileWriter);  
		for(int i=0;i<100;i++){
			String id=UUID.randomUUID().toString();
			String name="test"+i+ran.nextInt(5000);
			String sn="sn-"+333;
			String keywords="just test-"+UUID.randomUUID().toString().replace("-", "");
			products.add(new Product(id, name, keywords, null, sn));
			bw.write(id); 
			bw.write("\t");
			bw.write(name);  
			bw.write("\t");
			bw.write(keywords);  
			bw.write("\t");
			bw.write(sn);  
			bw.newLine();
		}
		  fileWriter.flush();
		  bw.close();
		  fileWriter.close();
		System.out.println(products.size());
		
		SolrIndexUtils.addIndexs(products);
	}
	
	
	public static void testSearch() throws SolrServerException, IOException{
		List<Product> search = SolrQueryUtils.search("test2833", null, null, null, 0, 20);
		for (Product product : search) {
			
			System.out.println(product.getId()+"||||||"+product.getName());
		}
	}
}
