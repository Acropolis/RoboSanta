package com.knightowlgames;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Driver {
	
	private static final boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
	
	private static final String EmailSubject = "RoboSanta has matched you with your elf for Secret Santa";
	private static final String EmailBody = "RoboSanta has matched you for the Secret Santa. You're going to get a gift for %s";

	public static void main(String[] args) throws IOException {
		
		LinkedList<Santa> giverQueue = new LinkedList<>();
		LinkedList<Santa> completedQueue = new LinkedList<>();
		
		Files.readAllLines(Paths.get("main/resources/santas.csv"))
			.stream()
			.map(s -> new Santa(s.split(",")))
			.forEach(giverQueue::add);
		
		giverQueue.sort((s1,s2) -> s1.identifier.compareTo(s2.identifier)); // Randomize.
		giverQueue.sort((s1,s2) -> s1.noPair.isEmpty() ? s2.noPair.isEmpty() ? 0 : 1 : -1); // Then prioritize those with noPair lists.
		
		while(!giverQueue.isEmpty()) {
			Santa gifter = giverQueue.remove();
			Santa giftee = giverQueue.remove();
			if(gifter.cannotPair(giftee)) {
				giverQueue.addFirst(gifter);
				giverQueue.add(giverQueue.size() - 2, giftee);
			} else {
				completedQueue.addLast(gifter);
				completedQueue.addLast(giftee);
			}
		}

		for(int i = 0; i < completedQueue.size(); i++) {
			if(isDebug)
				System.out.println(String.format("%s gets a gift for %s", completedQueue.get(i).name, completedQueue.get((i+1)%completedQueue.size()).name));
			
			//MailMan.sendMessage(completedQueue.get(i).email, EmailSubject,
				//String.format(EmailBody, completedQueue.get(i+1 % completedQueue.size()).name));
		}
	}
	
	public static class Santa {
		public final String name;
		public final String email;
		public final Set<String> noPair;
		public String identifier;

		public Santa(String[] csvElem) {
			List<String> csvElemList = Arrays.asList(csvElem);
			name = csvElemList.get(0);
			email = csvElemList.get(1);
			if(csvElem.length > 2)
				noPair = new HashSet<>(csvElemList.subList(2, csvElemList.size()));
			else
				noPair = new HashSet<>();
			identifier = UUID.randomUUID().toString();
		}
		
		public boolean cannotPair(Santa other) {
			return this.noPair.contains(other.name) || other.noPair.contains(this.name);
		}

		public String toString() {
			return String.join("|", "name="+ name, "email="+ email, "noPair=" + String.join(",", noPair));
		}
	}
}
