package Listener;

import java.awt.Color;
import java.util.ArrayList;

import Objects.Election;
import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ElectionListener extends ListenerAdapter{
	
	static ArrayList<Election> elections = new ArrayList<Election>();
	static ArrayList<String> electCount = new ArrayList<String>();

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		try {
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "election"))
		{
			System.out.println("creating an election");
			elections.add(new Election(event));
			while(elections.get(elections.size()-1).messageId.equals(" "))
			{
				System.out.println("");
			}
			electCount.add(elections.get(elections.size()-1).messageId);
			System.out.println(elections.get(elections.size()-1).messageId + "  added election id");
		}
		else if(args[0].equalsIgnoreCase(Kbot.prefix + "close"))
		{
			event.getMessage().delete().queue();
			try
			{
			if(elections.get(Integer.parseInt(args[1])).checkWinner().size() == 3)
			{
				String position = elections.get(Integer.parseInt(args[1])).checkWinner().get(0);
				String votes = elections.get(Integer.parseInt(args[1])).checkWinner().get(1);
				String winner = elections.get(Integer.parseInt(args[1])).checkWinner().get(2);
				
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.BLACK);
				number.setTitle("⚫Results for " + position);
				number.setDescription("The winner is " + winner + " with " + votes+ " votes.");
				event.getChannel().sendMessage(number.build()).queue();
			}
			else if(elections.get(Integer.parseInt(args[1])).checkWinner().size() > 3)
			{
				int size = elections.get(Integer.parseInt(args[1])).checkWinner().size();
				
				String position = elections.get(Integer.parseInt(args[1])).checkWinner().get(0);
				String votes = elections.get(Integer.parseInt(args[1])).checkWinner().get(1);
				
				String Discription = "There was a tie between ";
				
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.BLACK);
				number.setTitle("âš«Results for " + position);
				
				for(int i = 2; i < size; i++)
				{
					Discription += elections.get(Integer.parseInt(args[1])).checkWinner().get(i);
					if(i != size-1)
					{
						Discription += " and ";
					}
				}
				Discription += " with " + votes + " votes.";
				
				number.setDescription(Discription);
				event.getChannel().sendMessage(number.build()).queue();
			}
			}
			catch(NullPointerException e)
			{				
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.BLACK);
				number.setTitle("⚫Results");
				number.setDescription("There were no votes submitted.");
				event.getChannel().sendMessage(number.build()).queue();
			}
		}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 1"))
			{
				System.out.println("someone tried to close something and didnt provide which");
			}
		}
	}
	
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event)
	{
		if(event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			return;
		}
		
		boolean exists = false;
		
		int count = 0;
		for(String messages : electCount)
		{
			if(messages.equals(event.getMessageId()))
			{
				exists = true;
				break;
			}
			else
			{
				count++;
			}
		}
		try {
			if(exists == true)
			{
				System.out.println("calling election reaction add " + count);
				elections.get(count).onGuildMessageReactionAdd(event);
			}
			count = 0;
		}
		catch(IndexOutOfBoundsException e)
		{
			if(e.toString().startsWith("java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"))
			{
				System.out.println("Not a valid election reaction");
			}
		}
	}
}
