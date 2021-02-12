import React, { useState } from "react";
import { StartGame } from "./mancala/StartGame";
import { Play } from "./mancala/Play";
import { GameState } from "./mancala/gameState";

export function App() {

    const [ gameState, setGameState ] = useState<GameState | undefined>(undefined);
    const [ errorMessage, setErrorMessage ] = useState("");

    async function tryStartGame(playerOne: string, playerTwo: string) {
        localStorage.removeItem("gameState");
        if (!playerOne) {
            setErrorMessage("Player 1 name is required!");
            return;
        }

        if (!playerTwo) {
            setErrorMessage("Player 2 name is required!");
            return;
        }

        setErrorMessage("");

        try {
            const response = await fetch('mancala/api/players', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nameplayer1: playerOne , nameplayer2: playerTwo })
            });
    
            if (response.ok) {
                //Save gamestate to localstorage
                const gameState = await response.json();
                setGameState(gameState);
                localStorage.setItem("gameState", JSON.stringify(gameState));
            }
            setErrorMessage("Failed to start the game. Try again.");
        } catch (error) {
            setErrorMessage(error.toString());
        }
    }
    async function makeMove(pitIndex : number){
        try {
            const response = await fetch('mancala/api/play/'+pitIndex, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            });
            
            if (response.ok) {
                const gameState = await response.json();
                setGameState(gameState);
                localStorage.setItem("gameState", JSON.stringify(gameState));
            }
            setErrorMessage("Failed to do a move. Try again.");
        } catch (error) {
            setErrorMessage(error.toString());
        }
    }
    async function newGame(){
        console.log("Starting a new game");
        setErrorMessage("");
        localStorage.removeItem("gameState");
        //Trigger a change in state so React will be forced to re-render the page allowing for a new game
        setGameState(undefined);
    }
    //Check if gamestate is available in local storage, set as gameState if available
    var localGameState = localStorage.getItem("gameState");
    if(localGameState != null && !gameState) setGameState(JSON.parse(localGameState));
    if (!gameState) {
        return <StartGame onPlayersConfirmed={tryStartGame}
                          message={errorMessage}
        />
    }
    return <Play gameState={gameState}
                    onMove={makeMove}
                    startGame={tryStartGame}
                    startNewGame={newGame}
    />
}