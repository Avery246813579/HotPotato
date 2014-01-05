package me.avery246813579.hotpotato.handlers;

import me.avery246813579.hotpotato.HotPotato;

public class ConfigurationHandler {

	HotPotato plugin;
	
	/** Plugin Info **/
	private String prefix = "HotPotato";
	
	/** Game Info **/
	private int minPlayers = 6;
	private int maxPlayers = 12;
	
	private int lobbyTimer = 120;
	private int waitTimer = 10;
	private int loadingTimer = 10;
	private int endTimer = 10;
	private int blowupTimer = 15;
	
	private boolean xpTimer;
	private boolean spawnBack;
	private boolean saveHealth;
	private boolean flyinLobby = false;
	private boolean muteChatOnLoad;
	private boolean shootInAirOnRemove;
	private boolean announceOnDeath;
	
	public ConfigurationHandler ( HotPotato plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		if(plugin.getConfig().contains("prefix"))
			this.setPrefix(plugin.getConfig().getString("prefix"));
		if(plugin.getConfig().contains("minPlayers"))
			this.setMinPlayers(plugin.getConfig().getInt("minPlayers"));
		if(plugin.getConfig().contains("maxPlayers"))
			this.setMaxPlayers(plugin.getConfig().getInt("maxPlayers"));
		if(plugin.getConfig().contains("lobbyTimer"))
			this.setLobbyTimer(plugin.getConfig().getInt("lobbyTimer"));
		if(plugin.getConfig().contains("waitTimer"))
			this.setWaitTimer(plugin.getConfig().getInt("waitTimer"));
		if(plugin.getConfig().contains("loadingTimer"))
			this.setLoadingTimer(plugin.getConfig().getInt("loadingTimer"));
		if(plugin.getConfig().contains("endTimer"))
			this.setEndTimer(plugin.getConfig().getInt("endTimer"));
		if(plugin.getConfig().contains("blowupTimer"))
			this.setBlowupTimer(plugin.getConfig().getInt("blowupTimer"));
		if(plugin.getConfig().contains("xpTimer"))
			this.setXpTimer(plugin.getConfig().getBoolean("xpTimer"));
		if(plugin.getConfig().contains("spawnBack"))
			this.setSpawnBack(plugin.getConfig().getBoolean("spawnBack"));
		if(plugin.getConfig().contains("saveHealth"))
			this.setSaveHealth(plugin.getConfig().getBoolean("saveHealth"));
		if(plugin.getConfig().contains("flyinLobby"))
			this.setFlyinLobby(plugin.getConfig().getBoolean("flyinLobby"));
		if(plugin.getConfig().contains("muteChatOnLoad"))
			this.setMuteChatOnLoad(plugin.getConfig().getBoolean("muteChatOnLoad"));
		if(plugin.getConfig().contains("shootInAirOnRemove"))
			this.setShootInAirOnRemove(plugin.getConfig().getBoolean("shootInAirOnRemove"));
		if(plugin.getConfig().contains("announceOnDeath"))
			this.setAnnounceOnDeath(plugin.getConfig().getBoolean("announceOnDeath"));
	}

	/************************************************
	 * 
	 * 				Getters & Setters
	 * 
	 ************************************************/
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getLobbyTimer() {
		return lobbyTimer;
	}

	public void setLobbyTimer(int lobbyTimer) {
		this.lobbyTimer = lobbyTimer;
	}

	public int getWaitTimer() {
		return waitTimer;
	}

	public void setWaitTimer(int waitTimer) {
		this.waitTimer = waitTimer;
	}

	public int getLoadingTimer() {
		return loadingTimer;
	}

	public void setLoadingTimer(int loadingTimer) {
		this.loadingTimer = loadingTimer;
	}

	public int getEndTimer() {
		return endTimer;
	}

	public void setEndTimer(int endTimer) {
		this.endTimer = endTimer;
	}

	public int getBlowupTimer() {
		return blowupTimer;
	}

	public void setBlowupTimer(int blowupTimer) {
		this.blowupTimer = blowupTimer;
	}

	public boolean isXpTimer() {
		return xpTimer;
	}

	public void setXpTimer(boolean xpTimer) {
		this.xpTimer = xpTimer;
	}

	public boolean isSpawnBack() {
		return spawnBack;
	}

	public void setSpawnBack(boolean spawnBack) {
		this.spawnBack = spawnBack;
	}

	public boolean isSaveHealth() {
		return saveHealth;
	}

	public void setSaveHealth(boolean saveHealth) {
		this.saveHealth = saveHealth;
	}

	public boolean isFlyinLobby() {
		return flyinLobby;
	}

	public void setFlyinLobby(boolean flyinLobby) {
		this.flyinLobby = flyinLobby;
	}

	public boolean isMuteChatOnLoad() {
		return muteChatOnLoad;
	}

	public void setMuteChatOnLoad(boolean muteChatOnLoad) {
		this.muteChatOnLoad = muteChatOnLoad;
	}

	public boolean isShootInAirOnRemove() {
		return shootInAirOnRemove;
	}

	public void setShootInAirOnRemove(boolean shootInAirOnRemove) {
		this.shootInAirOnRemove = shootInAirOnRemove;
	}

	public boolean isAnnounceOnDeath() {
		return announceOnDeath;
	}

	public void setAnnounceOnDeath(boolean announceOnDeath) {
		this.announceOnDeath = announceOnDeath;
	}
}
