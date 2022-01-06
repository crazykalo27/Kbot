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
			info.setDescription("**It that was made for the sole purpose of outdoing MEE6**."
					+ "\n \n The command prefix is `$`"
					+ "\n \n message `$help` for commands");
			info.setColor(Color.GREEN);
			info.setFooter("Called by "+ event.getAuthor().getName(), event.getMember().getUser().getAvatarUrl());
		
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}
	}
}
