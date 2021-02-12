package minecraft.arena;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class TestArenaJoinCommand {
  private ServerMock server;
  private MyPlugin plugin;
  private World world;
  private Location someLocation;
  private Arena testArena;

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    plugin = (MyPlugin) MockBukkit.load(MyPlugin.class);

    world = new WorldMock(Material.DIRT, 3);
    someLocation = new Location(world, 10, 10, 10, 360, 0);
    // convention: x depth, z width, y height
    testArena = new Arena(someLocation, 30, 40, 50);
  }

  @AfterEach
  public void tearDown() {
      MockBukkit.unmock();
  }

  @Test
  public void arenaJoinCommand_JoinCommandWithoutArguments_PlayerIsAssignedTo1OfTheTeams(){
    PlayerMock player = server.addPlayer();

    plugin.setCurrentArena(testArena);
    plugin.getArenaJoinCommand().onCommand(player, null, null, new String[] {});

    assertTrue(testArena.getTeamOne().contains(player) || testArena.getTeamTwo().contains(player), "Player was not asigned to any team");
  }

  @Test
  public void arenaJoinCommand_JoinCommandWithArgument1_PlayerIsAssignedToTeam1(){
    PlayerMock player = server.addPlayer();

    plugin.setCurrentArena(testArena);
    plugin.getArenaJoinCommand().onCommand(player, null, null, new String[] {"1"});

    assertTrue(testArena.getTeamOne().contains(player), "Player was not asigned to team 1");
  }

  @Test
  public void arenaJoinCommand_JoinCommandWithArgument1_PlayerIsAssignedToTeam2(){
    PlayerMock player = server.addPlayer();

    plugin.setCurrentArena(testArena);
    plugin.getArenaJoinCommand().onCommand(player, null, null, new String[] {"2"});

    assertTrue(testArena.getTeamTwo().contains(player), "Player was not asigned to team 2");
  }

  @Test
  public void arenaJoinCommand_JoinCommandWithArgumenInvalidNumber_PlayerIsAssignedTo1OfTheTeams(){
    PlayerMock player = server.addPlayer();

    plugin.setCurrentArena(testArena);
    plugin.getArenaJoinCommand().onCommand(player, null, null, new String[] {"3"});

    assertTrue(testArena.getTeamOne().contains(player) || testArena.getTeamTwo().contains(player), "Player was not asigned to any team");
  }

  @Test
  public void arenaJoinCommand_JoinCommandWithArgumentNaN_PlayerIsAssignedTo1OfTheTeams(){
    PlayerMock player = server.addPlayer();

    plugin.setCurrentArena(testArena);
    plugin.getArenaJoinCommand().onCommand(player, null, null, new String[] {"x"});

    assertTrue(testArena.getTeamOne().contains(player) || testArena.getTeamTwo().contains(player), "Player was not asigned to any team");
  }

  @Test
  public void arenaJoinCommand_JoinCommandButNoArenaCreated_PlayerIsNotAssignedTo1OfTheTeams(){
    PlayerMock player = server.addPlayer();

    plugin.getArenaJoinCommand().onCommand(player, null, null, new String[] {"x"});

    assertFalse(testArena.getTeamOne().contains(player) && testArena.getTeamTwo().contains(player), "Player was asigned to a team but there is no arena");
  }

  @Test
  public void arenaJoinCommand_JoinCommandNotPerformedByAPlayer_FalseReturned(){
    ConsoleCommandSender ConsoleCommandSnd = new ConsoleCommandSenderMock();

    plugin.setCurrentArena(testArena);
    
    assertFalse(plugin.getArenaJoinCommand().onCommand(ConsoleCommandSnd, null, null, new String[] {"x"}), "Command was not sent by a player but still processed");
  }
}
