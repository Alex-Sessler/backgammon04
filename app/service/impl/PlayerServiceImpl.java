package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.dto.PlayerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.PlayerService;
import service.UserService;
import backgammon04.model.Game;
import backgammon04.model.Player;
import backgammon04.model.User;
import backgammon04.model.dao.PlayerDao;
import backgammon04.util.Color;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private UserService userService;

	@Override
	public Player createPlayer(Game game, User user, Color color) {
		Player player = (Player) applicationContext.getBean("playerImpl");
		player.setColor(color);
		player.setUser(user);
		player.setGame(game);

		playerDao.save(player);

		return player;
	}

	@Override
	public PlayerDto assembly(Player player) {
		PlayerDto playerDto = new PlayerDto();
		playerDto.setId(player.getId());
		playerDto.setColor(player.getColor());
		playerDto.setUser(userService.assembly(player.getUser()));
		return playerDto;
	}

	@Override
	public List<PlayerDto> assembly(Set<Player> playerList) {
		List<PlayerDto> playerDtoList = new ArrayList<PlayerDto>();
		for (Player player : playerList) {
			playerDtoList.add(assembly(player));
		}
		return playerDtoList;
	}

	@Override
	public void delete(Player player) {
		playerDao.delete(player);
	}

	@Override
	public Player getPlayer(Game game, User user) {
		return playerDao.get(game, user);
	}

	@Override
	public void update(Player player) {
		playerDao.save(player);
	}

}
