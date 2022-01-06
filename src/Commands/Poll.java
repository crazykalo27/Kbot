package Commands;

import java.awt.Color;
import java.util.List;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Poll extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");

		if(args[0].equalsIgnoreCase(Kbot.prefix + "poll"))
		{
			event.getMessage().delete().queue();
			EmbedBuilder number = new EmbedBuilder();
			
			try 
			{
				
			System.out.println(args.length);
			
			String title = "";
			for(int i = 3; i < args.length; i++)
			{
				title += " " + args[i];
				System.out.println(title);
			}
				
			number.setColor(Color.CYAN);
			number.setTitle("🔵 `POLL`");
			number.setDescription("**" + title + "**" + "\n Choose between : \n 1️⃣ " + args[1] + " \n 2️⃣  " + args[2]);
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3"))
				{
					number.setDescription("Choose between : \n 1️⃣ "+args[1]+"\n 2️⃣ "+args[2]);
				}
				if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 1"))
				{
					EmbedBuilder embed = new EmbedBuilder();
					embed.setColor(Color.RED);
					embed.setTitle("🔴 `Command Usage Error`");
					embed.setDescription("Usage: `&poll [item-one] [item-two] [optional-header]`");
					event.getChannel().sendMessage(embed.build()).queue();
					
					return;
				}
				//dont return here
			}
			number.setFooter("Please only choose one option.");

			MessageEmbed embed = number.build();
			event.getChannel().sendMessage(embed).queue((message) -> {
				message.addReaction("1️⃣").queue();
				message.addReaction("2️⃣").queue();
				
			});
		}
	}
	
	
}
		
