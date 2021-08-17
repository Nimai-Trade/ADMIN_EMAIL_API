package com.nimai.email.utility;

import java.util.Random;

public class AmountToWords {

	
	 public static String NumberToWords(double doubleNumber)
	    {
		 String afterFloatingPointWord="";
	        int beforeFloatingPoint =(int)Math.floor(doubleNumber) ;
	        System.out.println("beforeFloatingPoint:"+beforeFloatingPoint);
	        System.out.println("doubleNumber:"+doubleNumber);
	        int rounODValue=(int) (Math.round((doubleNumber - beforeFloatingPoint) * 100));
	        System.out.println("rounODValue:"+rounODValue);
	        String beforeFloatingPointWord = NumberToWords(beforeFloatingPoint)+" "+"dollars";
	        beforeFloatingPointWord = beforeFloatingPointWord.substring(0,1).toUpperCase() + beforeFloatingPointWord.substring(1).toLowerCase();
	    //    String afterFloatingPointWord =SmallNumberToWord((int) ((doubleNumber - beforeFloatingPoint) * 100), "")+" "+"cents";
	     
	        if(rounODValue!=0) {
	        	afterFloatingPointWord ="and"+" "+SmallNumberToWord( (rounODValue), "")+" "+"cents";
	        }
	        
	        
	        System.out.println(((doubleNumber - beforeFloatingPoint) * 100));
	        System.out.println(afterFloatingPointWord);
	        return beforeFloatingPointWord+" "+afterFloatingPointWord;
	    }

	    private static String NumberToWords(int number)
	    {
	        if (number == 0)
	            return "zero";

	        if (number < 0)
	            return "minus " + NumberToWords(Math.abs(number));

	        String words = "";
	        
	      

	        if (number / 1000000000 > 0)
	        {
	            words += NumberToWords(number / 1000000000) + " billion ";
	            number %= 1000000000;
	        }

	        if (number / 1000000 > 0)
	        {
	            words += NumberToWords(number / 1000000) + " million ";
	            number %= 1000000;
	        }

	        if (number / 1000 > 0)
	        {
	            words += NumberToWords(number / 1000) + " thousand ";
	            number %= 1000;
	        }

	        if (number / 100 > 0)
	        {
	            words += NumberToWords(number / 100) + " hundred ";
	            number %= 100;
	        }
	    
	        words = SmallNumberToWord(number, words);

	        return words;
	    }

	    private static String SmallNumberToWord(int number, String words)
	    {
	        if (number <= 0) return words;
	        if (words != "")
	            words += " ";

	        String[] unitsMap = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	        String[] tensMap = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

	        if (number < 20)
	            words += unitsMap[number];
	        else
	        {
	            words += tensMap[number / 10];
	            if ((number % 10) > 0)
	                words += "-" + unitsMap[number % 10];
	        }
	        return words;
	    }
	    
	    
	    
	//	
//		public static final String[] units= {
//				"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten",
//		"Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nighteen"};
	//
	//
	//public static final String[] tens= {
//			"","","Twenty","Thirty","Fourty","Fifty","Sixty","Seventy","Eighty","Ninety"
	//};
	//
	//public static String convert(final int n) {
//		  if (n < 0) {
//	          return "minus " + convert(-n);
//	      }
	//
//	      if (n < 20) {
//	          return units[n];
//	      }
	//
//	      if (n < 100) {
//	          return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
//	      }
	//
//	      if (n < 1000) {
//	          return units[n / 100] + " hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
//	      }
	//
//	      if (n < 1000000) {
//	          return convert(n / 1000) + " thousand" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
//	      }
	//
//	      if (n < 1000000000) {
//	          return convert(n / 1000000) + " million" + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
//	      }
	//
//	      return convert(n / 1000000000) + " billion"  + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
	//  }
		
public static void main(final String[] args) {
//	String name  = "stackoverflow"; 
//	name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
	AmountToWords words=new AmountToWords();
	System.out.println("==================words:"+words.NumberToWords(29876.8976));

}
}

