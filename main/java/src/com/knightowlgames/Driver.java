package com.knightowlgames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Driver {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		String santaFile = System.getProperty("santaFile");
		
		ArrayList<Santa> santas = new ObjectMapper()
				.readValue(new File((santaFile == null || santaFile.isEmpty() ? "main/resources/defaultSantas.json" : santaFile)), 
						   new TypeReference<ArrayList<Santa>>(){});
		
		List<Santa> matches = shuffle(santas);
		
		for(int i = 0; i < santas.size(); i++) {
			String message = santas.get(i).getName() + ",\nRoboSanta has made a match for you." +
					" You're going to get a gift for " + matches.get(i).getName() + "!" +
					(matches.get(i).getList() == null || matches.get(i).getList().isEmpty() ? "" : "\nHere is their Christmas List: " + matches.get(i).getList());
			MailMan.sendMessage(santas.get(i).getEmail(), "RoboSanta has matched you with your elf for Secret Santa",
				message);
		}
	}
	
	private static List<Santa> shuffle(ArrayList<Santa> santas) {
		ArrayList<Santa> matches = (ArrayList<Santa>) santas.clone();
		
		boolean valid = false;
		int count = 0;
		while(!valid) {
			System.out.println("Try " + count++);
			valid = true;
			long seed = System.nanoTime();
			Collections.shuffle(matches, new Random(seed));
			for(int i = 0; i < santas.size(); i++) {
				if(matches.get(i).getName() == santas.get(i).getName() ||
						santas.get(i).getNoMatch().contains(matches.get(i).getName())) {
					valid = false;
					break;
				}
			}
		}
		return matches;
	}
	
	public static class Santa {
		private String name;
		private String email;
		private String list;

		private List<String> noMatch;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getList() {
			return list;
		}
		
		public void setList(String list) {
			this.list = list;
		}
		
		public List<String> getNoMatch() {
			return noMatch;
		}
		
		public void setNoMatch(List<String> noMatch) {
			this.noMatch = noMatch;
		}
		
		public String toString() {
			try {
				return new ObjectMapper().writeValueAsString(this);
			} catch (JsonProcessingException e) {
				return "name : " + name +
					   " email : " + email +
					   " list : " + list +
					   " noMatch : " + noMatch.toString();
			}
		}
	}
}
