const slotsOnBoard = document.querySelectorAll('#board > .slot');
const slotsInHand = document.querySelectorAll('#hand > .slot');

let draggedCard;
let slots = [];

const rummikub = {};

function setupSlots(slotElement, x, y, source) {
    if (!slots[source]) {
        slots[source] = [];
    }
    if (!slots[source][x]) {
        slots[source][x] = [];
    }
    slots[source][x][y] = slotElement;
    slotElement.setAttribute('data-pos-x', x);
    slotElement.setAttribute('data-pos-y', y);
    slotElement.setAttribute('data-source', source);

    slotElement.addEventListener('dragover', dragOver);
    slotElement.addEventListener('dragenter', dragEnter);
    slotElement.addEventListener('dragleave', dragLeave);
    slotElement.addEventListener('drop', dragDrop);
}

function setupGroup(elements, source) {
    for (var i = 0; i < elements.length; i++) {
        var slotElement = elements[i];
        var x = i % 20;
        var y = parseInt(i / 20);
        setupSlots(slotElement, x, y, source);
    }
}

function onCardDragStart(e) {
    draggedCard = e.target;
    this.classList.add('hold');
    requestAnimationFrame(() => (this.classList.add('invisible')));
}

function onCardDragEnd() {
    draggedCard = undefined;
    this.classList.remove('hold');
    this.classList.remove('invisible');
}

function dragOver(event) {
    if (event.currentTarget.childNodes.length === 0) {
        event.preventDefault();
    }
}

function dragEnter(e) {
    this.classList.add("drop-allowed");
}

function dragLeave() {
    this.classList.remove("drop-allowed");
}

function dragDrop(event) {
    let sourceSlotElement = draggedCard.parentElement;
    let sourceX = sourceSlotElement.getAttribute('data-pos-x');
    let sourceY = sourceSlotElement.getAttribute('data-pos-y');
    let sourceLocation = sourceSlotElement.getAttribute('data-source');

    this.classList.remove("drop-allowed");

    let targetX = this.getAttribute('data-pos-x');
    let targetY = this.getAttribute('data-pos-y');
    let targetLocation = this.getAttribute('data-source');
    console.log(sourceLocation + ' ' + sourceX + ',' + sourceY + ' => ' + targetLocation + ' ' + targetX + ',' + targetY);
    if (sourceLocation === 'hand') {
        if (targetLocation === 'board') {
            sendMessage({
                'type': 'PacketPlaceCard',
                'card': allCards.indexOf(draggedCard),
                'x': targetX,
                'y': targetY
            });
        }
    } else if (sourceLocation === 'board') {
        if (targetLocation === 'board') {
            sendMessage({
                'type': 'PacketMoveCard',
                'fromX': sourceX,
                'fromY': sourceY,
                'toX': targetX,
                'toY': targetY
            });
        } else {
            sendMessage({
                'type': 'PacketTakeCard',
                'card': allCards.indexOf(draggedCard),
                'x': sourceX,
                'y': sourceY,
                'hint': targetX
            });
        }
    }
    draggedCard = undefined;
}

function placeCard(card, x, y) {
    let cardElement = allCards[card];
    let target = slots['board'][x][y];
    target.append(cardElement);
}

function removeCard(card, x, y) {
    let cardElement = allCards[card];
    let target = slots['board'][x][y];
    target.removeChild(cardElement);
}

function firstFreeSlotInHand() {
    for (var slotElement of slotsInHand) {
        if (slotElement.childNodes.length === 0) {
            return slotElement;
        }
    }
    return undefined;
}

function createCardElement(number, color) {
    let div = document.createElement("div");
    div.classList.add('card');
    div.setAttribute('draggable', 'true');
    div.setAttribute('data-num', number);
    div.setAttribute('data-color', color);
    div.addEventListener('dragstart', onCardDragStart);
    div.addEventListener('dragend', onCardDragEnd);

    if (!number) {
        number = 'J';
    }
    if (!color) {
        color = 'black';
    }

    let numDiv = document.createElement('div');
    numDiv.classList.add('num');
    let textElement = document.createTextNode(number);
    numDiv.appendChild(textElement);
    div.appendChild(numDiv);

    let circleElement = document.createElement('div');
    circleElement.classList.add('circle');
    circleElement.classList.add(color);
    div.appendChild(circleElement);
    return div;
}

function createAllCards() {
    const colors = ['red', 'blue', 'black', 'yellow'];
    const cards = [];
    for (let i = 0; i < 2; i++) {
        for (let cardNum = 1; cardNum <= 13; cardNum++) {
            for (let colorIndex = 0; colorIndex < colors.length; colorIndex++) {
                cards.push(createCardElement(cardNum, colors[colorIndex]));
            }
        }
        cards.push(createCardElement('', ''));
    }
    return cards;
}

function placeCardOnBoard(cardElement) {
    let element = firstFreeSlotInHand();
    element.appendChild(cardElement);
}

setupGroup(slotsOnBoard, 'board');
setupGroup(slotsInHand, 'hand');

let allCards = createAllCards();