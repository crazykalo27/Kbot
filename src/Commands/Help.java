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
			help.setTitle("**Working Commands:**");
			help.addField("", "", false);
			
			help.setDescription("`$clear [#number of messages]` "
					+ "\n Deletes up to 100 messages. "
					+ "\n `$info` "
					+ "\n Terrible Info "
					+ "\n `$spam [phrase] [#number of messages]` "
					+ "\n It spams the phrase however many times you wanted "
					+ "\n `$ban [user-mention]` "
					+ "\n Bans the mentioned "
					+ "\n `$mute [user-mention] [optional-minutes]` "
					+ "\n Mutes the mentioned either forever or in minutes, do again to unmute "
					+ "\n `$kick [user-mention]` "
					+ "\n Kicks the mentioned"
					+ "\n `$highlight [message]` "
					+ "\n Makes your message go BOOM "
					+ "\n `$roll [die]` or `$roll [leave blank for 6]` "
					+ "\n Rolls a die up to given number and tells you that number "
					+ "\n `$ping [user-mention] [optional messages]` "
					+ "\n Immediatally @'s them 5 times, then dm's them as well.");
			
			
			
			help.setColor(Color.GREEN);
			help.setFooter("Helped "+ event.getAuthor().getName(), event.getMember().getUser().getAvatarUrl());
		
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(help.build()).queue();
			help.clear();
		}
	}
}
