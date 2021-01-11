package Commands;


import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "info"))
		{
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("K$$) Bot Information");
			info.addField("", "", false);
			info.setDescription("Info about the best bot ever: Its really really really really really really really really cool.");
			info.setColor(Color.GREEN);
			info.setFooter("Info", event.getMember().getUser().getAvatarUrl());
		
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}
	}
}
