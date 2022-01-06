package Commands;

import java.awt.Color;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");

		if(args[0].equalsIgnoreCase(Kbot.prefix + "mute") && event.getMember().hasPermission(Permission.MANAGE_CHANNEL))
		{
			if(args.length == 2)
			{
				//.replace("<@", "").replace(">", "")
				
				Member member = event.getGuild().getMemberById(args[1].replace("<@!", "").replace(">", ""));
				List<Role> role = event.getGuild().getRolesByName("muted", true);
				System.out.println("muting" + member);
				try
				{
					if(!member.getRoles().contains(role.get(0)) && !member.getId().equals(event.getJDA().getSelfUser().getId()))
					{
						event.getGuild().addRoleToMember(member, role.get(0)).complete();
						
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.GRAY);
						embed.setDescription("⚫ Muted " + args[1]);
						event.getChannel().sendMessage(embed.build()).queue();
						
					}
					else if(member.getId().equals(event.getJDA().getSelfUser().getId()))
					{
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.RED);
						embed.setTitle("🔴 CANT MUTE BOT 🔴");
						embed.setDescription("HOW DARE YOU! YOU CANT MUTE ME!");
						event.getChannel().sendMessage(embed.build()).queue();
					}
					else
					{
						//unmute
						event.getGuild().removeRoleFromMember(member, role.get(0)).complete();
						
						
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.GRAY);
						embed.setDescription("⚫ Unmuted " + args[1]);
						event.getChannel().sendMessage(embed.build()).queue();
					}
				}
				catch(IndexOutOfBoundsException e)
				{
					e.printStackTrace();
					if(e.toString().startsWith("java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"))
					{
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.RED);
						embed.setTitle("🔴 No Muted Role");
						embed.setDescription("Create a roll with the name muted. Turn off speaking permissions for this role. Make sure this role is below K$$) in the list.");
						event.getChannel().sendMessage(embed.build()).queue();
						
						
					}
				}
				
			}
			else if(args.length == 3)
			{
				//timed mute
				Member member = event.getGuild().getMemberById(args[1].replace("<@!", "").replace(">", ""));
				List<Role> role = event.getGuild().getRolesByName("muted", true);
				System.out.println(role);
				
				event.getGuild().addRoleToMember(member, role.get(0)).complete();
				
				EmbedBuilder embed = new EmbedBuilder();
				embed.setColor(Color.GRAY);
				embed.setDescription("⚫ Muted " + args[1] + " for " + args[2] + " minutes.");
				event.getChannel().sendMessage(embed.build()).queue();
				
				//unmute after a few seconds
					TimerTask task = new TimerTask() {

						@Override
						public void run() {
							event.getGuild().removeRoleFromMember(member, role.get(0)).complete();
							
							EmbedBuilder embed = new EmbedBuilder();
							embed.setColor(Color.GRAY);
							embed.setDescription("⚫ Unmuted " + args[1]);
							event.getChannel().sendMessage(embed.build()).queue();
						}
					};		
					new Timer().schedule(task, Integer.parseInt(args[2])* 1000 * 60);
			}
			else
			{
				event.getChannel().sendMessage("Incorrect syntax. Use `$mute [user mention] [time]`").queue();
			}
		}
	}
}
