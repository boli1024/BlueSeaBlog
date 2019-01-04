function getRandomNumber(min, max) {
    // 获取一个随机整数
    let range = max-min;
    return min + Math.round(Math.random() * range);
}

function getRandomColor() {
    // 获取一个随机 rgb 颜色
    return "rgb("+getRandomNumber(0,255)+","+getRandomNumber(0,255)+","+getRandomNumber(0,255)+")";
}

// let numberPool=[];

function getNoRepeatRandomNumber(list,index) {
//    获取不重复随机数，随机数池大小为20
    let number = -1;
    if(index === 19){
        number = list[index];
        list.length = 0;
    }
    if(list.length === 0){
        for(let i=0;i<20;i++){
            let randomNumber = getRandomNumber(1,1000);
            for(let j=0;j<list.length;j++){
                if(list[j] === randomNumber){
                    i--;
                    randomNumber = -1;
                    break;
                }
            }
            if(randomNumber !== -1){
                list.push(randomNumber);
            }
        }
    }
    if (number !== -1){
        return number;
    }
    else {
        return list[index];
    }
}
// let n = getNoRepeatRandomNumber(numberPool,0);
// console.log(n);
// console.log(numberPool);
// n = getNoRepeatRandomNumber(numberPool,19);
// console.log(n);
// console.log(numberPool);
