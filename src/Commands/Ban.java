package Commands;

import java.awt.Color;
import java.util.List;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ban extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "ban"))
		{
			try
			{
				String reason = "";
				for(String epic : args)
				{
					reason += epic + " ";
				}
				
				if(event.getMember().hasPermission(Permission.BAN_MEMBERS))
				{
					for(Member member : event.getMessage().getMentionedMembers())
					{
						member.ban(0, reason).queue();
						
						EmbedBuilder number = new EmbedBuilder();
						number.setColor(Color.GREEN);
						number.setTitle("🟢Banned");
						number.setDescription(event.getAuthor() + " successfully banned " + event.getMessage().getMentionedMembers().get(0).getUser().toString());
						event.getChannel().sendMessage(number.build()).queue();
						
						System.out.println("banned " + member);
					}
					
				}
				else
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Ban Hierarchy Error");
					number.setDescription("You don't have ban permissions :(");
					event.getChannel().sendMessage(number.build()).queue();
				}
			}
			catch(HierarchyException e)
			{
				if(e.toString().startsWith("net.dv8tion.jda.api.exceptions.HierarchyException: Can't modify"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Ban Hierarchy Error");
					number.setDescription("**Cannot ban someone that important!** \n They either have a higher role than me... \n or it is me. \n \n You wouldn't try and ban me right???? \n \n *nervous sweating*");
					event.getChannel().sendMessage(number.build()).queue();
				}
				else
				{
					System.out.println("error");
				}
	
				return;
			}
		}
	}
}
	
