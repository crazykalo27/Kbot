package forge.Kbot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class MyFiles {
	
	public static String getPath() throws IOException
	{
		String current = new File( "." ).getCanonicalPath();
       //System.out.println("Current dir:"+current);
       // String currentDir = System.getProperty("user.dir");
       // System.out.println("Current dir using System:" +currentDir);
        
		return current;
	}

	public static File CreateDir(String name)
	{
		System.out.println("epic");
		File dir = new File(name);
		dir.mkdir();
		return dir;
	}
	
	public static File Log(String name, String messages)
	{
		File file = new File(name+ "log.txt");;
		
		try(FileWriter append = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(append);
			PrintWriter pr = new PrintWriter(br))
		{
			
			Scanner scan = new Scanner(file);
			System.out.println("in files");
			
			if(!messages.equals(""))
			{
				System.out.println("logging");
				pr.println(messages);
			}
			else
			{
				System.out.println("WOAH WATCH OUT THERES NOTHING BUT I ALMOST ADDED IT");
			}
			
			
			System.out.println(messages + " appended in " + name);
			
			pr.close();
			scan.close();
			return file;
			
		}
		catch(IOException e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
		return null;
	}
	
	public static File LogInDir(String Dir, String name, String messages)
	{
	
		File file = new File(Dir + name+ "log.txt");
		
		try(FileWriter append = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(append);
			PrintWriter pr = new PrintWriter(br))
		{
			
			Scanner scan = new Scanner(file);
			System.out.println("in files");
			
			if(!messages.equals(""))
			{
				System.out.println("logging");
				pr.println(messages);
			}
			else
			{
				System.out.println("WOAH WATCH OUT THERES NOTHING BUT I ALMOST ADDED IT");
			}
			
			
			System.out.println(messages + " appended in " + name);
			
			pr.close();
			scan.close();
			return file;
			
		}
		catch(IOException e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String> readLog(String string)
	{
		Scanner scan;
		try 
		{
			File file = new File(string + "log.txt");
			scan = new Scanner(file);
			ArrayList<String> text = new ArrayList<String>(); 
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				if(line.equals(""))
				{
					scan.close();
					System.out.println("empty line so returning null");
					return null;
				}
				text.add(line);
			}
			
			//System.out.println(text.get(0) + " read in log");
			scan.close();
			return text;
		} 
		catch (FileNotFoundException | IndexOutOfBoundsException e) {
			if(e.toString().startsWith("java.io.FileNotFoundException:"))
			{
				System.out.println("no file");
				return null;
			}
			e.printStackTrace();
			System.out.println("returning null 2");
			return null;
		}
		
	}
	
	public static ArrayList<String> readLoginDir(String Dir, String name)
	{
		Scanner scan;
		try 
		{
			File file = new File(Dir+ name + "log.txt");
			scan = new Scanner(file);
			ArrayList<String> text = new ArrayList<String>(); 
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				if(line.equals(""))
				{
					scan.close();
					System.out.println("returning null 1");
					return null;
				}
				text.add(line);
			}
			
			//System.out.println(text.get(0) + " read in log");
			scan.close();
			return text;
		} 
		catch (FileNotFoundException | IndexOutOfBoundsException e) {
			if(e.toString().startsWith("java.io.FileNotFoundException:"))
			{
				System.out.println("no file");
				return null;
			}
			e.printStackTrace();
			System.out.println("returning null 2");
			return null;
		}
		
	}
	
	
	public static ArrayList<GuildMessageReceivedEvent> getEvents(String Dir, String string)
	{
		Scanner scan;
		try 
		{
			File file = new File(Dir + string + "log.txt");
			scan = new Scanner(file);
			String EmbedId = "";
			String GuildId = "";
			String ChannelId = "";
			
			ArrayList<GuildMessageReceivedEvent> events = new ArrayList<GuildMessageReceivedEvent>();
			int count = 0;
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				String[] iDs = line.split(" ");
				
				//System.out.println(iDs[0]+ "   " + iDs[1] + "   " + iDs[2]);
				
				EmbedId = iDs[0];
				GuildId = iDs[1];
				ChannelId = iDs[2];
				
				//guild
				//System.out.println(Kbot.jda.getGuildById(GuildId));
				
				//channel
				//System.out.println(Kbot.jda.getGuildById(GuildId).getGuildChannelById(ChannelId));
				
				MessageHistory what = Kbot.jda.getGuildById(GuildId).getTextChannelById(ChannelId).getHistoryBefore(
						Kbot.jda.getGuildById(GuildId).getTextChannelById(ChannelId).getLatestMessageId(), 100).complete();
				Message real = what.getMessageById(EmbedId);
				
				//message
				//System.out.println(real);

				
				GuildMessageReceivedEvent data = new GuildMessageReceivedEvent(Kbot.jda, 0, real);
				events.add(count, data);
				count++;
			}
			scan.close();
			return events;
		} 
		catch (FileNotFoundException e) {
			System.out.println("my files error");
			e.printStackTrace();
		}
		return null;
		
	}
}
