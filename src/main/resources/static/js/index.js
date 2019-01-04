let words = ["富强","民主","文明","和谐","自由","平等","公正","法治","爱国","敬业","诚信","友善"];
let imageUrls = ["url('images/back1.jpg')","url('images/back2.jpg')","url('images/back3.jpg')","url('images/back4.jpg')","url('images/back5.jpg')"];
let imageIndex = 0;

let randomNumberPool = [];
let randomNumberPoolIndex = 0;

let inNavbar = false;

function mousePosition(e) {
    // if($("#mouseSpan") !== undefined){
    //     $("#mouseSpan").remove();
    //     return;
    // }

    if(inNavbar){
        return;
    }

    let spanLeft,spanTop;
    e = e || window.event;
    if(e.pageX || e.pageY){
        spanLeft = parseInt(e.pageX)-5;
        spanTop = parseInt(e.pageY)-10;
    }
    console.log(spanLeft,spanTop);

    let red = getRandomNumber(0,255);
    let green = getRandomNumber(0,255);
    let blue = getRandomNumber(0,255);

    let word = document.createElement("span");
    word.innerText = words[getRandomNumber(0,11)];

    let wordId = "mouseSpan"+getNoRepeatRandomNumber(randomNumberPool,++randomNumberPoolIndex);
    if(randomNumberPoolIndex === 20){
        randomNumberPoolIndex = 0;
    }

    word.id = wordId;
    word.style.fontSize = "18px";
    word.style.position = "absolute";
    word.style.left = spanLeft + "px";
    word.style.top = spanTop + "px";
    word.style.color = "rgb("+red+","+green+","+blue+")";
    word.classList.add("noSelect");
    document.body.append(word);

    let mouseSpanLet = $("#"+wordId);
    mouseSpanLet.animate({top:spanTop-80 + "px"},"slow",function () {
        mouseSpanLet.remove();
    });
}

function swap(){
    if(imageIndex >= 5){
        imageIndex = 0;
    }
    let body1 = $("body");
    body1.css("background-image",imageUrls[imageIndex]);
    imageIndex++;
}

function swap2(){
    alert("sfsfd");
    let body2 = $("body");
    body2.animate({backgroundImage:"url('images/back4.jpg')"},3000);
}
// setInterval(swap,4000);
// document.onmousedown=mousePosition;

// $("#navbar").onmouseover=function () {
//   inNavbar = true;
//   console.log("进入状态栏");
// };
// $("#navbar").onmouseout=function () {
//     inNavbar = false;
//     console.log("移出状态栏");
// };


function isNavbar(e) {
    let navDiv = $("#navbar");
    let x = e.pageX;
    let y = e.pageY;
    let divXMin = navDiv.offsetLeft;
    let divXMax = divXMin + navDiv.width;
    let divYMin = navDiv.offsetTop;
    let divYMax = divYMin + navDiv.height;

    if(x>= divXMin && x <= divXMax && y >= divYMin && y <= divYMax){
        return true;
    }
    return false;
}