package Commands;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Nickname extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "nick"))
		{
			String nick = args[1];
			int Length = args.length;
			
			for(int i = 2; i < Length; i++)
			{
				nick += " " + args[i];
			}
			
			Member botmember = event.getGuild().getMember(Kbot.jda.getSelfUser());
			
			botmember.modifyNickname(nick).queue();
			
			event.getMessage().addReaction("👍🏻").queue();
			
		}
	}
}
