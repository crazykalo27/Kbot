package Commands;

import java.awt.Color;

import forge.Kbot.Kbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Reaction extends ListenerAdapter{

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if(args[0].equalsIgnoreCase(Kbot.prefix + "reaction"))
		{
			try 
			{
				String role = args[1];
				
				System.out.println(args.length);
				for(int i = 2; i < args.length; i++)
				{
					role += " " + args[i];
				}
				System.out.println(role);
				Role givenRole = event.getGuild().getRolesByName(role, true).get(0);
				
				System.out.println(event.getGuild().getRoles().toString());

				event.getGuild().addRoleToMember(event.getMember(), givenRole).complete();
			}
			
			catch(IndexOutOfBoundsException e)
			{
				//role not found
				if(e.toString().startsWith("java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Role selection error");
					number.setDescription("That role does not exist");
					event.getChannel().sendMessage(number.build()).queue();
				}
				//no role specified
				if(e.toString().startsWith("java.lang.ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 1"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Role specification error");
					number.setDescription("Specify a role: `$reaction [role-select]`");
					event.getChannel().sendMessage(number.build()).queue();
				}
			}
			catch(HierarchyException e)
			{
				if(e.toString().startsWith("net.dv8tion.jda.api.exceptions.HierarchyException: Can't modify"))
				{
					EmbedBuilder number = new EmbedBuilder();
					number.setColor(Color.RED);
					number.setTitle("🔴Role Hierarchy Error");
					number.setDescription("Cannot give a role higher than my own :)");
					event.getChannel().sendMessage(number.build()).queue();
				}
			}
			
				
		}
		
	}
}
	
