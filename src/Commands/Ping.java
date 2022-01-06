package Commands;

import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		// && event.getMember().hasPermission(Permission.MESSAGE_MANAGE)
		if(args[0].equalsIgnoreCase(Kbot.prefix + "ping"))
		{
			try
			{
				Member member = event.getGuild().getMemberById(event.getMessage().getMentionedMembers().get(0).getId());
				String player = args[1];
				String user = event.getMember().getAsMention();
				
				if(event.getMessage().getMentionedMembers().get(0).getId().equals(event.getJDA().getSelfUser().getId()))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴DONT PING ME");
					number.setDescription("**IM RIGHT HERE FOOL**");
					event.getChannel().sendMessage(number.build()).queue();
					return;
				}
				
				for(int i = 3; i < args.length; i++)
				{
					args[2] += " " + args[i];
				}
				if(args.length > 2)
				{
					args[2] += " -" + user;
					for(int i = 0; i <5; i++)
					{
						event.getChannel().sendMessage(player + " " + args[2]).queue();
					}
				}
				else
				{
					for(int i = 0; i <5; i++)
					{
						event.getChannel().sendMessage(player).queue();
					}
				}
				
				member.getUser().openPrivateChannel().queue(channel -> {
					channel.sendMessage(event.getMember().getAsMention() + " wants you soooo bad at **" + event.getChannel().getName() + "** in **" + event.getGuild().getName() + "**.").queue();
					if(args.length > 2)
					{
						channel.sendMessage(args[2]).queue();
					}
				});
			}
			catch(IndexOutOfBoundsException e)
			{
				if(e.toString().startsWith("java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴User Specification Error");
					number.setDescription("Either you tried to ping no-one, or like EVERYONE. \n **BOTH** are not allowed :) \n \n Usage: `$ping [user] [optional messages]`");
					event.getChannel().sendMessage(number.build()).queue();
				}
				if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 2 out of bounds for length 2"))
				{
					Member member = event.getGuild().getMemberById(event.getMessage().getMentionedMembers().get(0).getId());
					String player = args[1];
					
					for(int i = 0; i <5; i++)
					{
						event.getChannel().sendMessage(player).queue();	
					}
					member.getUser().openPrivateChannel().queue(channel -> {
						channel.sendMessage(event.getMember().getAsMention() + " wants you soooo bad at **" + event.getChannel().getName() + "** in **" + event.getGuild().getName() + "**.").queue();
					});
				}
			e.printStackTrace();
			}
			
		}
	}
}

