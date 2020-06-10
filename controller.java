package Webcrwl;

import java.util.Scanner;

public class controller {

	
    public static void main(String args[]) throws InterruptedException {
		
    	Scanner in = new Scanner(System.in);
    	System.out.println("Give number of threads(recomended 2-3)");
		int threads = in.nextInt();
		System.out.println("Give number of depth(recomended 3)");
		int depth = in.nextInt();
		for(int i=0;i<threads;i++) {
        Thread crawl1 = new Crawler("https://cs.ucy.ac.cy",threads,depth);
        crawl1.start();
		}

        
        
   }    
}