//取消浏览器的所有事件，使得active的样式在手机上正常生效
document.addEventListener('touchstart',function(){
    return false;
},true);

// 禁止选择
document.oncontextmenu=function(){
    return false;
};
// H5 plus事件处理
var as='slide-in-right',at=200;// 默认动画时间
// 扩展API准备完成后要执行的操作
function plusReady(){
    // 隐藏滚动条
    plus.webview.currentWebview().setStyle({scrollIndicator:'none'});
    // Android处理返回键
    var pageUrl=window.location.href;
    plus.key.addEventListener('backbutton',function(){
        //判断是否返回到首页，是->退出,否则返回上一页
    	var title = document.title;
        if(pageUrl.indexOf('member.aysmzj.gov.cn/app')==-1 && title.indexOf('我的首页') == -1){
            history.back();
        }else{
            if(confirm('确认要退出吗？')){
                plus.runtime.quit();
            }
        }
    },false);
}

//扩展API是否准备好，如果没有则监听“plusready"事件
if(window.plus){
    plusReady();
}else{
    document.addEventListener('plusready',plusReady,false);
}