package Commands;

import java.awt.Color;
import java.util.List;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
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
				List<Member> member = event.getMessage().getMentionedMembers();
				
				event.getGuild().ban(member.get(0), 0).complete();
				System.out.println("banned " + member);
			}
			catch(HierarchyException e)
			{
				if(e.toString().startsWith("net.dv8tion.jda.api.exceptions.HierarchyException: Can't modify"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Ban Hierarchy Error");
					number.setDescription("Cannot ban someone that important! \n (They have a higher role than me O_O)");
					event.getChannel().sendMessage(number.build()).queue();
				}
			}
		}
	}
}
	
