package forge.Kbot;

import Commands.Ban;
import Commands.Clear;
import Commands.Help;
import Commands.Info;
import Commands.Reaction;
import Commands.Spam;
import Events.GuildMemberJoin;
import Events.GuildMemberLeave;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Kbot {
	public static JDA jda;
	
	public static String prefix = "$";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		

		
		//build the bot
		
		jda = new JDABuilder(AccountType.BOT).setToken("NzkwOTc4OTg5NjkwMDYwODAw.X-Ielg.a1WqGJSH1h9kSIse6BGTQUzNLpc").build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.listening("WOMPUS"));
		
		jda.addEventListener(new Info());
		jda.addEventListener(new Clear());
		jda.addEventListener(new Spam());
		jda.addEventListener(new Help());
		jda.addEventListener(new Reaction());
		jda.addEventListener(new Ban());
		
		jda.addEventListener(new GuildMemberJoin());
		jda.addEventListener(new GuildMemberLeave());
		
		//invite link
		//https://discord.com/oauth2/authorize?client_id=790978989690060800&permissions=8&scope=bot
	
	}
	
}
