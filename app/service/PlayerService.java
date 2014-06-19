package service;

import java.util.List;
import java.util.Set;

import model.dto.PlayerDto;
import backgammon04.model.Game;
import backgammon04.model.Player;
import backgammon04.model.User;
import backgammon04.util.Color;

public interface PlayerService {

	public Player createPlayer(Game game, User user, Color color);

	public List<PlayerDto> assembly(Set<Player> playerList);

	public PlayerDto assembly(Player player);

	public void delete(Player player);

	public void update(Player player);

	public Player getPlayer(Game game, User user);

}
