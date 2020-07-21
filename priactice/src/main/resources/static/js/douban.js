let input = document.getElementById("search");
let x = document.getElementById("icon-x");
let ul = document.getElementById("song-list");
function createli(songs){
    ul.innerHTML='';
    console.log(songs.empty);
    if (songs.empty){
        ul.innerHTML=`<p>没有找到！</p>`;
    } ;
    for (const song of songs.content) {
        const li = document.createElement("li");
        li.innerHTML=`
        <div class="songbox">
                   <img class="avator" src="${song.cover}" alt="">
                   <p>${song.name}</p>
               </$>
        `;
        ul.appendChild(li);
    }

}

function show(e) {
    const value = e.target.value;
    if (value === null || value === "") {
        ul.innerHTML="";
        x.style.display = "none";
    } else {
        fetch("/searchContent?keyword="+value).then(function(response){
            return response.json();
        }).then(function(data){
            createli(data.songs);
        });
        x.style.display = "inline-block";
    }

}
console.log(input);
input.addEventListener("input", show);

function clear() {
    input.value="";
    x.style.display="none";
    ul.innerHTML="";
}
x.addEventListener("click",clear);

var qrcode = new QRCode('qrcode', {
    text: 'https://douban.fm',
    width: 120,
    height: 120,
    colorDark: '#000000',
    colorLight: '#ffffff',
    correctLevel: QRCode.CorrectLevel.H
});

//注册登录窗口切换
let buttonList = document.getElementById("switch").children;
let box = document.getElementById("box").children;
for (let index = 0; index < buttonList.length; index++) {
    buttonList[index].addEventListener("click", function () {
        // if(box[i].className!=="show"){
        //     box[i].className = "show";
        // }else{
        //     box[i].className = "regi";
        // }
        for (let i = 0; i < buttonList.length; i++) {
            buttonList[i].removeAttribute("class");
            box[i].removeAttribute('class');
        }
        this.className = "current-button";
        box[index].className = "show";
    });

}
//表单不为空提示
function validatedNull(e){
    let value = e.target.value;
    if(value.trim()===null||value.trim()===""){
        e.target.style.borderColor= "red";
    }else{
        e.target.style.borderColor= "#E2E2E2";
    }
}
let inputdom = document.querySelectorAll("input");
//事件监听
inputdom.forEach(element => {
    element.addEventListener('blur',validatedNull);
});

let qr = document.getElementById("fr");
let img = document.getElementById("img");

function show() {
    img.style.display = "block";
};

function dishow() {
    img.style.display = "none";
}
qr.addEventListener("mouseenter", show);
qr.addEventListener("mouseleave", dishow);
$(document).ready(function(){
    $("#songlistsong").mouseenter(function(){
        $("#actions").show();
        console.log("enter");
    });
    $("#songlistsong").mouseleave(function(){
        $("#actions").hide();
    });
});

var bg = document.getElementById("bg");
var share = document.getElementById("share");
//分享show
$(document).ready(function () {
    $("#share-icon").click(function (){
        $("#share").show();
    });
    //X隐藏
    $("#close").click(function () {
        $("#share").hide();
    });
});
//点击分享模态框外部隐藏
window.onclick=(function (event) {
    if (event.target===bg) {
        share.style.display = "none";
    }

});
//获取video元素
var myVideo = document.getElementById('vdo');
var playicon = document.getElementById("play-icon");
var time = document.getElementById("time");
var tol = 0;
console.log(myVideo);

myVideo.addEventListener("loadedmetadata", function(){
    //要执行的代码
    tol = myVideo.duration;//获取总时长
    var currentTime = myVideo.currentTime;//获取当前播放时间
    var volume = myVideo.volume;//获取当前音量

    console.log(currentTime);
});

//播放
function play(){
    myVideo.play();
}
playicon.addEventListener("click",pause);
//设置音量
function setVol(num){
    myVideo.volume = num;
}
myVideo.addEventListener("timeupdate", function(){
    var currentTime = myVideo.currentTime;//获取当前播放时间
    var second = parseInt(currentTime%60);
    var min = parseInt(currentTime/60);

    time.innerText = min+":"+second+"s";
});
//暂停
function pause(){
    myVideo.pause();
}