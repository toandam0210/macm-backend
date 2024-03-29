package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.ExhibitionPlayer;

@Repository
public interface ExhibitionPlayerRepository extends JpaRepository<ExhibitionPlayer, Integer>{
	@Query(value = "select * \r\n"
			+ "from exhibition_player inner join exhibition_team on exhibition_player.exhibition_team_id = exhibition_team.id  \r\n"
			+ "where player_id = ?1 and exhibition_type_id = ?2", nativeQuery = true)
	Optional<ExhibitionPlayer> findByTournamentPlayerAndType(int tournamentPlayerId, int exhibitionTypeId);
	
	@Query(value = "select * from exhibition_player where player_id = ?1 and exhibition_team_id isnull = false", nativeQuery = true)
	List<ExhibitionPlayer> findAllByPlayerId(int playerId);
	
	@Query(value = "select * from exhibition_player where player_id = ?1 and exhibition_team_id isnull = true", nativeQuery = true)
	List<ExhibitionPlayer> findAllByPlayerIdAndExhibitionNull(int playerId);
}
