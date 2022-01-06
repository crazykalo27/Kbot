package Commands;

import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Spam extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "spam"))
		{
		
			try
			{
				String message = (String) args[1];
				String epic = (String) args[1];
				int times = Integer.parseInt(args[2]);
			
				for(int i = times -1; i > 0; i--)
				{
					epic += " "+message;
				}
			
			
				if(!args[1].equals(1))
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage(epic).queue();
				}
			
				if(args[1].equals(1))
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage(message).queue();
				}
				epic = "";
				message = "";
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				//if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 2 out of bounds for length 2"))
				
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Command Usage Error");
					number.setDescription("How to use spam: \n `$spam [WORD TO SPAM] [AMOUNT OF TIMES]`");
					event.getChannel().sendMessage(number.build()).queue();
				
			}
			catch(IllegalArgumentException e)
			{
				if(e.toString().startsWith("java.lang.IllegalArgumentException: Provided text for message must be less than 2000 characters in length"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Limit Exceed Error");
					number.setDescription("The bot can only send up to 2000 characters at a time");
					event.getChannel().sendMessage(number.build()).queue();
				}
			}
		}
	}
}
