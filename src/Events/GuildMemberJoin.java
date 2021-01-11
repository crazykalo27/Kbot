package Events;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter{
	
	String[] messages = {
			"[member] showed up. Someone show them the ropes.",
			"Never gonna give [member] up. Never gonna let [member] down!",
			"WATCH OUT, ITS [member]!",
			"Your new leader, [member], has arrived. BOW!",
			"BOING, [member] just bounced in.",
			"Oh no, [member] is here. Maybe if we hide he won't notice",
			"Yo wassup [member]! Remember me?",
			"God save the queen, and [member]",
			"Wooooo, [member] finally joined!"
	};
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event)
	{
		Random rand = new Random();
		int number = rand.nextInt(messages.length);
		
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(Color.CYAN);
		join.setDescription(messages[number].replace
				("[member]", event.getMember().getAsMention()));
		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
		
		
		
		
	}

}
