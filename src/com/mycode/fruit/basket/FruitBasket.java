package com.mycode.fruit.basket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class FruitBasket implements Utility{

	public static void main(String args[]) {
		
		//String fileName="fruitBasket.csv";
		List<FruitDTO> lsFruitDTO= new ArrayList<FruitDTO>();
		
		AtomicReference<Integer> data = new AtomicReference<>();
		data.set(0);
		AtomicReference<Integer> count = new AtomicReference<>();
		count.set(1);
		
		StringBuffer message=new StringBuffer("Oldest fruit & age: \n");
		try {
			
			Scanner scanner = new Scanner(new InputStreamReader(System.in));
	        System.out.println("Please enter your file path and then Enter: ");
	        String fileName = scanner.nextLine();
	        System.out.println("\nYou entered the path: " + fileName +"\n");
	        scanner.close();
	       
	        if(fileName!=null && !fileName.endsWith(".csv")) {  // If file name does not have .csv at end , throws the Error
	        	throw new Exception("The file is not in CSV extension(.csv)");
	        }
	       
			/*This is Functional Interface used to parse the CSV files contents and validate the format and convert a list of value object to process further easily
			 */
			Function <String , FruitDTO> convertToDTO=(line) ->{
				FruitDTO fruitDTO= new FruitDTO();
				count.set(count.get()+1);
				boolean validateLineFormat=Utility.validateLineFormat(line);
				if(validateLineFormat) { // If file is well format the proceed further to process
					String[] x = line.split(",");// a CSV has comma separated lines
					fruitDTO.setType(x[0]);
					boolean validateAgeInDays=Utility.validateAgeInDays(x[1]); // If the age-in-days is numeric , proceed further
					if(validateAgeInDays) {
						fruitDTO.setAgeInDays(Integer.parseInt(x[1]));
						if(data.get()<Integer.parseInt(x[1])) {
							data.set(Integer.parseInt(x[1]));  // Assigning the oldest age of a fruit.
						}					
					fruitDTO.setCharacteristic1(x[2]);
					fruitDTO.setCharacteristic2(x[3]);
					}else {
						System.out.println("age-in-days column should be numeric for the line "+ count.get());
						System.exit(0); //Exit if the age-in-days is not a numeric and here could have used return null
					}
				}else
				{
					System.out.println("The file is not correct formatted for the line "+ count.get());	
					System.exit(0); //Exit if the file is not a well formatted and here could have used return null
				}
				
				return fruitDTO;
			};
			
			/*This is Functional Interface(Consumer) used to fetch the oldest fruit and age
			 */
			
			Consumer<FruitDTO> deriveOldestFruitAge = (FruitDTO x) -> {
				if(data.get()==x.getAgeInDays()) {
					message.append(x.getType()+":"+data.get()+"\n");
				}
			};
        
			/*This is Functional Interface(Consumer) used to print exact format of the output has been asked in the requirement for showing various 
			 * characteristics (count, color, shape, etc.) of each fruit by type. The Map is the result of group by types and each fruit's character and here printing the message in required format
			 */
            Consumer<Map.Entry<String,Map<String,Map<String,Long>>>> groupbyMap = (Map.Entry<String,Map<String,Map<String,Long>>> x) -> {
            	x.getValue().entrySet().forEach(y->{y.getValue().entrySet().forEach(z->{System.out.println(z.getValue() +" "+ x.getKey()+": "+ y.getKey()+", "+ z.getKey());});});
       	
            };
            
			Stream<String> lines= Files.lines(Paths.get(fileName));
			lsFruitDTO=lines.skip(1).map(convertToDTO).collect(Collectors.toList()); //Skipping the header of the file
			lines.close();

			//Grouping by fruits type and their Characters
		    Map<String, Map<String,  Map<String, Long>>> fruitDTOGroupBy = lsFruitDTO.stream()
					  .collect(Collectors.groupingBy(FruitDTO::getType,Collectors.groupingBy(FruitDTO::getCharacteristic1,Collectors.groupingBy(FruitDTO::getCharacteristic2,Collectors.counting()))));
		
		
			
			System.out.println("Total Number of fruit: \n"+lsFruitDTO.size() +"\n");
			System.out.println("Total types of fruit: \n"+fruitDTOGroupBy.size()+"\n");
			
			//deriveOldestFruitAge  is the consumer where the lambda expression derive the Oldest fruit & age
			lsFruitDTO.stream().forEach(deriveOldestFruitAge); 
			System.out.println(message+"\n");
		
			//Grouping by fruits type by descending order
			Map<String, Long> fruitDTOPerType = lsFruitDTO.stream()
					  .collect(Collectors.groupingBy(FruitDTO::getType,Collectors.counting()));
			System.out.println("The number of each type of fruit in descending order:");
			fruitDTOPerType.entrySet().stream().forEach(s->System.out.println(s.getKey()+": "+ s.getValue()));
			
			System.out.println("\nThe various characteristics (count, color, shape, etc.) of each fruit by type: ");
			
			//groupbyMap is the Consumer where the lambda expression converts the map in required format
		    fruitDTOGroupBy.entrySet().stream().forEach(groupbyMap);	
			 
		}
		catch (NoSuchFileException e)  { //If the file does not exist or wrong file it throws the exception with proper message 
			System.out.println("No such file in the specified path: "+e.getMessage());
		}
		catch (IOException e)  {
			System.out.println("IOException occured :: "+e.getMessage());
		}
		catch (Exception e)  {
			System.out.println(e.getMessage());
		}
	}
	
	
}
