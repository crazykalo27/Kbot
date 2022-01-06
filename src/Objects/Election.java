package Objects;

import java.awt.Color;
import java.util.ArrayList;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class Election {

	public String messageId = " ";
	
	private ArrayList<Integer> voteCount = new ArrayList<Integer>();
	private ArrayList<Member> voters = new ArrayList<Member>();
	private ArrayList<Integer> ticket = new ArrayList<Integer>();
	
	String[] emotes =  {"1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣"};
	String Winner = "None";
	private boolean closed = false;
	String args[];
	
	public Election(GuildMessageReceivedEvent event)
	{
		onGuildMessageReceived(event);
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "election"))
		{
			try
			{
				event.getMessage().delete().complete();
			}
			catch(ErrorResponseException e)
			{
				e.printStackTrace();
			}
			
			String position = args[1];
			
			ArrayList<String> electors = new ArrayList<String>();
			for(int i = 2; i < args.length; i++)
			{
				electors.add(args[i]);
			}
			
			try 
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.WHITE);
				number.setTitle("⚫Vote for " + position);
				String Description = " ";
				for(int i = 0; i < electors.size(); i++)
				{
					Description += emotes[i] + " " + electors.get(i) + "\n";
				}
				Description += "\n" + "You may change your vote";
				number.setDescription(Description);
				event.getChannel().sendMessage(number.build());
				

				MessageEmbed embed = number.build();
				event.getChannel().sendMessage(embed).queue((message) -> {
					setId(message);
					for(int i = 0; i < electors.size(); i++)
					{
						message.addReaction(emotes[i]).queue();
					}	
					setId(message);
				});
				
				for(int i = 0; i < electors.size(); i++)
				{
					voteCount.add(0);
				}
				
			}
			catch(IndexOutOfBoundsException | HierarchyException | IllegalArgumentException e)
			{
				e.printStackTrace();
			}	
		}
	}

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) 
	{
		if(closed == false)
		{
			ReactionEmote emote = event.getReactionEmote();
			Member voter = event.getMember();
			
			event.getReaction().removeReaction(event.getUser()).queue();
			
			boolean voted = false;
			
			//figures out which voter it was if they did vote
			int which = 0;
			for(Member vo : voters)
			{
				if(vo.equals(voter))
				{
					System.out.println("weve got a voter");
					voted = true;
					break;
				}
				which++;
			}
			
			if(voted == false)
			{
				voters.add(voter);
				System.out.println("NEVER VOTED");
			}
			
			//figures out which emote they did
			int count = 0;
			for(String EM : emotes)
			{
				if(emote.getName().equals(EM))
				{
					break;
				}
				count++;
			}
			
			if(voted == false)
			{
				System.out.println("adding a vote to " + count);
				voteCount.set(count, voteCount.get(count)+1);
				
				ticket.add(count);
			}
			else if(voted == true)
			{
				int choice = ticket.get(which);
				
				System.out.println(which);
				
				System.out.println("sub a vote from: " + choice);
				voteCount.set(choice, voteCount.get(choice)-1);
				
				System.out.println("add a vote to: " + count);
				voteCount.set(count, voteCount.get(count)+1);
				ticket.set(which, count);
			}
			
			count = 0;
		}
		else
		{
			System.out.println("closed");
		}
	} 
	
	private void setId(Message message) {
		messageId = message.getId().toString();
	}
	
	public ArrayList<String> checkWinner()
	{
		System.out.println("closing");
		closed = true;
		ArrayList<String> winner = new ArrayList<String>();
		boolean tie = false;
		
		int highest = 0;
		ArrayList<Integer> victor = new ArrayList<Integer>();
		victor.add(null);
		
		int which = 0;
		for(int count : voteCount)
		{
			if(count > highest)
			{
				tie = false;
				highest = count;
				victor.set(0, which);
			}
			else if(count == highest)
			{
				tie = true;
				victor.add(which);
			}
			which++;
		}
		
		winner.add(args[1]);
		winner.add(Integer.toString(highest));
		if(tie == false)
		{
			winner.add(args[2 + victor.get(0)]);
		}
		else if(tie == true)
		{
			for(int vic : victor)
			{
				winner.add(args[2 + vic]);
			}
			
		}
		return winner;
	}
	
}
