package forge.Kbot;

import Commands.Ban;
import Commands.Clear;
import Commands.Help;
import Commands.Highlight;
import Commands.Info;
import Commands.Kick;
import Commands.Leave;
import Commands.Mute;
import Commands.Nickname;
import Commands.Ping;
import Commands.Poll;
import Commands.Roll;
import Commands.Spam;
import Listener.ElectionListener;
import Listener.ReactionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Kbot {
	public static JDA jda;
	public static JDABuilder jbuild;
	
	
	public static String prefix = "$";

	public static void main(String[] args) throws Exception {
		
		jda = JDABuilder.createDefault("insert bot token here").build();
				
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.watching("Battle-Bots"));
		//commands
		
		jda.addEventListener(new Info());
		jda.addEventListener(new Clear());
		jda.addEventListener(new Spam());
		jda.addEventListener(new Help());
		jda.addEventListener(new Ban());
		jda.addEventListener(new Poll());
		jda.addEventListener(new Mute());
		jda.addEventListener(new Highlight());
		jda.addEventListener(new Roll());
		jda.addEventListener(new Kick());
		jda.addEventListener(new Ping());
		jda.addEventListener(new Leave());
		jda.addEventListener(new Nickname());
		
		//listeners and events
		//jda.addEventListener(new GuildMemberJoin());
		//jda.addEventListener(new GuildMemberLeave());
		jda.addEventListener(new ReactionListener());
		jda.addEventListener(new ElectionListener());
		
		//invite link 
	
	}
	
}
