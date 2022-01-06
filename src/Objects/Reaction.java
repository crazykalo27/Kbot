package Objects;

import java.awt.Color;
import java.util.ArrayList;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;


//got rid of extends listener adapter
public class Reaction{

	public String messageId = " ";
	ArrayList<String> roles = new ArrayList<String>();

	public Reaction(GuildMessageReceivedEvent event)
	{
		onGuildMessageReceived(event);
	}
	
	public Reaction(GuildMessageReceivedEvent event, String roles)
	{
			messageId = event.getMessageId().toString();
			
			//takes the roles from one line and splits on a space
			String[] splitRoles = roles.split(" ");
			
			this.roles.add("epic");
			this.roles.add(1, splitRoles[0].replace("<@", "").replace(">", "").replace("&", "").replace("!", ""));
			this.roles.add(2, splitRoles[1].replace("<@", "").replace(">", "").replace("&", "").replace("!", ""));
	}
	
	public Reaction(GuildMessageReactionAddEvent event)
	{
		onGuildMessageReactionAdd(event);
	}
	
	public Reaction(GuildMessageReactionRemoveEvent event)
	{
		onGuildMessageReactionRemove(event);
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "reaction"))
		{
			try
			{
				event.getMessage().delete().complete();
			}
			catch(ErrorResponseException e)
			{
				e.printStackTrace();
			}
			
			try 
			{
				roles.add("epic");
				
				roles.add(1, args[1].replace("<@", "").replace(">", "").replace("&", "").replace("!", ""));
				roles.add(2, args[2].replace("<@", "").replace(">", "").replace("&", "").replace("!", ""));
				
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.BLUE);
				number.setTitle("🔵Click to gain roles");
				number.setDescription(args[1] + " \n " + args[2]);
				event.getChannel().sendMessage(number.build());

				MessageEmbed embed = number.build();
				event.getChannel().sendMessage(embed).queue((message) -> {
					setId(message);
					message.addReaction("1️⃣").queue();
					message.addReaction("2️⃣").queue();		
				});
			}
			catch(IndexOutOfBoundsException | HierarchyException | IllegalArgumentException e)
			{
				System.out.println(e + " one1");
				//no role specified
				if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 1"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴´Role specification error");
					number.setDescription("Specify a role: `$reaction [role-select]`");
					event.getChannel().sendMessage(number.build()).queue();
				}
			}	
		}
	}
	
	private void setId(Message message) {
		messageId = message.getId().toString();
	}

	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) 
	{
		try
		{
			if(event.getReactionEmote().getName().equals("1️⃣") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
			{
				event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(roles.get(1))).complete();
			}
			
			if(event.getReactionEmote().getName().equals("2️⃣") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
			{
				event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(roles.get(2))).complete();
			}
		}
		catch(IndexOutOfBoundsException | HierarchyException | IllegalArgumentException e)
		{
			System.out.println(e + " two2");
			//Hierarchy exceptions
			if(e.toString().startsWith("net.dv8tion.jda.api.exceptions.HierarchyException: Can't modify a role with higher or equal highest role than yoursel"))
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴´Hierarchy Error");
				number.setDescription("Cannot give or take roles higher than mine :)");
				event.getChannel().sendMessage(number.build()).queue();
			}
			//if they mention a group
			if(e.toString().startsWith("java.lang.NumberFormatException: The specified ID is not a valid snowflake"))
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴´Role selection error");
				number.setDescription("Not a valid role");
				event.getChannel().sendMessage(number.build()).queue();
			}
			//if they mention a person
			if(e.toString().startsWith("java.lang.IllegalArgumentException: Role may not be null"))
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴´Role selection error");
				number.setDescription("Not a valid role");
				event.getChannel().sendMessage(number.build()).queue();
			}
			return;
		}
	} 
	
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event)
	{
		try
		{
			if(event.getReactionEmote().getName().equals("1️⃣") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
			{
				event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(roles.get(1))).complete();
			}
			else if(event.getReactionEmote().getName().equals("2️⃣") && !event.getMember().getUser().equals(event.getJDA().getSelfUser()))
			{
				event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(roles.get(2))).complete();
			}
		}
		catch(IndexOutOfBoundsException | HierarchyException | IllegalArgumentException e)
		{
			//heirarchy exceptions
			if(e.toString().startsWith("net.dv8tion.jda.api.exceptions.HierarchyException: Can't modify a role with higher or equal highest role than yoursel"))
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴´Hierarchy Error");
				number.setDescription("Cannot give or take roles higher than mine :)");
				event.getChannel().sendMessage(number.build()).queue();
			}
			//if they mention a group
			if(e.toString().startsWith("java.lang.NumberFormatException: The specified ID is not a valid snowflake"))
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴´Role selection error");
				number.setDescription("Not a valid role");
				event.getChannel().sendMessage(number.build()).queue();
			}
			//if they mention a person
			if(e.toString().startsWith("java.lang.IllegalArgumentException: Role may not be null"))
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴´Role selection error");
				number.setDescription("Not a valid role");
				event.getChannel().sendMessage(number.build()).queue();
			}
			return;
		}
	}
}
	
