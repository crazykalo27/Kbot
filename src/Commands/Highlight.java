package Commands;

import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Highlight extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");

		if(args[0].equalsIgnoreCase(Kbot.prefix + "highlight"))
		{
			
			event.getMessage().delete().complete();
			
			String message = "";
			for(int i = 1; i < args.length; i++)
			{
				message += args[i] + " ";
			}
			
			EmbedBuilder highlight = new EmbedBuilder();
			highlight.setColor(Color.ORANGE);
			highlight.setDescription("**"+message+"**");
			highlight.setFooter("🟡  Highlighted " + event.getAuthor().getName() ,event.getMember().getUser().getAvatarUrl());
			event.getChannel().sendMessage(highlight.build()).queue();
		}
	}
}
