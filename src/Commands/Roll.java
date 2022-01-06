package Commands;

import java.awt.Color;
import java.util.Random;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Roll extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "roll"))
		{
			try
			{
				Random rand = new Random();
				int roll = rand.nextInt(Integer.parseInt(args[1]))+1;
				
				EmbedBuilder dice = new EmbedBuilder();
				dice.setColor(Color.BLACK);
				dice.setDescription(event.getAuthor().getAsMention()+" rolled a "+roll+" out of "+ args[1]);
				event.getChannel().sendMessage(dice.build()).queue();
			}
			catch(ArrayIndexOutOfBoundsException | NumberFormatException e)
			{

				if(e.toString().startsWith("java.lang.NumberFormatException: For input string:"))
				{
					EmbedBuilder dice = new EmbedBuilder();
					dice.setColor(Color.RED);
					dice.setTitle("🔴´Command Usage Error");
					dice.setDescription("Usage: `" + Kbot.prefix + "roll [die]` or \n `" + Kbot.prefix + "roll [leave blank for 6]`");
					event.getChannel().sendMessage(dice.build()).queue();
				}
				if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 1"))
				{
					Random rand = new Random();
					int roll = rand.nextInt(6)+1;
					
					EmbedBuilder dice = new EmbedBuilder();
					dice.setColor(Color.BLACK);
					dice.setDescription(event.getAuthor().getAsMention()+" rolled a "+roll+" out of 6");
					event.getChannel().sendMessage(dice.build()).queue();
				}
			}
			
			
		}
	}
}
