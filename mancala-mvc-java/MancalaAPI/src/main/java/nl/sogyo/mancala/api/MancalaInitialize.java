/**
 * 
 */
package nl.sogyo.mancala.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.mancala.api.models.*;
import nl.sogyo.mancala.domain.Mancala;
import nl.sogyo.mancala.domain.MancalaImpl;

@Path("players")
public class MancalaInitialize {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response initialize(
			@Context HttpServletRequest request, 
			PlayerInput players) {
		
		HttpSession session= request.getSession(true);
		Mancala mancala = new MancalaImpl();

		String namePlayer1 = players.getNameplayer1();
		String namePlayer2 = players.getNameplayer2();
		
		mancala.setPlayerName(namePlayer1, 1);
		mancala.setPlayerName(namePlayer2, 2);
			
		session.setAttribute("mancala", mancala);		

		var output = new MancalaDto(mancala, namePlayer1, namePlayer2);
		return Response.status(200).entity(output).build();
	}
}
