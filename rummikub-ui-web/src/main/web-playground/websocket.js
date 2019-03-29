"use strict";

let messageHandlers = {
    'LoginResponse': handleLoginResponse,
    'PacketPlayerJoined': handlePlayerJoined,
    'PacketPlayerLeft': handlePlayerLeft
};

let uiComponents = {
    'modal': document.querySelector('.modal'),
    'login': document.querySelector('#login'),
    'name': document.querySelector("#name"),
    'actual-message': document.querySelector('#actual-message')
};

var connection;
let loggedInPlayers = [];
let messageForDialog;

function reset() {
    connection && connection.close();
    connection = undefined;
    messageForDialog = '';
    loggedInPlayers = [];
}

function handleLoginResponse(response) {
    if (response.accepted) {
        setStatus('Logged in!');
        uiComponents['login']
            .removeAttribute('disabled');
        uiComponents['login']
            .value = 'Start';
    } else {
        messageForDialog = 'Login failed: ' + msg.reason;
        connection.close();
    }
}

function handlePlayerJoined(response) {
    loggedInPlayers.push(response.playerName);
    uiComponents['name']
        .value = loggedInPlayers;
}

function handlePlayerLeft(response) {
    loggedInPlayers = loggedInPlayers.filter(function (playerName) {
        return playerName !== response.playerName;
    });
    uiComponents['name']
        .value = loggedInPlayers;
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
    uiComponents['modal'].classList.add('show-modal');
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
    uiComponents['modal'].classList.remove('show-modal');
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
