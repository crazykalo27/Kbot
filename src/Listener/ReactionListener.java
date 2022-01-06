package Listener;

import java.io.IOException;
import java.util.ArrayList;

import Objects.Reaction;
import forge.Kbot.Kbot;
import forge.Kbot.MyFiles;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.requests.Route.Roles;

public class ReactionListener extends ListenerAdapter{
	
	ArrayList<Reaction> reactions = new ArrayList<Reaction>();
	ArrayList<String> reactsCount = new ArrayList<String>();
	ArrayList<Roles> roles = new ArrayList<Roles>();
	int startup = 0;
	String Path = "";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		//adds all past embeds
		if(startup == 0)
		{
			ArrayList<String> roles = new ArrayList<String>();
			try 
			{
				
				Path = MyFiles.getPath();
				String RolesPath = Path + "\\" + "ReactionStorage" + "\\";
				
				int rolecount = 0;
				for(int i = 0; i < MyFiles.readLoginDir(RolesPath, "reactionRoles").size(); i++)
				{
					roles.add(i, MyFiles.readLoginDir(RolesPath, "reactionRoles").get(i));;
					rolecount += 2;
				}
				
				if(MyFiles.readLoginDir(RolesPath, "reactionRoles").size() == 0)
				{
					System.out.println("No roles found, exiting");
					startup = 1;
					return;
				}
				System.out.println("read roles " + rolecount);
				
				Path = MyFiles.getPath();
				String EventsPath = Path + "\\" + "ReactionStorage" + "\\";
				
				int eventcount = 0;
				for(int i = 0; i < MyFiles.getEvents(EventsPath, "reactionEvents").size(); i ++)
				{
					eventcount++;
					reactions.add(new Reaction(MyFiles.getEvents(EventsPath, "reactionEvents").get(i), roles.get(i)));
					while(reactions.get(reactions.size()-1).messageId.equals(" "))
					{
						System.out.println("lag");
					}
					reactsCount.add(reactions.get(reactions.size()-1).messageId);
					System.out.println(reactions.get(reactions.size()-1).messageId + "  added reaction role id");
				}	
				System.out.println("got events " + eventcount);
			}
			catch(NullPointerException e)
			{
				System.out.println("there are no old embeds");
				startup = 1;
				return;
			} catch (IOException e) {
				System.out.println("not found or somthing reaction listener 70");
				e.printStackTrace();
			}
			startup = 1;
		}

		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "reaction"))
		{
			reactions.add(new Reaction(event));
			while(reactions.get(reactions.size()-1).messageId.equals(" "))
			{
				System.out.println("lag");
			}
			reactsCount.add(reactions.get(reactions.size()-1).messageId);
			System.out.println(reactions.get(reactions.size()-1).messageId + "  added");
			
			try 
			{
				Path = MyFiles.getPath();
				MyFiles.CreateDir("ReactionStorage");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String ReactionPath = Path + "\\" + "ReactionStorage" + "\\";
			
			MyFiles.LogInDir(ReactionPath, "reactionEvents", reactions.get(reactions.size()-1).messageId + " " + event.getGuild().getId() + " " + event.getChannel().getId());
			
			MyFiles.LogInDir(ReactionPath, "reactionRoles", args[1].replace("<@", "").replace(">", "").replace("&", "").replace("!", "") 
						+ " " + args[2].replace("<@", "").replace(">", "").replace("&", "").replace("!", ""));
			
		}
	}
	
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) 
	{
		//adds all past embeds
		if(startup == 0)
		{
			ArrayList<String> roles = new ArrayList<String>();
			try 
			{
				
				Path = MyFiles.getPath();
				String RolesPath = Path + "\\" + "ReactionStorage" + "\\";
				
				int rolecount = 0;
				for(int i = 0; i < MyFiles.readLoginDir(RolesPath, "reactionRoles").size(); i++)
				{
					roles.add(i, MyFiles.readLoginDir(RolesPath, "reactionRoles").get(i));;
					rolecount += 2;
				}
				
				if(MyFiles.readLoginDir(RolesPath, "reactionRoles").size() == 0)
				{
					System.out.println("No roles found, exiting");
					startup = 1;
					return;
				}
				System.out.println("read roles " + rolecount);
				
				Path = MyFiles.getPath();
				String EventsPath = Path + "\\" + "ReactionStorage" + "\\";
				
				int eventcount = 0;
				for(int i = 0; i < MyFiles.getEvents(EventsPath, "reactionEvents").size(); i ++)
				{
					eventcount++;
					reactions.add(new Reaction(MyFiles.getEvents(EventsPath, "reactionEvents").get(i), roles.get(i)));
					while(reactions.get(reactions.size()-1).messageId.equals(" "))
					{
						System.out.println("lag");
					}
					reactsCount.add(reactions.get(reactions.size()-1).messageId);
					System.out.println(reactions.get(reactions.size()-1).messageId + "  added reaction role id");
				}	
				System.out.println("got events " + eventcount);
			}
			catch(NullPointerException e)
			{
				System.out.println("there are no old embeds");
				startup = 1;
				return;
			} catch (IOException e) {
				System.out.println("not found or somthing reaction listener 70");
			}
			startup = 1;
		}
		
		if(event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			return;
		}
		
		int count = 0;
		for(String messages : reactsCount)
		{
			if(messages.equals(event.getMessageId()))
			{
				System.out.println("equal");
				break;
			}
			else
			{
				count++;
			}
		}
		try
		{
			reactions.get(count).onGuildMessageReactionAdd(event);
			count = 0;
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
	}

	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) 
	{
		//adds all past embeds
		if(startup == 0)
		{
			ArrayList<String> roles = new ArrayList<String>();
			try 
			{
				
				Path = MyFiles.getPath();
				String RolesPath = Path + "\\" + "ReactionStorage" + "\\";
				
				int rolecount = 0;
				for(int i = 0; i < MyFiles.readLoginDir(RolesPath, "reactionRoles").size(); i++)
				{
					roles.add(i, MyFiles.readLoginDir(RolesPath, "reactionRoles").get(i));;
					rolecount += 2;
				}
				
				if(MyFiles.readLoginDir(RolesPath, "reactionRoles").size() == 0)
				{
					System.out.println("No roles found, exiting");
					startup = 1;
					return;
				}
				System.out.println("read roles " + rolecount);
				
				Path = MyFiles.getPath();
				String EventsPath = Path + "\\" + "ReactionStorage" + "\\";
				
				int eventcount = 0;
				for(int i = 0; i < MyFiles.getEvents(EventsPath, "reactionEvents").size(); i ++)
				{
					eventcount++;
					reactions.add(new Reaction(MyFiles.getEvents(EventsPath, "reactionEvents").get(i), roles.get(i)));
					while(reactions.get(reactions.size()-1).messageId.equals(" "))
					{
						System.out.println("lag");
					}
					reactsCount.add(reactions.get(reactions.size()-1).messageId);
					System.out.println(reactions.get(reactions.size()-1).messageId + "  added reaction role id");
				}	
				System.out.println("got events " + eventcount);
			}
			catch(NullPointerException e)
			{
				System.out.println("there are no old embeds");
				startup = 1;
				return;
			} catch (IOException e) {
				System.out.println("not found or somthing reaction listener 70");
				e.printStackTrace();
			}
			startup = 1;
		}
				
		if(event.getMember().getUser().equals(event.getJDA().getSelfUser()))
		{
			return;
		}
		
		int count = 0;
		for(String messages : reactsCount)
		{
			if(messages.equals(event.getMessageId()))
			{
				System.out.println("equal");
				break;
			}
			else
			{
				count++;
			}
		}
		try
		{
			reactions.get(count).onGuildMessageReactionRemove(event);
			count = 0;
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
	}
}
