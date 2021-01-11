package Commands;

import java.awt.Color;
import java.util.List;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "clear"))
		{
			if(args.length != 2)
			{
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(Color.RED);
				usage.setTitle("🔴Specify amount to delete.");
				usage.setDescription("Usage: `" + Kbot.prefix + "clear [#number of messages]`");
				event.getChannel().sendMessage(usage.build()).queue();
			}
			else
			{
				try
				{
					List<Message> messages = event.getChannel().getHistory().retrievePast
							(Integer.parseInt(args[1])).complete();
					event.getChannel().deleteMessages(messages).queue();
					
					EmbedBuilder success = new EmbedBuilder();
					success.setColor(Color.GREEN);
					success.setTitle("🟢Successfully deleted " + args[1] + " messages.");
					event.getChannel().sendMessage(success.build()).queue();
				}
				catch(IllegalArgumentException e)
				{
					if(e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval limit is between") || e.toString().startsWith("java.lang.IllegalArgumentException: Must provide at least 2"))
					{
						EmbedBuilder number = new EmbedBuilder();
						number.setColor(Color.RED);
						number.setTitle("🔴Message number selection error");
						number.setDescription("From 2-100 messages can be deleted at one time");
						event.getChannel().sendMessage(number.build()).queue();
					}
					else
					{
						EmbedBuilder time = new EmbedBuilder();
						time.setColor(Color.RED);
						time.setTitle("🔴Selected messages are older than 2 weeks.");
						time.setDescription("Messages older than 2 weeks cannot be deleted.");
						event.getChannel().sendMessage(time.build()).queue();
					}

				}
				
			}	
			args[0] = " ";
		}
	}
}
