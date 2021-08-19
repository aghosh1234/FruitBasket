# FruitBasket

## Overview
FruitBasket is a console application built on Java 8 for allowing users to provide a CSV file and produce the summary report from it.

## Features
 * The application asks for a specified path to process the file and generate the report summary in console. 
 * Validates the correct format of the file and parse it's contents.
 * Provides proper error message if file does not exist in the specified path what user enters.
 * Provides proper error message if file does not satisfy as CSV format or not a CSV extension. 
 * Checks the column of the file has correct format.
 * Validates the output of the functional requirements by writing Junit test cases.  
 * Builds on Java 8 and based on Functional Programming so very minimal and concise coding.

 
## Versions
 * 1.0.0 - Initial Commit

## Setup
Here are few things to configure to start with FruitBasket.
 * Copy the CSV file in any of your local folder(A CSV file has been uploaded to Github named fruitBasket.csv).
 * Download the executable Jar file from GitHub (An executable Jar file has been uploaded along with Source code).
 * In terminal go to the path where you downloaded the jar.
 * Execute the "java -jar <jar name>"(FruitBasket.jar).(Assuming java is installed in your classpath and for reference Main class is com.mycode.fruit.basket.FruitBasket.java and kept in manifest.txt)
 * It will ask to enter the path where your CSV file located and then Enter.
 * If the file is well formatted it will show the desired output in console
 * You can run some negative scenarios by specifying  wrong path or wrong file extension and updating the incorrect format. 

## Sunny case scenarios

$ java -jar FruitBasket.jar
Please enter your file path and then Enter: 
/Users/aghosh005c/Desktop/fruitBasket.csv

You entered the path: /Users/aghosh005c/Desktop/fruitBasket.csv

Total Number of fruit: 
22

Total types of fruit: 
5

Oldest fruit & age: 
pineapple:6
orange:6


The number of each type of fruit in descending order:
orange: 6
apple: 5
pineapple: 4
grapefruit: 4
watermelon: 3

The various characteristics (count, color, shape, etc.) of each fruit by type: 
5 orange: round, sweet
1 orange: sweet, round
3 apple: red, sweet
1 apple: green, tart
1 apple: yellow, sweet
2 pineapple: prickly, sweet
2 pineapple: sweet, prickly
2 grapefruit: yellow, bitter
2 grapefruit: bitter, yellow
1 watermelon: green, heavy
2 watermelon: heavy, green


## Rainy days scenarios

#1.File doesn't exist in specified path

$ java -jar FruitBasket.jar
Please enter your file path and then Enter: 
/Users/aghosh005c/Desktop/filedoesnotexist.csv                                                  

You entered the path: /Users/aghosh005c/Desktop/filedoesnotexist.csv

No such file in the specified path: /Users/aghosh005c/Desktop/filedoesnotexist.csv

#2.The file doesn't have ".csv" extension

$java -jar FruitBasket.jar
Please enter your file path and then Enter: 
/Users/aghosh005c/Desktop/fruitBasket.png

You entered the path: /Users/aghosh005c/Desktop/fruitBasket.png

The file is not in CSV extension(.csv)

#3.Not in correct format. Remove one of the "," from any line of the csv file

$ java -jar FruitBasket.jar
Please enter your file path and then Enter: 
/Users/aghosh005c/Desktop/fruitBasket.csv

You entered the path: /Users/aghosh005c/Desktop/fruitBasket.csv

The file is not correct formatted for the line 6

#4.Not in correct format. Update one of the age-in-days column as string instead of numeric

$ java -jar FruitBasket.jar
Please enter your file path and then Enter: 
/Users/aghosh005c/Desktop/fruitBasket.csv

You entered the path: /Users/aghosh005c/Desktop/fruitBasket.csv

age-in-days column should be numeric for the line 10

##Unit Testing
 * Created a junit class(com.mycode.fruit.basket.FruitBasketTest) to validate all the functional requirements with both happy path and rainy day scenarios
 * Upladed the junit test report(Junit Result.png) to Github 



 