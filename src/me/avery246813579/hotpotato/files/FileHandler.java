package me.avery246813579.hotpotato.files;

import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {
	public static ConfigFile ConfigFile;
	public static DataFile DataFile;
	public static TextFile TextFile;
	public static SignFile SignFile;
	
	public FileHandler(){
		ConfigFile = new ConfigFile();
		DataFile = new DataFile();
		TextFile = new TextFile();
		SignFile = new SignFile();
		
		updateFiles();
	}
	
	public void updateFiles(){
		/** I know, I am doing this in a very bad way. Don't judge I was crunched on time **/
		if(!ConfigFile.getFile().contains("canFlyWhenDead")){
			ConfigFile.getFile().set("canFlyWhenDead", false);
		}
		
		if(!ConfigFile.getFile().contains("endTime")){
			ConfigFile.getFile().set("canFlyWhenDead", 10);
		}
		
		if(!ConfigFile.getFile().contains("prepareTime")){
			ConfigFile.getFile().set("prepareTime", 10);
		}
		
		if(!ConfigFile.getFile().contains("lobbyTime")){
			ConfigFile.getFile().set("lobbyTime", 10);
		}
		
		if(!ConfigFile.getFile().contains("maxGameLength")){
			ConfigFile.getFile().set("maxGameLength", 6000);
		}
		
		if(!ConfigFile.getFile().contains("minPlayers")){
			ConfigFile.getFile().set("minPlayers", 2);
		}
		
		if(!ConfigFile.getFile().contains("maxPlayers")){
			ConfigFile.getFile().set("maxPlayers", 8);
		}
		
		if(!ConfigFile.getFile().contains("description")){
			ConfigFile.getFile().set("description", "Try to get rid of the Potato by smacking a player! Last one alive wins!");
		}
		
		if(!ConfigFile.getFile().contains("potatoTime")){
			ConfigFile.getFile().set("potatoTime", 20);
		}
		
		if(!ConfigFile.getFile().contains("commandsOnWin")){
			ConfigFile.getFile().set("commandsOnWin", new ArrayList<String>(Arrays.asList("- 'Example Command'")));
		}
		
		/** Text File **/
		if(!TextFile.getFile().contains("makeSpec")){
			TextFile.getFile().set("makeSpec", "&aHot Potato >> &eYou are now spectator!");
		}

		if(!TextFile.getFile().contains("announceWinner")){
			TextFile.getFile().set("announceWinner", "&aHot Potato >> &e{OBJECT1} has won the battle!");
		}
		
		if(!TextFile.getFile().contains("timeRunout")){
			TextFile.getFile().set("timeRunout", "&aHot Potato >> &eTime has run out! Game has been reset!");
		}
		
		if(!TextFile.getFile().contains("announcePrepare")){
			TextFile.getFile().set("announcePrepare", "&aHot Potato >> &eGame starting in {OBJECT1}");
		}
		
		if(!TextFile.getFile().contains("notEnoughPlayers")){
			TextFile.getFile().set("notEnoughPlayers", "&aHot Potato >> &eNot enough players have joined! Game has been reset!");
		}
		
		if(!TextFile.getFile().contains("lobbyCountdown")){
			TextFile.getFile().set("lobbyCountdown", "&aHot Potato >> &e&lGame starting in {OBJECT1}!");
		}
		
		if(!TextFile.getFile().contains("maxPlayersReached")){
			TextFile.getFile().set("maxPlayersReached", "&aHot Potato >> &cUnable to join! The match is full!");
		}
		
		if(!TextFile.getFile().contains("onArenaJoin")){
			TextFile.getFile().set("onArenaJoin", "&aHot Potato >> &eYou have joined the arena");
		}
		
		if(!TextFile.getFile().contains("playerDeath")){
			TextFile.getFile().set("playerDeath", "&aHot Potato >> &c{OBJECT1} has been eliminated!");
		}
		
		if(!TextFile.getFile().contains("newPotato")){
			TextFile.getFile().set("newPotato", "&aHot Potato >> &e{OBJECT1} is the new Potato!");
		}
		
		if(!TextFile.getFile().contains("newPotatoTimeContinued")){
			TextFile.getFile().set("newPotatoTimeContinued", "&aHot Potato >> &c{OBJECT1} is the new Potato!");
		}
		
		if(!TextFile.getFile().contains("canNotTalk")){
			TextFile.getFile().set("canNotTalk", "&aHot Potato >> &cThe chat is currently disabled!");
		}
		
		if(!TextFile.getFile().contains("noPermission")){
			TextFile.getFile().set("noPermission", "&aHot Potato >> &cYou need the permission &e{OBJECT1} &cto use this command!");
		}
		
		if(!TextFile.getFile().contains("canNotUseAtTime")){
			TextFile.getFile().set("canNotUseAtTime", "&aHot Potato >> &cThis command can not be used at this time!");
		}
		
		if(!TextFile.getFile().contains("usage")){
			TextFile.getFile().set("usage", "&aHot Potato >> &cUsage: &e{OBJECT1}");
		}
		
		if(!TextFile.getFile().contains("arenaAlreadyCreated")){
			TextFile.getFile().set("arenaAlreadyCreated", "&aHot Potato >> &cAlready created!");
		}
		
		if(!TextFile.getFile().contains("createdArena")){
			TextFile.getFile().set("createdArena", "&aHot Potato >> &eYou have created the arena {OBJECT1}!");
		}
		
		if(!TextFile.getFile().contains("arenaNotFound")){
			TextFile.getFile().set("arenaNotFound", "&aHot Potato >> &cArena not found!");
		}
		
		if(!TextFile.getFile().contains("updateCommand")){
			TextFile.getFile().set("updateCommand", "&aHot Potato >> &eYou have updated this arenas {OBJECT1}!");
		}
		
		if(!TextFile.getFile().contains("alreadyInGame")){
			TextFile.getFile().set("alreadyInGame", "&aHot Potato >> &cYou are already in arena!");
		}
		
		if(!TextFile.getFile().contains("notEnoughArgs")){
			TextFile.getFile().set("notEnoughArgs", "&aHot Potato >> &cNot enough arguments!");
		}
		
		if(!TextFile.getFile().contains("noArenaFound")){
			TextFile.getFile().set("noArenaFound", "&aHot Potato >> &cYou are not in a game!");
		}
		
		if(!TextFile.getFile().contains("leftArena")){
			TextFile.getFile().set("leftArena", "&aHot Potato >> &eYou have left the arena!");
		}
		
		if(!TextFile.getFile().contains("forceStart")){
			TextFile.getFile().set("forceStart", "&aHot Potato >> &e&l{OBJECT1} has force started the game!");
		}
		
		if(!TextFile.getFile().contains("forceStop")){
			TextFile.getFile().set("forceStop", "&aHot Potato >> &e&l{OBJECT1} has force stopped the game!");
		}
		if(!TextFile.getFile().contains("signCreate")){
			TextFile.getFile().set("signCreate", "&aHot Potato >> &e&lYou have created a hot potato sign!");
		}
		
		if(!TextFile.getFile().contains("cantFindArena")){
			TextFile.getFile().set("cantFindArena", "&aHot Potato >> &c&lError trying to find Arena!");
		}
		
		TextFile.saveFile();
		ConfigFile.saveFile();
	}
	
	public static void saveConfigs(){
		ConfigFile.saveFile();
		DataFile.saveFile();
		TextFile.saveFile();
		SignFile.saveFile();
	}
}
