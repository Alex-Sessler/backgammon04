package service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import model.dto.MovementDto;
import service.impl.exception.ForbiddenMoveException;
import backgammon04.model.Game;
import backgammon04.model.Movement;
import backgammon04.model.Player;
import backgammon04.model.User;

public interface MovementService {
	public List<MovementDto> assembly(Set<Movement> movementList);

	public MovementDto assembly(Movement movement);

	public void move(User user, long gameId, byte sourcePointInternalId,
			byte targetPointInternalId) throws ForbiddenMoveException;

	public Movement initializeMovement(Player player, Game game, Date created);

	public Movement getLastMovement(Game game);
}
