const slotsOnBoard = document.querySelectorAll('#board > .slot');
const slotsInHand = document.querySelectorAll('#hand > .slot');

let draggedCard;

const rummikub = {};

function setupSlots(slotElement, x, y, source) {
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
    draggedCard = e.srcElement;
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

function dragDrop() {
    this.classList.remove("drop-allowed");
    this.append(draggedCard);
    let x = this.getAttribute('data-pos-x');
    let y = this.getAttribute('data-pos-y');
    console.log('dropped on ' + x + ' ' + y);
}

function firstFreeSlotInHand() {
    for (var slotElement of slotsInHand) {
        if (slotElement.childNodes.length === 0) {
            return slotElement;
        }
    }
    return undefined;
}

function addNewOne(color) {
    let number = Math.floor((Math.random() * 13) + 1);

    let div = document.createElement("div");
    div.classList.add('card');
    div.setAttribute('draggable', 'true');
    div.setAttribute('data-num', number);
    div.setAttribute('data-color', color);
    div.addEventListener('dragstart', onCardDragStart);
    div.addEventListener('dragend', onCardDragEnd);

    let numDiv = document.createElement('div');
    numDiv.classList.add('num');
    numDiv.appendChild(document.createTextNode(number));
    div.appendChild(numDiv);

    let circle = document.createElement('div');
    circle.classList.add('circle');
    circle.classList.add(color);
    div.appendChild(circle);

    let element = firstFreeSlotInHand();
    element.appendChild(div);
}


setupGroup(slotsOnBoard, 'board');
setupGroup(slotsInHand, 'hand');

addNewOne('blue');
addNewOne('black');
addNewOne('red');
addNewOne('yellow');
