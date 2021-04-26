
let canvas = document.getElementById("main-canvas");
let gc = canvas.getContext("2d");

const socketRoute = document.getElementById("sprite-route").value;
const socket = new WebSocket(socketRoute.replace("http", "ws"));

document.onkeydown = move;

let px = 160;
let py = 100;
let psize = 50;
let pspeed = 15;
let pcolor = "#073b4c";

gc.strokeRect(0,0,canvas.width, canvas.height);

socket.onopen = (event) => drawPlayer(px, py, psize);
socket.onmessage = (event) => {
    console.log("got a message");
    console.log(event.data);

    let sprites = JSON.parse(event.data)
    for (let i=0; i<sprites.length; i++) {
        let pcolor = sprites[i][2]
        let x = sprites[i][0]
        let y = sprites[i][1]
        gc.fillStyle = pcolor;
        gc.fillRect(x, y, psize, psize);
    }
}


function move(e) {
    gc.clearRect(px, py, psize, psize);
    if(e.keyCode == 87 || e.keyCode == 38) {
        py-=pspeed;
    }
    if(e.keyCode == 65 || e.keyCode == 37) {
        px-=pspeed;
    }
    if(e.keyCode == 83 || e.keyCode == 40) {
        py+=pspeed;
    }
    if(e.keyCode == 68 || e.keyCode == 39) {
        px+=pspeed;
    }
    drawPlayer(px,py,psize);
}


function drawPlayer(x,y,size) {
    gc.fillStyle = pcolor;
    gc.fillRect(x, y, size, size);
    //socket.send("actors.SpriteActor.DrawSprite(100, 100, 50, #073b4c)");
    //socket.send("200, 100, 50, color");
    socket.send(JSON.stringify([x, y, pcolor]));
}

function colorChange(color) {
    pcolor = color;
    drawPlayer(px,py,psize);
}
