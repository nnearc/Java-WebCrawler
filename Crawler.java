package Webcrwl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler extends Thread {

	public static Queue<String> queue = new LinkedList<String>();
	public static Set<String> to_do = new HashSet<>();
	public static String regex = "http[s]*://(\\w+\\.)*(\\w+)*";
	String root;
	int threads;
	int depth;
	int temp=-1;
	int stop=0;
	/** Initial URL to visit **/
	public Crawler(String d,int threads,int depth) {
		root = d;
		this.depth=depth;
		this.threads=threads;
	}
	public static void showResults() {
		System.out.println("\n\nResults  " + Thread.currentThread().getName() + " id ====");
		System.out.println("Web sited crawled :" + to_do.size()+ "\n");
		for(String s:to_do) {
			System.out.println("*" +s);
		}
	}
	public void run() {
		queue.add(root);
		BufferedReader br = null;

		while (!queue.isEmpty()) {
			String crawledUrl = queue.poll();
			System.out.println("\n==== Site crawled: " + crawledUrl + " ====");

			// limit to number sites
			if (stop == this.depth) {
				showResults();
				return;
			}
			boolean ok = false;
			URL url = null;

			while (!ok) {
				try {
					url = new URL(crawledUrl);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					ok = true;
				} catch (MalformedURLException e) {
					System.out.println("*** Malformed URL: " + crawledUrl);
					crawledUrl = queue.poll();
					ok = false;
				} catch (IOException ioe) {
					System.out.println("*** IOException for URL: " + crawledUrl);
					crawledUrl = queue.poll();
					ok = false;

				}
			}
			StringBuilder sb = new StringBuilder();
			String tmp = null;
				try {
					while ((tmp = br.readLine()) != null) {
						sb.append(tmp);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			tmp = sb.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(tmp);
			int depth1 = this.depth;
			while (matcher.find()) {
				String w = matcher.group();

				if (!to_do.contains(w) && depth1 > 0) {
					to_do.add(w);
					System.out.println("Sited added for crawling : " + w);
					queue.add(w);
					depth1--;
				}
			}
			temp++;
			if(temp==0) {
			System.out.println("==== Depth  " + temp + " finished  " + Thread.currentThread().getName() + " id ====");
			}
			else if(temp==this.depth) {
				stop=1;
				System.out.println("==== Depth  " + stop + " finished ==== " + Thread.currentThread().getName() + " id ====");
			}
			else if(temp==this.depth*this.depth) {
				stop=2;
				System.out.println("==== Depth  " + stop + " finished ==== " + Thread.currentThread().getName() + " id ====");
			}
			else if(temp==this.depth*this.depth*this.depth) {
				stop=3;
				System.out.println("====  Depth  " + stop + " finished ==== " + Thread.currentThread().getName() + " id ====");
			}
			else if(temp==this.depth*this.depth*this.depth*this.depth) {
				stop=4;
				System.out.println("==== Depth  " + stop + " finished ==== " + Thread.currentThread().getName() + " id ====");
			}
			else if(temp==this.depth*this.depth*this.depth*this.depth*this.depth) {
				stop=5;
				System.out.println("==== Depth  " + stop + " finished ==== " + Thread.currentThread().getName() + " id ====\"");
			}
			else if(temp==this.depth*this.depth*this.depth*this.depth*this.depth*this.depth) {
				stop=6;
				System.out.println("==== Depth  " + stop + " finished ==== " + Thread.currentThread().getName() + " id ====\"");
			}
		}
		
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}