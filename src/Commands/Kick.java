package Commands;

import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Kick extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "kick"))
		{
			if(event.getMember().hasPermission(Permission.KICK_MEMBERS))
			{
				for(Member member : event.getMessage().getMentionedMembers())
				{
					member.kick().queue();
				}
			}
			else
			{
				EmbedBuilder number = new EmbedBuilder();
				number.setColor(Color.RED);
				number.setTitle("🔴Kick Hierarchy Error");
				number.setDescription("You don't have kick permissions :()");
				event.getChannel().sendMessage(number.build()).queue();
			}
		}
	}

}
