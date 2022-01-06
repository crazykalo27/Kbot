package Events;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class GuildMemberLeave extends ListenerAdapter{
	
	String[] messages = {
			"[member] left up. Someone hang up the ropes.",
			"Never gonna give [member] up. [member] is gone tho.",
			"PHEW, [member]s gone!",
			"Your old leader, [member], has departed.",
			"BOING, [member] just bounced out.",
			"Oh no, [member] is gone",
			"Yo cya [member]! Never liked you anyway",
			"Rip the queen, and [member]",
			"Wooooo, [member] finally left!" };
	
	@SuppressWarnings("deprecation")
	public void onGuildMemberLeave(GuildMemberLeaveEvent event)
	{
		Random rand = new Random();
		int number = rand.nextInt(messages.length);
		
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(Color.MAGENTA);
		join.setDescription(messages[number].replace
				("[member]", event.getMember().getAsMention()));
		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
		
		Member member = event.getMember();
		
		member.getUser().openPrivateChannel().queue(channel -> {
			channel.sendMessage("we might miss you").queue();
		});
	}
	

}
