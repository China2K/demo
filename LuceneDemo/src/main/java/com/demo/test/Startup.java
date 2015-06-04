package com.demo.test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Startup
{
	private static final Logger logger = Logger.getLogger(Startup.class);
	
	private ApplicationContext context;

	public ApplicationContext getContext()
	{
		return context;
	}

	public void setContext(ApplicationContext context)
	{
		this.context = context;
	}
	
	public static void main(String[] args) throws IOException, ParseException
	{
		Startup startup = new Startup();
		String[] locations = new String[]{"applicationContext-*.xml"};
		ApplicationContext _context = new ClassPathXmlApplicationContext(locations);
		startup.setContext(_context);
		logger.info("加载Spring容器到BeanFactory...");
		System.out.println("11111111111111111111");
		/*List<Product> products =new ArrayList<Product>();
		 Random ran = new Random();
		 File dataFile=new File("E://data.txt");
		 FileWriter fileWriter=new FileWriter(dataFile);  
		 BufferedWriter bw=new BufferedWriter(fileWriter);  
		for(int i=0;i<2;i++){
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
		try {
			System.out.println("start build index "+new Date());
			IndexUtils.rebuildOrUpdateIndex(products, false);
			System.out.println("end build index"+new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		String keywords=" 333"	;
		List<Product> search = SearchUtils.search(keywords);
		File resFile=new File("E://resFile.txt");
		 FileWriter fileWriter=new FileWriter(resFile);  
		 BufferedWriter bw=new BufferedWriter(fileWriter);  
		 for (Product product : search) {
			 bw.write(product.getId()); 
			 bw.write("\t");
//			 bw.write(product.getName());  
//			 bw.write("\t");
			 bw.write(product.getKeywords());  
			 bw.write("\t");
			 bw.write(product.getSn());  
			 bw.newLine();
		}
		 fileWriter.flush();
		 bw.close();
		 fileWriter.close();
		
	}

}
