import React, { useState } from "react";
import { GameState } from "./gameState";
import styled from "styled-components";

interface PlayProps {
    gameState: GameState;
    onMove(pitIndex: number): void;
    startGame(playerOne: string, playerTwo: string): void;
    startNewGame(): void;
}

const BowlTD = styled.td`
    height: 40px;
    width: 40px;
    font-size: 2em;
    background-color: lightblue;
    border: 2px solid black;
`
const BowlButton = styled.button`
    font-size: 2em;
    background-color: lightblue;
    border: 2px solid black;
`
const RematchButton = styled.button`
    font-size: 2em;
    background-color: lightblue;
    border: 2px solid black;
`
const NewGameButton = styled.button`
    font-size: 2em;
    background-color: lightblue;
    border: 2px solid black;
`
const BoardTable = styled.table`
    font-size: 2em;
    background-color: lightblue;
    border: 2px solid black;
`;

export function Play({ gameState, onMove, startGame, startNewGame }: PlayProps) {
    const newPlayProps = { gameState, onMove } as PlayProps;
    return <div>
        <p>{gameState.players[0].name} vs {gameState.players[1].name}</p>
        {gameState.players[0].hasTurn ? <p>turn: {gameState.players[0].name}(lower deck)</p> : <p>turn: {gameState.players[1].name}(upper deck)</p>}
        {gameState.gameStatus.endOfGame &&
                <div id="winner">
                    <p>Game over!</p>
                    {gameState.gameStatus.winner != null ?
                         <p>{gameState.gameStatus.winner} is the winner!</p> : <p>Draw!</p>
                    }
                    <RematchButton onClick={(e) => {startGame(gameState.players[0].name, gameState.players[1].name)}}>Rematch</RematchButton>
                    <NewGameButton onClick={startNewGame}>New Game</NewGameButton>
                </div>
            }
        <Board playProps={newPlayProps} key={"board"}/>
    </div>
}
class Board extends React.Component<{playProps : PlayProps}> {
    handleOnClick = (event : React.MouseEvent) => {
        var index = Number(event.currentTarget.getAttribute("data-index"));
        this.props.playProps.onMove(index);
    }
    
    render() {
        var playerOnePits = [];
        var playerOne = this.props.playProps.gameState.players[0];
        for(var i = 0; i < this.props.playProps.gameState.players[0].pits.length - 1; i++){
            playerOnePits.push(<BowlTD key={playerOne.name + i}>
                <BowlButton data-index={playerOne.pits[i].index} onClick={this.handleOnClick}>{ this.props.playProps.gameState.players[0].pits[i].nrOfStones }</BowlButton></BowlTD>);
        }
        var playerTwoPits = [];
        var playerTwo = this.props.playProps.gameState.players[1];
        for(var k = this.props.playProps.gameState.players[1].pits.length - 2; k >= 0; k--){
            playerTwoPits.push(<BowlTD key={playerTwo.name + k}>
                <BowlButton data-index={playerTwo.pits[k].index} onClick={this.handleOnClick}>{ this.props.playProps.gameState.players[1].pits[k].nrOfStones }</BowlButton></BowlTD>);
        }

        var kalahas = [];
        kalahas.push(<BowlTD key={playerTwo.name + "kalaha"}><BowlButton>{ this.props.playProps.gameState.players[1].pits[this.props.playProps.gameState.players[1].pits.length - 1].nrOfStones }</BowlButton></BowlTD>);
        for(var j = 0; j < this.props.playProps.gameState.players[0].pits.length - 1; j++){
            kalahas.push(<BowlTD key = {"empty" + j}/>);
        }
        kalahas.push(<BowlTD key={playerOne.name + "kalaha"}><BowlButton>{ this.props.playProps.gameState.players[0].pits[this.props.playProps.gameState.players[0].pits.length - 1].nrOfStones }</BowlButton></BowlTD>);
        
        return <BoardTable key={"boardTable"}>
            <tbody>
                <tr>
                    <BowlTD key={"prependPlayerTwo"}/>
                    {playerTwoPits}
                    <BowlTD key={"appendPlayerTwo"}/>
                </tr>
                <tr>
                    {kalahas}
                </tr>
                <tr>
                    <BowlTD key={"prependPlayerOne"}/>
                    {playerOnePits}
                    <BowlTD key={"appendPlayerOne"}/>
                </tr>
            </tbody>
        </BoardTable>;
    }
}