package Commands;

import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "help"))
		{
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Help");
			help.addField("", "", false);
			help.setDescription("Working Commands: \n `$clear [#number of messages]` \n Clears up to 100 messages. \n `$info` \n Terrible Info \n `$spam [WORD TO SPAM] [AMOUNT OF TIMES]` \n it spams duh");
			help.setColor(Color.GREEN);
			help.setFooter("Help", event.getMember().getUser().getAvatarUrl());
		
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(help.build()).queue();
			help.clear();
		}
	}
}
