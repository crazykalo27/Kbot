package Commands;


import forge.Kbot.Kbot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Leave extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "leave"))
		{
			event.getGuild().leave().queue();
		}
	}

}
