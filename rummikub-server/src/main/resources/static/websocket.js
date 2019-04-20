"use strict";

let messageHandlers = {
    'LoginResponse': handleLoginResponse,
    'PacketPlayerJoined': handlePlayerJoined,
    'PacketPlayerStart': handlePlayerStart,
    'PacketNewTurn': handleNewTurn,
    'PacketReceiveCard': handleReceiveCard,
    'PacketPlayerLeft': handlePlayerLeft,
    'PacketCardPlaced': handleCardPlaced,
    'PacketCardRemoved': handleCardRemoved
};

let uiComponents = {
    'modal': document.querySelector('.modal'),
    'login': document.querySelector('#login'),
    'name': document.querySelector("#name"),
    'action-button': document.querySelector("#action-buttons"),
    'actual-message': document.querySelector('#actual-message')
};

var connection;
let loggedInPlayers = [];
let messageForDialog;
var myTurn;

function reset() {
    connection && connection.close();
    connection = undefined;
    messageForDialog = '';
    loggedInPlayers = [];
    uiComponents['login']
        .onclick = connect;
}

function handleLoginResponse(response) {
    if (response.accepted) {
        setStatus('Logged in!');
        uiComponents['login']
            .removeAttribute('disabled');
        uiComponents['action-button']
            .textContent = 'Start Game';
        uiComponents['action-button']
            .onclick = startGame;
        closeModal();
    } else {
        messageForDialog = 'Login failed: ' + msg.reason;
        connection.close();
    }
}

function handlePlayerJoined(response) {
    loggedInPlayers.push(response.playerName);
    uiComponents['name']
        .value = loggedInPlayers;
    addPlayer(response.playerName);
}

function handlePlayerLeft(response) {
    loggedInPlayers = loggedInPlayers.filter(function (playerName) {
        return playerName !== response.playerName;
    });
    uiComponents['name']
        .value = loggedInPlayers;
    removePlayer(response.playerName);
}

function handleCardPlaced(response) {
    placeCard(response.card, response.x, response.y);
}

function handleCardRemoved(response) {
    removeCard(response.card, response.x, response.y);
}

function handlePlayerStart(response) {
    uiComponents['action-button']
        .textContent = 'End Turn';
    uiComponents['action-button']
        .onclick = doEndTurn;
}

function doEndTurn() {
    sendMessage({
        'type': 'PacketEndTurn'
    });
}

function handleNewTurn(response) {
    playerList.childNodes.forEach(function (childNode) {
        if (childNode.textContent === response.playerName) {
            childNode.classList.add('w3-green');
        } else {
            if (childNode.classList) {
                childNode.classList.remove('w3-green');
            }
        }
    });
    myTurn = response.myTurn;
    if (myTurn) {
        uiComponents['action-button']
            .classList
            .remove('w3-disabled');
    } else {
        uiComponents['action-button']
            .classList
            .add('w3-disabled');
    }
}

function handleReceiveCard(response) {
    let cardElement = allCards[response.card];
    placeCardOnBoard(cardElement, response.hint);
}

function onMessage(event) {
    console.log('received ' + event.data);
    var msg = JSON.parse(event.data);
    if (messageHandlers.hasOwnProperty(msg.type)) {
        messageHandlers[msg.type](msg);
    } else {
        messageForDialog = 'Protocol error, what is ' + msg.type + '?';
        connection.close();
    }
}

function onConnectionLost(event) {
    messageForDialog = messageForDialog || 'Failed to connect to the server!';
    error(messageForDialog);

    uiComponents['login']
        .removeAttribute('disabled');

    uiComponents['name']
        .removeAttribute('readonly');

    reset();
}

function sendMessage(msg) {
    connection.send(JSON.stringify(msg));
}

function onConnectionEstablished(event) {
    setStatus('Attempting to log in...');
    sendMessage({
        'type': 'LoginRequest',
        'playerName': uiComponents['name'].value
    });
}

function openModal() {
    uiComponents['modal']
        .classList
        .add('show-modal');
}

function updateUiForConnecting() {
    setStatus('Connecting...');

    uiComponents['login']
        .setAttribute('disabled', true);

    uiComponents['name'].setAttribute('readonly', true);
}

function setStatus(statusMessage) {
    uiComponents['actual-message'].innerHTML = statusMessage;
}

function error(errorMsg) {
    uiComponents['actual-message'].innerHTML = errorMsg;
    openModal();
}

function closeModal() {
    uiComponents['modal']
        .classList
        .remove('show-modal');
}

function connect() {
    reset();

    let host = document.location.hostname || 'localhost';
    let serverUrl = "ws://" + host + ":8080";

    updateUiForConnecting();
    connection = new WebSocket(serverUrl + '/websocket');
    connection.onopen = onConnectionEstablished;
    connection.onclose = onConnectionLost;
    connection.onmessage = onMessage;
}

function startGame() {
    sendMessage({
        'type': 'StartGameRequest'
    });
}
