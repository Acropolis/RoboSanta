package com.nightowlgames;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new File("main/resources/santas.csv"));
		List<Santa> santas = new ArrayList<>();
		List<Santa> matches = new ArrayList<>();
		while(scanner.hasNext()) {
			String [] santaCSV = scanner.next().split(",");
			Santa santa = new Santa(santaCSV[0],santaCSV[1]);
			for(int i = 2; i < santaCSV.length; i++) {
				santa.addNoPair(santaCSV[i]);
			}
			santas.add(santa);
			matches.add(santa);
		}
		scanner.close();
		
		boolean valid = false;
		int count = 0;
		while(!valid) {
			System.out.println("Try " + count++);
			valid = true;
			long seed = System.nanoTime();
			Collections.shuffle(matches, new Random(seed));
			for(int i = 0; i < santas.size(); i++) {
				if(matches.get(i).getName().contentEquals(santas.get(i).getName()) ||
						santas.get(i).getNoPair().contains(matches.get(i).getName())) {
					valid = false;
					break;
				}
			}
		}
		
		for(int i = 0; i < santas.size(); i++) {
			MailMan.sendMessage(santas.get(i).getEmail(), "RoboSanta has matched you with your elf for Secret Santa",
				"RoboSanta has matched you for the Secret Santa. You're going to get a gift for " + matches.get(i).getName());
		}
	}
	
	public static class Santa {
		private String name;
		private String email;
		private List<String> noPair;
		
		public Santa(String name, String email) {
			this.name = name;
			this.email = email;
			noPair = new ArrayList<>();
		}
		
		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public List<String> getNoPair() {
			return noPair;
		}

		public void addNoPair(String pair) {
			noPair.add(pair);
		}
		
		public String toString() {
			StringJoiner joiner = new StringJoiner(",");
			joiner.add("name=" + name);
			joiner.add("email=" + email);
			noPair.stream().forEach(s -> joiner.add("noPair=" + s));
			return joiner.toString();
		}
	}
}
