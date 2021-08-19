package com.mycode.fruit.basket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class FruitBasketTest {
	AtomicReference<Integer> data = new AtomicReference<>();
	List<FruitDTO> lsFruitDTO= new ArrayList<FruitDTO>();
	
	Function <String , FruitDTO> convertToDTO=(line) ->{
		FruitDTO fruitDTO= new FruitDTO();
			String[] x = line.split(",");// a CSV has comma separated lines
			fruitDTO.setType(x[0]);
			fruitDTO.setAgeInDays(Integer.parseInt(x[1]));
			if(data.get()==null) {
				data.set(0); 
			}
			if(data.get()<Integer.parseInt(x[1])) {
				data.set(Integer.parseInt(x[1]));  // Assigning the oldest age of a fruit.
			}					
			fruitDTO.setCharacteristic1(x[2]);
			fruitDTO.setCharacteristic2(x[3]);
		return fruitDTO;
	};
	
	
    
    
	@Test
	void testValidateLineFormat() {
		String correctLine="apple,1,red,sweet";
		boolean validate1=Utility.validateLineFormat(correctLine);
		assertTrue(validate1);
		String badLine="apple1,red,sweet";
		boolean validate2=Utility.validateLineFormat(badLine);
		assertFalse(validate2);
	}
	
	@Test
	void testValidateLineAgeInDays() {
		String correctLine="2";
		boolean validate1=Utility.validateAgeInDays(correctLine);
		assertTrue(validate1);
		String badLine="x";
		boolean validate2=Utility.validateAgeInDays(badLine);
		assertFalse(validate2);
	}
	@Test
	public void testNoSuchFileinSpecifiedPath() throws IOException {
		String fileName="filedoesnotExist.csv";
		assertThrows(NoSuchFileException.class, () -> {
			Files.lines(Paths.get(fileName));
		  });
	}
	
	@Test
	public void testTotalNumberOfFruit() throws IOException {
		String fileName="fruitBasket.csv";
	    Stream<String> lines= Files.lines(Paths.get(fileName));
	    List<String[]> lsFruitDTO= new ArrayList<String[]>();
	    lsFruitDTO=lines.skip(1).map(line->line.split(",")).collect(Collectors.toList());
	    lines.close();
	    assertNotNull("List is not null",lsFruitDTO);
	    assertEquals("Total number of fruit:",lsFruitDTO.size(),22);

	}
	
	@Test
	public void testTotalTypesOfFruit() throws IOException {
		String fileName="fruitBasket.csv";
	    Stream<String> lines= Files.lines(Paths.get(fileName));
	    lsFruitDTO=lines.skip(1).map(convertToDTO).collect(Collectors.toList());
	    lines.close();
	    Map<String, Long> fruitDTOPerType = lsFruitDTO.stream()
				  .collect(Collectors.groupingBy(FruitDTO::getType,Collectors.counting()));
	    assertNotNull("Fruit List is not null",lsFruitDTO);
	    assertNotNull("FruitMapOfType is not null",fruitDTOPerType);
	    assertEquals("Total types of fruit:",fruitDTOPerType.size(),5);
	   		
	}
	
	@Test
	public void testOldestFruitAge() throws IOException {
		String fileName="fruitBasket.csv";
	    Stream<String> lines= Files.lines(Paths.get(fileName));
	    lsFruitDTO=lines.skip(1).map(convertToDTO).collect(Collectors.toList());
	    lines.close();
	    StringBuffer message=new StringBuffer("Oldest fruit & age: \n");
	    Consumer<FruitDTO> deriveOldestFruitAge = (FruitDTO x) -> {
			if(data.get()==x.getAgeInDays()) {
				message.append(x.getType()+":"+data.get()+"\n");
			}
		};
		lsFruitDTO.stream().forEach(deriveOldestFruitAge); 
		assertTrue(message.toString().contains("orange"));
		assertFalse(message.toString().contains("watermelon:"));
		assertTrue(message.toString().contains("pineapple:"));
		assertFalse(message.toString().contains("grapefruit:"));
		assertTrue(message.toString().contains("6"));
		
	}
	
	
	@Test
	public void testFruitTypeInDescOrder() throws IOException {
		String fileName="fruitBasket.csv";
	    Stream<String> lines= Files.lines(Paths.get(fileName));
	    lsFruitDTO=lines.skip(1).map(convertToDTO).collect(Collectors.toList());
	    lines.close();
	    Map<String, Long> fruitDTOPerType = lsFruitDTO.stream()
				  .collect(Collectors.groupingBy(FruitDTO::getType,Collectors.counting()));
	    assertNotNull("Fruit List is not null",lsFruitDTO);
	    assertNotNull("FruitMapOfType is not null",fruitDTOPerType);
	    assertEquals("The first fruit in decending order:",fruitDTOPerType.keySet().stream().findFirst().get(),"orange");
	    assertEquals("The number of orange:",(long)fruitDTOPerType.values().stream().findFirst().get(),6);
		
	}
	
	@Test
	public void testVariousCharacteristics() throws IOException {
		String fileName="fruitBasket.csv";
	    Stream<String> lines= Files.lines(Paths.get(fileName));
	    lsFruitDTO=lines.skip(1).map(convertToDTO).collect(Collectors.toList());
	    lines.close();
	    List<String> str= new ArrayList<String>();
	    Consumer<Map.Entry<String,Map<String,Map<String,Long>>>> groupbyMap = (Map.Entry<String,Map<String,Map<String,Long>>> x) -> {
	    	x.getValue().entrySet().forEach(y->{y.getValue().entrySet().forEach(z->{
	    		 str.add(z.getValue() +" "+ x.getKey()+": "+ y.getKey()+", "+ z.getKey());
	    		});});
		
	    };
	    
	  //Grouping by fruits type and their Characters
	    Map<String, Map<String,  Map<String, Long>>> fruitDTOGroupBy = lsFruitDTO.stream()
				  .collect(Collectors.groupingBy(FruitDTO::getType,Collectors.groupingBy(FruitDTO::getCharacteristic1,Collectors.groupingBy(FruitDTO::getCharacteristic2,Collectors.counting()))));
	    fruitDTOGroupBy.entrySet().stream().forEach(groupbyMap);
	   
	    assertNotNull("Fruit Map groupby type is not null",fruitDTOGroupBy);
	    assertEquals("size of may group by type and Characteristics",str.size(),11);
	    assertEquals("5 orange: round, sweet",str.get(0));
	    assertEquals("2 watermelon: heavy, green",str.get(10));
	    assertTrue(str.contains("1 apple: green, tart"));
	    assertFalse(str.contains("5 grapefruit: yellow, bitter"));
	}

}
